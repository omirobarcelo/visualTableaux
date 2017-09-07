package ver1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import ownapi.OWNAxiom;

public class Backtracker {
	class Snapshot {
		private HashMap<Node, HashSet<OWNAxiom>> Ln;
		private HashMap<Pair<Node, Node>, HashSet<OWNAxiom>> Lr;
		// TODO check if operations is necessary for Snapshot (maybe they can be recalculated after getting a Snapshot)
		private HashMap<Node, HashSet<Operation>> operations;
		// TODO probably adding firstNode and maybe blockedNodes and clashed to Snapshot
		
		// TODO probably builder could do the clones/copies of the elements
		public Snapshot(HashMap<Node, HashSet<OWNAxiom>> ln, HashMap<Pair<Node, Node>, HashSet<OWNAxiom>> lr,
				HashMap<Node, HashSet<Operation>> operations) {
			Ln = ln;
			Lr = lr;
			this.operations = operations;
		}
		
		public HashMap<Node, HashSet<OWNAxiom>> getLn() {
			return Ln;
		}
		public HashMap<Pair<Node, Node>, HashSet<OWNAxiom>> getLr() {
			return Lr;
		}
		public HashMap<Node, HashSet<Operation>> getOperations() {
			return operations;
		}
	}
	
	// TODO change to Stack
	private ArrayList<Pair<NonDeterministicOperation, Snapshot>> timeline;

	// TODO probably not necessary to pass timeline to builder; created in builder
	public Backtracker(ArrayList<Pair<NonDeterministicOperation, Snapshot>> timeline) {
		this.timeline = timeline;
	}
	
	//take last snapshot
	
	// TODO tableau.clashed = false when getting last snapshot
}
