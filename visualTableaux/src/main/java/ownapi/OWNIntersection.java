package ownapi;

public class OWNIntersection extends OWNAxiom {
	private OWNAxiom operand1;
	private OWNAxiom operand2;
	
	public OWNIntersection(OWNAxiom op1, OWNAxiom op2) {
		super();
		this.operand1 = op1;
		this.operand2 = op2;
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
		return (operand1.isLiteral() ? operand1 : "("+operand1+")")
				+ "\u2A05" + 
				(operand2.isLiteral() ? operand2 : "("+operand2+")");
		//return "OWNIntersection [operand1=(" + operand1 + "), operand2=(" + operand2 + ")]";
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}