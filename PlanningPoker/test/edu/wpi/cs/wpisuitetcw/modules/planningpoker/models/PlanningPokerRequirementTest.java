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

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class PlanningPokerRequirementTest {

	private PlanningPokerRequirement planningPokerRequirement;
	private PlanningPokerRequirement planningPokerRequirementWithVotes;
	private PlanningPokerVote testVote;
	private PlanningPokerVote testVote2;
	
	@Before
	public void setupFixtures() {
		planningPokerRequirement = new PlanningPokerRequirement();
		
		testVote = new PlanningPokerVote("test", 1);
		testVote2 = new PlanningPokerVote("test2", 2);
		planningPokerRequirementWithVotes = new PlanningPokerRequirement();
		planningPokerRequirementWithVotes.addVote(testVote);
		planningPokerRequirementWithVotes.addVote(testVote2);
	}
	
	@Test
	public void testEquals() {
		final UUID uuid = UUID.randomUUID();
		final PlanningPokerRequirement req = new PlanningPokerRequirement();
		req.setId(uuid);
		
		final PlanningPokerRequirement req2 = new PlanningPokerRequirement();
		req2.setId(uuid);
		
		assertTrue(req.equals(req2));
	}

	@Test
	public void testAddVote() {
		planningPokerRequirement.addVote(testVote);
		assertEquals("test", planningPokerRequirement.getVotes().get(0).getUser());
		assertEquals(1, planningPokerRequirement.getVotes().get(0).getCardValue());
	}

	@Test
	public void testDeleteVote() {
		assertEquals(2, planningPokerRequirementWithVotes.getVotes().size());
		planningPokerRequirementWithVotes.deleteVote(testVote);
		assertEquals(1, planningPokerRequirementWithVotes.getVotes().size());
		assertFalse(planningPokerRequirementWithVotes.hasUserVoted("test"));
	}
	
	@Test
	public void testHasUserVoted() {
		assertTrue(planningPokerRequirementWithVotes.hasUserVoted("test"));
		assertFalse(planningPokerRequirementWithVotes.hasUserVoted("antitest"));
	}
	
	@Test
	public void testGetVoteByUser() {
		assertEquals(null, planningPokerRequirement.getVoteByUser("test"));
		assertEquals(testVote, planningPokerRequirementWithVotes.getVoteByUser("test"));
		assertEquals(null, planningPokerRequirementWithVotes.getVoteByUser("antitest"));
	}
	
	@Test
	public void testCopyFrom() {
		final PlanningPokerRequirement planningPokerRequirement = new PlanningPokerRequirement();
		final PlanningPokerRequirement updatedRequirement = new PlanningPokerRequirement();
		updatedRequirement.addVote(new PlanningPokerVote("test", 1));
		updatedRequirement.setName("test");
		updatedRequirement.setDescription("test");
		assertFalse(planningPokerRequirement.equals(updatedRequirement));
		planningPokerRequirement.copyFrom(updatedRequirement);
		assertEquals(planningPokerRequirement, updatedRequirement);
		assertEquals(planningPokerRequirement.getVotes(), updatedRequirement.getVotes());
		assertEquals(planningPokerRequirement.getName(), updatedRequirement.getName());
		assertEquals(planningPokerRequirement.getDescription(), updatedRequirement.getDescription());
	}
}
