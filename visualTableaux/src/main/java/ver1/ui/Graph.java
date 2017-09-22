package ver1.ui;

import java.awt.Dimension;

import javax.swing.*;

public class Graph extends JPanel {
	private static final int X_SIZE = 800;
	private static final int Y_SIZE = 600;
	
	private JTextArea jtaTmp;
	
	public Graph() {
		this.setMaximumSize(new Dimension(X_SIZE, Y_SIZE));
		
		jtaTmp = new JTextArea();
		jtaTmp.setMaximumSize(new Dimension(X_SIZE, Y_SIZE));
		jtaTmp.setText("Test");
		jtaTmp.setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
		this.add(jtaTmp);
		//this.repaint();
	}
	
	public void setText(String text) {
		jtaTmp.setText(text);
	}
	
	public void addText(String text) {
		jtaTmp.append(text);
	}
	
	public void clearText() {
		jtaTmp.setText("");
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
}
