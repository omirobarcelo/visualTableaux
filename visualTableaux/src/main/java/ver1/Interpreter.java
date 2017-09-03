package ver1;

import java.util.HashSet;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

import ownapi.*;

import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;

public class Interpreter {
	public static Pair<OWNAxiom, HashSet<OWNAxiom>> read(OWLOntology oConcept, OWLOntology oTbox) {
		OWNAxiom concept = null;
		HashSet<OWNAxiom> k = new HashSet<OWNAxiom>();
		
		// Get concept axiom and transform it to OWNAxiom
		// There should be only one concept
		for (OWLAxiom axiom : oConcept.getAxioms()) {
			if (axiom.isOfType(AxiomType.SUBCLASS_OF)) {
				// Cast to SubClassOf to get the superclass, which is of ClassExpression
				OWLSubClassOfAxiomImpl subclass = (OWLSubClassOfAxiomImpl)axiom.getNNF();
				OWLClassExpressionCastVisitor visitor = new OWLClassExpressionCastVisitor();
				(subclass.getSuperClass()).accept(visitor);
				concept = visitor.getAxiom();
			}
		}
		k.add(concept);
		
		// Get all TBox axioms and transform them to OWNAxiom
		for (OWLAxiom axiom : oTbox.getAxioms()) {
			if (axiom.isOfType(AxiomType.SUBCLASS_OF)) {
				// Cast to SubClassOf to get the superclass, which is of ClassExpression
				OWLSubClassOfAxiomImpl subclass = (OWLSubClassOfAxiomImpl)axiom.getNNF();
				OWLClassExpressionCastVisitor visitor = new OWLClassExpressionCastVisitor();
				(subclass.getSuperClass()).accept(visitor);
				k.add(visitor.getAxiom());
			}
		}
		
		return new Pair<OWNAxiom, HashSet<OWNAxiom>>(concept, k);
	}
	
}
