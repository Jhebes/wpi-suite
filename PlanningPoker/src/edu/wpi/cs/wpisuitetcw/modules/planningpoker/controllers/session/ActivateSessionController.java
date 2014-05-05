/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.SendNotificationController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.tabs.SessionRequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * A controller that activates a session and sends SMS with email to the users
 * to inform a session is activated
 */
public class ActivateSessionController implements ActionListener {

	// private SessionRequirementPanel panel;
	private PlanningPokerSession session;

	/**
	 * Construct the controller by storing the ViewSessionPanel and
	 * PlanningPokerSession
	 * 
	 * @param panel
	 *            A ViewSessionPanel that exhibits the to-be-activated session
	 * @param session
	 *            A PlanningPokerSession that would be activated
	 */
	public ActivateSessionController(PlanningPokerSession session) {
		// this.panel = panel;
		this.session = session;
	}

	/**
	 * Activate the session {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (session.getRequirements().size() > 0) {
			session.activate();
			session.save();
			// ViewEventManager.getInstance().removeTab(panel);
			ViewEventManager.getInstance().viewSession(session);
		}
	}
}
