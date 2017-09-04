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
	
	@Override
	public int hashCode() {
		return type.hashCode() ^ relation.hashCode() ^ operand.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof OWNUniversal) {
			OWNUniversal universal = (OWNUniversal)other;
			return type == universal.type && relation.equals(universal.relation) && operand.equals(universal.operand);
		}
		return false;
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}