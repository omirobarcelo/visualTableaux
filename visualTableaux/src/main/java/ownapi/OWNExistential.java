package ownapi;

public class OWNExistential extends OWNAxiom{
	private OWNLiteral relation;
	private OWNAxiom operand;
	
	public OWNExistential(OWNLiteral relation, OWNAxiom op) {
		super();
		this.relation = relation;
		this.operand = op;
		super.type = AXIOM_TYPE.EXISTENTIAL;
	}
	
	public OWNLiteral getRelation() {
		return relation;
	}
	
	public OWNAxiom getOperand() {
		return operand;
	}

	@Override
	public String toString() {
		return "\u2203" + relation + "." + (operand.isLiteral() ? operand : "("+operand+")");
		//return "OWNExistential [relation=(" + relation + "), operand=(" + operand1 + ")]";
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}
