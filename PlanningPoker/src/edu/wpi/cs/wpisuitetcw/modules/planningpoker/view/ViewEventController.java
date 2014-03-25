/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;

/**
 * @author troyling
 * 
 */
public class ViewEventController {
	private static ViewEventController instance = null;
	private MainView main;
	private ToolbarView toolbarView;

	/**
	 * Default constructor for ViewEventController. It is set to private to
	 * prevent instantiation.
	 */
	private ViewEventController() {
	}

	/**
	 * Returns the instance of the ViewEventController
	 * 
	 * @return The instance of the controller
	 */
	public static ViewEventController getInstance() {
		if (instance == null) {
			instance = new ViewEventController();
		}
		return instance;
	}
	
	/**
	 * Opens a new tab for the creation of a session
	 */
	public void createSession() {
		CreateSessionPanel newSession = new CreateSessionPanel();
		main.addTab("New Session", null, newSession, "New session.");
		main.invalidate(); // force the tabbedpane to redraw
		main.repaint();
		main.setSelectedComponent(newSession);
	}
	/**
	 * Sets the toolbarview to the given toolbar
	 * @param tb the toolbar to be set as active.
	 */
	public void setToolBar(ToolbarView toolbarView) {
		this.toolbarView = toolbarView;
		this.toolbarView.repaint();
	}
	
	/**
	 * Sets the main view to the given view.
	
	 * @param mainview MainView
	 */
	public void setMainView(MainView mainview) {
		main = mainview;
		
	}
	
	
}
