package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * Contains a Planning Poker Session
 * 
 * @author Josh Hebert
 *
 */
public class PlanningPokerSession extends AbstractModel {

	private int id;
	private boolean isCanceled = false;
	private Date startTime;
	private Date endTime;
	private String name;
/////////////////////////////////////////////////////////////////
	public void cancel(){
		
	}
	public void activate(){
		
	}
	public boolean isActive(){
		return false;
	}
	public boolean isDone(){
		return false;
	}
	
	/**
	 * Constructs a PlanningPokerSession for the given string message
	 * @param message
	 */
	public PlanningPokerSession(int id, String name) {
		this.id = id;
		this.name = name;
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
	 * @param  Serialized JSON String that encodes to a Session Model
	 * @return the PlanningPokerSession contained in the given JSON
	 */
	public static PlanningPokerSession fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, PlanningPokerSession.class);
	}
	
	/**
	 * Returns an array of PlanningPokerSession parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json a string containing a JSON-encoded array of PlanningPokerSession
	 * @return an array of PlanningPokerSession deserialized from the given json string
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
	 * The methods below are required by the model interface, however they
	 * do not need to be implemented for a basic model like PlanningPokerSession. 
	 */

	@Override
	public void save() {}

	@Override
	public void delete() {}

	@Override
	public Boolean identify(Object o) {return null;}

}
