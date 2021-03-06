/*
 * Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */

package ver1;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;

import ownapi.*;

/**
 * Casts an OWLAxiom to OWNAxiom
 * @author Oriol Miro-Barcelo
 *
 */
public class OWLClassExpressionCastVisitor implements OWLClassExpressionVisitor {
	// OWNAxiom created from the visit operation
	private OWNAxiom axiom;
	
	public OWNAxiom getAxiom() {
		return axiom;
	}

	public void visit(OWLClass arg0) {
		String atomIRI = arg0.toString();
		axiom = new OWNAtom(atomIRI.substring(1, atomIRI.length()-1));
	}

	public void visit(OWLObjectIntersectionOf arg0) {
		OWLClassExpression[] operands = (arg0.getOperands()).toArray(new OWLClassExpression[0]);
		OWLClassExpressionCastVisitor visitor1 = new OWLClassExpressionCastVisitor();
		operands[0].accept(visitor1);
		OWLClassExpressionCastVisitor visitor2 = new OWLClassExpressionCastVisitor();
		operands[1].accept(visitor2);
		axiom = new OWNConjunction(visitor1.axiom, visitor2.axiom);
	}

	public void visit(OWLObjectUnionOf arg0) {
		OWLClassExpression[] operands = (arg0.getOperands()).toArray(new OWLClassExpression[0]);
		OWLClassExpressionCastVisitor visitor1 = new OWLClassExpressionCastVisitor();
		operands[0].accept(visitor1);
		OWLClassExpressionCastVisitor visitor2 = new OWLClassExpressionCastVisitor();
		operands[1].accept(visitor2);
		axiom = new OWNDisjunction(visitor1.axiom, visitor2.axiom);
	}

	public void visit(OWLObjectComplementOf arg0) {
		String compIRI = arg0.getComplementNNF().toString();
		OWNAtom lit = new OWNAtom(compIRI.substring(1, compIRI.length()-1));
		axiom = new OWNComplement(lit);
	}

	public void visit(OWLObjectSomeValuesFrom arg0) {
		String relIRI = arg0.getProperty().toString();
		OWNAtom relation = new OWNAtom(relIRI.substring(1, relIRI.length()-1));
		OWLClassExpressionCastVisitor visitor = new OWLClassExpressionCastVisitor();
		(arg0.getFiller()).accept(visitor);
		axiom = new OWNExistential(relation, visitor.axiom);
	}

	public void visit(OWLObjectAllValuesFrom arg0) {
		String relIRI = arg0.getProperty().toString();
		OWNAtom relation = new OWNAtom(relIRI.substring(1, relIRI.length()-1));
		OWLClassExpressionCastVisitor visitor = new OWLClassExpressionCastVisitor();
		(arg0.getFiller()).accept(visitor);
		axiom = new OWNUniversal(relation, visitor.axiom);
	}

	public void visit(OWLObjectHasValue arg0) {
		// TODO Auto-generated method stub

	}

	public void visit(OWLObjectMinCardinality arg0) {
		// TODO Auto-generated method stub

	}

	public void visit(OWLObjectExactCardinality arg0) {
		// TODO Auto-generated method stub

	}

	public void visit(OWLObjectMaxCardinality arg0) {
		// TODO Auto-generated method stub

	}

	public void visit(OWLObjectHasSelf arg0) {
		// TODO Auto-generated method stub

	}

	public void visit(OWLObjectOneOf arg0) {
		// TODO Auto-generated method stub

	}

	public void visit(OWLDataSomeValuesFrom arg0) {
		// TODO Auto-generated method stub

	}

	public void visit(OWLDataAllValuesFrom arg0) {
		// TODO Auto-generated method stub

	}

	public void visit(OWLDataHasValue arg0) {
		// TODO Auto-generated method stub

	}

	public void visit(OWLDataMinCardinality arg0) {
		// TODO Auto-generated method stub

	}

	public void visit(OWLDataExactCardinality arg0) {
		// TODO Auto-generated method stub

	}

	public void visit(OWLDataMaxCardinality arg0) {
		// TODO Auto-generated method stub

	}

}
