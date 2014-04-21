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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;

/**
 * VoteTableModel is a model that stores the ID and Vote
 * of a list of PlanningPokerVotes. 
 */
public class VoteTableModel extends DefaultTableModel {
	/** First row */
	private final String[] colNames = {"ID", "Vote"};
	
	/**
	 * Construct a VoteTableModel by assigning
	 * column names to its first row, column identifier
	 */
	public VoteTableModel() {
		setColumnIdentifiers(colNames);
	}
	
	/**
	 * Refreshes the votes.
	 * 
	 * @param votes
	 *            The new list of votes.
	 */
	public void refreshVotes(List<PlanningPokerVote> votes) {
		// Remove all the existing data
		this.setDataVector(null, colNames);
		
		for (PlanningPokerVote vote : votes) {			
			Object[] row = {vote.getID(),
							vote.getCardValue()};
			this.addRow(row);
		}
	}
}



