package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session.GetAllSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session.GetClosedSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session.GetOpenSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;

public class OverviewTreePanel extends JScrollPane implements MouseListener,
		TreeSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree tree;

	public OverviewTreePanel() {
		this.setViewportView(tree);
		ViewEventManager.getInstance().setOverviewTree(this);
		this.refresh();
	}

	public void refresh() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("All Sessions");
		DefaultMutableTreeNode openSessionNode = new DefaultMutableTreeNode(
				"Open Sessions");
		DefaultMutableTreeNode closedSessionNode = new DefaultMutableTreeNode(
				"Closed Sessions");

		try {
			GetAllSessionsController.getInstance().retrieveSessions();
			
		} catch (NullPointerException e) {
			System.out.println("Network configuration error");
		}

		PlanningPokerSession[] allSessions = OverviewTableSessionTableModel
				.getInstance().getSessions();
		System.out.println("There are " + allSessions.length);

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

		// tree.setDragEnabled(true);
		// tree.setDropMode(DropMode.ON);

		this.setViewportView(tree);

		// update the ViewEventControler so it contains the right tree
		ViewEventManager.getInstance().setOverviewTree(this);

		System.out.println("finished refreshing the tree");

	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * This should perform some actions
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		if (e.getClickCount() == 2) {
			TreePath path = tree.getPathForLocation(x, y);
			if (path != null) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();
				if (node != null) {
					if (node.toString().equals("All Sessions")) {
						System.out.println("all sessions refreshing");
						GetAllSessionsController.getInstance()
								.retrieveSessions();
					} else if (node.toString().equals("Open Sessions")) {
						System.out.println("open sessions refreshing");
						GetOpenSessionsController.getInstance()
								.retrieveOpenSessions();
					} else if (node.toString().equals("Closed Sessions")) {
						System.out.println("closed sessions refreshing");
						GetClosedSessionsController.getInstance()
								.retrieveClosedSession();
					} else if (node.getUserObject() instanceof PlanningPokerSession) {
						ViewEventManager.getInstance().viewSession(
								(PlanningPokerSession) node.getUserObject());
					}
				}
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
