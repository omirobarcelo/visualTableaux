package ver1.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.*;

import ver1.Node;
import ver1.Tableau;
import ver1.TreeNode;

import ver1.util.Pair;

public class Graph extends JPanel {
	private static final int X_SIZE = 700;
	private static final int Y_SIZE = 600;
	private static final int BORDER = 10;
	// Margin respect the sides and the graph
	private static final int OUT_MARGIN = 20;
	// Margin between the GraphNodes
	private static final int IN_MARGIN = 10;
	// Height distance between nodes
	private static final int DIST = 50;
	
	private int CANVAS_WIDTH = X_SIZE;
	private int CANVAS_HEIGHT = Y_SIZE;
		
	private Tableau tableau;
	private List<GraphNode> graphNodes;
	
	public Graph(Tableau tableau) {
		this.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
		
		this.tableau = tableau;
		this.graphNodes = new ArrayList<GraphNode>();
	}
	
	public void reset(Tableau tableau) {
		CANVAS_WIDTH = X_SIZE;
		CANVAS_HEIGHT = Y_SIZE;
		this.tableau = tableau;
		this.graphNodes = new ArrayList<GraphNode>();
	}
	
	public Node boldGraphNode(int x, int y) {
		Node n = null;
		for (GraphNode gn : graphNodes) {
			if (gn.isGraphNode(x, y)) {
				gn.setBolded(true);	
				n = gn.getNode();
			} else
				gn.setBolded(false);
		}
		return n;
	}
	
	private Node getBoldedNode() {
		for (GraphNode gn : graphNodes)
			if (gn.isBolded())
				return gn.getNode();
		return null;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// Obtain bolded node, if any, to repaint, and reset list
		Node bold = getBoldedNode();
		graphNodes.clear();
		// Paint background
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.WHITE);
		// TODO change max sizes to current canvas size
		// canvas size equal to lowest point of the graph plus some space
		//g2d.fillRect(0, 0, X_SIZE, Y_SIZE);
		// Paint background equal to shown plus 2 graph nodes at each side
		g2d.fillRect(0, 0, CANVAS_WIDTH+GraphNode.getWidth()*2, CANVAS_HEIGHT+GraphNode.getStdHeight(g)*2);
		
		// Write K
		g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
		g2d.setPaint(Color.BLACK);
		FontMetrics fm = g2d.getFontMetrics();
		// TODO change to GraphAxiom, so the position of each axiom in the ontology is known and can be highlighted
		// TODO max_width equal to longest axiom + ", "
		g2d.drawString(tableau.getOntology(), 10, 5+fm.getHeight());
		// Bottom line
		int lineLength = 10+fm.stringWidth(tableau.getOntology())+10;
		g2d.drawLine(0, 5+fm.getHeight()+10, lineLength, 5+fm.getHeight()+10);
		// If ontology longer than panel, adjust canvas size
		CANVAS_WIDTH = Math.max(CANVAS_WIDTH, lineLength+10);
		// Side line
		g2d.drawLine(lineLength, 5+fm.getHeight()+10, lineLength, 0);
		
		// Get root and create its information holder
		TreeNode root = tableau.getFirstNode();
		InfoHolder rootInfo = new InfoHolder();
		// Check number of leaves of root to allocate all the horizontal space and center the first GraphNode
		rootInfo.setLeaves(tableau.numLeaves(root));
		// Adjust canvas size
		int workspaceLength = rootInfo.getLeaves()*(GraphNode.getWidth()+IN_MARGIN*2);
		CANVAS_WIDTH = Math.max(CANVAS_WIDTH, OUT_MARGIN+workspaceLength+OUT_MARGIN);
		// Set workspace and position of first node
		rootInfo.setWorkspace(CANVAS_WIDTH/2-workspaceLength/2, CANVAS_WIDTH/2+workspaceLength/2);
		rootInfo.setTopRight(new Point(CANVAS_WIDTH/2-GraphNode.getWidth()/2, 100));
		// Adjust canvas size
		CANVAS_WIDTH = Math.max(CANVAS_WIDTH, OUT_MARGIN+(rootInfo.getLeaves()*(GraphNode.getWidth()+IN_MARGIN*2))+OUT_MARGIN);
		// Enqueue root and its information
		Queue<Pair<TreeNode, InfoHolder>> q = new LinkedList<Pair<TreeNode, InfoHolder>>();
		q.add(new Pair<TreeNode, InfoHolder>(root, rootInfo));
		while (!q.isEmpty()) {
			Pair<TreeNode, InfoHolder> pNodeIH = q.poll();
			TreeNode tn = pNodeIH.getFirst();
			InfoHolder gnIH = pNodeIH.getSecond();
			
			// Draw GraphNode
			// TODO maybe change getAxioms to return the axioms and not the string
			String sAxioms = tableau.getAxioms(tn.getData());
			String[] axioms = (sAxioms.substring(1, sAxioms.length()-1)).split(", ");
			GraphNode gn = new GraphNode(tn.getData(), axioms, gnIH.getTopRight().x, gnIH.getTopRight().y);
			if (bold != null && bold.equals(tn.getData())) {
				gn.setBolded(true);
			}
			if (tableau.isBlocked(tn.getData())) {
				gn.toggleBlocked();
			}
			gn.paint(g);
			graphNodes.add(gn);
			
			// Adjust canvas size
			CANVAS_HEIGHT = Math.max(CANVAS_HEIGHT, gnIH.getTopRight().y+gn.getHeight()+OUT_MARGIN);
			
			// Enqueue children
			// Cursor to allocate the start of each node workspace
			int wsCursor = gnIH.getWorkspace().getFirst();
			int wsLength = gnIH.getWorkspace().getSecond() - gnIH.getWorkspace().getFirst();
			for (TreeNode child : tn.getChildren()) {
				InfoHolder ih = new InfoHolder();
				ih.setLeaves(tableau.numLeaves(child));
				// Assign a fraction of the parent workspace proportional to the number of leaves the child has
				ih.setWorkspace(wsCursor, wsCursor+=(wsLength*ih.getLeaves()/gnIH.getLeaves()));
				int childWSLength = ih.getWorkspace().getSecond() - ih.getWorkspace().getFirst();
				ih.setTopRight(new Point(ih.getWorkspace().getSecond()-childWSLength/2-GraphNode.getWidth()/2, 
						gnIH.getTopRight().y+gn.getHeight()+DIST));
				// Draw GraphEdge
				// TODO maybe change how to retrieve relations
				String sRel = tableau.getRelations(tn.getData(), child.getData());
				String[] rel = (sRel.substring(1, sRel.length()-1)).split(", ");
				GraphEdge ge = new GraphEdge(rel, new Point(gnIH.getTopRight().x+GraphNode.getWidth()/2, gnIH.getTopRight().y+gn.getHeight()), 
						new Point(ih.getWorkspace().getSecond()-childWSLength/2, gnIH.getTopRight().y+gn.getHeight()+DIST));
				ge.paint(g);
				q.add(new Pair<TreeNode, InfoHolder>(child, ih));
			}
		}
		
		
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
