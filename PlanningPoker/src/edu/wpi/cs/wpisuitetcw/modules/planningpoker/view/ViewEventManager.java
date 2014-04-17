/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view;

import java.util.ArrayList;

import javax.swing.JComponent;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.OverviewPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.OverviewTreePanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionPanel;

public class ViewEventManager {
	private static ViewEventManager instance = null;
	private MainView main;
	private ImportRequirementsPanel requirementPanel;
	private OverviewPanel overviewPanel;
	private ToolbarView toolbarView;
	private boolean isWelcomePageOnDisplay = true;
	private ArrayList<ViewSessionPanel> viewSessionPanels = new ArrayList<ViewSessionPanel>();
	private ArrayList<SessionInProgressPanel> inProgressSessionPanels = new ArrayList<SessionInProgressPanel>();

	/**
	 * Default constructor for ViewEventController. It is set to private to
	 * prevent instantiation.
	 */
	private ViewEventManager() {
	}

	/**
	 * Returns the instance of the ViewEventController
	 * 
	 * @return The instance of the controller
	 */
	public static ViewEventManager getInstance() {
		if (instance == null) {
			instance = new ViewEventManager();
		}
		return instance;
	}

	/**
	 * Opens a new tab for the creation of a session
	 */
	public void createSession() {
		CreateSessionPanel newSession = new CreateSessionPanel();
		main.addTab("New Session", null, newSession, "New session.");
		main.invalidate(); // force the tabbedpane to redraw
		main.repaint();
		main.setSelectedComponent(newSession);
	}

	/**
	 * Display the tutorial panel
	 */
	public void showTutorial() {
		main.setSelectedComponent(overviewPanel);
	}

	/**
	 * Opens a new tab for the editing of a session
	 */
	public void editSession(PlanningPokerSession session) {
		CreateSessionPanel newSession = new CreateSessionPanel(session);
		main.addTab("Edit: " + session.getName(), null, newSession,
				"Edit session.");
		main.invalidate(); // force the tabbedpane to redraw
		main.repaint();
		main.setSelectedComponent(newSession);
	}

	/**
	 * Opens a new tab for viewing a session
	 */
	public void viewSession(PlanningPokerSession session) {
		if (session.getStartTime() != null) {
			// check if the panel of the session is opened
			SessionInProgressPanel exist = null;

			for (SessionInProgressPanel panel : inProgressSessionPanels) {
				if (panel.getSession() == session) {
					exist = panel;
					break;
				}
			}

			if (exist == null) {
				SessionInProgressPanel newPanel = new SessionInProgressPanel(
						session);
				inProgressSessionPanels.add(newPanel);
				main.addTab(session.getName(), null, newPanel,
						"Session in progress.");
				main.repaint();
				main.setSelectedComponent(newPanel);
			} else {
				// open the existed panel
				main.setSelectedComponent(exist);
			}

		} else {
			ViewSessionPanel exist = null;

			for (ViewSessionPanel panel : viewSessionPanels) {
				if (panel.getPPSession() == session) {
					exist = panel;
					break;
				}
			}

			if (exist == null) {
				// check if the panel of the session is opened
				ViewSessionPanel viewSession = new ViewSessionPanel(session);
				viewSessionPanels.add(viewSession);
				main.addTab(session.getName(), null, viewSession,
						"View Session.");
				main.repaint();
				main.setSelectedComponent(viewSession);
			} else {
				main.setSelectedComponent(exist);
			}

		}
	}

	/**
	 * displays a given panel with given msg
	 */
	public void display(JComponent panel, String displayMsg) {
		main.addTab(displayMsg, null, panel, displayMsg);
		main.repaint();
		main.setSelectedComponent(panel);
	}

	/**
	 * return the main view
	 */
	public MainView getMainview() {
		return this.main;
	}

	/**
	 * sets overview tree in the overview panel
	 * 
	 * @param overviewTree
	 *            overviewTreePanel
	 */
	public void setOverviewTree(OverviewTreePanel overviewTree) {
	}

	/**
	 * Sets the toolbarview to the given toolbar
	 * 
	 * @param tb
	 *            the toolbar to be set as active.
	 */
	public void setToolBar(ToolbarView toolbarView) {
		this.toolbarView = toolbarView;
		this.toolbarView.repaint();
	}

	/**
	 * Sets the main view to the given view.
	 * 
	 * @param mainview
	 *            MainView
	 */
	public void setMainView(MainView mainview) {
		main = mainview;

	}

	/**
	 * Removes the tab for the given JComponent
	 * 
	 * @param comp
	 *            the component to remove
	 */
	public void removeTab(JComponent component) {
		if (component instanceof ViewSessionPanel) {
			this.viewSessionPanels.remove(component);
		}
		if (component instanceof SessionInProgressPanel) {
			this.inProgressSessionPanels.remove(component);
		}
		if (component instanceof ImportRequirementsPanel) {
			this.requirementPanel = null;
		}
		main.remove(component);

	}

	/**
	 * Creates the import requirements panel.
	 */
	public void createImportRequirementsPanel() {
		if (requirementPanel == null) {
			ImportRequirementsPanel newPanel = new ImportRequirementsPanel();
			this.requirementPanel = newPanel;
			main.addTab("Import Requirements", null, newPanel,
					"Import a new requirement.");
			main.invalidate();
			main.repaint();
			main.setSelectedComponent(newPanel);
		} else {
			main.setSelectedComponent(requirementPanel);
		}
	}

	/**
	 * return whether a welcome page is on display
	 */
	public boolean isWelcomePageOnDisplay() {
		return this.isWelcomePageOnDisplay;
	}

	/**
	 * show table and remove the welcome page
	 */
	public void showSessionTable() {
		this.overviewPanel.showSessionTable();
	}

	/**
	 * setter for overview panel
	 */
	public void setOverviewPanel(OverviewPanel overviewPanel) {
		this.overviewPanel = overviewPanel;
	}

	/**
	 * update the contents on overview panel
	 */
	public void refreshOverviewPanel() {
		this.overviewPanel.updateUI();
	}

}
