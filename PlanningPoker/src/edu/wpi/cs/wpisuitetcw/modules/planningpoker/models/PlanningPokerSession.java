package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * PlanningPokerSession class represents a planning poker session
 * 
 * @author Josh Hebert
 * 
 */
public class PlanningPokerSession extends AbstractModel {

	//The id of the session
	private int id = -1;
	
	//Whether or not the session has been canceled prematurely
	private boolean isCancelled = false;
	
	//When the session was created
	private Date startTime = null;
	
	//When the session should end (has ended, depending on time perspective)
	private Date endTime = null;
	
	//The name of the session;
	private String name = "";

	/**
	 * Constructs a PlanningPokerSession for the given string message
	 * 
	 * @param message
	 */
	public PlanningPokerSession() {

	}
	
	/**
	 * Return true if the session is closed
	 * 
	 * @return Return true if the session if closed
	 */
	public boolean isClosed() {
		// TODO implement
		return true;
	}
	
	/**
	 * Return true if the session is open
	 * 
	 * @return Return true if the session is open
	 */
	public boolean isOpen() {
		// TODO implement
		return true;
	}

	/**
	 * Cancels a session by setting isCancelled to true
	 * and its finish time to the current time
	 */
	public void cancel() {
		this.isCancelled = true;
		this.endTime = new Date();
	}

	
	/**
	 * Sets the end date of this session
	 * @param d The date that the session should end
	 */
	public void setEndTime(Date d){
		this.endTime = d;
	}
	
	
	/**
	 * Returns true if this session has been prematurely terminated
	 * @return Returns true if this session has been prematurely terminated
	 */
	public boolean isCancelled() {
		return this.isCancelled;
	}

	/**
	 * Activate the session if it meets the following conditions:
	 * - It isn't active currently
	 * - It isn't canceled 
	 * - It must have at least one requirement (Temporarily not included)
	 */
	public void activate() {
		if (!this.isCancelled && !this.isActive()) {
			this.startTime = new Date();
		}
	}

	/**
	 * Returns true it is open to voting in the meantime
	 * @return Returns true it is open to voting in the meantime
	 */
	public boolean isActive() {
		return this.startTime != null;
	}

	/**
	 * Return true if this session has been assigned a completed time, 
	 * indicating that the session has been terminated in some way
	 * 
	 * @return Return true if this session has been assigned a completed time
	 */
	public boolean isDone() {
		return endTime != null;
	}

	/**
	 * Sets the name of this session
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return the name of this session
	 * @return Name of this session
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Set the ID of this session
	 * @param id
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Return the session ID
	 * @return The Session ID
	 */
	public int getID() {
		return this.id;
	}

	
	// Functions for requirements
	
	/**
	 * Sets sessionIds for the PlanningPokerRequirements
	 * @param newReq -> new Requirements to be added
	 */
	public void addRequirements(ArrayList<PlanningPokerRequirement> newReqs){
		for(int i = 0; i < newReqs.size(); i++){
			newReqs.get(i).setSessionID(this.id);
		}
	}
	
	/**
	 * Deletes a requirement by requirement ID
	 * @param requirementID -> ID of requirement to be deleted
	 */
	public void deleteRequirements(ArrayList<PlanningPokerRequirement> reqs){
		for(int i = 0; i < reqs.size(); i++){
			reqs.get(i).setSessionID(-1);
		}
	}
	
	

	// Functions for requirements

	// public void createRequirements

	/*
	 * Adds a requirement to the list of requirements
	 * 
	 * @param newReq -> new Requirements to be added
	 */
	/*
	 * public void addRequirements(ArrayList<PlanningPokerRequirement> newReqs){
	 * this.reqsList.append(newReqs); }
	 */

	/*
	 * Deletes a requirement by session ID
	 * 
	 * @param requirementId -> ID of requirement to be deleted
	 */
	/*
	 * public void deleteRequirement(int requirementId){ for(int i = 0; i <
	 * reqsList.size(); i++){ if(reqsList.get(i).getId() == requirementId){
	 * reqsList.remove(i); break; } } }
	 */


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
