package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

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

	}
}



