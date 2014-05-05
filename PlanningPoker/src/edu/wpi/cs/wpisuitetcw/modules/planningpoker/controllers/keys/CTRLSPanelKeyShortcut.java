
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.keys;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.AbstractAction;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.CreateNewDeckController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerDeck;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.EditSessionPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.tabs.SessionDeckPanel;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

@SuppressWarnings("serial")
public class CTRLSPanelKeyShortcut extends AbstractAction {
	
	private final EditSessionPanel view;
	private final PlanningPokerSession session;
	
	/**
	 * Construct an CTRLSPanelKeyShortcut for the given view
	 * 
	 * @param view
	 *            the view where the user enters data for the new session
	 * 
	 * 
	 * @param session
	 *            the planning poker session being edited
	 */
	public CTRLSPanelKeyShortcut(EditSessionPanel view,
			PlanningPokerSession session) {

		this.view = view;
		this.session = session;
	}

	/**
	 * This method is called when the user clicks the "Create" button
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
	 */
	@Override
	public synchronized void actionPerformed(ActionEvent event) {
		// if a name was entered create the session
		// otherwise the button will do nothing
		if (view.hasAllValidInputs()) {

			// Get the inputs from user
			final String name = view.getNameTextField().getText();
			final Date d = view.getDeadline();
			final String des = view.getDescriptionBox().getText();
			final String deckName = (String) view.getDeckType()
					.getSelectedItem();

			// Store the new deck if user creates one
			if (view.isInCreateMode()) {
				final CreateNewDeckController createDeckController = new CreateNewDeckController(
						view.getDeckPanel());
				createDeckController.addDeckToDatabase();
			}

			session.setOwnerUserName(ConfigManager.getConfig().getUserName());
			session.setName(name);
			session.setDeadline(d);
			session.setDescription(des);

			// Associate a deck to the new session if the user does not choose
			// 'No deck'
			if (!view.isInNoDeckMode()) {
				// 'Default' option, bind a new Fibonacci deck to the
				// session
				if (deckName.equals("Default")) {
					session.setDeck(new PlanningPokerDeck());
				} else {

					final SessionDeckPanel deckPanel = view.getDeckPanel();
					session.setDeck(new PlanningPokerDeck(deckName,
							deckPanel.getNewDeckValues(), deckPanel.getMaxSelectionCards()));
				}
			}

			// save changes to the session
			session.save();
			view.disableChangesBtn();
			view.getBtnSaveSession().setEnabled(false);

		} else {
			// user has yet entered all required data
			view.repaint();
		}

	}

}
