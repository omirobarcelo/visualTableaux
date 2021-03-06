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
import ver1.util.StringAxiomConstants;

public class Operation {
	public enum OPERATOR {AND, OR, SOME, ONLY, TOP, BOTTOM}
	
	private OPERATOR operator;
	private OWNAxiom operand1;
	private OWNAxiom other; //complement for BOTTOM and result for OR
	private Node node; // Can be null, when first 2 constructors used
	
	/**
	 * Not null arguments assumed
	 * @param operator
	 * @param operand1
	 */
	public Operation(OPERATOR operator, OWNAxiom operand1) {
		this.operator = operator;
		this.operand1 = operand1;
		// Repeat operand1 to other to avoid null attribute
		this.other = operand1;
	}
	
	/**
	 * Only for BOTTOM and OR operation
	 * other represents the complement for BOTTOM and the result for OR
	 * Not null arguments assumed
	 * @param operator
	 * @param operand1
	 * @param other
	 */
	public Operation(OPERATOR operator, OWNAxiom operand1, OWNAxiom other) {
		this.operator = operator;
		this.operand1 = operand1;
		this.other = other;
	}
	
	/**
	 * Only for ONLY operation
	 * Not null arguments assumed
	 * @param operator
	 * @param operand1
	 * @param node
	 */
	public Operation(OPERATOR operator, OWNAxiom operand1, Node node) {
		this.operator = operator;
		this.operand1 = operand1;
		// Repeat operand1 to other to avoid null attribute
		this.other = operand1;
		this.node = node;
	}

	public OPERATOR getOperator() {
		return operator;
	}
	
	public OWNAxiom getOperand1() {
		return operand1;
	}

	/**
	 * Same return as getResult() for abstractness 
	 * @return
	 */
	public OWNAxiom getOperand2() {
		return other;
	}
	
	/**
	 * Same return as getOperand2() for abstractness
	 * @return
	 */
	public OWNAxiom getResult() {
		return other;
	}
	
	/**
	 * @return May be null
	 */
	public Node getNode() {
		return node;
	}

	
	@Override
	public int hashCode() {
		int baseHashCode = operator.hashCode() ^ operand1.hashCode() ^ other.hashCode();
		int hashCode = (node == null) ? baseHashCode : baseHashCode ^ node.hashCode();
		return hashCode;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Operation) {
			Operation op = (Operation)other;
			// The order of the operands doesn't matter
			boolean sameOperands = (operand1.equals(op.operand1) && this.other.equals(op.other))
					|| (operand1.equals(op.other) && this.other.equals(op.operand1));
			boolean sameNode = (node==null && op.node==null) || 
					(node != null && op.node != null && node.equals(op.node));
			return this.operator == op.operator && sameOperands && sameNode;
		}
		return false;
	}
	
	@Override
	public String toString() {
		switch (operator) {
			case AND: 
				return StringAxiomConstants.OP_CONJUNCTION + "(" + operand1 + ")";
			case OR:
				return StringAxiomConstants.OP_DISJUNCTION + "(" + operand1 + ", " + other + ")";
			case SOME:
				return StringAxiomConstants.OP_EXISTENTIAL + "(" + operand1 + ")";
			case ONLY:
				return StringAxiomConstants.OP_UNIVERSAL + "(" + operand1 + ", " + node.getId() + ")";
			case TOP:
				return StringAxiomConstants.OP_TOP + "(" + operand1 + ")";
			case BOTTOM:
				return StringAxiomConstants.OP_BOTTOM + "(" + operand1 + ", " + other + ")";
		}
		return "";
	}
	
	/**
	 * Returns fully formatted string for the operation
	 * @param x
	 * @param nextCreatedNode id of what would be the next created node
	 * @return
	 */
	public String fullString(Node x, String nextCreatedNode) {
		String base = this.toString() + " " + StringAxiomConstants.ARROW_RIGHT + " "; // or \u27F6
		switch (operator) {
		case AND: {
			OWNConjunction axiom = (OWNConjunction)operand1;
			return base + "L(" + x.getId() + ") " + StringAxiomConstants.UNION + " {" + 
				axiom.getOperand1() + ", " + axiom.getOperand2() + "}";
		}
		case OR:
			return base + "L(" + x.getId() + ") " + StringAxiomConstants.UNION + " {" + other + "}";
		case SOME: {
			OWNExistential axiom = (OWNExistential)operand1;
			return base + "L(" + x.getId() + "," + nextCreatedNode + ") " + StringAxiomConstants.UNION + " {" + 
					axiom.getRelation() + "}, L(" + nextCreatedNode + ") " + StringAxiomConstants.UNION + " {" +
					axiom.getOperand() + "}";
		}
		case ONLY: {
			OWNUniversal axiom = (OWNUniversal)operand1;
			return base + "L(" + node.getId() + ") " + StringAxiomConstants.UNION + " {" + axiom.getOperand() + "}";
		}
		case TOP:
			return base + "L(" + x.getId() + ") " + StringAxiomConstants.UNION + " {" + operand1 + "}";
		case BOTTOM:
			return base + "L(" + x.getId() + ") " + StringAxiomConstants.UNION + " {" + StringAxiomConstants.OP_BOTTOM + "}";
	}
	return "";
	}
}
