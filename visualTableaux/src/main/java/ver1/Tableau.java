package ver1;

import java.util.HashMap;
import java.util.HashSet;

import ownapi.OWNAxiom;

public class Tableau {
	private HashSet<OWNAxiom> K;
	private Node firstNode;
	private HashMap<Node, HashSet<Operation>> operations;
	private HashMap<Node, HashSet<OWNAxiom>> Ln;
	private HashMap<Pair<Node, Node>, HashSet<OWNAxiom>> Lr;
	private HashSet<NonDeterministicOperation> conflictingOperations;
	private HashSet<Node> blockedNodes;
	private HashMap<Node, Node> predecessor;
	private HashMap<Node, HashSet<Node>> successors;
}
