package ver1;

import java.util.HashMap;
import java.util.HashSet;

import ownapi.OWNAxiom;
import ownapi.OWNLiteral;
import ver1.util.DeepClone;
import ver1.util.Pair;

/**
 * Represents a tableau snapshot
 * Stores the attributes necessary to recover a previous state
 */
public class Snapshot {
	private byte nodeCode;
	private TreeNode firstNode;
	private HashMap<Node, HashSet<OWNAxiom>> Ln;
	private HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> Lr;
	private HashMap<Node, HashSet<Operation>> operations;
	private HashSet<Node> blockedNodes;
	private HashMap<Node, Node> predecessor;
	private Backtracker backtracker;
	private boolean clashed, finished;
	
	/**
	 * NOT UPDATED
	 * Since we want to save the current state, and not be modified externally
	 * because of pointers, copy and deep clone all the elements upon creation
	 * @param nodeCode
	 * @param firstNode
	 * @param ln
	 * @param lr
	 * @param operations
	 * @param blockedNodes
	 * @param predecessor
	 * @param clashed
	 * @param finished
	 */
	public Snapshot(byte nodeCode, TreeNode firstNode, HashMap<Node, HashSet<OWNAxiom>> ln,
			HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> lr, HashMap<Node, HashSet<Operation>> operations,
			HashSet<Node> blockedNodes, HashMap<Node, Node> predecessor, boolean clashed, boolean finished) {
		this.nodeCode = nodeCode;
		this.firstNode = firstNode.copy();
		Ln = DeepClone.deepClone(ln);
		Lr = DeepClone.deepClone(lr);
		this.operations = DeepClone.deepClone(operations);
		// blockedNodes and predecessor do not require a deep clone
		// since we are only interested in the elements stored in them
		// and the elements won't be modified
		this.blockedNodes = new HashSet<Node>(blockedNodes);
		this.predecessor = new HashMap<Node, Node>(predecessor);
		this.clashed = clashed;
		this.finished = finished;
	}
	
	public Snapshot(Tableau tableau) {
		this.nodeCode = tableau.getNodeCode();
		this.firstNode = tableau.getFirstNode().copy();
		this.Ln = DeepClone.deepClone(tableau.getLn());
		this.Lr = DeepClone.deepClone(tableau.getLr());
		this.operations = DeepClone.deepClone(tableau.getOperations());
		// blockedNodes and predecessor do not require a deep clone
		// since we are only interested in the elements stored in them
		// and the elements won't be modified
		this.blockedNodes = new HashSet<Node>(tableau.getBlockedNodes());
		this.predecessor = new HashMap<Node, Node>(tableau.getPredecessor());
		this.backtracker = tableau.getBacktracker().copy();
		this.clashed = tableau.getClashed();
		this.finished = tableau.getFinished();
	}

	public byte getNodeCode() {
		return nodeCode;
	}

	public TreeNode getFirstNode() {
		return firstNode;
	}

	public HashMap<Node, HashSet<OWNAxiom>> getLn() {
		return Ln;
	}

	public HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> getLr() {
		return Lr;
	}

	public HashMap<Node, HashSet<Operation>> getOperations() {
		return operations;
	}

	public HashSet<Node> getBlockedNodes() {
		return blockedNodes;
	}

	public HashMap<Node, Node> getPredecessor() {
		return predecessor;
	}
	
	public Backtracker getBacktracker() {
		return backtracker;
	}

	public boolean getClashed() {
		return clashed;
	}

	public boolean getFinished() {
		return finished;
	}

}

