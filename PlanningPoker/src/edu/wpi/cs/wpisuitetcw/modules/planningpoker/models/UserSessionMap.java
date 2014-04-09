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

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * This class is used to map sessions and users together this alls all the
 * sessions a user is in to be known and allows all the users in a single
 * session to be known
 * 
 * @author Emanuel DeMaio, Louie Mistretta
 * 
 */
public class UserSessionMap extends AbstractModel {
	User user;
	PlanningPokerSession session;

	public int userID;
	public int sessionID;

	/**
	 * Constructs a user session map
	 * 
	 * @param user
	 *            The user for this mapping
	 * @param session
	 *            The session for this mapping
	 */
	public UserSessionMap(User user, PlanningPokerSession session) {
		this.session = session;
		this.user = user;
		this.userID = user.getIdNum();
		this.sessionID = session.getID();
	}

	public UserSessionMap() {

	}

	/**
	 * Convert from JSON back to a User Map Session
	 * 
	 * @param Serialized
	 *            JSON String that encodes to a Session Model
	 * @return the UserSessionMAp contained in the given JSON
	 */

	public static UserSessionMap fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, UserSessionMap.class);
	}

	/**
	 * Returns an array of UserMapSessions parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            a string containing a JSON-encoded array of UserSessionMaps
	 * @return an array of UserSessionMaps deserialized from the given json
	 *         string
	 */

	public static UserSessionMap[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, UserSessionMap[].class);
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public String toJSON() {
		return new Gson().toJson(this, UserSessionMap.class);
	}

	/*
	 * Getters and Setters
	 */

	/**
	 * Gets the user from the USerSessionMap
	 * 
	 * @return user
	 */

	public User getUser() {
		return user;
	}

	/**
	 * Sets the user
	 * 
	 * @param user
	 */

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 
	 * @return the session id number
	 */

	public int getSession() {
		return session.getID();
	}

	/**
	 * sets the session
	 * 
	 * @param session
	 */
	public void setSession(PlanningPokerSession session) {
		this.session = session;
	}

	/*
	 * The methods below are required by the model interface, however they do
	 * not need to be implemented.
	 */

	@Override
	public Boolean identify(Object o) {
		return null;
	}

	@Override
	public void delete() {

	}

	@Override
	public void save() {

	}

}
