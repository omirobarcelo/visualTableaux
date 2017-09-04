package ownapi;

public class OWNComplement extends OWNAxiom{
	private OWNLiteral operand;
	
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
		return "¬" + operand;
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
