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
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.AddRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

/**
 * A controller that adds a requirement to the default planning poker session, 
 * making it available to be added by all New Planning Poker sessions.
 */
public class AddRequirementToAllController implements ActionListener {

	private ViewSessionReqPanel panel;

	/**
	 * Constructs the controller by storing the given ViewSessionReqPanel.
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
		final PlanningPokerSession defaultSession = SessionStash.getInstance()
				.getDefaultSession();
		// Adds the model of requirements from the req manager, add a
		// requirement to this model, will add to and update
		// the req manager
		final RequirementModel addReqModel = RequirementModel.getInstance();
		final Requirement reqManagerRequirement = new Requirement();

		final PlanningPokerRequirement requirement = new PlanningPokerRequirement();
		requirement.setName(this.panel.getNewReqName());
		requirement.setDescription(this.panel.getNewReqDesc());
		defaultSession.addRequirement(requirement);
		defaultSession.save();
		this.panel.clearNewReqName();
		this.panel.clearNewReqDesc();

		// Fill in the information for the requirement being created
		reqManagerRequirement.setId(addReqModel.getNextID());
		// sync up the ID's in the req manager requirement and our PPrequirement.
		requirement.setCorrespondingReqManagerID(reqManagerRequirement.getId());
		reqManagerRequirement.setName(requirement.getName());
		reqManagerRequirement.setDescription(requirement.getDescription());
		// Add the new Requirement to the requirement manager
		addReqModel.addRequirement(reqManagerRequirement);

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
