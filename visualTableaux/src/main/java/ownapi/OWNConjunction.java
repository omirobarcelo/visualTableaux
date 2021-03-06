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

public class OWNConjunction extends OWNAxiom {
	private OWNAxiom operand1;
	private OWNAxiom operand2;
	
	/**
	 * Not null arguments assumed
	 * @param op1
	 * @param op2
	 */
	public OWNConjunction(OWNAxiom op1, OWNAxiom op2) {
		super();
		this.operand1 = op1;
		this.operand2 = op2;
		super.type = AXIOM_TYPE.CONJUNCTION;
	}

	public OWNAxiom getOperand1() {
		return operand1;
	}

	public OWNAxiom getOperand2() {
		return operand2;
	}

	@Override
	public String toString() {
		return (operand1.isLiteral() ? operand1 : "("+operand1+")")
				+ StringAxiomConstants.OP_CONJUNCTION + 
				(operand2.isLiteral() ? operand2 : "("+operand2+")");
		//return "OWNConjunction [operand1=(" + operand1 + "), operand2=(" + operand2 + ")]";
	}
	
	@Override
	public int hashCode() {
		return type.hashCode() ^ operand1.hashCode() ^ operand2.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof OWNConjunction) {
			OWNConjunction conjunction = (OWNConjunction)other;
			boolean same = (operand1.equals(conjunction.operand1) && operand2.equals(conjunction.operand2)) 
					|| (operand2.equals(conjunction.operand1) && operand1.equals(conjunction.operand2));
			return type == conjunction.type && same;
		}
		return false;
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}