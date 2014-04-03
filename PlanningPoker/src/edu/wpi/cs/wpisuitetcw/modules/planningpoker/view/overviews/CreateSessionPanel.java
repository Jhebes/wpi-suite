package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.AddSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics.SessionLiveType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ScrollablePanel;

/**
 * Panel for New Session tab.
 * 
 * @author Rob, Ben, Jenny
 */

public class CreateSessionPanel extends JSplitPane {
	final int DEFAULT_DATA_SIZE = 30; // default data size for database entry
	private final String EXPLANATIONSTRING = "A planning poker session (game) allows user to "
			+ "select one or more requirements for estimation for "
			+ "the team to estimate. A deadline for submission is optional. "
			+ "This will let the team reach a consensus on the amount of effort it will take to realize the requirements.";

	// The right panel holds info about selected requirements
	private final ScrollablePanel rightPanel;
	// The left leftPanel contains reqList, name, and Deadline.
	private final ScrollablePanel leftPanel;
	// name of the session
	private final JTextField nameTextField;
	// save button for the panel
	private final JButton btnSaveSession;
	// dropdown menu
	private final JComboBox<SessionLiveType> dropdownType;
	// deadline date and time picker
	private final JXDatePicker deadlinePicker;
	private final JSpinner pickerDeadlineTime;
	
	private JLabel labelName;

	/** Model used for requirements JList */
	DefaultListModel<String> existingRequirementsNames;

	/** list of existing requirements */
	JList<String> existingRequirements;

	/** List of requirements available to this create session tab. */
	private ArrayList<PlanningPokerRequirement> requirements = null;

	// Constructor for our Create Session Panel
	public CreateSessionPanel() {
		// initialize left and right panel
		rightPanel = new ScrollablePanel();
		leftPanel = new ScrollablePanel();

		// create labels for each data field
		labelName = new JLabel("Name *");
		JLabel labelDeadline = new JLabel("Deadline");
		JLabel labelDropdownType = new JLabel("Type *");
		// JLabel labelExplanation = new JLabel(EXPLANATIONSTRING);

		// text area
		JTextArea textAreaExp = new JTextArea(5, 15);
		textAreaExp.setText(EXPLANATIONSTRING);
		textAreaExp.setWrapStyleWord(true);
		textAreaExp.setLineWrap(true);
		textAreaExp.setBorder(BorderFactory.createEmptyBorder());
		textAreaExp.setOpaque(false);
		textAreaExp.setFocusable(false);
		textAreaExp.setEditable(false);

		// create date picker
		deadlinePicker = new JXDatePicker();
		deadlinePicker.setDate(Calendar.getInstance().getTime());
		deadlinePicker.setFormats(new SimpleDateFormat("MM/dd/yyyy"));

		// create time selector
		pickerDeadlineTime = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(
				pickerDeadlineTime, "HH:mm:ss");
		pickerDeadlineTime.setEditor(timeEditor);
		pickerDeadlineTime.setValue(new Date()); // will only show the current
													// time

		// create textfield
		nameTextField = new JTextField(DEFAULT_DATA_SIZE);
		// JTextField fieldDeadline = new JTextField(DEFAULT_DATA_SIZE);

		// create dropdown menu
		dropdownType = new JComboBox<SessionLiveType>(SessionLiveType.values());
		dropdownType.setEditable(false);
		dropdownType.setBackground(Color.WHITE);

		// create buttons and listeners
		btnSaveSession = new JButton("Save");

		// setup right panel
		// MigLayout is a convenient way of creating responsive layout with
		// Swing
		rightPanel.setLayout(new MigLayout("", "", "shrink"));
		rightPanel.setAlignmentX(LEFT_ALIGNMENT);

		// labels and textfields
		rightPanel.add(labelName, "wrap");
		rightPanel.add(nameTextField, "width 150px, left, wrap");

		rightPanel.add(labelDeadline, "wrap");
		rightPanel.add(deadlinePicker, "width 100px");

		rightPanel.add(pickerDeadlineTime, "width 100px, wrap");
		// leftPanel.add(fieldDeadline, "growx, pushx, shrinkx, span, wrap");

		// dropdowns
		rightPanel.add(labelDropdownType, "wrap");
		rightPanel.add(dropdownType, "width 150px, left, wrap");

		// buttons
		rightPanel.add(btnSaveSession, "width 150px, left, wrap");

		btnSaveSession.addActionListener(new AddSessionController(this));

		// center the container
		JPanel container = new JPanel();
		// container.setLayout(new GridBagLayout());
		// container.add(rightPanel, new GridBagConstraints());
		container.add(rightPanel);

		// add the label to the left panel
		leftPanel.add(textAreaExp);

		// setup the layout
		this.setLeftComponent(leftPanel);
		this.setRightComponent(container);
		this.setDividerLocation(180);
		this.setEnabled(false);
	}

	/**
	 * This returns the date object of what user enters
	 * 
	 * @return date for the deadline object
	 */
	public Date getDeadline() {
		Date date = deadlinePicker.getDate();
		Date time = (Date) pickerDeadlineTime.getValue();
		Calendar calendar1 = new GregorianCalendar();
		Calendar calendar2 = new GregorianCalendar();

		calendar1.setTime(date);
		calendar2.setTime(time);

		// adding the time to date object
		calendar1
				.set(Calendar.HOUR_OF_DAY, calendar2.get(Calendar.HOUR_OF_DAY));
		calendar1.set(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));
		calendar1.set(Calendar.SECOND, calendar2.get(Calendar.SECOND));

		Date deadline = calendar1.getTime();
		return deadline;
	}

	/**
	 * Determine whether user has entered anything in the panel Name of the
	 * session if the only thing to be entered now
	 * 
	 * @return true if anything is entered; false otherwise
	 */
	public boolean anythingEntered() {
		if (!(nameTextField.getText().equals(""))) {
			return true;
		}
		return false;
	}

	/**
	 * Return the name of the session
	 * 
	 * @return name of session
	 */
	public JTextField getNameTextField() {
		return nameTextField;
	}
	
	/**
	 * Return the name JLabel
	 * 
	 * @return the name JLabel
	 */
		public JLabel getLabelName() {
		return this.labelName;
	}
	

	/**
	 * Return the save button
	 * 
	 * @return save button
	 */
	public JButton getBtnSaveSession() {
		return btnSaveSession;
	}

	/**
	 * Return the dropdown menu for selecting the type
	 * 
	 * @return dropdown menu for session type
	 */
	public JComboBox<SessionLiveType> getDropdownType() {
		return dropdownType;
	}

	/**
	 * Updates the requirement list model with a new list of names.
	 * 
	 * @param names
	 *            The new list of names
	 */
	public void updateRequirementsList(String[] names) {
		existingRequirementsNames.removeAllElements();
		for (String name : names) {
			existingRequirementsNames.addElement(name);
		}
	}

	/**
	 * Updates internal list of requirements as well as the model for the list.
	 * 
	 * @param requirements
	 *            The list of new requirements
	 */
	public void updateRequirements(
			ArrayList<PlanningPokerRequirement> requirements) {
		setRequirements(requirements);
		ArrayList<String> names = new ArrayList<String>();
		for (PlanningPokerRequirement requirement : requirements) {
			names.add(requirement.getName());
		}
		updateRequirementsList(names.toArray(new String[0]));
	}

	/**
	 * 
	 * @return The internal list of planning poker requirements
	 */
	public ArrayList<PlanningPokerRequirement> getRequirements() {
		return requirements;
	}

	/**
	 * 
	 * @param requirements
	 *            A list of new planning poker requirements
	 */
	public void setRequirements(ArrayList<PlanningPokerRequirement> requirements) {
		this.requirements = requirements;
	}
}
