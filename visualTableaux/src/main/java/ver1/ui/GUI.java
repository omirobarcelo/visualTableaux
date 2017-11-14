package ver1.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
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
	
	private static final int X_SIZE = 1250;
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
	
	private Node selectedNode;
	
	public GUI() {
		super("Visual Tableaux");
		
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

		graph = new Graph(tableau);
		graph.addMouseListener(this);
		JScrollPane scrollGraph = new JScrollPane(graph);
		options = new Options();
		JScrollPane scrollOptions = new JScrollPane(options);
		
		container.add(scrollGraph);
		container.add(scrollOptions);
		this.getContentPane().add(container);
		
		this.setSize(X_SIZE, Y_SIZE+JMB_SIZE);
		
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
	
	/**
	 * Applies to tableau the operation in the index option. Then updates graph and options
	 * Called from class Options when a button is pressed
	 * @param option Index of the list of operations
	 */
	public void execOption(int option) {
		Pair<Node, Operation> pNode_Op = operations.get(option);
		// Blocking automatically applied
		tableau.apply(pNode_Op.getFirst(), pNode_Op.getSecond());
		
		// Backtracking automatically applied
		if (tableau.isFinished()) {
			// Show dialog, update status, clear highlighting, and remove operations
			// It's necessary to clear highlighting since when a button is pressed and the option
			// disappears, we haven't exited from it, so the highlighting is not turned off
			JOptionPane.showMessageDialog(this, "Tableau expansion has finished.\n" +
					"This ontology is " + (tableau.isSatisfiable() ? "" : "not ") + "satisfiable");

			graph.clearGraphOntologyHighlighting();
			graph.clearGraphNodesHighlighting();
			graph.revalidate();
			graph.repaint();
			options.clearOptions();
		} else {
			// Update status, and set new operations, and clear highlighting
			// It's necessary to clear highlighting since when a button is pressed and the option
			// disappears, we haven't exited from it, so the highlighting is not turned off
			graph.clearGraphOntologyHighlighting();
			graph.clearGraphNodesHighlighting();
			graph.revalidate();
			graph.repaint();
			operations = options.setOptions(tableau.getOperations(), tableau.checkNextCreatedNode(), selectedNode);
		}
	}
	
	/**
	 * Sets highlighting of axiom from ontology or graph node according to state
	 * The operation defines how the highlighting will be applied and to which axioms
	 * Called from class Options when the mouse hovers over a button
	 * @param op
	 * @param state true when button entered, false when button exited
	 */
	public void setHighlightAxiom(Operation op, boolean state) {
		graph.setHighlighting(selectedNode, op, state);
		graph.revalidate();
		graph.repaint();
	}

	public static void main(String[] args) {
		//Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	// openFiles will return false if the process has been cancelled
            	if (openFiles()) {
        		
	        		// Create and initialize tableau
	        		tableau = new Tableau(pairCon_K.getSecond(), true);
	        		tableau.init(pairCon_K.getFirst());
	        		
	        		
	        		GUI gui = new GUI();
	        		gui.setVisible(true);
            	} else {
            		System.exit(0);
            	}
            }
        });
	}
	
	private static boolean openFiles() {
		// Get files
		String sConcept = "ontologies/testConcept.owl";
		String sTBox = "ontologies/testB3TBox.owl";
		
		// If sConcept or sTBox are null, then the open file process has been cancelled
		sConcept = chooseFile("Select concept file", "OWL Ontology Document", "owl");
		if (sConcept == null)
			return false;
		sTBox = chooseFile("Select TBox file", "OWL Ontology Document", "owl");
		if (sTBox == null)
			return false;
		
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
		
		return true;
	}
	
	private static String chooseFile(String title, String desc, String ext) {
		String path = "";
		FileNameExtensionFilter filter = new FileNameExtensionFilter(desc, ext);
		boolean fileAccepted = false;
		while (!fileAccepted) {
			try {
				JFileChooser fc = new JFileChooser();
				// Set JFileChooser to working directory
				fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		        fc.setFileFilter(filter);
		        fc.setDialogTitle(title);
		        int option = fc.showOpenDialog(null);
		        switch (option) {
			        case JFileChooser.APPROVE_OPTION:
			        	if (!filter.accept(fc.getSelectedFile())) 
				        	throw new NonCompatibleFileException();
			        	path = fc.getSelectedFile().getAbsolutePath();
			        	break;
			        case JFileChooser.CANCEL_OPTION:
			        	path = null;
			        	break;
			        case JFileChooser.ERROR_OPTION:
			        	//System.out.println("Error");
			        	break;
		        }
		        fileAccepted = true;
			} catch(NonCompatibleFileException ex) {
				JOptionPane.showMessageDialog(null, "Select an appropriate " + desc + " file.");
			}
		}
		return path;
	}
	
	/**
	 * Resets tableau, paints the graph, and clear the options
	 * Executed on startup, when current tableau is reset, and when another tableau is opened
	 */
	private void reset() {
		tableau = new Tableau(pairCon_K.getSecond(), true);
		tableau.init(pairCon_K.getFirst());
		// Even though graph shares the above tableau, we need to reset it so it repaints
		graph.reset(tableau);
		graph.revalidate();
		graph.repaint();
		options.clearOptions();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		selectedNode = graph.boldGraphNode(e.getX(), e.getY());
		graph.revalidate();
		graph.repaint();
		operations = options.setOptions(tableau.getOperations(), tableau.checkNextCreatedNode(), selectedNode);
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
			// If openFiles returns false, the process has been cancelled, so do not reset
			if (openFiles())
				reset();
		} else if (item == jmitemReset) {
			reset();
		} else if (item == jmitemClose) {
			System.exit(0);
		}
	}

}
