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
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import ownapi.*;
import ownapi.OWNAxiom.AXIOM_TYPE;
import ver1.Operation.OPERATOR;
import ver1.util.*;

/**
 * Generates all the applicable operations of an axiom
 * @author Oriol Miro-BArcelo
 *
 */
public class OWNAxiomOperationVisitor implements OWNAxiomVisitor {
	// Contains all the operations possible from axiom
	private Set<Operation> operations;
	private TreeNode tn;
	private Map<Node, LinkedHashSet<OWNAxiom>> Ln;
	private Map<Pair<Node, Node>, HashSet<OWNAtom>> Lr;
	private Set<NonDeterministicOperation> appliedOperations;
	
	public OWNAxiomOperationVisitor(TreeNode tn, Map<Node, LinkedHashSet<OWNAxiom>> Ln, 
			Map<Pair<Node, Node>, HashSet<OWNAtom>> Lr, 
			Set<NonDeterministicOperation> appliedOperations) {
		operations = new HashSet<Operation>();
		this.tn = tn;
		this.Ln = Ln;
		this.Lr = Lr;
		this.appliedOperations = appliedOperations;
	}
	
	public Set<Operation> getOperations() {
		return operations;
	}
	
	public void visit(OWNAxiom axiom) {
		// TODO Auto-generated method stub
	}

	public void visit(OWNAtom axiom) {
		// TODO Auto-generated method stub
	}

	/**
	 * Only check for BOTTOM operations in complement to avoid
	 * duplicate operations and because probably there are less 
	 * OWNComplement to check than OWNAtom
	 */
	public void visit(OWNComplement axiom) {
		// If BOTTOM not in L(n)
		if (!((HashSet<OWNAxiom>)Ln.get(tn.getData())).contains(OWNAxiom.BOTTOM)) {
			// If L(n) includes ¬axiom
			for (OWNAxiom other : (HashSet<OWNAxiom>)Ln.get(tn.getData())) {
				if (other.isOfType(AXIOM_TYPE.ATOM) && other.equals(axiom.getOperand())) {
					operations.add(new Operation(OPERATOR.BOTTOM, other, axiom));
					break;
				}
			}
		}
	}

	public void visit(OWNConjunction axiom) {
		// If {op1,op2} not included in L(n)
		if (!(((HashSet<OWNAxiom>)Ln.get(tn.getData())).contains(axiom.getOperand1()) && 
				((HashSet<OWNAxiom>)Ln.get(tn.getData())).contains(axiom.getOperand2())))
			operations.add(new Operation(OPERATOR.AND, axiom));
	}

	public void visit(OWNDisjunction axiom) {
		// If {op1,op2} disjunct with L(n)
		if (!(((HashSet<OWNAxiom>)Ln.get(tn.getData())).contains(axiom.getOperand1()) || 
				((HashSet<OWNAxiom>)Ln.get(tn.getData())).contains(axiom.getOperand2()))) {
			// Only add operation if not branded as conflicting operation
			if (!appliedOperations.contains(new NonDeterministicOperation
					(tn.getData(), OPERATOR.OR, axiom, axiom.getOperand1())))
				operations.add(new Operation(OPERATOR.OR, axiom, axiom.getOperand1()));
			if (!appliedOperations.contains(new NonDeterministicOperation
					(tn.getData(), OPERATOR.OR, axiom, axiom.getOperand2())))
				operations.add(new Operation(OPERATOR.OR, axiom, axiom.getOperand2()));
		}
	}

	public void visit(OWNExistential axiom) {
		boolean axiomInL = false;
		if (!Lr.isEmpty()) {
			// Get all Node paired with n that contain the axiom relation
			HashSet<Node> Ys = new HashSet<Node>();
			for (TreeNode child : tn.getChildren()) {
				Pair<Node, Node> p = new Pair<Node, Node>(tn.getData(), child.getData());
				if (Lr.get(p).contains(axiom.getRelation())) {
					Ys.add(p.getSecond());
				}
			}
			// Check if some contain the axiom operand
			for (Node y : Ys) {
				axiomInL |= ((HashSet<OWNAxiom>)Ln.get(y)).contains(axiom.getOperand());
			}
		}
		// If op isn't in any L(y), for all y that L(n,y) contain rel
		if (!axiomInL)
			operations.add(new Operation(OPERATOR.SOME, axiom));
	}

	public void visit(OWNUniversal axiom) {
		if (!Lr.isEmpty()) {
			// Get all Node paired with n that contain the axiom relation
			HashSet<Node> Ys = new HashSet<Node>();
			for (TreeNode child : tn.getChildren()) {
				Pair<Node, Node> p = new Pair<Node, Node>(tn.getData(), child.getData());
				if (Lr.get(p).contains(axiom.getRelation())) {
					Ys.add(p.getSecond());
				}
			}
			// If op isn't in some L(y), add all operations to y
			for (Node y : Ys) {
				if (!((HashSet<OWNAxiom>)Ln.get(y)).contains(axiom.getOperand())) {
					operations.add(new Operation(OPERATOR.ONLY, axiom, y));
				}
			}
		}
	}

}
