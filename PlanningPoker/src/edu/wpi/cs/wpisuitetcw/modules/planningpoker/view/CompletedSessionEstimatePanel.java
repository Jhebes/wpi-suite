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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.VotePanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.UpdateRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

/**
 * Panel for final estimation.
 */
public class CompletedSessionEstimatePanel extends JPanel {

	// private final JPanel pnlCompletedSession;
	
	//The panel that the final estimate panel is placed in.
	private final VotePanel parentPanel;
	
/*	// The GridLayout that holds the stats panel, vote panel, and final estimate panel.
	private final GridLayout panelLayout;*/
	
	// Panel that contains stats and final estimate
	private JPanel pnlFinishedReq;
	
	// Panel that displays the final estimate text box and submission button
	private ImagePanel pnlFinalEstimate;
	
	// Panel that displays the stats of a requirement.
	private final JPanel pnlStats;
	
	// Panel that displays a table of users and their votes for a requirement.
	private final JPanel pnlVotes;
	
	/*// Label to hold the card picture for final estimate
	private final JLabel card;*/
	
	// Header for the votes panel.
	private final JLabel lblVotes;
	
	// Header for the final estimate panel.
	private final JLabel lblFinalEstimate;
	
	// Label of the Mean 
	private final JLabel lblMean;
	
	// Label of the Median 
	private final JLabel lblMedian;
	
	// Label of the Mode itself
	private final JLabel lblMode;
	
	// Label of the Mean itself
	private JLabel lblMeanValue;
	
	// Label of the Median itself
	private JLabel lblMedianValue;
	
	// Label of the Mode text field.
	private JLabel lblModeValue;
	
	
	// Holds the model to populate the Votes table.
	private DefaultTableModel tableModel;
	
	// Table that displays users and their votes for a requirement.
	private JTable tblVotes;
	
	// text field that takes the final estimate given by the owner of a session.
	private JTextField finalEstimateField;
	
	// The font to be used for headers in this panel.
	private final Font headerFont;
	
	// the font used for statistical labels
	private final Font statNameFont;
	
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
	 * @throws IOException 
	 */
	@SuppressWarnings("serial")
	public CompletedSessionEstimatePanel(
			final VotePanel parentPanel){
		this.parentPanel = parentPanel;
		
		// Set the reqManagerRequirementModel field equal to the Instance of the Requirement Model;
		reqManagerRequirementModel = RequirementModel.getInstance();
		
		pnlFinishedReq = new JPanel();
		pnlFinishedReq.setLayout(new MigLayout());
		
		pnlFinalEstimate = new ImagePanel();
		try {
			pnlFinalEstimate = new ImagePanel("new_card.png");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		pnlFinalEstimate.setLayout(new MigLayout());
		
		
		/*
		 * Create and set up the 3 panels used to create the CompletedSession
		 * Panel
		 */
		this.setLayout(new MigLayout());
		
		/*pnlFinalEstimate = new JPanel();
		pnlFinalEstimate.setLayout(new BoxLayout(pnlFinalEstimate,
				BoxLayout.Y_AXIS));
		pnlFinalEstimate.setBorder(BorderFactory.createEmptyBorder());*/

		// Statistical info of the PP Session
		pnlStats = new JPanel();
		pnlStats.setLayout(new MigLayout());
		pnlStats.setBorder(BorderFactory.createEmptyBorder());

		// Table of votes for each req
		pnlVotes = new JPanel();
		pnlVotes.setLayout(new BoxLayout(pnlVotes, BoxLayout.Y_AXIS));
		pnlVotes.setBorder(BorderFactory.createEmptyBorder());

		// Initialize the default font for JLabel headers
		headerFont = new Font("SansSerif", Font.BOLD, 25);

		// Initialize the Headers for the panels.
		lblVotes = new JLabel("Votes");
		lblVotes.setFont(headerFont);
		lblVotes.setAlignmentX(Component.CENTER_ALIGNMENT);

		lblFinalEstimate = new JLabel("Final Estimate");
		lblFinalEstimate.setFont(headerFont);
		lblFinalEstimate.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create the Final Estimate Panel
		finalEstimateField = new JTextField(3);
		finalEstimateField.setFont(new Font("SansSerif", Font.BOLD, 30));
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
							parentPanel.getSubmitFinalEstimationButton().setEnabled(true);
						} catch (NumberFormatException n) {
							parentPanel.getSubmitFinalEstimationButton().setEnabled(false);
						}
					}
				});

		final Component verticalStrut = Box.createVerticalStrut(97);
		//pnlFinalEstimate.add(lblFinalEstimate, "center, wrap");
		pnlFinalEstimate.add(verticalStrut, "wrap");
		pnlFinalEstimate.add(finalEstimateField, "wrap");
		
		
		// Create the Stats Panel
		statNameFont = new Font("SansSerif", Font.BOLD, 15);
		
		lblMeanValue = new JLabel("");
		lblMean = new JLabel("Mean");
		lblMean.setFont(statNameFont);
		lblMean.setAlignmentX(Component.CENTER_ALIGNMENT);

		lblMedianValue = new JLabel("");
		lblMedian = new JLabel("Median");
		lblMedian.setFont(statNameFont);
		lblMedian.setAlignmentX(Component.CENTER_ALIGNMENT);

		lblModeValue = new JLabel("");
		lblMode = new JLabel("Mode");
		lblMode.setFont(statNameFont);
		lblMode.setAlignmentX(Component.CENTER_ALIGNMENT);

		pnlStats.add(lblMean, "gapright 20px");
		pnlStats.add(lblMeanValue, "wrap");

		pnlStats.add(lblMedian, "gapright 20px");
		pnlStats.add(lblMedianValue, "wrap");

		pnlStats.add(lblMode, "gapright 20px");
		pnlStats.add(lblModeValue);

		
		pnlFinishedReq.add(pnlFinalEstimate, "height 194!, width 146!");
		pnlFinishedReq.add(pnlStats);
		
		// put the completed Session panel together
		//this.add(pnlVotes, "growy , wrap");
		this.add(pnlFinishedReq);
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
	 * sets the JLabel for Mean in the completed session view.
	 * 
	 * @param statsMean
	 */
	public void setStatsMean(int statsMean) {
		lblMeanValue.setText("" + statsMean);
	}

	/**
	 * sets the JLabel for Median in the completed session view.
	 * 
	 * @param statsMedian
	 */
	public void setStatsMedian(int statsMedian) {
		lblMedianValue.setText("" + statsMedian);
	}

	/**
	 * sets the JLabel for Mode in the completed session view.
	 * 
	 * @param statsMode
	 */
	public void setStatsMode(int statsMode) {
		lblModeValue.setText("" + statsMode);
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
