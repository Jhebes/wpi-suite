package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * @author Nicholas Kalamvokis and Matt Suarez
 * @date 3/24/2014
 */

public class PlanningPokerRequirement extends Requirement {
	private int sessionId;

	/**
	 * Retrieves the ID of the current Planning Poker Session
	 * @return Session ID
	 */
	public int getSessionID() {
		return sessionId;
	}

	/**
	 * Sets the ID of the current Planning Poker Session
	 * 
	 */
	public void setSessionID(int id) {
		this.sessionId = id;
	}

	/**
	 * Constructs the PlanningPokerObject
	 */
	public PlanningPokerRequirement(int id) {
		setId(id);
		System.out.println("Session ID:" + id);

	}

}
