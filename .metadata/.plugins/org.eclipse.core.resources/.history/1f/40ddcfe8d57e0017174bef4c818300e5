package ownapi;

public class OWNUniversal extends OWNAxiom{
	private OWNLiteral relation;
	
	public OWNUniversal(OWNLiteral relation, OWNAxiom op) {
		super(op, op);
		super.type = AXIOM_TYPE.UNIVERSAL;
		this.relation = relation;
	}
	
	public OWNLiteral getRelation() {
		return relation;
	}
}