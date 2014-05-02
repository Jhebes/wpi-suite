/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.help;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * Help panel that display the index tree and contents
 * 
 */
public class HelpPanel extends JSplitPane {

	private static final long serialVersionUID = 1L;
	private final HelpTreePanel treePanel;
	private final HelpDescriptionPanel helpDescription;
	private final JPanel rightPanel;

	public HelpPanel() {

		rightPanel = new JPanel();

		treePanel = new HelpTreePanel();

		helpDescription = new HelpDescriptionPanel();

		// Set layout for right panel;
		rightPanel.setLayout(new BorderLayout());

		// Add the JSP to the rightPanel
		rightPanel.add(helpDescription);

		// Set panels background to white (matching table)
		rightPanel.setBackground(Color.WHITE);

		rightPanel.add(helpDescription);

		// Add panels to main JSplitPane
		this.setRightComponent(rightPanel);
		this.setLeftComponent(treePanel);

		// Set divider location between right and left panel
		this.setDividerLocation(220);
		// ViewEventManager.getInstance().setHelpPanel(this);
	}
}
