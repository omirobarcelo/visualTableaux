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

import java.util.HashSet;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

import ownapi.*;
import ver1.util.*;

import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;

public class Interpreter {
	/**
	 * Obtains the pair <concept, ontology> in OWNAxiom from a OWLAxiom concept and TBox
	 * The ontology includes the concept
	 * @param oConcept
	 * @param oTbox
	 * @return
	 */
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
