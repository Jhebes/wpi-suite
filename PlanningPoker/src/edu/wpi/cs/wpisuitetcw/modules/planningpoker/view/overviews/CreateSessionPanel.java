package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXDatePicker;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics.SessionLiveType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ScrollablePanel;

/**
 * 
 * @author Rob, Ben, Jenny
 * 
 */

public class CreateSessionPanel extends JSplitPane {
	final int DEFAULT_DATA_SIZE = 30; // default data size for database entry

	// The right panel holds info about selected requirements
	private final ScrollablePanel rightPanel;
	// The left leftPanel contains reqList, name, and Deadline.
	private final ScrollablePanel leftPanel;
	private final JButton addReqButton;
	private final JButton makeActiveButton;

	// Constructor for our Create Session Panel
	public CreateSessionPanel() {
		// initialize left and right panel
		rightPanel = new ScrollablePanel();
		leftPanel = new ScrollablePanel();

		// create labels for each data field
		JLabel labelName = new JLabel("Name *");
		JLabel labelDeadline = new JLabel("Deadline *");
		JLabel labelDropdownType = new JLabel("Type *");

		// create datepicker
		JXDatePicker pickerDeadline = new JXDatePicker();
		pickerDeadline.setDate(Calendar.getInstance().getTime());
		pickerDeadline.setFormats(new SimpleDateFormat("MM/dd/yyyy"));

		// create textfield
		// TODO check with java team to see what the limit size for each data is
		JTextField fieldName = new JTextField(DEFAULT_DATA_SIZE);
		// JTextField fieldDeadline = new JTextField(DEFAULT_DATA_SIZE);

		// create dropdown menu
		JComboBox<SessionLiveType> dropdownType = new JComboBox<SessionLiveType>(
				SessionLiveType.values());
		dropdownType.setEditable(false);
		dropdownType.setBackground(Color.WHITE);

		// create buttons and listeners
		addReqButton = new JButton("<html>Add New <br /> Requirement</html>");
		makeActiveButton = new JButton("<html>Make Active</html>");

		// setup left panel
		// MigLayout is a convenient way of creating responsive layout with
		// Swing
		leftPanel.setLayout(new MigLayout("", "", "shrink"));
		leftPanel.setAlignmentX(LEFT_ALIGNMENT);

		// labels and textfields
		leftPanel.add(labelName, "wrap");
		leftPanel.add(fieldName, "growx, pushx, shrinkx, span, wrap");

		leftPanel.add(labelDeadline, "wrap");
		leftPanel.add(pickerDeadline, "growx, pushx, shrinkx, span, wrap");
		// leftPanel.add(fieldDeadline, "growx, pushx, shrinkx, span, wrap");

		// dropdowns
		leftPanel.add(labelDropdownType, "wrap");
		leftPanel.add(dropdownType, "growx, pushx, shrinkx, span, wrap");

		// buttons
		leftPanel.add(addReqButton, "wrap, span");
		leftPanel.add(makeActiveButton, "wrap, span");

		// Adding UI to the rightPane
		this.setLeftComponent(leftPanel);
		this.setRightComponent(rightPanel);
		this.setDividerLocation(180);
	}

	/**
	 * Method getAddReqButton.
	 * 
	 * @return addReqButton
	 */
	public JButton getAddReqButton() {
		return this.addReqButton;
	}

	/**
	 * Method getMakeActiveButton.
	 * 
	 * @return makeActiveButton
	 */
	public JButton getMakeActiveButton() {
		return this.makeActiveButton;
	}
}
