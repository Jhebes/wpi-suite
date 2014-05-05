/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.help;

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
		TreeSelectionListener {

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

		// Set selection
		tree.setSelectionInterval(0, 0);
		
		// add a listener
		tree.addTreeSelectionListener(this);

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
	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = e.getPath();
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

	public JTree getTree() {
		return tree;
	}
}
