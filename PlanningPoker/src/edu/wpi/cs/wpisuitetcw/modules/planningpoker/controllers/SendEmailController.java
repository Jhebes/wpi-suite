/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This retrieves all sessions from the core and publishes them to the view
 * 
 */
public class SendEmailController {
	
	private static SendEmailController instance;
	
	/**
	 * Instantiates a new controller tied to the specified view.
	 * Private because this is a singleton.
	 */
	private SendEmailController() {
	}
	
	public static SendEmailController getInstance() {
		if (instance == null) {
			instance = new SendEmailController();
		}
		return instance;
	}

	/**
	 * Returns a formatted URL to database 
	 */
	public static String makeURL(String notificationType, String recipient) {
		return "Advanced/planningpoker/session/sendEmail/" + notificationType + "/" + recipient;
	}
	
	/**
	 * Sends a notification to a user on session activate or close.
	 * @param notificationType is this a "start" notification or "end" notification
	 * @param recipient who is receiving this notification
	 */
	public void sendEmail(String notificationType, String recipient) {
		// Send a request to the core to retrieve the sessions
		final Request request = Network.getInstance().makeRequest(
				makeURL(notificationType, recipient), HttpMethod.GET);
		request.send(); // send the request
	}

}
