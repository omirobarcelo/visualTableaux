package ver1;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import ownapi.*;
import ver1.util.*;

public class Backtracker {
	
	/**
	 * Backtracking element
	 * @author Oriol Miro-Barcelo
	 *
	 */
	class BTElement {
		/**
		 * Tracking element. Contains a node and an OWNAxiom
		 * @author Oriol Miro-Barcelo
		 *
		 */
		class Tracker {
			private Pair<Node, OWNAxiom> tracker;
			
			public Tracker(Node n, OWNAxiom axiom) {
				tracker = new Pair<Node, OWNAxiom>(n, axiom);
			}
			
			public boolean isEqual(Tracker t) {
				return this.tracker.equals(t.tracker);
			}
			
			@Override
			public int hashCode() {
				return tracker.hashCode();
			}
			
			@Override
			public boolean equals(Object other) {
				if (other instanceof Tracker) {
					Tracker t = (Tracker)other;
					return this.tracker.equals(t.tracker);
				}
				return false;
			}
		}
		
		
		private NonDeterministicOperation NDO;
		private Snapshot snapshot;
		private List<Tracker> trackers;
		
		/**
		 * The result of the NDO is added to the tracker list
		 * @param NDO
		 * @param snapshot
		 */
		public BTElement(NonDeterministicOperation NDO, Snapshot snapshot) {
			this.NDO = NDO;
			this.snapshot = snapshot;
			this.trackers = new ArrayList<Tracker>();
			this.trackers.add(new Tracker(NDO.getNode(), NDO.getResult()));
		}
		
		/**
		 * Check if the tracker with (n, axiom) is contained and updates it to (updatedNode, results)
		 * @param n
		 * @param axiom
		 * @param updatedNode
		 * @param results
		 */
		public void updateTrackers(Node n, OWNAxiom axiom, Node updatedNode, OWNAxiom... results) {
			Tracker t = new Tracker(n, axiom);
//			for (Tracker tr : trackers) {
//				boolean equal = tr.isEqual(t);
//				System.out.println(equal);
//			}
			if (trackers.contains(t)) {
				trackers.remove(t);
				for (OWNAxiom ax : results) {
					trackers.add(new Tracker(updatedNode, ax));
				}
			}
		}
		
		public boolean containsTracker(Node n, OWNAxiom axiom) {
			return trackers.contains(new Tracker(n, axiom));
		}
	}
	
	
	private List<BTElement> backtracker;
	private List<Pair<Node, OWNAxiom>> executedNDO; //Keeps track of the executed NDO, because they have to be executed at least once 
	// to apply all the operations
	private int clashCause; //Position of the causing BTElement in backtracker

	public Backtracker() {
		this.backtracker = new ArrayList<BTElement>();
		this.executedNDO = new ArrayList<Pair<Node, OWNAxiom>>();
		clashCause = -1;
	}
	
	public void takeSnapshot(NonDeterministicOperation ndo, Tableau tableau) {
		Snapshot snapshot = new Snapshot(tableau);
		backtracker.add(new BTElement(ndo, snapshot));
		executedNDO.add(new Pair<Node, OWNAxiom>(ndo.getNode(), ndo.getOperand()));
	}
	
	public void updateTrackers(Node n, OWNAxiom axiom, Node updatedNode, OWNAxiom... results) {
		// TODO
		for (BTElement elem : backtracker) {
			elem.updateTrackers(n, axiom, updatedNode, results);
		}
	}
	
	public boolean isAxiomTracked(Node n, OWNAxiom axiom) {
		// TODO
		for (BTElement elem : backtracker) {
			return elem.containsTracker(n, axiom);
		}
		return false;
	}
	
	public boolean hasNDOBeenExecuted(Node n, OWNAxiom operand) {
		return executedNDO.contains(new Pair<Node, OWNAxiom>(n, operand));
	}
	
	public void selectCauseOfClash(Node n, OWNAxiom axiom) {
		// TODO get clashCause
		for (int i = 0; i < backtracker.size(); i++) {
			if (backtracker.get(i).containsTracker(n, axiom)) {
				clashCause = i;
			}
		}
	}
	
	/**
	 * Returns clashCause which has been selected through selectCauseOfClash
	 * It also removes all the elements after the cause of clash, including the cause (includes backtracker and executedNDO)
	 * @return
	 */
	public Pair<NonDeterministicOperation, Snapshot> getCauseOfClash() {
		Pair<NonDeterministicOperation, Snapshot> p = 
				new Pair<NonDeterministicOperation, Snapshot>(backtracker.get(clashCause).NDO, backtracker.get(clashCause).snapshot);
		int numNDOToRemove = backtracker.size()-clashCause-1; //We have to leave the NDO that caused the clash
		backtracker.subList(clashCause, backtracker.size()).clear();
		executedNDO.subList(executedNDO.size()-numNDOToRemove, executedNDO.size()).clear(); 
		return p;
	}
	
	public boolean thereAreSnapshots() {
		return !backtracker.isEmpty();
	}
	
//	/**
//	 * Peek at which non deterministic operation created the last snapshot
//	 * @return
//	 */
//	public OWNAxiom checkLastNDOAxiom() {
//		Pair<NonDeterministicOperation, Snapshot> p = timeline.peek();
//		return p.getFirst().getOperand();
//	}
//	
//	public Pair<NonDeterministicOperation, Snapshot> getLastSnapshot() {
//		return timeline.pop();
//	}
//		
//	public boolean thereAreSnapshots() {
//		return !timeline.isEmpty();
//	}
	
}
