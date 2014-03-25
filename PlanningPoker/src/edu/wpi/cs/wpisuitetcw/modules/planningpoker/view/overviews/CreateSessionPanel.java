package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

/**
 * 
 * @author remckenna, bjsharron
 *
 */

public class CreateSessionPanel extends JSplitPane {
	
	//The right panel holds info about selected requirements
	private final JPanel rightPanel;
	//The left leftPanel contains reqList, name, and Deadline.
	private final JPanel leftPanel;
		
		//Constructor for our Create Session Panel
		public CreateSessionPanel(){
			rightPanel = new JPanel();
			leftPanel = new JPanel();
			
			//Dummy list of Reqs for the session
			String dummyReqs[] = {"dummy1", "dummy2"};
			
			//Creates a List view in the UI that displays the dummy list
			JList<String> existingReqsList = new JList<String>(dummyReqs);
			
			//Creates a Name text field in the leftPane
			leftPanel.add(new JLabel("Name"));
			JTextField nameField = new JTextField(10);
			leftPanel.add(nameField);
			
			//Creates a deadline text field in the leftPane
			leftPanel.add(new JLabel("Deadline"));
			JTextField deadlineField = new JTextField(10);
			leftPanel.add(deadlineField);
			
			//Creates a list of Reqs for the session
			leftPanel.add(new JLabel("Deadline"));
			leftPanel.add(existingReqsList);
			
			
			this.setRightComponent((rightPanel));
			this.setLeftComponent(leftPanel);
			this.setDividerLocation(180);
			
			

	}
}



