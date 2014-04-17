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
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GetAllDecksController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session.AddSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics.SessionLiveType;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

/**
 * A Panel that display a session's basic information: name, type, description,
 * deck This panel is used to create or edit a session's basic information
 */
public class CreateSessionPanel extends JSplitPane {
	private static final int SESSION_NAME_BOX_WIDTH = 400;
	private static final int TYPE_DROPDOWN_WIDTH = 150;

	private static final long serialVersionUID = 8733539608651885877L;
	private final int DEFAULT_DATA_SIZE = 30; // default data size for database
												// entry
	public final String DISPLAY_MSG = "New Deck";

	// ############################## UI Right Component
	// ##############################
	/** The deck panel for viewing and creating a new deck */
	private CreateNewDeckPanel deckPanel;

	// ############################## UI Left Component
	// ##############################
	/** The left panel holds components to see the deck */
	private final JPanel leftPanel;

	/** Text box to fill session's name in */
	private JLabel labelName;
	private final JTextField nameTextField;

	/** Dropdown menu to choose type of session */
	private final JComboBox<SessionLiveType> dropdownType;

	/** Text box to fill a session's description in */
	private JLabel labelDescriptionBox;
	private final JTextArea descriptionBox;

	/** Dropdown menu to choose deck */
	private JLabel labeDeck;
	private final JComboBox<String> deckType;

	/** Check box for enabling date and time deadline. */
	private JCheckBox cbDeadline;

	/** Button to save the session */
	private final JButton btnSaveSession;

	/** Deadline date and time picker */
	private final JXDatePicker deadlinePicker;
	private final JSpinner pickerDeadlineTime;

	// ##################################### DATA
	// #####################################
	/** Model used for requirements JList */
	DefaultListModel<String> existingRequirementsNames;

	/** list of existing requirements */
	JList<String> existingRequirements;

	/** List of requirements available to this create session tab. */
	private ArrayList<PlanningPokerRequirement> requirements = null;

	/**
	 * Constructor to create a Create Session Panel This constructor is used to
	 * edit an existing session.
	 * 
	 * @param session
	 *            A Planning poker session
	 */
	public CreateSessionPanel(PlanningPokerSession session) {
		// Construct a Session Panel without a planning poker session
		this();

		// Display the name and description of a created session
		this.nameTextField.setText(session.getName());
		this.nameTextField.setEnabled(false);
		this.descriptionBox.setText(session.getDescription());
		this.descriptionBox.setEnabled(false);

	}

	/**
	 * Constructor to create a Create Session Panel without a session. This
	 * constructor is used to create a session This constructor sets up all
	 * graphical components
	 */
	public CreateSessionPanel() {
		// Initialize left and deck panel
		leftPanel = new JPanel();
		deckPanel = new CreateNewDeckPanel();

		// Initialize a text box to fill a new session's name in
		labelName = new JLabel("Name *");
		nameTextField = new JTextField(DEFAULT_DATA_SIZE);

		// Create dropdown menu to select type of session
		JLabel labelDropdownType = new JLabel("Type *");
		dropdownType = new JComboBox<SessionLiveType>(SessionLiveType.values());
		dropdownType.setEditable(false);
		dropdownType.setBackground(Color.WHITE);

		// Create dropdown to select an existed deck
		labeDeck = new JLabel("Deck *");
		deckType = new JComboBox<String>();
		this.setupDeckDropdown();
		deckType.setEditable(false);
		deckType.setBackground(Color.WHITE);
		deckType.setSelectedIndex(0);

		// Create description box
		labelDescriptionBox = new JLabel("Description *");
		descriptionBox = new JTextArea(10, 200);
		descriptionBox.setLineWrap(true);
		descriptionBox.setWrapStyleWord(true);
		descriptionBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		// Create checkbox for deadline
		JLabel labelDeadline = new JLabel("Deadline");
		cbDeadline = new JCheckBox();
		cbDeadline.addItemListener(new CreateSessionPanelController(this));

		// Create date picker
		deadlinePicker = new JXDatePicker();
		deadlinePicker.setDate(Calendar.getInstance().getTime());
		deadlinePicker.setFormats(new SimpleDateFormat("MM/dd/yyyy"));
		deadlinePicker.setEnabled(false);

		// Create time selector
		pickerDeadlineTime = new JSpinner(new SpinnerDateModel());
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(
				pickerDeadlineTime, "HH:mm:ss");
		pickerDeadlineTime.setEditor(timeEditor);
		pickerDeadlineTime.setValue(new Date()); // will only show the current
													// time
		pickerDeadlineTime.setEnabled(false);

		// Set the default text to the date of creation and the project name
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String defaultNameDate = sdf.format(new Date());
		String projectName = ConfigManager.getConfig().getProjectName();
		nameTextField.setText(projectName + " - " + defaultNameDate);

		// labelDropdownType.setAlignmentX(dropdownType.getAlignmentX());

		// Create Save session and Create new Deck button
		btnSaveSession = new JButton("Save");
		btnSaveSession.addActionListener(new AddSessionController(this, false));

		// Put all UI components creating a session to the left panel
		// MigLayout is a convenient way of creating responsive layout with
		// Swing
		leftPanel.setLayout(new MigLayout("", "[]10[]", "[]5[]"));
		leftPanel.setAlignmentX(LEFT_ALIGNMENT);

		// labels and textfields
		leftPanel.add(labelName, "span");
		leftPanel.add(nameTextField, "width " + SESSION_NAME_BOX_WIDTH
				+ "px, span");

		leftPanel.add(labelDropdownType, "width " + TYPE_DROPDOWN_WIDTH
				+ "px, left");
		leftPanel.add(labeDeck, "left, wrap");

		leftPanel.add(dropdownType, "width " + TYPE_DROPDOWN_WIDTH + "px");
		leftPanel.add(deckType, "growx, left, wrap");

		// textarea
		leftPanel.add(labelDescriptionBox, "wrap");
		leftPanel.add(descriptionBox, "width 400px, height 110px!, span");

		// optional deadline
		leftPanel.add(labelDeadline, "split2");
		leftPanel.add(cbDeadline, "wrap");
		leftPanel.add(deadlinePicker, "split2, gapbottom 220px");
		leftPanel.add(pickerDeadlineTime, "gapbottom 220px, growx");

		// buttons
		leftPanel.add(btnSaveSession, "growx");

		// setup the layout
		this.setLeftComponent(leftPanel);
		this.setRightComponent(deckPanel);
		this.setDividerLocation(0.25);
		this.setEnabled(false);
	}

	/**
	 * Setup the dropdown menu that contains the available decks of cards
	 */
	public void setupDeckDropdown() {
		deckType.removeAllItems();
		ArrayList<String> deckNames = GetAllDecksController.getInstance()
				.getAllDeckNames();
		for (String name : deckNames) {
			deckType.addItem(name);
		}
	}

	/**
	 * notify createSessionPanel when a new deck is created, so that it updates
	 * the dropdown list for names of decks
	 */
	// public void addCreateDeckListener(final CreateNewDeckPanel deckPanel,
	// final CreateSessionPanel sessionPanel) {
	// sessionPanel.addComponentListener(new ComponentListener() {
	//
	// @Override
	// public void componentShown(ComponentEvent e) {}
	//
	// @Override
	// public void componentResized(ComponentEvent e) {}
	//
	// @Override
	// public void componentMoved(ComponentEvent e) {}
	//
	// @Override
	// public void componentHidden(ComponentEvent e) {
	// sessionPanel.setUpDeckDropdown();
	// ViewEventManager.getInstance().removeTab(deckPanel);
	// }
	// });
	// }

	/**
	 * Returns the description of what user enters
	 * 
	 * @return description for session
	 */
	public JTextArea getDescriptionBox() {
		return descriptionBox;
	}

	/**
	 * Return the label of the description textarea
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
					.setText("<html>Description * <font color='red'>REQUIRED</font></html>");
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
	 * 
	 * @return deck Type pull down menu
	 */
	public JComboBox<String> getDeckType() {
		return deckType;
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
