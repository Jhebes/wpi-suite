package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view;

import java.text.DateFormat;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;

public class ImportRequirementsTableModel extends DefaultTableModel {
	/**
	 * random number for serializing
	 */
	private static final long serialVersionUID = -7397557876939565130L;

	private static ImportRequirementsTableModel instance;
	private final String[] colNames = { "Name", "Description" };
	private PlanningPokerSession[] sessions = {};

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

	/**
	 * Refreshes the sessions.
	 * 
	 * @param sessions
	 *            The new list of sessions
	 */
	public void refreshSessions(PlanningPokerSession[] sessions) {
		this.setDataVector(null, colNames);
		this.sessions = sessions;
		for (PlanningPokerSession session : sessions) {
			Date deadline = session.getDeadline();
			String formattedDeadline = "";
			if (deadline != null) {
				formattedDeadline = DateFormat.getInstance().format(deadline);	
			}
			
			Object[] row = { 
				session.getID(), 
				session.getName(),
				formattedDeadline, 
				session.getStatus()
			};
			this.addRow(row);
		}
	}

	public PlanningPokerSession[] getSessions() {
		return sessions;
	}

	public void setSessions(PlanningPokerSession[] sessions) {
		this.sessions = sessions;
	}
}
