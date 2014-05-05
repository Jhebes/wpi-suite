/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.help;

/**
 * Names for all the help entries.
 */
public enum HelpEntry {
	HELP("Help Guide"),DEFAULT("Default"), POKER("Planning poker"), SESSION("Session"), DECK(
			"Deck"), REQUIREMENT("Requirement"), VOTING("Voting"), SHORTCUT("Shortcuts");

	private final String type;

	/**
	 * Constructor for HelpEntry
	 * 
	 * @param s
	 *            String
	 */
	private HelpEntry(String s) {
		type = s;
	}

	/**
	 * Method toString
	 * 
	 * @return String
	 */
	public String toString() {
		return type;
	}
}
