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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.VotePanel;

/**
 * A controller that allows users editing an activated session
 */
public class EditActivatedSessionController implements ActionListener{

	private PlanningPokerSession session;
	private VotePanel panel;

	/**
	 * Construct the controller by storing the given PlanningPokerSession
	 * and SessionInProgressPanel
	 * @param session A PlanningPokerSession that would be stored
	 * @param panel A SessionInProgressPanel that would be stored
	 */
	public EditActivatedSessionController(PlanningPokerSession session,
										  VotePanel panel) {
		this.panel = panel;
		this.session = session;
		session.setEditMode(true);
	}

	/**
	 * Deactivate the session, close its current tab, and
	 * show the panel to edit the recently deactivated session
	 */
	public void passBackToNewSession() {
		session.deactivate();
		ViewEventManager.getInstance().removeTab(panel);
		ViewEventManager.getInstance().editSession(session);
	}

	/**
	 * Call passBackToNewSession method
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.passBackToNewSession();	
	}

}
