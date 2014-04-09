/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * PlanningPokerSession class represents a planning poker session
 */
public class PlanningPokerSession extends AbstractModel {

	/** The id of the session */
	private int id = -1;
	
	/** The user name of the creator of this session*/
	private String ownerUserName = "";

	/** The name of the session */
	private String name = "";

	/** The description for this session */
	private String description = "";

	/** The deadline for this session (optional) */
	private Date deadline = null;

	/** When the session was created */
	private Date startTime = null;

	/** When the session should end (has ended, depending on time perspective) */
	private Date endTime = null;

	/** List of requirements associated this session */
	private ArrayList<PlanningPokerRequirement> requirements;

	/** List of users in the session */
	private ArrayList<User> users;
	
	/** The deck to be used for this session */
	private PlanningPokerDeck deck;

	/** Whether or not the session has been canceled prematurely */
	private boolean isCancelled = false;

	/** Whether or not the voting on the requirements is complete */
	private boolean votingComplete = false;

	/**
	 * Constructs a PlanningPokerSession.
	 */
	public PlanningPokerSession() {
		requirements = new ArrayList<PlanningPokerRequirement>();
	}

	/**
	 * Return true if the session is closed TODO: make isClosed the
	 * authoritative command and deprecate isDone.
	 * 
	 * @return Return true if the session if closed
	 */
	public boolean isClosed() {
		return isDone();
	}

	/**
	 * Return true if the session is open
	 * 
	 * @return Return true if the session is open
	 */
	public boolean isOpen() {
		return isActive();
	}

	/**
	 * Activate the session if it meets the following conditions: - It isn't
	 * active currently - It isn't canceled - It must have at least one
	 * requirement (Temporarily not included)
	 */
	public void activate() {
		if (!this.isCancelled && !this.isActive()) {
			this.startTime = new Date();
		}
	}

	/**
	 * Cancels a session by setting isCancelled to true and its finish time to
	 * the current time
	 */
	public void cancel() {
		this.isCancelled = true;
		this.endTime = new Date();
	}

	/**
	 * Adds a single requirement to this session.
	 * 
	 * @param req
	 *            The new session to add
	 */
	public void addRequirement(PlanningPokerRequirement req) {
		requirements.add(req);
	}

	public void addVoteToRequirement(PlanningPokerRequirement req, PlanningPokerVote v){
		requirements.get(requirements.indexOf(req)).addVote(v);
	}
	
	public PlanningPokerRequirement getReqByName(String n){
		for(PlanningPokerRequirement r : requirements){
			if(r.getName().equals(n)){
				return r;
			}
		}
		throw new NullPointerException();
	}
	/**
	 * Adds a single user to this session.
	 * 
	 * @param new_user
	 *            The new user to add
	 */
	public void addUser(User new_user) {
		users.add(new_user);
	}

	/**
	 * Appends new requirements to the planning poker session.
	 * 
	 * @param newReqs
	 *            New Requirements to be added
	 */
	public void addRequirements(ArrayList<PlanningPokerRequirement> newReqs) {
		requirements.addAll(newReqs);
	}

	/**
	 * Appends new users to the planning poker session.
	 * 
	 * @param newUsers
	 *            New users to be added
	 */
	public void addUsers(ArrayList<User> newUsers) {
		users.addAll(newUsers);
	}

	/**
	 * Deletes a set of requirement from this session.
	 * 
	 * @param reqs
	 *            The requirements to delete
	 */
	public void deleteRequirements(ArrayList<PlanningPokerRequirement> reqs) {
		requirements.removeAll(reqs);
	}

	/**
	 * Deletes a set of users from this session.
	 * 
	 * @param newUsers
	 *            The users to delete
	 */
	public void deleteUsers(ArrayList<User> newUsers) {
		requirements.removeAll(newUsers);
	}

	/**
	 * This function compares the total number of votes to the number of votes
	 * needed to end the voting.
	 * 
	 * *sets the votingComplete flag
	 * 
	 * Should be called after every vote is added to a requirement
	 */
	public void voteStatus() {
		int totalVotes = requirements.size() * users.size();
		int votes = 0;

		for (int i = 0; i < requirements.size(); i++) {
			votes += requirements.get(i).votes.size();
		}

		if (votes == totalVotes) {
			setVotingComplete(true);
		}

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
	public static PlanningPokerSession[] fromJSONArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, PlanningPokerSession[].class);
	}

	/**
	 * Sets the end date of this session
	 * 
	 * @param d
	 *            The date that the session should end
	 */
	public void setEndTime(Date d) {
		this.endTime = d;
	}

	/**
	 * Returns true if this session has been prematurely terminated
	 * 
	 * @return Returns true if this session has been prematurely terminated
	 */
	public boolean isCancelled() {
		return this.isCancelled;
	}

	/**
	 * Returns true it is open to voting in the meantime
	 * 
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
	 * @param name
	 *            The new session name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return the name of this session
	 * 
	 * @return Name of this session
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Return the users in this session
	 * 
	 * @return users in this session
	 */
	public ArrayList<User> getUsers() {
		return this.users;
	}
	
/**
	 * 
	 * @param userName
	 * 
	 */
	public void setOwnerUserName(String userName) {
		this.ownerUserName = userName;
	}

	/**
	 * @return the user name of the Owner of this session
	 */
	
	public String getOwnerUserName() {
		return this.ownerUserName;
	}

	/**
	 * @param The
	 *            id to set
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * @return The Session ID
	 */
	public int getID() {
		return this.id;
	}
	
	/**
	 * Returns the deck
	 * @return deck
	 * 			the deck for this session
	 */
	public PlanningPokerDeck getDeck() {
		return deck;
	}

	/**
	 * Sets the deck!
	 * @param deck
	 * 			the inputed deck
	 */
	public void setDeck(PlanningPokerDeck deck) {
		this.deck = deck;
	}

	/**
	 * @return The deadline for this session.
	 */
	public Date getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline
	 *            The new deadline for this session.
	 */
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return The description for this session.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            A new description for this session.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return The list of requirements associated with his session.
	 */
	public ArrayList<PlanningPokerRequirement> getRequirements() {
		return requirements;
	}

	/**
	 * @param requirements
	 *            The new list of requirements.
	 */
	public void setRequirements(ArrayList<PlanningPokerRequirement> requirements) {
		this.requirements = requirements;
	}

	/**
	 * 
	 * @return voting complete boolean
	 */

	public boolean isVotingComplete() {
		return this.votingComplete;
	}

	/**
	 * 
	 * @param votingComplete
	 *            If all the users in the session have voted
	 */

	public void setVotingComplete(boolean votingComplete) {
		this.votingComplete = votingComplete;
	}

	/**
	 * @return The current status of this session.
	 */
	public String getStatus() {
		// TODO: Add more statuses as more methods get implemented
		// i.e., add isActivated, etc.
		if (isCancelled) {
			return "Cancelled";
		} else if (isOpen()) {
			return "Open";
		} else if (isClosed()) {
			return "Closed";
		} else {
			return "New";
		}
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
	
	public void update(){
		final Request request = Network.getInstance().makeRequest("planningpoker/session", HttpMethod.POST);
		request.setBody(this.toJSON());
		request.send();
	}

	public void copyFrom(PlanningPokerSession updatedSession) {
		this.isCancelled = updatedSession.isCancelled;
		this.startTime = updatedSession.startTime;
		this.endTime = updatedSession.endTime;
		this.deadline = updatedSession.deadline;
		this.name = updatedSession.name;
		this.description = updatedSession.description;
		this.requirements = updatedSession.requirements;
	}

}
