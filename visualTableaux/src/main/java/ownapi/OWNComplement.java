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
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}

}
