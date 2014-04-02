/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * @author Nick Kalamvokis and Matt Suarez
 * 
 */
public class PlanningPokerVote extends AbstractModel {

	private int id;
	private PlanningPokerSession session;
	private PlanningPokerRequirement requirement;
	private User user;
	private int cardValue;

	public PlanningPokerVote(){
		System.out.println("Using default constructor for vote (BAD)");
	}
	
	public PlanningPokerVote(PlanningPokerSession pps,
			PlanningPokerRequirement ppr, User u, int val) {
		this.session = pps;
		this.requirement = ppr;
		this.user = u;
		this.cardValue = val;
	}

	public PlanningPokerVote(int id, PlanningPokerSession pps,
			PlanningPokerRequirement ppr, User u, int val) {
		this.id = id;
		this.session = pps;
		this.requirement = ppr;
		this.user = u;
		this.cardValue = val;
	}

	/* database interaction */
	@Override
	public void save() {
	}

	@Override
	public void delete() {
	}

	/* serializing */// NEED HELP

	/**
	 * toJSON : serializing this Model's contents into a JSON/GSON string
	 * 
	 * @return A string containing the serialized JSON representation of this
	 *         Model.
	 */
	@Override
	public String toJSON() {
		return new Gson().toJson(this, PlanningPokerVote.class);
	}


	/**
	 * toString : enforce an override. May simply call serializeToJSON.
	 * 
	 * @return The string representation of this Model
	 */
	@Override
	public String toString() {
		return ("ID: " + this.id + "   Session ID: " + this.session.getID()
				+ "   Requirement ID: " + this.requirement.getId()
				+ "   User ID: " + this.user.getIdNum() + "   Card Info: " + this.cardValue);
	}

	
	/**
	 * Convert from JSON back to a Planning Poker Session
	 * 
	 * @param Serialized
	 *            JSON String that encodes to a Session Model
	 * @return the PlanningPokerSession contained in the given JSON
	 */
	public static PlanningPokerVote fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, PlanningPokerVote.class);
	}

	/**
	 * Returns an array of PlanningPokerSession parsed from the given
	 * JSON-encoded string.
	 * 
	 * @param json
	 *            a string containing a JSON-encoded array of
	 *            PlanningPokerSession
	 * @return an array of PlanningPokerSession deserialized from the given json
	 *         string
	 */
	public static PlanningPokerVote[] fromJSONArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, PlanningPokerVote[].class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean identify(Object o) {
		return ((Integer) o).equals(this.id);
	}


	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getCardValue() {
		return cardValue;
	}

	public void setCardValue(int cardValue) {
		this.cardValue = cardValue;
	}

}
