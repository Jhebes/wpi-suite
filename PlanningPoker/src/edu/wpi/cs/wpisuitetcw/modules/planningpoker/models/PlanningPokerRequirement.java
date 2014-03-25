package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * @author Nicholas Kalamvokis and Matt Suarez
 * @date 3/24/2014
 */

public class PlanningPokerRequirement extends Requirement {
	private int sessionID;
	
	/**
	 * Constructs the PlanningPokerObject
	 */
	public PlanningPokerRequirement() {
		setSessionID(-1);
		System.out.println("Session ID:" + this.sessionID);
	}

	public PlanningPokerRequirement(int id) {
		setSessionID(id);
		System.out.println("Session ID:" + id);
	}
	
	/**
	 * Retrieves the ID of the current Planning Poker Session
	 * @return Session ID
	 */
	public int getSessionID() {
		return sessionID;
	}

	/**
	 * Sets the ID of the current Planning Poker Session
	 * 
	 */
	public void setSessionID(int id) {
		this.sessionID = id;
	}

}
