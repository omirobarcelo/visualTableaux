package ver1;

import java.util.HashSet;

import ownapi.*;

public class OWNAxiomOperationVisitor implements OWNAxiomVisitor {
	private HashSet<Operation> operations;
	
	public OWNAxiomOperationVisitor() {
		operations = new HashSet<Operation>();
	}
	
	public HashSet<Operation> getOperations() {
		return operations;
	}
	
	public void visit(OWNAxiom axiom) {
		// TODO Auto-generated method stub
	}

	public void visit(OWNLiteral axiom) {
		// TODO Auto-generated method stub
		// The operations related to OWNLiteral are BOTTOM operations
		// Since it's necessary to check L for it, not obtained from visitor
	}

	public void visit(OWNComplement axiom) {
		// TODO Auto-generated method stub
		// The operations related to OWNComplement are BOTTOM operations
		// Since it's necessary to check L for it, not obtained from visitor
	}

	public void visit(OWNUnion axiom) {
		// TODO Auto-generated method stub
		System.out.println("union");
	}

	public void visit(OWNIntersection axiom) {
		// TODO Auto-generated method stub
		System.out.println("intersection");
	}

	public void visit(OWNExistential axiom) {
		// TODO Auto-generated method stub
		System.out.println("existential");
	}

	public void visit(OWNUniversal axiom) {
		// TODO Auto-generated method stub
		System.out.println("universal");
	}

}
