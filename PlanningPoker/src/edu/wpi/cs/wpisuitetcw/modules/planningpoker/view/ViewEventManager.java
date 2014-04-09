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

import javax.swing.JComponent;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers.ViewSessionTableManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateNewDeckPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionPanel;

/**
 * 
 * @author troyling
 * 
 */
public class ViewEventManager {
	private static ViewEventManager instance = null;
	private MainView main;
	private ToolbarView toolbarView;

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
	 * Opens a new tab for viewing a session
	 */
	public void viewSession(PlanningPokerSession session) {
		if (session.isActive()) {
			SessionInProgressPanel panel = new SessionInProgressPanel(session);
			main.addTab(session.getName(), null, panel, "Session in progress.");
			main.repaint();
			main.setSelectedComponent(panel);
		} else {
			
			ViewSessionPanel viewSession = new ViewSessionPanel(session);
			main.addTab(session.getName(), null, viewSession, "View Session.");
			main.repaint();
			main.setSelectedComponent(viewSession);
		}
	}
	
	/**
	 *  Opens a new tab for creatign a new deck of cards
	 */
	public void createDeck() {
		CreateNewDeckPanel deckPanel = new CreateNewDeckPanel();
		main.addTab("New Deck", null, deckPanel, "New Deck");
		main.repaint();
		main.setSelectedComponent(deckPanel);
	}
	
	
	/**
	 * Sets the toolbarview to the given toolbar
	 * @param tb the toolbar to be set as active.
	 */
	public void setToolBar(ToolbarView toolbarView) {
		this.toolbarView = toolbarView;
		this.toolbarView.repaint();
	}
	
	/**
	 * Sets the main view to the given view.
	 * @param mainview MainView
	 */
	public void setMainView(MainView mainview) {
		main = mainview;
		
	}
	
	
	/**
	 * Removes the tab for the given JComponent
	 * @param comp the component to remove
	 */
	public void removeTab(JComponent component) {
		main.remove(component);
		
	}

	/**
	 * Creates the import requirements panel.
	 */
	public void createImportRequirementsPanel() {
		ImportRequirementsPanel newPanel = new ImportRequirementsPanel();
		main.addTab("Import Requirements", null, newPanel, "Import a new requirement.");
		main.invalidate();
		main.repaint();
		main.setSelectedComponent(newPanel);
	}
	
}
