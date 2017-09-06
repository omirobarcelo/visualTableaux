package ownapi;

public class OWNAxiom {
	public enum AXIOM_TYPE {TOP, BOTTOM, LITERAL, UNION, INTERSECTION, EXISTENTIAL, UNIVERSAL, COMPLEMENT}
	
	public static final OWNAxiom TOP = new OWNAxiom(true);
	public static final OWNAxiom BOTTOM = new OWNAxiom(false);
	
	protected AXIOM_TYPE type;
	
	public OWNAxiom() {}
	
	private OWNAxiom(boolean isTop) {
		this.type = isTop ? AXIOM_TYPE.TOP : AXIOM_TYPE.BOTTOM;
	}
		
	public AXIOM_TYPE getType() {
		return type;
	}
	
	public boolean isLiteral() {
		return this.type == AXIOM_TYPE.LITERAL || this.type == AXIOM_TYPE.COMPLEMENT;
	}
	
	public boolean isOfType(AXIOM_TYPE type) {
		return (this.type == type);
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}
