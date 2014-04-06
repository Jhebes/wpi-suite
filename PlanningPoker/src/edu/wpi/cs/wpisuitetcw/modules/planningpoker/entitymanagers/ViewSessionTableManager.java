package edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers;

import java.util.HashMap;
import java.util.List;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionTableModel;

public class ViewSessionTableManager {
	private HashMap<Integer, ViewSessionTableModel> t = new HashMap<Integer, ViewSessionTableModel>();
	private static ViewSessionTableManager instance;
	public ViewSessionTableModel get(int i){
		System.out.println("Processing query for table for session " + i);
		return this.t.get(i);
	}
	
	
	public static ViewSessionTableManager getInstance() {
		if(instance == null) {
			instance = new ViewSessionTableManager();
		}
		return instance;
	}
	public void refreshRequirements(int i, List<PlanningPokerRequirement> requirements) {
		System.out.println("Refreshing table for session " + i);
		ViewSessionTableModel a = this.t.get(i);
		if(a == null){
			System.out.println("Not present, building");
			a = new ViewSessionTableModel();
			
		}
		a.refreshRequirements(requirements);
		this.t.put(i, a);
		System.out.println("Done");
	}
	
}
