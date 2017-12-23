package ownapi;

import ver1.util.StringAxiomConstants;

public class OWNIntersection extends OWNAxiom {
	private OWNAxiom operand1;
	private OWNAxiom operand2;
	
	/**
	 * Not null arguments assumed
	 * @param op1
	 * @param op2
	 */
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
				+ StringAxiomConstants.OP_INTERSECTION + 
				(operand2.isLiteral() ? operand2 : "("+operand2+")");
		//return "OWNIntersection [operand1=(" + operand1 + "), operand2=(" + operand2 + ")]";
	}
	
	@Override
	public int hashCode() {
		return type.hashCode() ^ operand1.hashCode() ^ operand2.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof OWNIntersection) {
			OWNIntersection intersection = (OWNIntersection)other;
			return type == intersection.type && operand1.equals(intersection.operand1) && operand2.equals(intersection.operand2);
		}
		return false;
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}