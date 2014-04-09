/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;

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
	private PlanningPokerSession[] sessions = {};

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
		this.sessions = sessions;
		if (sessions == null) {
			return;
		}
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
