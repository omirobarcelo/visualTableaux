package ver1.ui;

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
	private static final int X_SIZE = 250;
	private static final int Y_SIZE = 600;
	private static final int BORDER = 10;
	
	private static final int BUTTON_WIDTH = 230;
	private static final int MAX_STRING_SIZE = 23;
	
	private List<JButton> jbList;
	
	public Options() {
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
				String s = op.fullString(n, nextCreatedNode);
				//JButton jbOp = new JButton(op.fullString(n, nextCreatedNode));
				JButton jbOp = new JButton(transformText(op.fullString(n, nextCreatedNode)));
				// Needed both minimum and maximum size to make the button use all the 
				// panel space
				jbOp.setMinimumSize(new Dimension(BUTTON_WIDTH, 0));
				jbOp.setMaximumSize(new Dimension(BUTTON_WIDTH, Y_SIZE));
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
	}
	
	/**
	 * Transform the button text to fit
	 * @param s
	 * @return
	 */
	private String transformText(String s) {
		String t = s;
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
	
	@Override
	public int getWidth() {
		return X_SIZE;
	}
	
	@Override
	public int getHeight() {
		return Y_SIZE;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(X_SIZE, Y_SIZE);
	}

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
