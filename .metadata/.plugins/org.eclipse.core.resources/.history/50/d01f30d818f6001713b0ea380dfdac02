package ownapi;

import ver1.util.StringAxiomConstants;

public class OWNExistential extends OWNAxiom{
	/**
	 * Assumed that relations will always be literals
	 */
	private OWNLiteral relation;
	private OWNAxiom operand;
	
	/**
	 * Not null arguments assumed
	 * @param relation
	 * @param op
	 */
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
		return StringAxiomConstants.OP_EXISTENTIAL + relation + "." + (operand.isLiteral() ? operand : "("+operand+")");
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
