package ownapi;

public class OWNAxiom {
	public enum AXIOM_TYPE {LITERAL, UNION, INTERSECTION, EXISTENTIAL, UNIVERSAL}
	
	public AXIOM_TYPE type;
	public OWNAxiom operand1;
	public OWNAxiom operand2;
	
	public OWNAxiom(OWNAxiom op1, OWNAxiom op2) {
		this.operand1 = op1;
		this.operand2 = op2;
	}
		
	public AXIOM_TYPE getType() {
		return type;
	}

	public OWNAxiom getOperand1() {
		return operand1;
	}

	public OWNAxiom getOperand2() {
		return operand2;
	}
	
	public boolean isLiteral() {
		return this.type == AXIOM_TYPE.LITERAL;
	}
	
	public boolean isOfType(AXIOM_TYPE type) {
		return (this.type == type);
	}
}
