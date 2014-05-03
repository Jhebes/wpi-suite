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

import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.VotePanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

/**
 * Panel for final estimation.
 */
public class CompletedSessionEstimatePanel extends JPanel {

	// The panel that the final estimate panel is placed in.
	private final VotePanel parentPanel;

	// Panel that contains stats and final estimate
	private JPanel pnlFinishedReq;

	// Panel that displays the final estimate text box and submission button
	private ImagePanel pnlFinalEstimate;

	// Panel that displays the stats of a requirement.
	private final JPanel pnlStats;

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
	
	// Label of Standard Deviation;
	private final JLabel lblStandardDeviation;
	
	// Holds the model to populate the Votes table.
	// private DefaultTableModel tableModel;

	// Table that displays users and their votes for a requirement.
	// private JTable tblVotes;

	//private JTable tblVotes;
	
	// text field that displays the mean for a requirements votes.
	//private JTextField statsMean;
	
	// text field that displays the median of a requirements votes.
	//private JTextField statsMedian;
	
	// text field that displays the mode of a requirements votes.
	//private JTextField statsMode;
	
	// text field that displays the standard deviation of a requirment's votes.
	private JTextField statsStandardDeviation;
	
	// text field that takes the final estimate given by the owner of a session.
	private JTextField finalEstimateField;

	// The font to be used for headers in this panel.
	private final Font headerFont;

	// the font used for statistical labels
	private final Font statNameFont;

	// The requirement that has been chosen for analysis and estimation
	private PlanningPokerRequirement focusedRequirement = null;

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
	public CompletedSessionEstimatePanel(final VotePanel parentPanel) {
		this.parentPanel = parentPanel;

		// Set the reqManagerRequirementModel field equal to the Instance of the
		// Requirement Model;
		reqManagerRequirementModel = RequirementModel.getInstance();

		pnlFinishedReq = new JPanel();
		pnlFinishedReq.setLayout(new MigLayout());

		pnlFinalEstimate = new ImagePanel();
		try {
			pnlFinalEstimate = new ImagePanel("new_card.png");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		pnlFinalEstimate.setLayout(new MigLayout());

		/*
		 * Create and set up the 3 panels used to create the CompletedSession
		 * Panel
		 */
		this.setLayout(new MigLayout());

		// Statistical info of the PP Session
		pnlStats = new JPanel();
		pnlStats.setLayout(new MigLayout());
		pnlStats.setBorder(BorderFactory.createEmptyBorder());

		// Initialize the default font for JLabel headers
		headerFont = new Font("SansSerif", Font.BOLD, 25);

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
							parentPanel.getSubmitFinalEstimationButton()
									.setEnabled(true);
						} catch (NumberFormatException n) {
							parentPanel.getSubmitFinalEstimationButton()
									.setEnabled(false);
						}
					}
				});

		finalEstimateField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					parentPanel.getSubmitFinalEstimationButton().doClick();
				}
			}
		});
		
		finalEstimateField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				keyTyped(e);

			}

			@Override
			public void keyReleased(KeyEvent e) {
				keyTyped(e);

			}

			@Override
			public void keyTyped(KeyEvent e) {
				try {
					Integer.parseInt(finalEstimateField.getText());
					parentPanel.getSubmitFinalEstimationButton().setEnabled(true);
				} catch (NumberFormatException n) {
					parentPanel.getSubmitFinalEstimationButton().setEnabled(false);
				}
			}
		});
		// TODO move this code to Vote panel
//
//		final Component verticalStrut = Box.createVerticalStrut(50);
//
//		// Confirm message
//		final JLabel successMsg = new JLabel("Final Estimation Submitted.");
//		successMsg.setAlignmentY(Component.CENTER_ALIGNMENT);
//		successMsg.setVisible(false);
//
		final Component verticalStrut = Box.createVerticalStrut(60);

		pnlFinalEstimate.add(verticalStrut, "wrap");
		pnlFinalEstimate.add(finalEstimateField, "gapleft 30, align center");

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

		// TODO change this to fit the layout. Rob's code
//		statsMode = new JTextField(10);
//		// Keeps the text box from filling the entire statBox
//		statsMode.setMaximumSize(statsMode.getPreferredSize());
//		statsMode.setAlignmentX(Component.CENTER_ALIGNMENT);
//		statsMode.setEditable(false);
//		
		lblStandardDeviation =  new JLabel("Standard Deviation");
		lblStandardDeviation.setFont(statNameFont);
		lblStandardDeviation.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsStandardDeviation = new JTextField(10);
		// Keeps the text box from filling the entire statBox
		statsStandardDeviation.setMaximumSize(statsStandardDeviation.getPreferredSize());
		statsStandardDeviation.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsStandardDeviation.setEditable(false);

		pnlStats.add(lblMean);

		pnlStats.add(lblMean, "gapright 40px");
		pnlStats.add(lblMeanValue, "wrap");

		pnlStats.add(lblMedian, "gapright 40px");
		pnlStats.add(lblMedianValue, "wrap");

		// NEED TO LAYOUT THIS
		//pnlStats.add(statsMode);
		pnlStats.add(lblStandardDeviation);
		pnlStats.add(statsStandardDeviation);

		pnlStats.add(lblMode, "gapright 40px");
		pnlStats.add(lblModeValue);

		pnlFinishedReq.add(lblFinalEstimate, "gapbottom 10,wrap");
		pnlFinishedReq.add(pnlFinalEstimate,
				"height 194!, width 146!, gapright 80px");
		pnlFinishedReq.add(pnlStats);

		this.add(pnlFinishedReq);
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
		// Rob's code TODO change this
		//this.statsMode.setText("" + statsMode + "  ");
	}
	
	/**
	 * sets the text field for standard Deviation in the completed session view
	 * 
	 * @param statsStandardDeviation
	 */
	public void setStatsStandardDeviation(double statsStandardDeviation) {
		this.statsStandardDeviation.setText("" + statsStandardDeviation + "  ");
	}

	/**
	 * Populates the vote table with the votes from a requirement
	 * 
	 * @param requirement
	 *            The requirement whose votes to use for this table
	 */
//	public void fillTable(PlanningPokerRequirement requirement) {
//		// Clear the table model.
//		tableModel.setRowCount(0);
//
//		for (PlanningPokerVote vote : requirement.getVotes()) {
//			Object[] row = { vote.getUser(), vote.getCardValue() };
//			tableModel.addRow(row);
//		}
//	}

	/**
	 * Updates the estimate text field for this newly focused requirement.
	 * 
	 * @param requirement
	 *            The new focused requirement
	 */
	public void updateEstimateTextField(PlanningPokerRequirement requirement) {
		int end = requirement.getFinalEstimate();
		if (end != 0) {
			finalEstimateField.setText(Integer.valueOf(requirement.getFinalEstimate()).toString());
		} else {
			finalEstimateField.setText("");
		}
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
