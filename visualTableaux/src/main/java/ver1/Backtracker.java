package ver1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import ownapi.OWNAxiom;

public class Backtracker {
	class Snapshot {
		private HashMap<Node, HashSet<OWNAxiom>> Ln;
		private HashMap<Pair<Node, Node>, HashSet<OWNAxiom>> Lr;
		private HashMap<Node, HashSet<Operation>> operations;
		// TODO probably adding firstNode and maybe clashed to Snapshot
		
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
	
	private ArrayList<Pair<NonDeterministicOperation, Snapshot>> timeline;

	public Backtracker(ArrayList<Pair<NonDeterministicOperation, Snapshot>> timeline) {
		this.timeline = timeline;
	}
	
	//take last snapshot
	
	// TODO tableau.clashed = false when getting last snapshot
}
