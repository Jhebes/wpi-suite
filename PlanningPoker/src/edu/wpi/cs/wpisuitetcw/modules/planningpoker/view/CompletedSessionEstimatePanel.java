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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent.StatsTable;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent.textfield.LabelsWithTextField;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.VotePanel;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;

/**
 * CompletedSessionEstimatePanel has a card for final estimation
 * and a table exhibits the stats of a requirement 
 * (mean, mode, median, and standard deviation)
 */
public class CompletedSessionEstimatePanel extends JPanel {

	// The panel that the final estimate panel is placed in.
	private final VotePanel parentPanel;

//	/** A background for a card */
//	// TODO create GUi component for this
//	private ImagePanel finalEstimateCard;
//	
//	// text field that takes the final estimate given by the owner of a session.
//	// TODO create GUi component for this
//	private JTextField finalEstimateField;
//	
//	// Header for the final estimate panel.
//	// TODO move this into GUI component above
//	private final JLabel lblFinalEstimate;
	
	/** Card for final estimation */
	private LabelsWithTextField card;
	
	/** Stats table */
	private StatsTable statsTable;
	
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

		// Create a card
		BufferedImage background;
		try {
			background = ImageIO.read(getClass().getResource("new_card.png"));
		} catch (IOException e) {
			throw new RuntimeException("Cannot read the background image new_card.png");
		}
		card = new LabelsWithTextField(background);
		card.removeTop();
		card.setTextBottom("<html><font color='#3399FF'><b>&nbsp;&nbsp;Final estimation<b></html>");
		
		String currentUserName = ConfigManager.getConfig().getUserName();
		
		if (!currentUserName.equals(parentPanel.getSession().getOwnerUserName())) // Not Owner
			card.getTextField().setEditable(false);

		
		// Set default small text "Final estimation"
		// Enlarge the font when user clicks in
		//setToggleFontSize(card);
		

		card.getTextField().getDocument().addDocumentListener(
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
							Integer.parseInt(card.getTextField().getText());
							parentPanel.getSubmitFinalEstimationButton()
									.setEnabled(true);
						} catch (NumberFormatException n) {
							parentPanel.getSubmitFinalEstimationButton()
									.setEnabled(false);
						}
					}
				});

		card.getTextField().addKeyListener(new KeyListener() {
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
					Integer.parseInt(card.getTextField().getText());
					parentPanel.getSubmitFinalEstimationButton().setEnabled(true);
				} catch (NumberFormatException n) {
					parentPanel.getSubmitFinalEstimationButton().setEnabled(false);
				}

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					JButton submitButton = parentPanel.getSubmitFinalEstimationButton();
					submitButton.doClick();
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
		
		// Create a stats table
		statsTable = new StatsTable();
		
		putGUIComponentsOnPanel();
	}

	private void putGUIComponentsOnPanel() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setLayout(new MigLayout("insets 0, fill", "push[]20[]push", "push[]push"));

		add(card, "height 194!, width 146!");
		add(statsTable, "wrap, gapright 3");
		
//		pnlFinishedReq.add(lblFinalEstimate, "gapbottom 10,wrap");
//		pnlFinishedReq.add(finalEstimateCard,
//				"height 194!, width 146!, gapright 80px");
//		pnlFinishedReq.add(pnlStats);
//
//		this.add(pnlFinishedReq);
	}

	/**
	 * Updates the estimate text field for this newly focused requirement.
	 * 
	 * @param requirement
	 *            The new focused requirement
	 */
	public void updateEstimateTextField(PlanningPokerRequirement requirement) {
		int end = requirement.getFinalEstimate();
		if (end != 0) {
			card.setTextMiddle(Integer.valueOf(requirement.getFinalEstimate()).toString());
		} else {
			card.setTextMiddle("");
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
		final String estimateText = card.getTextMiddle();
		return Integer.parseInt(estimateText);
	}
	
	/**
	 * Assign a value to the mean
	 * @param A value that would be assigned
	 * to the mean of the stats table
	 */
	public void setMean(double mean) {
		statsTable.setMean(mean + "");
	}
	
	/**
	 * Assign a value to the median
	 * @param A value that would be assigned
	 * to the median of the stats table
	 */
	public void setMedian(int median) {
		statsTable.setMedian(median + "");
	}
	
	/**
	 * Assign a value to the mode
	 * @param A value that would be assigned
	 * to the mode of the stats table
	 */
	public void setMode(int mode) {
		statsTable.setMode(mode + "");
	}
	
	/**
	 * Assign a value to the standard deviation
	 * @param A value that would be assigned
	 * to the standard deviation of the stats table
	 */
	public void setStandardDeviation(double sd) {
		statsTable.setStandardDeviation(sd + "");
	}
	
}
