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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.put.GenericPUTRequestObserver;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionReqPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Adds a requirement directly to a particular session.
 */
public class AddRequirementToSessionController implements ActionListener {

	private ViewSessionReqPanel panel;

	/**
	 * Constructs the controller.
	 * 
	 * @param panel
	 *            The panel containing the information about the new
	 *            requirement.
	 */
	public AddRequirementToSessionController(ViewSessionReqPanel panel) {
		this.panel = panel;
	}

	/**
	 * Adds a requirement to the session's table by updating the session 
	 * locally, posting it to the database and updating the local table model.
	 */
	public void addRequirement() {
		int id = this.panel.getSession().getID();

		PlanningPokerSession session = SessionStash.getInstance()
				.getSessionByID(id);
		// Adds the model of requirements from the req manager, add a requirement to this model, will add and update 
		// the req manager
		RequirementModel addReqModel = RequirementModel.getInstance();

		PlanningPokerRequirement requirement = new PlanningPokerRequirement();
		requirement.setName(this.panel.getNewReqName());
		requirement.setDescription(this.panel.getNewReqDesc());
		session.addRequirement(requirement);
		session.save();
		this.panel.clearNewReqName();
		this.panel.clearNewReqDesc();
		
		// This adds a new requirement to the req manager, the ID is defaulting to 0 which will need to be changed.
		// TODO: change ID from 0 to something more useful
		addReqModel.addRequirement(new Requirement(0, requirement.getName(), requirement.getDescription()));

		(new RequirementTableManager()).fetch(id);
	}

	/*
	 * {@inheritdoc}
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		addRequirement(); // So we don't need to mock an action event to test
	}
}
