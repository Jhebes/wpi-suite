/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.longpoll;

/**
 * A long polling handler response to push events from the server of a
 * particular type T.
 * 
 * @param <T> The type of object sent from the server.
 */
public abstract class LongPollingHandler<T> {
	
	/**
	 * Handles an object of type T sent from the server.
	 * @param json JSON representation of the object sent from the server
	 */
	public abstract void handle(String json);
}
