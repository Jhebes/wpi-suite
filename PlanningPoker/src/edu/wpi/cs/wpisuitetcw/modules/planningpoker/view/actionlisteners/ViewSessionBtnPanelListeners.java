package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionBtnPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionReqPanel;

public class ViewSessionBtnPanelListeners {
	public static class ActivateSession implements ActionListener {
		private ViewSessionBtnPanel view;

		public ActivateSession(ViewSessionBtnPanel view) {
			this.view = view;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			PlanningPokerSession session = view.parentPanel.pkgPanel.session;
			session.activate();
			session.update();
			SessionStash.getInstance().updateSession(session.getID(),
					session);
			ViewEventManager.getInstance().removeTab(view.parentPanel);
			ViewEventManager.getInstance().viewSession(session);
		}
	}
}
