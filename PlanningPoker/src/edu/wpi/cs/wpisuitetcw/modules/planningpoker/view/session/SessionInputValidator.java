package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session;

import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SessionInputValidator {

	/** create session panel that needs to be validated */
	private final CreateSessionPanel sessionPanel;

	private final JTextField nameTextField;
	private final JTextArea descriptionBox;
	private final JCheckBox cbDeadline;
	private Date deadline;

	/**
	 * Constructor initialize the session panel
	 */
	public SessionInputValidator(CreateSessionPanel sessionPanel) {
		this.sessionPanel = sessionPanel;
		this.nameTextField = sessionPanel.getNameTextField();
		this.descriptionBox = sessionPanel.getDescriptionBox();
		this.cbDeadline = sessionPanel.getCbDeadline();
		this.deadline = sessionPanel.getDeadline();
	}

	/**
	 * validate all inputs
	 */
	public boolean areInputsValid() {
		return false;
	}
}
