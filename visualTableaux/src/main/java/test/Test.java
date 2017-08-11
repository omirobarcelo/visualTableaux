package test;

import java.io.File;

import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.OWLObjectIntersectionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectUnionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;

import ver1.*;
import ownapi.*;

public class Test {

	public static void main(String[] args) {
//		OWNLiteral lit = new OWNLiteral("a", "test#a");
//		OWNAxiom ax = (OWNAxiom)lit;
//		System.out.println(ax.isLiteral());
//		OWNUnion un = new OWNUnion(null, null);
//		OWNAxiom ax = (OWNAxiom)un;
//		System.out.println(ax.isOfType(AXIOM_TYPE.INTERSECTION));
		
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
		
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		//IRI ontologyIRI = IRI.create("http://protege.stanford.edu/ontologies/pizza/pizza.owl");
		IRI ontologyIRI = IRI.create(new File("ontologies/testTBox.owl"));
		
		try {
			OWLOntology ontology = man.loadOntology(ontologyIRI);
			System.out.println(ontologyIRI.toString());
			System.out.println(ontology.getLogicalAxiomCount() + "\n");
			
			Set<OWLAxiom> axioms = ontology.getAxioms();
			for (OWLAxiom axiom : axioms) {
//				System.out.println(axiom);
//				System.out.println(axiom.getNNF());
//				System.out.println(axiom.getAxiomType());
//				if (axiom.isOfType(AxiomType.DECLARATION)) {
//					OWLDeclarationAxiomImpl declaration = (OWLDeclarationAxiomImpl)axiom;
//					System.out.println(declaration.getIndividualsInSignature());
//					System.out.println(declaration.getEntity());
//					System.out.println(declaration.getSignature());
//				}
				if (axiom.isOfType(AxiomType.SUBCLASS_OF)) {
					System.out.println(axiom.getNNF());
					OWLSubClassOfAxiomImpl subclass = (OWLSubClassOfAxiomImpl)axiom;
					System.out.println(subclass.getSuperClass());
					System.out.println((subclass.getSuperClass()).getClass());
					
					if ((subclass.getSuperClass()).getClass() == OWLObjectIntersectionOfImpl.class) {
						OWLObjectIntersectionOfImpl intersection = (OWLObjectIntersectionOfImpl)subclass.getSuperClass();
						System.out.println(intersection);
						
						System.out.println(intersection.getOperands());
						for (OWLClassExpression operand : intersection.getOperands()) {
							System.out.println(operand.isClassExpressionLiteral());
						}
					}
					
					if ((subclass.getSuperClass()).getClass() == OWLObjectUnionOfImpl.class) {
						OWLObjectUnionOfImpl union = (OWLObjectUnionOfImpl)subclass.getSuperClass();
						System.out.println(union);
						
						System.out.println(union.getOperands());
						for (OWLClassExpression operand : union.getOperands()) {
							System.out.println(operand.isClassExpressionLiteral());
						}
					}
					
					System.out.println("------");
				}
			}
			
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}

}
