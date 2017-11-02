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
	
	private static Pair<OWNAxiom, HashSet<OWNAxiom>> pairCon_K;
	private static Tableau tableau;
	private static List<Pair<Node, Operation>> operations;
	
	public GUI() {
		super("Visual Tableaux");
		
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

		// TODO change to pass ontology string, and root
		graph = new Graph(tableau);
		// TODO maybe mouse could be used to detect the box clicked, and show 
		// the operations related to said box
		// If so, clicked box should also be remarked
		graph.addMouseListener(this);
		JScrollPane scrollGraph = new JScrollPane(graph);
		options = new Options();
		JScrollPane scrollOptions = new JScrollPane(options);
		
		// TODO update graph to paint instead of text area
		// init graph
		graph.setText(stringStatus());
		// DEBUG
		System.out.println(stringStatus());
		// DEBUG
		operations = options.setOptions(tableau.getOperations(), tableau.checkNextCreatedNode());
		
		//container.add(graph);
		container.add(scrollGraph);
		//container.add(options);
		container.add(scrollOptions);
		this.getContentPane().add(container);
		
        //this.setSize((graph.getWidth() + options.getWidth()), Y_SIZE + JMB_SIZE);
		this.setSize(1110, 600+21);
		
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
	
	// TODO may not be necessary when completed
	private static String stringStatus() {
		String status = "\n";
		status += "K : " + tableau.getOntology() + "\n";
		status += "\n";
		tableau.clearStatus();
		tableau.iterativePreorder(tableau.getFirstNode(), "stringNodeStatus");
		status += tableau.stringStatus() + "\n";
		status += "\n";
		return status;
	}
	
	public void execOption(int option) {
		// TODO
		Pair<Node, Operation> pNode_Op = operations.get(option);
		// Blocking automatically applied
		tableau.apply(pNode_Op.getFirst(), pNode_Op.getSecond());
		
		// Backtracking automatically applied
		if (tableau.isFinished()) {
			// TODO show dialog, update status, and remove operations
			JOptionPane.showMessageDialog(this, "Tableau expansion has finished.\n" +
					"This ontology is " + (tableau.isSatisfiable() ? "" : "not ") + "satisfiable");
			graph.setText(stringStatus());
			// TODO pass to graph
			graph.repaint();
			// DEBUG
			System.out.println(stringStatus());
			// DEBUG
			options.clearOptions();
		} else {
			// update
			graph.setText(stringStatus());
			// TODO pass to graph
			graph.repaint();
			// DEBUG
			System.out.println(stringStatus());
			// DEBUG
			operations = options.setOptions(tableau.getOperations(), tableau.checkNextCreatedNode());
		}
	}

	public static void main(String[] args) {
		//Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	openFiles();
        		
        		// Create and initialize tableau
        		tableau = new Tableau(pairCon_K.getSecond(), true);
        		tableau.init(pairCon_K.getFirst());
        		
        		
        		GUI gui = new GUI();
        		gui.setVisible(true);
            }
        });
	}
	
	private static void openFiles() {
		// Get files
		String sConcept = "ontologies/testConcept.owl";
		String sTBox = "ontologies/testB3TBox.owl";
		
		sConcept = chooseFile("Select concept file", "OWL Ontology Document", "owl");
		sTBox = chooseFile("Select TBox file", "OWL Ontology Document", "owl");
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
		pairCon_K = Interpreter.read(concept, tbox);
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
	
	private void reset() {
		tableau = new Tableau(pairCon_K.getSecond(), true);
		tableau.init(pairCon_K.getFirst());
		// TODO update to current graph instead of text area
		graph.setText(stringStatus());
		operations = options.setOptions(tableau.getOperations(), tableau.checkNextCreatedNode());
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
			// TODO
			openFiles();
			reset();
		} else if (item == jmitemReset) {
			// TODO
			reset();
		} else if (item == jmitemClose) {
			System.exit(0);
		}
	}

}
