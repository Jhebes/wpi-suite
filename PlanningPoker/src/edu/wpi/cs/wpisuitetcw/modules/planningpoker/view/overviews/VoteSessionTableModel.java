/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;

/**
 * @author Josh Hebert, Zach Arnold
 *
 */
public class VoteSessionTableModel extends DefaultTableModel{
	private final String[] colNames = {"ID", "Vote"};
	
	public VoteSessionTableModel() {
		setColumnIdentifiers(colNames);
	}
	
	
	
	
	/**
	 * Refreshes the requirements.
	 * 
	 * @param sessions
	 *            The new list of requirements
	 */
	public void refreshVotes(List<PlanningPokerVote> votes) {

		this.setDataVector(null, colNames);
		for (PlanningPokerVote vote : votes) {			
			Object[] row = { 
					vote.getID(),
					vote.getCardValue()
			};
			this.addRow(row);
		}
	}
}



