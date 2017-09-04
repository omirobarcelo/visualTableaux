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
	
	@Override
	public int hashCode() {
		return type.hashCode() ^ relation.hashCode() ^ operand.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof OWNExistential) {
			OWNExistential existential = (OWNExistential)other;
			return type == existential.type && relation.equals(existential.relation) && operand.equals(existential.operand);
		}
		return false;
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}
