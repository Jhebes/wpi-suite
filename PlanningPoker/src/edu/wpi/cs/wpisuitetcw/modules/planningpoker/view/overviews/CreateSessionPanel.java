package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.AddSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics.SessionLiveType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ScrollablePanel;

/**
 * Panel for New Session tab.
 * 
 * @author Rob, Ben, Jenny
 */

public class CreateSessionPanel extends JSplitPane {
	final int DEFAULT_DATA_SIZE = 30; // default data size for database entry

	// The right panel holds info about selected requirements
	private final ScrollablePanel rightPanel;
	// The left leftPanel contains reqList, name, and Deadline.
	private final ScrollablePanel leftPanel;

	private final JTextField nameTextField;

	private final JButton btnSaveSession;

	private final JComboBox<SessionLiveType> dropdownType;

	private final JXDatePicker deadlinePicker;
	private final JSpinner pickerDeadlineTime;

	// Constructor for our Create Session Panel
	public CreateSessionPanel() {
		// initialize left and right panel
		rightPanel = new ScrollablePanel();
		leftPanel = new ScrollablePanel();

		// create labels for each data field
		JLabel labelName = new JLabel("Name *");
		JLabel labelDeadline = new JLabel("Deadline");
		JLabel labelDropdownType = new JLabel("Type *");

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
		// TODO check with other people to see what the limit size for each data
		// is
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
		container.setLayout(new GridBagLayout());
		container.add(rightPanel, new GridBagConstraints());

		// setup the layout
		this.setLeftComponent(leftPanel);
		this.setRightComponent(container);
		this.setDividerLocation(180);
	}

	public JTextField getNameTextField() {
		return nameTextField;
	}

	public JButton getBtnSaveSession() {
		return btnSaveSession;
	}

	public JComboBox<SessionLiveType> getDropdownType() {
		return dropdownType;
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
}
