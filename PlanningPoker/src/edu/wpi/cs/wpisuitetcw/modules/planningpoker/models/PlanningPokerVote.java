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

import java.util.UUID;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class PlanningPokerVote extends AbstractModel {

	/** ID of the Vote */
	private UUID id;
	
	/** Name of user who voted */
	private String user;
	
	/** Vote value */
	private int cardValue;	
	
	/**
	 * Construct a Planning Poker Vote from the given user name
	 * and vote value
	 * @param user A user who made this PlanningPokerVote 
	 * @param cardValue Vote value 
	 */
	public PlanningPokerVote(String user, int cardValue) {
		this.user = user;
		this.cardValue = cardValue;
		this.id = UUID.randomUUID();
	}

	/* database interaction */

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
	 * {@inheritDoc}
	 */
	@Override
	public Boolean identify(Object o) {
		return ((Integer) o).equals(this.id);
	}
	
	/**
	 * toString : enforce an override. May simply call serializeToJSON.
	 * 
	 * @return The string representation of this Model
	 */
	@Override
	public String toString() {
		return "ID: " + this.user + ", Value: " + this.cardValue;
	}
	
	/**
	 * This class does not provide implementation for this method
	 * {@inheritDoc}
	 */
	@Override
	public void save() {}

	/**
	 * This class does not provide implementation for this method
	 * {@inheritDoc}
	 */
	@Override
	public void delete() {}

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
	 * Return the UUID of the vote
	 * @return Return the UUID of the vote
	 */
	public UUID getID() {
		return id;
	}

	/**
	 * Assign given UUID to this vote's 
	 * @param id Assign given UUID to this vote's 
	 */
	public void setID(UUID id) {
		this.id = id;
	}

	/**
	 * Return the card value
	 * @return Return the card value
	 */
	public int getCardValue() {
		return cardValue;
	}

	/**
	 * Assign the given integer to the card value 
	 * of the PlanningPokerVote
	 * @param cardValue An integer that would be assigned 
	 * to the card value of the PlanningPokerVote
	 */
	public void setCardValue(int cardValue) {
		this.cardValue = cardValue;
	}
	
	/**
	 * Return the name of the user who voted
	 * @return Return the name of the user who voted
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Assign the user name to this PlanningPokerVote's
	 * @param user A user who would be assigned to
	 * this PlanningPokerVote's
	 */
	public void setUser(String user) {
		this.user = user;
	}

}
