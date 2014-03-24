package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.buttons.SessionButtonsPanel;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;

/**
 * Sets up upper toolbar of PlanningPoker tab
 * 
 * @author troyling
 * 
 */
public class ToolbarView extends DefaultToolbarView {
	private final SessionButtonsPanel sessionPanel = new SessionButtonsPanel();

	/**
	 * creates and positions buttons in the upper toolbar
	 */
	public ToolbarView() {
		this.addGroup(sessionPanel);
	}

	/**
	 * Method getSessionButtonsPanel.
	 * 
	 * @return SessionButtonsPanel
	 */
	public SessionButtonsPanel getSessionButtonsPanel() {
		return this.sessionPanel;
	}

}
