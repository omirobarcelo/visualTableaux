package ver1;

import ownapi.OWNAxiom;
import ver1.Operation.OPERATOR;
import ver1.util.StringAxiomConstants;

/**
 * A non deterministic operation is characterized by the node on which
 * is performed, the operation applied (operator), which is the operand, 
 * and which is the result in this specific case
 */
public class NonDeterministicOperation {
	private Node n;
	private OPERATOR operator;
	private OWNAxiom operand;
	private OWNAxiom result;
	
	/**
	 * Non null arguments assumed
	 * @param n
	 * @param op Assumed that the operation represents a type of NDO (i.e.: OR)
	 */
	public NonDeterministicOperation(Node n, Operation op) {
		this.n = n;
		this.operator = op.getOperator();
		this.operand = op.getOperand1();
		this.result = op.getResult();
	}
	
	/**
	 * Non null arguments assumed
	 * @param n
	 * @param operator
	 * @param operand
	 * @param result
	 */
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
	
	/**
	 * As per right now, toString() returns always OR format
	 */
	@Override
	public String toString() {
		return StringAxiomConstants.OP_UNION + "(" + operand + ", " + result + ")" + 
				" " + StringAxiomConstants.ARROW_RIGHT + " " + 
				"L(" + n.getId() + ") " + StringAxiomConstants.UNION + " {" + result + "}";
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
