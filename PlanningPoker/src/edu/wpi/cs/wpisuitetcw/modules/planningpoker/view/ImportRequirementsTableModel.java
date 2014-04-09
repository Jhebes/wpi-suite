package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class ImportRequirementsTableModel extends DefaultTableModel {
	/**
	 * random number for serializing
	 */
	private static final long serialVersionUID = -7397557876939565130L;

	private static ImportRequirementsTableModel instance;
	private final String[] colNames = { "Import?", "Name", "Description" };
	private ArrayList<PlanningPokerRequirement> requirements = null;
	private ArrayList<Requirement> unimportedRequirements = null;

	/**
	 * Constructs a table session for the overview table.
	 */
	private ImportRequirementsTableModel() {
		setColumnIdentifiers(colNames);
	}

	public static ImportRequirementsTableModel getInstance() {
		if (instance == null) {
			instance = new ImportRequirementsTableModel();
		}
		return instance;
	}
	
	public void updateTableModel() {
		if (this.unimportedRequirements == null || this.requirements == null) {
			return;
		}
		this.setDataVector(null, colNames);
		ArrayList<Requirement> existingRequirements = new ArrayList<Requirement>();
		for (PlanningPokerRequirement requirement : requirements) {
			existingRequirements.add(requirement.getInnerRequirement());
		}
		
		for (Requirement unimportedRequirement : this.unimportedRequirements) {
			if (existingRequirements.contains(unimportedRequirement)) {
				continue;
			}
			PlanningPokerRequirement requirement = new PlanningPokerRequirement(unimportedRequirement);
			Object[] row = { 
					false,  // checkbox defaults off
					requirement,  // serializes to its name
					requirement.getInnerRequirement().getDescription()
			};
			this.addRow(row);
		}
	}

	/**
	 * Refreshes the unimported requirements.
	 * 
	 * @param requirements
	 *            The new list of requirements
	 */
	public void refreshUnimportedRequirements(ArrayList<Requirement> requirements) {
		this.unimportedRequirements = requirements;
		updateTableModel();
	}
	
	/**
	 * Refreshes the requirements.
	 * 
	 * @param requirements
	 *            The new list of sessions
	 */
	public void refreshRequirements(ArrayList<PlanningPokerRequirement> requirements) {
		this.requirements = requirements;
		updateTableModel();
	}

	public ArrayList<Requirement> getUnimportedRequirements() {
		return this.unimportedRequirements;
	}

	public void setUnimportedRequirements(
			ArrayList<Requirement> unimportedRequirements) {
		this.unimportedRequirements = unimportedRequirements;
	}

	public ArrayList<PlanningPokerRequirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(ArrayList<PlanningPokerRequirement> requirements) {
		this.requirements = requirements;
	}
}
