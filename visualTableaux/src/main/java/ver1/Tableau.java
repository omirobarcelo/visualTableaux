package ver1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import ownapi.*;
import ver1.Operation.OPERATOR;

public class Tableau {
	// TODO check if necessary to change Node to TreeNode
	private HashSet<OWNAxiom> K;
	private byte nodeCode;
	private TreeNode firstNode;
	private HashMap<Node, HashSet<OWNAxiom>> Ln;
	private HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> Lr;
	private HashMap<Node, HashSet<Operation>> operations;
	private HashSet<NonDeterministicOperation> conflictingOperations;
	private HashSet<Node> blockedNodes;
	
	public Tableau(HashSet<OWNAxiom> K) {
		this.K = K;
		nodeCode = 109; // ASCII m
		this.firstNode = new TreeNode(new Node(Character.toString((char)nodeCode++)));
		Ln = new HashMap<Node, HashSet<OWNAxiom>>();
		Lr = new HashMap<Pair<Node, Node>, HashSet<OWNLiteral>>();
		operations = new HashMap<Node, HashSet<Operation>>();
		conflictingOperations = new HashSet<NonDeterministicOperation>();
		blockedNodes = new HashSet<Node>();
	}
	
	public void init(OWNAxiom concept) {
		// Add first node with initial concept to L
		Ln.put(firstNode.getData(), new HashSet<OWNAxiom>());
		Ln.get(firstNode.getData()).add(concept);
		// TODO check if changing updateOperations to iteratePreorder
		// Get operations for firstNode
		operations.put(firstNode.getData(), new HashSet<Operation>());
		updateOperations(firstNode.getData());
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
	
	///////////////////////////////
	/////   PRIVATE METHODS   /////
	///////////////////////////////
	// TODO check if changing it to iteratePreorder
	// Fills operations with the possible operations applicable to node 
	private void updateOperations(Node n) {
		// If n hasn't clashed and it's not blocked
		if (!Ln.get(n).contains(OWNAxiom.BOTTOM) && !blockedNodes.contains(n)) {
			// Add all applicable TOP rules
			for (OWNAxiom axiom : K) {
				if (!Ln.get(n).contains(axiom))
					operations.get(n).add(new Operation(OPERATOR.TOP, axiom));
			}

			// Iterate over all axioms in node
			for (OWNAxiom axiom : Ln.get(n)) {
				OWNAxiomOperationVisitor visitor = new OWNAxiomOperationVisitor(n, Ln, Lr);
				axiom.accept(visitor);
				operations.get(n).addAll(visitor.getOperations());
			}
		}
	}
	
	// TODO updateAllOperations
	// Tree traversal to get all operations in all nodes
	
}
