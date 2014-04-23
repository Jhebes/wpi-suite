/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
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
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers.DisplayDeckPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

public class SessionInProgressPanel extends JPanel {

	private static final String REQ_NAME_LABEL = "Name";
	private static final String REQ_DESC_LABEL = "Description";
	private static final String VOTE_BUTTON_LABEL = "Submit Vote";
	private static final String RIGHT_PANEL_LABEL = "Requirements Detail:";
	private static final String LEFT_PANEL_LABEL = "Session Requirements:";
	private static final String END_SESSION_BUTTON_LABEL = "End Session";
	private static final int MIN_REQ_TEXTBOX_WIDTH = 200;
	private static final int MIN_DESC_TEXTBOX_WIDTH = 400;
	private static final int MIN_DESC_TEXTBOX_HEIGHT = 140;
	private static final int MIN_VOTE_TEXTFIELD_WIDTH = 150;
	private static final int MIN_VOTE_TEXTFIELD_HEIGHT = 150;
	
	private static final int MIN_EDIT_SESSION_BUTTON_WIDTH = 50;
	private static final int GAP_BETWEEN_BOTTOM_BUTTONS = 20;
	private static final int MIN_END_SESSION_BUTTON_WIDTH = 50;
	private static final int PADDING_RIGHT_PANEL = 10;
	private static final int GAP_BETWEEN_REQ_TEXTBOX_AND_VOTE_TEXTBOX = 20;
	
	private final PlanningPokerSession session;
	private PlanningPokerRequirement[] reqsList;

	// #################### GUI left components ####################
	/** The left container holding all the requirements' info */
	private JLabel leftPanelLabel;
	private JPanel leftPanel;
	private JScrollPane requirementFrame;
	private JList reqList;

	// ################### GUI right components ####################
	/** The right container holding all the GUI components */
	private JLabel rightPanelLabel;
	private JPanel rightPanel;

	/** Name of a requirement */
	private JLabel requirementNameLabel;
	private JTextField requirementNameTextbox;

	/** Description of a requirement */
	private JLabel descriptionLabel;
	private JTextField descriptionTextbox;

	/** A Panel exhibiting the cards */
	private DisplayDeckPanel cardPanel;

	/** A text field holding the final result */
	private JTextField voteTextField;

	/** Button submit the vote to the database */
	private JButton submitVoteButton;

	// ################# GUI bottom components ####################
	/** A bottom container holding the buttons below this */
	private JPanel bottomPanel;

	/** A button to end a session */
	private JButton endSessionButton;

	/** A button to edit a session */
	private JButton btnEditSession;

	// #%%##%%##%#%%###%%##%#%%###%%##%#%%# DO THIS LATER
	private String reqName;
	private String reqDescription;

	/**
	 * Construct a SessionInProgressPanel that displays the requirements needed
	 * to vote and provides GUI components to vote
	 */
	public SessionInProgressPanel(final PlanningPokerSession session) {
		this.session = session;

		setupLeftPanel();
		setupRightPanel();
		setupBottomPanel();

		// Need to add both left and right to the JSplitpane
		JSplitPane mainView = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				leftPanel, rightPanel);
		mainView.setEnabled(false);

		// Add mainView and the bottom panel to the canvas
		setLayout(new MigLayout());
		add(mainView, "dock center");
		add(bottomPanel, "dock south");
	}

	/*
	 * Construct the GUI components of the bottom panel
	 */
	private void setupBottomPanel() {
		// Create the container
		bottomPanel = new JPanel();

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
				ArrayList<PlanningPokerRequirement> reqList = session
						.getRequirements();
				for (PlanningPokerRequirement ppr : reqList) {
					int count = 0;
					int total = 0;
					for (PlanningPokerVote vote : ppr.votes) {
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

		String currentUserName = ConfigManager.getConfig().getUserName();
		if (session.isClosed())
			endSessionButton.setEnabled(false);
		if (currentUserName.equals(session.getOwnerUserName()))
			endSessionButton.setVisible(true);
		else
			endSessionButton.setVisible(false);

		// Extract the requirements from the table provided by
		// ViewSessionTableManager and converts them to list
		ArrayList<String> testReqs = new ArrayList<String>();
		RequirementTableManager a = new RequirementTableManager();
		RequirementTableModel v = a.get(this.session.getID());
		Vector vector = v.getDataVector();
		for (int i = 0; i < vector.size(); ++i) {
			testReqs.add((String) (((Vector) vector.elementAt(i)).elementAt(1)));
		}
		String[] reqArr = new String[testReqs.size()];
		for (int i = 0; i < testReqs.size(); ++i) {
			reqArr[i] = testReqs.get(i);
		}
		// reqsViewTable.getColumnModel().getColumn(1).setResizable(false);
		this.getReqsViewTable();

		// Create an edit session button
		btnEditSession = new JButton("Edit Session");
		btnEditSession.addActionListener(new EditActivatedSessionController(
				session, this));
		if (session.isHasVoted()) {
			btnEditSession.setEnabled(false);
		}
		
		// Create a submit vote button
		submitVoteButton = new JButton(VOTE_BUTTON_LABEL);
		submitVoteButton.addActionListener(new AddVoteController(this, this.session));
			
		addGUIComponentsToBottomPanel();

	}

	/*
	 * Add end session button, edit session button, and cancel session button to
	 * the bottom panel
	 */
	private void addGUIComponentsToBottomPanel() {
		bottomPanel.add(endSessionButton, "left, wmin " + MIN_END_SESSION_BUTTON_WIDTH  + "px");
		bottomPanel.add(btnEditSession,   "left, wmin " + MIN_EDIT_SESSION_BUTTON_WIDTH + "px, "
										+ "gapleft " + GAP_BETWEEN_BOTTOM_BUTTONS + "px");
		bottomPanel.add(submitVoteButton, "right");
	}

	/*
	 * Construct the left panel and its GUI component: a JLabel and a JList
	 */
	private void setupLeftPanel() {
		leftPanel = new JPanel();
		leftPanelLabel = new JLabel(LEFT_PANEL_LABEL);

		// TODO: sleep
		ArrayList<PlanningPokerRequirement> reqs = session.getRequirements();
		String[] reqNames = new String[reqs.size()];
		int j = 0;
		for (PlanningPokerRequirement ppr : reqs) {
			reqNames[j] = ppr.getName();
			j++;
		}

		reqList = new JList<String>();
		reqList.setListData(reqNames);
		reqList.setBackground(Color.WHITE);
		reqList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		reqList.setAlignmentX(LEFT_ALIGNMENT);
		reqList.setMinimumSize(new Dimension(100, 400));
		reqList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Check to see if user double clicked
				if (e.getClickCount() == 1) {
					reqName = (String) reqList.getModel().getElementAt(
							reqList.getSelectedIndex());
					
					PlanningPokerRequirement requirement = session
							.getReqByName(reqName);
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
					PlanningPokerVote vote = requirement.getVoteByUser(ConfigManager.getConfig().getUserName());
					if(vote != null) {
						setVoteTextFieldWithValue(vote.getCardValue());
						updateUI();
					}					
				}
			}
		});

		// Put the list of requirement in a scroll pane
		//requirementFrame = new JScrollPane();
		//requirementFrame.setViewportView(reqList);

		addGUIComponentsOnLeftPanel();
	}

	/*
	 * Add the GUI component to the left panel
	 */
	private void addGUIComponentsOnLeftPanel() {
		leftPanel.setLayout(new MigLayout());

		leftPanel.add(leftPanelLabel, "center, wrap");
		leftPanel.add(reqList, "grow, wrap");
	}

	private void setupRightPanel() {
		rightPanel = new JPanel();

		// Create a label for right panel
		rightPanelLabel = new JLabel(RIGHT_PANEL_LABEL);

		// Create a requirement name text box
		requirementNameLabel = new JLabel(REQ_NAME_LABEL);
		requirementNameTextbox = new JTextField();
		requirementNameTextbox.setEditable(false);

		// Create a requirement description text box
		descriptionLabel = new JLabel(REQ_DESC_LABEL);
		descriptionTextbox = new JTextField();
		descriptionTextbox.setEditable(false);
		
		// Create a text field to store the final vote result
		voteTextField = new JTextField(3);
		voteTextField.setFont(new Font("SansSerif", Font.BOLD, 60));

		addGUIComponentsOnRightPanel();

		// if the session has a deck, we can't let the user submit a vote
		// manually
		if (session.getDeck() != null) {
			voteTextField.setEnabled(false);
			// Create a deck panel
			cardPanel = new DisplayDeckPanel(session.getDeck(), this);
			rightPanel.add(cardPanel);
		} else {
			// user votes by entering a number
		}
	}

	/*
	 * Add the requirement name and desciption text field, vote submit button,
	 * vote text field, and their labels on the right panel
	 */
	private void addGUIComponentsOnRightPanel() {	
		// Add the padding around the right panel
		rightPanel.setLayout(new MigLayout("insets " + PADDING_RIGHT_PANEL + " "
													 + PADDING_RIGHT_PANEL + " "
													 + PADDING_RIGHT_PANEL + " "
													 + PADDING_RIGHT_PANEL + ", fillx"));

		// Add the label of the panel
		rightPanel.add(rightPanelLabel, "center, span");

		// Add the requirement name and its label
		rightPanel.add(requirementNameLabel, "growx, left, wrap");
		rightPanel.add(requirementNameTextbox, "growx, gapright " + 
												GAP_BETWEEN_REQ_TEXTBOX_AND_VOTE_TEXTBOX + "px, wrap");
		
		// Add the requirement description box and its label
		rightPanel.add(descriptionLabel, "growx, left, wrap");
		rightPanel.add(descriptionTextbox, "hmin " + MIN_DESC_TEXTBOX_HEIGHT + "px, "
										 + "growx, "
										 + "gapright" + GAP_BETWEEN_REQ_TEXTBOX_AND_VOTE_TEXTBOX + "px, "
										 + "wrap");
		
		// Add the vote text field to the right side
		rightPanel.add(voteTextField, "wmin " + MIN_VOTE_TEXTFIELD_WIDTH  + "px, "
								   	+ "hmin " + MIN_VOTE_TEXTFIELD_HEIGHT + "px, "
								   	+ "dock east, "
								   	+ "gaptop "   + PADDING_RIGHT_PANEL + "px, "
								   	+ "gapright " + PADDING_RIGHT_PANEL + "px");
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
	 * @param sessionDeadlineDate
	 *            Deadline Date (mm/dd/yyyy) of Session as a String
	 * @param sessionDeadlineTime
	 *            Deadline Time (hh:mm AM) of Session as a String
	 */
	// public void setSessionDeadline(Date date) {
	// if (date != null)
	// deadline = new JTextField(date.toString());
	// else
	// deadline = new JTextField("");
	//
	// deadline.setEditable(false);
	// }

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
	 * @return Requirement Name selected in the list
	 */
	public String getSelectedRequirement() {
		return reqName;
	}

	/**
	 * 
	 * @return vote parsed as an integer
	 */
	public int getVote() {
		return cardPanel.getVoteValue();
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
		this.voteTextField.setText(Integer.toString(value));;
	}
}