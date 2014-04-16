package edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.get.session.GetAllSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;

public class SessionStash {

	private boolean initialized = false;
	private static SessionStash self = null;
	private ArrayList<PlanningPokerSession> sessions = new ArrayList<PlanningPokerSession>();

	public SessionStash() {

	}

	public static SessionStash getInstance() {
		if (self == null) {
			self = new SessionStash();
		}
		return self;
	}

	public ArrayList<PlanningPokerSession> getSessions() {
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
		for (PlanningPokerSession s : incomingSessions) {
			if (this.getSessionByID(s.getID()) == null) {
				this.sessions.add(s);
				// Don't attempt to open tabs on first merge
				if (initialized) {
					ViewEventManager.getInstance().viewSession(s);
				}
			}
		}

		for (PlanningPokerSession s : this.sessions) {
			s.save();
		}
		initialized = true;
	}

	public void synchronize() {
		System.out.println("Synchronizing SessionStash");
		GetAllSessionsController.getInstance().retrieveSessions();
	}

}
