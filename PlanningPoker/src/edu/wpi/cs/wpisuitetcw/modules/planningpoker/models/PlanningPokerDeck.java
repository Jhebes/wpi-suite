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
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * This class represents the deck, a list of cards with values PlanningPokerDeck
 * provides cards with specified values so that the users can vote on the
 * requirements
 */
public class PlanningPokerDeck extends AbstractModel {
	/** List of card values */
	private List<Integer> deck;

	/** Name of the deck */
	private String deckName;

	/** ID of the deck */
	private int id;

	/** Number of cards that can be chosen */
	private int maxSelection;
	
	/**
	 * This constructor creates the default deck with the fibonacci values TODO
	 * the default deck should be a database entry
	 */
	public PlanningPokerDeck() {
		final int[] defaultDeck = { 0, 1, 1, 2, 3, 5, 8, 13 };
		this.deckName = "Default Deck";
		deck = new ArrayList<Integer>();
		for (int i : defaultDeck) {
			this.deck.add(i);
		}
		// default deck should be single selection
		this.maxSelection = 1;
	}

	/**
	 * This constructor creates the deck from the imported list of values
	 *
	 * @param name_in
	 *            The name of the deck
	 * @param deck_in
	 *            the inputed deck
	 */
	public PlanningPokerDeck(String name_in, List<Integer> deck_in) {
		this.deckName = name_in;
		this.deck = deck_in;
		this.maxSelection = deck.size();
	}
	
	/**
	 * Construct a Planning Poker Deck with the given name, a set of integers,
	 * and a number of cards that can be selected
	 * @param deckName A name of the deck that would be created
	 * @param cardValues A set of values that the new deck would have
	 * @param maxSelections A number of cards that can be chosen
	 */
	public PlanningPokerDeck(String deckName, List<Integer> cardValues, int maxSelection) {
		this.deckName = deckName;
		this.deck = cardValues;
		this.maxSelection = maxSelection;
	}

	/**
	 * Return the max number of cards that can be chosen
	 * @return Return the max number of cards that can be chosen
	 */
	public int getMaxSelection() {
		return maxSelection;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJSON() {
		return new Gson().toJson(this, PlanningPokerDeck.class);
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
	 * This class does not provide implementation for this method {@inheritDoc}
	 */
	@Override
	public Boolean identify(Object o) {
		return null;
	}

	/**
	 * Return the name of the deck
	 * 
	 * @return Return the name of the deck
	 */
	public String getDeckName() {
		return deckName;
	}

	/**
	 * Return the ID of the deck
	 * 
	 * @return Return the ID of the deck
	 */
	public int getId() {
		return id;
	}

	/**
	 * Assign an integer to the ID of the deck
	 * 
	 * @param id
	 *            An integer that would be assigned to the deck's ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return the deck list
	 */
	public List<Integer> getDeck() {
		return deck;
	}

}
