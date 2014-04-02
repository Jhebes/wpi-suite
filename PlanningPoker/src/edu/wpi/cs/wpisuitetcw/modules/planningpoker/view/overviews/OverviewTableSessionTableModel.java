/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;

/**
 * TODO: add description
 */
public class OverviewTableSessionTableModel extends DefaultTableModel {

	/**
	 * random number for serializing
	 */
	private static final long serialVersionUID = -7397557876939565129L;

	private static OverviewTableSessionTableModel instance;
	private final String[] colNames = { "ID", "Name", "Deadline", "Status" };

	/**
	 * Constructs a table session for the overview table.
	 */
	private OverviewTableSessionTableModel() {
		setColumnIdentifiers(colNames);
	}

	public static OverviewTableSessionTableModel getInstance() {
		if (instance == null) {
			instance = new OverviewTableSessionTableModel();
		}
		return instance;
	}

	/**
	 * Refreshes the sessions.
	 * 
	 * @param sessions
	 *            The new list of sessions
	 */
	public void refreshSessions(PlanningPokerSession[] sessions) {
		this.setDataVector(null, colNames);
		for (PlanningPokerSession session : sessions) {
			Object[] row = { 
				session.getID(), 
				session.getName(),
				session.getDeadline(), 
				session.getStatus()
			};
			this.addRow(row);
		}
	}

}
