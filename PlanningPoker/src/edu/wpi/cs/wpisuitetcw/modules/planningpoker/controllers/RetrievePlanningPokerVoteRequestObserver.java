/**
 * @author Nick Kalamvokis and Matt Suarez
 */

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * An observer for a request to retrieve a vote with the provided id
 */
public class RetrievePlanningPokerVoteRequestObserver implements
		RequestObserver {

	/** The retrieve vote controller using this observer */
	protected RetrievePlanningPokerVoteController controller;

	/**
	 * Construct a new observer
	 * 
	 * @param controller
	 *            the controller managing the request
	 */
	public RetrievePlanningPokerVoteRequestObserver(
			RetrievePlanningPokerVoteController controller) {
		this.controller = controller;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		// check the response code of the request
		if (response.getStatusCode() != 200) {
			controller.errorRetrievingPlanningPokerVote("Received "
					+ iReq.getResponse().getStatusCode()
					+ " error from server: "
					+ iReq.getResponse().getStatusMessage());
			return;
		}

		// parse the PlanningPokerVote received from the core
		PlanningPokerVote[] votes = PlanningPokerVote.fromJSONArray(response.getBody());
		if (votes.length > 0 && votes[0] != null) {
			controller.showPlanningPokerVote(votes[0]);
		} else {
			controller.errorRetrievingPlanningPokerVote("No votes received.");
		}
	}

	@Override
	public void responseError(IRequest iReq) {
		controller.errorRetrievingPlanningPokerVote("Received "
				+ iReq.getResponse().getStatusCode() + " error from server: "
				+ iReq.getResponse().getStatusMessage());
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO deal with exception
		controller
				.errorRetrievingPlanningPokerVote("Unable to complete request: "
						+ exception.getMessage());
	}
}
