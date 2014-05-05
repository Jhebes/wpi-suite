/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.help;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;

/**
 * 
 * Tree structure which contains the indices for the help contents
 * 
 */
public class HelpTreePanel extends JScrollPane implements
		TreeSelectionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Tree for the help entries */
	private final JTree tree;

	/** Enums for the HelpEntry */
	private final HelpEntry[] entries = HelpEntry.values();

	/** Node of the tree */
	private final DefaultMutableTreeNode top;

	/** The containing panel */
	private final HelpPanel parentPanel;

	public HelpTreePanel(HelpPanel parentPanel) {
		this.parentPanel = parentPanel;

		// set up tree
		top = new DefaultMutableTreeNode("Help Guide");

		tree = new JTree(top);

		// adds the help entries to the tree
		setupHelpEntries();
		expandTree();

		// tree should be single selection
		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setCellRenderer(new CustomTreeCellRenderer());

		// add a listener to check for clicking
		tree.addMouseListener(this);

		// add the tree to the view
		JScrollPane treeView = new JScrollPane(tree);
		this.add(treeView);
		this.setViewportView(tree);
		this.setViewportView(tree);

	}

	/**
	 * Adds all help entries to the tree
	 */
	private void setupHelpEntries() {
		for (HelpEntry entry : entries) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(entry);
			top.add(node);
		}
	}

	/**
	 * Expand the tree
	 */
	private void expandTree() {
		TreePath path = new TreePath(top.getPath());
		tree.expandPath(path);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 1) {
			TreePath path = tree.getPathForLocation(e.getX(), e.getY());
			if (path != null) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();
				if (node != null) {
					// update the right panel with the selected help entry
					parentPanel.updateHelpContent((HelpEntry) node
							.getUserObject());
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
