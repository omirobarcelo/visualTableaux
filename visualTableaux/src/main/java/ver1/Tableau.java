package ver1;

import java.util.HashMap;
import java.util.HashSet;

import ownapi.OWNAxiom;

public class Tableau {
	private HashSet<OWNAxiom> K;
	private Node firstNode;
	private HashMap<Node, HashSet<OWNAxiom>> Ln;
	private HashMap<Pair<Node, Node>, HashSet<OWNAxiom>> Lr;
	private HashMap<Node, HashSet<Operation>> operations;
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
		conflictingOperations = new HashSet<NonDeterministicOperation>();
		blockedNodes = new HashSet<Node>();
		predecessor = new HashMap<Node, Node>();
		successors = new HashMap<Node, HashSet<Node>>();
	}
	
	public void init(OWNAxiom concept) {
		//throw new UnsupportedOperationException();
		// Add first node with initial concept to L
		Ln.put(this.firstNode, new HashSet<OWNAxiom>());
		Ln.get(this.firstNode).add(concept);
		// get operations for firstNode
	}
}
