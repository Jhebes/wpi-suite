/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

/**
 * Planning poker tests utilize a mock session, user and project.
 */
public class PlanningPokerMockTest {
	String mockSsid;
	User testUser;
	Project testProject;
	Session defaultSession;
	Data db;

	/**
	 * Sets up the test environment.
	 */
	@Before
	public void setUpEnvironment() {
		mockSsid = "abc123";
		testUser = new User("testUser", "testUser", "1234", 1);
		testProject = new Project("test", "1");
		defaultSession = new Session(testUser, testProject, mockSsid);

		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(new NetworkConfiguration("http://wpisuitetng"));

		db = new MockData(new HashSet<Object>());
		db.save(testUser);
	}

	/**
	 * This test exists solely to make it pass in 'ant test'. For some reason,
	 * when 'ant test' is run, it assumes that this base class is a test suite,
	 * and attempts to run some tests. Inevitably, it fails because there are
	 * none. This fixes that. It also has the amusing effect of increasing the
	 * number of tests we have because it is run in every subclass.
	 */
	@Test
	public void testEnvironmentSetup() {
		assertEquals("abc123", mockSsid);
		assertFalse(null == Network.getInstance());
	}
}
