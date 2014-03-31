package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * This model encapsulates a requirement in the context of planning poker. A
 * requirement in planning poker is identical to a requirement in the canonical
 * RequirementsManager module, but has the additional trait of belonging to a
 * particular planning poker session.
 * 
 * @author Nicholas Kalamvokis and Matt Suarez
 * @date 3/24/2014
 */
public class PlanningPokerRequirement extends Requirement {
	private int sessionID;

	/**
	 * Default constructor for PlanningPokerRequirement. Sets the session ID to
	 * -1 (meaning no session), and the name/description to blank.
	 */
	public PlanningPokerRequirement() {
		this(-1);
	}

	/**
	 * Constructor for Planning Poker requirement. Lets you specify the session
	 * ID while defaulting to a blank name and description.
	 * 
	 * @param sessionID
	 *            The planning poker session ID
	 */
	public PlanningPokerRequirement(int sessionID) {
		this(-1, "", "");
	}

	/**
	 * Constructor for planning poker requirement.
	 * 
	 * @param sessionID
	 *            The planning poker session ID
	 * @param name
	 *            The name for this requirement
	 * @param description
	 *            A description of this requirement
	 */
	public PlanningPokerRequirement(int sessionID, String name,
			String description) {
		super(sessionID, name, description);
		setSessionID(sessionID);
	}

	/**
	 * @return This requirement's planning poker session ID.
	 */
	public int getSessionID() {
		return sessionID;
	}

	/**
	 * @param sessionID
	 *            The planning poker session ID for this requirement.
	 */
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * Returns an instance of PlanningPokerRequirement constructed using the
	 * given Requirement encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded Requirement to deserialize
	 * 
	 * @return the Requirement contained in the given JSON
	 */
	public static PlanningPokerRequirement fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, PlanningPokerRequirement.class);
	}

	/**
	 * Returns an array of PlanningPokerRequirements parsed from the given
	 * JSON-encoded string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Requirement
	 * 
	 * @return an array of Requirement deserialized from the given JSON string
	 */
	public static PlanningPokerRequirement[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, PlanningPokerRequirement[].class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJSON() {
		return new Gson().toJson(this, PlanningPokerRequirement.class);
	}

}
