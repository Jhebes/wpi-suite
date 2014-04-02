/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionPanel;

/**
 * @author troyling
 *
 */
public class ViewSessionInfoPanel extends JPanel {
	private final String sessionName;
	private final ViewSessionPanel parentPanel;
	
	public ViewSessionInfoPanel(ViewSessionPanel parentPanel, String sessionName) {
		this.parentPanel = parentPanel;
		this.sessionName = sessionName;
		
		// create labels for data field;
		JLabel labelName = new JLabel("Name ");
		JLabel labelSessionName = new JLabel(this.sessionName);
		
		this.add(labelName);
		this.add(labelSessionName);
		
	}
	
}
