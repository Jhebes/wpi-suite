/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers;

import java.util.List;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

/**
 * This is the entity manager for the PlanningPokerSession in the PlanningPoker
 * module.
 * 
 */
public class PlanningPokerSessionEntityManager implements
		EntityManager<PlanningPokerSession> {

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
	public PlanningPokerSessionEntityManager(Data db) {
		this.db = db;
	}

	/*
	 * Saves a PlanningPokerSession when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.
	 * wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public PlanningPokerSession makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {

		// Parse the message from JSON
		final PlanningPokerSession newPlanningPokerSession = PlanningPokerSession
				.fromJson(content);

		int newID;
		PlanningPokerSession[] allSessions = this.getAll(s);
		if (allSessions.length == 0) {
			newID = 1;
		} else {
			PlanningPokerSession mostRecent = allSessions[allSessions.length - 1];
			newID = mostRecent.getID() + 1;
		}
		newPlanningPokerSession.setID(newID);

		// Save the message in the database if possible, otherwise throw an
		// exception
		// We want the message to be associated with the project the user logged
		// in to
		if (!db.save(newPlanningPokerSession, s.getProject())) {
			throw new WPISuiteException();
		}

		// Return the newly created message (this gets passed back to the
		// client)
		return newPlanningPokerSession;
	}

	/*
	 * @see
	 * edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng
	 * .Session, java.lang.String)
	 */
	@Override
	public PlanningPokerSession[] getEntity(Session s, String id)
			throws NotFoundException, WPISuiteException {
		// Throw an exception if an ID was specified, as this module does not
		// support
		// retrieving specific PlanningPokerSessions.
		// List<Model> results = db.retrieveAll(new PlanningPokerSession(),
		// s.getProject());
		List<Model> results = db.retrieve(
				new PlanningPokerSession().getClass(), "ID",
				Integer.parseInt(id), s.getProject());
		return results.toArray(new PlanningPokerSession[0]);
	}

	/*
	 * Returns all of the messages that have been stored.
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng
	 * .Session)
	 */
	@Override
	public PlanningPokerSession[] getAll(Session s) throws WPISuiteException {
		// Ask the database to retrieve all objects of the type
		// PlanningPokerSession.
		// Passing a dummy PlanningPokerSession lets the db know what type of
		// object to retrieve
		// Passing the project makes it only get messages from that project
		List<Model> messages = db.retrieveAll(new PlanningPokerSession(),
				s.getProject());

		// Return the list of messages as an array
		return messages.toArray(new PlanningPokerSession[0]);
	}

	/*
	 * Message cannot be updated. This method always throws an exception.
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng
	 * .Session, java.lang.String)
	 */
	@Override
	public PlanningPokerSession update(Session s, String content)
			throws WPISuiteException {

		PlanningPokerSession updatedSession = PlanningPokerSession.fromJson(content);
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save PlanningPokerSessions.
		 * We have to get the original defect from db4o, copy properties from updatedPlanningPokerSession,
		 * then save the original PlanningPokerSession again.
		 */
		List<Model> oldPlanningPokerSessions = db.retrieve(PlanningPokerSession.class, "id", updatedSession.getID(), s.getProject());
		if(oldPlanningPokerSessions.size() < 1 || oldPlanningPokerSessions.get(0) == null) {
			throw new BadRequestException("PlanningPokerSession with ID does not exist.");
		}
				
		PlanningPokerSession existingSession = (PlanningPokerSession)oldPlanningPokerSessions.get(0);		

		// copy values to old PlanningPokerSession and fill in our changeset appropriately
		existingSession.copyFrom(updatedSession);
		
		if(!db.save(existingSession, s.getProject())) {
			throw new WPISuiteException("Could not save when updating existing session.");
		}
		return existingSession;
	}

	/*
	 * @see
	 * edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng
	 * .Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, PlanningPokerSession model)
			throws WPISuiteException {

		// Save the given session in the database
		db.save(model);
	}

	/*
	 * Messages cannot be deleted
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.
	 * wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {

		// This module does not allow PlanningPokerSessions to be deleted, so
		// throw an exception
		throw new WPISuiteException();
	}

	/*
	 * Messages cannot be deleted
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(edu.wpi.cs.wpisuitetng
	 * .Session)
	 */
	@Override
	public void deleteAll(Session s) throws WPISuiteException {

		// This module does not allow PlanningPokerSessions to be deleted, so
		// throw an exception
		throw new WPISuiteException();
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	@Override
	public int Count() throws WPISuiteException {
		// Return the number of PlanningPokerSessions currently in the database
		return db.retrieveAll(new PlanningPokerSession()).size();
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

}
