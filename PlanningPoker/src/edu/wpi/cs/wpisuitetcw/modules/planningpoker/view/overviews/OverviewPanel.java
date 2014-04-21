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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.UserStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;

public class OverviewPanel extends JSplitPane {

	private static final long serialVersionUID = 1L;
	private final JPanel rightPanel;
	private final OverviewTreePanel treePanel;
	// this will be the panel of a welcome page
	private final DefaultHomePanel welcomePanel;
	private final JScrollPane sessionPanelJsp;

	public OverviewPanel() {
		// Create the right side of the panel
		rightPanel = new JPanel();

		// Create the left side
		treePanel = new OverviewTreePanel();

		// TODO add the real welcome panel
		welcomePanel = new DefaultHomePanel();

		JTable table = new OverviewTable(
				SessionTableModel.getInstance());

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
		SessionStash.getInstance().synchronize();
		UserStash.getInstance().synchronize();
		this.rightPanel.remove(welcomePanel);
		this.rightPanel.add(sessionPanelJsp);
		updateUI();
	}

}
