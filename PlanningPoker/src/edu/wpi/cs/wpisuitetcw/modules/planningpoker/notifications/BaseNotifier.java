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

/**
 * Base class for sending notifications to users on start or end of a planning
 * poker session.
 */
public class BaseNotifier {
	private static String START_MESSAGE = "A new planning poker session has begun!";
	private static String END_MESSAGE = "A planning poker session has ended!";

	/**
	 * Creates a new message for a notification based on the notification type
	 * and the deadline.
	 * 
	 * @param notificationType
	 *            'start' or 'end'
	 * @param deadline
	 *            String containing the deadline information
	 * @return The generated message
	 */
	public static String createMessage(String notificationType, String deadline) {
		String message = "";

		if (notificationType.equals("start")) {
			message += START_MESSAGE;
		} else if (notificationType.equals("end")) {
			message += END_MESSAGE;
		}

		// No deadline, start
		if ((deadline.equals("") || deadline == null)
				&& notificationType.equals("start")) {
			message += " There is no deadline for this session.";
			// No deadline, end
		} else if ((deadline.equals("") || deadline == null)
				&& notificationType.equals("end")) {
			message += " There was no deadline for this session.";
			// Deadline, start
		} else if ((!deadline.equals("") && deadline != null)
				&& notificationType.equals("start")) {
			message += " It's deadline is " + deadline + ".";
			// Deadline, end
		} else if ((!deadline.equals("") && deadline != null)
				&& notificationType.equals("end")) {
			message += " It's deadline was " + deadline + ".";
		}

		return message;
	}
}
