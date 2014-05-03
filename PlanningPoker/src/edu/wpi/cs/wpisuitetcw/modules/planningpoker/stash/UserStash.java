/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.get.user.GetAllUsersController;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * Keeps a local cache of all the users in the system, so they can be
 * quickly referenced by local processes
 *
 */
public class UserStash {

	private static UserStash self = null;
	private List<User> users = new ArrayList<User>();

	/**
	 * This is a singleton
	 * 
	 * @return The instance for the singleton
	 */
	public static UserStash getInstance() {
		if (self == null) {
			self = new UserStash();
		}
		return self;
	}

	/**
	 * 
	 * @return All users stored locally
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * Used by the updater
	 * 
	 * @param u
	 *            The user to add to the local stash
	 */
	public void addUser(User u) {
		users.add(u);
	}

	/**
	 * Bulk form of addUser()
	 * 
	 * @param p
	 *            List of users to add
	 */
	public void addUser(Iterable<User> p) {
		for (User s : p) {
			this.addUser(s);
		}
	}

	/**
	 * Clear all users in the list (used by updater)
	 */
	public void clear() {
		users.clear();
	}

	/**
	 * Using only a username, get the user instance that it corresponds to
	 * 
	 * @param name
	 *            The username to look up
	 * @return The user instance
	 */
	public User getUserByName(String name) {
		for (User u : users) {
			if (u.getUsername().equals(name)) {
				return u;
			}
		}
		return null;
	}

	/**
	 * Connect to the remote database and pull its list of users
	 */
	public void synchronize() {
		GetAllUsersController.getInstance().retrieveUsers();

	}
}
