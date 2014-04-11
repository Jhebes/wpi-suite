/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.CreateSessionPanelController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session.AddSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics.SessionLiveType;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ScrollablePanel;

/**
 * Panel for New Session tab.
 * 
 * @author Rob, Ben, Jenny
 */

public class CreateSessionPanel extends JSplitPane {
	final int DEFAULT_DATA_SIZE = 30; // default data size for database entry
	// final int LARGE_DATA_SIZE = 100;
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
	private final JButton btnCreateNewDeck;
	// dropdown menu
	private final JComboBox<SessionLiveType> dropdownType;
	private final JComboBox<String> deckType;
	// deadline date and time picker
	private final JXDatePicker deadlinePicker;
	private final JSpinner pickerDeadlineTime;

	private JLabel labelName;
	private JLabel labelDescriptionBox;
	private JLabel labeDeck;

	private final JTextArea descriptionBox;
	// check box for enabling date and time deadline.
	private JCheckBox cbDeadline;

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
		labelDescriptionBox = new JLabel("Description *");
		labeDeck = new JLabel("Deck *");

		// JLabel labelExplanation = new JLabel(EXPLANATIONSTRING);

		// checkbox for deadline
		cbDeadline = new JCheckBox();
		cbDeadline.addItemListener(new CreateSessionPanelController(this));

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
		deadlinePicker.setEnabled(false);

		// create time selector
		pickerDeadlineTime = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(
				pickerDeadlineTime, "HH:mm:ss");
		pickerDeadlineTime.setEditor(timeEditor);
		pickerDeadlineTime.setValue(new Date());// will only show the current
		// time
		pickerDeadlineTime.setEnabled(false);

		// create textfield
		nameTextField = new JTextField(DEFAULT_DATA_SIZE);

		descriptionBox = new JTextArea(10, 200);
		descriptionBox.setLineWrap(true);
		descriptionBox.setWrapStyleWord(true);
		descriptionBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		// create dropdown menu
		dropdownType = new JComboBox<SessionLiveType>(SessionLiveType.values());
		dropdownType.setEditable(false);
		dropdownType.setBackground(Color.WHITE);

		deckType = new JComboBox<String>();
		deckType.addItem("Default");
		deckType.addItem("Fibonacci");
		deckType.setEditable(false);
		deckType.setBackground(Color.WHITE);

		// labelDropdownType.setAlignmentX(dropdownType.getAlignmentX());

		// create buttons and listeners
		btnSaveSession = new JButton("Save");
		btnCreateNewDeck = new JButton("Create New Deck");

		// setup right panel
		// MigLayout is a convenient way of creating responsive layout with
		// Swing
		rightPanel.setLayout(new MigLayout("", "[]10[]", "[]5[]"));
		rightPanel.setAlignmentX(LEFT_ALIGNMENT);

		// labels and textfields
		rightPanel.add(labelName, "width 240px, left");
		rightPanel.add(labelDropdownType, "left, wrap");

		rightPanel.add(nameTextField, "width 150px, left");
		rightPanel.add(dropdownType, "width 150px, right, wrap");

		rightPanel.add(labeDeck, "width 150px, left, wrap");
		rightPanel.add(deckType, "width 150px, left, split2");
		rightPanel.add(new JLabel("<html> &nbsp&nbsp&nbsp&nbsp Or</html>"),
				"center, split2"); // Text
		rightPanel.add(btnCreateNewDeck, "width 150px, right, wrap");

		// textarea
		rightPanel.add(labelDescriptionBox, "wrap");
		rightPanel.add(descriptionBox, "width 400px, span, wrap");

		// optional deadline
		rightPanel.add(labelDeadline, "split2");
		rightPanel.add(cbDeadline, "wrap");

		rightPanel.add(deadlinePicker, "width 100px, split2");
		rightPanel.add(pickerDeadlineTime, "width 100px, wrap");

		// buttons
		rightPanel.add(btnSaveSession, "width 150px, left, wrap");

		btnSaveSession.addActionListener(new AddSessionController(this));
		btnCreateNewDeck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventManager.getInstance().createDeck();
			}
		});

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
	 * This returns the description of what user enters
	 * 
	 * @return description for session
	 */
	public JTextArea getDescriptionBox() {
		return descriptionBox;
	}

	/**
	 * return the label for the description textarea
	 * 
	 * @return description label
	 */
	public JLabel getLabelDescriptionBox() {
		return this.labelDescriptionBox;
	}

	/**
	 * This returns the date object of what user enters
	 * 
	 * @return date for the deadline object
	 */
	public Date getDeadline() {
		// checks to see if the deadline picker is enabled, if it is return a
		// deadline.
		if (this.deadlinePicker.isEnabled()) {
			Date date = deadlinePicker.getDate();
			Date time = (Date) pickerDeadlineTime.getValue();
			Calendar calendar1 = new GregorianCalendar();
			Calendar calendar2 = new GregorianCalendar();

			calendar1.setTime(date);
			calendar2.setTime(time);

			// adding the time to date object
			calendar1.set(Calendar.HOUR_OF_DAY,
					calendar2.get(Calendar.HOUR_OF_DAY));
			calendar1.set(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));
			calendar1.set(Calendar.SECOND, calendar2.get(Calendar.SECOND));

			Date deadline = calendar1.getTime();

			return deadline;
		} else {
			// if the deadline picker isn't enabled don't return a deadline.
			Date deadline = null;
			return deadline;
		}
	}

	/**
	 * Determine whether user has entered anything in all required fields
	 * 
	 * @return true if anything is entered; false otherwise
	 */
	public boolean requiredFieldEntered() {
		// this is to avoid short circuit evaluation
		boolean nameEntered = sessionNameEntered();
		boolean desEntered = sessionDescriptionEntered();
		return nameEntered && desEntered;
	}

	/**
	 * Determine if anything is entered in the session name checkbox
	 * 
	 * @return true if so; false otherwise
	 */
	private boolean sessionNameEntered() {
		// textbox for session name
		if (this.nameTextField.getText().equals("")) {
			this.labelName
					.setText("<html>Name * <font color='red'>REQUIRES</font></html>");
			return false;
		} else {
			this.labelName.setText("Name *");
			return true;
		}
	}

	/**
	 * determine if anything is entered in the textarea for session description
	 * 
	 * @return true if so; false otherwise
	 */
	private boolean sessionDescriptionEntered() {
		// textarea for session description
		if (this.descriptionBox.getText().equals("")) {
			this.labelDescriptionBox
					.setText("<html>Description * <font color='red'>REQUIRES</font></html>");
			return false;
		} else {
			this.labelDescriptionBox.setText("Description *");
			return true;
		}
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

	/**
	 * Enable the deadline picker and fill in the place holder
	 */
	public void enableDeadlineField() {
		this.deadlinePicker.setEnabled(true);
		this.pickerDeadlineTime.setEnabled(true);
	}

	/**
	 * Disable the deadline picker and remote the data
	 */
	public void disableDeadlineField() {
		this.deadlinePicker.setEnabled(false);
		this.pickerDeadlineTime.setEnabled(false);
	}

}
