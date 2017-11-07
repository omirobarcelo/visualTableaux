package ver1.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.*;

import ver1.Node;
import ver1.Tableau;

public class Graph extends JPanel {
	private static final int X_SIZE = 800;
	private static final int Y_SIZE = 600;
	private static final int BORDER = 10;
	
	// TODO canvas size variables
	private int CANVAS_WIDTH = X_SIZE;
	private int CANVAS_HEIGHT = Y_SIZE;
		
	private Tableau tableau;
	// TODO instead of the full tableau maybe pass the ontology string
	// and then the TreeNode to make the breadth first search to paint the graph
	
	public Graph(Tableau tableau) {
		//this.setMaximumSize(new Dimension(X_SIZE, Y_SIZE));
		this.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
		
		// TODO change to string ontology and TreeNode root
		this.tableau = tableau;
	}
	
	public void setTableau(Tableau tableau) {
		this.tableau = tableau;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// Paint background
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.WHITE);
		// TODO change max sizes to current canvas size
		// canvas size equal to lowest point of the graph plus some space
		//g2d.fillRect(0, 0, X_SIZE, Y_SIZE);
		// 50 represents some margin so everything shown is filled
		g2d.fillRect(0, 0, CANVAS_WIDTH+50, CANVAS_HEIGHT+50);
		
		// Write K
		g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
		g2d.setPaint(Color.BLACK);
		FontMetrics fm = g2d.getFontMetrics();
		// TODO change to GraphAxiom, so the position of each axiom in the ontology is known and can be highlighted
		g2d.drawString(tableau.getOntology(), 10, 5+fm.getHeight());
		// Bottom line
		int lineLength = 10+fm.stringWidth(tableau.getOntology())+10;
		g2d.drawLine(0, 5+fm.getHeight()+10, lineLength, 5+fm.getHeight()+10);
		// If ontology longer than panel, adjust canvas size
		CANVAS_WIDTH = Math.max(CANVAS_WIDTH, lineLength+10);
		// Side line
		g2d.drawLine(lineLength, 5+fm.getHeight()+10, lineLength, 0);
		
		// TODO paint the whole tree according to the current state
		String sAxioms = tableau.getAxioms(tableau.getFirstNode().getData());
		String[] axioms = (sAxioms.substring(1, sAxioms.length()-1)).split(", ");
		GraphNode gn = new GraphNode(tableau.getFirstNode().getData().getId(), axioms, 200, 200);
		gn.paint(g);
		
		// TODO update canvas size
	}
	
	@Override
	public Dimension getPreferredSize() {
		// TODO make it dynamic
		// Take the lowest point of the lowest box, and add some space
		// AKA use canvas size variables
		//return new Dimension(X_SIZE, Y_SIZE);
		return new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
	}
}
