/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionReqPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;

/**
 * Adds a requirement directly to the planning poker session.
 */
public class AddRequirementToAllController implements ActionListener {

	private ViewSessionReqPanel panel;

	/**
	 * Constructs the controller.
	 * 
	 * @param panel
	 *            The panel containing the information about the new
	 *            requirement.
	 */
	public AddRequirementToAllController(ViewSessionReqPanel panel) {
		this.panel = panel;
	}

	/**
	 * Adds a requirement to the "All Requirements" table by updating the
	 * default session locally, posting it to the database and updating the
	 * local table model.
	 */
	public void addRequirement() {
		PlanningPokerSession defaultSession = SessionStash.getInstance()
				.getDefaultSession();

		PlanningPokerRequirement requirement = new PlanningPokerRequirement();
		requirement.setName(this.panel.getNewReqName());
		requirement.setDescription(this.panel.getNewReqDesc());
		defaultSession.addRequirement(requirement);
		defaultSession.save();
		this.panel.clearNewReqName();
		this.panel.clearNewReqDesc();

		(new RequirementTableManager()).fetch(1);
	}

	/*
	 * {@inheritdoc}
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		addRequirement();  // So we don't need to mock an action event to test
	}
}
