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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
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
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent.NameDescriptionPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent.UserVoteListPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent.VoteRequirementCellRenderer;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers.DisplayDeckPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.UpdateRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

/**
 * Panel for voting.
 */
public class VotePanel extends JPanel {

	private static final String REQ_NAME_LABEL = "Requirement Name";
	private static final String REQ_DESC_LABEL = "Description";
	private static final String VOTE_BUTTON_LABEL = "Submit Vote";
	private static final String LEFT_PANEL_LABEL = "Session Requirements:";
	private static final String SESSION_LABEL = "Session:";
	private static final String SESSION_NAME_LABEL = "Name:";
	private static final String SESSION_DESC_LABEL = "Description:";
	private static final String END_SESSION_BUTTON_LABEL = "End Session";
	private static final String NO_DECK_MSG = "<html><font color='red'>No deck. Please enter your vote in the white box</font></html>";
	private static final String CANCELLED_SESSION = "This session has been cancelled.";
	
	private static final int DEFAULT_INSETS = 20;
	private static final int DEFAULT_HEIGHT = 26;
	private static final int MIN_VOTE_TEXTFIELD_WIDTH = 118;
	private static final int MIN_VOTE_TEXTFIELD_HEIGHT = 118;
	private static final int MIN_BUTTON_WIDTH = 50;
	private static final int VERTICAL_PADDING_RIGHT_PANEL = 10;
	private static final int HORIZONTAL_PADDING_RIGHT_PANEL = 20;

	private final PlanningPokerSession session;
	private PlanningPokerRequirement[] reqsList;

	// #################### GUI left components ####################
	/** The left container holding all the requirements' info */
	private JLabel sessionLabel;
	private JLabel sessionNameLabel;
	private JLabel sessionNameValueLabel;
	private JLabel sessionDescLabel;
	private JLabel sessionDescValueLabel;
	private JLabel leftPanelLabel;
	private JSplitPane leftPanel;
	private JPanel topLeftPanel;
	private JPanel bottomLeftPanel;
	
	/** List of requirements */
	private JScrollPane requirementFrame;
	private JList<PlanningPokerRequirement> reqList;

	// ################### GUI right components ####################
	/** The right container holding all the GUI components */
	private JPanel rightPanel;
	
	/** The name and description text box */
	private NameDescriptionPanel nameDescriptionPanel;

	/** A Panel exhibiting the cards */
	private JScrollPane cardFrame;
	private DisplayDeckPanel cardPanel;

	/** A text field holding the final result */
	private JTextField voteTextField;
	private JLabel errorMsg;

	/** Button submit the vote to the database */
	private JButton submitVoteButton;

	/** Final estimation panel */
	private CompletedSessionEstimatePanel finalEstimatePnl;
	
	/** Vote Panel */
	private UserVoteListPanel userVotePanel;

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
	
	/** A button to submit the final estimation */
	private JButton submitFinalEstimationButton;

	
	// ##################### DATA #########################
	/** The name of the currently selected requirement */
	private PlanningPokerRequirement selectedRequirement;
	private int selectedReqIndex;

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
		add(bottomPanel, "dock south, height 45px!");

		// Exhibit the information of the 1st requirement
		setupInitData();
	}

	/**
	 * Put the name and description of the 1st requirement in the name and
	 * description text field
	 */
	private void setupInitData() {
		if (session.getRequirements().size() > 0) {
			final PlanningPokerRequirement firstReq = session.getRequirements().get(0);
			String requirementName = firstReq.getName();
			String requirementDescription = firstReq.getDescription();
			
			// Auto select the first requirement
			selectedRequirement = firstReq;
			selectedReqIndex = 0;
			reqList.setSelectionInterval(0, 0);
			
			// Populate name and description of the first requirement
			nameDescriptionPanel.setName(requirementName);
			nameDescriptionPanel.setDescription(requirementDescription);
			
			// Show the vote of the first requirement if it has
			final PlanningPokerVote vote = firstReq.getVoteByUser(ConfigManager.getConfig().getUserName());
			if (vote != null) {
				setVoteTextFieldWithValue(vote.getCardValue());
			}
			
			// Populate the vote and stats of the first requirement when the session's closed
			if (session.isClosed()) {
				nameDescriptionPanel.setDescription(requirementDescription);
				userVotePanel.setFocusedRequirement(selectedRequirement);
				userVotePanel.fillTable();
				finalEstimatePnl.setFocusedRequirement(selectedRequirement);
				finalEstimatePnl.setMean(selectedRequirement.getMean());
				finalEstimatePnl.setMedian(selectedRequirement.getMedian());
				finalEstimatePnl.setMode(selectedRequirement.getMode());
				finalEstimatePnl.updateEstimateTextField(selectedRequirement);
				disableSubmitBtn();
			}
		}
	}

	/**
	 * Construct the GUI components of the bottom panel
	 */
	private void setupBottomPanel() {

		// Create the container
		bottomPanel = new JPanel();
		
		this.submitFinalEstimationButton = new JButton("Submit");
		this.submitFinalEstimationButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {				
				final PlanningPokerRequirement focusedReq = reqList.getSelectedValue();
				final int estimate = finalEstimatePnl.getEstimate();
				final int correspondingReqID = focusedReq
						.getCorrespondingReqManagerID();
				focusedReq.setFinalEstimate(estimate);

				// Update the Requirement manager requirement estimate.
				final Requirement focusedRequirementManagerRequirement = RequirementModel.getInstance().getRequirement(correspondingReqID);
				focusedRequirementManagerRequirement.setEstimate(estimate);

				getSession().save();
				UpdateRequirementController.getInstance().updateRequirement(
						focusedRequirementManagerRequirement);
				ViewEventController.getInstance().refreshTable();
				ViewEventController.getInstance().refreshTree();
				
//				successMsg.setVisible(true);
//				pnlFinalEstimate.add(successMsg);
//				repaint();
			}
		});

		// Create the end session button
		endSessionButton = new JButton(END_SESSION_BUTTON_LABEL);
		endSessionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				endSession();
			}

			private void endSession() {
				if (!allVoted()) // Unvoted Req's
					// Warn the user and cancel if they change their mind
					if (JOptionPane.showConfirmDialog((Component) null, 
							"You have requirements without votes." +
									"Are you sure you wish to end the session?",
							"Are you sure?", JOptionPane.YES_NO_OPTION) != 0)
						return;
				
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
					if(count > 0){
						ppr.setFinalEstimate(total / count);
					} else {
						ppr.setFinalEstimate(0);
					}
						ppr.setTotalVotes(count);
				}
				closeTab();
				openFinalEstimation();
				SessionTableModel.getInstance().update();
				
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

		// enabling buttons by checking the ownership of the session
		String currentUserName = ConfigManager.getConfig().getUserName();

		if (session.isClosed() || session.isCancelled()) {
			cancelSessionButton.setEnabled(false);
		}

		// only owner has the ability to cancel a session
		if (currentUserName.equals(session.getOwnerUserName())) {
			cancelSessionButton.setVisible(true);
		} else {
			cancelSessionButton.setVisible(false);
		}

		// only owner has the ability to close a session
		if (session.isClosed()) {
			endSessionButton.setEnabled(false);			
		}
		if (currentUserName.equals(session.getOwnerUserName())) {
			endSessionButton.setVisible(true);
		} else {
			endSessionButton.setVisible(false);
		}

		// Extract the requirements from the table provided by
		// ViewSessionTableManager and converts them to list
		// TODO WHAT IS THIS?
		final List<String> testReqs = new ArrayList<String>();
		final RequirementTableManager a = new RequirementTableManager();
		final RequirementTableModel v = a.get(session.getID());
		final Vector vector = v.getDataVector();
		for (int i = 0; i < vector.size(); ++i) {
			testReqs.add((String) (((Vector) vector.elementAt(i)).elementAt(1)));
		}
		final String[] reqArr = new String[testReqs.size()];
		for (int i = 0; i < testReqs.size(); ++i) {
			reqArr[i] = testReqs.get(i);
		}

		// Create an edit session button
		btnEditSession = new JButton("Edit Session");
		btnEditSession.addActionListener(new EditActivatedSessionController(
				session, this));

		// user can only edit the session if the session is not voted and user
		// is the owner
		if (!currentUserName.equals(session.getOwnerUserName())) {
			btnEditSession.setVisible(false);
		} else if (session.isHasVoted()) {
			btnEditSession.setEnabled(false);
		}

		// Create a submit vote button
		submitVoteButton = new JButton(VOTE_BUTTON_LABEL);
		submitVoteButton.addActionListener(new AddVoteController(this, session));

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
		bottomPanel.setLayout(new MigLayout("inset 5 "
								 				 + DEFAULT_INSETS + " "
												 + "5 " 
												 + DEFAULT_INSETS + ", fill", 
											"", "push[]push"));
		
		if (session.isClosed()) {
			bottomPanel.add(submitFinalEstimationButton, "align center");
		} else if(session.isCancelled()){
			//add no buttons to bottom panel
		} else {
			bottomPanel.add(endSessionButton, "left, "
											+ "wmin " + MIN_BUTTON_WIDTH + "px, "
											+ "height " + DEFAULT_HEIGHT + "px!, "
											+ "split3");
			bottomPanel.add(btnEditSession, "left, "
										  + "wmin " + MIN_BUTTON_WIDTH + "px, "
										  + "height " + DEFAULT_HEIGHT + "px!");
			bottomPanel.add(cancelSessionButton, "left, "
											   + "wmin " + MIN_BUTTON_WIDTH + "px, "
											   + "height " + DEFAULT_HEIGHT + "px!");
			bottomPanel.add(cardSelectionModeLabel, "left, wmin " + MIN_BUTTON_WIDTH + "px");
			bottomPanel.add(submitVoteButton, "right, "
											+ "height " + DEFAULT_HEIGHT + "px!");
		}
	}

	/*
	 * Construct the left panel and its GUI component: a JLabel and a JList
	 */
	private void setupLeftPanel() {
		leftPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		topLeftPanel = new JPanel();
		bottomLeftPanel = new JPanel();
		
		leftPanelLabel = new JLabel(LEFT_PANEL_LABEL);
		sessionLabel = new JLabel(SESSION_LABEL);
		sessionNameLabel = new JLabel(SESSION_NAME_LABEL);
		sessionNameValueLabel = new JLabel(session.getName());
		sessionDescLabel = new JLabel(SESSION_DESC_LABEL);
		sessionDescValueLabel = new JLabel(session.getDescription());

		final List<PlanningPokerRequirement> reqs = session.getRequirements();
		
		final DefaultListModel<PlanningPokerRequirement> requirementModel = 
				new DefaultListModel<PlanningPokerRequirement>();
		for (PlanningPokerRequirement ppr : reqs) {
			requirementModel.addElement(ppr);
		}
		
		reqList = new JList<PlanningPokerRequirement>(requirementModel);
		reqList.setBackground(Color.WHITE);
		reqList.setAlignmentX(LEFT_ALIGNMENT);
		reqList.setCellRenderer(new VoteRequirementCellRenderer());
		
		reqList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Check to see if user double clicked
				if (e.getClickCount() == 1) {
					selectedRequirement = reqList.getModel().getElementAt(reqList.getSelectedIndex());
					selectedReqIndex = reqList.getSelectedIndex();
					
					if (selectedRequirement.getName() == null) {
						nameDescriptionPanel.setName(" ");
					} else {
						nameDescriptionPanel.setName(selectedRequirement.getName());
					}
					if (selectedRequirement.getDescription() == null) {
						nameDescriptionPanel.setDescription(" ");
					} else {
						nameDescriptionPanel.setDescription(selectedRequirement.getDescription());
					}

					if (session.isClosed()) {
						userVotePanel.setFocusedRequirement(selectedRequirement);
						userVotePanel.fillTable();
						double mean = selectedRequirement.getMean();
						
						finalEstimatePnl.setFocusedRequirement(selectedRequirement);
						finalEstimatePnl.setMean(mean);
						finalEstimatePnl.setMedian(selectedRequirement.getMedian());
						finalEstimatePnl.setMode(selectedRequirement.getMode());

						// ERROR PRONE
						finalEstimatePnl.setStatsStandardDeviation(selectedRequirement.calculateStandardDeviation(mean));
						finalEstimatePnl.updateEstimateTextField(selectedRequirement);
						updateUI();
					} else {
						final PlanningPokerVote vote = selectedRequirement.getVoteByUser(ConfigManager.getConfig().getUserName());

						if (usesDeck())
							clearDeckPanel();
						
						if (vote != null) {
							setVoteTextFieldWithValue(vote.getCardValue());
							disableSubmitBtn();
						} else {
							clearVoteTextField();
							enableSubmitBtn();
						}
						updateUI();
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
		topLeftPanel.setLayout(new MigLayout("insets 0, fill"));
		bottomLeftPanel.setLayout(new MigLayout("insets 0, fill", "", "10[]10[]0"));
		topLeftPanel.add(sessionLabel, "center, wrap");
		topLeftPanel.add(sessionNameLabel, "wrap");
		topLeftPanel.add(sessionNameValueLabel, "center, wrap");
		topLeftPanel.add(sessionDescLabel, "wrap");
		topLeftPanel.add(sessionDescValueLabel, "center, wrap");
		bottomLeftPanel.add(leftPanelLabel, "center, wrap");
		bottomLeftPanel.add(requirementFrame, "width 250::, growy, dock center");
		
		leftPanel.add(topLeftPanel);
		leftPanel.add(bottomLeftPanel);
	}

	private void setupRightPanel() {
		rightPanel = new JPanel();

		// Create a requirement name and description text box
		nameDescriptionPanel = new NameDescriptionPanel(REQ_NAME_LABEL, REQ_DESC_LABEL, false);
		if (session.isClosed()) {
			// Remove the name text box in final estimation
			nameDescriptionPanel.removeNameTextbox();
		}
		
		// Create a text field to store the final vote result
		voteTextField = new JTextField(3);
		voteTextField.setFont(new Font("SansSerif", Font.BOLD, 12));
		voteTextField.setHorizontalAlignment(JTextField.CENTER);

		// Set up ErrorMsg Label
		// TODO: create new GUI component to store this and the vote
		errorMsg = new JLabel("");
		errorMsg.setForeground(Color.RED);
		errorMsg.setHorizontalAlignment(JLabel.CENTER);

		// Create a list of cards OR a card for voting
		if (session.getDeck() != null) {
			voteTextField.setEnabled(false);
			// Create a deck panel
			cardPanel = new DisplayDeckPanel(session.getDeck(), this);
			cardFrame = new JScrollPane();
			cardFrame.setViewportView(cardPanel);
		} else {
			// TODO change this
			voteTextField.setEnabled(true);
		}
		
		// Create an user-vote subpanel
		userVotePanel = new UserVoteListPanel();

		// Create the card panel or final estimation GUI
		finalEstimatePnl = new CompletedSessionEstimatePanel(this);
		// TODO change this migLayout later
		finalEstimatePnl.setAlignmentX(Component.CENTER_ALIGNMENT);

		addGUIComponentsOnRightPanel();
	}

	/*
	 * Add the requirement name and desciption text field, vote submit button,
	 * vote text field, and their labels on the right panel
	 */
	private void addGUIComponentsOnRightPanel() {
		// Add the padding around the right panel
		rightPanel.setLayout(new MigLayout("insets " + VERTICAL_PADDING_RIGHT_PANEL   + " "
													 + HORIZONTAL_PADDING_RIGHT_PANEL + " " 
													 + VERTICAL_PADDING_RIGHT_PANEL   + " "
													 + HORIZONTAL_PADDING_RIGHT_PANEL + ", fill",
											"", "[growprio 65, grow][growprio 35, grow]"));
		
		// Add the Name & Description text boxes
		rightPanel.add(nameDescriptionPanel, "grow, wrap");
		
		if (session.isClosed()) {
			// Add the stats and input for final estimation
			rightPanel.add(finalEstimatePnl, "grow");
			
			// Add the user-vote panel
			rightPanel.add(userVotePanel, "gaptop " + VERTICAL_PADDING_RIGHT_PANEL + ", "
										+ "gapbottom " + VERTICAL_PADDING_RIGHT_PANEL + ", "
										+ "gapright " + HORIZONTAL_PADDING_RIGHT_PANEL + ", "
										+ "width 150:300:500, dock east");
		} else if(session.isCancelled()){
			final JLabel messageLabel = new JLabel(CANCELLED_SESSION);
			rightPanel.add(messageLabel, "gapleft 150px, hmin 230px, grow, dock south");
		} else {
			if (cardFrame != null) {
				rightPanel.add(cardFrame, "height 235::, grow, dock south");
			} else {
				// TODO remove the message
				final JLabel messageLabel = new JLabel(NO_DECK_MSG);
				rightPanel.add(messageLabel, "gapleft 150px, hmin 230px, grow, dock south");
			}
			
			// Add the vote text field to the right side
			rightPanel.add(voteTextField, "wmin " + MIN_VOTE_TEXTFIELD_WIDTH  + "px, " 
										+ "hmin " + MIN_VOTE_TEXTFIELD_HEIGHT + "px, " 
										+ "dock east, " 
										+ "gaptop "   + VERTICAL_PADDING_RIGHT_PANEL   + "px, " 
										+ "gapright " + HORIZONTAL_PADDING_RIGHT_PANEL + "px, "
										+ "gapbottom" + VERTICAL_PADDING_RIGHT_PANEL   + "px");

			// Add the error message to the right side
			// Modify this to GUI component. Ben's request
			rightPanel.add(errorMsg, "dock east");
		}		
	}

	/**
	 * Opens final estimation GUI for this requirement
	 */
	private void openFinalEstimation(){
		ViewEventManager.getInstance().viewSession(this.session);
	}
	
	/**
	 * Closes this tab
	 */
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
			try {
				setErrorMsg(""); // Clear Error Message
				return Integer.parseInt(voteTextField.getText());
			} catch (NumberFormatException e) {
				setErrorMsg("Must enter an integer");
				
				return 0;
			}
		} else {
			return cardPanel.getVoteValue();
		}
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
		voteTextField.setText(Integer.toString(value));
	}
	
	/**
	 * clears voteTextField
	 * 
	 * @param voteTextField
	 */
	public void clearVoteTextField() {
		voteTextField.setText("");
	}
	
	/**
	 * 
	 * @param msg Error Message to be displayed
	 */
	private void setErrorMsg(String msg) {
		errorMsg.setText(msg);
	}
	
	/** Return the GUI list of requirements
	 * @return Return the GUI list of requirements
	 */
	public JList<PlanningPokerRequirement> getRequirementList() {
		return reqList;
	}
	
	/**
	 * Clears the deck highlighting
	 */
	public void clearDeckPanel() {
		cardPanel.removeHighlight();
		cardPanel.clearVoteValue();
	}
	
	/**
	 * 
	 * @return true if the session uses a deck
	 */
	public boolean usesDeck() {
		return this.cardFrame != null;
	}
	
	/**
	 * Go to the next "un-voted" requirement in the list
	 */
	public void advanceInList() {
		PlanningPokerRequirement nextReq = null;
		PlanningPokerVote vote = null;
		
		int i;
		for (i = 0; i < session.getRequirements().size(); i++) {
			reqList.setSelectionInterval(i, i);
			
			nextReq = reqList.getSelectedValue();
			vote = nextReq.getVoteByUser(ConfigManager.getConfig().getUserName());
			
			if (vote == null)
				break;
		}
		
		if (i == session.getRequirements().size()) { // All Reqs voted on
			reqList.setSelectionInterval(selectedReqIndex, selectedReqIndex);
			disableSubmitBtn();
		} else {
			selectedRequirement = nextReq;
			nameDescriptionPanel.setName(nextReq.getName());
			nameDescriptionPanel.setDescription(nextReq.getDescription());
			
			clearVoteTextField();
		
			if (usesDeck())
				clearDeckPanel();
		}
	}
	
	/**
	 * Disable the submit button
	 */
	public void disableSubmitBtn() {
		if (submitVoteButton != null) {
			submitVoteButton.setEnabled(false);
		}
	}
	
	/**
	 * Enable the submit button
	 */
	public void enableSubmitBtn() {
		if (submitVoteButton != null) {
			submitVoteButton.setEnabled(true);
		}
	}
	
	/**
	 * 
	 * @return true if all the reqs have been voted on
	 */
	public boolean allVoted() {
		for (PlanningPokerRequirement ppr : session.getRequirements())
			if (ppr.getVotes().size() == 0) // Req hasn't been votes on
				return false;
		
		return true;
	}

	/**
	 * @return the submitFinalEstimationButton
	 */
	public JButton getSubmitFinalEstimationButton() {
		return submitFinalEstimationButton;
	}

	/**
	 * @param submitFinalEstimationButton the submitFinalEstimationButton to set
	 */
	public void setSubmitFinalEstimationButton(JButton submitFinalEstimationButton) {
		this.submitFinalEstimationButton = submitFinalEstimationButton;
	}
}