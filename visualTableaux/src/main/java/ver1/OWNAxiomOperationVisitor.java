package ver1;

import java.util.HashMap;
import java.util.HashSet;

import ownapi.*;
import ownapi.OWNAxiom.AXIOM_TYPE;
import ver1.Operation.OPERATOR;

public class OWNAxiomOperationVisitor implements OWNAxiomVisitor {
	private HashSet<Operation> operations;
	private TreeNode tn;
	private HashMap<Node, HashSet<OWNAxiom>> Ln;
	private HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> Lr;
	private HashSet<NonDeterministicOperation> conflictingOperations;
	
	public OWNAxiomOperationVisitor(TreeNode tn, HashMap<Node, HashSet<OWNAxiom>> Ln, 
			HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> Lr, 
			HashSet<NonDeterministicOperation> conflictingOperations) {
		operations = new HashSet<Operation>();
		this.tn = tn;
		this.Ln = Ln;
		this.Lr = Lr;
		this.conflictingOperations = conflictingOperations;
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

	// Only check for BOTTOM operations in complement to avoid
	// duplicate operations and because probably there are less 
	// OWNComplement to check than OWNLiteral
	public void visit(OWNComplement axiom) {
		// If BOTTOM not in L(n)
		if (!Ln.get(tn.getData()).contains(OWNAxiom.BOTTOM)) {
			// If L(n) includes ¬axiom
			for (OWNAxiom other : Ln.get(tn.getData())) {
				if (other.isOfType(AXIOM_TYPE.LITERAL) && other.equals(axiom.getOperand())) {
					operations.add(new Operation(OPERATOR.BOTTOM, other, axiom));
					break;
				}
			}
		}
	}

	public void visit(OWNIntersection axiom) {
		// If {op1,op2} not included in L(n)
		if (!(Ln.get(tn.getData()).contains(axiom.getOperand1()) && 
				Ln.get(tn.getData()).contains(axiom.getOperand2())))
			operations.add(new Operation(OPERATOR.AND, axiom));
	}

	public void visit(OWNUnion axiom) {
		// If {op1,op2} disjunct with L(n)
		if (!(Ln.get(tn.getData()).contains(axiom.getOperand1()) || 
				Ln.get(tn.getData()).contains(axiom.getOperand2()))) {
			// Only add operation if not branded as conflicting operation
			if (!conflictingOperations.contains(new NonDeterministicOperation
					(tn.getData(), OPERATOR.OR, axiom, axiom.getOperand1())))
				operations.add(new Operation(OPERATOR.OR, axiom, axiom.getOperand1()));
			if (!conflictingOperations.contains(new NonDeterministicOperation
					(tn.getData(), OPERATOR.OR, axiom, axiom.getOperand2())))
				operations.add(new Operation(OPERATOR.OR, axiom, axiom.getOperand2()));
		}
	}

	public void visit(OWNExistential axiom) {
		boolean axiomInL = false;
		if (!Lr.isEmpty()) {
			// Get all Node paired with n that contain the axiom relation
			HashSet<Node> Ys = new HashSet<Node>();
			for (TreeNode child : tn.getChildren()) {
				Pair<Node, Node> p = new Pair<Node, Node>(tn.getData(), child.getData());
				if (Lr.get(p).contains(axiom.getRelation())) {
					Ys.add(p.getSecond());
				}
			}
			// Check if some contain the axiom operand
			for (Node y : Ys) {
				axiomInL |= Ln.get(y).contains(axiom.getOperand());
			}
		}
		// If op isn't in any L(y), for all y that L(n,y) contain rel
		if (!axiomInL)
			operations.add(new Operation(OPERATOR.SOME, axiom));
	}

	public void visit(OWNUniversal axiom) {
		if (!Lr.isEmpty()) {
			// Get all Node paired with n that contain the axiom relation
			HashSet<Node> Ys = new HashSet<Node>();
			for (TreeNode child : tn.getChildren()) {
				Pair<Node, Node> p = new Pair<Node, Node>(tn.getData(), child.getData());
				if (Lr.get(p).contains(axiom.getRelation())) {
					Ys.add(p.getSecond());
				}
			}
			// If op isn't in some L(y), add all operations to y
			for (Node y : Ys) {
				if (!Ln.get(y).contains(axiom.getOperand())) {
					operations.add(new Operation(OPERATOR.ONLY, axiom, y));
				}
			}
		}
	}

}
