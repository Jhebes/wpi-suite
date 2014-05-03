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

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers.LongPollingResponseEntityManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.typeadapters.ClassTypeAdapter;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * A {@link LongPollingResponseEntityManager} returns a long poll response
 * object in order to push any new updates to the clients.
 */
public class LongPollResponse extends AbstractModel {

	/** The type of the object stored */
	private Class type;

	/** The encapsulated object converted to JSON */
	private String data;

	/**
	 * Constructs a long poll response object, which contains an object of a any
	 * type.
	 * 
	 * @param type
	 *            The type of the object pushed by the server
	 * @param data
	 *            The object pushed by the server
	 */
	public LongPollResponse(Class type, AbstractModel data) {
		this.type = type;
		this.data = data.toJSON();
	}

	/**
	 * toJSON : serializing this Model's contents into a JSON/GSON string
	 * 
	 * @return A string containing the serialized JSON representation of this
	 *         Model.
	 */
	@Override
	public String toJSON() {
		final Gson parser = new GsonBuilder().registerTypeAdapter(
				Class.class, new ClassTypeAdapter()).create();
		return parser.toJson(this, LongPollResponse.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean identify(Object o) {
		return false;
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
	 * Convert from JSON back to a Long Polling Request
	 * 
	 * @param Serialized
	 *            JSON String that encodes to a Request Model
	 * @return the Request contained in the given JSON
	 */
	public static LongPollResponse fromJson(String json) {
		final Gson parser = new GsonBuilder().registerTypeAdapter(
				Class.class, new ClassTypeAdapter()).create();
		return parser.fromJson(json, LongPollResponse.class);
	}

	/**
	 * Returns an array of Requests parsed from the given JSON-encoded string.
	 * 
	 * @param json
	 *            a string containing a JSON-encoded array of Requests
	 * @return an array of Request deserialized from the given json string
	 */
	public static LongPollResponse[] fromJSONArray(String json) {
		final Gson parser = new GsonBuilder().registerTypeAdapter(
				Class.class, new ClassTypeAdapter()).create();
		return parser.fromJson(json, LongPollResponse[].class);
	}

	/**
	 * @return The type of the encapsulated data object
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return The encapsulated data object
	 */
	public String getData() {
		return data;
	}
}
