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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.vote.AddVoteController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.vote.GetRequirementsVotesController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

public class SessionInProgressPanel extends JSplitPane {

	private final PlanningPokerSession session;
	private JTextField vote;
	private JLabel ownerName;
	private JLabel name;
	private JLabel description;
	private JLabel deadline;
	private PlanningPokerRequirement[] reqsList;
	private JButton btnSubmit;
	private String selectedReqName;
	private JTable reqsViewTable;
	private RequirementTableManager reqsViewTableManager = new RequirementTableManager();
	private JList voteList;
	private JLabel label_1 = new JLabel("");
	private JLabel label_2 = new JLabel("");
	private String reqName;
	private String reqDescription;
	private JButton endSession;
	private JSplitPane splitTopBottom;

	/**
	 * Create the panel.
	 */
	public SessionInProgressPanel(final PlanningPokerSession session) {
		this.session = session;
		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		// Set up Session Info Panel
		final JPanel LeftPanel = new JPanel();
		LeftPanel.setLayout(new BoxLayout(LeftPanel, BoxLayout.Y_AXIS));

		// Padding
		Component verticalStrut = Box.createVerticalStrut(10);
		LeftPanel.add(verticalStrut);

		// "Session Info" label
		JLabel lblSessionInfo = new JLabel("Session Info:");
		lblSessionInfo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LeftPanel.add(lblSessionInfo);

		// Padding
		Component verticalStrut2 = Box.createVerticalStrut(20);
		LeftPanel.add(verticalStrut2);

		JLabel lblOwnerName = new JLabel("Owner Name:");
		lblOwnerName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LeftPanel.add(lblOwnerName);

		setOwnerUsername(session.getOwnerUserName());
		LeftPanel.add(ownerName);

		Component verticalStrut21 = Box.createVerticalStrut(20);
		LeftPanel.add(verticalStrut21);

		// "Name" label
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LeftPanel.add(lblName);

		// Call setter for session name
		setSessionName(session.getName());
		LeftPanel.add(name);

		// Padding
		Component verticalStrut3 = Box.createVerticalStrut(20);
		LeftPanel.add(verticalStrut3);

		// "Description" label
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LeftPanel.add(lblDescription);

		// Call setter for session description
		setSessionDescription(session.getDescription());
		LeftPanel.add(description);

		// Padding
		Component verticalStrut4 = Box.createVerticalStrut(20);
		LeftPanel.add(verticalStrut4);

		// "Deadline" label
		JLabel lblDate = new JLabel("Deadline:");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LeftPanel.add(lblDate);
		// TODO: make it so this can take a real date.
		// Call setter for session deadline (TBR)
		setSessionDeadline("12/13/14", "12:00 PM");
		LeftPanel.add(deadline);

		// Padding
		Component verticalStrut5 = Box.createVerticalStrut(20);
		LeftPanel.add(verticalStrut5);
				
		// End session button
		String currentUserName = ConfigManager.getConfig().getUserName();
		endSession = new JButton("End Session");
		endSession.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//session.setEndTime(new Date());
				cancelSession();
			}

			private void cancelSession() {
				session.cancel();
				session.update();
				closeTab();
			}

		});
		LeftPanel.add(endSession);
		if(session.isCancelled())
			endSession.setEnabled(false);
		if(currentUserName.equals(session.getOwnerUserName()))
			endSession.setVisible(true);
		else
			endSession.setVisible(false);
		
		// Set up Reqs Panel
		JPanel requirementsPanel = new JPanel();
		requirementsPanel.setLayout(new BoxLayout(requirementsPanel,
				BoxLayout.X_AXIS));

		// Split out panes
		JSplitPane splitTopBottom = new JSplitPane();
		splitTopBottom.setResizeWeight(0.8);
		splitTopBottom.setOrientation(JSplitPane.VERTICAL_SPLIT);

		// Set up tabs at bottom
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		// Set up "Stats Tab"
		JPanel statsTab = new JPanel();
		tabbedPane.addTab("Statistics", null, statsTab, null);
		statsTab.setLayout(new GridLayout(1, 0, 0, 0));

		// Holder label (TBM)
		JLabel lblCurrentEstimate = new JLabel("Current Estimate:");
		statsTab.add(lblCurrentEstimate);

		// Holder label (TBM)
		JLabel lblNumberOfVotes = new JLabel("Number of Votes:");
		statsTab.add(lblNumberOfVotes);
		
		voteList = new JList();
		statsTab.add(voteList);

		// Set up "Vote Tab"
		JPanel voteTab = new JPanel();
		tabbedPane.addTab("Voting", null, voteTab, null);

		// "Estimate" label
		JLabel lblEstimate = new JLabel("Estimate:");
		voteTab.add(lblEstimate);

		// Text box for vote
		vote = new JTextField(10);
		voteTab.add(vote);

		// Submit button
		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new AddVoteController(this, this.session));
		voteTab.add(btnSubmit);

		// Refresh Button
		JButton btnRefresh;
		btnRefresh = new JButton("*DEV* Refresh");
		btnRefresh.addActionListener(new GetRequirementsVotesController(this,
				this.session));
		voteTab.add(btnRefresh);
		
		// Split into Reqs list and Reqs info
		JSplitPane splitLeftRight = new JSplitPane();
		splitLeftRight.setResizeWeight(0.8);
		splitLeftRight.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		JPanel reqsView = new JPanel();

		// Extract the requirements from the table provided by
		// ViewSessionTableManager and converts them to list
		ArrayList<String> testReqs = new ArrayList<String>();
		RequirementTableManager a = new RequirementTableManager();
		ViewSessionTableModel v = a.get(this.session.getID());
		Vector vector = v.getDataVector();
		for (int i = 0; i < vector.size(); ++i) {
			testReqs.add((String) (((Vector) vector.elementAt(i)).elementAt(1)));
		}
		String[] reqArr = new String[testReqs.size()];
		for (int i = 0; i < testReqs.size(); ++i) {
			reqArr[i] = testReqs.get(i);
		}

		reqsViewTable = new JTable(sessionInProgressTableModel.getInstance()) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		reqsViewTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				int row = table.getSelectedRow();

				if (row != -1){
					RequirementTableManager m = new RequirementTableManager();
					Vector v = m.get(session.getID()).getDataVector();
					String name = (String)((Vector) v.elementAt(row)).elementAt(1);
					PlanningPokerRequirement r = session.getReqByName(name);
					this.setSuperClassVariables(name, r.getDescription());
				}

			}
			public void setSuperClassVariables(String name, String desc){
				System.out.println(name);
				System.out.println(desc);			
				reqName = name;
				reqDescription = desc;
				setReqLabels();
			}
		});
		
		reqsViewTable.setFillsViewportHeight(true);
		// reqsViewTable.getColumnModel().getColumn(1).setResizable(false);
		this.getReqsViewTable();
		// this.reqsViewTable.
		reqsView.setLayout(new BorderLayout(0, 0));
		reqsView.add(reqsViewTable);

		//JPanel reqsDetail = new JPanel();
		//reqsDetail.setLayout(new BorderLayout(0, 0));

		JPanel ReqsDetail = new JPanel();
		ReqsDetail.setLayout(new BorderLayout(0, 0));

		JLabel lblRequirementDetail = new JLabel("Requirement Detail:");
		lblRequirementDetail.setHorizontalAlignment(SwingConstants.CENTER);
		ReqsDetail.add(lblRequirementDetail, BorderLayout.NORTH);

		// Set all components
		splitLeftRight.setLeftComponent(reqsView);

		JLabel lblNewLabel = new JLabel("Requirements");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		reqsView.add(lblNewLabel, BorderLayout.NORTH);
		splitLeftRight.setRightComponent(ReqsDetail);
		
		JPanel panel = new JPanel();
		ReqsDetail.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_1);
		
		JLabel lblName_1 = new JLabel("Name:");
		panel.add(lblName_1);
		
		panel.add(label_1);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_3);
		
		JLabel lblDescription_1 = new JLabel("Description:");
		panel.add(lblDescription_1);
		
		panel.add(label_2);

		splitTopBottom.setTopComponent(splitLeftRight);
		splitTopBottom.setBottomComponent(tabbedPane);

		setLeftComponent(LeftPanel);
		setRightComponent(splitTopBottom);
	}
	
	private void closeTab(){
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
	 * /**
	 * 
	 * @param sessionName
	 */
	public void setSessionName(String sessionName) {
		name = new JLabel(sessionName, JLabel.LEFT);
	}

	public void setSessionDescription(String sessionDescription) {
		description = new JLabel("<html>" + sessionDescription + "</html>",
				JLabel.LEFT);
	}

	void setOwnerUsername(String userName) {
		ownerName = new JLabel(userName, JLabel.CENTER);
	}

	/**
	 * 
	 * @param sessionDeadlineDate
	 *            Deadline Date (mm/dd/yyyy) of Session as a String
	 * @param sessionDeadlineTime
	 *            Deadline Time (hh:mm AM) of Session as a String
	 */
	public void setSessionDeadline(String sessionDeadlineDate,
			String sessionDeadlineTime) {
		deadline = new JLabel("<html>" + sessionDeadlineDate + " at "
				+ sessionDeadlineTime + "</html>", JLabel.LEFT);
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
		int c = reqsViewTable.getSelectedRow();
		String name = (String) this.reqsViewTableManager.get(
				this.session.getID()).getValueAt(c, 0);
		System.out.println("Found " + name);
		return name;
	}

	/**
	 * 
	 * @return vote parsed as an integer
	 */
	public int getVote() {
		return Integer.parseInt(vote.getText());
	}

	/**
	 * Sets the contents of the list of votes for the specified requirement
	 * 
	 * @param votes
	 */
	
	public void setVoteList(ArrayList<PlanningPokerVote> votes) {
		String[] array = new String[votes.size()];
		for(int i = 0; i < votes.size(); ++i){
			array[i] = votes.get(i).toString() ;
		}
		this.voteList.setListData(array);
	}
	

	/**
	 * sets the reqsViewTable with the appropriate information
	 */
	public void getReqsViewTable() {
		reqsViewTable.setModel(reqsViewTableManager.get(session.getID()));
	}
	
	public void setReqLabels() {
		label_1.setText("<html>"+reqName+"</html>");
		label_2.setText("<html>"+reqDescription+"</html>");
	}
}