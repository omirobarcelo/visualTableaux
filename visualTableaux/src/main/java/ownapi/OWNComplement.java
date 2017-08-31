package ownapi;

public class OWNComplement extends OWNAxiom{
	
	public OWNComplement(OWNLiteral op) {
		super(op);
		this.type = AXIOM_TYPE.COMPLEMENT;
	}
	
	public OWNLiteral getOperand() {
		return this.operand;
	}

	@Override
	public String toString() {
		return "OWNComplement [operand=(" + operand + ")]";
	}

}
