package ver1;

/**
 * Manages the saving of user states
 * @author Oriol Miro-Barcelo
 *
 */
public class UserSaveState {
	public static final int NUM_STATES = 3;
	
	private Snapshot[] states;
	
	public UserSaveState() {
		states = new Snapshot[NUM_STATES];
	}
	
	/**
	 * If state bigger than NUM_STATES, save in last state
	 * @param tableau
	 * @param state
	 */
	public void saveState(Tableau tableau, int state) {
		Snapshot snapshot = new Snapshot(tableau);
		states[state >= NUM_STATES ? NUM_STATES-1 : state] = snapshot;
	}
	
	/**
	 * If state bigger than NUM_STATES, load last state
	 * @param state
	 * @return
	 */
	public Snapshot loadState(int state) {
		return states[state >= NUM_STATES ? NUM_STATES-1 : state];
	}
}
