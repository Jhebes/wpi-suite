/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionPanel;

/**
 * @author troyling
 *
 */
public class ViewSessionInfoPanel extends JPanel {
	private final PlanningPokerSession session;
	private final ViewSessionPanel parentPanel;
	
	public ViewSessionInfoPanel(ViewSessionPanel parentPanel, PlanningPokerSession session) {
		this.parentPanel = parentPanel;
		this.session = session;
		
		// create labels for data field;
		JLabel labelName = new JLabel("Name ");
		JLabel labelSessionName = new JLabel(this.session.getName());
		
		this.add(labelName);
		this.add(labelSessionName);
		
	}
	
}