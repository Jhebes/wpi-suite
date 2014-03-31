package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import javax.swing.JSplitPane;
import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTable;
import javax.swing.JTree;

import java.awt.Font;

import javax.swing.JSeparator;
import java.awt.SystemColor;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JButton;

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
		votingPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("35dlu:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(1dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblEstimate = new JLabel("Estimate:");
		votingPanel.add(lblEstimate, "2, 2, right, default");
		
		textField = new JTextField();
		votingPanel.add(textField, "4, 2, left, default");
		textField.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		votingPanel.add(btnSubmit, "2, 6");
		
		JPanel requirementsPanel = new JPanel();
		detailWindowSplitPane.setLeftComponent(requirementsPanel);
		requirementsPanel.setLayout(new BoxLayout(requirementsPanel, BoxLayout.X_AXIS));
		
		JSplitPane requirmentsDetailsSplitPane = new JSplitPane();
		requirmentsDetailsSplitPane.setResizeWeight(0.6);
		requirementsPanel.add(requirmentsDetailsSplitPane);
		
		JList requirmentsList = new JList();
		requirmentsDetailsSplitPane.setLeftComponent(requirmentsList);
		
		JPanel requiementsDetailPanel = new JPanel();
		requirmentsDetailsSplitPane.setRightComponent(requiementsDetailPanel);
		requiementsDetailPanel.setLayout(new BoxLayout(requiementsDetailPanel, BoxLayout.X_AXIS));

	}
}
