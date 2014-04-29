package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.util.TimerTask;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;

public class EndOnDeadlineController extends TimerTask {

	private int id;

	public EndOnDeadlineController(int newID) {
		this.id = newID;
	}

	public void run() {

	}

}
