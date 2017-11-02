package ver1.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class GraphAxiom {
	
	public String axiom;
	// TODO which position is this?
	public Point position;
	// Highlight color
	public Color color;
	public boolean highlighted;
	
	public GraphAxiom(String text) {
		axiom = text;
		color = Color.YELLOW;
		highlighted = false;
	}
	
	public String getText() {
		return axiom;
	}
	
	public boolean isHighligthed() {
		return highlighted;
	}
	
	public void setPosition(int x, int y) {
		position = new Point(x, y);
	}
	
	public void toggleHighlight() {
		highlighted = !highlighted;
	}
	
	public void paint(Graphics g) {
		
	}
}
