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

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * This class represents the deck for voting
 */

public class PlanningPokerDeck extends AbstractModel {
	private ArrayList<Integer> deck;
	private String deckName;
	private int id;
	
	/** Allow single card selection or multiple card selection */
	private boolean isSingleSelection;

	/**
	 * This constructor creates the default deck with the fibonacci values TODO
	 * the default deck should be a database entry
	 */
	public PlanningPokerDeck() {
		int[] defaultDeck = { 0, 1, 1, 2, 3, 5, 8, 13 };
		this.deckName = "Default Deck";
		deck = new ArrayList<Integer>();
		for (int i : defaultDeck) {
			this.deck.add(i);
		}
	}

	/**
	 * This constructor creates the deck from the imported arrayList of values
	 * 
	 * @param deck_in
	 *            the inputed deck
	 */
	public PlanningPokerDeck(String name_in, ArrayList<Integer> deck_in) {
		this.deckName = name_in;
		this.deck = deck_in;
	}

	/**
	 * Returns an array of PlanningPokerSession parsed from the given
	 * JSON-encoded string.
	 * 
	 * @param json
	 *            a string containing a JSON-encoded array of
	 *            PlanningPokerSession
	 * @return an array of PlanningPokerDeck deserialized from the given json
	 *         string
	 */
	public static PlanningPokerDeck[] fromJSONArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, PlanningPokerDeck[].class);
	}

	@Override
	public void save() {

	}

	@Override
	public void delete() {

	}

	@Override
	public String toJSON() {
		return new Gson().toJson(this, PlanningPokerDeck.class);
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
	public static PlanningPokerDeck fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, PlanningPokerDeck.class);
	}

	@Override
	public Boolean identify(Object o) {
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Return the name of the deck
	 * 
	 * @return name of the deck
	 */
	public String getDeckName() {
		return deckName;
	}

}
