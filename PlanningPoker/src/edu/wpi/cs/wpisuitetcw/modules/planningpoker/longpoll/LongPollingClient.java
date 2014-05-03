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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.LongPollResponse;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * A long polling client attempts to connect to the server using the advanced
 * get API with a long timeout. The server can then "push" information to the
 * client by responding to this request. The polling client then handles the
 * request using a {@link LongPollingHandler}.
 */
public class LongPollingClient {

	public static final int TIMEOUT = 30000; // milliseconds
	public static final String LONG_POLL_URL = "Advanced/planningpoker/longpoll";
	@SuppressWarnings("rawtypes")
	public static final Map<Type, LongPollingHandler> handlers = new HashMap<Type, LongPollingHandler>();

	/**
	 * Constructs a long polling client, which instantiates the appropriate
	 * {@link LongPollingHandler}s for each type supported.
	 */
	public LongPollingClient() {
		handlers.put(PlanningPokerSession.class, new PlanningPokerSessionHandler());
	}

	/**
	 * Starts a thread that sends new requests in a loop.
	 */
	public void startThread() {
		final Thread thread = new Thread(new Runnable() {
			public void run() {
				// Wait for login
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					
				}  
				while (true) {
					try {
						final ResponseModel response = sendLongRequest();
						
						if (response == null) {
							continue;
						}
						
						final String body = response.getBody();
						final LongPollResponse longPollResponse = LongPollResponse.fromJson(body);
						if (longPollResponse == null) {
							continue;
						} else {
							handleLongPollResponse(longPollResponse);
						}
					} catch (NullPointerException e) {
						Logger.getLogger("PlanningPoker").log(Level.FINEST, "Waiting for network initialization.");
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}
		}, "LongPollingClient");
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * Sends a request to the server. This request is not asynchronous and will
	 * block until the server replies.
	 * 
	 * @return The response from the asynchronous call
	 */
	public ResponseModel sendLongRequest() {
		final Semaphore sem = new Semaphore(1);
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		final List<ResponseModel> responses = new ArrayList<ResponseModel>();

		final Request request = Network.getInstance().makeRequest(LONG_POLL_URL, HttpMethod.GET);
		request.setReadTimeout(TIMEOUT);
		request.addObserver(new RequestObserver() {
			@Override
			public void responseSuccess(IRequest request) {
				responses.add(request.getResponse());
				sem.release();
			}

			@Override
			public void responseError(IRequest request) {
				sem.release();
			}

			@Override
			public void fail(IRequest request, Exception exception) {
				sem.release();
			}
		});
		request.send();

		boolean acquired = false;
		while (!acquired) {
			try {
				sem.acquire();
				acquired = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return request.getResponse();
	}

	/**
	 * Handles long poll responses by deferring to the
	 * {@link LongPollingHandler}s.
	 * 
	 * @param longPollResponse
	 *            The response object to handle
	 */
	@SuppressWarnings("unchecked")
	public void handleLongPollResponse(LongPollResponse longPollResponse) {
		final Type type = longPollResponse.getType();
		if (!handlers.containsKey(type)) {
			Logger.getLogger("PlanningPoker").log(Level.SEVERE,
					"Could not handle new object from server of type " + type.toString());
		} else {
			handlers.get(type).handle(longPollResponse.getData());
		}
	}
}
