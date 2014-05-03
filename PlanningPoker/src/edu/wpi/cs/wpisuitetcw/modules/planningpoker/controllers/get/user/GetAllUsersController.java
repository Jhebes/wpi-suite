/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.get.user;

import java.util.List;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.UserStash;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This retrieves all sessions from the core and publishes them to the view
 * 
 */
public class GetAllUsersController {

	/** Instance field for this singleton controller. */
	private static GetAllUsersController instance = null;
	
	/**
	 * Instantiates a new controller tied to the specified view.
	 * Private because this is a singleton.
	 */
	private GetAllUsersController() {
	}
	
	/**
	 * @return The instance for this controller.
	 */
	public static GetAllUsersController getInstance() {
		if (instance == null) {
			instance = new GetAllUsersController();
		}
		return instance;
	}
	
	/**
	 * Callback to handle receiving the users. Updates the user stash.
	 * @param users The users received from the server.
	 */
	public void receivedUsers(List<User> users) {
		UserStash.getInstance().clear();
		UserStash.getInstance().addUser(users);
	}

	/**
	 * Initiate the request to the server on click
	 * 
	 * @param e
	 *            The triggering event
	 */

	/**
	 * Retrieves the sessions from the database.
	 */
	public void retrieveUsers() {
		// Send a request to the core to retrieve the sessions
		final Request request = Network.getInstance().makeRequest(
				"core/user", HttpMethod.GET);
		request.addObserver(new GetAllUsersRequestObserver(this));
		request.send(); // send the request
	}
	
	
}
