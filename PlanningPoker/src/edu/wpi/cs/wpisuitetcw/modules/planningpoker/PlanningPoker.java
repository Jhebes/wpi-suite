/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;

/**
 * @author troyling
 *
 */
public class PlanningPoker implements IJanewayModule {
	private ArrayList<JanewayTabModel> tabs;
	
	public PlanningPoker() {
		tabs = new ArrayList<JanewayTabModel>();
		
		// toolbar panel
		ToolbarView toolbarPanel = new ToolbarView();
	    
	    // main view
		JTabbedPane mainview = new MainView();
	    
	    // add this to tab
	    JanewayTabModel tab1 = new JanewayTabModel(getName(), new ImageIcon(), toolbarPanel, mainview);
	    
	    // add the tab
	    tabs.add(tab1);
	}
	
	@Override
	public String getName() {
		return "PlanningPoker";
	}

	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}
}


