package ver1;

import ownapi.OWNAxiom;

public class Operation {
	public enum OPERATOR {AND, OR, SOME, ONLY, TOP, BOTTOM}
	
	private OPERATOR operator;
	private OWNAxiom operand1;
	private OWNAxiom other; //operand2 for BOTTOM and result for OR
//	private OWNAxiom result1;
//	private OWNAxiom result2;
	
	public Operation(OPERATOR operator, OWNAxiom operand1) {
		this.operator = operator;
		this.operand1 = operand1;
		this.other = operand1;
	}
	
	// Only for BOTTOM and OR operation
	public Operation(OPERATOR operator, OWNAxiom operand1, OWNAxiom other) {
		this.operator = operator;
		this.operand1 = operand1;
		this.other = other;
	}
	

	public OPERATOR getOperator() {
		return operator;
	}
	
	public OWNAxiom getOperand1() {
		return operand1;
	}

	public OWNAxiom getOperand2() {
		return other;
	}
	
	public OWNAxiom getResult() {
		return other;
	}

//	public OWNAxiom getResult1() {
//		return result1;
//	}
//
//	public OWNAxiom getResult2() {
//		return result2;
//	}
	
	@Override
	public int hashCode() {
		return operator.hashCode() ^ operand1.hashCode() ^ other.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Operation) {
			Operation op = (Operation)other;
			boolean sameOperands = (operand1.equals(op.operand1) && other.equals(op.other))
					|| (operand1.equals(op.other) && other.equals(op.operand1));
			return this.operator == op.operator && sameOperands;
		}
		return false;
	}
	
	// TODO override toString
	@Override
	public String toString() {
		switch (operator) {
			case AND: 
				return "\u2A05(" + operand1 + ")";
			case OR:
				return "\u2A06(" + operand1 + ", " + other + ")";
			case SOME:
				return "\u2203(" + operand1 + ")";
			case ONLY:
				return "\u2200(" + operand1 + ")";
			case TOP:
				return "\u22A4(" + operand1 + ")";
			case BOTTOM:
				return "\u22A5(" + operand1 + ", " + other + ")";
		}
		return "";
	}
}
