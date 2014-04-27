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

public class SessionStash {

	private boolean initialized = false;
	private static SessionStash self = null;
	private List<PlanningPokerSession> sessions = new ArrayList<PlanningPokerSession>();

	

	public static SessionStash getInstance() {
		if (self == null) {
			self = new SessionStash();
		}
		return self;
	}

	public List<PlanningPokerSession> getSessions() {
		return this.sessions;
	}

	public void addSession(PlanningPokerSession p) {
		this.sessions.add(p);
	}

	public void addSession(Iterable<PlanningPokerSession> p) {
		for (PlanningPokerSession s : p) {
			this.addSession(s);
		}
	}

	public void clear() {
		this.sessions.clear();
	}

	public PlanningPokerSession getSessionByID(int id) {
		for (PlanningPokerSession p : this.sessions) {
			if (p.getID() == id) {
				return p;
			}
		}
		return null;
	}

	public void mergeFromServer(List<PlanningPokerSession> incomingSessions) {	
		for (PlanningPokerSession s : this.sessions) {
			s.save();
		}
		
		for (PlanningPokerSession s : incomingSessions) {
			if (this.getSessionByID(s.getID()) == null) {
				this.sessions.add(s);
				if(this.initialized && s.getID() != 1){
					ViewEventManager.getInstance().viewSession(s);
				}
			}
		}
//		SessionTableModel.getInstance().getSessions();
		
		initialized = true;
	}

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
