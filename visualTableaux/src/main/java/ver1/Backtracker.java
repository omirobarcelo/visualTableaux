package ver1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import ownapi.*;
import ver1.util.*;

public class Backtracker {
	/**
	 * Represents a tableau snapshot
	 * Stores the attributes necessary to recover a previous state
	 */
	class Snapshot {
		private byte nodeCode;
		private TreeNode firstNode;
		private HashMap<Node, HashSet<OWNAxiom>> Ln;
		private HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> Lr;
		private HashMap<Node, HashSet<Operation>> operations;
		private HashSet<Node> blockedNodes;
		private HashMap<Node, Node> predecessor;
		private boolean clashed, finished;
		
		/**
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

		public boolean getClashed() {
			return clashed;
		}

		public boolean getFinished() {
			return finished;
		}

	}
	
	/**
	 * Every time a recovery is necessary, last snapshot will be obtained
	 * So stack data structure used
	 * Every snapshot is characterized by the non deterministic operations
	 * that created it
	 */
	private Stack<Pair<NonDeterministicOperation, Snapshot>> timeline;

	public Backtracker() {
		this.timeline = new Stack<Pair<NonDeterministicOperation, Snapshot>>();
	}
	
	public void takeSnapshot(NonDeterministicOperation ndo, Tableau tableau) {
		Snapshot snapshot = new Snapshot(tableau.getNodeCode(), tableau.getFirstNode(), 
				tableau.getLn(), tableau.getLr(), tableau.getOperations(), tableau.getBlockedNodes(), 
				tableau.getPredecessor(), tableau.getClashed(), tableau.getFinished());
		timeline.push(new Pair<NonDeterministicOperation, Snapshot>(ndo, snapshot));
	}
	
	/**
	 * Peek at which non deterministic operation created the last snapshot
	 * @return
	 */
	public OWNAxiom checkLastNDOAxiom() {
		Pair<NonDeterministicOperation, Snapshot> p = timeline.peek();
		return p.getFirst().getOperand();
	}
	
	public Pair<NonDeterministicOperation, Snapshot> getLastSnapshot() {
		return timeline.pop();
	}
		
	public boolean thereAreSnapshots() {
		return !timeline.isEmpty();
	}
	
}
