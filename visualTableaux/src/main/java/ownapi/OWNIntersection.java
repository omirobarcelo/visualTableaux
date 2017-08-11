package ownapi;

public class OWNIntersection extends OWNAxiom {
	
	public OWNIntersection(OWNAxiom op1, OWNAxiom op2) {
		super(op1, op2);
		super.type = AXIOM_TYPE.UNION;
	}

}