package ver1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
		
		
		private Pair<Node, OWNAxiom> NDO;
		private Snapshot snapshot;
		private List<OWNAxiom> operandsApplied;
		private List<Tracker> trackers;
		
		/**
		 * The result of the NDO is added to the tracker list
		 * @param NDO
		 * @param snapshot
		 */
		public BTElement(NonDeterministicOperation NDO, Snapshot snapshot) {
			this.NDO = new Pair<Node, OWNAxiom>(NDO.getNode(), NDO.getOperand());
			this.snapshot = snapshot;
			this.operandsApplied = new ArrayList<OWNAxiom>();
			this.operandsApplied.add(NDO.getResult());
			this.trackers = new ArrayList<Tracker>();
			this.trackers.add(new Tracker(NDO.getNode(), NDO.getResult()));
		}
		
		/**
		 * Updates the BTElement with the new applied result, the current state,
		 * and resets the trackers
		 * @param result
		 * @param snapshot
		 */
		public void updateBTElement(OWNAxiom result, Snapshot snapshot) {
			this.snapshot = snapshot;
			this.operandsApplied.add(result);
			this.trackers.clear();
			this.trackers.add(new Tracker(NDO.getFirst(), result));
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
		
		public boolean containsAnyTracker(Node n, List<OWNAxiom> axioms) {
			for (OWNAxiom axiom : axioms)
				if (trackers.contains(new Tracker(n, axiom)))
					return true;
			return false;
		}
		
		public boolean backtracksNDO(Node n, OWNAxiom axiom) {
			return n.equals(NDO.getFirst()) && axiom.equals(NDO.getSecond());
		}
		
		public boolean allResultsApplied() {
			OWNAxiom axiom = NDO.getSecond();
			// Cast to appropriate type
			// In this case, only OWNUnion possible
			OWNUnion op = (OWNUnion)axiom;
			return operandsApplied.contains(op.getOperand1()) && operandsApplied.contains(op.getOperand2());
		}
	}
	
	
	private List<BTElement> backtracker;
	private int clashCause; //Position of the causing BTElement in backtracker

	public Backtracker() {
		this.backtracker = new ArrayList<BTElement>();
		clashCause = -1;
	}
	
	public void takeSnapshot(NonDeterministicOperation ndo, Tableau tableau) {
		Snapshot snapshot = new Snapshot(tableau);
		BTElement elem = null;
		if ((elem = NDOInBacktracker(ndo.getNode(), ndo.getOperand())) != null)
			elem.updateBTElement(ndo.getResult(), snapshot);
		else
			backtracker.add(new BTElement(ndo, snapshot));
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
			if (elem.containsTracker(n, axiom))
				return true;
		}
		return false;
	}
	
	/**
	 * Saves the index with the BTElement to which the tableau backtracks, 
	 * and updated the backtracker
	 * @param n
	 * @param lit
	 * @param compl
	 */
	public void selectCauseOfClash(Node n, OWNAxiom lit, OWNAxiom compl) {
		List<OWNAxiom> tmp = Arrays.asList(new OWNAxiom[]{lit, compl});
		for (int i = backtracker.size()-1; i >= 0; i--) {
			if (backtracker.get(i).containsAnyTracker(n, tmp)) {
				clashCause = i;
				// If the causing element has all the results applied, go back until
				// an element with still applicable results is found
				while (clashCause >= 0 && backtracker.get(clashCause).allResultsApplied())
					clashCause--;
				break;
			}
		}
		// Remove elements after cause of clash
		if (clashCause+1 < backtracker.size())
			backtracker.subList(clashCause+1, backtracker.size()).clear();
	}
		
	/**
	 * Returns clashCause which has been selected through selectCauseOfClash
	 * @return
	 */
	public Pair<NonDeterministicOperation, Snapshot> getCauseOfClash() {
		BTElement cause = backtracker.get(clashCause);
		NonDeterministicOperation ndo = 
				new NonDeterministicOperation(cause.NDO.getFirst(), 
						new Operation(Operation.OPERATOR.OR, cause.NDO.getSecond(), 
								cause.operandsApplied.get(cause.operandsApplied.size()-1)));
		Pair<NonDeterministicOperation, Snapshot> p = 
				new Pair<NonDeterministicOperation, Snapshot>(ndo, cause.snapshot);		
		return p;
	}
	
	public boolean thereAreSnapshots() {
		return !backtracker.isEmpty();
	}
	
	public Backtracker copy() {
		Backtracker copy = new Backtracker();
		copy.backtracker = DeepClone.deepClone(this.backtracker);
		copy.clashCause = this.clashCause;
		return copy;
	}
	
	public Set<NonDeterministicOperation> getAppliedNDOs() {
		HashSet<NonDeterministicOperation> set = new HashSet<NonDeterministicOperation>();
		for (BTElement elem : backtracker) {
			for (OWNAxiom result : elem.operandsApplied) {
				set.add(new NonDeterministicOperation(elem.NDO.getFirst(), 
						new Operation(Operation.OPERATOR.OR, elem.NDO.getSecond(), result)));
			}
		}
		return set;
	}
	
	/**
	 * Returns the BTElement if the NDO is in the backtracker. 
	 * Else returns null (as in false)
	 * @param n
	 * @param axiom
	 * @return
	 */
	private BTElement NDOInBacktracker(Node n, OWNAxiom axiom) {
		for (BTElement elem : backtracker) {
			if (elem.backtracksNDO(n, axiom))
				return elem;
		}
		return null;
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
