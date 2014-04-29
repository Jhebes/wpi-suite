/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.put;



import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A controller that stores the given PlanningPokerSession to the database
 */
public class PutSessionController {

	/**
	 * Store the given PlanningPokerSession to the database
	 * @param session a PlanningPokerSession that would be stored
	 * in the database
	 */
	public PutSessionController(PlanningPokerSession session) {
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/session", HttpMethod.PUT);
		request.setBody(session.toJSON());
		request.addObserver(new GenericPUTRequestObserver());
		request.send();
	}

}
