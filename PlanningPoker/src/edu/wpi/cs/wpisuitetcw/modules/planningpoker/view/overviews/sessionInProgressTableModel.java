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

public class sessionInProgressTableModel extends  DefaultTableModel {
	/**
	 * random number for serializing
	 */
	private static final long serialVersionUID = -7397557876939565129L;

	private static sessionInProgressTableModel instance;
	private final String[] colNames = { "Name", "Description" };

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
