package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models;

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

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

public class PlanningPokerRequirementTest {

	@Test
	public void testEquals() {
		UUID uuid = UUID.randomUUID();
		PlanningPokerRequirement req = new PlanningPokerRequirement();
		req.setId(uuid);
		
		PlanningPokerRequirement req2 = new PlanningPokerRequirement();
		req2.setId(uuid);
		
		assertTrue(req.equals(req2));
	}
}
