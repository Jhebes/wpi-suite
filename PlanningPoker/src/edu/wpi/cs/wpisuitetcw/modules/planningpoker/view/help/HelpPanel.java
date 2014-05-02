/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.help;

import javax.swing.JSplitPane;

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
	private final HelpDescriptionPanel helpDescription;

	public HelpPanel() {

		// set up panels
		treePanel = new HelpTreePanel();
		helpDescription = new HelpDescriptionPanel();

		// set up the entire panel
		this.setRightComponent(helpDescription);
		this.setLeftComponent(treePanel);

		// Set divider location between right and left panel
		this.setDividerLocation(220);
	}

}
