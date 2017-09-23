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
	private static final int X_SIZE = 200;
	private static final int Y_SIZE = 600;
	
	private JButton jbTmp1;
	private JButton jbTmp2;
	
	private List<JButton> jbList;
	
	public Options() {
//		jbTmp1 = new JButton("Temp1");
//		jbTmp2 = new JButton("Temp2");
		jbList = new ArrayList<JButton>();
//		jbList.add(jbTmp1);
//		jbList.add(jbTmp2);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
//		this.add(jbTmp1);
//		this.add(jbTmp2);
//		jbTmp1.addActionListener(this);
//		jbTmp2.addActionListener(this);
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
				JButton jbOp = new JButton(op.fullString(n, nextCreatedNode));
				jbOp.addActionListener(this);
				jbList.add(jbOp);
				this.add(jbOp);
			}
		}
		
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
