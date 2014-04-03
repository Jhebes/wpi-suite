package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;

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
import javax.swing.JTree;
import javax.swing.AbstractListModel;

public class SessionInProgressPanel extends JSplitPane {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public SessionInProgressPanel() {
		JPanel LeftPanel = new JPanel();
		LeftPanel.setBackground(SystemColor.menu);
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

		JLabel name = new JLabel("Planning Poker", JLabel.CENTER);
		LeftPanel.add(name);

		Component verticalStrut3 = Box.createVerticalStrut(20);
		LeftPanel.add(verticalStrut3);

		JLabel lblDate = new JLabel("Session Ends:");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LeftPanel.add(lblDate);

		JLabel date = new JLabel("12/13/14", JLabel.CENTER);
		LeftPanel.add(date);

		JPanel requirementsPanel = new JPanel();
		requirementsPanel.setLayout(new BoxLayout(requirementsPanel,
				BoxLayout.X_AXIS));
		
		
		
		setLeftComponent(LeftPanel);
		
		JSplitPane splitTopBottom = new JSplitPane();
		splitTopBottom.setResizeWeight(0.8);
		splitTopBottom.setOrientation(JSplitPane.VERTICAL_SPLIT);
		setRightComponent(splitTopBottom);
		
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
		
		textField = new JTextField();
		voteTab.add(textField);
		textField.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		voteTab.add(btnSubmit);
		
		JSplitPane splitLeftRight = new JSplitPane();
		splitLeftRight.setResizeWeight(0.8);
		splitTopBottom.setLeftComponent(splitLeftRight);
		
		JPanel reqsView = new JPanel();
		splitLeftRight.setLeftComponent(reqsView);
		
		JList reqsList = new JList();
		reqsView.add(reqsList);
		
		JPanel ReqsDetail = new JPanel();
		splitLeftRight.setRightComponent(ReqsDetail);
		ReqsDetail.setLayout(new BorderLayout(0, 0));
		
		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"ID:", "", "", "Name:", "", "", "Description:", ""};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		ReqsDetail.add(list, BorderLayout.CENTER);
		
		JLabel lblRequirementDetail = new JLabel("Requirement Detail:");
		lblRequirementDetail.setHorizontalAlignment(SwingConstants.CENTER);
		ReqsDetail.add(lblRequirementDetail, BorderLayout.NORTH);
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
	
	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(String t) {
		this.textField.setText(t);
	}
	*/
}