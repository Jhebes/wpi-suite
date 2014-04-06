package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models;

import java.util.ArrayList;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * This class represents the deck for voting
 * 
 * @author Manny DeMaio, Louie MIstretta
 * 
 */

public class PlanningPokerDeck extends AbstractModel {
	private ArrayList<int[]> deck;

	
	/**
	 * This constructor creates the default deck with the fibonacci values
	 */
	public PlanningPokerDeck() {
		int[] defaultDeck = {0,1,1,2,3,5,8,13};
		deck = new ArrayList<int[]>();
		deck.add(defaultDeck);
	}
	
	/**
	 * This constructor creates the deck from the imported arrayList of values
	 * @param deck_in
	 * 			the inputed deck
	 */
	public PlanningPokerDeck(ArrayList<int[]> deck_in){
		deck.addAll(deck_in);
	}

	
	
	
	@Override
	public void save() {
		

	}

	@Override
	public void delete() {
		

	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
