package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;

public class sessionInProgressTableModel extends  DefaultTableModel {
	/**
	 * random number for serializing
	 */
	private static final long serialVersionUID = -7397557876939565129L;

	private static sessionInProgressTableModel instance;
	private final String[] colNames = { "Name", "Description", "Voted" };

	/**
	 * Constructs a table session for the overview table.
	 */
	private sessionInProgressTableModel() {
		setColumnIdentifiers(colNames);
	}

	public static sessionInProgressTableModel getInstance() {
		if (instance == null) {
			instance = new sessionInProgressTableModel();
		}
		return instance;
	}
}
