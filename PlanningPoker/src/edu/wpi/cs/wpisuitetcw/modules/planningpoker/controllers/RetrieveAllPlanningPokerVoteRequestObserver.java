/**
 * @author Nick Kalamvokis and Matt Suarez
 *
 */

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * An observer for a request to retrieve all votes
 */
public class RetrieveAllPlanningPokerVoteRequestObserver implements RequestObserver {

	/** The controller managing the request */
	protected RetrieveAllPlanningPokerVoteController controller;

	/**
	 * Construct the observer
	 * @param controller
	 */
	public RetrieveAllPlanningPokerVoteRequestObserver(RetrieveAllPlanningPokerVoteController controller) {
		this.controller = controller;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		if (response.getStatusCode() == 200) {
			// parse the response				
			PlanningPokerVote[] votes = PlanningPokerVote.fromJSONArray(response.getBody());

			// notify the controller
			controller.receivedData(votes);
		}
		else {
			controller.errorReceivingData("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
		}
	}

	@Override
	public void responseError(IRequest iReq) {
		// an error occurred
		controller.errorReceivingData("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
	}
	
	@Override
	public void fail(IRequest iReq, Exception exception) {
		// an error occurred
		controller.errorReceivingData("Unable to complete request: " + exception.getMessage());
	}
}
