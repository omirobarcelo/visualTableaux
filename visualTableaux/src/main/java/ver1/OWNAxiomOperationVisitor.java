package ver1;

import java.util.HashMap;
import java.util.HashSet;

import ownapi.*;
import ownapi.OWNAxiom.AXIOM_TYPE;
import ver1.Operation.OPERATOR;

public class OWNAxiomOperationVisitor implements OWNAxiomVisitor {
	private HashSet<Operation> operations;
	private Node n;
	private HashMap<Node, HashSet<OWNAxiom>> Ln;
	private HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> Lr;
	
	public OWNAxiomOperationVisitor() {
		operations = new HashSet<Operation>();
	}
	
	public OWNAxiomOperationVisitor(Node n, HashMap<Node, HashSet<OWNAxiom>> Ln, 
			HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> Lr) {
		operations = new HashSet<Operation>();
		this.n = n;
		this.Ln = Ln;
		this.Lr = Lr;
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
		for (OWNAxiom other : Ln.get(n)) {
			if (other.isOfType(AXIOM_TYPE.LITERAL) && other.equals(axiom.getOperand())) {
				operations.add(new Operation(OPERATOR.BOTTOM, other, axiom));
				break;
			}
		}
	}

	public void visit(OWNIntersection axiom) {
		// TODO Auto-generated method stub
		// If {op1,op2} not included in L(n)
		if (!(Ln.get(n).contains(axiom.getOperand1()) && Ln.get(n).contains(axiom.getOperand2())))
			operations.add(new Operation(OPERATOR.AND, axiom));
	}

	public void visit(OWNUnion axiom) {
		// TODO Auto-generated method stub
		// If {op1,op2} disjunct with L(n)
		if (!(Ln.get(n).contains(axiom.getOperand1()) || Ln.get(n).contains(axiom.getOperand2()))) {
			operations.add(new Operation(OPERATOR.OR, axiom, axiom.getOperand1()));
			operations.add(new Operation(OPERATOR.OR, axiom, axiom.getOperand2()));
		}
	}

	public void visit(OWNExistential axiom) {
		// TODO Auto-generated method stub
		boolean axiomInL = false;
		if (!Lr.isEmpty()) {
			// Get all Node paired with n that contain the axiom relation
			HashSet<Node> Ys = new HashSet<Node>();
			for (Pair<Node, Node> p : Lr.keySet()) {
				if (n.equals(p.getFirst()) && Lr.get(p).contains(axiom.getRelation()))
					Ys.add(p.getSecond());
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
		// TODO Auto-generated method stub
		if (!Lr.isEmpty()) {
			// Get all Node paired with n that contain the axiom relation
			HashSet<Node> Ys = new HashSet<Node>();
			for (Pair<Node, Node> p : Lr.keySet()) {
				if (n.equals(p.getFirst()) && Lr.get(p).contains(axiom.getRelation()))
					Ys.add(p.getSecond());
			}
			// If op isn't in some L(y)
			for (Node y : Ys) {
				if (!Ln.get(y).contains(axiom.getOperand())) {
					operations.add(new Operation(OPERATOR.ONLY, axiom, y));
					break;
				}
			}
		}
	}

}
