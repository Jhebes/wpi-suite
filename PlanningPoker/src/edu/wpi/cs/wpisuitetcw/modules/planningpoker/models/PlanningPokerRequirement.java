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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * PlanningPokerRequirement model has identical data to a Requirement's in the
 * canonical Requirements Manager module with additional traits of belonging to
 * a particular PlanningPokerSession.
 * 
 * This model encapsulates a requirement in the context of planning poker.
 */
public class PlanningPokerRequirement extends AbstractModel {

	/** ID of a planning poker requirement */
	private UUID id;

	/** Corresponding requirement from the Requirement Manager module */
	private Requirement innerRequirement;

	/** ID of the session that has this requirement */
	private int sessionID;

	/** A list of votes from users */
	private List<PlanningPokerVote> votes = new ArrayList<PlanningPokerVote>();

	/** The final estimation of the requirement */
	private int finalEstimate;

	/** Total number of votes for this requirement */
	private int totalVotes;

	/** This is the ID the corresponds with the requirment manager ID */
	private int correspondingReqManagerID;

	/**
	 * Construct a Planning poker requirement with no data
	 */
	public PlanningPokerRequirement() {
		this(new Requirement());
	}

	/**
	 * Construct a Planning poker requirement from the given Requirement
	 * 
	 * @param requirement
	 *            The requirement whose data would be copied
	 */
	public PlanningPokerRequirement(Requirement requirement) {
		this(requirement, -1);
	}

	/**
	 * Construct a Planning poker requirement from the given requirement and
	 * session ID
	 * 
	 * @param requirement
	 *            The requirement whose data would be copied
	 * @param sessionID
	 *            The ID of the session that has this planning poker requirement
	 */
	public PlanningPokerRequirement(Requirement requirement, int sessionID) {
		id = UUID.randomUUID();
		innerRequirement = requirement;
		this.sessionID = sessionID;
		this.finalEstimate = 0;
	}

	/**
	 * Gets the vote cast by a particular user, returning null if the user
	 * hasn't voted yet.
	 * 
	 * @param user The username to check
	 * @return PlanningPokerVote corresponding to the requirement (if it has
	 *         been voted on by user)
	 */
	public PlanningPokerVote getVoteByUser(String user) {
		PlanningPokerVote vote = null;
		for (PlanningPokerVote v : votes) {
			if (v.getUser().equals(user)) {
				vote = v;
				break;
			}
		}
		return vote;
	}

	/**
	 * @return The mean of the values of all the votes.
	 */
	public double getMean() {
		int total = 0;
		int num = 0;
		double mean;
		if(votes.isEmpty()){
			return 0;
		}
		DecimalFormat df = new DecimalFormat("#.#");
		for (PlanningPokerVote v : votes) {
			total += v.getCardValue();
			num++;
		}
		mean = (total / num);
		mean  = Double.parseDouble(df.format(mean));
		
		return mean;
	}

	/**
	 * @return The median of the values of all the votes.
	 */
	public int getMedian() {
		final int size = votes.size();
		if( size == 0){
			return size;
		}
		final int[] numList = new int[votes.size()];
		for (int i = 0; i < votes.size(); i++) {
			numList[i] = votes.get(i).getCardValue();
		}
		Arrays.sort(numList);
		int median;
		if (size % 2 == 1) {
			median = numList[(size / 2)];
		} else {
			median = (numList[size / 2] + numList[(size / 2) - 1]) / 2;
		}
		
		return median;
	}

	/**
	 * @return The mode of the values of all the votes.
	 */
	public int getMode() {
		final int[] numList = new int[votes.size()];
		int max, temp, mode;
		if(votes.size() == 0){
			return 0;
		}
		for (int i = 0; i < votes.size(); i++) {
			numList[i] = votes.get(i).getCardValue();
		}
		Arrays.sort(numList);
		max = 1;
		temp = 1;// temporary count of max
		mode = numList[0];
		for (int j = 0; j < numList.length; j++) {
			if (numList[j] == mode) {
				temp++;
			}
			if (temp > max) {
				max = temp;
				mode = numList[j];
			}
		}
		return mode;
	}
	/**
	 * To calculate the standard deviation, find the sqrt of the variance of the data.
	 * @param mean
	 * @return Standard deviation of votes
	 */
	public double calculateStandardDeviation(double mean){
		double variance, stndDev;
		DecimalFormat df = new DecimalFormat("#.#");
		
		if(mean == 0){
			return mean;
		}
		
		variance  = calculateVariance(mean);
		stndDev = Math.sqrt(variance);
		
		stndDev = Double.parseDouble(df.format(stndDev));
		
		return stndDev;
		
	}
	
	/**
	 * Calculates the variance of vote requirements. To calculate variance, find the average of each sample of data 
	 * subtracted by the mean.
	 * @param mean
	 * @return the variance of all requirement votes for this session
	 */
	public double calculateVariance(double mean){
		double variance;
		int numOfVotes;
		variance = numOfVotes = 0;
		
		for (PlanningPokerVote v : votes) {
			variance += Math.pow((v.getCardValue() - mean), 2);
			numOfVotes++;
		}
		variance = (variance / (numOfVotes));
		return variance;
		
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
	 * Return a JSON String of the PlanningPokerRequirement
	 * 
	 * @Return Return a JSON String of the PlanningPokerRequirement
	 */
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
	public static PlanningPokerRequirement importRequirement(Requirement requirement) {
		final PlanningPokerRequirement ppReq = new PlanningPokerRequirement();
		ppReq.innerRequirement = requirement;
		return ppReq;
	}

	/**
	 * Return the String form of this PlanningPokerRequirement TODO Reimplement
	 * this method
	 */
	public String toString() {
		return innerRequirement.getName();
	}

	/**
	 * Return this requirement's planning poker session ID.
	 * 
	 * @return Return this requirement's planning poker session ID.
	 */
	public int getSessionID() {
		return sessionID;
	}

	/**
	 * Assign the given integer to the PlanningPokerRequirement's ID
	 * 
	 * @param sessionID
	 *            An integer that would be assigned to this requirement's
	 *            session ID
	 */
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * Return the Requirement stored inside this PlanningPokerRequirement
	 * 
	 * @return Return the Requirement stored inside this
	 *         PlanningPokerRequirement
	 */
	public Requirement getInnerRequirement() {
		return innerRequirement;
	}

	/**
	 * Assign the given Requirement to the PlanningPokerRequirement's inner
	 * requirement
	 * 
	 * @param innerRequirement
	 *            The Requirement that would be assigned to the
	 *            PlanningPokerRequirement's inner requirement
	 */
	public void setInnerRequirement(Requirement innerRequirement) {
		this.innerRequirement = innerRequirement;
	}

	/**
	 * Return the UUID ID of the PlanningPokerRequirement
	 * 
	 * @return Return the UUID ID of the PlanningPokerRequirement
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * Assign the given UUID to the PlanningPokerRequirement's
	 * 
	 * @param id
	 *            The UUID that would be assigned to the
	 *            PlanningPokerRequirement's
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * Return the name of the PlanningPokerRequirement
	 * 
	 * @return Return the name of the Requirement stored inside the
	 *         PlanningPokerRequirement
	 */
	public String getName() {
		return innerRequirement.getName();
	}

	/**
	 * Assign a String to the PlanningPokerRequirement's name
	 * 
	 * @param name
	 *            A String that would be assigned to the
	 *            PlanningPokerRequirement's name
	 */
	public void setName(String name) {
		innerRequirement.setName(name);
	}

	/**
	 * Return the Description of the PlanningPokerRequirement
	 * 
	 * @return Return the Description of the PlanningPokerRequirement
	 */
	public String getDescription() {
		return innerRequirement.getDescription();
	}

	/**
	 * Assign the given String to the PlanningPokerRequirement's description
	 * 
	 * @param description
	 *            The String that would be assigned to the
	 *            PlanningPokerRequirement's description
	 */
	public void setDescription(String description) {
		innerRequirement.setDescription(description);
	}

	/**
	 * Return the PlanningPokerRequirement's array list of PlanningPokerVotes
	 * 
	 * @return Return the PlanningPokerRequirement's array list of
	 *         PlanningPokerVotes
	 */
	public List<PlanningPokerVote> getVotes() {
		return votes;
	}

	/**
	 * Copy the data from the given PlanningPokerRequirement to the calling
	 * PlanningPokerRequirement object
	 * 
	 * @param updatedRequirement
	 *            A PlanningPokerRequirement whose data would be copied to the
	 *            calling PlanningPokerRequirement object
	 */
	public void copyFrom(PlanningPokerRequirement updatedRequirement) {
		id = updatedRequirement.id;
		innerRequirement = updatedRequirement.innerRequirement;
		sessionID = updatedRequirement.sessionID;
		votes = updatedRequirement.votes;
		finalEstimate = updatedRequirement.finalEstimate;
		totalVotes = updatedRequirement.totalVotes;
	}

	/**
	 * Return the total votes
	 * 
	 * @return Return the total votes
	 */
	public int getTotalVotes() {
		return totalVotes;
	}

	/**
	 * Assign the given integer to the total votes
	 * 
	 * @param totalVotes
	 *            An integer that would be assigned to the total votes
	 */
	public void setTotalVotes(int totalVotes) {
		this.totalVotes = totalVotes;
	}

	/**
	 * Return this requirement's final estimation
	 * 
	 * @return Return this requirement's final estimation
	 */
	public int getFinalEstimate() {
		return finalEstimate;
	}

	/**
	 * Assign the given integer to the final estimate
	 * 
	 * @param estimate
	 *            An integer that would be assigned to the
	 *            PlanningPokerRequirement's final estimate
	 */
	public void setFinalEstimate(int estimate) {
		finalEstimate = estimate;
	}

	/**
	 * Add a vote to the list of votes
	 * 
	 * @param vote
	 *            A PlanningPokerVote that would be added to the list of votes
	 */
	public void addVote(PlanningPokerVote vote) {
		votes.add(vote);
	}

	/**
	 * Remove the given PlanningPokerVote from the list of votes
	 * 
	 * @param vote
	 *            The PlanningPokerVote that would be removed from the list of
	 *            votes
	 */
	public void deleteVote(PlanningPokerVote vote) {
		votes.remove(vote);
	}

	/**
	 * Determines whether or not a given user has voted on this requirement
	 * 
	 * @param user
	 *            The user in question
	 * @return Yes or no
	 */
	public boolean hasUserVoted(String user) {
		boolean hasVoted = false;
		for (PlanningPokerVote v : votes) {
			if (v.getUser().equals(user)) {
				hasVoted = true;
				break;
			}
		}
		return hasVoted;
	}

	/**
	 * This class does not provide implementation for this method {@inheritDoc}
	 */
	@Override
	public void save() {
	}

	/**
	 * This class does not provide implementation for this method {@inheritDoc}
	 */
	@Override
	public void delete() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean identify(Object o) {
		return id.equals(((PlanningPokerRequirement) o).id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Arrays.hashCode(new Object[] { new String(id.toString()),
				new Integer(sessionID) });
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object arg) { // $codepro.audit.disable
		if (this == arg) {
			return true;
		}
		if (!(arg instanceof PlanningPokerRequirement)) {
			return false;
		}
		return id.equals(((PlanningPokerRequirement) arg).id);
	}

	/**
	 * Set the ID the is given to this requirement in the requirement manager.
	 * @param ID
	 */
	public void setCorrespondingReqManagerID(int ID){
		correspondingReqManagerID = ID;
	}
	
	/**
	 * 
	 * @return The ID of the PPreq that corresponds with the requirement manager requirement.
	 */
	public int getCorrespondingReqManagerID(){
		return correspondingReqManagerID;
	}
}
