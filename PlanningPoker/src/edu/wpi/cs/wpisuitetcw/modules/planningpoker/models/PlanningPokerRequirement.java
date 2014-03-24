/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models;


import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * @author Nicholas Kalamvokis and Matt Suarez
 * @date 3/24/2014
 */


public class PlanningPokerRequirement extends AbstractModel{

	public PlanningPokerRequirement() {
		
		
		
		
	}

	@Override
	public void save() {
		
	}

	@Override
	public void delete() {
		
	}

	@Override
	public String toJSON() {
		return new Gson().toJson(this, PlanningPokerSession.class);
	}

	@Override
	public Boolean identify(Object o) {
		
		return null;
	}

}
