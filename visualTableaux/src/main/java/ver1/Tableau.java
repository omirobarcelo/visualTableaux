package ver1;

import java.util.HashMap;
import java.util.HashSet;

import ownapi.*;
import ownapi.OWNAxiom.AXIOM_TYPE;
import ver1.Operation.OPERATOR;

public class Tableau {
	private HashSet<OWNAxiom> K;
	private byte nodeCode;
//	private Node firstNode;
	private TreeNode firstNode;
	private HashMap<Node, HashSet<OWNAxiom>> Ln;
	private HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> Lr;
	private HashMap<Node, HashSet<Operation>> operations;
	private HashMap<Node, HashSet<Operation>> executedOperations;
	private HashSet<NonDeterministicOperation> conflictingOperations;
	private HashSet<Node> blockedNodes;
//	private HashMap<Node, Node> predecessor;
//	private HashMap<Node, HashSet<Node>> successors;
	
	public Tableau(HashSet<OWNAxiom> K) {
		this.K = K;
		nodeCode = 109; // ASCII m
		this.firstNode = new TreeNode(new Node(Character.toString((char)nodeCode++)));
		Ln = new HashMap<Node, HashSet<OWNAxiom>>();
		Lr = new HashMap<Pair<Node, Node>, HashSet<OWNLiteral>>();
		operations = new HashMap<Node, HashSet<Operation>>();
		executedOperations = new HashMap<Node, HashSet<Operation>>();
		conflictingOperations = new HashSet<NonDeterministicOperation>();
		blockedNodes = new HashSet<Node>();
//		predecessor = new HashMap<Node, Node>();
//		successors = new HashMap<Node, HashSet<Node>>();
	}
	
	public void init(OWNAxiom concept) {
		//throw new UnsupportedOperationException();
		// Add first node with initial concept to L
		Ln.put(firstNode.getData(), new HashSet<OWNAxiom>());
		Ln.get(firstNode.getData()).add(concept);
		// Get operations for firstNode
		operations.put(firstNode.getData(), new HashSet<Operation>());
		getPossibleOperations(firstNode.getData());
		executedOperations.put(firstNode.getData(), new HashSet<Operation>());
	}
	
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
		// Return the difference between available operations and executed operations
		HashMap<Node, HashSet<Operation>> ops = new HashMap<Node, HashSet<Operation>>();
		for (Node n : operations.keySet()) {
			HashSet<Operation> tmpOperations = (HashSet<Operation>)operations.get(n).clone();
			tmpOperations.removeAll(executedOperations.get(n));
			ops.put(n, tmpOperations);
		}
		return ops;
	}
	
//	public HashSet<Node> getSuccessors(Node n) {
//		return successors.get(n);
//	}
	
	public String getAxioms(Node n) {
		return Ln.get(n).toString();
	}
	
	public String getRelations(Node pred, Node succ) {
//		Pair<Node, Node> edge = new Pair<Node, Node>(pred, succ);
//		String[] relations = new String[Lr.get(edge).size()];
//		int i = 0;
//		for (OWNLiteral rel : Lr.get(edge)) {
//			relations[i++] = rel.toString();
//		}
//		return relations;
		return Lr.get(new Pair<Node, Node>(pred, succ)).toString();
	}
	
	// DEBUG
	public String[] printOperations() {
		String[] strings = new String[operations.get(firstNode.getData()).size()];
		int i = 0;
		for (Operation op : operations.get(firstNode.getData()))
			strings[i++] = op.toString();
		return strings;
	}
	// DEBUG
	
	// Fills operations with the possible operations applicable to node 
	private void getPossibleOperations(Node n) {
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
	
	// TODO getAllPossibleOperations
	// Tree traversal to get all operations in all nodes
}
