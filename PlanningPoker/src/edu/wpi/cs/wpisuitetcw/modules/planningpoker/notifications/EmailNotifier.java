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

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.ConfigLoader;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.exceptions.ConfigLoaderException;

/**
 * Class for sending notifications to users via email.
 */
public class EmailNotifier extends BaseNotifier {

	/**
	 * Generates and sends a message to the user indicating that the planning
	 * poker session started or ended.
	 * 
	 * @param notificationType
	 *            'start' or 'end'
	 * @param recipient
	 *            The recipient address (phone number or email address)
	 * @param deadline
	 *            The deadline for this planning poker session
	 */
	public static void sendMessage(String notificationType, String recipient,
			String deadline) {
		final String message = BaseNotifier.createMessage(notificationType, deadline);

		final String subject = "Planning Poker";

		final Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.smtp.port", 587);
		props.put("mail.smtp.socketFactory.port", 587);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.transport.protocol", "smtp");
		javax.mail.Session mailSession = null;

		mailSession = javax.mail.Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						try {
							return new PasswordAuthentication(ConfigLoader
									.getEmailUsername(), ConfigLoader
									.getEmailPassword());
						} catch (ConfigLoaderException e) {
							Logger.getLogger("PlanningPoker").log(
									Level.SEVERE,
									"Could not load email address or " + 
									"password from coniguration file.", e);
							return null;
						}
					}
				});

		try {

			final Transport transport = mailSession.getTransport();

			final MimeMessage email = new MimeMessage(mailSession);

			email.setSubject(subject);
			email.setFrom(new InternetAddress(ConfigLoader.getEmailUsername()));
			final String[] to = new String[] { recipient };
			email.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to[0]));
			email.setContent(message, "text/html");
			transport.connect();

			transport.sendMessage(email,
					email.getRecipients(Message.RecipientType.TO));
			transport.close();
		} catch (Exception exception) {

		}
	}
}
