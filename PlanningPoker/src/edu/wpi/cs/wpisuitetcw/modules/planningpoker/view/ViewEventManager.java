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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.OverviewPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.OverviewTreePanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.EditSessionPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.VotePanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.tabs.SessionRequirementPanel;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

/**
 * Main class for controlling events that happen in our view.
 */
public class ViewEventManager {
	private static ViewEventManager instance = null;
	private MainView main;
	private OverviewPanel overviewPanel;
	private OverviewTreePanel overviewTreePanel;
	private ToolbarView toolbarView;
	private boolean isWelcomePageOnDisplay = true;
	private List<EditSessionPanel> editSessionPanels = new ArrayList<EditSessionPanel>();
	private List<VotePanel> inProgressSessionPanels = new ArrayList<VotePanel>();

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
	
	//call createBlankSessionController here
	public void createSession() {

		// create a blank session and save it to database
		PlanningPokerSession blankSession = new PlanningPokerSession();
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		final String defaultNameDate = sdf.format(new Date());
		final String projectName = ConfigManager.getConfig().getProjectName();
		blankSession.setName(projectName + " - " + defaultNameDate);
		blankSession.create();
	}

	/**
	 * Display the tutorial panel
	 */
	public void showTutorial() {
		main.setSelectedComponent(overviewPanel);
	}

	/**
	 * Opens a new tab for the editing of a session
	 * @param session The session to edit
	 */
	public void editSession(PlanningPokerSession session) {
		final EditSessionPanel newSession = new EditSessionPanel(session);
		main.addTab(session.getName(), null, newSession, "Session.");
		main.invalidate(); // force the tabbedpane to redraw
		main.repaint();
		main.setSelectedComponent(newSession);
	}

	/**
	 * Opens a new tab for viewing a session
	 * @param session The session whose info to gather when building the panel.
	 */
	public void viewSession(PlanningPokerSession session) {
		if (session.getStartTime() != null) {
			// check if the panel of the session is opened
			VotePanel exist = null;

			for (VotePanel panel : inProgressSessionPanels) {
				if (panel.getSession() == session) {
					exist = panel;
					break;
				}
			}

			if (exist == null) {
				final VotePanel newPanel = new VotePanel(
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
			EditSessionPanel exist = null;

			for (EditSessionPanel panel : editSessionPanels) {
				if (panel.getSession() == session) {
					exist = panel;
					break;
				}
			}

			if (exist == null) {
				// check if the panel of the session is opened
				final EditSessionPanel editSessionPanel = new EditSessionPanel(session);
				editSessionPanels.add(editSessionPanel);
				main.addTab(session.getName(), null, editSessionPanel,
						"Edit Session.");
				main.repaint();
				main.setSelectedComponent(editSessionPanel);
			} else {
				main.setSelectedComponent(exist);
			}

		}
	}

	/**
	 * displays a given panel with given msg
	 * @param panel The panel to display
	 * @param displayMsg The mouseover text for this tab
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
		return main;
	}

	public List<VotePanel> getVotePanels(){
		return this.inProgressSessionPanels;
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
	 * @param component
	 *            the component to remove
	 */
	public void removeTab(JComponent component) {
		if (component instanceof SessionRequirementPanel) {
			editSessionPanels.remove(component);
		}
		if (component instanceof VotePanel) {
			inProgressSessionPanels.remove(component);
		}
		
		main.remove(component);

	}



	/**
	 * return whether a welcome page is on display
	 */
	public boolean isWelcomePageOnDisplay() {
		return isWelcomePageOnDisplay;
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
		overviewPanel.updateUI();
	}

	/**
	 * the tree panel for updating the session
	 * @param overviewTreePanel
	 */
	public void setOverviewTree(OverviewTreePanel overviewTreePanel) {
		this.overviewTreePanel = overviewTreePanel;
	}
	
	/**
	 * return the tree panel
	 * @return tree panel
	 */
	public OverviewTreePanel getOverviewTreePanel() {
		return overviewTreePanel;
	}

}
