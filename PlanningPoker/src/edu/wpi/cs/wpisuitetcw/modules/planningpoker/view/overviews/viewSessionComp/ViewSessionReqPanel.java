/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.AddRequirementController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveAllRequirementsToAllController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveAllRequirementsToCurrentSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveRequirementToAllController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveRequirementToCurrentSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.RetrievePlanningPokerRequirementsForSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers.ViewSessionTableManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ScrollablePanel;

public class ViewSessionReqPanel extends JPanel {
	private final ViewSessionPanel parentPanel;
	private final ScrollablePanel sessionReqPanel;
	private final ScrollablePanel allReqPanel;
	private final JPanel buttonsPanel;
	private final JTextArea description;
	private final JTextField name;
	private final JButton moveRequirementToAll;
	private final JButton moveAllRequirementsToAll;
	private final JButton moveRequirementToSession;
	private final JButton moveAllRequirementsToSession;
	private final JButton addRequirementToAll;
	private final JButton addRequirementToSession;
	public final JTable allReqTable;
	public final JTable sessionReqTable;
	private final PlanningPokerSession session;
	
	/**
	 * @return this.name.getText() This requirement's name
	 */
	public String getNewReqName(){
		return this.name.getText();
	}
	
	/**
	 * @return this.name.setText("") Clear this requirement's name
	 */
	public void clearNewReqName(){
		this.name.setText("");
	}
	
	/**
	 * @return this.description.getText() The description of this requirement
	 */
	public String getNewReqDesc(){

		return this.description.getText();
	}
	
	/**
	 * Sets the description to a default of an empty String
	 */
	public void clearNewReqDesc(){
		this.description.setText("");
	}
	
	/**
	 * Gets all requirements from the left requirement pane
	 * @return selectedNames The ArrayList<String> of names on the left requriements panel
	 */
	public ArrayList<String> getAllLeftRequirements(){
		ArrayList<String> selectedNames = new ArrayList<String>();
		for(int i = 0; i < this.allReqTable.getRowCount(); ++i){
			selectedNames.add(this.allReqTable.getValueAt(i,1).toString());
		}
		return selectedNames;
	}
	
	/**
	 * Gets all requirements from the right requirement pane
	 * @return selectedNames The ArrayList<String> of names on the right requriements panel
	 */
	public ArrayList<String> getAllRightRequirements(){
		ArrayList<String> selectedNames = new ArrayList<String>();
		for(int i = 0; i < this.sessionReqTable.getRowCount(); ++i){
			selectedNames.add(this.sessionReqTable.getValueAt(i,1).toString());
		}
		return selectedNames;
	}
	
	/**
	 * Gets all selected requirements from the left requirement pane
	 * @return selectedNames The ArrayList<String> of names on the left requriements panel
	 */
	public ArrayList<String> getLeftSelectedRequirements()	{
		int[] selectedRows = this.allReqTable.getSelectedRows();
		
		ArrayList<String> selectedNames = new ArrayList<String>();
		for(int i = 0; i < selectedRows.length; i++){
			// Get the 0th column which should be the name
			selectedNames.add(this.allReqTable.getValueAt(selectedRows[i],0).toString());
		}
		return selectedNames;
	}
	
	/**
	 * Gets all selected requirements from the right requirement pane
	 * @return selectedNames The ArrayList<String> of selected names on the right requriements panel
	 */
	public ArrayList<String> getRightSelectedRequirements()	{
		int[] selectedRows = this.sessionReqTable.getSelectedRows();
		
		ArrayList<String> selectedNames = new ArrayList<String>();
		for(int i = 0; i < selectedRows.length; i++){
			// Get the 0th column which should be the name
			selectedNames.add(this.sessionReqTable.getValueAt(selectedRows[i],0).toString());
		}
		return selectedNames;
	}

	public ViewSessionReqPanel(ViewSessionPanel parentPanel, PlanningPokerSession s) {
		this.session = s;
		this.setLayout(new GridBagLayout());
		this.parentPanel = parentPanel;
		this.sessionReqPanel = new ScrollablePanel();
		this.allReqPanel = new ScrollablePanel();
		this.buttonsPanel = new JPanel();
		this.description = new JTextArea("");
		this.name = new JTextField("");
		this.moveRequirementToAll = new JButton(" < ");
		this.moveAllRequirementsToAll = new JButton(" << ");
		this.moveRequirementToSession = new JButton(" > ");
		this.moveAllRequirementsToSession = new JButton(" >> ");
		this.addRequirementToAll = new JButton("Add Requirement to All");
		this.addRequirementToSession = new JButton("Add Requirement to Session");
		
		
		// setup panels
		Panel namePanel = new Panel();
		Panel leftPanel = new Panel();
		Panel rightPanel = new Panel();
		Panel centerPanel = new Panel();
		Panel bottomPanel = new Panel();

		// setup tables
		// Left Table
		allReqTable = new JTable(new ViewSessionTableManager().get(1)) {
			private static final long serialVersionUID = 1L;
			private boolean initialized = false;

			public boolean isCellEditable(int row, int colunm) {
				return false;
			}

			public void valueChanged(ListSelectionEvent e) {

			}

			@Override
			public void repaint() {
				// because janeway is terrible and instantiates this class
				// before the network objects
				
				if (!initialized) {
					try {
						System.out.println("Trying to get free reqs...");
						RetrievePlanningPokerRequirementsForSessionController
								.getInstance().refreshData(1);
						initialized = true;
					} catch (Exception e) {

					}
				}

				super.repaint();
			}
		};

		allReqTable.setBackground(Color.WHITE);

		// add table to rightPanel
		JLabel leftLabel = new JLabel("All Requirements");
		leftPanel.setLayout(new BorderLayout());
		JScrollPane allReqSp = new JScrollPane(allReqTable);
		leftPanel.add(leftLabel, BorderLayout.NORTH);
		leftPanel.add(allReqSp);

		// table for left pain
		//Right table
		sessionReqTable = new JTable(new ViewSessionTableManager().get(this.session.getID())) {
			private static final long serialVersionUID = 2L;
			private boolean initialized = false;

			public boolean isCellEditable(int row, int colunm) {
				return false;
			}

			public void valueChanged(ListSelectionEvent e) {

			}

			@Override
			public void repaint() {
				// because janeway is terrible and instantiates this class
				// before the network objects
				if (!initialized) {
					try {
						RetrievePlanningPokerRequirementsForSessionController
								.getInstance().refreshData(session.getID());
						initialized = true;
					} catch (Exception e) {

					}
				}

				super.repaint();
			}
		};

		
		sessionReqTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		allReqTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		//rightPanel formatting
		JLabel rightLabel = new JLabel("Current Session's Requirements");
		rightPanel.setLayout(new BorderLayout());
		JScrollPane sessionReqSp = new JScrollPane(sessionReqTable);
		rightPanel.add(rightLabel, BorderLayout.NORTH);
		rightPanel.add(sessionReqSp);

		moveAllRequirementsToSession.setPreferredSize(new Dimension(70, 50));
		moveRequirementToSession.setPreferredSize(new Dimension(70, 50));
		moveRequirementToAll.setPreferredSize(new Dimension(70, 50));
		moveAllRequirementsToAll.setPreferredSize(new Dimension(70, 50));

		//Action Handlers 
		this.addRequirementToAll.addActionListener(new AddRequirementController(this));
		this.moveRequirementToSession.addActionListener(new MoveRequirementToCurrentSessionController(this.session, this));
		this.moveRequirementToAll.addActionListener(new MoveRequirementToAllController(this.session, this));
		this.moveAllRequirementsToSession.addActionListener(new MoveAllRequirementsToCurrentSessionController(this.session, this));
		this.moveAllRequirementsToAll.addActionListener(new MoveAllRequirementsToAllController(this.session, this));
		
		// setup buttons panel
		buttonsPanel.setLayout(new GridLayout(0, 1, 0, 20));
		buttonsPanel.add(moveAllRequirementsToSession);
		buttonsPanel.add(moveRequirementToSession);
		buttonsPanel.add(moveRequirementToAll);
		buttonsPanel.add(moveAllRequirementsToAll);

		// buttons panel goes in the center
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(buttonsPanel);

		// text field for name goes in the top of the panel
		JLabel nameLabel = new JLabel("Name:");
		namePanel.setLayout(new BorderLayout());
		namePanel.add(nameLabel, BorderLayout.NORTH);
		namePanel.add(name, BorderLayout.SOUTH);
		
		// text field for description goes in the bottom of the panel
		JLabel descriptionLabel = new JLabel("Description:");
		JScrollPane descriptionSp = new JScrollPane(description);
		description.setLineWrap(true);
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(descriptionLabel, BorderLayout.NORTH);
		bottomPanel.add(descriptionSp, BorderLayout.CENTER);

		GridBagConstraints c = new GridBagConstraints();
		
		//constraints for centerPanel
		c.insets = new Insets(10,10,10,10);
		c.weighty = 0;
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 0;
		this.add(centerPanel, c);
		
		//constraints for addRequirementToAll button
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 1;
		this.add(addRequirementToAll, c);
		
		//constraints for addRequirementToSessoin button
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 2;
		c.gridy = 1;
		this.add(addRequirementToSession, c);
		
		//constraints for namePanel
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		this.add(namePanel, c);
		
		//constraints for leftPanel
		c.anchor = GridBagConstraints.WEST;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.insets = new Insets(10,10,10,0);
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		this.add(leftPanel, c);

		//constraints for rightPanel
		c.anchor = GridBagConstraints.EAST;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.insets = new Insets(10,0,10,10);
		c.gridx = 2;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		this.add(rightPanel, c);
		
		//constraints for bottomPanel
		c.ipady = 100;
		c.insets = new Insets(10,10,10,10);
		c.weighty = 0;
		c.weightx = 0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(bottomPanel, c);
	}
}
