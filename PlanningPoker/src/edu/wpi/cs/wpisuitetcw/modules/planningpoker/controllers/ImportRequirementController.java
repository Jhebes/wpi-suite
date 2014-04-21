/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ImportRequirementsTableModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A controller that creates a planning poker requirement 
 * from a requirement manager requirement and sends it to the database.
 */
public class ImportRequirementController implements ActionListener {
	/** A PlanningPokerRequirement that would be created */
	private PlanningPokerRequirement requirement;
	
	

	/**
	 * Convert all Requirements to PlanningPokerRequirements and
	 * send them to the database
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		ImportRequirementsTableModel dtm = ImportRequirementsTableModel
				.getInstance();
		int nRow = dtm.getRowCount();
		for (int i = 0; i < nRow; i++) {
			if ((Boolean) dtm.getValueAt(i, 0)) {
				PlanningPokerRequirement ppreq = (PlanningPokerRequirement) dtm
						.getValueAt(i, 1);
				importRequirement(ppreq);
			}
		}
	}

	/**
	 * Send a request to get all the PlanningPokerSession of ID 1 
	 * from the database
	 * @param requirement A PlanningPokerRequirement that would be assigned
	 * to the ImportRequirementController
	 */
	public void importRequirement(PlanningPokerRequirement requirement) {
		this.requirement = requirement;
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/session/1", HttpMethod.GET);
		request.addObserver(new ImportRequirementRequestObserver(this));
		request.send();
	}

	/**
	 * Add the requirement to the PlanningPokerSession.
	 * This method is called by the ImportRequirementRequestObserver
	 * @param freeReqsSession A session that would have the 
	 * PlanningPokerRequirement stored in this controller
	 */
	public void onSuccess(PlanningPokerSession freeReqsSession) {
		freeReqsSession.addRequirement(requirement);
		freeReqsSession.save();
	}
}
