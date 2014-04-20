package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionReqPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;

public class ViewSessionReqPanelListeners{
	public static class MoveSelectedToSession implements ActionListener{
		private ViewSessionReqPanel view;
		public MoveSelectedToSession(ViewSessionReqPanel view){
			this.view = view;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			PlanningPokerSession target = SessionStash.getInstance().getSessionByID(view.session.getID());
			for(String a : this.view.getLeftSelectedRequirements()){
				SessionStash.getInstance().getSessionByID(1).passRequirementTo(a, target);	
			}
			SessionStash.getInstance().synchronize();
			RequirementTableManager a1 = new RequirementTableManager();
			a1.refreshRequirements(1, SessionStash.getInstance().getSessionByID(1).getRequirements());
			a1.refreshRequirements(target.getID(), SessionStash.getInstance().getSessionByID(target.getID()).getRequirements());
		}
	}
	public static class MoveAllToSession implements ActionListener{
		private ViewSessionReqPanel view;
		public MoveAllToSession(ViewSessionReqPanel view){
			this.view = view;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			PlanningPokerSession target = SessionStash.getInstance().getSessionByID(view.session.getID());

			for(String a : this.view.getAllLeftRequirements()){
				SessionStash.getInstance().getSessionByID(1).passRequirementTo(a, (SessionStash.getInstance().getSessionByID(view.session.getID())));	
			}
			SessionStash.getInstance().synchronize();
			RequirementTableManager a1 = new RequirementTableManager();
			a1.refreshRequirements(1, SessionStash.getInstance().getSessionByID(1).getRequirements());
			a1.refreshRequirements(target.getID(), SessionStash.getInstance().getSessionByID(target.getID()).getRequirements());

		}
	}
	public static class MoveSelectedToAll implements ActionListener{
		private ViewSessionReqPanel view;
		public MoveSelectedToAll(ViewSessionReqPanel view){
			this.view = view;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			PlanningPokerSession target = SessionStash.getInstance().getSessionByID(view.session.getID());

			for(String a : this.view.getRightSelectedRequirements()){
				SessionStash.getInstance().getSessionByID(view.session.getID()).passRequirementTo(a, (SessionStash.getInstance().getSessionByID(1)));	
			}
			SessionStash.getInstance().synchronize();
			RequirementTableManager a1 = new RequirementTableManager();
			a1.refreshRequirements(1, SessionStash.getInstance().getSessionByID(1).getRequirements());
			a1.refreshRequirements(target.getID(), SessionStash.getInstance().getSessionByID(target.getID()).getRequirements());

		}
	}
	public static class MoveAllToAll implements ActionListener{
		private ViewSessionReqPanel view;
		public MoveAllToAll(ViewSessionReqPanel view){
			this.view = view;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			PlanningPokerSession target = SessionStash.getInstance().getSessionByID(view.session.getID());

			for(String a : this.view.getAllRightRequirements()){
				SessionStash.getInstance().getSessionByID(view.session.getID()).passRequirementTo(a, (SessionStash.getInstance().getSessionByID(1)));	
			}
			SessionStash.getInstance().synchronize();
			RequirementTableManager a1 = new RequirementTableManager();
			a1.refreshRequirements(1, SessionStash.getInstance().getSessionByID(1).getRequirements());
			a1.refreshRequirements(target.getID(), SessionStash.getInstance().getSessionByID(target.getID()).getRequirements());

		}
	}
	public static class AddRequirement implements ActionListener{
		private ViewSessionReqPanel view;
		public AddRequirement(ViewSessionReqPanel view){
			this.view = view;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			PlanningPokerRequirement r = new PlanningPokerRequirement();
			r.setName(this.view.getNewReqName());
			this.view.clearNewReqName();
			r.setDescription(this.view.getNewReqDesc());
			this.view.clearNewReqDesc();
			SessionStash.getInstance().getSessionByID(1).addRequirement(r);
			
			SessionStash.getInstance().synchronize();
			RequirementTableManager a1 = new RequirementTableManager();
			a1.refreshRequirements(1, SessionStash.getInstance().getSessionByID(1).getRequirements());

		}
	}	
			
	
}
