/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.sessions;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author troyling
 * 
 */
public class SessionPanel extends JPanel {
	private JPanel mainPanel;

	/**
	 * Constructor for creating a session
	 * 
	 * @param parentID
	 *            the parent id, or -1 if no parent.
	 */
	public SessionPanel(int parentID) {
		// TODO add a session model
		// Session session = new Session();
		
		this.buildLayout();	
	}
	
	/**
	 *  Builds the layout of the panel
	 */
	private void buildLayout() {
		// TODO Auto-generated method stub
		this.add(new JLabel("Hello World"));
		
	}
}
