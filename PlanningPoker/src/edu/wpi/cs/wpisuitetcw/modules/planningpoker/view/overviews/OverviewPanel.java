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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session.GetAllSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;

public class OverviewPanel extends JSplitPane {

	private static final long serialVersionUID = 1L;
	private final JPanel rightPanel;
	private final OverviewTreePanel treePanel;
	// this will be the panel of a welcome page
	private final JPanel welcomePanel;
	private final JScrollPane sessionPanelJsp;

	// private final JButton openSessionBtn;
	// private final JButton closedSessionBtn;
	// private final JButton allSessionsBtn;

	public OverviewPanel() {
		// Create the right side of the panel
		rightPanel = new JPanel();

		// Create the left side
		treePanel = new OverviewTreePanel();

		// TODO add the real welcome panel
		welcomePanel = new JPanel();
		welcomePanel.add(new JLabel("This is a Welcome Page."));

		final JTable table = new JTable(
				OverviewTableSessionTableModel.getInstance()) {
			private static final long serialVersionUID = 1L;
			private boolean initialized = false;

			public boolean isCellEditable(int row, int column) {
				return false;
			};

			public void valueChanged(ListSelectionEvent e) {

			}

			@Override
			public void repaint() {
				try {
					if (!initialized) {
						GetAllSessionsController.getInstance()
								.retrieveSessions();
						initialized = true;
					}
				} catch (Exception e) {

				}
			}
		};

		// Add mouse listener to check for mouse clicks on the table
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// Check to see if user double clicked
				if (e.getClickCount() == 2) {
					JTable resultsTable = (JTable) e.getSource();
					int row = resultsTable.rowAtPoint(e.getPoint());

					if (row > -1) {
						// Gets the name, which is index 1
						PlanningPokerSession session = OverviewTableSessionTableModel
								.getInstance().getSessions()[row];
						ViewEventManager.getInstance().viewSession(session);
					}
				}
			}
		});

		// Sets table bg to white
		table.setBackground(Color.WHITE);

		// allow sorting in table
		table.setAutoCreateRowSorter(true);// update model
		table.getTableHeader().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				rightPanel.updateUI();

			}
		});
		table.getTableHeader().setReorderingAllowed(false);

		// disallow moving of columns
		table.getTableHeader().setReorderingAllowed(false);

		// Set layout for right panel;
		rightPanel.setLayout(new BorderLayout());

		// Add table inside a JScrollPane to rightPanel
		sessionPanelJsp = new JScrollPane(table);

		// Add the JSP to the rightPanel
		rightPanel.add(welcomePanel);

		// Set panels background to white (matching table)
		rightPanel.setBackground(Color.WHITE);

		// Add panels to main JSplitPane
		this.setRightComponent(rightPanel);
		this.setLeftComponent(treePanel);

		// Set divider location between right and left panel
		this.setDividerLocation(180);
		ViewEventManager.getInstance().setOverviewPanel(this);
	}

	/**
	 * relaces the welcome page with the session table
	 */
	public void showSessionTable() {
		this.rightPanel.remove(welcomePanel);
		this.rightPanel.add(sessionPanelJsp);
		updateUI();
	}

}
