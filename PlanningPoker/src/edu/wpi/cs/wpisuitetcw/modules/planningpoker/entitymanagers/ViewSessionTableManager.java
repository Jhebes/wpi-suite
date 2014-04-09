package edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.RetrievePlanningPokerRequirementsForSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionTableModel;

public class ViewSessionTableManager {
	/** Hashmap of table models for each session ID */
	private static HashMap<Integer, ViewSessionTableModel> t = new HashMap<Integer, ViewSessionTableModel>();

	public ViewSessionTableModel get(int i) {
		System.out.println("Processing query for table for session " + i);
		this.fetch(i);
		return ViewSessionTableManager.t.get(i);
	}
	
	public void init(int i){
		System.out.println("Initializing session " + String.valueOf(i));
		ViewSessionTableModel a = ViewSessionTableManager.t.get(i);
		if(a == null){
			a = new ViewSessionTableModel();
			
		}
		ViewSessionTableManager.t.put(i, a);
	}
	
	public void refreshRequirements(int i, List<PlanningPokerRequirement> requirements) {
		
		ViewSessionTableModel a = ViewSessionTableManager.t.get(i);
		if(a == null){
			System.out.println("Not present, building");
			a = new ViewSessionTableModel();
			
		}
		a.refreshRequirements(requirements);
		ViewSessionTableManager.t.put(i, a);
		System.out.println("Done");
	}
	public void fetch(int i){
		System.out.println("Fetching session details for session " + i);
		RetrievePlanningPokerRequirementsForSessionController a = new RetrievePlanningPokerRequirementsForSessionController();
		a.refreshData(i);
	}
	
	
}
