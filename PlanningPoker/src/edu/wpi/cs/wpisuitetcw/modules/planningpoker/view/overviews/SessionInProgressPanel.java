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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session.EditActivatedSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.vote.AddVoteController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.CompletedSessionEstimatePanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

public class SessionInProgressPanel extends JSplitPane {

	private final PlanningPokerSession session;
	private JTextField ownerName;
	private JTextField name;
	private JTextField description;
	private JTextField deadline;
	private PlanningPokerRequirement[] reqsList;
	private String selectedReqName;
	private RequirementTableManager reqsViewTableManager = new RequirementTableManager();
	private JLabel label_1 = new JLabel("");
	private JLabel label_2 = new JLabel("");
	private PlanningPokerRequirement selectedReq;
	private String reqName;
	private String reqDescription;
	private JButton endSession;
	private JSplitPane splitTopBottom;
	private JButton btnEditSession = new JButton("Edit Session");
	private JTextField txtVoteField;
	private JList VoteList;
	private CompletedSessionEstimatePanel finalEstimatePnl;

	/**
	 * Create the panel.
	 */
	public SessionInProgressPanel(final PlanningPokerSession session) {
		this.session = session;
		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		// Set up Session Info Panel
		final JPanel LeftPanel = new JPanel();
		LeftPanel.setLayout(new BoxLayout(LeftPanel, BoxLayout.Y_AXIS));
		LeftPanel.setAlignmentX(TOP_ALIGNMENT);

		/*
		 * // "Session Info" label JLabel lblSessionInfo = new
		 * JLabel("Session Info:"); lblSessionInfo.setFont(new
		 * Font("Sans-Serif", Font.BOLD, 15)); LeftPanel.add(lblSessionInfo);
		 * 
		 * // Padding LeftPanel.add(Box.createVerticalStrut(10));
		 */

		// "Name" label
		JLabel lblName = new JLabel("Session Name:");
		LeftPanel.add(lblName);

		// session name adding it to left panel
		name = new JTextField(session.getName());
		name.setEditable(false);
		name.setMaximumSize(new Dimension(500, 10));
		name.setHorizontalAlignment(JLabel.CENTER);
		name.setBorder(BorderFactory.createEmptyBorder());
		// TODO: figure out what to do with runover
		LeftPanel.add(name);

		// Padding
		LeftPanel.add(Box.createVerticalStrut(10));

		JLabel lblOwnerName = new JLabel("Owner Name:");
		LeftPanel.add(lblOwnerName);

		// session owner name adding it to left panel
		ownerName = new JTextField(session.getOwnerUserName());
		ownerName.setEditable(false);
		ownerName.setMaximumSize(new Dimension(500, 10));
		ownerName.setHorizontalAlignment(JLabel.CENTER);
		ownerName.setBorder(BorderFactory.createEmptyBorder());
		LeftPanel.add(ownerName);

		// Padding
		LeftPanel.add(Box.createVerticalStrut(10));

		// "Description" label
		JLabel lblDescription = new JLabel("Session Description:");
		LeftPanel.add(lblDescription);

		// session description adding it to left panel
		description = new JTextField(session.getDescription());
		description.setEditable(false);
		description.setMaximumSize(new Dimension(500, 10));
		description.setHorizontalAlignment(JLabel.CENTER);
		description.setBorder(BorderFactory.createEmptyBorder());
		LeftPanel.add(description);

		// Padding
		LeftPanel.add(Box.createVerticalStrut(10));

		// "Deadline" label
		JLabel lblDate = new JLabel("Session Deadline:");
		LeftPanel.add(lblDate);

		// session deadline adding it to left panel
		setSessionDeadline(session.getDeadline());
		deadline.setMaximumSize(new Dimension(500, 10));
		deadline.setHorizontalAlignment(JLabel.CENTER);
		deadline.setBorder(BorderFactory.createEmptyBorder());
		LeftPanel.add(deadline);

		LeftPanel.add(Box.createVerticalStrut(10));

		JLabel reqHeader = new JLabel("Session Requirements:");
		reqHeader.setHorizontalAlignment(JLabel.CENTER);
		LeftPanel.add(reqHeader);

		// TODO: sleep
		ArrayList<PlanningPokerRequirement> reqs = session.getRequirements();
		String[] reqNames = new String[reqs.size()];
		int j = 0;
		for (PlanningPokerRequirement ppr : reqs) {
			reqNames[j] = ppr.getName();
			j++;
		}
		JList<String> reqList = new JList<String>();
		reqList.setListData(reqNames);
		reqList.setBackground(Color.WHITE);
		reqList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		reqList.setAlignmentX(CENTER_ALIGNMENT);
		reqList.setPreferredSize(new Dimension(100, 500));
		LeftPanel.add(reqList);

		// End session button
		String currentUserName = ConfigManager.getConfig().getUserName();
		endSession = new JButton("End Session");
		// endSession.setBackground(new Color(255, 255, 255));
		// endSession.setForeground(new Color(255, 255, 255));
		// endSession.setContentAreaFilled(false);
		// endSession.setOpaque(true);
		endSession.addActionListener(new ActionListener() {
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
		LeftPanel.add(endSession);

		if (session.isClosed())
			endSession.setEnabled(false);
		if (currentUserName.equals(session.getOwnerUserName()))
			endSession.setVisible(true);
		else
			endSession.setVisible(false);

		// Set up Reqs Panel
		JPanel requirementsPanel = new JPanel();
		requirementsPanel.setLayout(new BoxLayout(requirementsPanel,
				BoxLayout.X_AXIS));

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

		setLeftComponent(LeftPanel);

		btnEditSession = new JButton("Edit Session");
		btnEditSession.addActionListener(new EditActivatedSessionController(
				session, this));
		if (session.isHasVoted()) {
			btnEditSession.setEnabled(false);
		}

		Component verticalGlue = Box.createVerticalGlue();
		LeftPanel.add(verticalGlue);
		LeftPanel.add(btnEditSession);

		JPanel RightPanel = new JPanel();
		setRightComponent(RightPanel);
		RightPanel.setLayout(new BorderLayout(0, 0));

		JPanel DetailPanel = new JPanel();
		RightPanel.add(DetailPanel, BorderLayout.PAGE_START);// Changed from
																// CENTER to
																// Page Star
		DetailPanel.setLayout(new BoxLayout(DetailPanel, BoxLayout.Y_AXIS));

		Component verticalStrut = Box.createVerticalStrut(20);
		DetailPanel.add(verticalStrut);

		JLabel lblName_1 = new JLabel("Name:");
		DetailPanel.add(lblName_1);
		DetailPanel.add(label_1);

		Component verticalStrut_3 = Box.createVerticalStrut(20);
		DetailPanel.add(verticalStrut_3);

		JLabel lblDescription_1 = new JLabel("Description:");
		DetailPanel.add(lblDescription_1);
		DetailPanel.add(label_2);

		JPanel DeckPanel = new JPanel();
		DetailPanel.add(DeckPanel);
		DeckPanel.setLayout(new BoxLayout(DeckPanel, BoxLayout.X_AXIS));

		JLabel lblRequirementsDetail = new JLabel("Requirements Detail:");
		lblRequirementsDetail.setHorizontalAlignment(SwingConstants.CENTER);
		RightPanel.add(lblRequirementsDetail, BorderLayout.NORTH);

		JPanel VotingPanel = new JPanel();
		RightPanel.add(VotingPanel, BorderLayout.SOUTH);
		VotingPanel.setLayout(new BoxLayout(VotingPanel, BoxLayout.X_AXIS));

		Component verticalStrut_1 = Box.createVerticalStrut(40);
		VotingPanel.add(verticalStrut_1);

		JButton btnSubmitVote = new JButton("Submit Vote");
		VotingPanel.add(btnSubmitVote);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		VotingPanel.add(horizontalStrut);

		txtVoteField = new JTextField();
		txtVoteField.setEditable(false);
		VotingPanel.add(txtVoteField);
		txtVoteField.setColumns(3);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		VotingPanel.add(horizontalStrut_1);

		JList VoteList = new JList();
		VotingPanel.add(VoteList);
	}

	public void setNumVotesLabel(int n) {
		this.txtVoteField.setText(Integer.toString(n));
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
	public void setSessionDeadline(Date date) {
		if (date != null)
			deadline = new JTextField(date.toString());
		else
			deadline = new JTextField("");

		deadline.setEditable(false);
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
		return Integer.parseInt(this.txtVoteField.getText());
	}

	/**
	 * Sets the contents of the list of votes for the specified requirement
	 * 
	 * @param votes
	 */

	public void setVoteList(ArrayList<PlanningPokerVote> votes) {
		String[] array = new String[votes.size()];
		for (int i = 0; i < votes.size(); ++i) {
			array[i] = votes.get(i).toString();
		}
		this.VoteList.setListData(array);
	}

	public JList getVoteList() {
		return this.VoteList;
	}

	/**
	 * sets the reqsViewTable with the appropriate information
	 */

	public void getReqsViewTable() {
	}

	public void setReqLabels() {
		label_1.setText("<html>" + reqName + "</html>");
		label_2.setText("<html>" + reqDescription + "</html>");
	}

	public void disableEditSession() {
		btnEditSession.setEnabled(false);
	}

}