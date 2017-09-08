package ver1;

import ownapi.OWNAxiom;
import ver1.Operation.OPERATOR;

public class NonDeterministicOperation {
	private Node n;
	private OPERATOR operator;
	private OWNAxiom operand;
	private OWNAxiom result;
	
	public NonDeterministicOperation(Node n, Operation op) {
		this.n = n;
		this.operator = op.getOperator();
		this.operand = op.getOperand1();
		this.result = op.getResult();
	}
	
	public NonDeterministicOperation(Node n, OPERATOR operator, 
			OWNAxiom operand, OWNAxiom result) {
		this.n = n;
		this.operator = operator;
		this.operand = operand;
		this.result = result;
	}

	public Node getNode() {
		return n;
	}
	
	public OPERATOR getOperator() {
		return operator;
	}

	public OWNAxiom getOperand() {
		return operand;
	}

	public OWNAxiom getResult() {
		return result;
	}
	
	@Override
	public String toString() {
		return "\u2A06(" + operand + ", " + result + ")" + 
				" \u27F6 " + 
				"L(" + n.getId() + ") \u222A {" + result + "}";
	}
	
	@Override
	public int hashCode() {
		return n.hashCode() ^ operator.hashCode() ^ operand.hashCode() ^ result.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof NonDeterministicOperation) {
			NonDeterministicOperation ndo = (NonDeterministicOperation)other;
			return n.equals(ndo.n) && operator == ndo.operator &&
					operand.equals(ndo.operand) && result.equals(ndo.result);
		}
		return false;
	}
}
