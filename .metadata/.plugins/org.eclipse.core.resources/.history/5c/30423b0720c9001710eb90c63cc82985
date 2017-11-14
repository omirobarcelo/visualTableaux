/**
 * Parent class for specific OWNAxiom types
 */
package ownapi;

public class OWNAxiom {
	public enum AXIOM_TYPE {TOP, BOTTOM, LITERAL, UNION, INTERSECTION, EXISTENTIAL, UNIVERSAL, COMPLEMENT}
	
	public static final OWNAxiom TOP = new OWNAxiom(true);
	public static final OWNAxiom BOTTOM = new OWNAxiom(false);
	
	protected AXIOM_TYPE type;
	
	/**
	 * Constructor only created to be able to create related objects
	 */
	public OWNAxiom() {}
	
	/**
	 * Constructor only created to build the constant axioms TOP and BOTTOM
	 * @param isTop true for constant TOP, false for constant BOTTOM
	 */
	private OWNAxiom(boolean isTop) {
		this.type = isTop ? AXIOM_TYPE.TOP : AXIOM_TYPE.BOTTOM;
	}
	
	/**
	 * Assumed that will only be called from related created objects (i.e.: set of OWNAxiom
	 * filled with OWNUnion, OWNLiteral, etc.) 
	 * @return Enum type AXIOM_TYPE given upon object creation
	 */
	public AXIOM_TYPE getType() {
		return type;
	}
	
	/**
	 * @return true if is a literal axiom (OWNLiteral or OWNComplement)
	 */
	public boolean isLiteral() {
		return this.type == AXIOM_TYPE.LITERAL || this.type == AXIOM_TYPE.COMPLEMENT;
	}
	
	/**
	 * @return true if is a literal axiom (OWNLiteral or OWNComplement)
	 */
	public boolean isTop() {
		return this.type == AXIOM_TYPE.TOP;
	}
	
	/**
	 * @return true if is a literal axiom (OWNLiteral or OWNComplement)
	 */
	public boolean isBottom() {
		return this.type == AXIOM_TYPE.BOTTOM;
	}
	
	/**
	 * Checks if OWNAxiom is of specified type
	 * @param type
	 * @return 
	 */
	public boolean isOfType(AXIOM_TYPE type) {
		return (this.type == type);
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		if (this.isTop())
			return "\u22A4";
		else if (this.isBottom())
			return "\u22A5";
		return "";
	}
}
