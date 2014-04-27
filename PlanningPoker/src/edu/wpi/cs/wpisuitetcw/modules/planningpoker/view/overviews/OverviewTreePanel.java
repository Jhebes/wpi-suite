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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
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
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;

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
	private JTree tree;
	private DefaultTreeModel model;

	/** Parent node for the tree */
	private DefaultMutableTreeNode top;

	/** is the tree initialized */
	private boolean initialized = false;

	/** all planning poker sessions */
	private ArrayList<PlanningPokerSession> sessions = null;

	/** branches */
	private DefaultMutableTreeNode newSessionNode;
	private DefaultMutableTreeNode openSessionNode;
	private DefaultMutableTreeNode closedSessionNode;
	private DefaultMutableTreeNode cancelledSessionNode;

	/** planning poker sessions */
	private PlanningPokerSession[] newSessions;
	private PlanningPokerSession[] openSessions;
	private PlanningPokerSession[] closedSessions;
	private PlanningPokerSession[] cancelledSessions;

	public OverviewTreePanel() {

		// setup the tree
		model = new DefaultTreeModel(null);
		tree = new JTree(model); // create the tree with the top created above

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
		top = new DefaultMutableTreeNode("All Sessions");
		// DefaultMutableTreeNode draftSessionNode = new
		// DefaultMutableTreeNode("Draft Sessions");
		newSessionNode = new DefaultMutableTreeNode();
		openSessionNode = new DefaultMutableTreeNode();
		closedSessionNode = new DefaultMutableTreeNode();
		cancelledSessionNode = new DefaultMutableTreeNode();

		try {
			// get a list of sessions
			sessions = SessionStash.getInstance().getSessions();

			if (this.sessions != null) {
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

		// update the ViewEventController so it contains the right tree
		ViewEventManager.getInstance().setOverviewTree(this);
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
	 * @param allSessions
	 * @return open sessions
	 */
	private PlanningPokerSession[] sortForNewSessions() {
		ArrayList<PlanningPokerSession> tempNewSessions = new ArrayList<PlanningPokerSession>();
		for (PlanningPokerSession pps : sessions) {
			// Do not display the "default" planning poker session
			if (pps.isNew() && pps.getID() != 1) {
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
		ArrayList<PlanningPokerSession> tempOpenSessions = new ArrayList<PlanningPokerSession>();
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
		ArrayList<PlanningPokerSession> tempClosedSessions = new ArrayList<PlanningPokerSession>();
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
		ArrayList<PlanningPokerSession> tempCancelledSessions = new ArrayList<PlanningPokerSession>();
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

		// we no longer want to show the session table. we only want to show the
		// tree and to keep the welcome page up when interacting with the tree
		if (!initialized) {
			// retrieve sessions and initialize the tree
			SessionStash.getInstance().synchronize();

			if (this.sessions.size() != 0) {
				initialized = true;
			}

			this.refresh();
		}

		// mouse position
		int x = e.getX();
		int y = e.getY();

		if (e.getClickCount() == 2) {
			TreePath path = tree.getPathForLocation(x, y);
			if (path != null) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
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

}
