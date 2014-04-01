/**
 * @author Nick Kalamvokis and Matt Suarez
 *
 */

package edu.wpi.cs.wpisuitetcw.modules.planningpoker;
import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Controller to handle retrieving all votes from the server and
 * displaying them in the {@link SearchPlanningPokerVoteView}
 */
public class RetrieveAllPlanningPokerVoteController {

	/** The search votes view */
	
	//not yet implemented. need to make GUI
	protected SearchPlanningPokerVoteView view;

	/** The votes data retrieved from the server */
	protected PlanningPokerVote[] data = null;

	/**
	 * Constructs a new RetrieveAllPlanningPokerVoteController
	 * 
	 * @param view the search votes view
	 */
	public RetrieveAllPlanningPokerVoteController(SearchPlanningPokerVoteView view) {
		this.view = view;
	}

	/**
	 * Sends a request for all of the votes
	 */
	public void refreshData() {		
		final RequestObserver requestObserver = new RetrieveAllPlanningPokerVoteRequestObserver(this);
		Request request;
		request = Network.getInstance().makeRequest("planningpoker/vote", HttpMethod.GET);
		request.addObserver(requestObserver);
		request.send();
	}

	/**
	 * This method is called by the {@link RetrieveAllPlanningPokerVoteRequestObserver} when the
	 * response is received
	 * 
	 * @param votes an array of votes returned by the server
	 */
	public void receivedData(PlanningPokerVote[] votes) {	
		if (votes.length > 0) {
			// save the data
			this.data = votes;

			// set the column names
			String[] columnNames = {"ID", "Card Value"}; //"Title", "Description", "Creator", "Assignee", "Status", "Created", "Last Modified"};
			view.getSearchPanel().getResultsPanel().getModel().setColumnNames(columnNames);

			// put the data in the table
			Object[][] entries = new Object[votes.length][columnNames.length];
			for (int i = 0; i < votes.length; i++) {
				entries[i][0] = String.valueOf(votes[i].getID());
				entries[i][1] = votes[i].getCardValue();
				/*entries[i][2] = votes[i].getDescription();
				entries[i][3] = votes[i].getCreator().getName();*/
			/*	if (votes[i].getUser() != null) {
					entries[i][4] = votes[i].getAssignee().getName();
				}
				else {
					entries[i][4] = "";
				}
				entries[i][5] = votes[i].getStatus().toString();
				entries[i][6] = votes[i].getCreationDate();
				entries[i][7] = votes[i].getLastModifiedDate(); */
			}
			view.getSearchPanel().getResultsPanel().getModel().setData(entries);
			view.getSearchPanel().getResultsPanel().getModel().fireTableStructureChanged();
		}
		else {
			// do nothing, there are no votes
		}
	}

	/**
	 * This method is called by the {@link RetrieveAllPlanningPokerVoteRequestObserver} when an
	 * error occurs retrieving the votes from the server.
	 */
	public void errorReceivingData(String error) {
		JOptionPane.showMessageDialog(view, "An error occurred retrieving votes from the server. " + error, 
				"Error Communicating with Server", JOptionPane.ERROR_MESSAGE);
	}
}

