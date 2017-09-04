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
	
	@Override
	public String toString() {
		return (operand1.isLiteral() ? operand1 : "("+operand1+")")
				+ "\u2A06" + 
				(operand2.isLiteral() ? operand2 : "("+operand2+")");
		//return "OWNUnion [operand1=(" + operand1 + "), operand2=(" + operand2 + ")]";
	}
	
	void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}