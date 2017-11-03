package ver1.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.swing.*;

import ver1.Node;
import ver1.Operation;
import ver1.util.Pair;

public class Options extends JPanel implements ActionListener {
	private static final int X_SIZE = 300;
	private static final int Y_SIZE = 600;
	private static final int BORDER = 10;
	
	private static final int BUTTON_WIDTH = 270;
	private static final int BUTTON_HEIGHT = 60;
	private static final int MAX_STRING_SIZE = 25;
	
	private List<JButton> jbList;
	
	public Options() {
		//this.setMinimumSize(new Dimension(X_SIZE, Y_SIZE));
		
		jbList = new ArrayList<JButton>();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
	}
	
	public List<Pair<Node, Operation>> setOptions(HashMap<Node, HashSet<Operation>> options, 
												  String nextCreatedNode) {
		jbList.clear();
		this.removeAll();
		
		List<Pair<Node, Operation>> ops = new ArrayList<Pair<Node, Operation>>();
		for (Node n : options.keySet()) {
			JLabel jlNode = new JLabel("Node " + n.getId());
			this.add(jlNode);
			for (Operation op : options.get(n)) {
				ops.add(new Pair<Node, Operation>(n, op));
				//String s = op.fullString(n, nextCreatedNode);
				//JButton jbOp = new JButton(op.fullString(n, nextCreatedNode));
				JButton jbOp = new JButton(transformText(op.fullString(n, nextCreatedNode)));
				// Needed both minimum and maximum size to make the button use all the 
				// panel space, plus preferredSize because Java layout is a bitch
				jbOp.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
				// With preferredSize button doesn't adapt to the text,
				// but the horizontal size now changes a bit,
				// because Java pretty much ignores min/max without preferred
				//jbOp.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
				jbOp.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT+30));
				jbOp.setHorizontalAlignment(SwingConstants.CENTER);
				jbOp.addActionListener(this);
				jbList.add(jbOp);
				this.add(jbOp);
			}

		}
		// To make everything stick to the top
		this.add(Box.createVerticalGlue());
		
		// TODO maybe repaint?
		this.revalidate();
		this.repaint();
		
		return ops;
	}
	
	public void clearOptions() {
		jbList.clear();
		this.removeAll();
		// TODO maybe repaint?
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Transform the button text to fit
	 * @param s
	 * @return
	 */
	private String transformText(String s) {
		// TODO take out HTML wrapper if it doesn't make it work in other systems
		String t = "<html><center>" + s + "</center></html>";
		String[] splitted = s.split("\u2192");
		//if (splitted[0].trim().length() > MAX_STRING_SIZE || 
		//	splitted[1].trim().length() > MAX_STRING_SIZE) {
		// Line breaks have to be done in HTML, and also centered in HTML
		if (s.length() > MAX_STRING_SIZE) {
			t = "<html><center>" + 
					splitted[0].trim() + 
					"<br />\u2193<br />" + 
					splitted[1].trim() + 
				"</center></html>";
		}
		return t;
	}
	
//	@Override
//	public Dimension getPreferredSize() {
//		// Dynamically growing preferredSize to accomodate all buttons
//		// and make sure scrollable works
//		System.out.println("\nnew");
//		for (Component c : this.getComponents()) {
//			System.out.println(c.getClass() + ":" + c.getY());
//		}
//		return new Dimension(X_SIZE, jbList.size() * (65));
////		// Get y of bottom button
////		int max = 0;
////		for (JButton jb : jbList) {
////			System.out.println("y: " + jb.getLocation().y);
////			System.out.println("abs: " + jb.getY());
////			max = jb.getLocation().y > max ? jb.getLocation().y : max;
////		}
////		System.out.println("max: " + max+BUTTON_HEIGHT+BORDER);
////		return new Dimension(X_SIZE, max+BUTTON_HEIGHT+BORDER);
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton button = (JButton)e.getSource();
		int index = jbList.indexOf(button);
//		System.out.println(index);
		//GUI.execOption(index);
		// Get GUI where this component lives and execute option
		((GUI)SwingUtilities.getWindowAncestor(this)).execOption(index);
	}
}
