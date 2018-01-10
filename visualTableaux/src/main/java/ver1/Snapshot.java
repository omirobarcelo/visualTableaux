/*
 * Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */

package ver1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import ownapi.OWNAxiom;
import ownapi.OWNAtom;
import ver1.util.DeepClone;
import ver1.util.Pair;

/**
 * Represents a tableau snapshot
 * Stores the attributes necessary to recover a previous state
 */
public class Snapshot {
	private byte nodeCode;
	private TreeNode firstNode;
	private Map<Node, LinkedHashSet<OWNAxiom>> Ln;
	private Map<Pair<Node, Node>, HashSet<OWNAtom>> Lr;
	private Map<Node, HashSet<Operation>> operations;
	private Set<Node> blockedNodes;
	private Map<Node, Node> predecessor;
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
	public Snapshot(byte nodeCode, TreeNode firstNode, Map<Node, LinkedHashSet<OWNAxiom>> ln,
			Map<Pair<Node, Node>, HashSet<OWNAtom>> lr, Map<Node, HashSet<Operation>> operations,
			Set<Node> blockedNodes, Map<Node, Node> predecessor, boolean clashed, boolean finished) {
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

	public Map<Node, LinkedHashSet<OWNAxiom>> getLn() {
		return Ln;
	}

	public Map<Pair<Node, Node>, HashSet<OWNAtom>> getLr() {
		return Lr;
	}

	public Map<Node, HashSet<Operation>> getOperations() {
		return operations;
	}

	public Set<Node> getBlockedNodes() {
		return blockedNodes;
	}

	public Map<Node, Node> getPredecessor() {
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

