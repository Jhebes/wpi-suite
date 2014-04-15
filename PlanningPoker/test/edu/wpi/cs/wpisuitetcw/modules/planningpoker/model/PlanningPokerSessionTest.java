/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;

public class PlanningPokerSessionTest {
	private PlanningPokerSession planningPokerSession;
	
	@Before
	public void setUp() {
		planningPokerSession = new PlanningPokerSession();
		
	}
	
	@Test
	public void testActivate() {		
		// Activate a session that is not cancelled and not active. [Default case] Expected true
		planningPokerSession.activate();
		// Activate a session that is cancelled and not active. Expected false
		// Activate a session that is not cancelled and active. Expected false
		// Activate a session that is cancelled and active. Expected false
	}
	
	@Test
	public void testCancel() {
		planningPokerSession = new PlanningPokerSession();
		planningPokerSession.cancel();
		assertTrue(planningPokerSession.isCancelled());
		// End time is not null anymore. Cannot test this
	}
	
	@Test
	public void testGetStatus() {
		planningPokerSession = new PlanningPokerSession();
		// Test new case. [Default case] Expected "New"
		assertEquals("New", planningPokerSession.getStatus());
		
		// Test cancel case. Expected "Cancelled"
		planningPokerSession.cancel();
		assertEquals("Cancelled", planningPokerSession.getStatus());
		
		// Test open case. Expected "Open"
		planningPokerSession.activate();
		assertEquals("Open", planningPokerSession.getStatus());
		
		// Test closed case. Expected "Closed"
		assertEquals("Closed", planningPokerSession.getStatus());
	}
}
