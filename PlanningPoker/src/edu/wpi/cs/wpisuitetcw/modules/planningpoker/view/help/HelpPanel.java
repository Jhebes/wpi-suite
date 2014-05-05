/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.help;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.TreePath;

/**
 * Help panel that display the index tree and contents
 * 
 */
public class HelpPanel extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Panel for displaying indices tree */
	private final HelpTreePanel treePanel;

	/** Panel for displaying a specific help message */
	private final HelpDescriptionPanel descriptionPanel;

	/**
	 * Constructor to create a help panel
	 */
	public HelpPanel() {
		JPanel rightPanel = new JPanel();

		// set up panels
		treePanel = new HelpTreePanel(this);
		descriptionPanel = new HelpDescriptionPanel();

		JScrollPane jsp = new JScrollPane(descriptionPanel);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Set layout for right panel;
		rightPanel.setLayout(new BorderLayout());

		// Add the JSP to the rightPanel
		rightPanel.add(jsp);

		// Set panels background to white (matching table)
		rightPanel.setBackground(Color.WHITE);

		// set up the entire panel
		this.setLeftComponent(treePanel);
		this.setRightComponent(rightPanel);

		// Set divider location between right and left panel
		this.setDividerLocation(220);
	}

	/**
	 * Update the right panel to show the help content
	 */
	public void updateHelpContent(HelpEntry entry) {
		descriptionPanel.updateHelp(entry);
		JTree tree = treePanel.getTree();
		TreePath path = tree.getNextMatch(entry.toString(), 0, null);
		tree.setSelectionPath(path);
	}

}
