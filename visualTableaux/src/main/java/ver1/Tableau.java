package ver1;

import java.util.HashMap;
import java.util.HashSet;

import ownapi.*;
import ownapi.OWNAxiom.AXIOM_TYPE;
import ver1.Operation.OPERATOR;

public class Tableau {
	private HashSet<OWNAxiom> K;
	private Node firstNode;
	private HashMap<Node, HashSet<OWNAxiom>> Ln;
	private HashMap<Pair<Node, Node>, HashSet<OWNAxiom>> Lr;
	private HashMap<Node, HashSet<Operation>> operations;
	private HashMap<Node, HashSet<Operation>> executedOperations;
	private HashSet<NonDeterministicOperation> conflictingOperations;
	private HashSet<Node> blockedNodes;
	private HashMap<Node, Node> predecessor;
	private HashMap<Node, HashSet<Node>> successors;
	
	public Tableau(HashSet<OWNAxiom> K) {
		this.K = K;
		this.firstNode = new Node("x");
		Ln = new HashMap<Node, HashSet<OWNAxiom>>();
		Lr = new HashMap<Pair<Node, Node>, HashSet<OWNAxiom>>();
		operations = new HashMap<Node, HashSet<Operation>>();
		executedOperations = new HashMap<Node, HashSet<Operation>>();
		conflictingOperations = new HashSet<NonDeterministicOperation>();
		blockedNodes = new HashSet<Node>();
		predecessor = new HashMap<Node, Node>();
		successors = new HashMap<Node, HashSet<Node>>();
	}
	
	public void init(OWNAxiom concept) {
		//throw new UnsupportedOperationException();
		// Add first node with initial concept to L
		Ln.put(firstNode, new HashSet<OWNAxiom>());
		Ln.get(firstNode).add(concept);
		// get operations for firstNode
		operations.put(firstNode, new HashSet<Operation>());
		getPossibleOperations(firstNode);
		executedOperations.put(firstNode, new HashSet<Operation>());
	}
	
	public String[] printOperations() {
		String[] strings = new String[operations.get(firstNode).size()];
		int i = 0;
		for (Operation op : operations.get(firstNode))
			strings[i++] = op.toString();
		return strings;
	}
	
	// Fills operations with the possible operations applicable to node 
	private void getPossibleOperations(Node n) {
		// Add all applicable TOP rules
		for (OWNAxiom axiom : K) {
			if (!Ln.get(n).contains(axiom))
				operations.get(n).add(new Operation(OPERATOR.TOP, axiom));
		}
		
		// Iterate over all axioms in node
		for (OWNAxiom axiom : Ln.get(n)) {
			OWNAxiomOperationVisitor visitor = new OWNAxiomOperationVisitor(Ln.get(n));
			axiom.accept(visitor);
			operations.get(n).addAll(visitor.getOperations());
		}
	}
}
