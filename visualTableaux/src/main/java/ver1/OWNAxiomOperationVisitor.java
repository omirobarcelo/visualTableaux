package ver1;

import java.util.HashSet;

import ownapi.*;
import ver1.Operation.OPERATOR;

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
		operations.add(new Operation(OPERATOR.OR, axiom, axiom.getOperand1()));
		operations.add(new Operation(OPERATOR.OR, axiom, axiom.getOperand2()));
	}

	public void visit(OWNIntersection axiom) {
		// TODO Auto-generated method stub
		operations.add(new Operation(OPERATOR.AND, axiom));
	}

	public void visit(OWNExistential axiom) {
		// TODO Auto-generated method stub
		operations.add(new Operation(OPERATOR.SOME, axiom));
	}

	public void visit(OWNUniversal axiom) {
		// TODO Auto-generated method stub
		operations.add(new Operation(OPERATOR.ONLY, axiom));
	}

}
