package ver1.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class GUI extends JFrame implements ActionListener, MouseListener {
	private static final int Y_SIZE = 600;
	private static final int JMB_SIZE = 21;
	
	Graph graph;
	Options options;
	private JMenuBar jmbarGUI;
	private JMenu jmFile;
	private JMenuItem jmitemOpen, jmitemReset, jmitemClose;
	
	public GUI() {
		super("Visual Tableaux");

		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

		graph = new Graph();
		graph.addMouseListener(this);
		options = new Options();
		options.addMouseListener(this);
		
		container.add(graph);
		container.add(options);
		this.getContentPane().add(container);
		
        this.setSize((graph.getWidth() + options.getWidth()), Y_SIZE + JMB_SIZE);
		//this.setSize(1000, 600+21);
		
        //this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
	}
	
	private void initComponents() {
		jmbarGUI = new JMenuBar();
		jmFile = new JMenu();
		jmitemOpen = new JMenuItem();
		jmitemReset = new JMenuItem();
		jmitemClose = new JMenuItem();
		
		// TODO set accelerators
		jmitemOpen.setText("Open");
		jmitemOpen.addActionListener(this);
		jmitemReset.setText("Reset");
		jmitemReset.addActionListener(this);
		jmitemClose.setText("Close");
		jmitemClose.addActionListener(this);
		
		jmFile.setText("File");
		jmFile.add(jmitemOpen);
		jmFile.add(jmitemReset);
		jmFile.add(jmitemClose);
		
		jmbarGUI.add(jmFile);
		this.setJMenuBar(jmbarGUI);
	}

	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem item = (JMenuItem)e.getSource();
		if (item == jmitemOpen) {
			//TODO
		} else if (item == jmitemReset) {
			// TODO
		} else if (item == jmitemClose) {
			System.exit(0);
		}
	}

}
