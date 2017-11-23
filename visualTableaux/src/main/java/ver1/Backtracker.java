package ver1;

import java.util.Stack;

import ownapi.*;
import ver1.util.*;

public class Backtracker {
	
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
		Snapshot snapshot = new Snapshot(tableau);
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
