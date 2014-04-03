package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.AddVoteController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.RetrieveAllPlanningPokerVoteController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.AbstractListModel;

public class SessionInProgressPanel extends JSplitPane {
	
	private JTextField vote;
	private	 JLabel name;
	private	 JLabel description;
	private JLabel deadline;

	/**
	 * Create the panel.
	 */
	public SessionInProgressPanel() {
		JPanel LeftPanel = new JPanel();
		LeftPanel.setLayout(new BoxLayout(LeftPanel, BoxLayout.Y_AXIS));

		Component verticalStrut = Box.createVerticalStrut(10);
		LeftPanel.add(verticalStrut);

		JLabel lblSessionInfo = new JLabel("Session Info:");
		lblSessionInfo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LeftPanel.add(lblSessionInfo);

		Component verticalStrut2 = Box.createVerticalStrut(20);
		LeftPanel.add(verticalStrut2);

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LeftPanel.add(lblName);

		setSessionName("Planning Poker");
		LeftPanel.add(name);

		Component verticalStrut3 = Box.createVerticalStrut(20);
		LeftPanel.add(verticalStrut3);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LeftPanel.add(lblDescription);

		setSessionDescription("A sweet session where you do stuff and all is well.");
		LeftPanel.add(description);

		Component verticalStrut4 = Box.createVerticalStrut(20);
		LeftPanel.add(verticalStrut4);

		JLabel lblDate = new JLabel("Deadline:");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LeftPanel.add(lblDate);

		setSessionDeadline("12/13/14", "12:00 PM");
		LeftPanel.add(deadline);

		JPanel requirementsPanel = new JPanel();
		requirementsPanel.setLayout(new BoxLayout(requirementsPanel, BoxLayout.X_AXIS));
		
		JSplitPane splitTopBottom = new JSplitPane();
		splitTopBottom.setResizeWeight(0.8);
		splitTopBottom.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitTopBottom.setRightComponent(tabbedPane);
		
		JPanel statsTab = new JPanel();
		tabbedPane.addTab("Statistics", null, statsTab, null);
		statsTab.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblCurrentEstimate = new JLabel("Current Estimate:");
		statsTab.add(lblCurrentEstimate);
		
		JLabel lblNumberOfVotes = new JLabel("Number of Votes:");
		statsTab.add(lblNumberOfVotes);
		
		JPanel voteTab = new JPanel();
		tabbedPane.addTab("Voting", null, voteTab, null);
		
		JLabel lblEstimate = new JLabel("Estimate:");
		voteTab.add(lblEstimate);
		
		vote = new JTextField();
		voteTab.add(vote);
		vote.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		voteTab.add(btnSubmit);
		
		JSplitPane splitLeftRight = new JSplitPane();
		splitLeftRight.setResizeWeight(0.8);
		splitTopBottom.setLeftComponent(splitLeftRight);
		
		JPanel reqsView = new JPanel();
		
		JList<String> reqsList = new JList<String>();
		reqsView.add(reqsList);
		
		JPanel ReqsDetail = new JPanel();
		ReqsDetail.setLayout(new BorderLayout(0, 0));
		JList<String> list = new JList<String>();
		list.setModel(new AbstractListModel<String>() {
			String[] values = new String[] {"ID:", "", "", "Name:", "", "", "Description:", ""};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		ReqsDetail.add(list, BorderLayout.CENTER);
		
		JLabel lblRequirementDetail = new JLabel("Requirement Detail:");
		lblRequirementDetail.setHorizontalAlignment(SwingConstants.CENTER);
		ReqsDetail.add(lblRequirementDetail, BorderLayout.NORTH);
		
		splitLeftRight.setLeftComponent(reqsView);
		splitLeftRight.setRightComponent(ReqsDetail);
		
		setLeftComponent(LeftPanel);
		setRightComponent(splitTopBottom);
	}
	
	/**
	 * 
	 * @param sessionName
	 */
	void setSessionName(String sessionName) {
		name = new JLabel(sessionName, JLabel.LEFT);
	}
	
	void setSessionDescription(String sessionDescription) {
		description = new JLabel("<html>" + sessionDescription + "</html>", JLabel.LEFT);
	}
	
	/**
	 * 
	 * @param sessionDeadlineDate Deadline Date (mm/dd/yyyy) of Session as a String
	 * @param sessionDeadlineTime Deadline Time (hh:mm AM) of Session as a String
	 */
	void setSessionDeadline(String sessionDeadlineDate, String sessionDeadlineTime) {
		deadline = new JLabel("<html>" + sessionDeadlineDate + " at " + sessionDeadlineTime + "</html>", JLabel.LEFT);
	}
	
	/**
	 * 
	 * @return vote parsed as an integer
	 */
	int getVote() {
		return Integer.getInteger(vote.toString());
	}
	
/*
	public void receiveVotes(PlanningPokerVote[] votes) {
		String text = "";
		for(PlanningPokerVote v: votes){
			text += String.valueOf(v.getCardValue()) + "\n";
		}
		//System.out.println("Recieved votes: " + text);
		this.textField.setText(text);
	}

	public void setTextField(String t) {
		this.textField.setText(t);
	}
	*/
}