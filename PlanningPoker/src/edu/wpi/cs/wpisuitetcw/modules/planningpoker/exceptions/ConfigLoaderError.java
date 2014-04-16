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
public class ConfigLoaderError extends Exception {

	/**
	 * Constructs a config loader error with an error message.
	 * @param string The error message
	 */
	public ConfigLoaderError(String string) {
		super(string);
	}

	/**
	 * Version UID for serializing.
	 */
	private static final long serialVersionUID = -8160649805099358043L;

}
