/******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.tabs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.AddRequirementToSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.EditRequirementDescriptionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveAllRequirementsToAllController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveAllRequirementsToCurrentSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveRequirementToAllController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveRequirementToCurrentSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ScrollablePanel;

/**
 * Panel for adding requirements to a planning poker session.
 */
public class SessionRequirementPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final SessionTabsPanel parentPanel;
	private final ScrollablePanel sessionReqPanel;
	private final ScrollablePanel allReqPanel;
	private final JPanel buttonsPanel;
	private JTextArea description;
	private JTextField name;
	private JLabel errorMessage;
	private final JButton moveRequirementToAll;
	private final JButton moveAllRequirementsToAll;
	private final JButton moveRequirementToSession;
	private final JButton moveAllRequirementsToSession;
	private final JButton addRequirementToSession;
	private final JButton saveRequirement;
	private final JTable allReqTable;
	private final JTable sessionReqTable;
	private final PlanningPokerSession session;
	private PlanningPokerSession editRequirementsSession;
	private String selectedReqName;
	private String selectedReqDescription;

	/**
	 * Constructs the panel for adding requirements.
	 * 
	 * @param s
	 *            The session for this panel
	 */
	public SessionRequirementPanel(final SessionTabsPanel parentPanel,
			final PlanningPokerSession session) {
		final RequirementTableManager manager = new RequirementTableManager();
		manager.fetch(session.getID());

		this.parentPanel = parentPanel;
		this.session = session;
		this.setLayout(new GridBagLayout());
		sessionReqPanel = new ScrollablePanel();
		allReqPanel = new ScrollablePanel();
		buttonsPanel = new JPanel();
		description = new JTextArea("");
		name = new JTextField("");
		errorMessage = new JLabel();
		moveRequirementToAll = new JButton(" < ");
		moveAllRequirementsToAll = new JButton(" << ");
		moveRequirementToSession = new JButton(" > ");
		moveAllRequirementsToSession = new JButton(" >> ");
		addRequirementToSession = new JButton("Add Requirement to Session");
		saveRequirement = new JButton("Save Changes");
		saveRequirement.setEnabled(false);
		addRequirementToSession.setEnabled(false);
		selectedReqName = "";
		selectedReqDescription = "";

		// setup panels
		final Panel namePanel = new Panel();
		final Panel leftPanel = new Panel();
		final Panel rightPanel = new Panel();
		final Panel centerPanel = new Panel();
		final Panel bottomPanel = new Panel();

		// setup tables
		// Left Table
		allReqTable = new JTable(new RequirementTableManager().get(1)) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colunm) {
				return false;
			}

		};

		allReqTable.setBackground(Color.WHITE);
		allReqTable.getTableHeader().setReorderingAllowed(false);

		// allows multiple reqs to be selected and unselected
		allReqTable.setRowSelectionAllowed(true);
		allReqTable
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// add table to rightPanel
		final JLabel leftLabel = new JLabel("All Requirements");
		leftPanel.setLayout(new BorderLayout());
		final JScrollPane allReqSp = new JScrollPane(allReqTable);
		leftPanel.add(leftLabel, BorderLayout.NORTH);
		leftPanel.add(allReqSp);

		// table for left pane
		// Right table
		sessionReqTable = new JTable(new RequirementTableManager().get(session
				.getID())) {
			private static final long serialVersionUID = 2L;

			public boolean isCellEditable(int row, int colunm) {
				return false;
			}

		};

		sessionReqTable.getTableHeader().setReorderingAllowed(false);
		sessionReqTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// allows multiple reqs to be selected and unselected
		sessionReqTable.setRowSelectionAllowed(true);
		sessionReqTable
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		allReqTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		// rightPanel formatting
		final JLabel rightLabel = new JLabel("Current Session's Requirements");
		rightPanel.setLayout(new BorderLayout());
		final JScrollPane sessionReqSp = new JScrollPane(sessionReqTable);
		rightPanel.add(rightLabel, BorderLayout.NORTH);
		rightPanel.add(sessionReqSp);

		moveAllRequirementsToSession.setPreferredSize(new Dimension(70, 50));
		moveRequirementToSession.setPreferredSize(new Dimension(70, 50));
		moveRequirementToAll.setPreferredSize(new Dimension(70, 50));
		moveAllRequirementsToAll.setPreferredSize(new Dimension(70, 50));

		// Action Handlers
		// need to change so it adds to the right side
		addRequirementToSession
				.addActionListener(new AddRequirementToSessionController(
						session, this));
		moveRequirementToSession
				.addActionListener(new MoveRequirementToCurrentSessionController(
						session, this));
		moveRequirementToAll
				.addActionListener(new MoveRequirementToAllController(session,
						this));
		moveAllRequirementsToSession
				.addActionListener(new MoveAllRequirementsToCurrentSessionController(
						session, this));
		moveAllRequirementsToAll
				.addActionListener(new MoveAllRequirementsToAllController(
						session, this));
		saveRequirement
				.addActionListener(new EditRequirementDescriptionController(
						session, this));

		// this will populate the name and description field when clicking on a
		// requirement in the all session table
		allReqTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sessionReqTable.clearSelection();
				refreshMoveButtons();
				final RequirementTableManager n = new RequirementTableManager();
				n.fetch(session.getID());
				sessionReqTable.updateUI();
				final JTable table = (JTable) e.getSource();
				final int row = table.getSelectedRow();
				saveRequirement.setEnabled(false);
				addRequirementToSession.setEnabled(false);
				hideErrorMessage();

				if (row == -1) {
					name.setEnabled(true);
					setReqInfo("", "");
					selectedReqName = "";
					selectedReqDescription = "";
				} else {
					for (int i = 0; i < 2; i++) {
						if (i == 0) {
							selectedReqName = allReqTable.getModel()
									.getValueAt(row, 0).toString();
						}
						if (i == 1) {
							selectedReqDescription = allReqTable.getModel()
									.getValueAt(row, 1).toString();
						}
					}

					setReqInfo(selectedReqName, selectedReqDescription);
					name.setEnabled(false);
					editRequirementsSession = SessionStash.getInstance()
							.getDefaultSession();
				}
			}
		});

		/**
		 * this will populate the name and description field when clicking on a
		 * requirement
		 */
		sessionReqTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				allReqTable.clearSelection();
				refreshMoveButtons();
				final JTable table = (JTable) e.getSource();
				final int row = table.getSelectedRow();
				saveRequirement.setEnabled(false);
				addRequirementToSession.setEnabled(false);
				hideErrorMessage();
				if (row == -1) {
					name.setEnabled(true);
					setReqInfo("", "");
					selectedReqName = "";
					selectedReqDescription = "";
				} else {
					for (int i = 0; i < 2; i++) {
						if (i == 0) {
							selectedReqName = sessionReqTable.getModel()
									.getValueAt(row, 0).toString();
						}
						if (i == 1) {
							selectedReqDescription = sessionReqTable.getModel()
									.getValueAt(row, 1).toString();
						}
					}

					setReqInfo(selectedReqName, selectedReqDescription);
					name.setEnabled(false);
					editRequirementsSession = SessionRequirementPanel.this.session;
				}
			}
		});

		/**
		 * Takes care of highlighting by dragging for the sessionReqTable
		 */
		sessionReqTable.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				refreshMoveButtons();
				allReqTable.clearSelection();
				hideErrorMessage();
			}
		});

		/**
		 * Takes care of highlighting by dragging for the allReqTable
		 */
		allReqTable.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				refreshMoveButtons();
				sessionReqTable.clearSelection();
				hideErrorMessage();
			}
		});

		/**
		 * Clears the selections of the req panels when parent panel is clicked
		 * on
		 */
		parentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				allReqTable.clearSelection();
				sessionReqTable.clearSelection();
			}

		});

		// setup buttons panel
		buttonsPanel.setLayout(new GridLayout(0, 1, 0, 20));
		buttonsPanel.add(moveAllRequirementsToSession);
		buttonsPanel.add(moveRequirementToSession);
		buttonsPanel.add(moveRequirementToAll);
		buttonsPanel.add(moveAllRequirementsToAll);

		// adds a scroll pane to the req table but not the panel, so it has no
		// effect
		// JScrollPane jsp1 = new JScrollPane();
		// JScrollPane.add(sessionReqTable);

		// buttons panel goes in the center
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(buttonsPanel);

		// text field for name goes in the top of the panel
		final JLabel nameLabel = new JLabel("Name:");
		namePanel.setLayout(new BorderLayout(6, 2));
		namePanel.add(nameLabel, BorderLayout.LINE_START);
		namePanel.add(errorMessage, BorderLayout.CENTER);
		namePanel.add(name, BorderLayout.SOUTH);

		KeyListener nameDescriptionValidationListener = new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				keyTyped(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				keyTyped(e);

			}

			@Override
			public void keyTyped(KeyEvent e) {
				SessionRequirementPanel.this.validateNameAndDescription();
			}
		};
		description.addKeyListener(nameDescriptionValidationListener);
		name.addKeyListener(nameDescriptionValidationListener);

		// text field for description goes in the bottom of the panel
		final JLabel descriptionLabel = new JLabel("Description:");
		final JScrollPane descriptionSp = new JScrollPane(description);
		description.setLineWrap(true);
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(descriptionLabel, BorderLayout.NORTH);
		bottomPanel.add(descriptionSp, BorderLayout.CENTER);

		final GridBagConstraints c = new GridBagConstraints();

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
		this.add(saveRequirement, c);

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
		refreshMoveButtons();
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(JTextField name) {
		this.name = name;
	}

	/**
	 * @return this.name.getText() This requirement's name
	 */
	public String getNewReqName() {
		return name.getText();
	}

	/**
	 * @return the saveRequirement
	 */
	public JButton getSaveRequirement() {
		return saveRequirement;
	}

	/**
	 * Clears this requirement's name
	 */
	public void clearNewReqName() {
		name.setText("");
	}

	/**
	 * @return this.description.getText() The description of this requirement
	 */
	public String getNewReqDesc() {

		return description.getText();
	}

	/**
	 * Sets the description to a default of an empty String
	 */
	public void clearNewReqDesc() {
		description.setText("");
	}

	/**
	 * Enables the name field
	 */
	public void enableName() {
		name.setEnabled(true);
	}

	/**
	 * Gets all requirements from the left requirement pane
	 * 
	 * @return selectedNames The ArrayList<String> of names on the left
	 *         requriements panel
	 */
	public List<String> getAllLeftRequirements() {
		final List<String> selectedNames = new ArrayList<String>();
		for (int i = 0; i < allReqTable.getRowCount(); ++i) {
			selectedNames.add(allReqTable.getValueAt(i, 0).toString());
		}
		return selectedNames;
	}

	/**
	 * Gets all requirements from the right requirement pane
	 * 
	 * @return selectedNames The ArrayList<String> of names on the right
	 *         requriements panel
	 */
	public List<String> getAllRightRequirements() {
		final List<String> selectedNames = new ArrayList<String>();
		for (int i = 0; i < sessionReqTable.getRowCount(); ++i) {
			selectedNames.add(sessionReqTable.getValueAt(i, 0).toString());
		}
		return selectedNames;
	}

	/**
	 * Gets all selected requirements from the left requirement pane
	 * 
	 * @return selectedNames The ArrayList<String> of names on the left
	 *         requriements panel
	 */
	public List<String> getLeftSelectedRequirements() {
		final int[] selectedRows = allReqTable.getSelectedRows();

		final List<String> selectedNames = new ArrayList<String>();
		for (int i = 0; i < selectedRows.length; i++) {
			// Get the 0th column which should be the name
			selectedNames.add(allReqTable.getValueAt(selectedRows[i], 0)
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
	public List<String> getRightSelectedRequirements() {
		final int[] selectedRows = sessionReqTable.getSelectedRows();

		final List<String> selectedNames = new ArrayList<String>();
		for (int i = 0; i < selectedRows.length; i++) {
			// Get the 0th column which should be the name
			selectedNames.add(sessionReqTable.getValueAt(selectedRows[i], 0)
					.toString());
		}
		return selectedNames;
	}

	/**
	 * Refreshes all buttons in the buttonPanel
	 */
	public void refreshMoveButtons() {
		if (getRightSelectedRequirements().size() == 0) {
			moveRequirementToAll.setEnabled(false);
		} else {
			moveRequirementToAll.setEnabled(true);
		}

		if (getLeftSelectedRequirements().size() == 0) {
			moveRequirementToSession.setEnabled(false);
		} else {
			moveRequirementToSession.setEnabled(true);
		}

		if (getAllRightRequirements().size() == 0) {
			moveAllRequirementsToAll.setEnabled(false);
		} else {
			moveAllRequirementsToAll.setEnabled(true);
		}

		if (getAllLeftRequirements().size() == 0) {
			moveAllRequirementsToSession.setEnabled(false);
		} else {
			moveAllRequirementsToSession.setEnabled(true);
		}
	}

	/**
	 * @return the addRequirementToSession
	 */
	public JButton getAddRequirementToSession() {
		return addRequirementToSession;
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

	/**
	 * Checks whether there is at least 1 requirement in this session and
	 * disables the "Activate" button if there are no requirements.
	 */
	public void validateOpenSession() {
		boolean parentHasValidInputs = parentPanel.getParentPanel()
				.hasAllValidInputs();
		parentPanel.getParentPanel().getBtnOpenSession().setEnabled(false);
		if (!session.getRequirements().isEmpty() && parentHasValidInputs) {
			parentPanel.getParentPanel().getBtnOpenSession().setEnabled(true);
		}
	}

	/**
	 * Enables and disables the Add to Session and Save Changes buttons
	 * depending on the conditions of the view, namely the name and description
	 * views.
	 */
	public void validateNameAndDescription() {
		addRequirementToSession.setEnabled(false);
		saveRequirement.setEnabled(false);

		// validates that no other req in the panel has the same name
		boolean notUsed = true;
		List<String> reqNames = getAllRightRequirements();
		String nameField = name.getText();

		for (int i = 0; i < reqNames.size(); i++) {
			if (nameField.equals(reqNames.get(i))) {
				notUsed = false;
				break;
			}
		}

		reqNames = getAllLeftRequirements();

		if (notUsed == true) {
			for (int i = 0; i < reqNames.size(); i++) {
				if (nameField.equals(reqNames.get(i))) {
					notUsed = false;
					break;
				}
			}
		}

		// If there is no selected requirement,
		if (selectedReqDescription.isEmpty()) {
			if (!description.getText().isEmpty() && !name.getText().isEmpty()
					&& notUsed) {
				addRequirementToSession.setEnabled(true);
			}
		} else {
			if (!description.getText().isEmpty() && hasDescriptionChanged()) {
				saveRequirement.setEnabled(true);
			}
		}
	}

	/**
	 * Clears selection of the two tables
	 */
	public void clearSelection(){
		// gets rid of selection
		allReqTable.clearSelection();
		sessionReqTable.clearSelection();
	}
	
	/**
	 * @return Whether the has changed
	 */
	public boolean hasDescriptionChanged() {
		return !selectedReqDescription.equals(description.getText());
	}

	public JButton getUpdateDescription() {
		return saveRequirement;
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

	public String getSelectedReqName() {
		return selectedReqName;
	}

	public void setSelectedReqName(String reqName) {
		this.selectedReqName = reqName;
	}

	public String getSelectedReqDescription() {
		return selectedReqDescription;
	}

	public void setSelectedReqDescription(String reqDescription) {
		this.selectedReqDescription = reqDescription;
	}

	/**
	 * @return The session belonging to the requirement that is being edited.
	 */
	public PlanningPokerSession getEditRequirementsSession() {
		return editRequirementsSession;
	}

	/**
	 * Assign the given string to the error message
	 */
	public void setErrorMessage(String message) {
		errorMessage.setText(message);
	}

	/**
	 * Set the error message visible
	 */
	public void showErrorMessage() {
		errorMessage.setVisible(true);
	}

	/**
	 * Hide the error message
	 */
	public void hideErrorMessage() {
		errorMessage.setVisible(false);
	}

}
