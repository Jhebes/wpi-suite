package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.AddSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.RetrieveFreePlanningPokerRequirementsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;

/**
 * Panel for New Session tab.
 * 
 * @author Rob, Ben, Jenny
 */
public class CreateSessionPanel extends JSplitPane {

	/** The right panel holds info about selected requirements */
	private final JPanel rightPanel;
	/** The left leftPanel contains reqList, name, and deadline. */
	private final JPanel leftPanel;
	/** The name textbox */
	public JTextField nameField;
	/** The deadline textbox */
	public JTextField deadlineField;
	
	/** Model used for requirements JList */
	DefaultListModel<String> existingRequirementsNames;

	/** list of existing requirements */
	JList<String> existingRequirements;

	/** List of requirements available to this create session tab. */
	private ArrayList<PlanningPokerRequirement> requirements = null;

	/**
	 * Constructs a new Create Session panel
	 */
	public CreateSessionPanel() {
		rightPanel = new JPanel();
		leftPanel = new JPanel();

		RetrieveFreePlanningPokerRequirementsController controller = new RetrieveFreePlanningPokerRequirementsController(
				this);

		existingRequirementsNames = new DefaultListModel<String>();

		// Creates a List view in the UI that displays the dummy list
		existingRequirements = new JList<String>(existingRequirementsNames);
		existingRequirements
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		existingRequirements.setLayoutOrientation(JList.VERTICAL);
		existingRequirements.setVisibleRowCount(-1);

		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		// Creates a Name text field in the leftPane
		leftPanel.add(new JLabel("Name:"));
		nameField = new JTextField(20);
		nameField.setMaximumSize(nameField.getPreferredSize());
		leftPanel.add(nameField);

		// Creates a deadline text field in the leftPane
		leftPanel.add(new JLabel("Deadline:"));
		deadlineField = new JTextField(20);
		deadlineField.setMaximumSize(deadlineField.getPreferredSize());
		leftPanel.add(deadlineField);

		/*// Creates a list of Reqs for the session
		leftPanel.add(new JLabel("Requirements:"));
		leftPanel.add(existingRequirements);

		leftPanel.setAlignmentY(LEFT_ALIGNMENT);
		leftPanel.add(Box.createHorizontalStrut(10));*/
		JButton SaveButton = new JButton("Save");// TODO make big
		rightPanel.add(SaveButton);
		
		// action listener for the SaveButton button
		SaveButton.addActionListener(new AddSessionController(this));
		
		this.setRightComponent(rightPanel);
		this.setLeftComponent(leftPanel);
		this.setDividerLocation(180);

		controller.refreshData();
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
	public void updateRequirements(ArrayList<PlanningPokerRequirement> requirements) {
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
