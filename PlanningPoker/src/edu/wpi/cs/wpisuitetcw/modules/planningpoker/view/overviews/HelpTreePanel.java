/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;

/**
 * @author Jenny12593
 * 
 */
public class HelpTreePanel extends JScrollPane implements
		TreeSelectionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JTree tree;

	public HelpTreePanel() {

		this.setViewportView(tree);
		this.refresh();
	}

	public void refresh() {
		JLabel test = new JLabel();
		this.add(test);

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("General Help");

		createTreeNodes(top);
		tree = new JTree(top);

		// Where the tree is initialized:
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setCellRenderer(new CustomTreeCellRenderer());
		// tree.addMouseListener(this); // add a listener to check for clicking
		// tree.addTreeSelectionListener(this);

		JScrollPane treeView = new JScrollPane(tree);
		this.add(treeView);
		this.setViewportView(tree);

		// update the ViewEventController so it contains the right tree
		// ViewEventManager.getInstance().setHelpTree(this);
	}

	private void createTreeNodes(DefaultMutableTreeNode top) {

		DefaultMutableTreeNode Level1HelpNode = null;
		DefaultMutableTreeNode Level2HelpNode = null;

		Level1HelpNode = new DefaultMutableTreeNode("Some more help");
		top.add(Level1HelpNode);

		Level1HelpNode = new DefaultMutableTreeNode("I don't get it");
		top.add(Level1HelpNode);

		Level2HelpNode = new DefaultMutableTreeNode("I am desperately lost");
		Level1HelpNode.add(Level2HelpNode);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		// we no longer want to show the session table. we only want to show the
		// tree and to keep the welcome page up when interacting with the tree

		// mouse position
		int x = e.getX();
		int y = e.getY();

		if (e.getClickCount() == 1) {
			TreePath path = tree.getPathForLocation(x, y);
			if (path != null) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();
				if (node != null) {
					// open a session
					if (node.getUserObject() instanceof HelpDescriptionPanel) {
						// ViewEventManager.getInstance().createHelp();
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void valueChanged(TreeSelectionEvent e) {
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
