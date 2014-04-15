package edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.get.session.GetAllSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionTableModel;

public class SessionStash {
	
	private static SessionStash self = null;
	private ArrayList<PlanningPokerSession> sessions = new ArrayList<PlanningPokerSession>();
	public SessionStash(){
		
	}
	
	public static SessionStash getInstance(){
		if(self == null){
			self = new SessionStash();
		}
		return self;
	}
	
	public ArrayList<PlanningPokerSession> getSessions(){
		return this.sessions;
	}
	
	public void addSession(PlanningPokerSession p){
		this.sessions.add(p);
	}
	public void addSession(Iterable<PlanningPokerSession> p){
		for(PlanningPokerSession s : p){
			this.addSession(s);
		}
	}
	
	public void clear(){
		this.sessions.clear();
	}
	
	public PlanningPokerSession getSessionByID(int id){
		for(PlanningPokerSession p : this.sessions){
			if(p.getID() == id){
				return p;
			}
		}
		return null;
	}
	
	
	public void mergeFromServer(PlanningPokerSession[] incomingSessions){
		for(PlanningPokerSession s : incomingSessions){
			if(this.getSessionByID(s.getID()) == null){
				this.sessions.add(s);
			}
		}
		
		for(PlanningPokerSession s : this.sessions){
			s.update();
		}
		SessionTableModel.getInstance().refreshSessions((PlanningPokerSession[])this.sessions.toArray());
		System.out.println("Success");
	}
	
	
	
	public void synchronize(){
		System.out.println("Synchronizing SessionStash");
		GetAllSessionsController.getInstance().retrieveSessions();
	}
	
}
