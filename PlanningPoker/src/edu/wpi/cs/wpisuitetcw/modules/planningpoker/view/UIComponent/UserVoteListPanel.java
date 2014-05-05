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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;

/**
 * UserVoteListPanel exhibits the users and
 * their votes
 *
 */
public class UserVoteListPanel extends JPanel {

	private static final Object[] COLUMN_LABELS = { "User", "Vote" };
	private static final int VOTE_COLUMN_MAX_WIDTH = 100;
	
	/** Header for the votes panel */
	private final JLabel lblVotes;

	// Holds the model to populate the Votes table.
	private DefaultTableModel tableModel;

	// Table that displays users and their votes for a requirement.
	private JScrollPane tableScrollFrame;
	private JTable tblVotes;
	
	// focused requirement
	private PlanningPokerRequirement focusedRequirement;

	public UserVoteListPanel() {
		focusedRequirement = null;

		// Create title for the panel
		lblVotes = new JLabel("Votes");

		createTable();
		
		setLayout(new MigLayout("insets 0, fill", "", "[]10[grow]"));
		add(lblVotes, "left, wrap");
		add(tableScrollFrame, "grow");
	}

	public void createTable() {
		tableModel = new DefaultTableModel(new Object[0][0], COLUMN_LABELS);
		tblVotes = new JTable(tableModel) {
			private static final long serialVersionUID = -1948465013583690161L;

			@Override
			// disables the ability to edit cells
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		tblVotes.getColumnModel().getColumn(1).setMaxWidth(VOTE_COLUMN_MAX_WIDTH);
		tblVotes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblVotes.setFillsViewportHeight(true);
		
		// Set the value in the vote column to center
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tblVotes.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		
		// Prevent user moving the column
		tblVotes.getTableHeader().setReorderingAllowed(false);
		// Prevent user resizing the columns
		tblVotes.getTableHeader().setEnabled(false);
		
		tableScrollFrame = new JScrollPane(tblVotes);
	}

	public void fillTable() {
		if (focusedRequirement == null) {
			return;
		}
		// Clear the table model.
		tableModel.setRowCount(0);
		// System.out.println(UserStash.getInstance().getUsers().size());
		// for (User user : UserStash.getInstance().getUsers()) {
		for (PlanningPokerVote vote : focusedRequirement.getVotes()) {
			Object[] row = { vote.getUser(), vote.getCardValue() };
			tableModel.addRow(row);
			/*
			 * if (requirement.hasUserVoted(user.getUsername())) {
			 * setBackground(new Color(0x22, 0xff, 0x33)); } else {
			 * setBackground(new Color(0xff, 0x88, 0x99)); }
			 */
		}
		// }
	}

	/**
	 * @return the focusedRequirement
	 */
	public PlanningPokerRequirement getFocusedRequirement() {
		return focusedRequirement;
	}

	/**
	 * @param focusedRequirement
	 *            the focusedRequirement to set
	 */
	public void setFocusedRequirement(
			PlanningPokerRequirement focusedRequirement) {
		this.focusedRequirement = focusedRequirement;
	}

}
