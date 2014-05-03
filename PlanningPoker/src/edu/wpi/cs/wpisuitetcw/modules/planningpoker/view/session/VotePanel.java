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
import javax.swing.JTextArea;
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
	private static final String SESSION_LABEL = "Session:";
	private static final String SESSION_NAME_LABEL = "Name:";
	private static final String SESSION_DESC_LABEL = "Description:";
	private static final String END_SESSION_BUTTON_LABEL = "End Session";
	private static final String NO_DECK_MSG = "<html><font color='red'>No deck. Please enter your vote in the white box</font></html>";

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
	private JTextArea sessionNameValueLabel;
	private JLabel sessionDescLabel;
	private JTextArea sessionDescValueLabel;
	private JLabel leftPanelLabel;
	private JSplitPane leftPanel;
	private JPanel topLeftPanel;
	private JPanel bottomLeftPanel;
	
	/** List of requirements */
	private JScrollPane requirementFrame;
	private JList<PlanningPokerRequirement> reqList;

	// ################### GUI right components ####################
	/** The right container holding all the GUI components */
	private JLabel rightPanelLabel;
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
			nameDescriptionPanel.setName(firstReq.getName());
			nameDescriptionPanel.setDescription(firstReq.getDescription());
			selectedRequirement = firstReq;
			selectedReqIndex = 0;
			reqList.setSelectionInterval(0, 0);
			
			final PlanningPokerVote vote = firstReq.getVoteByUser(ConfigManager.getConfig().getUserName());
			if (vote != null) {
				setVoteTextFieldWithValue(vote.getCardValue());
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
		bottomPanel.setLayout(new MigLayout("inset 5 "// + DEFAULT_INSETS / 2 + " "
									  				 + DEFAULT_INSETS + " "
													 + "5 " //DEFAULT_INSETS / 2 + " "
													 + DEFAULT_INSETS + ", fill", 
											"", "push[]push"));
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
		sessionNameValueLabel = new JTextArea(session.getName());
		sessionNameValueLabel.setBackground(this.getBackground());
		sessionNameValueLabel.setEditable(false);
		sessionNameValueLabel.setLineWrap(true);
		sessionNameValueLabel.setWrapStyleWord(false);
		
		sessionDescLabel = new JLabel(SESSION_DESC_LABEL);
		sessionDescValueLabel = new JTextArea(session.getDescription());
		sessionDescValueLabel.setBackground(this.getBackground());
		sessionDescValueLabel.setEditable(false);
		sessionDescValueLabel.setLineWrap(true);
		sessionDescValueLabel.setWrapStyleWord(false);
		

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
						double mean = selectedRequirement.getMean();
						finalEstimatePnl.setFocusedRequirement(selectedRequirement);
						finalEstimatePnl.setStatsMean(mean);
						finalEstimatePnl.setStatsMedian(selectedRequirement.getMedian());
						finalEstimatePnl.setStatsMode(selectedRequirement.getMode());
						finalEstimatePnl.setStatsStandardDeviation(selectedRequirement.calculateStandardDeviation(mean));
						finalEstimatePnl.fillTable(selectedRequirement);
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

		// Create a label for right panel
		rightPanelLabel = new JLabel(RIGHT_PANEL_LABEL);

		// Create a requirement name and description text box
		nameDescriptionPanel = new NameDescriptionPanel(REQ_NAME_LABEL, REQ_DESC_LABEL, false);

		// Create a text field to store the final vote result
		voteTextField = new JTextField(3);
		voteTextField.setFont(new Font("SansSerif", Font.BOLD, 60));

		voteTextField.setHorizontalAlignment(JTextField.CENTER);
		
		// Set up ErrorMsg Label
		errorMsg = new JLabel("");
		errorMsg.setForeground(Color.RED);
		errorMsg.setHorizontalAlignment(JLabel.CENTER);

		voteTextField.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

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
		rightPanel.setLayout(new MigLayout("insets " + VERTICAL_PADDING_RIGHT_PANEL   + " "
													 + HORIZONTAL_PADDING_RIGHT_PANEL + " " 
													 + VERTICAL_PADDING_RIGHT_PANEL   + " "
													 + HORIZONTAL_PADDING_RIGHT_PANEL + ", fill",
											"", "[][grow]"));

		// Add the label of the panel
		rightPanel.add(rightPanelLabel, "center, wrap");

		// Add the requirement name and its label
		rightPanel.add(nameDescriptionPanel, "grow");
		
		// Add the card panel or final estimation GUI
		finalEstimatePnl = new CompletedSessionEstimatePanel(this);
		finalEstimatePnl.setAlignmentX(Component.CENTER_ALIGNMENT);
		if (session.isClosed()) {
			rightPanel.add(finalEstimatePnl);
		} else {
			if (cardFrame != null) {
				rightPanel.add(cardFrame, "height 235::, grow, dock south");
			} else {
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
			rightPanel.add(errorMsg, "dock east");
		}		

	}

	/**
	 * Opens final estiamtion GUI for this requirement
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
}