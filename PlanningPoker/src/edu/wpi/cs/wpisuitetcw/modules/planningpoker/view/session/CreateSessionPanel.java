/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.CreateSessionPanelController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GetAllDecksController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session.AddSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session.CancelCreateSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics.CardDisplayMode;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics.SessionLiveType;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers.Card;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers.CreateDeckPanel;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

/**
 * A Panel that displays a session's basic information: name, type, description,
 * and deck.
 * 
 * This panel is used to create or edit a session's basic information
 */
public class CreateSessionPanel extends JPanel {
	private static final Date CURRENT_TIME = Calendar.getInstance().getTime();

	private static final String NO_DECK = "No deck";

	private static final String DEFAULT_DECK = "Default";

	private static final long serialVersionUID = 8733539608651885877L;
	
	private static final int DEFAULT_INSETS = 20;
	private static final int DEFAULT_HEIGHT = 26;
	private static final int DEADLINE_DATEPICKER_WIDTH = 170;
	private static final int DROPDOWN_WIDTH = 150;
	private static final int DROPDOWN_DECK_WIDTH = 200;
	private static final int DESCRIPTION_BOX_HEIGHT = 110;
	private static final int GAP_LENGTH_DEADLINE_TO_BOTTOM = 0;
	private static final String REQUIRED_LABEL = "<html><font color='red'>Required field *</font></html>";
	private static final String DEADLINE_ERR_LABEL = "<html><font color='red'>Deadline cannot be in the past</font></html>";
	private static final String CREATE_DECK = "Create new deck";

	// default data size for database entry
	private final int DEFAULT_DATA_SIZE = 30;

	public static final String DISPLAY_MSG = "New Deck";

	private JSplitPane mainPanel;

	// ################ UI Bottom Component #################
	/** The bottom panel contains buttons */
	private JPanel bottomPanel;

	/** Button to save the session */
	private JButton btnSaveSession;

	/** Button to cancel making a session */
	private JButton btnCancel;

	/** Require field label */
	private JLabel labelRequireField;

	// ################ UI Right Component #################
	private CreateDeckPanel deckPanel;

	// ################ UI Left Component #################
	/** The left panel holds components to see the deck */
	private JPanel leftPanel;

	/** Text box to fill session's name in */
	private JLabel labelName;
	private JTextField nameTextField;

	/** Dropdown menu to choose type of session */
	private JLabel labelDropdownType;
	private JComboBox<SessionLiveType> dropdownType;

	/** Text box to fill a session's description in */
	private JScrollPane descriptionFrame;
	private JLabel labelDescriptionBox;
	private JTextArea descriptionBox;

	/** Dropdown menu to choose deck */
	private JLabel labeDeck;
	private JComboBox<String> deckType;

	/** Check box for enabling date and time deadline. */
	private JLabel labelDeadline;
	private JCheckBox cbDeadline;

	/** Deadline date and time picker */
	private JXDatePicker deadlinePicker;
	private JSpinner pickerDeadlineTime;

	/** Label for indicating the deadline is in the past */
	private JLabel labelDeadlineErr;

	// ###################### DATA ########################
	/** Model used for requirements JList */
	DefaultListModel<String> existingRequirementsNames;

	/** list of existing requirements */
	JList<String> existingRequirements;

	/** List of requirements available to this create session tab. */
	private List<PlanningPokerRequirement> requirements = null;

	/** mode for the create new deck panel */
	private CardDisplayMode mode = CardDisplayMode.DISPLAY;

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
	 * constructor is used to create a session. It sets up all graphical
	 * components
	 */
	public CreateSessionPanel() {
		setupLeftPanel();

		// Use display mode since the default deck is displayed by default
		deckPanel = new CreateDeckPanel(CardDisplayMode.DISPLAY);
		deckPanel.displayDefaultDeck();

		setupBottomPanel();

		setupEntirePanel();
	}

	/**
	 * Setup the dropdown menu that contains the available decks of cards
	 */
	public void setupDeckDropdown() {
		deckType.removeAllItems();
		final List<String> deckNames = GetAllDecksController.getInstance()
				.getAllDeckNames();
		for (String name : deckNames) {
			deckType.addItem(name);
		}
	}

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
			final Date date = deadlinePicker.getDate();
			final Date time = (Date) pickerDeadlineTime.getValue();
			final Calendar calendar1 = new GregorianCalendar();
			final Calendar calendar2 = new GregorianCalendar();

			calendar1.setTime(date);
			calendar2.setTime(time);

			// adding the time to date object
			calendar1.set(Calendar.HOUR_OF_DAY,
					calendar2.get(Calendar.HOUR_OF_DAY));
			calendar1.set(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));
			calendar1.set(Calendar.SECOND, calendar2.get(Calendar.SECOND));

			final Date deadline = calendar1.getTime();

			return deadline;
		} else {
			// if the deadline picker isn't enabled don't return a deadline.
			final Date deadline = null;
			return deadline;
		}
	}

	/**
	 * determine what mode the deck panel is in
	 */
	public boolean isInCreateMode() {
		return this.mode.equals(CardDisplayMode.CREATE);
	}

	/**
	 * Return true if the deck panel is in display mode
	 * 
	 * @return Return true if the deck panel is in display mode
	 */
	public boolean isInDisplayMode() {
		return this.mode.equals(CardDisplayMode.DISPLAY);
	}

	/**
	 * Return true if the deck panel is in no deck mode
	 * 
	 * @return Return true if the deck panel is in no deck mode
	 */
	public boolean isInNoDeckMode() {
		return this.mode.equals(CardDisplayMode.NO_DECK);
	}

	/**
	 * determine if users has entered all required values
	 * 
	 * @return true if all values are valid
	 */
	public boolean hasAllValidInputs() {
		// new deck is being created
		if (this.mode.equals(CardDisplayMode.CREATE)) {
			// validate session and deck input
			final boolean isDeckValid = hasValidDeckInputs();
			final boolean isSessionValide = hasValidSessionInputs();

			return isDeckValid && isSessionValide;
		} else {
			// display mode
			return hasValidSessionInputs();
		}

	}

	/**
	 * Determine whether user has entered anything in all required fields
	 * 
	 * @return true if anything is entered; false otherwise
	 */
	private boolean hasValidSessionInputs() {
		// this is to avoid short circuit evaluation
		final boolean nameEntered = isSessionNameEntered();
		final boolean desEntered = isSessionDescriptionEntered();
		final boolean deadlineValid = isDeadlineValid();

		return nameEntered && desEntered && deadlineValid;

	}

	/**
	 * Determine if user has entered all required fields in the deck panel
	 * 
	 * @return true if all inputs are valid
	 */
	private boolean hasValidDeckInputs() {
		final boolean areCardsValid = hasValidCardValues();
		final boolean isNameEntered = this.deckPanel.isDeckNameEntered();
		return areCardsValid && isNameEntered;
	}

	/**
	 * Validate all the values for the entire deck of cards
	 * 
	 * @return true if so; false otherwise
	 */
	private boolean hasValidCardValues() {
		boolean isAllInputValid = true;

		final Map<Integer, Card> cards = this.deckPanel.getCards();

		// check if the deck contains any card
		if (cards.size() == 0) {
			isAllInputValid = false;
		}

		for (Card aCard : cards.values()) {
			if (!aCard.hasValidCardValue()) {
				// aCard.setCardInvalid();
				isAllInputValid = false;
			} else {
				// aCard.setCardValid();
			}
		}
		return isAllInputValid;
	}

	/**
	 * Determine if anything is entered in the session name checkbox
	 * 
	 * @return true if so; false otherwise
	 */
	private boolean isSessionNameEntered() {
		// textbox for session name
		if (this.nameTextField.getText().equals("")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * determine if anything is entered in the textarea for session description
	 * 
	 * @return true if so; false otherwise
	 */
	private boolean isSessionDescriptionEntered() {
		// textarea for session description
		if (this.descriptionBox.getText().equals("")) {
			return false;
		} else {
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
	public void updateRequirements(List<PlanningPokerRequirement> requirements) {
		setRequirements(requirements);
		final List<String> names = new ArrayList<String>();
		for (PlanningPokerRequirement requirement : requirements) {
			names.add(requirement.getName());
		}
		updateRequirementsList(names.toArray(new String[0]));
	}

	/**
	 * 
	 * @return The internal list of planning poker requirements
	 */
	public List<PlanningPokerRequirement> getRequirements() {
		return requirements;
	}

	/**
	 * 
	 * @param requirements
	 *            A list of new planning poker requirements
	 */
	public void setRequirements(List<PlanningPokerRequirement> requirements) {
		this.requirements = requirements;
	}

	/**
	 * Enable the deadline picker and fill in the place holder
	 */
	public void enableDeadlineField() {
		this.deadlinePicker.setEnabled(true);
		this.pickerDeadlineTime.setEnabled(true);
		checkSessionValidation();
	}

	/**
	 * Disable the deadline picker and remote the data
	 */
	public void disableDeadlineField() {
		this.deadlinePicker.setEnabled(false);
		this.pickerDeadlineTime.setEnabled(false);
		labelDeadlineErr.setVisible(false);
	}

	/*
	 * Construct the left panel. Add buttons, text field, dropdown to it
	 */
	private void setupLeftPanel() {
		leftPanel = new JPanel();

		createSessionNameTextbox();
		createSessionTypeDropdown();
		createDeckSelectionDropdown();
		createDescriptionTextbox();
		createDeadlineButtonGroup();

		// Set the default text to the date of creation and the project name
		setupDefaultInitialData();

		addUIComponentsToLeftPanel();
	}

	/*
	 * Put all UI components creating a session to some particular positions on
	 * the left panel MigLayout is a convenient way of creating responsive
	 * layout with Swing
	 */
	private void addUIComponentsToLeftPanel() {
		leftPanel.setLayout(new MigLayout("inset " + DEFAULT_INSETS, "", "[]5[]"));
		leftPanel.setAlignmentX(LEFT_ALIGNMENT);

		// Add session name text field and its label
		leftPanel.add(labelName, "span");
		leftPanel.add(nameTextField, "growx, span, height " + DEFAULT_HEIGHT + "px!");

		// Add labels for the dropdowns of session type and deck to 1 row
		leftPanel.add(labelDropdownType, "width " + DROPDOWN_WIDTH
				+ "px!, left, split2");
		leftPanel.add(labeDeck, "left, wrap");

		// Add the dropdowns of session type and deck to 1 row
		leftPanel.add(dropdownType, "width " + DROPDOWN_WIDTH
				+ "px!, left, split2");
		leftPanel.add(deckType, "width " + DROPDOWN_DECK_WIDTH
				+ "px!, left, wrap");

		// Add the description text field and its label to 2 separate rows
		leftPanel.add(labelDescriptionBox, "wrap");
		leftPanel.add(descriptionFrame, "growx, hmin " + DESCRIPTION_BOX_HEIGHT
				+ "px, span");

		// Add the label for deadline and a check box next to it
		leftPanel.add(labelDeadline, "split3");
		leftPanel.add(cbDeadline);
		leftPanel.add(labelDeadlineErr, "wrap");

		// Add deadline date picker and time picker
		leftPanel.add(deadlinePicker, "split2, "
				+ "width " + DEADLINE_DATEPICKER_WIDTH + "px!, " 
				+ "gapbottom " + GAP_LENGTH_DEADLINE_TO_BOTTOM + "px, " 
				+ "height " + DEFAULT_HEIGHT + "px!");
		leftPanel.add(pickerDeadlineTime, "growx, " 
				+ "gapbottom " + GAP_LENGTH_DEADLINE_TO_BOTTOM + "px, "
				+ "height " + DEFAULT_HEIGHT + "px!, wrap");
	}

	/*
	 * Set up the initial text in the session's name text field
	 */
	private void setupDefaultInitialData() {
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		final String defaultNameDate = sdf.format(new Date());
		final String projectName = ConfigManager.getConfig().getProjectName();
		nameTextField.setText(projectName + " - " + defaultNameDate);
	}

	/*
	 * Create a checkbox, date picker, and time selector for deadline
	 */
	private void createDeadlineButtonGroup() {
		createDeadlineCheckbox();
		createDatePicker();
		createTimeSelector();
	}

	/*
	 * Create time selector
	 */
	private void createTimeSelector() {
		pickerDeadlineTime = new JSpinner(new SpinnerDateModel());
		final JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(
				pickerDeadlineTime, "HH:mm:ss");
		pickerDeadlineTime.setEditor(timeEditor);
		pickerDeadlineTime.setValue(new Date()); // will only show the current
													// time
		pickerDeadlineTime.setEnabled(false);

		// action listener to validate the picker
		pickerDeadlineTime.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				checkSessionValidation();
			}
		});
	}

	/*
	 * Create date picker
	 */
	private void createDatePicker() {
		deadlinePicker = new JXDatePicker();
		deadlinePicker.setDate(CURRENT_TIME);
		deadlinePicker.setFormats(new SimpleDateFormat("MM/dd/yyyy"));
		deadlinePicker.setEnabled(false);

		// dynamically validate deadline
		deadlinePicker.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkSessionValidation();
			}
		});

		// deadline error indicator
		labelDeadlineErr = new JLabel(DEADLINE_ERR_LABEL);
		labelDeadlineErr.setVisible(false);
	}

	/*
	 * Create checkbox for deadline
	 */
	private void createDeadlineCheckbox() {
		labelDeadline = new JLabel("Deadline");
		cbDeadline = new JCheckBox();
		cbDeadline.addItemListener(new CreateSessionPanelController(this));
	}

	/*
	 * Create description box
	 */
	private void createDescriptionTextbox() {
		labelDescriptionBox = new JLabel("Description *");
		descriptionBox = new JTextArea();
		descriptionBox.setLineWrap(true);
		descriptionBox.setWrapStyleWord(true);
		descriptionBox.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		// adds dynamic validation for session description
		addTextInputValidation(descriptionBox);

		// Add scroll bar to the text area. It only appears when needed
		descriptionFrame = new JScrollPane();
		descriptionFrame.setViewportView(descriptionBox);
	}

	/**
	 * Trigger dynamic input validation when the given input is entered in the
	 * given textfield
	 */
	private void addTextInputValidation(JTextComponent element) {
		element.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				checkSessionValidation();
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	/**
	 * enable save button if a session is ready
	 */
	public void checkSessionValidation() {
		if (hasAllValidInputs()) {
			btnSaveSession.setEnabled(true);
		} else {
			btnSaveSession.setEnabled(false);
		}
	}

	/*
	 * Create dropdown to select an existed deck
	 */
	private void createDeckSelectionDropdown() {
		labeDeck = new JLabel("Deck *");
		deckType = new JComboBox<String>();
		this.setupDeckDropdown();
		deckType.setEditable(false);
		deckType.setBackground(Color.WHITE);
		deckType.setSelectedIndex(0);

		// set up listen for creating/displaying a deck of cards
		deckType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final String deckName = String.valueOf(deckType.getSelectedItem());
				if (deckName.equals(CREATE_DECK)) {
					// create mode
					mode = CardDisplayMode.CREATE;
					// create a new deck of cards
					createNewDeck();
				} else if (deckName.equals(DEFAULT_DECK)) {
					// display mode
					mode = CardDisplayMode.DISPLAY;
					// display default deck
					displayDefaultDeck();
				} else if (deckName.equals(NO_DECK)) {
					// no deck mode
					mode = CardDisplayMode.NO_DECK;
					// nothing should be displayed
					displayNoDeck();
				} else {
					// display mode
					mode = CardDisplayMode.DISPLAY;
					// display a selected deck of cards
					try {
						displayDeck(deckName);
					} catch (WPISuiteException e1) {
						e1.printStackTrace();
					}
				}

				// dynamic validation when selection is changed
				// TODO this is somehow not working properly
				checkSessionValidation();
			}
		});
	}

	/*
	 * Create dropdown menu to select type of session
	 */
	private void createSessionTypeDropdown() {
		labelDropdownType = new JLabel("Type *");
		dropdownType = new JComboBox<SessionLiveType>(SessionLiveType.values());
		dropdownType.setEditable(false);
		dropdownType.setBackground(Color.WHITE);
	}

	/*
	 * Create a text box to fill a new session's name in
	 */
	private void createSessionNameTextbox() {
		labelName = new JLabel("Name *");
		labelRequireField = new JLabel(REQUIRED_LABEL);
		nameTextField = new JTextField(DEFAULT_DATA_SIZE);
		// add dynamic validation to session name
		addTextInputValidation(nameTextField);
	}

	/*
	 * Add "Save" button, "Cancel" button, and a label informing the required
	 * fields are not filled to the bottom panel
	 */
	private void setupBottomPanel() {
		// Create Save session button
		btnSaveSession = new JButton("Save");
		btnSaveSession.addActionListener(new AddSessionController(this, false));
		// save button is initially disable
		btnSaveSession.setEnabled(false);

		// Create Cancel create session button
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new CancelCreateSessionController(this));

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new MigLayout("inset 5 " 
												+ DEFAULT_INSETS 
												+ " 5 " 
												+ DEFAULT_INSETS, 
											"", "push[]push"));
		bottomPanel.add(btnSaveSession, "left, width 120px, height " + DEFAULT_HEIGHT + "px!");
		bottomPanel.add(btnCancel, "width 120px, height " + DEFAULT_HEIGHT + "px!");
		bottomPanel.add(labelRequireField, "gapleft 10px, height " + DEFAULT_HEIGHT + "px!");
	}

	/**
	 * invoke a right panel for creating a new deck of cards
	 */
	private void createNewDeck() {
		// new deck panel for creating a deck of cards
		this.deckPanel = new CreateDeckPanel(CardDisplayMode.CREATE, this);

		setupEntirePanel();
		updateUI();
	}

	/**
	 * Displays a previously created deck
	 * 
	 * @param deckName
	 *            Name of the deck to be shown
	 * @throws WPISuiteException
	 */
	private void displayDeck(String deckName) throws WPISuiteException {
		this.deckPanel = new CreateDeckPanel(CardDisplayMode.DISPLAY);
		this.deckPanel.displayDeck(deckName);

		setupEntirePanel();
		updateUI();
	}

	/**
	 * display the default Fibonacci deck
	 */
	private void displayDefaultDeck() {
		// Use display mode since the default deck is displayed by default
		this.deckPanel = new CreateDeckPanel(CardDisplayMode.DISPLAY);
		this.deckPanel.displayDefaultDeck();

		setupEntirePanel();
		updateUI();
	}

	/**
	 * display no card on the deck panel
	 */
	public void displayNoDeck() {
		this.deckPanel = new CreateDeckPanel(CardDisplayMode.NO_DECK);

		setupEntirePanel();
		updateUI();
	}

	/*
	 * Put the left and right panel into a JSplitpane and add this JSplitpane
	 * with bottom panel to the window
	 */
	private void setupEntirePanel() {
		// Remove all the UI Components on the CreateSessionPanel
		removeAll();

		// Put the left and card panel into a JSplitpane
		mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,
				deckPanel);
		// Prevent users resize left panel
		mainPanel.setEnabled(false);

		// Add the mainPanel and bottom panel to the canvas
		this.setLayout(new MigLayout("insets 0"));
		this.add(mainPanel, "dock center");
		this.add(bottomPanel, "dock south, height 45px!");
	}

	/**
	 * getter for the deck panel
	 * 
	 * @return deck panel
	 */
	public CreateDeckPanel getDeckPanel() {
		return this.deckPanel;
	}

	/**
	 * 
	 * @return the checkbox for deadline
	 */
	public JCheckBox getCbDeadline() {
		return cbDeadline;
	}

	/**
	 * validate the deadline
	 * 
	 * @return true if valid, false if the entered deadline is in the past
	 */
	private boolean isDeadlineValid() {
		final Date enteredDate = getDeadline();

		// check if deadline box is checked
		if (!cbDeadline.isSelected()) {
			return true;
		}

		if (enteredDate.after(CURRENT_TIME)) {
			// valid
			labelDeadlineErr.setVisible(false);
			return true;
		} else {
			// invalide
			labelDeadlineErr.setVisible(true);
			return false;
		}
	}
}
