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

import ownapi.*;

/**
 * Checks if an axiom is contained in an another axiom
 * @author Oriol Miro-Barcelo
 *
 */
public class OWNAxiomContainsVisitor implements OWNAxiomVisitor {
	// OWNAxiom which is going to be checked if it's contained in axiom
	private OWNAxiom searched;
	// True if this contains searched
	private boolean contains;
	
	public OWNAxiomContainsVisitor(OWNAxiom searched) {
		this.searched = searched;
		contains = false;
	}
	
	public boolean isContained() {
		return contains;
	}

	/**
	 * Not implemented since should only be called for specific OWNAxiom
	 */
	@Override
	public void visit(OWNAxiom axiom) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWNAtom axiom) {
		if (axiom.equals(searched))
			contains = true;
	}

	@Override
	public void visit(OWNComplement axiom) {
		if (axiom.equals(searched))
			contains = true;
		else {
			OWNAxiomContainsVisitor visitor = new OWNAxiomContainsVisitor(searched);
			axiom.getOperand().accept(visitor);
			contains = visitor.contains;
		}
	}

	@Override
	public void visit(OWNConjunction axiom) {
		if (axiom.equals(searched))
			contains = true;
		else {
			OWNAxiomContainsVisitor visitor1 = new OWNAxiomContainsVisitor(searched);
			axiom.getOperand1().accept(visitor1);
			OWNAxiomContainsVisitor visitor2 = new OWNAxiomContainsVisitor(searched);
			axiom.getOperand2().accept(visitor2);
			contains = visitor1.contains || visitor2.contains;
		}
	}

	@Override
	public void visit(OWNDisjunction axiom) {
		if (axiom.equals(searched))
			contains = true;
		else {
			OWNAxiomContainsVisitor visitor1 = new OWNAxiomContainsVisitor(searched);
			axiom.getOperand1().accept(visitor1);
			OWNAxiomContainsVisitor visitor2 = new OWNAxiomContainsVisitor(searched);
			axiom.getOperand2().accept(visitor2);
			contains = visitor1.contains || visitor2.contains;
		}
	}

	@Override
	public void visit(OWNExistential axiom) {
		if (axiom.equals(searched))
			contains = true;
		else {
			OWNAxiomContainsVisitor visitor = new OWNAxiomContainsVisitor(searched);
			axiom.getOperand().accept(visitor);
			contains = visitor.contains;
		}
	}

	@Override
	public void visit(OWNUniversal axiom) {
		if (axiom.equals(searched))
			contains = true;
		else {
			OWNAxiomContainsVisitor visitor = new OWNAxiomContainsVisitor(searched);
			axiom.getOperand().accept(visitor);
			contains = visitor.contains;
		}
	}

}
