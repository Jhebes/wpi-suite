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

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class AddDeckRequestObserver implements RequestObserver {
	private final CreateNewDeckController controller;

	public AddDeckRequestObserver(CreateNewDeckController controller) {
		this.controller = controller;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		final ResponseModel response = iReq.getResponse();
		final PlanningPokerDeck deck = PlanningPokerDeck.fromJson(response.getBody());
		Logger.getLogger("PlanningPoker").log(Level.INFO, "New deck " + deck.getDeckName() + " has been stored!");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a deck failed.");

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a deck failed.");

	}

}
