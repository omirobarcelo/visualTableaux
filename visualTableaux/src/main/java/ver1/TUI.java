package ver1;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import ownapi.*;

public class TUI {

	public static void main(String[] args) {
		// Load files
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		IRI conceptIRI = IRI.create(new File("ontologies/testConcept.owl"));
		IRI tboxIRI = IRI.create(new File("ontologies/testTBox.owl"));
		
		OWLOntology concept = null;
		OWLOntology tbox = null;
		try {
			concept = man.loadOntology(conceptIRI);
			tbox = man.loadOntology(tboxIRI);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// DEBUG
		if (concept == null || tbox == null)
			System.out.println("Something is null");
		// DEBUG
		
		// Interpreter.read
		Pair<OWNAxiom, HashSet<OWNAxiom>> pairCon_K = Interpreter.read(concept, tbox);
		// DEBUG
		System.out.println(pairCon_K.getFirst());
		System.out.println(pairCon_K.getSecond());
		// DEBUG
		
		// Create and initialize tableau
		Tableau tableau = new Tableau(pairCon_K.getSecond());
		tableau.init(pairCon_K.getFirst());
		// DEBUG
		String[] ops = tableau.printOperations();
		for (String s : ops)
			System.out.println(s);
		// DEBUG
				
		// Loop (show status, execute operation, get new status)
		boolean endProgram = false;
		while (!endProgram) {
			showStatus(tableau);
			showOperations(tableau);
			int option = readIntFromStdin("\nWhich option do you select? ");
			if (option == 0)
				endProgram = true;
			else {
				// TODO apply operation
			}
		}
	}
	

	private static void showStatus(Tableau tableau) {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("K : " + tableau.getOntology());
		System.out.println();
		Stack<TreeNode> s = new Stack<TreeNode>();
		s.push(tableau.getFirstNode());
		while (!s.isEmpty()) {
			TreeNode n = s.pop();
			//visit(node)
			System.out.println(n.getData().getId() + " : " + tableau.getAxioms(n.getData()));
			if (!n.getChildren().isEmpty()) {
				for (TreeNode succ : n.getChildren()) {
					System.out.println(n.getData().getId() + "--" + 
							tableau.getRelations(n.getData(), succ.getData()) + 
							"--" + succ.getData().getId());
					s.push(succ);
				}
			}
		}
		System.out.println();
	}

	private static void showOperations(Tableau tableau) {
		// TODO Auto-generated method stub
		
	}
	
	private static int readIntFromStdin(String msg) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
	    System.out.print(msg);
	    int number = scanner.nextInt();
	    scanner.close();
	    return number;
	}
}
