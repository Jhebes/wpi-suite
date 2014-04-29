/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.notifications;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.plivo.helper.api.client.RestAPI;
import com.plivo.helper.api.response.message.MessageResponse;
import com.plivo.helper.exception.PlivoException;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.ConfigLoader;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.exceptions.ConfigLoaderException;

/**
 * Class for sending notifications to users via SMS.
 */
public class SMSNotifier extends BaseNotifier {

	/**
	 * Generates and sends a message to the user indicating that the planning
	 * poker session started or ended.
	 * 
	 * @param notificationType
	 *            'start' or 'end'
	 * @param recipient
	 *            The recipient phone number
	 * @param deadline
	 *            The deadline for this planning poker session
	 */
	public static void sendMessage(String notificationType, String recipient,
			String deadline) {
		final String message = BaseNotifier.createMessage(notificationType, deadline);

		String authId, authToken, src;
		try {
			authId = ConfigLoader.getPlivoAuthId();
			authToken = ConfigLoader.getPlivoAuthToken();
			src = ConfigLoader.getPlivoPhoneNumber();
		} catch (ConfigLoaderException e) {
			Logger.getLogger("PlanningPoker").log(
					Level.SEVERE,
					"Could not load Plivo auth from coniguration file.", e);
			return;
		}

		final RestAPI api = new RestAPI(authId, authToken, "v1");

		final Map<String, String> parameters = new LinkedHashMap<String, String>();
		parameters.put("src", src);
		parameters.put("dst", recipient);
		parameters.put("text", message);
		parameters.put("url", "http://server/message/notification/");

		final Logger logger = Logger.getLogger("PlanningPoker");
		try {
			api.sendMessage((LinkedHashMap<String, String>) parameters);
		} catch (PlivoException e) {
			logger.log(Level.INFO, e.getLocalizedMessage());
		}
	}
}
