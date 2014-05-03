/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.exceptions.ConfigLoaderException;

/**
 * A config loader loads planning poker-related configuration files from a file
 * normally called ".planningpoker.properties". On Linux, this file exists in
 * the user's home folder. On Windows and Mac, it exists in the build for
 * Eclipse. When run as a standalone JAR, it exists in the same directory as the
 * JAR.
 */
public class ConfigLoader {
	private static boolean initialized = false;
	private static String filename = "";
	private static String emailUsername = "";
	private static String emailPassword = "";
	private static String plivoAuthId = "";
	private static String plivoAuthToken = "";
	private static String plivoPhoneNumber = "";

	/**
	 * Initializes the configuration loader with the default filename.
	 */
	public static void initialize() {
		initialize(".planningpoker.properties");
	}

	/**
	 * Initializes this static class, loading all information from the user's
	 * configuration file. Initializes with the default filename of
	 * ".planningpoker.properties"
	 * 
	 * @param filename
	 *            The filename for the configuration file
	 */
	public static void initialize(String filename) {
		ConfigLoader.filename = filename;
		final Properties props = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(filename);
			props.load(input);

			// set the properties value
			emailUsername = props.getProperty("email.username", "");
			emailPassword = props.getProperty("email.password", "");
			plivoAuthId = props.getProperty("plivo.authId", "");
			plivoAuthToken = props.getProperty("plivo.authToken", "");
			plivoPhoneNumber = props.getProperty("plivo.phoneNumber", "");

			initialized = true;
		} catch (IOException e) {
			Logger.getLogger("PlanningPoker").log(Level.SEVERE,
					"Could not load configuration file " + filename, e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					Logger.getLogger("PlanningPoker")
							.log(Level.SEVERE,
									"Could not close configuration file "
											+ filename, e);
				}
			}
		}
	}

	/**
	 * Ensures that the config loader is initialized.
	 * 
	 * @throws ConfigLoaderException
	 */
	public static void ensureInitialized() throws ConfigLoaderException {
		if (!initialized) {
			initialize();
			if (!initialized) {
				throw new ConfigLoaderException(
						"Could not initialize the class loader. Do you have"
								+ " a .planningpoker.properties file?");
			}
		}
	}

	/**
	 * Attempts to get the email username for sending emails.
	 * 
	 * @return The email username
	 * @throws ConfigLoaderException
	 */
	public static String getEmailUsername() throws ConfigLoaderException {
		ensureInitialized();
		return emailUsername;
	}

	/**
	 * Attempts to get the email password for sending emails.
	 * 
	 * @return The email password
	 * @throws ConfigLoaderException
	 */
	public static String getEmailPassword() throws ConfigLoaderException {
		ensureInitialized();
		return emailPassword;
	}

	/**
	 * Attempts to get the auth ID for the user's Plivo account.
	 * 
	 * @return The Plivo auth ID
	 * @throws ConfigLoaderException
	 */
	public static String getPlivoAuthId() throws ConfigLoaderException {
		ensureInitialized();
		return plivoAuthId;
	}

	/**
	 * Attempts to get the auth token for the user's Plivo account.
	 * 
	 * @return The Plivo auth token
	 * @throws ConfigLoaderException
	 */
	public static String getPlivoAuthToken() throws ConfigLoaderException {
		ensureInitialized();
		return plivoAuthToken;
	}

	/**
	 * Attempts to get the phone number for the user's Plivo account.
	 * 
	 * @return The Plivo phone number
	 * @throws ConfigLoaderException
	 */
	public static String getPlivoPhoneNumber() throws ConfigLoaderException {
		ensureInitialized();
		return plivoPhoneNumber;
	}

	/**
	 * @return The filename for this configuration file.
	 */
	public static String getFilename() {
		return filename;
	}
}
