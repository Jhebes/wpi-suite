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
import java.util.UUID;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

/**
 * This is the entity manager for the PlanningPokerRequirement in the
 * PlanningPoker module.
 * 
 * @author Ian Naval
 * @author Penny Over
 * 
 */
public class PlanningPokerRequirementEntityManager implements
		EntityManager<PlanningPokerRequirement> {

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
	public PlanningPokerRequirementEntityManager(Data db) {
		this.db = db;
	}

	/**
	 * Saves a PlanningPokerRequirement when it is received from a client
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.
	 *      wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public PlanningPokerRequirement makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {

		System.out.println("Making new requirement");
		// Parse the message from JSON
		final PlanningPokerRequirement newPlanningPokerRequirement = PlanningPokerRequirement
				.fromJson(content);
		
		// Save the message in the database if possible, otherwise throw an
		// exception. We want the message to be associated with the project the
		// user logged in to
		if (!db.save(newPlanningPokerRequirement, s.getProject())) {
			throw new WPISuiteException();
		}

		// Return the newly created message (this gets passed back to the
		// client)
		return newPlanningPokerRequirement;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng
	 *      .Session, java.lang.String)
	 */
	@Override
	public PlanningPokerRequirement[] getEntity(Session s, String id)
			throws NotFoundException, WPISuiteException {
		List<Model> results = db.retrieve(
				new PlanningPokerRequirement().getClass(), "Id",
				UUID.fromString(id), s.getProject());
		return results.toArray(new PlanningPokerRequirement[0]);
	}

	/**
	 * Returns all of the messages that have been stored.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng
	 *      .Session)
	 */
	@Override
	public PlanningPokerRequirement[] getAll(Session s)
			throws WPISuiteException {
		// Ask the database to retrieve all objects of the type
		// PlanningPokerRequirement. Passing a dummy PlanningPokerRequirement
		// lets the db know what type of object to retrieve. Passing the project
		// makes it only get messages from that project.
		List<Model> messages = db.retrieveAll(new PlanningPokerRequirement(),
				s.getProject());

		// Return the list of messages as an array
		return messages.toArray(new PlanningPokerRequirement[0]);
	}

	/**
	 * Message cannot be updated. This method always throws an exception.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng
	 *      .Session, java.lang.String)
	 */
	@Override
	public PlanningPokerRequirement update(Session s, String content)
			throws WPISuiteException {
		throw new WPISuiteException();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng
	 *      .Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, PlanningPokerRequirement model)
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
		return db.retrieveAll(new PlanningPokerRequirement()).size();
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
