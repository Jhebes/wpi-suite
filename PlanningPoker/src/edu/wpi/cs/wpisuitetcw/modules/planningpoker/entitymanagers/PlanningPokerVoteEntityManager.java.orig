/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers;

import java.util.List;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetng.Session; //Not sure
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

/**
 * @author Nick Kalamvokis and Matt Suarez
 * 
 */

public class PlanningPokerVoteEntityManager implements
		EntityManager<PlanningPokerVote> {

	/** The database */
	Data db;

	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in the
	 * ManagerLayer file.
	 * 
	 * @param db
	 *            a reference to the persistent database
	 */
	public PlanningPokerVoteEntityManager(Data db) {
		this.db = db;
	}

	// Need review, not sure what to do with content and saving to database
	public PlanningPokerVote makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {


		final PlanningPokerVote newVote = PlanningPokerVote.fromJson(content);
		int newID;
		PlanningPokerVote[] allSessions = this.getAll(s);
		if (allSessions.length == 0) {
			newID = 1;
		} else {
			PlanningPokerVote mostRecent = allSessions[allSessions.length - 1];
			newID = mostRecent.getID() + 1;
		}
		newVote.setID(newID);
		// try to save the vote to the database throw WPISuiteException if this
		// doesn't work
		if (!db.save(newVote, s.getProject())) {
			throw new WPISuiteException();
		}
		
		// return the new vote
		return newVote;
	}

	/* Retrieve */
	/**
	 * Retrieves the PlanningPokerVote with the given unique identifier, id.
	 * 
	 * @param id
	 *            the unique identifier value
	 * @return the PlanningPokerVote with the given ID
	 * @throws NotFoundException
	 *             if entity does not exist
	 */
	@Override
	public PlanningPokerVote[] getEntity(Session s, String id)
			throws NotFoundException {
		
		final int intID = Integer.parseInt(id);
		if (intID < 1) {
			throw new NotFoundException();
		}
		
		PlanningPokerVote[] votes = null;
		try {
			votes = db.retrieve(PlanningPokerVote.class, "id", intID,
					s.getProject()).toArray(new PlanningPokerVote[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if (votes.length < 1 || votes[0] == null) {
			throw new NotFoundException();
		}
		return votes;
	}

	/**
	 * Retrieves all entities of Model class PlanningPokerVote
	 * 
	 * @return an ArrayList<PlanningPokerVote> with all instances of
	 *         PlanningPokerVote
	 */
	@Override
	public PlanningPokerVote[] getAll(Session s) throws WPISuiteException {
		List<Model> votes = db.retrieveAll(new PlanningPokerVote(),
				s.getProject());

		// return the list of votes as an array
		return votes.toArray(new PlanningPokerVote[0]);
	}

	/* Update */
	/**
	 * 
	 * @param s
	 *            the session of the User executing this action
	 * @param content
	 *            - JSON representation of the model updates
	 * @return the updated model
	 * @throws WPISuiteException
	 */
	@Override
	public PlanningPokerVote update(Session s, String content)
			throws WPISuiteException {
		throw new WPISuiteException();
	}

	/**
	 * Saves the given model of class T to the database
	 * 
	 * @param model
	 *            the Model to update.
	 */
	@Override
	public void save(Session s, PlanningPokerVote model)
			throws WPISuiteException {
		db.save(model, s.getProject());
	}

	/* Delete */
	/**
	 * Deletes the entity with the given unique identifier, id.
	 * 
	 * @param id
	 *            the unique identifier for the entity
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		return (db.delete(getEntity(s, id)[0]) != null);
	}

	/* Advanced Get */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String advancedGet(Session s, String[] args)
			throws NotImplementedException {
		throw new NotImplementedException();
	}

	/**
	 * Deletes all entities of Model class T
	 * 
	 * @param s
	 *            Current session
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		System.out.println("Deleting all votes...");
		db.deleteAll(new PlanningPokerVote(), s.getProject());
	}

	/* Utility Methods */
	/**
	 * 
	 * @return the number of records of this Manager's Model class T
	 */
	@Override
	public int Count() throws WPISuiteException {
		return db.retrieveAll(new PlanningPokerVote()).size();
	}

	/* Advanced Put */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws NotImplementedException {
		throw new NotImplementedException();
	}

	/* Advanced Post */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String advancedPost(Session s, String string, String content)
			throws NotImplementedException {
		throw new NotImplementedException();
	}

}
