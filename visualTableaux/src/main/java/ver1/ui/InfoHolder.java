package ver1.ui;

import java.awt.Point;

import ver1.util.Pair;

public class InfoHolder {
	private Point parentBottomLeft;
	private Point topRight;
	private int leaves;
	private Pair<Integer, Integer> workspace;
	
	public InfoHolder() {}

	public Point getParentBottomLeft() {
		return parentBottomLeft;
	}

	public void setParentBottomLeft(Point parentBottomLeft) {
		this.parentBottomLeft = parentBottomLeft;
	}

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

	public void setWorkspace(int wsStart, int wsEnd) {
		this.workspace = new Pair<Integer, Integer>(wsStart, wsEnd);
	}
	
	
}
