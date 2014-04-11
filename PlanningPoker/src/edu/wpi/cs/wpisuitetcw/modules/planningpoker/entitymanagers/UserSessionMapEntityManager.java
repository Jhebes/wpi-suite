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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.UserSessionMap;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

/**
 * This is the entity manager for the PlanningPokerSession in the PostBoard
 * module.
 * 
 * @author Emanuel DeMaio
 * @author Louie Mistretta
 * 
 */
public class UserSessionMapEntityManager implements
		EntityManager<UserSessionMap> {

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
	public UserSessionMapEntityManager(Data db) {
		this.db = db;
	}

	@Override
	public UserSessionMap makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		// Parse the message from JSON
		final UserSessionMap newUserSessionMap = UserSessionMap
				.fromJson(content);

		// Save the message in the database if possible, otherwise throw an
		// exception. We want the message to be associated with the project the
		// user logged in to
		if (!db.save(newUserSessionMap, s.getProject())) {
			throw new WPISuiteException();
		}

		// Return the newly created message (this gets passed back to the
		// client)
		return newUserSessionMap;
	}

	@Override
	public UserSessionMap[] getEntity(Session s, String id)
			throws NotFoundException, WPISuiteException {
		List<Model> results = db.retrieve(new UserSessionMap().getClass(),
				"Session", Integer.parseInt(id), s.getProject());
		return results.toArray(new UserSessionMap[0]);
	}

	@Override
	public UserSessionMap[] getAll(Session s) throws WPISuiteException {

		List<Model> messages = db.retrieveAll(new UserSessionMap(),
				s.getProject());

		// Return the list of messages as an array
		return messages.toArray(new UserSessionMap[0]);
	}

	@Override
	public UserSessionMap update(Session s, String content)
			throws WPISuiteException {

		throw new WPISuiteException();
	}

	@Override
	public void save(Session s, UserSessionMap model) throws WPISuiteException {
		db.save(model);

	}

	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {

		throw new WPISuiteException();
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {

		throw new WPISuiteException();
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		throw new WPISuiteException();

	}

	@Override
	public int Count() throws WPISuiteException {

		return db.retrieveAll(new UserSessionMap()).size();
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {

		throw new WPISuiteException();
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {

		throw new WPISuiteException();
	}
}