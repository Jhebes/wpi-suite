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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.longpoll.LongPollingThread;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.longpoll.ModelWithType;
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
		
		new Thread() {
			/**
			 * Blocks until all clients have responded to the push.
			 */
			private void checkClientsHaveResent() {
				System.out.println("Checking...");
				Date startCheck = new Date();
				Date currentTime;
				do {
					currentTime = new Date();

					if (currentTime.getTime() - startCheck.getTime() > 1000) {
						Set<Session> sessions = new HashSet<Session>();

						// forget people who took too long
						for (Session session : clientsToWaitFor.keySet()) {
							if (!clientsToWaitFor.get(session)) {
								sessions.add(session);
							}
						}
						clientsToWaitFor.keySet().removeAll(sessions);
						break;
					}
				} while (clientsToWaitFor.containsValue(false));

				Iterator<Session> it = clientsToWaitFor.keySet().iterator();
				
				// Reset client list
				while (it.hasNext()) {
					Session session = it.next();
					clientsToWaitFor.put(session, false);
				}
			}
			
			public void run() {
				while (true) {
					for (ModelWithType queuedThing : queuedData) {
						checkClientsHaveResent(); 
						
						Class<?> type = queuedThing.getType();
						AbstractModel object = queuedThing.getObject();
						Session session = queuedThing.getSession();

						for (Entry<Session, LongPollingThread> entry : threadsToUpdate.entrySet()) {
							LongPollResponse request = new LongPollResponse(type, object);
							if (entry.getKey() != session) {
								entry.getValue().pushData(request);
							}
						}
					}
					queuedData.clear();
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
				}
				
			}
		}.start();
	}

	/** Set of all connected clients. */
	private static final Map<Session, LongPollingThread> threadsToUpdate = new HashMap<Session, LongPollingThread>();
	
	private static final Set<ModelWithType> queuedData = new HashSet<ModelWithType>();
	
	private static final Map<Session, Boolean> clientsToWaitFor = new HashMap<Session, Boolean>();
	
	/**
	 * Pushes an object to all clients currently connected to the server.
	 * 
	 * @param type
	 *            The type of the object to push
	 * @param object
	 *            The object to push
	 */
	public static void pushToClients(Class<?> type, AbstractModel object, Session session) {
		Iterator<Entry<Session, LongPollingThread>> threads = threadsToUpdate.entrySet().iterator();

		queuedData.add(new ModelWithType(type, object, session));
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
		threadsToUpdate.put(s, requestBlocker);
		clientsToWaitFor.put(s, true);
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
