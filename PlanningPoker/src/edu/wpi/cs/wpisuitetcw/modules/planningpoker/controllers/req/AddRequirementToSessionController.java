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
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.tabs.SessionRequirementPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

/**
 * Adds a requirement directly to a particular session.
 */
public class AddRequirementToSessionController implements ActionListener {

	private static final String DUPLICATE_REQ_MESSAGE = 
			"<html><font color='red'>This name has been used</font></html>";
	
	private PlanningPokerSession session;
	private SessionRequirementPanel panel;

	/**
	 * Constructs the controller.
	 * 
	 * @param panel
	 *            The panel containing the information about the new
	 *            requirement.
	 */
	public AddRequirementToSessionController(
			PlanningPokerSession session, 
			SessionRequirementPanel panel) {
		this.session = session;
		this.panel = panel;
	}

	/**
	 * Adds a requirement to the session's table by updating the session 
	 * locally, posting it to the database and updating the local table model.
	 */
	public void addRequirement() {

		// Create a PlanningPokerRequirement with the name and description
		// that user provides from the ViewSessionReqPanel
		final PlanningPokerRequirement requirement = new PlanningPokerRequirement();
		requirement.setName(this.panel.getNewReqName());
		requirement.setDescription(this.panel.getNewReqDesc());
		requirement.setSessionID(session.getID());

		
		// Create RequirementModel and Requirement to UPDATE the requirement
		// list of the Requirement manager module
		RequirementModel addReqModel = RequirementModel.getInstance();
		Requirement reqManagerRequirement = new Requirement();
		
		// Get the list of all PlanningPokerRequirements to check if
		// the user's requirement name is already existed or not
		List<PlanningPokerRequirement> requirements = new ArrayList<>();
		for (PlanningPokerSession planningPokerSession : SessionStash.getInstance().getSessions()) {
			for (PlanningPokerRequirement planningPokerRequirement : planningPokerSession.getRequirements()) {
				requirements.add(planningPokerRequirement);
			}
		}
		
		// Only add a new requirement if the current session 
		// AND the requirement module don't have it
		if (!hasPlanningPokerRequirement(requirements, requirement) && 
			!hasRequirement(addReqModel, requirement.getInnerRequirement())) {
			
			// Add the requirement to the Requirement Manager
			reqManagerRequirement.setId(addReqModel.getNextID());
			reqManagerRequirement.setName(requirement.getName());
			reqManagerRequirement.setDescription(requirement.getDescription());
			addReqModel.addRequirement(reqManagerRequirement);

			requirement.setCorrespondingReqManagerID(reqManagerRequirement.getId());

			// Add new requirement to the session
			session.addRequirement(requirement);
			session.save();
			// Validate session
			(new RequirementTableManager()).fetch(session.getID());
			panel.getSaveRequirement().setEnabled(false);
			panel.validateOpenSession();
			panel.refreshMoveButtons();
			
			// Clear the texts in name and description box
			panel.clearNewReqName();
			panel.clearNewReqDesc();
			
			// Clears the internal list used to determine whether we're editing a requirement
			panel.setSelectedReqName("");
			panel.setSelectedReqDescription("");
			
			// Hide error message if needed
			panel.hideErrorMessage();
		} else {
			// Warn user about duplicate requirement
			panel.setErrorMessage(DUPLICATE_REQ_MESSAGE);
			panel.showErrorMessage();
		}
		
		panel.validateNameAndDescription();
	}
	
	/*
	 * {@inheritdoc}
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		addRequirement(); // So we don't need to mock an action event to test
	}
	
	/*
	 * Return true if the List of planning poker requirement has
	 * the given planning poker requirement
	 */
	private boolean hasPlanningPokerRequirement(
			List<PlanningPokerRequirement> requirements,
			PlanningPokerRequirement otherRequirement) {
		for (PlanningPokerRequirement requirement : requirements) {
			if (requirement.getName().equals(otherRequirement.getName())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Return true if the given requirement model has
	 * the given requirement
	 */
	private boolean hasRequirement(RequirementModel addReqModel,
			Requirement innerRequirement) {
		List<Requirement> requirements = addReqModel.getRequirements();
		for (Requirement requirement : requirements) {
			if (requirement.getName().equals(innerRequirement.getName())) {
				return true;
			}
		}
		return false;
	}

}
