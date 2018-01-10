package ownapi;

import ver1.util.StringAxiomConstants;

public class OWNUnion extends OWNAxiom {
	// To know how many applicable operands have NDOs
	private static final int NUM_OPERANDS = 2;
	
	private OWNAxiom operand1;
	private OWNAxiom operand2;

	/**
	 * Not null arguments assumed
	 * @param op1
	 * @param op2
	 */
	public OWNUnion(OWNAxiom op1, OWNAxiom op2) {
		super();
		this.operand1 = op1;
		this.operand2 = op2;
		super.type = AXIOM_TYPE.UNION;
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
				+ StringAxiomConstants.OP_UNION + 
				(operand2.isLiteral() ? operand2 : "("+operand2+")");
		//return "OWNUnion [operand1=(" + operand1 + "), operand2=(" + operand2 + ")]";
	}
	
	@Override
	public int hashCode() {
		return type.hashCode() ^ operand1.hashCode() ^ operand2.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof OWNUnion) {
			OWNUnion union = (OWNUnion)other;
			boolean same = (operand1.equals(union.operand1) && operand2.equals(union.operand2)) 
					|| (operand2.equals(union.operand1) && operand1.equals(union.operand2));
			return type == union.type && same;
		}
		return false;
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}