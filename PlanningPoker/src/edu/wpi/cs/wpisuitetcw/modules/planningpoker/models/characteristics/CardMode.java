package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics;

/**
 * Display mode of a card
 */
public enum CardMode {
	CREATE("Create"),
	DISPLAY("Display");
	
	private final String type;
	
	/**
	 * Constructor for CardMode.
	 * @param s String
	 */
	private CardMode(String s)
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
