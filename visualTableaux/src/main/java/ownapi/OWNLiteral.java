package ownapi;

public class OWNLiteral extends OWNAxiom {
	private String fullName;
	
	public OWNLiteral(String fullName) {
		super();
		this.fullName = fullName;
		super.type = AXIOM_TYPE.LITERAL;
	}

	public String getId() {
		return fullName.split("#")[1];
	}
	
	public String getFullName() {
		return fullName;
	}

	@Override
	public String toString() {
		return getId();
		//return "OWNLiteral [Id=" + getId() + "]";
	}
	
	@Override
	public int hashCode() {
		return type.hashCode() ^ fullName.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof OWNLiteral) {
			OWNLiteral literal = (OWNLiteral)other;
			return type == literal.type && fullName.equals(literal.fullName);
		}
		return false;
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}
