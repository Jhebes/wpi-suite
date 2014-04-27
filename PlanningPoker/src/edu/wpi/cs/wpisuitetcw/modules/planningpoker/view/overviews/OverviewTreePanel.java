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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;

public class OverviewTreePanel extends JScrollPane implements MouseListener,
		TreeSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree tree;
	private boolean initialized = false;
	private List<PlanningPokerSession> sessions = null;

	public OverviewTreePanel() {
		this.setViewportView(tree);
		this.refresh();
	}

	public void refresh() {
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("All Sessions");
		// DefaultMutableTreeNode draftSessionNode = new
		// DefaultMutableTreeNode("Draft Sessions");
		final DefaultMutableTreeNode newSessionNode = new DefaultMutableTreeNode(
				"New Sessions");
		final DefaultMutableTreeNode openSessionNode = new DefaultMutableTreeNode(
				"Open Sessions");
		final DefaultMutableTreeNode closedSessionNode = new DefaultMutableTreeNode(
				"Closed Sessions");
		
		try {
			// get a list of sessions
			this.sessions = SessionStash.getInstance().getSessions();

			if (this.sessions != null) {
				final PlanningPokerSession[] newSessions = sortForNewSessions(this.sessions);
				final PlanningPokerSession[] openSessions = sortForOpenSessions(this.sessions);
				final PlanningPokerSession[] closedSessions = sortForClosedSessions(this.sessions);

				// add new sessions to the node
				for (PlanningPokerSession s : newSessions) {
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
							s);
					newSessionNode.add(newNode);
				}

				// add open sessions to the node
				for (PlanningPokerSession s : openSessions) {
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
							s);
					openSessionNode.add(newNode);
				}

				// add closed sessions to the node
				for (PlanningPokerSession s : closedSessions) {
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
							s);
					closedSessionNode.add(newNode);
				}
			}

		} catch (NullPointerException e) {
			Logger.getLogger("PlanningPoker").log(Level.FINE,
					"Network configuration error", e);
		}

		top.add(newSessionNode);
		top.add(openSessionNode);
		top.add(closedSessionNode);

		tree = new JTree(top); // create the tree with the top created above
		// tell it that it can only select one thing at a time
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setToggleClickCount(0);

		tree.setCellRenderer(new CustomTreeCellRenderer());
		tree.addMouseListener(this); // add a listener to check for clicking
		tree.addTreeSelectionListener(this);

		this.setViewportView(tree);

		// update the ViewEventController so it contains the right tree
		ViewEventManager.getInstance().setOverviewTree(this);
	}

	/**
	 * sort for open sessions from a given array of sessions
	 * 
	 * @param allSessions
	 * @return open sessions
	 */
	private PlanningPokerSession[] sortForNewSessions(
			List<PlanningPokerSession> allSessions) {
		final ArrayList<PlanningPokerSession> tempNewSessions = new ArrayList<PlanningPokerSession>();
		for (PlanningPokerSession pps : allSessions) {
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
	private PlanningPokerSession[] sortForOpenSessions(
			List<PlanningPokerSession> allSessions) {
		final ArrayList<PlanningPokerSession> tempOpenSessions = new ArrayList<PlanningPokerSession>();
		for (PlanningPokerSession pps : allSessions) {
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
	private PlanningPokerSession[] sortForClosedSessions(
			List<PlanningPokerSession> allSessions) {
		final ArrayList<PlanningPokerSession> tempClosedSessions = new ArrayList<PlanningPokerSession>();
		for (PlanningPokerSession pps : allSessions) {
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
		final int x = e.getX();
		final int y = e.getY();

		if (e.getClickCount() == 2) {
			final TreePath path = tree.getPathForLocation(x, y);
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
