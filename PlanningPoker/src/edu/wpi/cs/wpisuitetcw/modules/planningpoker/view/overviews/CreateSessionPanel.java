package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.RetrieveFreePlanningPokerRequirementsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;

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

	/** List of requirements available to this create session tab. */
	private PlanningPokerRequirement[] requirements = null;

	public PlanningPokerRequirement[] getRequirements() {
		return requirements;
	}

	public void setRequirements(PlanningPokerRequirement[] requirements) {
		this.requirements = requirements;
	}

	// Constructor for our Create Session Panel
	public CreateSessionPanel() {
		rightPanel = new JPanel();
		leftPanel = new JPanel();

		RetrieveFreePlanningPokerRequirementsController controller = 
				new RetrieveFreePlanningPokerRequirementsController(this);
		
		// Dummy list of Reqs for the session
		String dummyReqs[] = { "dummy1", "dummy2" };

		// Creates a List view in the UI that displays the dummy list
		JList<String> existingReqsList = new JList<String>(dummyReqs);
		existingReqsList
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		existingReqsList.setLayoutOrientation(JList.VERTICAL);
		existingReqsList.setVisibleRowCount(-1);

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

		// Creates a list of Reqs for the session
		leftPanel.add(new JLabel("Requirements:"));
		leftPanel.add(existingReqsList);

		leftPanel.setAlignmentY(LEFT_ALIGNMENT);
		leftPanel.add(Box.createHorizontalStrut(10));

		this.setRightComponent(rightPanel);
		this.setLeftComponent(leftPanel);
		this.setDividerLocation(180);
		
		controller.refreshData();
	}

	public void updateRequirements(PlanningPokerRequirement[] requirements) {
		this.requirements = requirements;
	}
}
