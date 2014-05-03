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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.MockData;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class PlanningPokerSessionEntityManagerTest {

	User bob;
	Project testProject;
	Session defaultSession;
	Session defaultSession2;


	Data db;
	PlanningPokerSessionEntityManager manager;

	PlanningPokerSession session;
	PlanningPokerSession session2;

	String mockSsid;

	@Before
	public void setUp() throws Exception {
		mockSsid = "abc123";
		bob = new User("bob", "bob", "1234", 1);
		testProject = new Project("test", "1");
		defaultSession = new Session(bob, testProject, mockSsid);
		defaultSession2 = new Session(bob, testProject, mockSsid);

		session = new PlanningPokerSession();
		session.setID(1);

		session2 = new PlanningPokerSession();
		session2.setID(2);

	
		db = new MockData(new HashSet<Object>());
		db.save(session, testProject);
		db.save(bob, testProject);

		manager = new PlanningPokerSessionEntityManager(db);

	}

	@Test
	public void testMakeEntity() throws WPISuiteException {
		final PlanningPokerSession created = manager.makeEntity(defaultSession,
				session.toJSON());
		assertEquals(2, created.getID());
		assertSame(testProject, created.getProject());
	}


	@Test
	public void testGetEntity() throws WPISuiteException {
		final PlanningPokerSession[] sessions = manager
				.getEntity(defaultSession, "1");
		assertEquals(1, sessions[0].getID());
		assertSame(testProject, sessions[0].getProject());
	}
}
