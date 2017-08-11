package ownapi;

public class OWNAxiom {
	public enum AXIOM_TYPE {LITERAL, UNION, INTERSECTION, EXISTENTIAL, UNIVERSAL}
	
	protected AXIOM_TYPE type;
	protected OWNAxiom operand1;
	protected OWNAxiom operand2;
	protected String fullName;
	protected OWNLiteral relation;
	
	public OWNAxiom(String fullName) {
		this.fullName = fullName;
	}
	
	public OWNAxiom(OWNAxiom op1, OWNAxiom op2) {
		this.operand1 = op1;
		this.operand2 = op2;
	}
	
	public OWNAxiom(OWNLiteral relation, OWNAxiom op) {
		this.relation = relation;
		this.operand1 = op;
		this.operand2 = op;
	}
		
	public AXIOM_TYPE getType() {
		return type;
	}
	
//	public String getId() {
//		return fullName.split("#")[1];
//	}
	
//	public String getFullName() {
//		return fullName;
//	}

//	public OWNAxiom getOperand1() {
//		return operand1;
//	}
//
//	public OWNAxiom getOperand2() {
//		return operand2;
//	}
	
//	public OWNLiteral getRelation() {
//		return relation;
//	}
	
	public boolean isLiteral() {
		return this.type == AXIOM_TYPE.LITERAL;
	}
	
	public boolean isOfType(AXIOM_TYPE type) {
		return (this.type == type);
	}
}
