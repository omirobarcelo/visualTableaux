package ver1.ui;

import javax.swing.*;

public class Graph extends JPanel {
	private static final int X_SIZE = 800;
	private static final int Y_SIZE = 600;
	
	private JTextArea jtaTmp;
	
	public Graph() {
		jtaTmp = new JTextArea();
		jtaTmp.setText("Test");
		this.add(jtaTmp);
		//this.repaint();
	}
	
	@Override
	public int getWidth() {
		return X_SIZE;
	}
	
	@Override
	public int getHeight() {
		return Y_SIZE;
	}
	
//	@Override
//	public Dimension getPreferredSize() {
//		return new Dimension(X_SIZE, Y_SIZE+JMB_SIZE);
//	}
}
