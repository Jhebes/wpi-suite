/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.get.requirementmanagerrequirements.GetAllRequirementsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.get.session.GetAllSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;

/**
 * Centralized cache for storing and manipulating sessions.
 */
public class SessionStash {

	private boolean initialized = false;
	private static SessionStash self = null;
	private List<PlanningPokerSession> sessions = new ArrayList<PlanningPokerSession>();

	/**
	 * @return The instance for this singleton.
	 */
	public static SessionStash getInstance() {
		if (self == null) {
			self = new SessionStash();
		}
		return self;
	}

	/**
	 * @return The current list of planning poker sessions
	 */
	public List<PlanningPokerSession> getSessions() {
		return sessions;
	}

	/**
	 * Adds a new session to the stash.
	 * @param p The new session
	 */
	public void addSession(PlanningPokerSession p) {
		sessions.add(p);
	}

	/**
	 * Adds an iterable set of sessions to the stash.
	 * @param p The set of sessions to add
	 */
	public void addSession(Iterable<PlanningPokerSession> p) {
		for (PlanningPokerSession s : p) {
			this.addSession(s);
		}
	}

	/**
	 * Clears the internal list of sessions.
	 */
	public void clear() {
		sessions.clear();
	}

	/**
	 * Gets a particular planning poker session by ID.
	 * @param id The ID queried
	 * @return The planning poker session if found, else null
	 */
	public PlanningPokerSession getSessionByID(int id) {
		for (PlanningPokerSession p : sessions) {
			if (p.getID() == id) {
				return p;
			}
		}
		return null;
	}

	/** 
	 * Callback for synchronize. Updates the local cache with the server's changes.
	 * @param incomingSessions The server's sessions
	 */
	public void mergeFromServer(List<PlanningPokerSession> incomingSessions) {
		for (PlanningPokerSession s : incomingSessions) {
			if (this.getSessionByID(s.getID()) == null) {
				sessions.add(s);
			}
		}
		
		initialized = true;
	}

	/**
	 * Updates the local cache from the server
	 */
	public void synchronize() {
		GetAllRequirementsController.getInstance().retrieveRequirements();
		GetAllSessionsController.getInstance().retrieveSessions();
	}

	/**
	 * Creates the default planning poker session for unadded requirements.
	 * 
	 * @return The default planning poker session with special ID 1.
	 */
	public PlanningPokerSession createDefaultSession() {
		final PlanningPokerSession defaultSession = new PlanningPokerSession();
		defaultSession.setID(1);
		defaultSession.create();
		return defaultSession;
	}

	/**
	 * Gets or creates the default session with special ID 1.
	 * 
	 * @return The default session
	 */
	public PlanningPokerSession getDefaultSession() {
		PlanningPokerSession defaultSession = getSessionByID(1);
		if (defaultSession == null) {
			defaultSession = createDefaultSession();
			SessionStash.getInstance().addSession(defaultSession);
		}
		return defaultSession;
	}

	/**
	 * Updates the master list of sessions with an edited (but already existing)
	 * session.
	 * 
	 * @param editedSession
	 *            The editted session
	 */
	public void update(PlanningPokerSession editedSession) {
		for (int i = 0; i < sessions.size(); ++i) {
			if (sessions.get(i).getID() == editedSession.getID()) {
				sessions.set(i, editedSession);
			}
		}
	}
}
