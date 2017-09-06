package ver1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
		
		// Get concept and ontology K
		Pair<OWNAxiom, HashSet<OWNAxiom>> pairCon_K = Interpreter.read(concept, tbox);
		
		// Create and initialize tableau
		Tableau tableau = new Tableau(pairCon_K.getSecond());
		tableau.init(pairCon_K.getFirst());
				
		// Loop (show status, choose operations, execute operation)
		boolean endProgram = false;
		boolean finished = false;
		boolean isSatisfiable = false;
		while (!endProgram && !finished) {
			showStatus(tableau);
			List<Pair<Node, Operation>> ops = showOperations(tableau);
			int option = readIntFromStdin("\nWhich option do you select? ");
			if (option == 0)
				endProgram = true;
			else {
				// TODO apply operation
				Pair<Node, Operation> pNode_Op = ops.get(option-1);
				tableau.apply(pNode_Op.getFirst(), pNode_Op.getSecond());
				
				if (finished = tableau.isFinished()) {
					// TODO get last snapshot
					// if no more snapshots, check satisfiability
					// isSatisfiable = tableau.isSatisfiable();
				}
			}
		}
		
		if (finished)
			System.out.println("This ontology is " +
					(isSatisfiable ? "" : "not ") + "satisfiable");
	}
	

	private static void showStatus(Tableau tableau) {
		System.out.println();
		System.out.println("K : " + tableau.getOntology());
		System.out.println();
		tableau.iterativePreorder(tableau.getFirstNode(), "printNodeStatus");
		System.out.println();
	}

	private static List<Pair<Node, Operation>> showOperations(Tableau tableau) {
		List<Pair<Node, Operation>> ops = new ArrayList<Pair<Node, Operation>>();
		HashMap<Node, HashSet<Operation>> operations = tableau.getOperations();
		System.out.println();
		int option = 1;
		for (Node n : operations.keySet()) {
			System.out.println(n);
			for (Operation op : operations.get(n)) {
				ops.add(new Pair<Node, Operation>(n, op));
				System.out.println("\t" + option++ + ". " + op.fullString(n, tableau.checkNextCreatedNode()));
			}
		}
		System.out.println("0. Exit program");
		System.out.println();
		return ops;
	}
	
	private static int readIntFromStdin(String msg) {
		int number = 0;
        try {
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            System.out.print(msg);
            number = Integer.parseInt(in.readLine());
        } catch (Exception e) {System.err.println("\n"+e.toString());}
        return number;
	}
}
