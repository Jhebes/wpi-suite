package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

/**
 * 
 * @author Rob, Ben, Jenny
 * 
 */

public class CreateSessionPanel extends JSplitPane {

	// The right panel holds info about selected requirements
	private final JPanel rightPanel;
	// The left leftPanel contains reqList, name, and Deadline.
	private final JPanel leftPanel;
	private final JButton addReqButton;
	private final JButton makeActiveButton;

	// Constructor for our Create Session Panel
	public CreateSessionPanel() {
		rightPanel = new JPanel();
		leftPanel = new JPanel();
		addReqButton = new JButton("<html>Add New <br /> Requirement</html>");
		makeActiveButton = new JButton("<html>Make Active</html>");

		// //Dummy list of Reqs for the session
		// String dummyReqs[] = {"dummy1", "dummy2"};
		//
		// //Creates a List view in the UI that displays the dummy list
		// JList<String> existingReqsList = new JList<String>(dummyReqs);
		// existingReqsList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		// existingReqsList.setLayoutOrientation(JList.VERTICAL);
		// existingReqsList.setVisibleRowCount(-1);

		// Setting up leftPane
		leftPanel.setLayout(new GridLayout( 6, 1, 1, 20 ) );
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		// Creates a Name text field in the leftPane
		leftPanel.add(new JLabel("Name:"));
		JTextField nameField = new JTextField(20);
		nameField.setMaximumSize(nameField.getPreferredSize());
		leftPanel.add(nameField);

		// Creates a deadline text field in the leftPane
		leftPanel.add(new JLabel("Deadline:"));
		JTextField deadlineField = new JTextField(20);
		deadlineField.setMaximumSize(deadlineField.getPreferredSize());
		leftPanel.add(deadlineField);

		// add buttons to the leftPane
		leftPanel.add(addReqButton);

		leftPanel.add(makeActiveButton);

		// Creates a list of Reqs for the session
		// leftPanel.add(new JLabel("Requirements:"));
		// leftPanel.add(existingReqsList);

		leftPanel.setAlignmentY(LEFT_ALIGNMENT);
		leftPanel.add(Box.createHorizontalStrut(10));

		// Adding UI to the rightPane

		this.setRightComponent(rightPanel);
		this.setLeftComponent(leftPanel);
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
