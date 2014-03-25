package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
<<<<<<< HEAD
//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

=======

//import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
>>>>>>> 3055a5bd18c382213a691101a2d34b74a7c52976

/**
 * Contains a Planning Poker Session
 * 
 * @author Josh Hebert
 * 
 */
public class PlanningPokerSession extends AbstractModel {
<<<<<<< HEAD
	private ArrayList<PlanningPokerRequirement> reqsList;
=======
	private ArrayList<String> reqsList;
>>>>>>> 3055a5bd18c382213a691101a2d34b74a7c52976
	private int id = -1;
	private boolean isCancelled = false;
	private Date startTime = null;
	private Date endTime = null;
	private String name = "";

	// Getters and Setters
	// ///////////////////////////////////////////////////////////////
	public void cancel() {
		this.isCancelled = true;
		this.endTime = new Date();
	}

	public boolean isCancelled() {
		return this.isCancelled;
	}

	/**
	 * If this model is eligible for activation, it will be activated. Criteria
	 * for activation are: -> It cannot already be active -> It cannot have been
	 * canceled -> It must have at least one requirement
	 */
	public void activate() {
		// If this is not active and hasn't been canceled
		if (!this.isCancelled) {
			// And has a valid amount of requirements
			if (reqsList.size() >= 0) {
				// Activate
				this.startTime = new Date();
			}
		}

	}

	/**
	 * Returns the status of this session, i.e. whether or not it is open to
	 * voting
	 * 
	 * @return The status of the session
	 */
	public boolean isActive() {
			return !(this.startTime == null);
	}

	public boolean isDone() {
		if (endTime == null) {
			return false;
		} else
			return true;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getName() {
		return this.name;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return this.id;
	}

	
	public ArrayList<PlanningPokerRequirement> getRequirements(){
		return this.reqsList;
	}
	
	
	
	// Functions for requirements
	
	/* Sets sessionIds for the PlanningPokerRequirements
	 * @param newReq -> new Requirements to be added
	 */
	public void addRequirements(ArrayList<PlanningPokerRequirement> newReqs){
		for(int i = 0; i < newReqs.size(); i++){
			newReqs.get(i).setSessionId(this.id);
		}
	}
	
	
	/* Deletes a requirement by requirement ID
	 * @param requirementId -> ID of requirement to be deleted
	 */
	public void deleteRequirements(ArrayList<PlanningPokerRequirement> reqs){
		for(int i = 0; i < reqs.size(); i++){
			reqs.get(i).setSessionId(-1);
		}
	}
	
	
	// ///////////////////////////////////////////////////////////////
	/**
	 * Constructs a PlanningPokerSession for the given string message
	 * 
	 * @param message
	 */
	public PlanningPokerSession() {

	}

	/**
	 * Converts the model to a JSON String
	 */
	@Override
	public String toJSON() {
		return new Gson().toJson(this, PlanningPokerSession.class);
	}

	/**
	 * Convert from JSON back to a Planning Poker Session
	 * 
	 * @param Serialized
	 *            JSON String that encodes to a Session Model
	 * @return the PlanningPokerSession contained in the given JSON
	 */
	public static PlanningPokerSession fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, PlanningPokerSession.class);
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
	public static PlanningPokerSession[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, PlanningPokerSession[].class);
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Session";
	}

	/*
	 * The methods below are required by the model interface, however they do
	 * not need to be implemented for a basic model like PlanningPokerSession.
	 */

	@Override
	public void save() {
	}

	@Override
	public void delete() {
	}

	@Override
	public Boolean identify(Object o) {
		return null;
	}

}
