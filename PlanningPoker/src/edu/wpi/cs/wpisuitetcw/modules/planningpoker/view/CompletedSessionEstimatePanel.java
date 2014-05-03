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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.VotePanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

/**
 * CompletedSessionEstimatePanel has a card for final estimation
 * and a table exhibits the stats of a requirement 
 * (mean, mode, median, and standard deviation)
 */
public class CompletedSessionEstimatePanel extends JPanel {

	// The panel that the final estimate panel is placed in.
	private final VotePanel parentPanel;

	/** A background for a card */
	// TODO create GUi component for this
	private ImagePanel finalEstimateCard;
	
	// text field that takes the final estimate given by the owner of a session.
	// TODO create GUi component for this
	private JTextField finalEstimateField;
	
	// Header for the final estimate panel.
	// TODO move this into GUI component above
	private final JLabel lblFinalEstimate;

	/** Mean value and its label*/
	private final JLabel meanLabel;
	private JLabel lblMeanValue;

	/** Median value and its label */
	private final JLabel medianLabel;
	private JLabel lblMedianValue;

	/** Mode value and its label */
	private final JLabel modeLabel;
	private JLabel lblModeValue;
	
	/** Standard Deviation and its label */
	private final JLabel standardDeviationLabel;
	private final JLabel standardDeviation;

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


		finalEstimateCard = new ImagePanel();
		try {
			finalEstimateCard = new ImagePanel("new_card.png");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

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

		// Create the Stats Panel
		statNameFont = new Font("SansSerif", Font.BOLD, 15);

		// Create label for MEAN
		lblMeanValue = new JLabel("TEST");
		meanLabel = new JLabel("Mean");
		meanLabel.setFont(statNameFont);
		meanLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create label for MEDIAN
		lblMedianValue = new JLabel("TEST");
		medianLabel = new JLabel("Median");
		medianLabel.setFont(statNameFont);
		medianLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create label for MODE
		lblModeValue = new JLabel("TEST");
		modeLabel = new JLabel("Mode");
		modeLabel.setFont(statNameFont);
		modeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create label for STANDARD DEVIATION
		standardDeviation = new JLabel("TEST");
		standardDeviationLabel = new JLabel("Standard Deviation");
		standardDeviationLabel.setFont(statNameFont);
		standardDeviationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		putGUIComponentsOnPanel();
	}

	private void putGUIComponentsOnPanel() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setLayout(new MigLayout("insets 0, fill", "push[]push[][]push", "push[]0[]0[]0[]push"));

		add(finalEstimateCard, "height 194!, width 146!, spany 5");
		
		add(meanLabel);
		add(lblMeanValue, "wrap");
		add(medianLabel);
		add(lblMedianValue, "wrap");
		add(modeLabel);
		add(lblModeValue, "wrap");
		add(standardDeviationLabel);
		add(standardDeviation, "wrap");
		

		// TEST SET COLOR
		meanLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
		medianLabel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		modeLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		standardDeviationLabel.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		
//		pnlFinishedReq.add(lblFinalEstimate, "gapbottom 10,wrap");
//		pnlFinishedReq.add(finalEstimateCard,
//				"height 194!, width 146!, gapright 80px");
//		pnlFinishedReq.add(pnlStats);
//
//		this.add(pnlFinishedReq);
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
		// TODO fix the method
		//this.statsStandardDeviation.setText("" + statsStandardDeviation + "  ");
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
