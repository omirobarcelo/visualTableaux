package ver1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import ownapi.*;
import ver1.Operation.OPERATOR;
import ver1.util.*;

public class Tableau {
	private HashSet<OWNAxiom> K;
	private byte nodeCode;
	private TreeNode firstNode;
	private HashMap<Node, HashSet<OWNAxiom>> Ln;
	private HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> Lr;
	private HashMap<Node, HashSet<Operation>> operations;
	private HashSet<NonDeterministicOperation> conflictingOperations;
	private HashSet<Node> blockedNodes;
	private HashMap<Node, Node> predecessor;
	private boolean clashed, finished;
	private Backtracker backtracker;
	
	public Tableau(HashSet<OWNAxiom> K) {
		this.K = K;
		nodeCode = 109; // ASCII m
		this.firstNode = new TreeNode(new Node(Character.toString((char)nodeCode++)));
		Ln = new HashMap<Node, HashSet<OWNAxiom>>();
		Lr = new HashMap<Pair<Node, Node>, HashSet<OWNLiteral>>();
		operations = new HashMap<Node, HashSet<Operation>>();
		conflictingOperations = new HashSet<NonDeterministicOperation>();
		blockedNodes = new HashSet<Node>();
		predecessor = new HashMap<Node, Node>();
		clashed = false;
		finished = false;
		backtracker = new Backtracker();
	}
	
	public void init(OWNAxiom concept) {
		// Add first node with initial concept to L
		Ln.put(firstNode.getData(), new HashSet<OWNAxiom>());
		Ln.get(firstNode.getData()).add(concept);
		// Add null predecessor
		predecessor.put(firstNode.getData(), null);
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
		Node updatedNode = null;
		switch (op.getOperator()) {
			case TOP: {
				Ln.get(n).add(op.getOperand1());
				updatedNode = n;
				break;
			}
			case BOTTOM: {
				Ln.get(n).add(OWNAxiom.BOTTOM);
				updatedNode = n;
				clashed = true;
				break;
			}
			case AND: {
				OWNIntersection intersection = (OWNIntersection)op.getOperand1();
				Ln.get(n).add(intersection.getOperand1());
				Ln.get(n).add(intersection.getOperand2());
				updatedNode = n;
				break;
			}
			case OR: {
				// TODO
				backtracker.takeSnapshot(new NonDeterministicOperation(n, op), this);
				Ln.get(n).add(op.getResult());
				updatedNode = n;
				break;
			}
			case SOME: {
				OWNExistential existential = (OWNExistential)op.getOperand1();
				TreeNode tn = TreeNode.getTreeNode(firstNode, n);
				// Creation of new node
				Node newNode = new Node(Character.toString((char)nodeCode++));
				tn.addChild(newNode);
				predecessor.put(newNode, n);
				Pair<Node, Node> p = new Pair<Node, Node>(n, newNode);
				Lr.put(p, new HashSet<OWNLiteral>());
				Ln.put(newNode, new HashSet<OWNAxiom>());
				operations.put(newNode, new HashSet<Operation>());
				// Apply operation
				Lr.get(p).add(existential.getRelation());
				Ln.get(newNode).add(existential.getOperand());
				updatedNode = newNode;
				break;
			}
			case ONLY: {
				OWNUniversal universal = (OWNUniversal)op.getOperand1();
				Ln.get(op.getNode()).add(universal.getOperand());
				updatedNode = op.getNode();
				break;
			}
		}
		
		checkBlocking(updatedNode);
		
		updateAllOperations();
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
	
	
	/////////////////////////////////
	/////   PROTECTED METHODS   /////
	/////////////////////////////////
	protected byte getNodeCode() {
		return nodeCode;
	}
	
	protected HashMap<Node, HashSet<OWNAxiom>> getLn() {
		return Ln;
	}
	
	protected HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> getLr() {
		return Lr;
	}
	
	protected HashSet<Node> getBlockedNodes() {
		return blockedNodes;
	}
	
	protected HashMap<Node, Node> getPredecessor() {
		return predecessor;
	}
	
	protected boolean getClashed() {
		return clashed;
	}
	
	protected boolean getFinished() {
		return finished;
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
				case "updateOperations":
					updateOperations(n);
					break;
				case "markAsBlocked":
					markAsBlocked(n);
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
		System.out.println(n.getData().getId() + 
				(blockedNodes.contains(n.getData()) ? "[blocked]" : "") +
				" : " + getAxioms(n.getData()));
		if (!n.getChildren().isEmpty()) {
			for (TreeNode succ : n.getChildren()) {
				System.out.println(n.getData().getId() + "--" + 
						getRelations(n.getData(), succ.getData()) + 
						"--" + succ.getData().getId());
			}
		}
	}
	
	private void markAsBlocked(TreeNode n) {
		blockedNodes.add(n.getData());
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
		// Reset operations set
		operations.get(n).clear();
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
	
	// Tree traversal to get all operations in all nodes
	private void updateAllOperations() {
		iterativePreorder(firstNode, "updateOperations");
	}
	
	private void checkBlocking(Node updatedNode) {
		if (updatedNode == null)
			return;
		Node parent = predecessor.get(updatedNode);
		if (parent != null) {
			// If L(updatedNode) is a subset of L(parent)
			if (Ln.get(parent).containsAll(Ln.get(updatedNode))) {
				// Mark updated node and all its subtree as blocked
				TreeNode tn = TreeNode.getTreeNode(firstNode, updatedNode);
				iterativePreorder(tn, "markAsBlocked");
			}
		}
	}
}
