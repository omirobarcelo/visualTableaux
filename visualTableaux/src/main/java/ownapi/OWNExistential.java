package ownapi;

public class OWNExistential extends OWNAxiom{
	
	public OWNExistential(OWNLiteral relation, OWNAxiom op) {
		super(relation, op);
		super.type = AXIOM_TYPE.EXISTENTIAL;
	}
	
	public OWNLiteral getRelation() {
		return relation;
	}
	
	public OWNAxiom getOperand() {
		return operand1;
	}

	@Override
	public String toString() {
		return "\u2203" + relation + "." + (operand1.isLiteral() ? operand1 : "("+operand1+")");
		//return "OWNExistential [relation=(" + relation + "), operand=(" + operand1 + ")]";
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}
