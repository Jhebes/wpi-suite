/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class CompletedSessionEstimatePanel extends JPanel {

	//private final JPanel pnlCompletedSession;
	private final GridLayout panelLayout;
	private final JPanel pnlFinalEstimate;
	private final JPanel pnlStats;
	private final JPanel pnlVotes;
	private final JLabel lblVotes;
	private final JLabel lblStats;
	private final JLabel lblFinalEstimate;
	private final JLabel lblMean;
	private final JLabel lblMedian;
	private final JLabel lblMode;
	private final JTable tblVotes;
	private JTextField statsMean;
	private JTextField statsMedian;
	private JTextField statsMode;
	private final Font headerFont;

	/**
	 * Create the panel.
	 */
	public CompletedSessionEstimatePanel() {
		
		panelLayout = new GridLayout(0, 3);

		/*
		 * Create and set up the 3 panels used to 
		 * create the CompletedSession Panel
		 */
		// Used to make the final estimate
		this.setLayout(panelLayout);
		pnlFinalEstimate = new JPanel();
		pnlFinalEstimate.setLayout(new BoxLayout(pnlFinalEstimate, BoxLayout.Y_AXIS));
		pnlFinalEstimate.setBorder(BorderFactory.createLineBorder(Color.orange));
		
		// Statistical info of the PP Session
		pnlStats = new JPanel(); 
		pnlStats.setLayout(new BoxLayout(pnlStats, BoxLayout.Y_AXIS));
		pnlStats.setBorder(BorderFactory.createLineBorder(Color.red));
		
		// Table of votes for each req
		pnlVotes = new JPanel(); 
		pnlVotes.setLayout(new BoxLayout(pnlVotes, BoxLayout.Y_AXIS));
		pnlVotes.setBorder(BorderFactory.createLineBorder(Color.green));
		
		

		// Initialize the default font for JLabel headers
		headerFont = new Font("TimesRoman", Font.BOLD, 20);

		// Initialize the Headers for the panels.
		lblVotes = new JLabel("Votes");
		lblVotes.setFont(headerFont);
		lblVotes.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		lblStats = new JLabel("Stats");
		lblStats.setFont(headerFont);
		lblStats.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		lblFinalEstimate = new JLabel("Final Estimate");
		lblFinalEstimate.setFont(headerFont);
		lblFinalEstimate.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Create the Final Estimate Panel
		pnlFinalEstimate.add(lblFinalEstimate);

		
		
		
		// Create the Stats Panel
		lblMean = new JLabel("Mean");
		lblMean.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsMean = new JTextField(10);
		// Keeps the text box from filling the entire statBox
		statsMean.setMaximumSize(statsMean.getPreferredSize()); 
		statsMean.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsMean.setEditable(false);
		
		lblMedian = new JLabel("Median");
		lblMedian.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsMedian = new JTextField(10);
		// Keeps the text box from filling the entire statBox
		statsMedian.setMaximumSize(statsMedian.getPreferredSize());
		statsMedian.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsMedian.setEditable(false);
		
		lblMode = new JLabel("Mode");
		lblMode.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsMode = new JTextField(10);
		// Keeps the text box from filling the entire statBox
		statsMode.setMaximumSize(statsMode.getPreferredSize()); 
		statsMode.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsMode.setEditable(false);
		
		pnlStats.add(lblStats);
		
		pnlStats.add(lblMean);
		pnlStats.add(statsMean);
		
		pnlStats.add(lblMedian);
		pnlStats.add(statsMedian);
		
		pnlStats.add(lblMode);
		pnlStats.add(statsMode);
		

	
		
		
		
		// Create the Votes Panel
		Object[][] data = {
				{"remckenna",new Integer(2)}, /// This data is all dummy change
				{"Somebody else", new Integer(6)}
				};
		Object[] voteTableColHeaders = {"User", "Votes"};
		tblVotes = new JTable(data, voteTableColHeaders);
		
		JScrollPane votesScrollPane = new JScrollPane(tblVotes);
		tblVotes.setFillsViewportHeight(true);
		
		pnlVotes.add(lblVotes);
		pnlVotes.add(votesScrollPane);
		
		

		// put the completed Session panel together
		this.add(pnlVotes);
		this.add(pnlStats);
		this.add(pnlFinalEstimate);

	}
	


	public void setStatsMean(int statsMean) {
		this.statsMean.setText(""+ statsMean +"  ;");
	}



	public void setStatsMedian(int statsMedian) {
		this.statsMedian.setText("" + statsMedian + "  ;");
	}



	public void setStatsMode(int statsMode) {
		this.statsMode.setText(""+ statsMode + "  ;");
	}
	
	
}
