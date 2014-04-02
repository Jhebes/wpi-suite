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

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.MockData;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class PlanningPokerVoteEntityManagerTest {

	User bob;
	Project testProject;
	PlanningPokerRequirement defaultRequirement;
	Session defaultSession;
	PlanningPokerSession defaultPlanningPokerSession;
	int defaultCard;
	
	Data db;
	PlanningPokerVoteEntityManager manager;
	
	PlanningPokerVote vote;

	
	String mockSsid;
	
	@Before
	public void setUp() throws Exception {
		mockSsid = "abc123";
		bob = new User("bob", "bob", "1234", 1);
		testProject = new Project("test", "1");
		defaultSession = new Session(bob, testProject, mockSsid);
		defaultPlanningPokerSession = new PlanningPokerSession();
		defaultRequirement = new PlanningPokerRequirement();
		
		
		vote = new PlanningPokerVote(defaultPlanningPokerSession, defaultRequirement, bob, defaultCard);
		vote.setID(1);
		
		db = new MockData(new HashSet<Object>());
		db.save(defaultPlanningPokerSession, testProject);
		db.save(bob);
		manager = new PlanningPokerVoteEntityManager(db);
	}

	@Test
	public void testMakeEntity() throws WPISuiteException {
		PlanningPokerVote created = manager.makeEntity(defaultSession, vote.toJSON());
		assertEquals(1, created.getID());
		assertSame(testProject, created.getProject());	
	}
	
	@Test
	public void testGetEntity() throws WPISuiteException {
		PlanningPokerVote created = manager.makeEntity(defaultSession, vote.toJSON());
		PlanningPokerVote[] votes = manager.getEntity(defaultSession, "1");
		assertEquals(1, votes[0].getID());
		assertSame(testProject, votes[0].getProject());
	}
}
