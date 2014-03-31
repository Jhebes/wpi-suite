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

public class SessionInProgressPanel extends JSplitPane {

	/**
	 * Create the panel.
	 */
	public SessionInProgressPanel() {
		
		JPanel LeftPanel = new JPanel();
		LeftPanel.setBackground(SystemColor.menu);
		setLeftComponent(LeftPanel);
		LeftPanel.setLayout(new BoxLayout(LeftPanel, BoxLayout.Y_AXIS));
		
		JLabel lblSessionInfo = new JLabel("Session Info:");
		Border empty = BorderFactory.createEmptyBorder();
		
		Component verticalStrut = Box.createVerticalStrut(10);
		LeftPanel.add(verticalStrut);
		lblSessionInfo.setBorder(empty);
		
		lblSessionInfo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblSessionInfo.setAlignmentY(Component.TOP_ALIGNMENT);
		lblSessionInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
		LeftPanel.add(lblSessionInfo);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		LeftPanel.add(verticalStrut_1);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName.setAlignmentY(Component.CENTER_ALIGNMENT);
		lblName.setAlignmentX(Component.LEFT_ALIGNMENT);
		LeftPanel.add(lblName);
		
		JPanel RightPanel = new JPanel();
		RightPanel.setBackground(Color.WHITE);
		setRightComponent(RightPanel);
		RightPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

	}
}
