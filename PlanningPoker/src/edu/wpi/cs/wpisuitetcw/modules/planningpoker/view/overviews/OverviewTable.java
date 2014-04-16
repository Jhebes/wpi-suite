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

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session.GetAllSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;

public class OverviewTable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean initialized = false;

	public OverviewTable(OverviewTableSessionTableModel sessionModel) {
		// set up tables
		super(sessionModel);
		this.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// Check to see if user double clicked
				if (e.getClickCount() == 2) {
					JTable resultsTable = (JTable) e.getSource();
					int row = resultsTable.rowAtPoint(e.getPoint());

					if (row > -1) {
						// Gets the name, which is index 1
						PlanningPokerSession session = OverviewTableSessionTableModel
								.getInstance().getSessions().get(row);
						ViewEventManager.getInstance().viewSession(session);
					}
				}
			}
		});

		this.setBackground(Color.WHITE);
		this.setAutoCreateRowSorter(true);// update model
		this.getTableHeader().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ViewEventManager.getInstance().refreshOverviewPanel();
			}
		});

		// disallow moving of columns
		this.getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void repaint() {
		try {
			if (!initialized) {
				GetAllSessionsController.getInstance().retrieveSessions();
				initialized = true;
			}
		} catch (Exception e) {
			Logger.getLogger("PlanningPoker").log(Level.FINE,
					"Repaint failed for overview table.", e);
		}
	}
}
