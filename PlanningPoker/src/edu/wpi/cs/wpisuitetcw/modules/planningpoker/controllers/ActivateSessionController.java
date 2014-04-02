package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class ActivateSessionController implements ActionListener {

	private ViewSessionPanel panel;
	private PlanningPokerSession session;

	public ActivateSessionController(ViewSessionPanel panel, PlanningPokerSession session) {
		this.panel = panel;
		this.session = session;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		this.session.activate();
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/session", HttpMethod.POST);
		request.setBody(session.toJSON());
		request.addObserver(new ActivateSessionObserver(this));
		request.send();
		
	}
	
	public void onSuccess() {
		ViewEventManager.getInstance().removeTab(panel);
		ViewEventManager.getInstance().viewSession(session);
		//asdf
	}

}
