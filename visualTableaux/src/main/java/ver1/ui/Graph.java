package ver1.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.*;

import ver1.Tableau;

public class Graph extends JPanel {
	private static final int X_SIZE = 800;
	private static final int Y_SIZE = 600;
	private static final int BORDER = 10;
	
	// TODO canvas size variables
	
	private JTextArea jtaTmp;
	
	private Tableau tableau;
	// TODO instead of the full tableau maybe pass the ontology string
	// and then the TreeNode to make the breadth first search to paint the graph
	
	public Graph(Tableau tableau) {
		//this.setMaximumSize(new Dimension(X_SIZE, Y_SIZE));
		this.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
		
		// TODO remove the text area when the graph painting starts to work
		jtaTmp = new JTextArea();
		jtaTmp.setMaximumSize(new Dimension(X_SIZE, Y_SIZE));
		jtaTmp.setText("Test");
		jtaTmp.setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
		//this.add(jtaTmp);
		//this.repaint();
		
		// TODO change to string ontology and TreeNode root
		this.tableau = tableau;
	}
	
	// TODO remove in the final version
	public void setText(String text) {
		jtaTmp.setText("");
		jtaTmp.setText(text);
		this.revalidate();
		this.repaint();
	}
	
	public void addText(String text) {
		jtaTmp.append(text);
		this.revalidate();
		this.repaint();
	}
	
	public void clearText() {
		jtaTmp.setText("");
		this.revalidate();
		this.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// Paint background
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.WHITE);
		// TODO change max sizes to current canvas size
		// canvas size equal to lowest point of the graph plus some space
		g2d.fillRect(0, 0, X_SIZE, Y_SIZE);
		
		// Write K
		g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
		g2d.setPaint(Color.BLACK);
		FontMetrics fm = g2d.getFontMetrics();
		g2d.drawString(tableau.getOntology(), 10, 5+fm.getHeight());
		g2d.drawLine(0, 5+fm.getHeight()+10, 
				10+fm.stringWidth(tableau.getOntology())+10, 5+fm.getHeight()+10);
		g2d.drawLine(10+fm.stringWidth(tableau.getOntology())+10, 5+fm.getHeight()+10, 
				10+fm.stringWidth(tableau.getOntology())+10, 0);
		
		// TODO remove this line. Just to test
		g2d.setPaint(Color.RED);
		Line2D line = new Line2D.Double(200, 50, 200, 1000);
		g2d.draw(line);
		
		// TODO paint the whole tree according to the current state
	}
	
	@Override
	public Dimension getPreferredSize() {
		// TODO make it dynamic
		// Take the lowest point of the lowest box, and add some space
		// AKA use canvas size variables
		return new Dimension(X_SIZE, Y_SIZE);
	}
}
