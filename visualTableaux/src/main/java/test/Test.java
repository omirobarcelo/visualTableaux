package test;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class Test {

	public static void main(String[] args) {
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		//IRI ontologyIRI = IRI.create("http://protege.stanford.edu/ontologies/pizza/pizza.owl");
		IRI ontologyIRI = IRI.create(new File("ontologies/hw.owl"));
		
		try {
			OWLOntology ontology = man.loadOntology(ontologyIRI);
			System.out.println(ontologyIRI.toString());
			System.out.println(ontology.getLogicalAxiomCount());
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}

}
