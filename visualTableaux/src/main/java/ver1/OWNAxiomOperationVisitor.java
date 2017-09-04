package ver1;

import java.util.HashSet;

import ownapi.*;
import ownapi.OWNAxiom.AXIOM_TYPE;
import ver1.Operation.OPERATOR;

public class OWNAxiomOperationVisitor implements OWNAxiomVisitor {
	private HashSet<Operation> operations;
	private HashSet<OWNAxiom> L;
	
	public OWNAxiomOperationVisitor() {
		operations = new HashSet<Operation>();
	}
	
	public OWNAxiomOperationVisitor(HashSet<OWNAxiom> L) {
		operations = new HashSet<Operation>();
		this.L = L;
	}
	
	public HashSet<Operation> getOperations() {
		return operations;
	}
	
	public void visit(OWNAxiom axiom) {
		// TODO Auto-generated method stub
	}

	public void visit(OWNLiteral axiom) {
		// TODO Auto-generated method stub
	}

	public void visit(OWNComplement axiom) {
		// TODO Auto-generated method stub
		for (OWNAxiom other : L) {
			if (other.isOfType(AXIOM_TYPE.LITERAL) && other.equals(axiom.getOperand())) {
				operations.add(new Operation(OPERATOR.BOTTOM, other, axiom));
				break;
			}
		}
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
