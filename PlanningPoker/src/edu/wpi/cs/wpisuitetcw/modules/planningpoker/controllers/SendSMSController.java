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

import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * This retrieves all sessions from the core and publishes them to the view
 * 
 */
public class SendSMSController {

	private static SendSMSController instance;

	/**
	 * Instantiates a new controller tied to the specified view. Private because
	 * this is a singleton.
	 */
	private SendSMSController() {
	}

	public static SendSMSController getInstance() {
		if (instance == null) {
			instance = new SendSMSController();
		}
		return instance;
	}

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
	 * @return The URL at which to post.
	 */
	public static String makeURL(String notificationType, String recipient,
			Date deadline) {

		try {
			String sDeadline = (new SimpleDateFormat("yyyy-MM-dd hh:mm"))
					.format(deadline);
			StringBuilder sb = new StringBuilder();
			sb.append("Advanced/planningpoker/session/sendIM/");
			sb.append(URLEncoder.encode(notificationType, "UTF-8"));
			sb.append("/");
			sb.append(URLEncoder.encode(recipient, "UTF-8"));
			sb.append("/");
			sb.append(URLEncoder.encode(sDeadline, "UTF-8"));
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
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
	 */
	public void sendSMS(String notificationType, String recipient,
			Date deadline) {
		// Send a request to the core to retrieve the sessions
		final Request request = Network.getInstance().makeRequest(
				makeURL(notificationType, recipient, deadline), HttpMethod.GET);
		request.send(); // send the request
	}

}
