package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics;

/**
 * Display mode of a card
 */
public enum CardDisplayMode {
	CREATE("Create"),
	DISPLAY("Display");
	
	private final String type;
	
	/**
	 * Constructor for CardMode.
	 * @param s String
	 */
	private CardDisplayMode(String s)
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
