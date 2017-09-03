package ownapi;

public class OWNLiteral extends OWNAxiom {
	
	public OWNLiteral(String fullName) {
		super(fullName);
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
}
