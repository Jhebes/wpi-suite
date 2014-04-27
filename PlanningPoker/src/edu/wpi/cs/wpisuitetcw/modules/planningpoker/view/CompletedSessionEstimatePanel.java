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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.VotePanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.UpdateRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

public class CompletedSessionEstimatePanel extends JPanel {

	// private final JPanel pnlCompletedSession;
	
	//The panel that the final estimate panel is placed in.
	private final VotePanel parentPanel;
	
	// The GridLayout that holds the stats panel, vote panel, and final estimate panel.
	private final GridLayout panelLayout;
	
	// Panel that displays the final estimate text box and submission button
	private final JPanel pnlFinalEstimate;
	
	// Panel that displays the stats of a requirement.
	private final JPanel pnlStats;
	
	// Panel that displays a table of users and their votes for a requirement.
	private final JPanel pnlVotes;
	
	// Header for the votes panel.
	private final JLabel lblVotes;
	
	// Header for the stats panel.
	private final JLabel lblStats;
	
	// Header for the final estimate panel.
	private final JLabel lblFinalEstimate;
	
	// Label of the Mean text field.
	private final JLabel lblMean;
	
	// Label of the Median text field.
	private final JLabel lblMedian;
	
	// Label of the Mode text field.
	private final JLabel lblMode;
	
	// Holds the model to populate the Votes table.
	private DefaultTableModel tableModel;
	
	// Table that displays users and their votes for a requirement.
	private JTable tblVotes;
	
	// text field that displays the mean for a requirements votes.
	private JTextField statsMean;
	
	// text field that displays the median of a requirements votes.
	private JTextField statsMedian;
	
	// text field that displays the mode of a requirements votes.
	private JTextField statsMode;
	
	// text field that takes the final estimate given by the owner of a session.
	private JTextField finalEstimateField;
	
	// The font to be used for headers in this panel.
	private final Font headerFont;
	
	// the font used for statistical labels
	private final Font statNameFont;
	
	// Button to submit the final estimation.
	private final JButton btnFinalEstimate;
	
	// The requirement that has been chosen for analysis and estimation
	private PlanningPokerRequirement focusedRequirement = null;
	
	// Headers for the tblVotes JTable.
	private static final Object[] voteTableColHeaders = { "User", "Vote" };
	
	// The Requirement Manager requirement Model.
	private RequirementModel reqManagerRequirementModel;

	/**
	 * Create the panel.
	 * 
	 * @param parentPanel
	 *            The parent panel containing this panel.
	 */
	public CompletedSessionEstimatePanel(
			final VotePanel parentPanel) {
		this.parentPanel = parentPanel;
		
		// Create a new grid layout that is 3 columns across.
		panelLayout = new GridLayout(0, 3);
		
		// Set the reqManagerRequirementModel field equal to the Instance of the Requirement Model;
		reqManagerRequirementModel = RequirementModel.getInstance();
		

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
		finalEstimateField.getDocument().addDocumentListener(
				new DocumentListener() {
					public void changedUpdate(DocumentEvent e) {
						warn();
					}

					public void removeUpdate(DocumentEvent e) {
						warn();
					}

					public void insertUpdate(DocumentEvent e) {
						warn();
					}

					public void warn() {
						try {
							Integer.parseInt(finalEstimateField.getText());
							btnFinalEstimate.setEnabled(true);
						} catch (NumberFormatException n) {
							btnFinalEstimate.setEnabled(false);
						}
					}
				});

		final Component verticalStrut = Box.createVerticalStrut(50);

		btnFinalEstimate = new JButton("Submit Final Estimation");
		btnFinalEstimate.setAlignmentX(Component.CENTER_ALIGNMENT);
		// btnFinalEstimate.setEnabled(false);

		btnFinalEstimate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final PlanningPokerRequirement focusedReq = CompletedSessionEstimatePanel.this
						.getFocusedRequirement();
				final int estimate = CompletedSessionEstimatePanel.this
						.getEstimate();
				final int correspondingReqID = focusedReq
						.getCorrespondingReqManagerID();
				focusedReq.setFinalEstimate(estimate);

				// Update the Requirement manager requirement estimate.
				final Requirement focusedRequirementManagerRequirement = reqManagerRequirementModel
						.getRequirement(correspondingReqID);
				focusedRequirementManagerRequirement.setEstimate(estimate);

				parentPanel.getSession().save();
				UpdateRequirementController.getInstance().updateRequirement(
						focusedRequirementManagerRequirement);
				ViewEventController.getInstance().refreshTable();
				ViewEventController.getInstance().refreshTree();
			}

		});

		pnlFinalEstimate.add(lblFinalEstimate);
		pnlFinalEstimate.add(verticalStrut);
		pnlFinalEstimate.add(finalEstimateField);
		pnlFinalEstimate.add(btnFinalEstimate);

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

	/**
	 * Populates the vote table with the votes from a requirement
	 * 
	 * @param requirement
	 *            The requirement whose votes to use for this table
	 */
	public void fillTable(PlanningPokerRequirement requirement) {
		// Clear the table model.
		tableModel.setRowCount(0);

		for (PlanningPokerVote vote : requirement.getVotes()) {
			Object[] row = { vote.getUser(), vote.getCardValue() };
			tableModel.addRow(row);
		}
	}

	/**
	 * Updates the estimate text field for this newly focused requirement.
	 * 
	 * @param requirement
	 *            The new focused requirement
	 */
	public void updateEstimateTextField(PlanningPokerRequirement requirement) {
		finalEstimateField.setText(Integer.valueOf(
				requirement.getFinalEstimate()).toString());
	}

	/**
	 * @return The requirement being estimated
	 */
	public PlanningPokerRequirement getFocusedRequirement() {
		return focusedRequirement;
	}

	/**
	 * @param focusedRequirement
	 *            The new requirement to estimate
	 */
	public void setFocusedRequirement(
			PlanningPokerRequirement focusedRequirement) {
		this.focusedRequirement = focusedRequirement;
	}

	/**
	 * @return The estimate as parsed from the textbox
	 */
	public int getEstimate() {
		final String estimateText = finalEstimateField.getText();
		return Integer.parseInt(estimateText);
	}
	

}
