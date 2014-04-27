/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.PlanningPokerMockTest;



public class PlanningPokerSessionTest extends PlanningPokerMockTest {
	private PlanningPokerSession planningPokerSession;
	
	@Before
	public void setUp() {
		planningPokerSession = new PlanningPokerSession();
	}
	
	@Test
	public void testActivateNotCancelledSession() {
		// Required for activation
		planningPokerSession.addRequirement(new PlanningPokerRequirement());
		
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
	public void testGetStatusOpenNoReqs() {
		planningPokerSession.setRequirements(new ArrayList<PlanningPokerRequirement>());
		planningPokerSession.activate();
		assertEquals("New", planningPokerSession.getStatus());
	}
	
	@Test
	public void testGetStatusOpenWithReqs() {
		planningPokerSession.addRequirement(new PlanningPokerRequirement());
		planningPokerSession.activate();
		assertEquals("Open", planningPokerSession.getStatus());
	}
	
	@Test
	public void testGetStatusClosed() {
		assertEquals("New", planningPokerSession.getStatus());
		planningPokerSession.close();
		assertEquals("Closed", planningPokerSession.getStatus());
	}
	
	@Test
	public void testCannotBeActivatedWithoutReqs() {
		planningPokerSession.setRequirements(new ArrayList<PlanningPokerRequirement>());
		assertFalse(planningPokerSession.canBeActivated());
	}
	
	@Test
	public void testCanBeActivatedWithAReq() {
		planningPokerSession.addRequirement(new PlanningPokerRequirement());
		assertTrue(planningPokerSession.canBeActivated());
	}
	
	@Test
	public void testCannotBeActivatedTwice() {
		planningPokerSession.addRequirement(new PlanningPokerRequirement());
		planningPokerSession.activate();
		assertFalse(planningPokerSession.canBeActivated());
	}
	
	@Test
	public void testCannotBeActivatedWhenCancelled() {
		planningPokerSession.cancel();
		assertFalse(planningPokerSession.canBeActivated());
	}

}
