package ver1.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import ownapi.OWNAxiom;
import ver1.Interpreter;
import ver1.Node;
import ver1.Operation;
import ver1.Tableau;
import ver1.util.Pair;

public class GUI extends JFrame implements ActionListener, MouseListener {
	public static class NonCompatibleFileException extends Exception {}
	
	private static final int Y_SIZE = 600;
	private static final int JMB_SIZE = 21;
	
	private Graph graph;
	private Options options;
	private JMenuBar jmbarGUI;
	private JMenu jmFile;
	private JMenuItem jmitemOpen, jmitemReset, jmitemClose;
	
	private static Tableau tableau;
	private static List<Pair<Node, Operation>> operations;
	
	public GUI() {
		super("Visual Tableaux");
		
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

		graph = new Graph();
		graph.addMouseListener(this);
		options = new Options();
		
		// init graph
		operations = options.setOptions(tableau.getOperations(), tableau.checkNextCreatedNode());
		
		container.add(graph);
		container.add(options);
		this.getContentPane().add(container);
		
        this.setSize((graph.getWidth() + options.getWidth()), Y_SIZE + JMB_SIZE);
		//this.setSize(1000, 600+21);
		
        this.pack();
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
	
	public static void execOption(int option) {
		System.out.println(operations.get(option));
	}

	public static void main(String[] args) {
		//Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	// Get files
        		String sConcept = "ontologies/testConcept.owl";
        		String sTBox = "ontologies/testB3TBox.owl";
        		
        		//sConcept = chooseFile("Select concept file", "OWL Ontology Document", "owl");
        		//sTBox = chooseFile("Select TBox file", "OWL Ontology Document", "owl");
        		//System.out.println(sConcept);
        		//System.out.println(sTBox);
        		
        		// Load files
        		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        		IRI conceptIRI = IRI.create(new File(sConcept));
        		IRI tboxIRI = IRI.create(new File(sTBox));
        		
        		OWLOntology concept = null;
        		OWLOntology tbox = null;
        		try {
        			concept = man.loadOntology(conceptIRI);
        			tbox = man.loadOntology(tboxIRI);
        		} catch (OWLOntologyCreationException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		
        		// Get concept and ontology K
        		Pair<OWNAxiom, HashSet<OWNAxiom>> pairCon_K = Interpreter.read(concept, tbox);
        		
        		// Create and initialize tableau
        		tableau = new Tableau(pairCon_K.getSecond());
        		tableau.init(pairCon_K.getFirst());
        		
        		
        		GUI gui = new GUI();
        		gui.setVisible(true);
            }
        });
	}
	
	private static String chooseFile(String title, String desc, String ext) {
		String path = "";
		FileNameExtensionFilter filter = new FileNameExtensionFilter(desc, ext);
		boolean fileAccepted = false;
		while (!fileAccepted) {
			try {
				JFileChooser fc = new JFileChooser();
		        fc.setFileFilter(filter);
		        fc.setDialogTitle(title);
		        int option = fc.showOpenDialog(null);
		        if (!filter.accept(fc.getSelectedFile())) 
		        	throw new NonCompatibleFileException();
		        if (option == JFileChooser.APPROVE_OPTION) 
		        	path = fc.getSelectedFile().getAbsolutePath();
		        fileAccepted = true;
			} catch(NonCompatibleFileException ex) {
				JOptionPane.showMessageDialog(null, "Select an appropriate " + desc + " file.");
			}
		}
		return path;
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
		System.out.println("gui");
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
