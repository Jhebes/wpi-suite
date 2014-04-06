package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.MockData;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.MockRequest;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.RetrievePlanningPokerRequirementsForSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class RetrieveFreePlanningPokerRequirementsControllerTest {

	String mockSsid;
	User bob;
	Project testProject;
	Session defaultSession;

	Data db;

	PlanningPokerSession session;
	RetrievePlanningPokerRequirementsForSessionController controller;
	CreateSessionPanel panel;

	@Before
	public void setUp() throws Exception {
		mockSsid = "abc123";
		bob = new User("bob", "bob", "1234", 1);
		testProject = new Project("test", "1");
		defaultSession = new Session(bob, testProject, mockSsid);

		session = new PlanningPokerSession();
		session.setID(1);

		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));

		db = new MockData(new HashSet<Object>());
		db.save(session, testProject);
		db.save(bob);
		panel = new CreateSessionPanel();
		controller = RetrievePlanningPokerRequirementsForSessionController.getInstance();
	}

	@Test
	/**
	 * Tests that the controller sends a valid request.
	 */
	public void testRefreshDataSendsValidRequest() throws WPISuiteException {
		controller.refreshData(1); 

		// See whether the request was sent
		MockRequest request = ((MockNetwork) Network.getInstance())
				.getLastRequestMade();
		if (request == null) {
			fail("request not sent");
		}
		assertTrue(request.isSent());

		// Validate the request
		assertEquals("/planningpoker/requirement", request.getUrl().getPath());
		assertEquals(HttpMethod.GET, request.getHttpMethod());
	}
}
