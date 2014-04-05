/**
 * @author Nick Kalamvokis and Matt Suarez
 *
 */

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving all votes from the server and displaying them
 * in the {@link SearchPlanningPokerVoteView}
 */
public class RetrieveAllPlanningPokerVoteController implements ActionListener {

	/** The search votes view */

	// not yet implemented. need to make GUI
	protected SessionInProgressPanel view;
	protected Requirement req;

	/**
	 * Constructs a new RetrieveAllPlanningPokerVoteController
	 * 
	 * @param view
	 *            the search votes view
	 */
	public RetrieveAllPlanningPokerVoteController(SessionInProgressPanel view,
			Requirement req) {
		this.req = req;
		this.view = view;
	}

	/**
	 * This method is called by the
	 * {@link RetrieveAllPlanningPokerVoteRequestObserver} when the response is
	 * received
	 * 
	 * @param votes
	 *            an array of votes returned by the server
	 */
	public void receivedData(PlanningPokerVote[] votes) {
		if (votes.length > 0) {
			// passing votes to view
			
			
			for(PlanningPokerVote v: votes){
				System.out.println(v.getCardValue());
			}
			
			//view.receiveVotes(votes);
		}
	}

	/**
	 * This method is called by the
	 * {@link RetrieveAllPlanningPokerVoteRequestObserver} when an error occurs
	 * retrieving the votes from the server.
	 */
	public void errorReceivingData(String error) {
		JOptionPane.showMessageDialog(view,
				"An error occurred retrieving votes from the server. " + error,
				"Error Communicating with Server", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		final RequestObserver requestObserver = new RetrieveAllPlanningPokerVoteRequestObserver(
				this);
		Request request;
		request = Network.getInstance().makeRequest("planningpoker/vote",
				HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();

	}
}
