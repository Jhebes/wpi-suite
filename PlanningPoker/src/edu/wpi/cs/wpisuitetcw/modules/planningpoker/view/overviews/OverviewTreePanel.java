/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.UserStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.GetRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.iterationcontroller.GetIterationController;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The tree panel that greets you on the left side of the overview.
 */
public class OverviewTreePanel extends JScrollPane implements MouseListener,
		TreeSelectionListener {

	/** constant strings */
	private static final String CANCELLED_SESSIONS = "Cancelled Sessions";
	private static final String CLOSED_SESSIONS = "Closed Sessions";
	private static final String OPEN_SESSIONS = "Open Sessions";
	private static final String NEW_SESSIONS = "New Sessions";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Tree for displaying sessions */
	private final JTree tree;
	private final DefaultTreeModel model;

	/** Parent node for the tree */
	private DefaultMutableTreeNode top;

	/** is the tree initialized */
	private boolean initialized = false;

	/** all planning poker sessions */
	private List<PlanningPokerSession> sessions = null;

	/** branches */
	private DefaultMutableTreeNode newSessionNode;
	private DefaultMutableTreeNode openSessionNode;
	private DefaultMutableTreeNode closedSessionNode;
	private DefaultMutableTreeNode cancelledSessionNode;

	/** hashmap to remember the states of nodes */
	private HashMap<DefaultMutableTreeNode, Boolean> nodeStates;

	/** planning poker sessions */
	private PlanningPokerSession[] newSessions;
	private PlanningPokerSession[] openSessions;
	private PlanningPokerSession[] closedSessions;
	private PlanningPokerSession[] cancelledSessions;

	public OverviewTreePanel() {

		// setup nodes
		top = new DefaultMutableTreeNode("All Sessions");

		// branches
		newSessionNode = new DefaultMutableTreeNode();
		openSessionNode = new DefaultMutableTreeNode();
		closedSessionNode = new DefaultMutableTreeNode();
		cancelledSessionNode = new DefaultMutableTreeNode();

		// hashmap for saving states of the nodes
		nodeStates = new HashMap<DefaultMutableTreeNode, Boolean>();

		// setup the tree
		model = new DefaultTreeModel(top);
		tree = new JTree(model); // create the tree with the top created above
		tree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					final TreePath path = tree.getSelectionPath();
					if (path != null) {
						final DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
								.getLastSelectedPathComponent();
						if (node != null) {
							// open a session
							if (node.getUserObject() instanceof PlanningPokerSession) {
								ViewEventManager.getInstance().viewSession(
										(PlanningPokerSession) node.getUserObject());
							}
						}
					}
				}
			}
		});

		// tell it that it can only select one thing at a time
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setToggleClickCount(0);

		tree.setCellRenderer(new CustomTreeCellRenderer());

		// add a listener to check for clicking
		tree.addMouseListener(this);
		tree.addTreeSelectionListener(this);

		// setup viewport
		this.setViewportView(tree);

		// refresh the session to start
		refresh();

	}

	/**
	 * Refresh the tree with updates on planning poker session
	 */
	public void refresh() {

		// remove all children contents to prevent from duplication
		clearAllNodes();

		try {
			// get a list of sessions
			sessions = SessionStash.getInstance().getSessions();

			if (sessions != null) {
				newSessions = sortForNewSessions();
				openSessions = sortForOpenSessions();
				closedSessions = sortForClosedSessions();
				cancelledSessions = sortForCancelledSessions();

				// add sessions to respective nodes
				addSessionsToNode(newSessions, newSessionNode);
				addSessionsToNode(openSessions, openSessionNode);
				addSessionsToNode(closedSessions, closedSessionNode);
				addSessionsToNode(cancelledSessions, cancelledSessionNode);

			}

		} catch (NullPointerException e) {
			Logger.getLogger("PlanningPoker").log(Level.FINE,
					"Network configuration error", e);
		}

		// setup name for all branches
		setupBranchNames();

		// adds nodes to the tree
		top.add(newSessionNode);
		top.add(openSessionNode);
		top.add(closedSessionNode);
		top.add(cancelledSessionNode);

		// set up the tree
		model.setRoot(top);
		tree.setModel(model);

		if (!initialized) {
			// initialize the states
			initTreeStates();
		} else {
			// restore the states for the tree
			maintainTreeStates();
		}

		// update the ViewEventController so it contains the right tree
		ViewEventManager.getInstance().setOverviewTree(this);
		
		updateUI();
	}

	/**
	 * remove all children for the branch
	 */
	private void clearAllNodes() {
		newSessionNode.removeAllChildren();
		openSessionNode.removeAllChildren();
		closedSessionNode.removeAllChildren();
		cancelledSessionNode.removeAllChildren();
		top.removeAllChildren();
	}

	/**
	 * initialize the states of the tree and set up the tree so that new session
	 * and open session branches are initially expanded
	 * 
	 */
	public void initTreeStates() {
		// tree path to each node
		final TreePath newSessionPath = new TreePath(newSessionNode.getPath());
		final TreePath openSessionPath = new TreePath(openSessionNode.getPath());

		// expand new session and open session
		tree.expandPath(newSessionPath);
		tree.expandPath(openSessionPath);

		// saves all the states
		updateTreeStates();
	}

	/**
	 * maintains tree expansion based on the states remembered
	 */
	private void maintainTreeStates() {
		for (Entry<DefaultMutableTreeNode, Boolean> e : nodeStates.entrySet()) {
			boolean isExpanded = e.getValue();
			TreePath path = new TreePath(e.getKey().getPath());

			// restore expansion for the node
			if (isExpanded) {
				tree.expandPath(path);
			}
		}
	}

	/**
	 * update tree expansion states
	 */
	private void updateTreeStates() {
		// tree path to each node
		final TreePath newSessionPath = new TreePath(newSessionNode.getPath());
		final TreePath openSessionPath = new TreePath(openSessionNode.getPath());
		final TreePath closedSessionPath = new TreePath(
				closedSessionNode.getPath());
		final TreePath cancelledSessionPath = new TreePath(
				cancelledSessionNode.getPath());

		// saves all the states
		nodeStates.put(newSessionNode, tree.isExpanded(newSessionPath));
		nodeStates.put(openSessionNode, tree.isExpanded(openSessionPath));
		nodeStates.put(closedSessionNode, tree.isExpanded(closedSessionPath));
		nodeStates.put(cancelledSessionNode,
				tree.isExpanded(cancelledSessionPath));
	}

	/**
	 * Adds given sessions to the given tree
	 * 
	 * @param sessions
	 * @param node
	 */
	private void addSessionsToNode(PlanningPokerSession[] sessions,
			DefaultMutableTreeNode node) {
		for (PlanningPokerSession s : sessions) {
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(s);
			node.add(newNode);
		}
	}

	/**
	 * Setup the name for all branches in the tree
	 */
	private void setupBranchNames() {
		// set names for nodes
		if (newSessions.length != 0) {
			newSessionNode.setUserObject(NEW_SESSIONS + " ("
					+ newSessions.length + ")");
		} else {
			newSessionNode.setUserObject(NEW_SESSIONS);
		}

		if (openSessions.length != 0) {
			openSessionNode.setUserObject(OPEN_SESSIONS + " ("
					+ openSessions.length + ")");
		} else {
			openSessionNode.setUserObject(OPEN_SESSIONS);
		}

		if (closedSessions.length != 0) {
			closedSessionNode.setUserObject(CLOSED_SESSIONS + " ("
					+ closedSessions.length + ")");
		} else {
			closedSessionNode.setUserObject(CLOSED_SESSIONS);
		}

		if (cancelledSessions.length != 0) {
			cancelledSessionNode.setUserObject(CANCELLED_SESSIONS + " ("
					+ cancelledSessions.length + ")");
		} else {
			cancelledSessionNode.setUserObject(CANCELLED_SESSIONS);
		}
	}

	/**
	 * sort for open sessions from a given array of sessions
	 * 
	 * @return open sessions
	 */
	private PlanningPokerSession[] sortForNewSessions() {
		final List<PlanningPokerSession> tempNewSessions = new ArrayList<PlanningPokerSession>();
		final String username = ConfigManager.getConfig().getUserName();
		for (PlanningPokerSession pps : sessions) {
			// Do not display the "default" planning poker session
			if (pps.isNew() && pps.getID() != 1 && pps.getOwnerUserName().equals(username)) {
				tempNewSessions.add(pps);
			}
		}
		PlanningPokerSession[] newSessions = new PlanningPokerSession[tempNewSessions
				.size()];
		newSessions = tempNewSessions.toArray(newSessions);
		return newSessions;
	}

	/**
	 * sort for open sessions from a given array of sessions
	 * 
	 * @param allSessions
	 * @return open sessions
	 */
	private PlanningPokerSession[] sortForOpenSessions() {
		final List<PlanningPokerSession> tempOpenSessions = new ArrayList<PlanningPokerSession>();
		for (PlanningPokerSession pps : sessions) {
			if (pps.isOpen()) {
				tempOpenSessions.add(pps);
			}
		}
		PlanningPokerSession[] openSessions = new PlanningPokerSession[tempOpenSessions
				.size()];
		openSessions = tempOpenSessions.toArray(openSessions);
		return openSessions;
	}

	/**
	 * sort for closed sessions from a given array of sessions
	 * 
	 * @param allSessions
	 * @return closed sessions
	 */
	private PlanningPokerSession[] sortForClosedSessions() {
		final List<PlanningPokerSession> tempClosedSessions = new ArrayList<PlanningPokerSession>();
		for (PlanningPokerSession pps : sessions) {
			if (pps.isClosed()) {
				tempClosedSessions.add(pps);
			}
		}
		PlanningPokerSession[] closedSessions = new PlanningPokerSession[tempClosedSessions
				.size()];
		closedSessions = tempClosedSessions.toArray(closedSessions);
		return closedSessions;
	}

	/**
	 * 
	 * sort for cancelled sessions from a given array of sessions
	 * 
	 * @param allSessions
	 * @return cancelled sessions
	 */
	private PlanningPokerSession[] sortForCancelledSessions() {
		final ArrayList<PlanningPokerSession> tempCancelledSessions = new ArrayList<PlanningPokerSession>();
		for (PlanningPokerSession pps : sessions) {
			if (pps.isCancelled()) {
				tempCancelledSessions.add(pps);
			}
		}
		PlanningPokerSession[] cancelledSessions = new PlanningPokerSession[tempCancelledSessions
				.size()];
		cancelledSessions = tempCancelledSessions.toArray(cancelledSessions);
		return cancelledSessions;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(TreeSelectionEvent e) {
	}

	/**
	 * This should perform some actions
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			final TreePath path = tree.getPathForLocation(e.getX(), e.getY());
			if (path != null) {
				final DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();
				if (node != null) {
					// open a session
					if (node.getUserObject() instanceof PlanningPokerSession) {
						ViewEventManager.getInstance().viewSession(
								(PlanningPokerSession) node.getUserObject());
					}
				}
			}
		}

		// Update all state changes
		updateTreeStates();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	/**
	 * Overrides the paintComponent method to retrieve the requirements on the first painting.
	 * 
	 * @param g	The component object to paint
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		if (!initialized) {
			try {
				SessionStash.getInstance().synchronize();
				UserStash.getInstance().synchronize();
				initialized = true;
			} catch (Exception e) {
				
			}
		}
	}
	

}
