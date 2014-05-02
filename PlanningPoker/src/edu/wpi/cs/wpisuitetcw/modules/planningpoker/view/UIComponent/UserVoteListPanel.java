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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.UserStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.VotePanel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class UserVoteListPanel extends JPanel {

	// Header for the votes panel.
	private final JLabel lblVotes;

	// focused requirement
	private PlanningPokerRequirement focusedRequirement;
	
	// The panel that the final estimate panel is placed in.
	private final VotePanel parentPanel;

	// The font to be used for headers in this panel.
	private final Font headerFont;

	// Headers for the tblVotes JTable.
	private static final Object[] voteTableColHeaders = { "User", "Vote" };

	// Holds the model to populate the Votes table.
	private DefaultTableModel tableModel;

	// Table that displays users and their votes for a requirement.
	private JTable tblVotes;

	// Panel that displays a table of users and their votes for a requirement.
	private final JPanel pnlVotes;

	// The GridLayout that holds the stats panel, vote panel, and final estimate
	// panel.
	private final GridLayout panelLayout;

	public UserVoteListPanel(final VotePanel parentPanel) {
		this.parentPanel = parentPanel;

		focusedRequirement = null;
		
		// Create a new grid layout that is 3 columns across.
		panelLayout = new GridLayout(1, 1);

		// Initialize the default font for JLabel headers
		headerFont = new Font("TimesRoman", Font.BOLD, 25);

		// Table of votes for each req
		pnlVotes = new JPanel();
		pnlVotes.setLayout(new BoxLayout(pnlVotes, BoxLayout.Y_AXIS));
		pnlVotes.setBorder(BorderFactory.createEmptyBorder());

		// Initialize the Headers for the panels.
		lblVotes = new JLabel("Votes");
		lblVotes.setFont(headerFont);
		lblVotes.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.add(pnlVotes);
		this.createTable();
		final JScrollPane votesScrollPane = new JScrollPane(tblVotes);
		tblVotes.setFillsViewportHeight(true);
		pnlVotes.add(lblVotes);
		pnlVotes.add(votesScrollPane);
	}

	public void createTable() {
		tableModel = new DefaultTableModel(new Object[0][0],
				voteTableColHeaders);
		tblVotes = new JTable(tableModel) {
			private static final long serialVersionUID = -1948465013583690161L;

			@Override
			// disables the ability to edit cells
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
	}

	public void fillTable() {
		// Clear the table model.
		tableModel.setRowCount(0);
		//System.out.println(UserStash.getInstance().getUsers().size());
		//for (User user : UserStash.getInstance().getUsers()) {
			for (PlanningPokerVote vote : focusedRequirement.getVotes()) {
				Object[] row = { vote.getUser(), vote.getCardValue() };
				tableModel.addRow(row);
		/*		if (requirement.hasUserVoted(user.getUsername())) {
					setBackground(new Color(0x22, 0xff, 0x33));
				} else {
					setBackground(new Color(0xff, 0x88, 0x99));
				}*/
			}
		//}
	}

	/**
	 * @return the focusedRequirement
	 */
	public PlanningPokerRequirement getFocusedRequirement() {
		return focusedRequirement;
	}

	/**
	 * @param focusedRequirement the focusedRequirement to set
	 */
	public void setFocusedRequirement(PlanningPokerRequirement focusedRequirement) {
		this.focusedRequirement = focusedRequirement;
	}
	
	
	
}
