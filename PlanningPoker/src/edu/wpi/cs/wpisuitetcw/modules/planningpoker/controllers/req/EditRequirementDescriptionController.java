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
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.tabs.SessionRequirementPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;

/**
 * This controller adds all the requirements to the specified session
 * 
 */
public class EditRequirementDescriptionController implements ActionListener {

	private PlanningPokerSession session = null;
	private SessionRequirementPanel view;

	/**
	 * Construct the controller by storing the given PlanningPokerSession and
	 * ViewSessionReqPanel
	 * 
	 * @param s
	 *            A PlanningPokerSession that would be stored
	 * @param v
	 *            A ViewSessionReqPanel that would be stored
	 */
	public EditRequirementDescriptionController(PlanningPokerSession s, SessionRequirementPanel v) {
		session = s;
		view = v;
	}

	/*
	 * This method is called when the user clicks the vote button
	 * 
	 * {@see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent}
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		final PlanningPokerSession session = view.getEditRequirementsSession();
		final PlanningPokerRequirement requirement;
		final List<PlanningPokerRequirement> requirements = new ArrayList<PlanningPokerRequirement>();
		final String requirementNames = view.getSelectedReqName();
		view.clearSelection();
		requirement = session.getReqByName(requirementNames);
		requirements.add(requirement);
		session.deleteRequirements(requirements);
		requirements.remove(requirement);
		requirement.setDescription(view.getNewReqDesc());
		requirements.add(requirement);
		session.addRequirements(requirements);
		final RequirementTableManager tableManager = new RequirementTableManager();
		tableManager.refreshRequirements(session.getID(), session.getRequirements());

		session.save();

		view.getSaveRequirement().setEnabled(false);
		view.clearNewReqDesc();
		view.clearNewReqName();
		view.setSelectedReqName("");
		view.setSelectedReqDescription("");
		view.enableName();
		view.getAllReqTable().repaint();
		view.getSessionReqTable().repaint();
		
		view.validateOpenSession();

	}
}
