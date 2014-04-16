/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;

public class VoteSessionTableModel extends DefaultTableModel{
	private static final long serialVersionUID = 211621657544637746L;
	private final String[] colNames = {"ID", "Vote"};
	
	public VoteSessionTableModel() {
		setColumnIdentifiers(colNames);
	}
	
	/**
	 * Refreshes the votes.
	 * 
	 * @param votes
	 *            The new list of votes.
	 */
	public void refreshVotes(List<PlanningPokerVote> votes) {

		this.setDataVector(null, colNames);
		for (PlanningPokerVote vote : votes) {			
			Object[] row = { 
					vote.getID(),
					vote.getCardValue()
			};
			this.addRow(row);
		}
	}
}



