/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

public class VoteRequirementCellRenderer extends JLabel implements ListCellRenderer<PlanningPokerRequirement> {

	/** An icon for not-yet-voted requirement */
	private final ImageIcon notVoteIcon;
	
	/** An icon for voted requirement */
	private final ImageIcon voteIcon;
	
	/** User who is voting */
	private final String user;
	
	public VoteRequirementCellRenderer() {
		notVoteIcon = new ImageIcon(VoteRequirementCellRenderer.class.getResource("red.png"));
		voteIcon = new ImageIcon(VoteRequirementCellRenderer.class.getResource("green.png"));
		user = ConfigManager.getConfig().getUserName();
	}
	
	/**
	 * Provide the customized GUI for the cell of JList.
	 * The cell would have an icon representing vote status
	 */
	@Override
	public Component getListCellRendererComponent(
						JList<? extends PlanningPokerRequirement> list,
						PlanningPokerRequirement value, 
						int index, 
						boolean isSelected, 
						boolean cellHasFocus) {
		// Set requirement name to the cell
		final String requirementName = value.getName();
		setText(requirementName);

		// Set icon
		boolean isVoted = false;
		for (PlanningPokerVote vote : value.getVotes()) {
			if (vote.getUser().equals(user)) {
				isVoted = true;
			}
		}
		setIcon(isVoted ? voteIcon : notVoteIcon);
	
		// Set background when a cell is selected and not selected
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
		return this;
	}

}
