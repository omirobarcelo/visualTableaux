package ver1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
//		System.out.println(pairCon_K.getFirst());
//		System.out.println(pairCon_K.getSecond());
		// DEBUG
		
		// Create and initialize tableau
		Tableau tableau = new Tableau(pairCon_K.getSecond());
		tableau.init(pairCon_K.getFirst());
		// DEBUG
//		String[] ops = tableau.printOperations();
//		for (String s : ops)
//			System.out.println(s);
		// DEBUG
				
		// Loop (show status, execute operation, get new status)
		boolean endProgram = false;
		while (!endProgram) {
			showStatus(tableau);
			List<Pair<Node, Operation>> ops = showOperations(tableau);
			int option = readIntFromStdin("\nWhich option do you select? ");
			if (option == 0)
				endProgram = true;
			else {
				// TODO apply operation
				Pair pNode_Op = ops.get(option-1);
				// tableau.apply(pNode_Op.getFirst(), pNode_Op.getSecond());
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

	private static List<Pair<Node, Operation>> showOperations(Tableau tableau) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		int number = 0;
        try {
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            System.out.print(msg);
            number = Integer.parseInt(in.readLine());
        } catch (Exception e) {System.err.println("\n"+e.toString());}
        return number;
	}
}
