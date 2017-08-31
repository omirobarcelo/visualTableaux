package ownapi;

public class OWNIntersection extends OWNAxiom {
	
	public OWNIntersection(OWNAxiom op1, OWNAxiom op2) {
		super(op1, op2);
		super.type = AXIOM_TYPE.INTERSECTION;
	}

	public OWNAxiom getOperand1() {
		return operand1;
	}

	public OWNAxiom getOperand2() {
		return operand2;
	}

	@Override
	public String toString() {
		return "OWNIntersection [operand1=(" + operand1 + "), operand2=(" + operand2 + ")]";
	}
}