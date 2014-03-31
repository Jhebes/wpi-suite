/**
 * @author Nick Kalamvokis and Matt Suarez
 */

package edu.wpi.cs.wpisuitetcw.modules.planningpoker;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

/**
 * Action that calls {@link RetrieveAllPlanningPokerVoteController#refreshData()}, default mnemonic key is R
 */
@SuppressWarnings("serial")
public class RefreshPlanningPokerVoteAction extends AbstractAction {
	
	/** The controller to be called when this action is performed */
	protected final RetrieveAllPlanningPokerVoteController controller;
	
	/**
	 * Construct a RefreshPlanningPokerVoteAction
	 * @param controller when the action is performed this controller's refreshData() method will be called
	 */
	public RefreshPlanningPokerVoteAction(RetrieveAllPlanningPokerVoteController controller) {
		super("Refresh");
		this.controller = controller;
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		controller.refreshData();
	}

}
