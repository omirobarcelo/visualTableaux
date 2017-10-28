package ver1.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class GraphNode {
	
	private static final int WIDTH = 200;
	
	private String node;
	private GraphAxiom[] axioms;
	// Top-left corner
	private Point position;
	// Background color
	private Color paint;
	// Font color
	private Color color;
	private boolean bolded;
	
	public GraphNode() {
		// TODO set node, axioms, position from arg
		// set default paint, and color
		bolded = false;
	}
	
	public void toggleBolded() {
		bolded = !bolded;
	}
	
	public void paint(Graphics g) {
		
	}
}
