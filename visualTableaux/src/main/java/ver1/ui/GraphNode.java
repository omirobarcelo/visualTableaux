package ver1.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class GraphNode {
	
	private static final int WIDTH = 200;
	
	private String node;
	private GraphAxiom[] axioms;
	// Top-left corner
	private Point position;
	// Background color
	private Color color;
	// Font and line color
	private Color paint;
	private Font nameFont, textFont;
	private boolean bolded;
		
	public GraphNode(String node, String[] axioms, int x, int y) {
		this.node = node;
		this.axioms = new GraphAxiom[axioms.length];
		int i = 0;
		for (String s : axioms) 
			this.axioms[i++] = new GraphAxiom(s);
		position = new Point(x, y);
		
		color = Color.WHITE;
		paint = Color.BLACK;
		nameFont = new Font(Font.MONOSPACED, Font.PLAIN, 16);
		textFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		
		bolded = false;
	}
	
	public void toggleBolded() {
		bolded = !bolded;
	}
	
	public void paint(Graphics g) {
		// DEBUG
		System.out.println(axioms.length);
		// DEBUG
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(color);	
		g2d.setPaint(paint);
		final int STD_MARGIN = 5;
		
		g2d.setStroke(new BasicStroke(bolded ? 5.0f : 3.0f));
		// Upper line
		g2d.drawLine(position.x, position.y, position.x+WIDTH, position.y);
		// Node name
		g2d.setFont(nameFont);
		FontMetrics fm = g2d.getFontMetrics();
		int fontHeight = fm.getHeight();
		int nodeWidth = fm.stringWidth(node);
		g2d.drawString(node, position.x+(WIDTH/2-nodeWidth/2), position.y+fontHeight); // WIDTH/2-nodeWidth/2 -> Centers the text
		// Middle line
		g2d.drawLine(position.x, position.y+fontHeight+STD_MARGIN, position.x+WIDTH, position.y+fontHeight+STD_MARGIN);
		// Axioms
		g2d.setFont(textFont);
		fm = g2d.getFontMetrics();
		fontHeight = fm.getHeight();
		int height = fontHeight + STD_MARGIN; // height of the box; each text line height is going to be added
		String text = "";
		int xCursor = position.x+STD_MARGIN, yCursor = position.y+height; // cursor of the text, to save each axiom position
		for (int i = 0; i < axioms.length; i++) {
			// If last line longer than the box, add a new line
			String lastLine = text.split("\n")[text.split("\n").length-1];
			if (fm.stringWidth(lastLine+axioms[i].getText()+", ") > WIDTH-STD_MARGIN*2) {
				text += "\n";
				yCursor += fontHeight; // update yCursor
				xCursor = position.x+STD_MARGIN; // reset xCursor
			}
			axioms[i].setPosition(xCursor, yCursor);
			if (axioms[i].isHighligthed()) {
				// TODO use xCursor and yCursor
			}
			text += axioms[i].getText() + (i+1 < axioms.length ? ", " : "");
			xCursor += fm.stringWidth(axioms[i].getText() + ", "); // Even though the last axiom doesn't add ', ', xCursor is not used after it, so it doesn't matter
		}
		// Write each line
		for (String line : text.split("\n")) {
			g2d.drawString(line, position.x+STD_MARGIN, position.y+height+fontHeight);
			height += fontHeight;
		}
		// Bottom line
		g2d.drawLine(position.x, position.y+height+STD_MARGIN, position.x+WIDTH, position.y+height+STD_MARGIN);
		// Side lines
		g2d.drawLine(position.x, position.y, position.x, position.y+height+STD_MARGIN);
		g2d.drawLine(position.x+WIDTH, position.y, position.x+WIDTH, position.y+height+STD_MARGIN);
	}
}
