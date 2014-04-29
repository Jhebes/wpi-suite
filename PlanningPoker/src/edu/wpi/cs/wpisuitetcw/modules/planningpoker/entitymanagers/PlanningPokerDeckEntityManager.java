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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

public class PlanningPokerDeckEntityManager implements
EntityManager<PlanningPokerDeck>{

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
	public PlanningPokerDeckEntityManager(Data db) {
		this.db = db;
	}

	/**
	 * Saves a PlanningPokerDeck when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.
	 *      wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public PlanningPokerDeck makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {

		// Parse the message from JSON
		final PlanningPokerDeck newPlanningPokerDeck = PlanningPokerDeck
				.fromJson(content);

		int newID;
		final PlanningPokerDeck[] allSessions = this.getAll(s);
		if (allSessions.length == 0) {
			newID = 1;
		} else {
			final PlanningPokerDeck mostRecent = allSessions[allSessions.length - 1];
			newID = mostRecent.getId() + 1;
		}
		newPlanningPokerDeck.setId(newID);

		// Save the message in the database if possible, otherwise throw an
		// exception. We want the message to be associated with the project the
		// user logged in to
		if (!db.save(newPlanningPokerDeck, s.getProject())) {
			throw new WPISuiteException();
		}

		// Return the newly created message (this gets passed back to the
		// client)
		return newPlanningPokerDeck;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng
	 *      .Session, java.lang.String)
	 */
	@Override
	public PlanningPokerDeck[] getEntity(Session s, String id)
			throws NotFoundException, WPISuiteException {
		final List<Model> results = db.retrieve(
				new PlanningPokerDeck().getClass(), "ID",
				Integer.parseInt(id), s.getProject());
		// If the default session does not exist, create it
		if (id.equals("0") && results.size() == 0) {
			final PlanningPokerSession defaultSession = new PlanningPokerSession();
			defaultSession.setName("No session");
			if (makeEntity(s, defaultSession.toJSON()) != null) {
				results.add(defaultSession);
			}
		} 
		return results.toArray(new PlanningPokerDeck[0]);
	}

	/**
	 * Returns all of the messages that have been stored.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng
	 *      .Session)
	 */
	@Override
	public PlanningPokerDeck[] getAll(Session s)
			throws WPISuiteException {
		// Ask the database to retrieve all objects of the type
		// PlanningPokerDeck. Passing a dummy PlanningPokerDeck
		// lets the db know what type of object to retrieve. Passing the project
		// makes it only get messages from that project.
		final List<Model> messages = db.retrieveAll(new PlanningPokerDeck(),
				s.getProject());

		// Return the list of messages as an array
		return messages.toArray(new PlanningPokerDeck[0]);
	}

	/**
	 * Message cannot be updated. This method always throws an exception.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng
	 *      .Session, java.lang.String)
	 */
	@Override
	public PlanningPokerDeck update(Session s, String content)
			throws WPISuiteException {
		throw new WPISuiteException();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng
	 *      .Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, PlanningPokerDeck model)
			throws WPISuiteException {
		db.save(model);
	}

	/**
	 * Requirements cannot be deleted
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.
	 *      wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		throw new WPISuiteException();
	}

	/**
	 * Requirements cannot be deleted
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(edu.wpi.cs.wpisuitetng
	 *      .Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		throw new WPISuiteException();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int Count() throws WPISuiteException {
		return db.retrieveAll(new PlanningPokerDeck()).size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		return null;
	}

}
