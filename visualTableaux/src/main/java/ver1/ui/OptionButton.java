package ver1.ui;

import javax.swing.JButton;

import ver1.Operation;

/**
 * JButton that holds an operation
 * @author Oriol Miro-Barcelo
 *
 */
public class OptionButton extends JButton {
	
	private Operation op;
	
	public OptionButton(String text, Operation op) {
		super(text);
		this.op = op;
	}
	
	public Operation getOperation() {
		return op;
	}
}
