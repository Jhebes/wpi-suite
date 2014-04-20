package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.MockRequest;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.PlanningPokerMockTest;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class SendEmailControllerTest extends PlanningPokerMockTest {

	final String testRecipient = "test@test.com";
	@SuppressWarnings("deprecation")
	final Date testDeadline = new Date(2014, 04, 01, 12, 34);
	final String testExpectedURL = "Advanced/planningpoker/session/sendEmail/start/test%40test.com/3914-05-01+12%3A34";

	@Test
	public void testMakeURL() {
		String url = SendEmailController.makeURL("start", "test@test.com",
				testDeadline, "sendEmail");
		assertEquals(testExpectedURL, url);
	}

	@Test
	public void testSendEmail() {
		SendEmailController.sendNotification("start", testRecipient,
				testDeadline, "sendEmail");

		// See whether the request was sent
		MockRequest request = ((MockNetwork) Network.getInstance())
				.getLastRequestMade();
		if (request == null) {
			fail("Request not sent.");
		}
		assertTrue(request.isSent());

		// Validate the request
		assertEquals("/" + testExpectedURL, request.getUrl().getPath());
		assertEquals(HttpMethod.GET, request.getHttpMethod());
	}
}
