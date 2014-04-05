/**
 * @author Nick Kalamvokis and Matt Suarez
 *
 */

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving one vote from the server
 */
public class RetrievePlanningPokerVoteController extends MouseAdapter {

	/** The results panel */
	protected SessionInProgressPanel view;

	/**
	 * Construct the controller
	 * 
	 * @param view
	 *            the parent view
	 */
	public RetrievePlanningPokerVoteController(SessionInProgressPanel view) {
		this.view = view;
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() == 2) { /* only respond to double clicks */

			// Get a reference to the results JTable from the mouse event
			JTable resultsTable = (JTable) me.getSource();

			// Determine the row the user clicked on
			int row = resultsTable.rowAtPoint(me.getPoint());

			// make sure the user actually clicked on a row
			if (row > -1) {
				String voteId = (String) resultsTable.getValueAt(row, 0);

				// Create and send a request for the vote with the given ID
				Request request;
				request = Network.getInstance().makeRequest(
						"votetracker/vote/" + voteId, HttpMethod.GET);
				request.addObserver(new RetrievePlanningPokerVoteRequestObserver(
						this));
				request.send();
			}
		}
	}

	/**
	 * Called by {@link RetrievePlanningPokerVoteRequestObserver} when the
	 * response is received from the server.
	 * 
	 * @param vote
	 *            the vote that was retrieved
	 */
	public void showPlanningPokerVote(PlanningPokerVote vote) {
		// Make a new vote view to display the vote that was received

		//view.setTextField(view.getTextField().getText() + "\n"
		//		+ String.valueOf(vote.getCardValue()));

	}

	/**
	 * Called by {@link RetrievePlanningPokerVoteRequestObserver} when an error
	 * occurred retrieving the vote from the server.
	 */
	public void errorRetrievingPlanningPokerVote(String error) {
		JOptionPane.showMessageDialog(view,
				"An error occurred opening the vote you selected. " + error,
				"Error opening vote", JOptionPane.ERROR_MESSAGE);
	}
}
