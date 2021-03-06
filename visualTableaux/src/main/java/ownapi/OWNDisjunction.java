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

package ownapi;

import ver1.util.StringAxiomConstants;

public class OWNDisjunction extends OWNAxiom {
	// To know how many applicable operands have NDOs
	private static final int NUM_OPERANDS = 2;
	
	private OWNAxiom operand1;
	private OWNAxiom operand2;

	/**
	 * Not null arguments assumed
	 * @param op1
	 * @param op2
	 */
	public OWNDisjunction(OWNAxiom op1, OWNAxiom op2) {
		super();
		this.operand1 = op1;
		this.operand2 = op2;
		super.type = AXIOM_TYPE.DISJUNCTION;
	}

	public OWNAxiom getOperand1() {
		return operand1;
	}

	public OWNAxiom getOperand2() {
		return operand2;
	}
	
	public int getNumOperands() {
		return NUM_OPERANDS;
	}
	
	@Override
	public String toString() {
		return (operand1.isLiteral() ? operand1 : "("+operand1+")")
				+ StringAxiomConstants.OP_DISJUNCTION + 
				(operand2.isLiteral() ? operand2 : "("+operand2+")");
		//return "OWNDisjunction [operand1=(" + operand1 + "), operand2=(" + operand2 + ")]";
	}
	
	@Override
	public int hashCode() {
		return type.hashCode() ^ operand1.hashCode() ^ operand2.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof OWNDisjunction) {
			OWNDisjunction disjunction = (OWNDisjunction)other;
			boolean same = (operand1.equals(disjunction.operand1) && operand2.equals(disjunction.operand2)) 
					|| (operand2.equals(disjunction.operand1) && operand1.equals(disjunction.operand2));
			return type == disjunction.type && same;
		}
		return false;
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}