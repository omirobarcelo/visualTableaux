package test;

import java.io.File;

import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.OWLDeclarationAxiomImpl;

import ver1.Node;

public class Test {

	public static void main(String[] args) {
//		Node first = new Node("a", "test#a");
//		Node secondA = new Node("b1", "test#b1");
//		Node secondB = new Node("b2", "test#b2");
//		Node third = new Node("c", "test#c");
//		
//		first.addSuccessor(secondA);
//		//secondA.setPredecessor(first);
//		first.addSuccessor(secondB);
//		//secondB.setPredecessor(first);
//		secondA.addSuccessor(third);
//		//third.setPredecessor(secondA);
//		
//		System.out.println(first);
//		System.out.println(secondA);
//		System.out.println(secondB);
//		System.out.println(third);
//		System.out.println();
//		
//		Node c = first.clone();
//		c.setId("clone");
//		System.out.println(c);
//		for (Node n : c.getSuccessors())
//			n.setId("changed");
//		System.out.println(c);
//		System.out.println(first);
//		//Node secondC = new Node("b3", "test#b3");
//		//c.addSuccessor(secondC);
//		//System.out.println(first);
//		//System.out.println(c);
//		//System.out.println(secondC);
		
//		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//		//IRI ontologyIRI = IRI.create("http://protege.stanford.edu/ontologies/pizza/pizza.owl");
//		IRI ontologyIRI = IRI.create(new File("ontologies/test3.owl"));
//		
//		try {
//			OWLOntology ontology = man.loadOntology(ontologyIRI);
//			System.out.println(ontologyIRI.toString());
//			System.out.println(ontology.getLogicalAxiomCount() + "\n");
//			
//			Set<OWLAxiom> axioms = ontology.getAxioms();
//			for (OWLAxiom axiom : axioms) {
//				System.out.println(axiom);
//				System.out.println(axiom.getNNF());
//				System.out.println(axiom.getAxiomType());
//				if (axiom.isOfType(AxiomType.DECLARATION)) {
//					OWLDeclarationAxiomImpl declaration = (OWLDeclarationAxiomImpl)axiom;
//					System.out.println(declaration.getIndividualsInSignature());
//					System.out.println(declaration.getEntity());
//					System.out.println(declaration.getSignature());
//				}
//				System.out.println("------");
//			}
//			
//		} catch (OWLOntologyCreationException e) {
//			e.printStackTrace();
//		}
	}

}
