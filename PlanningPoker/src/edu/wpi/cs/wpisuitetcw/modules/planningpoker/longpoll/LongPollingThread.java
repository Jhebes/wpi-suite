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

import java.util.List;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.LongPollResponse;

/**
 * A special kind of thread used for long polling. This thread simply blocks
 * until the {@link pushData} method is called.
 */
public class LongPollingThread extends Thread {

	/** Whether the thread has been stopped. */
	private boolean stopped;

	/** The list of data to push to the server. */
	private final List<LongPollResponse> dataToPush;

	/**
	 * Constructs a long polling thread, storing a copy of the list of data
	 * to push to the clients.
	 * 
	 * @param dataToPush
	 */
	public LongPollingThread(List<LongPollResponse> dataToPush) {
		this.dataToPush = dataToPush;
		stopped = false;
	}

	/**
	 * Main method for this thread. Simply blocks until it is stopped by
	 * {@link pushData}.
	 */
	public void run() {
		while (!stopped) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
		}
	}

	/**
	 * Unblocks this thread and passes the data to the calling method by
	 * adding it to the list used in this thread's constructor.
	 * 
	 * @param data
	 *            The long poll response to push
	 */
	public void pushData(LongPollResponse data) {
		stopped = true;
		dataToPush.add(data);
	}
}
