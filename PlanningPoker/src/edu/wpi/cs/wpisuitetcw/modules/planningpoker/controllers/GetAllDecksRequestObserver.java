/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.util.ArrayList;
import java.util.Arrays;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class GetAllDecksRequestObserver implements RequestObserver {

	private GetAllDecksController controller;

	public GetAllDecksRequestObserver(GetAllDecksController controller) {
		this.controller = controller;
	}

	/*
	 * Parse the session out of the response body and pass them to the
	 * controller
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi
	 * .cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		final PlanningPokerDeck[] decks = PlanningPokerDeck.fromJSONArray(iReq
				.getResponse().getBody());
		controller.updateDecks(new ArrayList<PlanningPokerDeck>(Arrays
				.asList(decks)));
	}

	/*
	 * @see
	 * edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.
	 * cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		fail(iReq, null);
	}

	/*
	 * What to do when we fail to get sessions from the server
	 * 
	 * @see
	 * edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng
	 * .network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {

	}

}
