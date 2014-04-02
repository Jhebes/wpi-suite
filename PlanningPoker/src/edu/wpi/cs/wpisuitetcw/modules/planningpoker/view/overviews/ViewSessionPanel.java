/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionBtnPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionInfoPanel;

/**
 * @author troyling
 *
 */
public class ViewSessionPanel extends JSplitPane{
	final private ViewSessionBtnPanel buttonPanel;
	final private ViewSessionInfoPanel infoPanel;
	
	/**
	 * Create a view session panel
	 */
	public ViewSessionPanel(String sessionName) {
		this.infoPanel = new ViewSessionInfoPanel(this, sessionName);
		this.buttonPanel = new ViewSessionBtnPanel(this);
		
		// delete these
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		this.setLayout(new BorderLayout());
		this.add(infoPanel, BorderLayout.WEST);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
	}
	
	
}
