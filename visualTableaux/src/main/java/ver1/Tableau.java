package ver1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import ownapi.*;
import ver1.Operation.OPERATOR;

public class Tableau {
	private HashSet<OWNAxiom> K;
	private byte nodeCode;
	private TreeNode firstNode;
	private HashMap<Node, HashSet<OWNAxiom>> Ln;
	private HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> Lr;
	private HashMap<Node, HashSet<Operation>> operations;
	private HashSet<NonDeterministicOperation> conflictingOperations;
	private HashSet<Node> blockedNodes;
	private boolean clashed, finished;
	
	public Tableau(HashSet<OWNAxiom> K) {
		this.K = K;
		nodeCode = 109; // ASCII m
		this.firstNode = new TreeNode(new Node(Character.toString((char)nodeCode++)));
		Ln = new HashMap<Node, HashSet<OWNAxiom>>();
		Lr = new HashMap<Pair<Node, Node>, HashSet<OWNLiteral>>();
		operations = new HashMap<Node, HashSet<Operation>>();
		conflictingOperations = new HashSet<NonDeterministicOperation>();
		blockedNodes = new HashSet<Node>();
		clashed = false;
		finished = false;
	}
	
	public void init(OWNAxiom concept) {
		// Add first node with initial concept to L
		Ln.put(firstNode.getData(), new HashSet<OWNAxiom>());
		Ln.get(firstNode.getData()).add(concept);
		// Get operations for firstNode
		operations.put(firstNode.getData(), new HashSet<Operation>());
		updateOperations(firstNode);
	}
	
	///////////////////////
	/////   GETTERS   /////
	///////////////////////
	public String getOntology() {
		return K.toString();
	}
	
	public TreeNode getFirstNode() {
		return firstNode;
	}
	
	public String checkNextCreatedNode() {
		return Character.toString((char)nodeCode);
	}
	
	public HashMap<Node, HashSet<Operation>> getOperations() {
		return operations;
	}
	
	public String getAxioms(Node n) {
		return Ln.get(n).toString();
	}
	
	public String getRelations(Node pred, Node succ) {
		return Lr.get(new Pair<Node, Node>(pred, succ)).toString();
	}
	
	//////////////////////////////
	/////   PUBLIC METHODS   /////
	//////////////////////////////
	public void apply(Node n, Operation op) {
		switch (op.getOperator()) {
			case TOP: {
				Ln.get(n).add(op.getOperand1());
				break;
			}
			case BOTTOM: {
				Ln.get(n).add(OWNAxiom.BOTTOM);
				clashed = true;
				break;
			}
			case AND: {
				OWNIntersection intersection = (OWNIntersection)op.getOperand1();
				Ln.get(n).add(intersection.getOperand1());
				Ln.get(n).add(intersection.getOperand2());
				break;
			}
			case OR: {
				break;
			}
			case SOME: {
				OWNExistential existential = (OWNExistential)op.getOperand1();
				TreeNode tn = TreeNode.getTreeNode(firstNode, n);
				Node newNode = new Node(Character.toString((char)nodeCode++));
				tn.addChild(newNode);
				Pair<Node, Node> p = new Pair<Node, Node>(n, newNode);
				Lr.put(p, new HashSet<OWNLiteral>());
				Lr.get(p).add(existential.getRelation());
				Ln.put(newNode, new HashSet<OWNAxiom>());
				Ln.get(newNode).add(existential.getOperand());
				operations.put(newNode, new HashSet<Operation>());
				break;
			}
			case ONLY: {
				OWNUniversal universal = (OWNUniversal)op.getOperand1();
				Ln.get(op.getNode()).add(universal.getOperand());
				break;
			}
		}
		
		// TODO get new operations
		
		// TODO check blocking
	}
	
	public boolean isFinished() {
		// Traverse all nodes and check if finished still true after traversal
		finished = true;
		iterativePreorder(firstNode, "checkFinished");
		return finished;
	}
	
	public boolean isSatisfiable() {
		return finished && !clashed;
	}
	
	///////////////////////////////////////////////////
	/////   TREE TRAVERSAL AND VISITING METHODS   /////
	///////////////////////////////////////////////////
	public void iterativePreorder(TreeNode treeNode, String method)  {
		Stack<TreeNode> s = new Stack<TreeNode>();
		s.push(treeNode);
		while (!s.isEmpty()) {
			TreeNode n = s.pop();
			switch(method) {
				case "printNodeStatus":
					printNodeStatus(n);
					break;
				case "checkFinished":
					checkFinished(n);
					break;
			}
			if (!n.getChildren().isEmpty()) {
				for (TreeNode succ : n.getChildren()) {
					s.push(succ);
				}
			}
		}
	}
	
	private void printNodeStatus(TreeNode n) {
		System.out.println(n.getData().getId() + " : " + getAxioms(n.getData()));
		if (!n.getChildren().isEmpty()) {
			for (TreeNode succ : n.getChildren()) {
				System.out.println(n.getData().getId() + "--" + 
						getRelations(n.getData(), succ.getData()) + 
						"--" + succ.getData().getId());
			}
		}
	}
	
	private void checkFinished(TreeNode n) {
		boolean tmpFinished = clashed ||
				(operations.get(n.getData()).isEmpty() || 
						blockedNodes.contains(n.getData()) || 
						Ln.get(n.getData()).contains(OWNAxiom.BOTTOM));
		finished &= tmpFinished;		
	}
	
	///////////////////////////////
	/////   PRIVATE METHODS   /////
	///////////////////////////////
	// Fills operations with the possible operations applicable to node 
	private void updateOperations(TreeNode tn) {
		Node n = tn.getData();
		// If n hasn't clashed and it's not blocked
		if (!Ln.get(n).contains(OWNAxiom.BOTTOM) && !blockedNodes.contains(n)) {
			// Add all applicable TOP rules
			for (OWNAxiom axiom : K) {
				if (!Ln.get(n).contains(axiom))
					operations.get(n).add(new Operation(OPERATOR.TOP, axiom));
			}

			// Iterate over all axioms in node
			for (OWNAxiom axiom : Ln.get(n)) {
				OWNAxiomOperationVisitor visitor = 
						new OWNAxiomOperationVisitor(tn, Ln, Lr, conflictingOperations);
				axiom.accept(visitor);
				operations.get(n).addAll(visitor.getOperations());
			}
		}
	}
	
	// TODO updateAllOperations (iteratePreorder)
	// Tree traversal to get all operations in all nodes
	
}
