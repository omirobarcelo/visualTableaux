package ver1.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import ver1.util.Pair;

public class GraphOntology {
	private static final Color COL_FONT = Color.BLACK;
	private static final Color COL_LINE = Color.BLACK;
	private static final Color COL_HL = Color.YELLOW;
	private static final String SEP = ", ";
	private static final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 18);
	private static final int X_MARGIN = 10;
	private static final int Y_MARGIN = 5;
	
	private GraphAxiom[] axioms;
	private String highlighted = "";
	private Color colFont, colLine, colHL;
	private Font font;
	
	public GraphOntology(String[] axioms) {
		this.axioms = new GraphAxiom[axioms.length];
		int i = 0;
		for (String s : axioms) 
			this.axioms[i++] = new GraphAxiom(s);
		
		this.colFont = COL_FONT;
		this.colLine = COL_LINE;
		this.colHL = COL_HL;
		
		this.font = FONT;
	}
	
	public void clearHighlight() {
		for (GraphAxiom ga : axioms) {
			ga.setHighlight(false);
		}
	}
	
	public void setHighlight(String axiom, boolean state) {
		for (GraphAxiom ga : axioms) {
			if (ga.getText().equals(axiom)) {
				//highlighted = state ? axiom : "";
				ga.setHighlight(state);
			}
		}
	}
	
	public GraphAxiom[] getGraphAxioms() {
		return axioms;
	}
	
	public void mergeGraphAxioms(GraphAxiom[] prevAxioms) {
		for (int i = 0; i < prevAxioms.length; i++) {
			for (int j = 0; j < axioms.length; j++) {
				if (prevAxioms[i].getText().equals(axioms[j].getText()))
					axioms[j].setHighlight(prevAxioms[i].isHighligthed());
			}
		}
	}
	
	/**
	 * Paints ontology and returns a pair with <ontoloogyLength, maxWidthOfAxiom>
	 * @param g
	 * @return
	 */
	public Pair<Integer, Integer> paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		
		// length -> How long is the drawn ontology, maxWidth -> Longest axiom
		int length = 0, maxWidth = 0;
		
		// Get axioms text and highlighting, if any
		String text = "";
		int offsetHL = -1, width = -1; // offset respect the String beginning and the width of highlighted axiom, if any
		for (int i = 0; i < axioms.length; i++) {
			// DEBUG
			//if (i == 0) {
			// DEBUG
//			if (axioms[i].isHighligthed()) {
//				width = fm.stringWidth(axioms[i].getText());
//				offsetHL = fm.stringWidth(text);
//			}
			
			if (axioms[i].isHighligthed()) {
			//if (axioms[i].getText().equals(highlighted)) {
				g2d.setPaint(colHL);
				g2d.fillRect(X_MARGIN+fm.stringWidth(text), Y_MARGIN, fm.stringWidth(axioms[i].getText()), fm.getHeight());
			}

			maxWidth = Math.max(maxWidth, fm.stringWidth(axioms[i].getText()+SEP));
			
			text += axioms[i].getText() + (i+1 < axioms.length ? SEP : "");;
		}
		
//		// If some relation is highlighted
//		if (offsetHL != -1 && width != -1) {
//			g2d.setPaint(colHL);
//			g2d.fillRect(x+offsetHL, y-fm.getHeight(), width, fm.getHeight());
//		}
		
		// Write K
		g2d.setPaint(colFont);
		g2d.drawString(text, X_MARGIN, Y_MARGIN+fm.getHeight());
		
		// Bottom line
		length = X_MARGIN+fm.stringWidth(text)+X_MARGIN;
		g2d.drawLine(0, Y_MARGIN+fm.getHeight()+Y_MARGIN, length, Y_MARGIN+fm.getHeight()+Y_MARGIN);
		
		// Side line
		g2d.drawLine(length, 0, length, Y_MARGIN+fm.getHeight()+Y_MARGIN);
		
		return new Pair<Integer, Integer>(length, maxWidth);
	}
}
