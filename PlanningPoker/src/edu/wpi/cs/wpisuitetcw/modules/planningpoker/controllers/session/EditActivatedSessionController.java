package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;

public class EditActivatedSessionController {

	private PlanningPokerSession session;
	private SessionInProgressPanel panel;

	// the constructor for this session
	public EditActivatedSessionController(PlanningPokerSession session,
			SessionInProgressPanel panel) {

		this.panel = panel;
		this.session = session;
	}

	/**
	 * passes back the session to the edit menu, by simply de-activating it,
	 * closing the tab that's open, and then opening the session in the edit
	 * view.
	 */
	public void passBackToNewSession() {

		this.
		this.session.deactivate();

		// close me
		// open new edit tab

	}

}
