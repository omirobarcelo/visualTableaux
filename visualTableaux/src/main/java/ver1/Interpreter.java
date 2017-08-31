package ver1;

import java.util.HashSet;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;

import ownapi.*;
import uk.ac.manchester.cs.owl.owlapi.OWLClassExpressionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectAllValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectComplementOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectIntersectionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectSomeValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectUnionOfImpl;
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
				//concept = transformToOWNAxiom((OWLClassExpressionImpl)subclass.getSuperClass());
				concept = transformToOWNAxiom(subclass.getSuperClass());
			}
		}
		k.add(concept);
		
		// Get all TBox axioms and transform them to OWNAxiom
		for (OWLAxiom axiom : oTbox.getAxioms()) {
			if (axiom.isOfType(AxiomType.SUBCLASS_OF)) {
				// Cast to SubClassOf to get the superclass, which is of ClassExpression
				OWLSubClassOfAxiomImpl subclass = (OWLSubClassOfAxiomImpl)axiom.getNNF();
				//k.add(transformToOWNAxiom((OWLClassExpressionImpl)subclass.getSuperClass()));
				k.add(transformToOWNAxiom(subclass.getSuperClass()));
			}
		}
		
		return new Pair<OWNAxiom, HashSet<OWNAxiom>>(concept, k);
	}
	
	private static OWNAxiom transformToOWNAxiom(OWLClassExpression classExp) {
		if (classExp.isClassExpressionLiteral()) {
			// Check if it is a ComplementOf
			if (classExp.getClass() == OWLObjectComplementOfImpl.class) {
				// Cast to specific class
				OWLObjectComplementOfImpl complement = (OWLObjectComplementOfImpl)classExp;
				// Get complement IRI and return OWNComplement
				String compIRI = complement.getComplementNNF().toString();
				OWNLiteral lit = new OWNLiteral(compIRI.substring(1, compIRI.length()-1));
				return new OWNComplement(lit);
			} else {
				// Get IRI and return OWNLiteral
				String litIRI = classExp.toString();
				return new OWNLiteral(litIRI.substring(1, litIRI.length()-1));
			}
		} else {
			// Switch only allows convertible to int or enum values, so if, else-if it is
			if (classExp.getClass() == OWLObjectUnionOfImpl.class) {
				// Cast to specific class
				OWLObjectUnionOfImpl union = (OWLObjectUnionOfImpl)classExp;
				OWLClassExpression[] operands = (union.getOperands()).toArray(new OWLClassExpression[0]);
				// Transform operands to OWNAxioms (recursive since the operands may be larger axioms)
				OWNAxiom op1 = transformToOWNAxiom(operands[0]);
				OWNAxiom op2 = transformToOWNAxiom(operands[1]);
				// Return OWNUnion
				return new OWNUnion(op1, op2);
			} else if (classExp.getClass() == OWLObjectIntersectionOfImpl.class) {
				// Cast to specific class
				OWLObjectIntersectionOfImpl intersection = (OWLObjectIntersectionOfImpl)classExp;
				OWLClassExpression[] operands = (intersection.getOperands()).toArray(new OWLClassExpression[0]);
				// Transform operands to OWNAxioms (recursive since the operands may be larger axioms)
				OWNAxiom op1 = transformToOWNAxiom(operands[0]);
				OWNAxiom op2 = transformToOWNAxiom(operands[1]);
				// Return OWNIntersection
				return new OWNIntersection(op1, op2);
			} else if (classExp.getClass() == OWLObjectSomeValuesFromImpl.class) {
				// Cast to specific class
				OWLObjectSomeValuesFromImpl existential = (OWLObjectSomeValuesFromImpl)classExp;
				// Get property and transform to OWNLiteral
				String relIRI = existential.getProperty().toString();
				OWNLiteral relation = new OWNLiteral(relIRI.substring(1, relIRI.length()-1));
				// Get filler and transform to OWNAxiom (recursive since it can be a larger axiom)
				OWNAxiom op = transformToOWNAxiom(existential.getFiller());
				// Return OWNExistential
				return new OWNExistential(relation, op);
			} else if (classExp.getClass() == OWLObjectAllValuesFromImpl.class) {
				// Cast to specific class
				OWLObjectAllValuesFromImpl universal = (OWLObjectAllValuesFromImpl)classExp;
				// Get property and transform to OWNLiteral
				String relIRI = universal.getProperty().toString();
				OWNLiteral relation = new OWNLiteral(relIRI.substring(1, relIRI.length()-1));
				// Get filler and transform to OWNAxiom (recursive since it can be a larger axiom)
				OWNAxiom op = transformToOWNAxiom(universal.getFiller());
				// Return OWNUniversal
				return new OWNUniversal(relation, op);
			}
		}
		return null;
	}
}
