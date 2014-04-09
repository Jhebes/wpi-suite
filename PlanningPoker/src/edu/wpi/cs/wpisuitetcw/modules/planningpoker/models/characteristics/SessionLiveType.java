/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.characteristics;

/**
 * Type of a session
 * @author troyling
 *
 */
public enum SessionLiveType {
	LIVE("Live"),
	DISTRIBUTED("Distributed");
	
	private final String type;
	
	/**
	 * Constructor for SessionLiveType.
	 * @param s String
	 */
	private SessionLiveType(String s)
	{
		type = s;
	}
	
	/** 
	 * Method toString
	 * @return String
	 */
	public String toString() {
		return type;
	}
}
