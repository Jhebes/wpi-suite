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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.AddRequirementToAllController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.AddRequirementToSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.EditRequirementDescriptionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveAllRequirementsToAllController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveAllRequirementsToCurrentSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveRequirementToAllController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveRequirementToCurrentSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.RetrievePlanningPokerRequirementsForSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ScrollablePanel;

public class ViewSessionReqPanel extends JPanel {
	private final ViewSessionPanel parentPanel;
	private final ScrollablePanel sessionReqPanel;
	private final ScrollablePanel allReqPanel;
	private final JPanel buttonsPanel;
	private JTextArea description;
	private JTextField name;
	private final JButton moveRequirementToAll;
	private final JButton moveAllRequirementsToAll;
	private final JButton moveRequirementToSession;
	private final JButton moveAllRequirementsToSession;
	private final JButton addRequirementToAll;
	private final JButton addRequirementToSession;
	private final JButton updateDescription;
	private final JTable allReqTable;
	private final JTable sessionReqTable;
	private final PlanningPokerSession session;
	private String reqName;
	private String reqDescription;

	/**
	 * @return this.name.getText() This requirement's name
	 */
	public String getNewReqName() {
		return this.name.getText();
	}

	/**
	 * @return this.name.setText("") Clear this requirement's name
	 */
	public void clearNewReqName() {
		this.name.setText("");
	}

	/**
	 * @return this.description.getText() The description of this requirement
	 */
	public String getNewReqDesc() {

		return this.description.getText();
	}

	/**
	 * Sets the description to a default of an empty String
	 */
	public void clearNewReqDesc() {
		this.description.setText("");
	}

	/**
	 * Gets all requirements from the left requirement pane
	 * 
	 * @return selectedNames The ArrayList<String> of names on the left
	 *         requriements panel
	 */
	public ArrayList<String> getAllLeftRequirements() {
		ArrayList<String> selectedNames = new ArrayList<String>();
		for (int i = 0; i < this.allReqTable.getRowCount(); ++i) {
			selectedNames.add(this.allReqTable.getValueAt(i, 0).toString());
		}
		return selectedNames;
	}

	/**
	 * Gets all requirements from the right requirement pane
	 * 
	 * @return selectedNames The ArrayList<String> of names on the right
	 *         requriements panel
	 */
	public ArrayList<String> getAllRightRequirements() {
		ArrayList<String> selectedNames = new ArrayList<String>();
		for (int i = 0; i < this.sessionReqTable.getRowCount(); ++i) {
			selectedNames.add(this.sessionReqTable.getValueAt(i, 0).toString());
		}
		return selectedNames;
	}

	/**
	 * Gets all selected requirements from the left requirement pane
	 * 
	 * @return selectedNames The ArrayList<String> of names on the left
	 *         requriements panel
	 */
	public ArrayList<String> getLeftSelectedRequirements() {
		int[] selectedRows = this.allReqTable.getSelectedRows();

		ArrayList<String> selectedNames = new ArrayList<String>();
		for (int i = 0; i < selectedRows.length; i++) {
			// Get the 0th column which should be the name
			selectedNames.add(this.allReqTable.getValueAt(selectedRows[i], 0)
					.toString());
		}
		return selectedNames;
	}

	/**
	 * Gets all selected requirements from the right requirement pane
	 * 
	 * @return selectedNames The ArrayList<String> of selected names on the
	 *         right requriements panel
	 */
	public ArrayList<String> getRightSelectedRequirements() {
		int[] selectedRows = this.sessionReqTable.getSelectedRows();

		ArrayList<String> selectedNames = new ArrayList<String>();
		for (int i = 0; i < selectedRows.length; i++) {
			// Get the 0th column which should be the name
			selectedNames.add(this.sessionReqTable.getValueAt(selectedRows[i],
					0).toString());
		}
		return selectedNames;
	}

	public ViewSessionReqPanel(ViewSessionPanel parentPanel,
			PlanningPokerSession s) {
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
		this.updateDescription = new JButton("Update Description");

		// setup panels
		Panel namePanel = new Panel();
		Panel leftPanel = new Panel();
		Panel rightPanel = new Panel();
		Panel centerPanel = new Panel();
		Panel bottomPanel = new Panel();

		// setup tables
		// Left Table
		allReqTable = new JTable(new RequirementTableManager().get(1)) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colunm) {
				return false;
			}

			public void valueChanged(ListSelectionEvent e) {

			}

			@Override
			public void repaint() {
				super.repaint();
			}
		};

		allReqTable.setBackground(Color.WHITE);
		allReqTable.getTableHeader().setReorderingAllowed(false);

		// add table to rightPanel
		JLabel leftLabel = new JLabel("All Requirements");
		leftPanel.setLayout(new BorderLayout());
		JScrollPane allReqSp = new JScrollPane(allReqTable);
		leftPanel.add(leftLabel, BorderLayout.NORTH);
		leftPanel.add(allReqSp);

		// table for left pain
		// Right table
		sessionReqTable = new JTable(
				new RequirementTableManager().get(this.session.getID())) {
			private static final long serialVersionUID = 2L;

			public boolean isCellEditable(int row, int colunm) {
				return false;
			}

			public void valueChanged(ListSelectionEvent e) {

			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void repaint() {
				super.repaint();
			}
		};

		sessionReqTable.getTableHeader().setReorderingAllowed(false);
		sessionReqTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		allReqTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// rightPanel formatting
		JLabel rightLabel = new JLabel("Current Session's Requirements");
		rightPanel.setLayout(new BorderLayout());
		JScrollPane sessionReqSp = new JScrollPane(sessionReqTable);
		rightPanel.add(rightLabel, BorderLayout.NORTH);
		rightPanel.add(sessionReqSp);

		moveAllRequirementsToSession.setPreferredSize(new Dimension(70, 50));
		moveRequirementToSession.setPreferredSize(new Dimension(70, 50));
		moveRequirementToAll.setPreferredSize(new Dimension(70, 50));
		moveAllRequirementsToAll.setPreferredSize(new Dimension(70, 50));
		updateDescription.setPreferredSize(new Dimension(70, 50));

		// Action Handlers
		this.addRequirementToAll
				.addActionListener(new AddRequirementToAllController(this));
		// need to change so it adds to the right side
		this.addRequirementToSession
				.addActionListener(new AddRequirementToSessionController(this));
		this.moveRequirementToSession
				.addActionListener(new MoveRequirementToCurrentSessionController(
						this.session, this));
		this.moveRequirementToAll
				.addActionListener(new MoveRequirementToAllController(
						this.session, this));
		this.moveAllRequirementsToSession
				.addActionListener(new MoveAllRequirementsToCurrentSessionController(
						this.session, this));
		this.moveAllRequirementsToAll
				.addActionListener(new MoveAllRequirementsToAllController(
						this.session, this));
		this.updateDescription
				.addActionListener(new EditRequirementDescriptionController(
						this.session, this));

		// this will populate the name and description field when clicking on a
		// requirement in the all session table
		allReqTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				int row = table.getSelectedRow();

				if (row != -1) {
					RequirementTableManager m = new RequirementTableManager();
					Vector v = m.get(1).getDataVector();
					String name = (String) ((Vector) v.elementAt(row))
							.elementAt(0);
					String desc = (String) ((Vector) v.elementAt(row))
							.elementAt(1);

					reqName = name;
					reqDescription = desc;

					setReqInfo(reqName, reqDescription);

				}
			}
		});

		// this will populate the name and description field when clicking on a
		// requirement in the current session table
		sessionReqTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PlanningPokerRequirement selectedReq;
				ArrayList<String> requirementNames = getRightSelectedRequirements();
				selectedReq = session.getReqByName(requirementNames.get(0));

				reqName = selectedReq.getName();
				reqDescription = selectedReq.getDescription();

				setReqInfo(reqName, reqDescription);

			}
		});

		// setup buttons panel
		buttonsPanel.setLayout(new GridLayout(0, 1, 0, 20));
		buttonsPanel.add(moveAllRequirementsToSession);
		buttonsPanel.add(moveRequirementToSession);
		buttonsPanel.add(moveRequirementToAll);
		buttonsPanel.add(moveAllRequirementsToAll);
		buttonsPanel.add(updateDescription);

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

		// constraints for centerPanel
		c.insets = new Insets(10, 10, 10, 10);
		c.weighty = 0;
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 0;
		this.add(centerPanel, c);

		// constraints for addRequirementToAll button
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 1;
		this.add(addRequirementToAll, c);

		// constraints for addRequirementToSessoin button
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 2;
		c.gridy = 1;
		this.add(addRequirementToSession, c);

		// constraints for namePanel
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		this.add(namePanel, c);

		// constraints for leftPanel
		c.anchor = GridBagConstraints.WEST;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.insets = new Insets(10, 10, 10, 0);
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		this.add(leftPanel, c);

		// constraints for rightPanel
		c.anchor = GridBagConstraints.EAST;
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.insets = new Insets(10, 0, 10, 10);
		c.gridx = 2;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		this.add(rightPanel, c);

		// constraints for bottomPanel
		c.ipady = 100;
		c.insets = new Insets(10, 10, 10, 10);
		c.weighty = 0;
		c.weightx = 0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(bottomPanel, c);
	}

	/**
	 * This functions sets the texts of the name and description field after
	 * clicking on the requirement
	 * 
	 * @param reqName
	 * @param reqDescription
	 */
	public void setReqInfo(String reqName, String reqDescription) {
		name.setText(reqName);
		description.setText(reqDescription);

	}

	public JButton getUpdateDescription() {
		return updateDescription;
	}

	public JTable getAllReqTable() {
		return allReqTable;
	}

	public JTable getSessionReqTable() {
		return sessionReqTable;
	}

	public PlanningPokerSession getSession() {
		return session;
	}

	public String getReqName() {
		return reqName;
	}

	public void setReqName(String reqName) {
		this.reqName = reqName;
	}

	public String getReqDescription() {
		return reqDescription;
	}

	public void setReqDescription(String reqDescription) {
		this.reqDescription = reqDescription;
	}

}
