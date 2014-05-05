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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * A controller that sends notifications on start or end of a planning poker
 * session. Defaults to sending an email.
 */
public class SendNotificationController {

	/**
	 * Returns a formatted URL to database. URL encodes the deadline into
	 * yyyy-MM-dd hh:mm format. Returns a blank string if there was an error
	 * encoding anything into a URL.
	 * 
	 * @param notificationType
	 *            'start' or 'end'
	 * @param recipient
	 *            The email address of the recipient
	 * @param deadline
	 *            The deadline for this session
	 * @param command
	 *            'sendEmail' or 'sendSMS'
	 * @return The URL at which to post.
	 */
	public static String makeURL(String notificationType, String recipient,
			Date deadline, String command) {
		try {
			String sDeadline = "";

			// Append date for start notifications if there was a date given
			if (deadline != null && notificationType.equals("start")) {
				sDeadline += (new SimpleDateFormat("yyyy-MM-dd hh:mma"))
						.format(deadline);
			}
			// Append date for end notifications if there was a date given
			else if (deadline != null && notificationType.equals("end")) {
				sDeadline += (new SimpleDateFormat("yyyy-MM-dd hh:mma"))
						.format(deadline);
			}

			final StringBuilder sb = new StringBuilder();
			sb.append("Advanced/planningpoker/session/" + command + "/");
			sb.append(URLEncoder.encode(notificationType, "UTF-8"));
			sb.append('/');
			sb.append(URLEncoder.encode(recipient, "UTF-8"));
			sb.append('/');

			sb.append(URLEncoder.encode(sDeadline, "UTF-8"));

			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			Logger.getLogger("PlanningPoker").log(
					Level.SEVERE,
					"Unsupported encoding when creating URL for "
							+ "sending notifications.", e);
			return "";
		}
	}

	/**
	 * Sends a notification to a user on session activate or close.
	 * 
	 * @param notificationType
	 *            is this a "start" notification or "end" notification
	 * @param recipient
	 *            who is receiving this notification
	 * @param deadline
	 *            the deadline for this planning poker session
	 * @param command
	 *            'sendEmail' or 'sendSMS'
	 */
	public static void sendNotification(String notificationType,
			String recipient, Date deadline, String command) {
		// Send a request to the core to retrieve the sessions
		final Request request = Network.getInstance().makeRequest(
				makeURL(notificationType, recipient, deadline, command),
				HttpMethod.GET);
		request.send(); // send the request
	}

}
