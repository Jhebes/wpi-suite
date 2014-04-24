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
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.AddRequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * A controller that activates a session and sends SMS with email to the users
 * to inform a session is activated
 */
public class ActivateSessionController implements ActionListener {

	private AddRequirementPanel panel;
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
	public ActivateSessionController(AddRequirementPanel panel,
			PlanningPokerSession session) {
		this.panel = panel;
		this.session = session;
	}

	/**
	 * Activate the session {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.session.getRequirements().size() > 0) {
			this.session.activate();
			session.save();
			ViewEventManager.getInstance().removeTab(panel);
			ViewEventManager.getInstance().viewSession(session);
		}
	}

	/**
	 * Send email and SMS to users
	 */
	public void onSuccess() {
		ViewEventManager.getInstance().removeTab(panel);
		ViewEventManager.getInstance().viewSession(session);

		String command = "sendEmail";
		// Send email to everyone in a session
		if (this.session.getUsers() != null) {
			for (User user : this.session.getUsers()) {
				String sendTo = user.getEmail();
				if (!sendTo.equals("")) {
					SendNotificationController.sendNotification("start",
							sendTo, session.getDeadline(), command);
				} else {
					SendNotificationController.sendNotification("start",
							"teamcombatwombat@gmail.com",
							session.getDeadline(), command);
				}
			}
		} else {
			SendNotificationController.sendNotification("start",
					"teamcombatwombat@gmail.com", session.getDeadline(),
					command);
		}

		// Send SMS to everyone in a session
		command = "sendSMS";
		if (this.session.getUsers() != null) {
			for (User user : this.session.getUsers()) {
				String sendTo = user.getSMS();
				if (!sendTo.equals("")) {
					SendNotificationController.sendNotification("start",
							sendTo, session.getDeadline(), command);
				} else {
					SendNotificationController.sendNotification("start",
							"15189662284", session.getDeadline(), command);
				}
			}
		} else {
			SendNotificationController.sendNotification("start", "15189662284",
					session.getDeadline(), command);
		}
	}

}
