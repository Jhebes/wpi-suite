/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.ActivateSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionPanel;

/**
 * @author troyling
 *
 */
public class ViewSessionBtnPanel extends JPanel{
	private final JButton activateBtn;
	private final JButton addBtn;
	private final ViewSessionPanel parentPanel;
	
	
	public ViewSessionBtnPanel(ViewSessionPanel parentPanel) {
		this.parentPanel = parentPanel;
		
		// set up buttons
		this.addBtn = new JButton("Add Requirements");
		this.activateBtn = new JButton("Activate Session");
		
		activateBtn.addActionListener(new ActivateSessionController(parentPanel, parentPanel.getPPSession()));
		
		// add buttons
		this.add(this.addBtn);
		this.add(this.activateBtn);
	}
	

}
