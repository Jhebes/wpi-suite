/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session.EditActivatedSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.vote.AddVoteController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.CompletedSessionEstimatePanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent.VoteRequirementCellRenderer;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers.DisplayDeckPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

/**
 * Panel for voting.
 */
public class VotePanel extends JPanel {

	private static final String REQ_NAME_LABEL = "Name";
	private static final String REQ_DESC_LABEL = "Description";
	private static final String VOTE_BUTTON_LABEL = "Submit Vote";
	private static final String RIGHT_PANEL_LABEL = "Requirements Detail:";
	private static final String LEFT_PANEL_LABEL = "Session Requirements:";
	private static final String END_SESSION_BUTTON_LABEL = "End Session";
	private static final String NO_DECK_MSG = "<html><font color='red'>No deck. Please enter your vote in the white box</font></html>";

	private static final int MIN_DESC_TEXTBOX_HEIGHT = 80;
	private static final int MIN_VOTE_TEXTFIELD_WIDTH = 120;
	private static final int MIN_VOTE_TEXTFIELD_HEIGHT = 120;
	private static final int MIN_BUTTON_WIDTH = 50;
	private static final int PADDING_RIGHT_PANEL = 10;
	private static final int GAP_BETWEEN_REQ_TEXTBOX_AND_VOTE_TEXTBOX = 20;

	private final PlanningPokerSession session;
	private PlanningPokerRequirement[] reqsList;

	// #################### GUI left components ####################
	/** The left container holding all the requirements' info */
	private JLabel leftPanelLabel;
	private JPanel leftPanel;
	
	/** List of requirements */
	private JScrollPane requirementFrame;
	private JList<PlanningPokerRequirement> reqList;

	// ################### GUI right components ####################
	/** The right container holding all the GUI components */
	private JLabel rightPanelLabel;
	private JPanel rightPanel;

	/** Name of a requirement */
	private JLabel requirementNameLabel;
	private JTextField requirementNameTextbox;

	/** Description of a requirement */
	private JLabel descriptionLabel;
	private JScrollPane descriptionFrame;
	private JTextField descriptionTextbox;

	/** A Panel exhibiting the cards */
	private JScrollPane cardFrame;
	private DisplayDeckPanel cardPanel;

	/** A text field holding the final result */
	private JTextField voteTextField;

	/** Button submit the vote to the database */
	private JButton submitVoteButton;

	/** Final estimation panel */
	private CompletedSessionEstimatePanel finalEstimatePnl;

	// ################# GUI bottom components ####################
	/** A bottom container holding the buttons below this */
	private JPanel bottomPanel;

	/** A button to end a session */
	private JButton endSessionButton;

	/** A button to edit a session */
	private JButton btnEditSession;

	/** A button to cancel a session */
	private JButton cancelSessionButton;

	/** A JLabel informing the card selection mode (single/multiple selection) */
	private JLabel cardSelectionModeLabel;

	/** The name of the currently selected requirement */
	private PlanningPokerRequirement selectedRequirement;

	/**
	 * Construct a SessionInProgressPanel that displays the requirements needed
	 * to vote and provides GUI components to vote
	 */
	public VotePanel(final PlanningPokerSession session) {
		this.session = session;

		setupLeftPanel();
		setupRightPanel();
		setupBottomPanel();

		// Need to add both left and right to the JSplitpane
		final JSplitPane mainView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);

		// Add mainView and the bottom panel to the canvas
		setLayout(new MigLayout());
		add(mainView, "dock center");
		add(bottomPanel, "dock south");

		// Exhibit the information of the 1st requirement
		setupInitData();
	}

	/**
	 * Put the name and description of the 1st requirement in the name and
	 * description text field
	 */
	private void setupInitData() {
		// TODO programmatically select the requirement and set reqName
		// Prevent getting requirement from an empty array list
		if (session.getRequirements().size() > 0) {
			final PlanningPokerRequirement firstReq = session.getRequirements().get(0);
			requirementNameTextbox.setText(firstReq.getName());
			descriptionTextbox.setText(firstReq.getDescription());
			selectedRequirement = firstReq;
			reqList.setSelectionInterval(0, 0);
		}
	}

	/**
	 * Construct the GUI components of the bottom panel
	 */
	private void setupBottomPanel() {
		
		// Create the container
		bottomPanel = new JPanel();
		
		// Don't draw bottom buttons if we're in final estimation
		if (session.isClosed()) {
			return;
		}

		// Create the end session button
		endSessionButton = new JButton(END_SESSION_BUTTON_LABEL);
		endSessionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endSession();
			}

			private void endSession() {
				session.close();
				session.save();
				final List<PlanningPokerRequirement> reqList = session.getRequirements();
				for (PlanningPokerRequirement ppr : reqList) {
					int count = 0;
					int total = 0;
					for (PlanningPokerVote vote : ppr.getVotes()) {
						total += vote.getCardValue();
						count++;
					}
					ppr.setFinalEstimate(total / count);
					ppr.setTotalVotes(count);
				}
				SessionTableModel.getInstance().update();
				closeTab();
			}
		});

		// Create the cancel session button
		cancelSessionButton = new JButton("Cancel Session");
		cancelSessionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				session.cancel();
				session.save();
				SessionTableModel.getInstance().update();
				closeTab();
			}
		});

		if (session.isClosed() || session.isCancelled()) {
			cancelSessionButton.setEnabled(false);
		}
		if (cancelSessionButton.equals(session.getOwnerUserName())) {
			cancelSessionButton.setVisible(true);
		} else {
			cancelSessionButton.setVisible(false);
		}

		final String currentUserName = ConfigManager.getConfig().getUserName();
		if (session.isClosed())
			endSessionButton.setEnabled(false);
		if (currentUserName.equals(session.getOwnerUserName()))
			endSessionButton.setVisible(true);
		else
			endSessionButton.setVisible(false);

		// Extract the requirements from the table provided by
		// ViewSessionTableManager and converts them to list
		final List<String> testReqs = new ArrayList<String>();
		final RequirementTableManager a = new RequirementTableManager();
		final RequirementTableModel v = a.get(this.session.getID());
		final Vector vector = v.getDataVector();
		for (int i = 0; i < vector.size(); ++i) {
			testReqs.add((String) (((Vector) vector.elementAt(i)).elementAt(1)));
		}
		final String[] reqArr = new String[testReqs.size()];
		for (int i = 0; i < testReqs.size(); ++i) {
			reqArr[i] = testReqs.get(i);
		}
		// reqsViewTable.getColumnModel().getColumn(1).setResizable(false);
		this.getReqsViewTable();

		// Create an edit session button
		btnEditSession = new JButton("Edit Session");
		btnEditSession.addActionListener(new EditActivatedSessionController(session, this));
		if (session.isHasVoted()) {
			btnEditSession.setEnabled(false);
		}

		// Create a submit vote button
		submitVoteButton = new JButton(VOTE_BUTTON_LABEL);
		submitVoteButton.addActionListener(new AddVoteController(this, this.session));

		// Create a JLabel holding the card selection mode
		cardSelectionModeLabel = new JLabel();
		if (session.getDeck() != null && session.getDeck().getMaxSelection() == 1) {
			cardSelectionModeLabel.setText("Single selection deck");
		} else {
			cardSelectionModeLabel.setText("Multiple selection deck");
		}

		addGUIComponentsToBottomPanel();

	}

	/*
	 * Add end session button, edit session button, and cancel session button to
	 * the bottom panel
	 */
	private void addGUIComponentsToBottomPanel() {
		bottomPanel.setLayout(new MigLayout("fillx", "", "5[]5"));
		bottomPanel.add(endSessionButton, "left, wmin " + MIN_BUTTON_WIDTH + "px, split3");
		bottomPanel.add(btnEditSession, "left, wmin " + MIN_BUTTON_WIDTH + "px");
		bottomPanel.add(cancelSessionButton, "left, wmin " + MIN_BUTTON_WIDTH + "px");
		bottomPanel.add(cardSelectionModeLabel, "left, wmin " + MIN_BUTTON_WIDTH + "px");
		bottomPanel.add(submitVoteButton, "right");
	}

	/*
	 * Construct the left panel and its GUI component: a JLabel and a JList
	 */
	private void setupLeftPanel() {
		leftPanel = new JPanel();
		leftPanelLabel = new JLabel(LEFT_PANEL_LABEL);

		// TODO: sleep
		final List<PlanningPokerRequirement> reqs = session.getRequirements();
		final DefaultListModel<PlanningPokerRequirement> requirementModel = 
				new DefaultListModel<PlanningPokerRequirement>();
		for (PlanningPokerRequirement ppr : reqs) {
			requirementModel.addElement(ppr);
		}

		reqList = new JList<PlanningPokerRequirement>(requirementModel);
		reqList.setBackground(Color.WHITE);
		reqList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		reqList.setAlignmentX(LEFT_ALIGNMENT);
		reqList.setSelectedIndex(0);
		reqList.setCellRenderer(new VoteRequirementCellRenderer());
		
		// Change the red light
		//requirementModel.addListDataListener(new NewVoteListener(reqList));

		
		reqList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Check to see if user double clicked
				if (e.getClickCount() == 1) {
					final PlanningPokerRequirement requirement = reqList.getModel().getElementAt(reqList.getSelectedIndex());

					if (requirement.getName() == null) {
						requirementNameTextbox.setText(" ");
					} else {
						requirementNameTextbox.setText(requirement.getName());
					}
					if (requirement.getDescription() == null) {
						descriptionTextbox.setText(" ");
					} else {
						descriptionTextbox.setText(requirement.getDescription());
					}
					
					if (session.isClosed()) {
						finalEstimatePnl.setFocusedRequirement(requirement);
						finalEstimatePnl.setStatsMean(requirement.getMean());
						finalEstimatePnl.setStatsMedian(requirement.getMedian());
						finalEstimatePnl.setStatsMode(requirement.getMode());
						finalEstimatePnl.fillTable(requirement);
						finalEstimatePnl.updateEstimateTextField(requirement);
						updateUI();
					} else {
						final PlanningPokerVote vote = requirement.getVoteByUser(ConfigManager.getConfig().getUserName());
						if (vote != null) {
							setVoteTextFieldWithValue(vote.getCardValue());
							updateUI();
						}
					}
				}
			}
		});

		// Put the list of requirement in a scroll pane
		requirementFrame = new JScrollPane();
		requirementFrame.setViewportView(reqList);

		addGUIComponentsOnLeftPanel();
	}

	/*
	 * Add the GUI component to the left panel
	 */
	private void addGUIComponentsOnLeftPanel() {
		leftPanel.setLayout(new MigLayout("fill, center"));

		leftPanel.add(leftPanelLabel, "gapleft 50px, dock north");
		leftPanel.add(requirementFrame, "width 250::, growy, dock center");
	}

	private void setupRightPanel() {
		rightPanel = new JPanel();

		// Create a label for right panel
		rightPanelLabel = new JLabel(RIGHT_PANEL_LABEL);

		// Create a requirement name text box
		requirementNameLabel = new JLabel(REQ_NAME_LABEL);
		requirementNameTextbox = new JTextField();
		requirementNameTextbox.setEditable(false);
		requirementNameTextbox.setBackground(Color.WHITE);

		// Create a requirement description text box
		descriptionLabel = new JLabel(REQ_DESC_LABEL);
		descriptionFrame = new JScrollPane();
		descriptionTextbox = new JTextField();
		descriptionTextbox.setEditable(false);
		descriptionTextbox.setBackground(Color.WHITE);
		descriptionFrame.setViewportView(descriptionTextbox);

		// Create a text field to store the final vote result
		voteTextField = new JTextField(3);
		voteTextField.setFont(new Font("SansSerif", Font.BOLD, 60));
		voteTextField.setHorizontalAlignment(JTextField.CENTER);

		// if the session has a deck, we can't let the user submit a vote
		// manually
		if (session.getDeck() != null) {
			voteTextField.setEnabled(false);
			// Create a deck panel
			cardPanel = new DisplayDeckPanel(session.getDeck(), this);
			cardFrame = new JScrollPane();
			cardFrame.setViewportView(cardPanel);
		} else {
			voteTextField.setEnabled(true);
		}

		addGUIComponentsOnRightPanel();
	}

	/*
	 * Add the requirement name and desciption text field, vote submit button,
	 * vote text field, and their labels on the right panel
	 */
	private void addGUIComponentsOnRightPanel() {
		// Add the padding around the right panel
		rightPanel.setLayout(new MigLayout("insets " + PADDING_RIGHT_PANEL + " " + PADDING_RIGHT_PANEL + " "
				+ PADDING_RIGHT_PANEL + " " + PADDING_RIGHT_PANEL + ", fill"));

		// Add the label of the panel
		rightPanel.add(rightPanelLabel, "center, span");

		// Add the requirement name and its label
		rightPanel.add(requirementNameLabel, "growx, left, wrap");
		rightPanel.add(requirementNameTextbox, "growx, gapright " + GAP_BETWEEN_REQ_TEXTBOX_AND_VOTE_TEXTBOX
				+ "px, wrap");

		// Add the requirement description box and its label
		rightPanel.add(descriptionLabel, "growx, left, wrap");
		rightPanel.add(descriptionFrame, "hmin " + MIN_DESC_TEXTBOX_HEIGHT + "px, " + "growx, " + "gapright"
				+ GAP_BETWEEN_REQ_TEXTBOX_AND_VOTE_TEXTBOX + "px, " + "wrap");

		// Add the card panel or final estimation GUI
		finalEstimatePnl = new CompletedSessionEstimatePanel(this);
		finalEstimatePnl.setAlignmentX(Component.CENTER_ALIGNMENT);
		if (this.session.isClosed()) {
			rightPanel.add(finalEstimatePnl);
		} else {
			if (cardFrame != null) {
				rightPanel.add(cardFrame, "hmin 250px, grow, dock south");
			} else {
				final JLabel messageLabel = new JLabel(NO_DECK_MSG);
				rightPanel.add(messageLabel, "gapleft 150px, hmin 250px, grow, dock south");
			}
			
			// Add the vote text field to the right side
			rightPanel.add(voteTextField, "wmin " + MIN_VOTE_TEXTFIELD_WIDTH + "px, " + "hmin " + MIN_VOTE_TEXTFIELD_HEIGHT
					+ "px, " + "dock east, " + "gaptop " + PADDING_RIGHT_PANEL + "px, " + "gapright " + PADDING_RIGHT_PANEL
					+ "px");
		}		

	}

	private void closeTab() {
		ViewEventManager.getInstance().removeTab(this);
	}

	/**
	 * 
	 * @return Session Model for this Panel
	 */
	public PlanningPokerSession getSession() {
		return session;
	}

	/**
	 * 
	 * @return List of Reqs for this session
	 */
	public PlanningPokerRequirement[] getReqsList() {
		return reqsList;
	}

	/**
	 * 
	 * @param reqsList
	 */
	public void setReqsList(PlanningPokerRequirement[] reqsList) {
		this.reqsList = reqsList;
	}

	/**
	 * 
	 * @return Requirement selected in the list
	 */
	public PlanningPokerRequirement getSelectedRequirement() {
		return selectedRequirement;
	}

	/**
	 * 
	 * @return vote parsed as an integer
	 */
	public int getVote() {
		if (this.session.getDeck() == null) {
			return Integer.parseInt(voteTextField.getText());
		} else {
			return cardPanel.getVoteValue();
		}
	}

	/**
	 * sets the reqsViewTable with the appropriate information
	 */

	public void getReqsViewTable() {
	}

	public void disableEditSession() {
		btnEditSession.setEnabled(false);
	}

	/**
	 * @return the voteTextField
	 */
	public JTextField getVoteTextField() {
		return voteTextField;
	}

	/**
	 * setter for voteTextField
	 * 
	 * @param voteTextField
	 */
	public void setVoteTextFieldWithValue(int value) {
		this.voteTextField.setText(Integer.toString(value));
	}
	
	/**
	 * Return the GUI list of requirements
	 * @return Return the GUI list of requirements
	 */
	public JList<PlanningPokerRequirement> getRequirementList() {
		return reqList;
	}
}