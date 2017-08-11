package ownapi;

public class OWNUnion extends OWNAxiom {
	
	public OWNUnion(OWNAxiom op1, OWNAxiom op2) {
		super(op1, op2);
		super.type = AXIOM_TYPE.UNION;
	}

	public OWNAxiom getOperand1() {
		return operand1;
	}

	public OWNAxiom getOperand2() {
		return operand2;
	}
}