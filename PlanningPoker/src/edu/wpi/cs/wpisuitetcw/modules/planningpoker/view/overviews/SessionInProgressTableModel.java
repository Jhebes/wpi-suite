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

import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;

/**
 * SessionInProgressTableModel is a singleton model that stores 
 * a list of PlanningPokerSessions that are in progress
 */
public class SessionInProgressTableModel extends DefaultTableModel {
	/** Random number for serializing */
	private static final long serialVersionUID = -7397557876939565129L;

	/** First row */
	private final String[] colNames = { "Name", "Description", "Voted" };

	private static SessionInProgressTableModel instance;
		
	/**
	 * Constructs a table session for the overview table.
	 */
	private SessionInProgressTableModel() {
		setColumnIdentifiers(colNames);
	}

	public static SessionInProgressTableModel getInstance() {
		if (instance == null) {
			instance = new SessionInProgressTableModel();
		}
		return instance;
	}
}
