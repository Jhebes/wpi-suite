package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.util.TimerTask;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;

public class EndOnDeadlineController extends TimerTask {

	private PlanningPokerSession session;

	public EndOnDeadlineController(PlanningPokerSession session) {
		this.session = session;
	}

	public void run() {
		session.close();
		session.save();
	}

}
