package ver1;

import java.io.File;
import java.util.HashSet;

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
		if (concept == null || tbox == null)
			System.out.println("Something is null");
		
		// Interpreter.read
		Pair<OWNAxiom, HashSet<OWNAxiom>> pairCon_K = Interpreter.read(concept, tbox);
		System.out.println(pairCon_K.getFirst());
		System.out.println(pairCon_K.getSecond());
		
		// Tableau.init
		
		// draw
		
		// show possible operations
	}

}
