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
 * This class represents the deck, a list of cards with values
 * PlanningPokerDeck provides cards with specified values so that
 * the users can vote on the requirements
 */
public class PlanningPokerDeck extends AbstractModel {
	/** List of card values */
	private ArrayList<Integer> deck;
	
	/** Name of the deck */
	private String deckName;

	/** ID of the deck */
	private int id;
	
	/**
	 * Construct the default deck in which the cards have Fibonacci values
	 * TODO the default deck should be a database entry
	 */
	public PlanningPokerDeck() {
		int[] defaultDeck = {0,1,1,2,3,5,8,13};
		this.deckName = "Default Deck";
		deck = new ArrayList<Integer>();
		for(int i : defaultDeck) {
			this.deck.add(i);
		}
	}
	
	/**
	 * Construct a deck from the given name and arrayList of values
	 * @param deckName The name of the deck
	 * @param deck An array of integer representing the cards' values
	 */
	public PlanningPokerDeck(String deckName, ArrayList<Integer> deck){
		this.deckName = deckName;
		this.deck = deck;
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
	public String toJSON() {
		return new Gson().toJson(this, PlanningPokerDeck.class);
	}
	
	@Override
	public void save() {}

	@Override
	public void delete() {}
	
	@Override
	public Boolean identify(Object o) {return null;}

	/**
	 * Return the name of the deck
	 * @return Return the name of the deck
	 */
	public String getDeckName() {
		return deckName;
	}
	
	/**
	 * Return the ID of the deck
	 * @return Return the ID of the deck
	 */
	public int getId() {
		return id;
	}

	/**
	 * Assign an integer to the ID of the deck
	 * @param id An integer that would be assigned to the deck's ID
	 */
	public void setId(int id) {
		this.id = id;
	}

}
