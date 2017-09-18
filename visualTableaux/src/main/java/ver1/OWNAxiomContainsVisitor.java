package ver1;

import ownapi.OWNAxiom;
import ownapi.OWNAxiomVisitor;
import ownapi.OWNComplement;
import ownapi.OWNExistential;
import ownapi.OWNIntersection;
import ownapi.OWNLiteral;
import ownapi.OWNUnion;
import ownapi.OWNUniversal;

public class OWNAxiomContainsVisitor implements OWNAxiomVisitor {
	// OWNAxiom which is going to be checked if it's contained in axiom
	private OWNAxiom searched;
	// True if this contains searched
	private boolean contains;
	
	public OWNAxiomContainsVisitor(OWNAxiom searched) {
		this.searched = searched;
		contains = false;
	}
	
	public boolean isContained() {
		return contains;
	}

	/**
	 * Not implemented since should only be called for specific OWNAxiom
	 */
	@Override
	public void visit(OWNAxiom axiom) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OWNLiteral axiom) {
		if (axiom.equals(searched))
			contains = true;
	}

	@Override
	public void visit(OWNComplement axiom) {
		if (axiom.equals(searched))
			contains = true;
		else {
			OWNAxiomContainsVisitor visitor = new OWNAxiomContainsVisitor(searched);
			axiom.getOperand().accept(visitor);
			contains = visitor.contains;
		}
	}

	@Override
	public void visit(OWNIntersection axiom) {
		if (axiom.equals(searched))
			contains = true;
		else {
			OWNAxiomContainsVisitor visitor1 = new OWNAxiomContainsVisitor(searched);
			axiom.getOperand1().accept(visitor1);
			OWNAxiomContainsVisitor visitor2 = new OWNAxiomContainsVisitor(searched);
			axiom.getOperand2().accept(visitor2);
			contains = visitor1.contains || visitor2.contains;
		}
	}

	@Override
	public void visit(OWNUnion axiom) {
		if (axiom.equals(searched))
			contains = true;
		else {
			OWNAxiomContainsVisitor visitor1 = new OWNAxiomContainsVisitor(searched);
			axiom.getOperand1().accept(visitor1);
			OWNAxiomContainsVisitor visitor2 = new OWNAxiomContainsVisitor(searched);
			axiom.getOperand2().accept(visitor2);
			contains = visitor1.contains || visitor2.contains;
		}
	}

	@Override
	public void visit(OWNExistential axiom) {
		if (axiom.equals(searched))
			contains = true;
		else {
			OWNAxiomContainsVisitor visitor = new OWNAxiomContainsVisitor(searched);
			axiom.getOperand().accept(visitor);
			contains = visitor.contains;
		}
	}

	@Override
	public void visit(OWNUniversal axiom) {
		if (axiom.equals(searched))
			contains = true;
		else {
			OWNAxiomContainsVisitor visitor = new OWNAxiomContainsVisitor(searched);
			axiom.getOperand().accept(visitor);
			contains = visitor.contains;
		}
	}

}
