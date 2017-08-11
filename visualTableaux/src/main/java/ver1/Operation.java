package ver1;

import ownapi.OWNAxiom;

public class Operation {
	public enum OPERATOR {AND, OR, SOME, ONLY, TOP, BOTTOM}
	
	private OPERATOR operator;
	private OWNAxiom operand1;
	private OWNAxiom operand2;
	
	public Operation(OPERATOR operator, OWNAxiom operand1, OWNAxiom operand2) {
		this.operator = operator;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	public OPERATOR getOperator() {
		return operator;
	}

	public OWNAxiom getOperand1() {
		return operand1;
	}

	public OWNAxiom getOperand2() {
		return operand2;
	}
	
}
