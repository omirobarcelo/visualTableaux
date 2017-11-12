package ver1.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class GraphNode {
	
	private static final String SEP = ", ";
	private static final Font FONT_NAME = new Font(Font.MONOSPACED, Font.PLAIN, 16);
	private static final Font FONT_TEXT = new Font(Font.MONOSPACED, Font.PLAIN, 14);
	private static final int STD_MARGIN = 5;
	
	private static final int MIN_WIDTH = 200;
	
	private static int width = MIN_WIDTH;
	
	private String node;
	private GraphAxiom[] axioms;
	private int height;
	// Top-left corner
	private Point position;
	// Background color
	private Color colBG;
	// Font color
	private Color colFont;
	// Line color
	private Color colLine;
	// Highlight color
	private Color colHL;
	private Font nameFont, textFont;
	private boolean blocked;
	private boolean bolded;
		
	public GraphNode(String node, String[] axioms, int x, int y) {
		this.node = node;
		this.axioms = new GraphAxiom[axioms.length];
		int i = 0;
		for (String s : axioms) 
			this.axioms[i++] = new GraphAxiom(s);
		this.height = 0;
		position = new Point(x, y);
		
		colBG = Color.GRAY;
		colFont = Color.BLACK;
		colLine = Color.BLACK;
		colHL = Color.YELLOW;
		
		nameFont = FONT_NAME;
		textFont = FONT_TEXT;
		
		blocked = false;
		bolded = false;
	}
	
	public void toggleBlocked() {
		blocked = !blocked;
	}
	
	public void toggleBolded() {
		bolded = !bolded;
	}
	
	public static int getWidth() {
		return width;
	}
	
	/**
	 * Sets widths to the given width if it's larger than the minimum
	 * @param width
	 */
	public static void setWidth(int width) {
		GraphNode.width = width < MIN_WIDTH ? MIN_WIDTH : width;
	}
	
	public static int getStdHeight(Graphics g) {
		int stdHeight = 0;
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(FONT_NAME);
		stdHeight += g2d.getFontMetrics().getHeight();
		stdHeight += STD_MARGIN;
		g2d.setFont(FONT_TEXT);
		stdHeight += g2d.getFontMetrics().getHeight();
		stdHeight += STD_MARGIN;
		return stdHeight;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setStroke(new BasicStroke(bolded ? 5.0f : 3.0f));
		// If blocked, color background of node name gray
		g2d.setFont(nameFont);
		FontMetrics fm = g2d.getFontMetrics();
		int fontHeight = fm.getHeight();
		if (blocked) {
			g2d.setPaint(colBG);
			g2d.fillRect(position.x, position.y, width, fontHeight+STD_MARGIN);
		}
		// Upper line
		g2d.setPaint(colLine);
		g2d.drawLine(position.x, position.y, position.x+width, position.y);
		// Node name
		g2d.setPaint(colFont);
		int nodeWidth = fm.stringWidth(node);
		g2d.drawString(node, position.x+(width/2-nodeWidth/2), position.y+fontHeight); // WIDTH/2-nodeWidth/2 -> Centers the text
		// Middle line
		g2d.setPaint(colLine);
		g2d.drawLine(position.x, position.y+fontHeight+STD_MARGIN, position.x+width, position.y+fontHeight+STD_MARGIN);
		// Axioms
		g2d.setFont(textFont);
		fm = g2d.getFontMetrics();
		fontHeight = fm.getHeight();
		int height = fontHeight + STD_MARGIN; // height of the box; each text line height is going to be added
		String text = "";
		int xCursor = position.x+STD_MARGIN, yCursor = position.y+height+STD_MARGIN; // cursor of the text, to save each axiom position
		for (int i = 0; i < axioms.length; i++) {
			// If last line longer than the box, add a new line
			String lastLine = text.split("\n")[text.split("\n").length-1];
			if (fm.stringWidth(lastLine+axioms[i].getText()+SEP) > width-STD_MARGIN*2) {
				text += "\n";
				yCursor += fontHeight; // update yCursor
				xCursor = position.x+STD_MARGIN; // reset xCursor
			}
			axioms[i].setPosition(xCursor, yCursor);
			// DEBUG
			//if (i == 1) {
			// DEBUG
			if (axioms[i].isHighligthed()) {
				g2d.setPaint(colHL);
				g2d.fillRect(xCursor, yCursor, fm.stringWidth(axioms[i].getText()), fontHeight);
			}
			text += axioms[i].getText() + (i+1 < axioms.length ? SEP : "");
			xCursor += fm.stringWidth(axioms[i].getText() + SEP); // Even though the last axiom doesn't add ', ', xCursor is not used after it, so it doesn't matter
		}
		// Write each line
		g2d.setPaint(colFont);
		for (String line : text.split("\n")) {
			g2d.drawString(line, position.x+STD_MARGIN, position.y+height+fontHeight);
			height += fontHeight;
		}
		// Save GraphNode total height
		this.height = height + STD_MARGIN;
		// Bottom line
		g2d.setPaint(colLine);
		g2d.drawLine(position.x, position.y+this.height, position.x+width, position.y+this.height);
		// Side lines
		g2d.setPaint(colLine);
		g2d.drawLine(position.x, position.y, position.x, position.y+this.height);
		g2d.drawLine(position.x+width, position.y, position.x+width, position.y+this.height);
	}
}
