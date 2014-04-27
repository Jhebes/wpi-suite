/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.exceptions;

/**
 * This error indicates that there was some problem loading the configuration
 * file for planning poker.
 */
public class ConfigLoaderException extends Exception {

	/**
	 * Constructs a config loader error with an error message.
	 * @param string The error message
	 */
	public ConfigLoaderException(String string) {
		super(string);
	}
}
