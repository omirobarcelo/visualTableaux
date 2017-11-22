package ver1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import javax.swing.JOptionPane;

import ownapi.*;
import ver1.Backtracker.Snapshot;
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
	private HashSet<Pair<Node, OWNAxiom>> executedNDO;
	private HashSet<Node> blockedNodes;
	private HashMap<Node, Node> predecessor;
	private boolean clashed, clashConsequenceNDO, finished;
	private Backtracker backtracker;
	private boolean modeGUI;
	
	// Auxiliary string with the status
	private String currentStatus = "";
	public void clearStatus() {
		currentStatus = "";
	}
	public String stringStatus() {
		return currentStatus;
	}
	
	public Tableau(HashSet<OWNAxiom> K, boolean modeGUI) {
		this.K = K;
		nodeCode = 109; // ASCII m
		this.firstNode = new TreeNode(new Node(Character.toString((char)nodeCode++)));
		Ln = new HashMap<Node, HashSet<OWNAxiom>>();
		Lr = new HashMap<Pair<Node, Node>, HashSet<OWNLiteral>>();
		operations = new HashMap<Node, HashSet<Operation>>();
		conflictingOperations = new HashSet<NonDeterministicOperation>();
		executedNDO = new HashSet<Pair<Node, OWNAxiom>>();
		blockedNodes = new HashSet<Node>();
		predecessor = new HashMap<Node, Node>();
		clashed = false;
		clashConsequenceNDO = false;
		finished = false;
		backtracker = new Backtracker();
		this.modeGUI = modeGUI;
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
	public HashSet<OWNAxiom> getOntology() {
		return K;
	}
	
	public TreeNode getFirstNode() {
		return firstNode;
	}
	
	/**
	 * Checks which would be the ID of the next created node
	 * @return
	 */
	public String checkNextCreatedNode() {
		return Character.toString((char)nodeCode);
	}
	
	public HashMap<Node, HashSet<Operation>> getOperations() {
		return operations;
	}
	
	public HashSet<OWNAxiom> getAxioms(Node n) {
		return Ln.get(n);
	}
	
	public HashSet<OWNLiteral> getRelations(Node pred, Node succ) {
		return Lr.get(new Pair<Node, Node>(pred, succ));
	}
	
	
	//////////////////////////////
	/////   PUBLIC METHODS   /////
	//////////////////////////////
	public void apply(Node n, Operation op) {
		Node updatedNode = null;
		// Assumed that operator will be one of the cases, so an operation
		// will always be applied and updatedNode won't remain null
		switch (op.getOperator()) {
			case TOP: {
				Ln.get(n).add(op.getOperand1());
				updatedNode = n;
				break;
			}
			case BOTTOM: {
				Ln.get(n).add(OWNAxiom.BOTTOM);
				// If there aren't snapshots to backtrack to, and BOTTOM operation
				// is applied, then the tableau expansion clashed
				// If there are snapshots, check if the clash is a consequence from
				// the last non deterministic operation applied
				if (backtracker.thereAreSnapshots()) {
					// Check if last NDO's operand contains literal
					OWNAxiomContainsVisitor visitor1 = new OWNAxiomContainsVisitor(op.getOperand1());
					backtracker.checkLastNDOAxiom().accept(visitor1);
					// Check if last NDO's operand contains complement
					OWNAxiomContainsVisitor visitor2 = new OWNAxiomContainsVisitor(op.getOperand2());
					backtracker.checkLastNDOAxiom().accept(visitor2);
					// If the literal or the complement is contained, the clash is a consequence of 
					// the last NDO
					clashConsequenceNDO = visitor1.isContained() || visitor2.isContained();
				}
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
				// All operations have to be applied to fully expand the tableau
				// NDO can have more than one application (assumed 2 in this implementation)
				// In executedNDO stored the NDO applied. If an NDO was already applied once, 
				// then a snapshot is not taken since last application resulted in a clash,
				// and operation has to be applied at least once
				Pair<Node, OWNAxiom> p = new Pair<Node, OWNAxiom>(n, op.getOperand1());
				if (!executedNDO.contains(p)) {
					executedNDO.add(p);
					backtracker.takeSnapshot(new NonDeterministicOperation(n, op), this);
				}
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
				Ln.put(newNode, new HashSet<OWNAxiom>());
				Pair<Node, Node> p = new Pair<Node, Node>(n, newNode);
				Lr.put(p, new HashSet<OWNLiteral>());
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
		
		iterativePreorder(TreeNode.getTreeNode(firstNode, updatedNode), "checkBlocking");
		//checkBlocking(updatedNode);
		
		updateAllOperations();
	}
	
	public boolean isBlocked(Node n) {
		return blockedNodes.contains(n);
	}
	
	public boolean isFinished() {
		if (clashed && clashConsequenceNDO) {
			if (backtracker.thereAreSnapshots()) {
				// TODO if GUI mode, inform of backtracking
				if (modeGUI) {
					JOptionPane.showMessageDialog(null, 
							"There has been a clash, consequence of the last " + 
							"non deterministic operation.\n" + 
							"Recovering last state.");
				}
				recoverFromLastSnapshot();
				updateAllOperations();
			}
		}
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
	/**
	 * Use string to indicate the visiting method to avoid all
	 * the overhead required to pass a function as argument
	 * @param treeNode
	 * @param method
	 */
	public void iterativePreorder(TreeNode treeNode, String method)  {
		Stack<TreeNode> s = new Stack<TreeNode>();
		s.push(treeNode);
		while (!s.isEmpty()) {
			TreeNode n = s.pop();
			switch(method) {
				case "printNodeStatus":
					printNodeStatus(n);
					break;
				case "stringNodeStatus":
					stringNodeStatus(n);
					break;
				case "updateOperations":
					updateOperations(n);
					break;
				case "markAsBlocked":
					markAsBlocked(n);
					break;
				case "checkBlocking":
					checkBlocking(n.getData());
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
				" : " + getAxioms(n.getData()).toString());
		if (!n.getChildren().isEmpty()) {
			for (TreeNode succ : n.getChildren()) {
				System.out.println(n.getData().getId() + "--" + 
						getRelations(n.getData(), succ.getData()) + 
						"--" + succ.getData().getId());
			}
		}
	}
	
	private void stringNodeStatus(TreeNode n) {
		currentStatus += n.getData().getId() + 
				(blockedNodes.contains(n.getData()) ? "[blocked]" : "") +
				" : " + getAxioms(n.getData()) + "\n";
		if (!n.getChildren().isEmpty()) {
			for (TreeNode succ : n.getChildren()) {
				currentStatus += n.getData().getId() + "--" + 
						getRelations(n.getData(), succ.getData()) + 
						"--" + succ.getData().getId() + "\n";
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
	
	public int numLeaves(TreeNode treeNode) {
		int leaves = 0;
		Stack<TreeNode> s = new Stack<TreeNode>();
		s.push(treeNode);
		while (!s.isEmpty()) {
			TreeNode n = s.pop();
			if (!n.getChildren().isEmpty()) {
				for (TreeNode succ : n.getChildren()) {
					s.push(succ);
				}
			} else {
				leaves++;
			}
		}
		return leaves;
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
		//if (!Ln.get(n).contains(OWNAxiom.BOTTOM) && !blockedNodes.contains(n)) {
		// OR if n hasn't clashed (can keep operating even if it's blocked)
		if (!Ln.get(n).contains(OWNAxiom.BOTTOM)) {
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
	
	/**
	 * Check if updatedNode becomes blocked by its parent
	 * and mark all the updatedNode children as blocked if so
	 * @param updatedNode
	 */
	private void checkBlocking(Node updatedNode) {
		if (updatedNode == null)
			return;
		// TODO check all predecessors
		// TODO unblock if necessary
		Node pred = predecessor.get(updatedNode);
		boolean blocked = false;
		// Check until there are no more predecessors or the updated node is blocked
		while (pred != null && !blocked) {
			// If L(updatedNode) is a subset of L(parent) or the predecessor is blocked
			if (Ln.get(pred).containsAll(Ln.get(updatedNode)) || blockedNodes.contains(pred)) {
				// If GUI mode, dialog informing of blocking if node wasn't blocked before
				if (modeGUI && !blockedNodes.contains(updatedNode)) {
					JOptionPane.showMessageDialog(null, "Node " + updatedNode.getId() + 
							" has become blocked.");
				}
				blockedNodes.add(updatedNode);
				blocked = true;
			} else {
				pred = predecessor.get(pred);
			}
		}
		// If the updated node was previously blocked and it's not blocked anymore
		if (!blocked && blockedNodes.contains(updatedNode)) {
			blockedNodes.remove(updatedNode);
			if (modeGUI) {
				JOptionPane.showMessageDialog(null, "Node " + updatedNode.getId() + 
						" has become unblocked.");
			}
		}
//		// Mark updated node as blocked and check all its successors
//		TreeNode tn = TreeNode.getTreeNode(firstNode, updatedNode);
//		iterativePreorder(tn, "checkBlocking");
//		
//		
//		
//		
//		Node parent = predecessor.get(updatedNode);
//		if (parent != null) {
//			// If L(updatedNode) is a subset of L(parent)
//			if (Ln.get(parent).containsAll(Ln.get(updatedNode))) {
//				// If GUI mode, dialog informing of blocking if node wasn't blocked before
//				if (modeGUI && !blockedNodes.contains(updatedNode)) {
//					JOptionPane.showMessageDialog(null, "Node " + updatedNode.getId() + 
//							" and all its children have become blocked.");
//				}
//				// Mark updated node and all its subtree as blocked
//				TreeNode tn = TreeNode.getTreeNode(firstNode, updatedNode);
//				iterativePreorder(tn, "markAsBlocked");
//			} else {
//				// If the updated node was previously blocked
//				if (blockedNodes.contains(updatedNode)) {
//					blockedNodes.remove(updatedNode);
//					if (modeGUI) {
//						JOptionPane.showMessageDialog(null, "Node " + updatedNode.getId() + 
//								" has become unblocked.");
//					}
//				}
//			}
//		}
	}
	
	private void recoverFromLastSnapshot() {
		Pair<NonDeterministicOperation, Snapshot> p = backtracker.getLastSnapshot();
		conflictingOperations.add(p.getFirst());
		// Recover
		nodeCode = p.getSecond().getNodeCode();
		firstNode = p.getSecond().getFirstNode();
		Ln = p.getSecond().getLn();
		Lr = p.getSecond().getLr();
		operations = p.getSecond().getOperations();
		blockedNodes = p.getSecond().getBlockedNodes();
		predecessor = p.getSecond().getPredecessor();
		clashed = p.getSecond().getClashed();
		finished = p.getSecond().getFinished();
	}
}
