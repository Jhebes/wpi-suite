package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class AddDeckRequestObserver implements RequestObserver {
	private final CreateNewDeckController controller;

	public AddDeckRequestObserver(CreateNewDeckController controller) {
		this.controller = controller;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		// TODO Auto-generated method stub
		final ResponseModel response = iReq.getResponse();
		final PlanningPokerDeck deck = PlanningPokerDeck.fromJson(response
				.getBody());
		controller.onSuccess(deck);
	}

	@Override
	public void responseError(IRequest iReq) {
		System.err.println("The request to add a deck failed.");

	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("The request to add a deck failed.");

	}

}
