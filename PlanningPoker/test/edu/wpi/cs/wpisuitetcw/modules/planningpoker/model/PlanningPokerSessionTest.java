/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
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
	public void testActivateNotCancelledSession() {
		assertFalse(planningPokerSession.isActive());
		planningPokerSession.activate();

		assertTrue(planningPokerSession.isActive());
	}

	@Test
	public void testActivateCancelledSession() {
		planningPokerSession.cancel();
		assertFalse(planningPokerSession.isActive());
		planningPokerSession.activate();
		assertFalse(planningPokerSession.isActive());
	}

	@Test
	public void testCancel() {
		planningPokerSession.cancel();
		assertTrue(planningPokerSession.isCancelled());
		assertNotNull(planningPokerSession.getEndTime());
		// End time is not null anymore. Cannot test this
	}

	@Test
	public void testGetStatusNew() {
		assertEquals("New", planningPokerSession.getStatus());
	}
		
	@Test
	public void testGetStatusCancelled() {
		planningPokerSession.cancel();
		assertEquals("Cancelled", planningPokerSession.getStatus());
	}
	
	@Test
	public void testGetStatusOpen() {
		planningPokerSession.activate();
		assertEquals("Open", planningPokerSession.getStatus());
	}
	
//	@Test
//	public void testGetStatusClose() {
//		planningPokerSession.close();
//		assertEquals("Closed", planningPokerSession.getStatus());
//	}
}
