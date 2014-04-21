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
import java.util.Arrays;
import java.util.UUID;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * This model encapsulates a requirement in the context of planning poker. A
 * requirement in planning poker is identical to a requirement in the canonical
 * RequirementsManager module, but has the additional trait of belonging to a
 * particular planning poker session.
 */
public class PlanningPokerRequirement extends AbstractModel {

	private UUID id;
	private Requirement innerRequirement;
	private int sessionID;
	public ArrayList<PlanningPokerVote> votes = new ArrayList<PlanningPokerVote>();
	private int finalEstimate;
	private int totalVotes;

	public int getTotalVotes() {
		return totalVotes;
	}

	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}

	public int getFinalEstimate() {
		return finalEstimate;
	}

	public PlanningPokerRequirement() {
		this(new Requirement());
	}

	public PlanningPokerRequirement(Requirement requirement) {
		this(requirement, -1);
	}

	public PlanningPokerRequirement(Requirement requirement, int sessionID) {
		id = UUID.randomUUID();
		innerRequirement = requirement;
		this.sessionID = sessionID;
	}

	public void addVote(PlanningPokerVote vote) {
		votes.add(vote);
	}
	
	public void deleteVote(PlanningPokerVote vote) {
		votes.remove(vote);
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

	public String toJSON() {
		return new Gson().toJson(this, PlanningPokerRequirement.class);
	}

	/**
	 * Casts a requirement manager requirement into a Planning Poker
	 * Requirement.
	 * 
	 * @param requirement
	 *            The requirement to import
	 * @return The converted planning poker requirement
	 */
	public static PlanningPokerRequirement importRequirement(
			Requirement requirement) {
		PlanningPokerRequirement ppReq = new PlanningPokerRequirement();
		ppReq.innerRequirement = requirement;
		return ppReq;
	}

	public String toString() {
		return this.innerRequirement.getName();
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

	public Requirement getInnerRequirement() {
		return innerRequirement;
	}

	public void setInnerRequirement(Requirement innerRequirement) {
		this.innerRequirement = innerRequirement;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return this.getInnerRequirement().getName();
	}

	public void setName(String name) {
		this.getInnerRequirement().setName(name);
	}

	public String getDescription() {
		return this.getInnerRequirement().getDescription();
	}

	public void setDescription(String description) {
		this.getInnerRequirement().setDescription(description);
	}
	
	public ArrayList<PlanningPokerVote> getVotes() {
		return votes;
	}
	

	@Override
	public void save() {

	}

	@Override
	public void delete() {

	}

	@Override
	public Boolean identify(Object o) {
		return this.id.equals(((PlanningPokerRequirement) o).id);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(new Object[] { new String(id.toString()),
				new Integer(sessionID) });
	}

	@Override
	public boolean equals(Object o) {
		return this.id.equals(((PlanningPokerRequirement) o).id);
	}

	public void copyFrom(PlanningPokerRequirement updatedRequirement) {
		this.innerRequirement = updatedRequirement.innerRequirement;
		this.sessionID = updatedRequirement.sessionID;
		this.votes = updatedRequirement.votes;
	}

	public void setFinalEstimate(int estimate) {
		this.finalEstimate = estimate;
		
	}
	/**
	 * Determines whether or not a given user has voted on this requirement
	 * @param user The user in question
	 * @return Yes or no
	 */
	public boolean hasUserVoted(String user){
		for(PlanningPokerVote v : this.votes){
			if(v.getUser().equals(user)){
				return true;
			}
		}
		return false;
	}
}
