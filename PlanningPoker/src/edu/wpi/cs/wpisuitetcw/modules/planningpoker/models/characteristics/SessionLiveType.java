/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics;

/**
 * Type of a session
 * @author troyling
 *
 */
public enum SessionLiveType {
	LIVE("Live"),
	DISTRIBUTED("Distributed");
	
	private final String type;
	
	/**
	 * Constructor for SessionLiveType.
	 * @param s String
	 */
	private SessionLiveType(String s)
	{
		type = s;
	}
	
	/** 
	 * Method toString
	 * @return String
	 */
	public String toString() {
		return type;
	}
}
