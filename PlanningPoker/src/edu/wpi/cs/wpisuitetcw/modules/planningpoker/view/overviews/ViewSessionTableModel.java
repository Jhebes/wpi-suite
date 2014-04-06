/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;

/**
 * @author troyling, Jenny
 *
 */
public class ViewSessionTableModel extends DefaultTableModel{
	private final String[] colNames = {"ID", "Name", "Priority"};
	
	public ViewSessionTableModel() {
		setColumnIdentifiers(colNames);
	}
	
	
	
	
	/**
	 * Refreshes the requirements.
	 * 
	 * @param sessions
	 *            The new list of requirements
	 */
	public void refreshRequirements(List<PlanningPokerRequirement> requirements) {

		this.setDataVector(null, colNames);
		for (PlanningPokerRequirement requirement : requirements) {			
			Object[] row = { 
					requirement.getId(),
					requirement.getName(),
					requirement.getPriority()
			};
			this.addRow(row);
		}
	}
}



