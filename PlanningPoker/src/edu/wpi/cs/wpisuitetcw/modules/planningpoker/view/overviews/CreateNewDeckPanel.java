/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * @author troyling
 *
 */
public class CreateNewDeckPanel extends JSplitPane {
	private final JPanel leftPanel;
	private final JPanel rightPanel;
	
	public CreateNewDeckPanel() {
		this.leftPanel = new JPanel();
		this.rightPanel = new JPanel();
		
		leftPanel.add(new JLabel("hello"));
		rightPanel.add(new JLabel("world"));
		
		this.setLeftComponent(leftPanel);
		this.setRightComponent(rightPanel);
	}
	
	
}
