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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.longpoll.LongPollingThread;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.LongPollResponse;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;

/**
 * Manages long polling requests from the Janeway client
 */
public class LongPollingResponseEntityManager implements EntityManager<LongPollResponse> {

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
	public LongPollingResponseEntityManager(Data db) {
		this.db = db;
	}

	
	/** Set of all connected clients. */
	private static final Set<LongPollingThread> threadsToUpdate = new HashSet<LongPollingThread>();
	
	/**
	 * Pushes an object to all clients currently connected to the server.
	 * 
	 * @param type
	 *            The type of the object to push
	 * @param object
	 *            The object to push
	 */
	public static void pushToClients(Class type, AbstractModel object) {
		for (LongPollingThread thread : threadsToUpdate) {
			LongPollResponse request = new LongPollResponse(type, object);
			thread.pushData(request);
		}
		threadsToUpdate.clear();
	}

	/**
	 * Attaches to the server and waits for there to be something of the
	 * appropriate type in the queue. When there is, return a LongPollingRequest
	 * with the changes necessary
	 * 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(edu.wpi.cs.wpisuitetng.Session,
	 *      java.lang.String)
	 */
	@Override
	public String advancedGet(Session s, String[] args) throws WPISuiteException {

		final List<LongPollResponse> dataToPush = new ArrayList<LongPollResponse>();
		final LongPollingThread requestBlocker = new LongPollingThread(dataToPush);
		threadsToUpdate.add(requestBlocker);
		try {
			requestBlocker.start();
			requestBlocker.join();
		} catch (InterruptedException e) {
			Logger.getLogger("PlanningPoker").log(Level.SEVERE, "Request blocker thread interrupted.", e);
		}

		if (dataToPush.size() == 0) {
			throw new WPISuiteException("No data was passed!");
		}
		
		return dataToPush.get(0).toJSON();
	}

	// ///Not used///////////

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(edu.wpi.cs.
	 *      wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public LongPollResponse makeEntity(Session s, String content) throws BadRequestException,
			ConflictException, WPISuiteException {

		throw new WPISuiteException();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(edu.wpi.cs.wpisuitetng
	 *      .Session)
	 */
	@Override
	public LongPollResponse[] getAll(Session s) throws WPISuiteException {
		throw new WPISuiteException();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(edu.wpi.cs.wpisuitetng
	 *      .Session, java.lang.String)
	 */
	@Override
	public LongPollResponse update(Session s, String content) throws WPISuiteException {
		throw new WPISuiteException();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#save(edu.wpi.cs.wpisuitetng
	 *      .Session, edu.wpi.cs.wpisuitetng.modules.Model)
	 */
	@Override
	public void save(Session s, LongPollResponse model) throws WPISuiteException {
		throw new WPISuiteException();
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(edu.wpi.cs.
	 *      wpisuitetng.Session, java.lang.String)
	 */
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		throw new WPISuiteException();
	}

	/**
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
		throw new WPISuiteException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LongPollResponse[] getEntity(Session s, String id) throws NotFoundException, WPISuiteException {
		throw new WPISuiteException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String advancedPut(Session s, String[] args, String content) throws WPISuiteException {
		throw new WPISuiteException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String advancedPost(Session s, String string, String content) throws WPISuiteException {
		throw new WPISuiteException();
	}

}
