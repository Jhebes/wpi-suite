package edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.RetrievePlanningPokerRequirementsForSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionTableModel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.VoteSessionTableModel;

public class VoteSessionTableManager{
	private static HashMap<UUID, VoteSessionTableModel> t = new HashMap<UUID, VoteSessionTableModel>();

	public VoteSessionTableModel get(UUID i){
		System.out.println("Processing query for table for requirement " + i);
		//this.fetch(i);
		return VoteSessionTableManager.t.get(i);
	}
	
	public void init(UUID i){
		System.out.println("Initializing session " + i.toString());
		VoteSessionTableModel a = VoteSessionTableManager.t.get(i);
		if(a == null){
			a = new VoteSessionTableModel();
			
		}
		VoteSessionTableManager.t.put(i, a);
	}
	
	public void refreshRequirements(UUID i, List<PlanningPokerVote> votes) {
		
		VoteSessionTableModel a = VoteSessionTableManager.t.get(i);
		if(a == null){
			System.out.println("Not present, building");
			a = new VoteSessionTableModel();
			
		}
		a.refreshVotes(votes);
		VoteSessionTableManager.t.put(i, a);
		System.out.println("Done");
	}
	public void fetch(UUID i){
		System.out.println("Fetching session details for requirements " + i.toString());
		RetrievePlanningPokerRequirementsForSessionController a = new RetrievePlanningPokerRequirementsForSessionController();
		//a.refreshData(i);
	}
	
	
}
