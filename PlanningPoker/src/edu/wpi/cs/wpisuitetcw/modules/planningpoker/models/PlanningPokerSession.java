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
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.SendNotificationController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.put.PutSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.UserStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
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

	/** The user name of the creator of this session */
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
	private List<PlanningPokerRequirement> requirements;

	/** List of users in the session */
	private List<User> users;

	/** The deck to be used for this session */
	private PlanningPokerDeck deck;

	/** Whether or not the session has been canceled prematurely */
	private boolean isCancelled = false;

	/** Whether or not the voting on the requirements is complete */
	private boolean votingComplete = false;

	/** Whether or not the voting on the requirements has begun */
	private boolean hasVoted;

	/** Whether or not the session is being edited or created */
	private boolean isEditMode = false;
	
	/**
	 * Constructs a PlanningPokerSession.
	 */
	public PlanningPokerSession() {

		
		requirements = new ArrayList<PlanningPokerRequirement>();
	}

	/**
	 * Return true if the session has just been created but not yet open or
	 * closed.
	 * 
	 * @return true if the session has just been created but not yet open or
	 *         closed.
	 */
	public boolean isNew() {
		return !isClosed() && !isOpen() && !isCancelled;
	}

	/**
	 * Return true if this session has been assigned a completed time,
	 * indicating that the session has been terminated in some way
	 * 
	 * @return Return true if this session has been assigned a completed time
	 */
	public boolean isClosed() {
		return endTime != null;
	}

	/**
	 * Return true if the session is open
	 * 
	 * @return Return true if the session is open
	 */
	public boolean isOpen() {
		return isActive() && !isClosed() && !isCancelled;
	}

	/**
	 * A session can be activated if it meets the following conditions: 
	 * <ul>
	 * 	<li>It isn't active currently</li> 
	 * 	<li>It isn't canceled</li>
	 *  <li>It must have at least one requirement</li>
	 * </ul>
	 * @return Whether the session can be activated
	 */
	public boolean canBeActivated() {
		return !isCancelled && !isActive() && requirements.size() > 0;
	}

	/**
	 * Activate the sessions a session if it meets all the criteria 
	 * specified in {@link #canBeActivated() canBeActivated} method.
	 */
	public void activate() {
		if (canBeActivated()) {
			startTime = new Date();
			
			List<User> users = UserStash.getInstance().getUsers();
			for (User user: users) {
				// Send email to everyone in a session
				String emailAddress = user.getEmail();
				if (emailAddress != null && !emailAddress.equals("")) {
					SendNotificationController.sendNotification("start",
							emailAddress, this.getDeadline(), "sendEmail");
				}
				
				// Send SMS to everyone in a session
				String phoneNumber = user.getSMS();
				if (phoneNumber != null && !phoneNumber.equals("")) {
					SendNotificationController.sendNotification("start",
							phoneNumber, this.getDeadline(), "sendSMS");
				}
			}
		}
	}

	/**
	 * Deactivates a session by basically undoing what activate would. If the
	 * session is already active, and not cancelled, then it would set the start
	 * time to null
	 */
	public void deactivate() {	
		if (!isCancelled && this.isActive()) {
			startTime = null;
		}
	}

	/**
	 * Closes a session without canceling it.
	 */
	public void close() {
		endTime = new Date();
		
		List<User> users = UserStash.getInstance().getUsers();
		for (User user: users) {
			// Send email to everyone in a session
			String emailAddress = user.getEmail();
			if (emailAddress != null && !emailAddress.equals("")) {
				SendNotificationController.sendNotification("end",
						emailAddress, this.getDeadline(), "sendEmail");
			}
			
			// Send SMS to everyone in a session
			String phoneNumber = user.getSMS();
			if (phoneNumber != null && !phoneNumber.equals("")) {
				SendNotificationController.sendNotification("end",
						phoneNumber, this.getDeadline(), "sendSMS");
			}
		}
	}

	/**
	 * Cancels a session by setting isCancelled to true and its finish time to
	 * the current time
	 */
	public void cancel() {
		isCancelled = true;
	}

	/**
	 * Adds a single requirement to this session.
	 * 
	 * @param req
	 *            The new session to add
	 */
	public void addRequirement(PlanningPokerRequirement req) {
		requirements.add(req);
		req.setSessionID(id);
	}

	/**
	 * Add a PlanningPokerVote for a PlanningPokerRequirement from an user to
	 * the PlanningPokerSession
	 * 
	 * @param req
	 *            A PlanningPokerRequirement that is voted
	 * @param v
	 *            A PlanningPokerVote of the PlanningPokerRequirement
	 * @param requestingUser
	 *            A String represents the user
	 */
	public void addVoteToRequirement(PlanningPokerRequirement req,
			PlanningPokerVote v, String requestingUser) {
		// Remove the corresponding requirement from this session
		final PlanningPokerRequirement r = requirements.get(requirements
				.indexOf(req));
		requirements.remove(r);

		// Add the vote of the user to the requirement
		for (PlanningPokerVote vote : r.getVotes()) {
			if (vote.getUser().equals(v.getUser())) {
				vote.setCardValue(v.getCardValue());
				requirements.add(r); // Add the requirement back
				this.save();
				return;
			}
		}

		r.addVote(v);
		requirements.add(r);
		this.isVotingComplete();
		this.save();
	}

	/**
	 * Return the PlanningPokerRequirement that has the given name
	 * @param reqName String of the requirement that would be returned
	 * @return Return the PlanningPokerRequirement that has the given name
	 */
	public PlanningPokerRequirement getReqByName(String reqName) {
		for (PlanningPokerRequirement requirement : requirements) {
			System.out.printf("%s = %s?\n", reqName, requirement.getName());
			if (requirement.getName().equals(reqName)) {
				return requirement;
			}
		}
		System.out.println(reqName);
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
	public void addRequirements(List<PlanningPokerRequirement> newReqs) {
		requirements.addAll(newReqs);
	}

	/**
	 * Appends new users to the planning poker session.
	 * 
	 * @param newUsers
	 *            New users to be added
	 */
	public void addUsers(List<User> newUsers) {
		users.addAll(newUsers);
	}

	/**
	 * Deletes a set of requirement from this session.
	 * 
	 * @param reqs
	 *            The requirements to delete
	 */
	public void deleteRequirements(List<PlanningPokerRequirement> reqs) {
		requirements.removeAll(reqs);
	}

	/**
	 * Deletes a set of users from this session.
	 * 
	 * @param newUsers
	 *            The users to delete
	 */
	public void deleteUsers(List<User> newUsers) {
		requirements.removeAll(newUsers);
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
	 * @param json
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
		endTime = d;
	}

	/**
	 * Returns true if this session has been prematurely terminated
	 * 
	 * @return Returns true if this session has been prematurely terminated
	 */
	public boolean isCancelled() {
		return isCancelled;
	}

	/**
	 * Returns true it is open to voting in the meantime
	 * 
	 * @return Returns true it is open to voting in the meantime
	 */
	public boolean isActive() {
		return startTime != null;
	}

	/**
	 * Assign a String to the session's name
	 * 
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
		return name;
	}

	/**
	 * Return the users in this session
	 * 
	 * @return users in this session
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * Assign an user name to the name of the session's owner
	 * 
	 * @param userName
	 *            A string that would be assigned to the session's username
	 */
	public void setOwnerUserName(String userName) {
		ownerUserName = userName;
	}

	/**
	 * @return the user name of the Owner of this session
	 */

	public String getOwnerUserName() {
		return ownerUserName;
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
		return id;
	}

	/**
	 * Calculates and the number of vote associated with a requirement
	 * @param req The requirement whose votes to count
	 * @return The number of votes for the given requirement
	 */
	public int getNumVotes(PlanningPokerRequirement req) {
		return req.getVotes().size();
	}

	/**
	 * Returns the deck
	 * 
	 * @return deck the deck for this session
	 */
	public PlanningPokerDeck getDeck() {
		return deck;
	}

	/**
	 * Sets the deck!
	 * 
	 * @param deck
	 *            the inputed deck
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
	public List<PlanningPokerRequirement> getRequirements() {
		return requirements;
	}

	/**
	 * @param requirements
	 *            The new list of requirements.
	 */
	public void setRequirements(List<PlanningPokerRequirement> requirements) {
		this.requirements = requirements;
	}

	/**
	 * 
	 * @return voting complete boolean
	 */

	public boolean isVotingComplete() {
		return votingComplete;
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
	 * 
	 * @return has voted boolean it is true if one user has voted
	 */

	public boolean isHasVoted() {
		boolean hasVotes = false;
		
		for (PlanningPokerRequirement ppr : requirements) {
			if (!ppr.getVotes().isEmpty()) {
				hasVotes = true;
			}
		}
		
		return hasVotes;
	}

	/**
	 * 
	 * @param hasVoted
	 *            sets the has Voted boolean
	 */

	public void setHasVoted(boolean hasVoted) {
		this.hasVoted = hasVoted;
	}

	/**
	 * @return The current status of this session.
	 */
	public String getStatus() {
		// TODO: Add more statuses as more methods get implemented
		// i.e., add isActivated, etc.
		if (isCancelled) {
			return "Cancelled";
		} else if (isClosed()) {
			return "Closed";
		} else if (isOpen()) {
			return "Open";
		} else {
			return "New";
		}
	}

	/**
	 * @return The end time
	 */
	public Date getEndTime() {
		return endTime;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * This class does not provide implementation for this method
	 * {@inheritDoc}
	 */
	@Override
	public void delete() {
	}

	/**
	 * This class does not provide implementation for this method
	 * {@inheritDoc}
	 */
	@Override
	public Boolean identify(Object o) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void save() {
		SessionStash.getInstance().update(this);
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/session", HttpMethod.POST);
		request.setBody(this.toJSON());
		request.send();

		// refresh the tree
		ViewEventManager.getInstance().getOverviewTreePanel().refresh();
	}

	/**
	 * Synchronizes the new session with the server
	 */
	public void create() {
		new PutSessionController(this);
	}

	/**
	 * Copy the data from the given PlanningPokerSession to
	 * the calling PlanningPokerSession object
	 * @param updatedSession A PlanningPokerSession whose
	 * data would be copied to the calling PlanningPokerSession object
	 */
	public void copyFrom(PlanningPokerSession updatedSession) {
		ownerUserName = updatedSession.ownerUserName;
		name = updatedSession.name;
		description = updatedSession.description;
		deadline = updatedSession.deadline;
		startTime = updatedSession.startTime;
		endTime = updatedSession.endTime;
		requirements = updatedSession.requirements;
		users = updatedSession.users;
		deck = updatedSession.deck;
		isCancelled = updatedSession.isCancelled;
		votingComplete = updatedSession.votingComplete;
		hasVoted = updatedSession.hasVoted;
		isEditMode = updatedSession.isEditMode;
	}

	/**
	 * Return the start time of the session
	 * @return Return the start time of the session
	 */
	public Object getStartTime() {
		return startTime;
	}
	
	/**
    *
    * @return whether or not the session has ended
    */
    public boolean hasPassedDeadline(){
        return deadline.after(new Date());
    }

	public boolean isEditMode() {
		return isEditMode;
	}

	public void setEditMode(boolean isEditMode) {
		this.isEditMode = isEditMode;
	}
}
