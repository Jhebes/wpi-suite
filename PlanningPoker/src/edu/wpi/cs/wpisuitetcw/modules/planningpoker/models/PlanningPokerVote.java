/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models;

import edu.wpi.cs.wpisuitetng.Permission;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * @author Nick Kalamvokis and Matt Suarez
 *
 */
public class PlanningPokerVote extends AbstractModel {

	private int id;
	private PlanningPokerSession session;
	private PlanningPokerRequirement requirement;
	
	/* We need a type User
	 *private User user;
	 */
	
	/* We need a type Card
	 * private Card card;
	 */
	
	/**
	 * 
	 */
	public PlanningPokerVote() {
	}
	
	public PlanningPokerVote(int id, PlanningPokerSession pps, PlanningPokerRequirement ppr, User u, Card c) {
		this.id = id;
		this.session = pps;
		this.requirement = ppr;
		this.user = u;
		this.card = c;
	}
	
	/* database interaction */ // NEED HELP
	public void save();
	public void delete();
	
	/* serializing */  // NEED HELP
	
	/** toJSON : serializing this Model's contents into a JSON/GSON string
	 * @return	A string containing the serialized JSON representation of this Model.
	 */
	public String toJSON();
	
	/* deserializing */
	
	
	
	
	/* Built-in overrides/overloads */
	
	/** toString : enforce an override. May simply call serializeToJSON.
	 * @return	The string representation of this Model
	 */
	public String toString() {
		return ("ID: " + this.id + "   Session ID: " + this.session.getID() + "   Requirement ID: " 
				+ this.requirement.getId() + "   User ID: " + this.user.getId() + "   Card Info: " 
				+ this.card.toString());
	}
	
	/**
	 * identify: true if the argument o is equal this object's unique identifier or this object
	 * this method was created for use with the mock database
	 * 
	 * implementations overriding this method should check if o is either a unique identifier, or an instance of this class
	 * if o is an instance of this class, this method should check if it contains the same unique identifier
	 * 
	 * @param o - a unique identifier belonging to an object
	 * @return true if the o is equal to this Model's unique identifier, else false
	 */
	public Boolean identify(Object o) {
		return this.id == (Integer) o;
	}
}
