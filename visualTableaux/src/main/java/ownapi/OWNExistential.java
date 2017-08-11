package ownapi;

public class OWNExistential extends OWNAxiom{
	private OWNLiteral relation;
	
	public OWNExistential(OWNLiteral relation, OWNAxiom op) {
		super(op, op);
		super.type = AXIOM_TYPE.EXISTENTIAL;
		this.relation = relation;
	}
	
	public OWNLiteral getRelation() {
		return relation;
	}
}
