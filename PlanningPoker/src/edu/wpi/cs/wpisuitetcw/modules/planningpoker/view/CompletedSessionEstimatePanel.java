/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;

public class CompletedSessionEstimatePanel extends JPanel {

	// private final JPanel pnlCompletedSession;
	private final GridLayout panelLayout;
	private final JPanel pnlFinalEstimate;
	private final JPanel pnlStats;
	private final JPanel pnlVotes;
	private final JLabel lblVotes;
	private final JLabel lblStats;
	private final JLabel lblFinalEstimate;
	private final JLabel lblMean;
	private final JLabel lblMedian;
	private final JLabel lblMode;
	private DefaultTableModel tableModel;
	private JTable tblVotes;
	private JTextField statsMean;
	private JTextField statsMedian;
	private JTextField statsMode;
	private JTextField finalEstimateField;
	private final Font headerFont;
	private final Font statNameFont;
	private final JButton btnFinalEstimate;
	private int mean;
	private int mode;
	private int median;

	private static final Object[] voteTableColHeaders = { "User", "Vote" };

	/**
	 * Create the panel.
	 */
	public CompletedSessionEstimatePanel() {

		panelLayout = new GridLayout(0, 3);

		/*
		 * Create and set up the 3 panels used to create the CompletedSession
		 * Panel
		 */
		// Used to make the final estimate
		this.setLayout(panelLayout);
		pnlFinalEstimate = new JPanel();
		pnlFinalEstimate.setLayout(new BoxLayout(pnlFinalEstimate,
				BoxLayout.Y_AXIS));
		pnlFinalEstimate.setBorder(BorderFactory.createLineBorder(Color.black));

		// Statistical info of the PP Session
		pnlStats = new JPanel();
		pnlStats.setLayout(new BoxLayout(pnlStats, BoxLayout.Y_AXIS));
		pnlStats.setBorder(BorderFactory.createLineBorder(Color.black));

		// Table of votes for each req
		pnlVotes = new JPanel();
		pnlVotes.setLayout(new BoxLayout(pnlVotes, BoxLayout.Y_AXIS));
		pnlVotes.setBorder(BorderFactory.createLineBorder(Color.black));

		// Initialize the default font for JLabel headers
		headerFont = new Font("TimesRoman", Font.BOLD, 25);

		// Initialize the Headers for the panels.
		lblVotes = new JLabel("Votes");
		lblVotes.setFont(headerFont);
		lblVotes.setAlignmentX(Component.CENTER_ALIGNMENT);

		lblStats = new JLabel("Stats");
		lblStats.setFont(headerFont);
		lblStats.setAlignmentX(Component.CENTER_ALIGNMENT);

		lblFinalEstimate = new JLabel("Final Estimate");
		lblFinalEstimate.setFont(headerFont);
		lblFinalEstimate.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create the Final Estimate Panel
		finalEstimateField = new JTextField(3);
		finalEstimateField.setFont(new Font("TimeRoman", Font.BOLD, 30));
		finalEstimateField
				.setMaximumSize(finalEstimateField.getPreferredSize());
		finalEstimateField.setAlignmentX(Component.CENTER_ALIGNMENT);

		Component verticalStrut = Box.createVerticalStrut(50);

		btnFinalEstimate = new JButton("Submit Final Estimation");
		btnFinalEstimate.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnFinalEstimate.setEnabled(false);

		pnlFinalEstimate.add(lblFinalEstimate);
		pnlFinalEstimate.add(verticalStrut);
		pnlFinalEstimate.add(finalEstimateField);
		pnlFinalEstimate.add(btnFinalEstimate);

		try {
			Integer.parseInt(finalEstimateField.getText());
			btnFinalEstimate.setEnabled(true);
		} catch (NumberFormatException n) {
			JLabel Error = new JLabel("Must input a number");
			Error.setAlignmentX(Component.CENTER_ALIGNMENT);
			pnlFinalEstimate.add(Error);
		}

		// Create the Stats Panel
		statNameFont = new Font("TimesRoman", Font.BOLD, 15);

		lblMean = new JLabel("Mean");
		lblMean.setFont(statNameFont);
		lblMean.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsMean = new JTextField(10);
		// Keeps the text box from filling the entire statBox
		statsMean.setMaximumSize(statsMean.getPreferredSize());
		statsMean.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsMean.setEditable(false);

		lblMedian = new JLabel("Median");
		lblMedian.setFont(statNameFont);
		lblMedian.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsMedian = new JTextField(10);
		// Keeps the text box from filling the entire statBox
		statsMedian.setMaximumSize(statsMedian.getPreferredSize());
		statsMedian.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsMedian.setEditable(false);

		lblMode = new JLabel("Mode");
		lblMode.setFont(statNameFont);
		lblMode.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsMode = new JTextField(10);
		// Keeps the text box from filling the entire statBox
		statsMode.setMaximumSize(statsMode.getPreferredSize());
		statsMode.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsMode.setEditable(false);

		pnlStats.add(lblStats);

		pnlStats.add(lblMean);
		pnlStats.add(statsMean);

		pnlStats.add(lblMedian);
		pnlStats.add(statsMedian);

		pnlStats.add(lblMode);
		pnlStats.add(statsMode);

		// put the completed Session panel together
		this.add(pnlVotes);
		this.add(pnlStats);
		this.add(pnlFinalEstimate);
		this.createTable();
		JScrollPane votesScrollPane = new JScrollPane(tblVotes);
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

	/**
	 * sets the text field for Mean in the completed session view.
	 * 
	 * @param statsMean
	 */
	public void setStatsMean(int statsMean) {
		this.statsMean.setText("" + statsMean + "  ");
	}

	/**
	 * sets the text field for Median in the completed session view.
	 * 
	 * @param statsMedian
	 */
	public void setStatsMedian(int statsMedian) {
		this.statsMedian.setText("" + statsMedian + "  ");
	}

	/**
	 * sets the text field for Mode in the completed session view.
	 * 
	 * @param statsMode
	 */
	public void setStatsMode(int statsMode) {
		this.statsMode.setText("" + statsMode + "  ");
	}

	public void fillTable(PlanningPokerRequirement requirement) {
		// Clear the table model.
		tableModel.setRowCount(0);

		for (PlanningPokerVote vote : requirement.getVotes()) {
			Object[] row = { vote.getUser(), vote.getCardValue() };
			tableModel.addRow(row);
		}
	}
}
