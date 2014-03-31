package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import javax.swing.JSplitPane;
import javax.swing.JPanel;

import javax.swing.BoxLayout;
import java.awt.Color;

import javax.swing.JLabel;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.JTextField;

import java.awt.Font;

import java.awt.SystemColor;
import javax.swing.JList;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class SessionInProgressPanel extends JSplitPane {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public SessionInProgressPanel() {
		setResizeWeight(0.15);
		
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

		JPanel RightPanel = new JPanel();
		RightPanel.setBackground(Color.WHITE);
		
		setLeftComponent(LeftPanel);
		setRightComponent(RightPanel);
		RightPanel.setLayout(new BoxLayout(RightPanel, BoxLayout.X_AXIS));
		
		JSplitPane detailWindowSplitPane = new JSplitPane();
		detailWindowSplitPane.setResizeWeight(1.0);
		detailWindowSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		RightPanel.add(detailWindowSplitPane);
		
		JPanel votingPanel = new JPanel();
		detailWindowSplitPane.setRightComponent(votingPanel);
		votingPanel.setLayout(new BoxLayout(votingPanel, BoxLayout.X_AXIS));
		
		JPanel requirementsPanel = new JPanel();
		detailWindowSplitPane.setLeftComponent(requirementsPanel);
		requirementsPanel.setLayout(new BoxLayout(requirementsPanel, BoxLayout.X_AXIS));
		
		JSplitPane requirmentsDetailsSplitPane = new JSplitPane();
		requirmentsDetailsSplitPane.setResizeWeight(0.8);
		requirementsPanel.add(requirmentsDetailsSplitPane);
		
		JList requirmentsList = new JList();
		requirmentsDetailsSplitPane.setLeftComponent(requirmentsList);
		
		JPanel requiementsDetailPanel = new JPanel();
		requirmentsDetailsSplitPane.setRightComponent(requiementsDetailPanel);
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
		panel.add(btnSubmit);

	}
}
