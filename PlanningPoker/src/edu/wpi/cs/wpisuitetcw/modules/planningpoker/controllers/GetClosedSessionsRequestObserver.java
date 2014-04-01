/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * @author Hoang Ngo
 *
 */
public class GetClosedSessionsRequestObserver implements RequestObserver {
	private final GetClosedSessionsController controller;
	
	public GetClosedSessionsRequestObserver(GetClosedSessionsController controller) {
		this.controller = controller;
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Parse the sessions out of the response body
		PlanningPokerSession[] sessions = PlanningPokerSession.fromJsonArray(iReq.getResponse().getBody());
		
		// Filter the closed sessions using arrayList
		ArrayList<PlanningPokerSession> tempClosedSessions = new ArrayList<PlanningPokerSession>();
		for (int i = 0; i < sessions.length; i++) {
			if (sessions[i].isClosed()) {
				tempClosedSessions.add(sessions[i]);
			}
		}
		
		// Convert the arrayList to array
		PlanningPokerSession[] closedSessions = new PlanningPokerSession[tempClosedSessions.size()];
		closedSessions = tempClosedSessions.toArray(closedSessions);
		
		// Pass the session back to the controller
		controller.receiveClosedSessions(closedSessions);
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		// TODO print the error message to the overview panel
		System.err.println("The request to get closed sessions failed.");
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO print the error message to the overview panel
		System.err.println("The request to get closed sessions failed.");
	}

}
