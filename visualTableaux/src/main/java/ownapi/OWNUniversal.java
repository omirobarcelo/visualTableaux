package ownapi;

public class OWNUniversal extends OWNAxiom{
	
	public OWNUniversal(OWNLiteral relation, OWNAxiom op) {
		super(relation, op);
		super.type = AXIOM_TYPE.UNIVERSAL;
	}
	
	public OWNLiteral getRelation() {
		return relation;
	}
	
	public OWNAxiom getOperand() {
		return operand1;
	}
	
	@Override
	public String toString() {
		return "\u2200" + relation + "." + (operand1.isLiteral() ? operand1 : "("+operand1+")");
		//return "OWNUniversal [relation=(" + relation + "), operand=(" + operand1 + ")]";
	}
}