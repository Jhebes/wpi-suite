/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionPanel;

/**
 * @author troyling
 *
 */
public class ViewSessionBtnPanel extends JPanel{
	private final JButton addBtn;
	private final ViewSessionPanel parentPanel;
	
	public ViewSessionBtnPanel(ViewSessionPanel parentPanel) {
		this.parentPanel = parentPanel;
		this.addBtn = new JButton("Add");
		
		this.add(this.addBtn);
	}
	

}
