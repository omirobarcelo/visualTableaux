package ver1.ui;

import java.awt.Point;

import ver1.util.Pair;

/**
 * Stores information of GraphNode
 * @author Oriol Miro-Barcelo
 *
 */
public class InfoHolder {
	private Point topRight; // x, y coordinates of the top right corner
	private int leaves; // number of leaves
	private Pair<Integer, Integer> workspace; // horizontal workspace; workspace defines the space where the node and its leaves can be painted
	
	public InfoHolder() {}

	public Point getTopRight() {
		return topRight;
	}

	public void setTopRight(Point topRight) {
		this.topRight = topRight;
	}

	public int getLeaves() {
		return leaves;
	}

	public void setLeaves(int leaves) {
		this.leaves = leaves;
	}

	public Pair<Integer, Integer> getWorkspace() {
		return workspace;
	}

	/**
	 * 
	 * @param wsStart Pixel where workspace starts
	 * @param wsEnd Pixel where workspace ends
	 */
	public void setWorkspace(int wsStart, int wsEnd) {
		this.workspace = new Pair<Integer, Integer>(wsStart, wsEnd);
	}
	
	
}
