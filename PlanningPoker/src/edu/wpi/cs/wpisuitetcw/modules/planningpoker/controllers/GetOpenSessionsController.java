/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.OverviewPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;

/**
 * This controller responds when the users click on "Open Sessions"
 * by exhibiting all the open planning poker sessions
 * on the overview panel
 * 
 * @author Hoang Ngo
 *
 */
public class GetOpenSessionsController implements ActionListener {
	private final OverviewPanel view;
	
	public GetOpenSessionsController(OverviewPanel view) {
		this.view = view;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Send a request to the core to get the open sessions
		final Request request = Network.getInstance().makeRequest("planningpoker/planningpokersession", HttpMethod.GET);
		request.addObserver(new GetOpenSessionsRequestObserver(this));
		request.send();
	}
	
	/**
	 * Add given open sessions to the overview panel
	 * This method is called by the GetOpenSessionsRequestObserver
	 * 
	 * @param an array of open sessions received from the server
	 */
	public void receiveOpenSessions(PlanningPokerSession[] sessions) {
		this.view.addSessions(sessions);
	}

}
