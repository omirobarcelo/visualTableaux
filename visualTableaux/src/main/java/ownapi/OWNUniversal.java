package ownapi;

public class OWNUniversal extends OWNAxiom{
	private OWNLiteral relation;
	private OWNAxiom operand;
	
	public OWNUniversal(OWNLiteral relation, OWNAxiom op) {
		super();
		this.relation = relation;
		this.operand = op;
		super.type = AXIOM_TYPE.UNIVERSAL;
	}
	
	public OWNLiteral getRelation() {
		return relation;
	}
	
	public OWNAxiom getOperand() {
		return operand;
	}
	
	@Override
	public String toString() {
		return "\u2200" + relation + "." + (operand.isLiteral() ? operand : "("+operand+")");
		//return "OWNUniversal [relation=(" + relation + "), operand=(" + operand1 + ")]";
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}