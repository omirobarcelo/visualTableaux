package ownapi;

import ver1.util.StringAxiomConstants;

public class OWNComplement extends OWNAxiom{
	/**
	 * Assumed that OWNAxioms will always be created from NNF, so a complement only
	 * complements a literal, not a whole axiom
	 */
	private OWNLiteral operand;
	
	/**
	 * Not null arguments assumed
	 * @param op
	 */
	public OWNComplement(OWNLiteral op) {
		super();
		this.operand = op;
		this.type = AXIOM_TYPE.COMPLEMENT;
	}
	
	public OWNLiteral getOperand() {
		return this.operand;
	}

	@Override
	public String toString() {
		return StringAxiomConstants.OP_COMPLEMENT + operand;
		//return "OWNComplement [operand=(" + operand + ")]";
	}
	
	@Override
	public int hashCode() {
		return type.hashCode() ^ operand.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof OWNComplement) {
			OWNComplement complement = (OWNComplement)other;
			return type == complement.type && operand.equals(complement.operand);
		}
		return false;
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}

}
