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
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;

public class EditActivatedSessionController implements ActionListener{

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

		
		this.session.deactivate();
		ViewEventManager.getInstance().removeTab(this.panel);
		ViewEventManager.getInstance().editSession(this.session);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.passBackToNewSession();
		
	}

}
