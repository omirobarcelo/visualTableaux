package ver1;

import ownapi.*;

public class Operation {
	public enum OPERATOR {AND, OR, SOME, ONLY, TOP, BOTTOM}
	
	private OPERATOR operator;
	private OWNAxiom operand1;
	private OWNAxiom other; //operand2 for BOTTOM and result for OR
	private Node node;
	
	public Operation(OPERATOR operator, OWNAxiom operand1) {
		this.operator = operator;
		this.operand1 = operand1;
		this.other = operand1;
	}
	
	// Only for BOTTOM and OR operation
	public Operation(OPERATOR operator, OWNAxiom operand1, OWNAxiom other) {
		this.operator = operator;
		this.operand1 = operand1;
		this.other = other;
	}
	
	// Only for ONLY operation
	public Operation(OPERATOR operator, OWNAxiom operand1, Node node) {
		this.operator = operator;
		this.operand1 = operand1;
		this.node = node;
	}

	public OPERATOR getOperator() {
		return operator;
	}
	
	public OWNAxiom getOperand1() {
		return operand1;
	}

	public OWNAxiom getOperand2() {
		return other;
	}
	
	public OWNAxiom getResult() {
		return other;
	}
	
	public Node getNode() {
		return node;
	}

	
	@Override
	public int hashCode() {
		int baseHashCode = operator.hashCode() ^ operand1.hashCode() ^ other.hashCode();
		int hashCode = (node == null) ? baseHashCode : baseHashCode ^ node.hashCode();
		return hashCode;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Operation) {
			Operation op = (Operation)other;
			boolean sameOperands = (operand1.equals(op.operand1) && other.equals(op.other))
					|| (operand1.equals(op.other) && other.equals(op.operand1));
			boolean sameNode = (node==null && op.node==null) || 
					(node != null && op.node != null && node.equals(op.node));
			return this.operator == op.operator && sameOperands && sameNode;
		}
		return false;
	}
	
	@Override
	public String toString() {
		switch (operator) {
			case AND: 
				return "\u2A05(" + operand1 + ")";
			case OR:
				return "\u2A06(" + operand1 + ", " + other + ")";
			case SOME:
				return "\u2203(" + operand1 + ")";
			case ONLY:
				return "\u2200(" + operand1 + ", " + node.getId() + ")";
			case TOP:
				return "\u22A4(" + operand1 + ")";
			case BOTTOM:
				return "\u22A5(" + operand1 + ", " + other + ")";
		}
		return "";
	}
	
	public String fullString(Node x, String nextCreatedNode) {
		String base = this.toString() + " \u27F6 ";
		switch (operator) {
		case AND: {
			OWNIntersection axiom = (OWNIntersection)operand1;
			return base + "L(" + x.getId() + ") \u222A {" + 
				axiom.getOperand1() + ", " + axiom.getOperand2() + "}";
		}
		case OR:
			return base + "L(" + x.getId() + ") \u222A {" + other + "}";
		case SOME: {
			OWNExistential axiom = (OWNExistential)operand1;
			return base + "L(" + x.getId() + "," + nextCreatedNode + ") \u222A {" + 
					axiom.getRelation() + "}, L(" + nextCreatedNode + ") \u222A {" +
					axiom.getOperand() + "}";
		}
		case ONLY: {
			OWNUniversal axiom = (OWNUniversal)operand1;
			return base + "L(" + node + ") \u222A {" + axiom.getOperand() + "}";
		}
		case TOP:
			return base + "L(" + x.getId() + ") \u222A {" + operand1 + "}";
		case BOTTOM:
			return base + "L(" + x.getId() + ") \u222A {\u22A5}";
	}
	return "";
	}
}
