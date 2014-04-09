package edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers;

/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Andrew Hurle
 *    Tyler Wack
 ******************************************************************************/

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.HashSet;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.MockData;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.UserSessionMap;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class PlanningPokerRequirementEntityManagerTest {

	User bob;
	Project testProject;
	Session defaultSession;

	Data db;
	PlanningPokerRequirementEntityManager manager;

	UUID uuid;
	PlanningPokerRequirement req;

	String mockSsid;

	@Before
	public void setUp() throws Exception {
		mockSsid = "abc123";
		bob = new User("bob", "bob", "1234", 1);
		testProject = new Project("test", "1");
		defaultSession = new Session(bob, testProject, mockSsid);

		uuid = UUID.randomUUID();
		req = new PlanningPokerRequirement();
		req.setId(uuid);

		db = new MockData(new HashSet<Object>());
		db.save(bob, testProject);
		db.save(req, testProject);
		manager = new PlanningPokerRequirementEntityManager(db);
	}

	@Test
	public void testMakeEntity() throws WPISuiteException {
		PlanningPokerRequirement created = manager.makeEntity(defaultSession,
				req.toJSON());
		assertEquals(uuid, created.getId());
		assertSame(testProject, created.getProject());
	}

	@Test
	public void testGetEntity() throws WPISuiteException {
		PlanningPokerRequirement[] sessions = manager
				.getEntity(defaultSession, uuid.toString());
		assertEquals(uuid, sessions[0].getId());
		assertSame(testProject, sessions[0].getProject());
	}
}
