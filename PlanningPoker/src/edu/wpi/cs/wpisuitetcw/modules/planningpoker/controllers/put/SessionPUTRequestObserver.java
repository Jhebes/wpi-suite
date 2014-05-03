package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.put;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * 
 * Handles requests to create a session
 * 
 */
public class SessionPUTRequestObserver implements RequestObserver {

	@Override
	public void responseSuccess(IRequest iReq) {
		// open edit session panel with the session
		PlanningPokerSession session = PlanningPokerSession.fromJson(iReq
				.getResponse().getBody());
		if (session == null) {
			System.err.println("Fail to open the session");
		} else {
			// update the stash 
			SessionStash.getInstance().synchronize();
			new RequirementTableManager().init(session.getID());
			// open edit session panel
			ViewEventManager.getInstance().editSession(session);
		}
	}

	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to create a new session failed. (ERR)");

	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a session failed. (NETW)");
	}
}
