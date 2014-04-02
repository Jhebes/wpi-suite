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

		JSplitPane RightPanel = new JSplitPane();
		RightPanel.setBackground(Color.WHITE);
		RightPanel.setLayout(new BoxLayout(RightPanel, BoxLayout.X_AXIS));

		JPanel requirementsPanel = new JPanel();
		requirementsPanel.setLayout(new BoxLayout(requirementsPanel,
				BoxLayout.X_AXIS));

		JList requirementsList = new JList();
		RightPanel.setLeftComponent(requirementsList);

		JPanel requiementsDetailPanel = new JPanel();
		RightPanel.setRightComponent(requiementsDetailPanel);
		requiementsDetailPanel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Requirement Detail:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		requiementsDetailPanel.add(lblNewLabel);

		JPanel panel = new JPanel();
		requiementsDetailPanel.add(panel, BorderLayout.SOUTH);

		JLabel lblEstimate = new JLabel("Estimate:");
		panel.add(lblEstimate);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new AddVoteController(this));
		panel.add(btnSubmit);

		
		JButton btnGetAllVotes = new JButton("Get All Votes");
		btnGetAllVotes.addActionListener(new RetrieveAllPlanningPokerVoteController(this, new PlanningPokerRequirement()));
		panel.add(btnGetAllVotes);
		
		
		
		setLeftComponent(LeftPanel);
		setRightComponent(RightPanel);
	}

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
	
}