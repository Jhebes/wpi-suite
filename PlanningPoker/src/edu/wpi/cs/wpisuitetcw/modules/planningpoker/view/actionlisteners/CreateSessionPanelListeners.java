package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GetAllDecksController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionBtnPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionReqPanel;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;

public class CreateSessionPanelListeners {
	public static class CreateSession implements ActionListener {
		private final CreateSessionPanel view;

		private boolean isEditMode;



		/**
		* Construct an AddSessionController for the given view
		*
		* @param view
		* the view where the user enters data for the new session
		*
		* @param isEditMode
		* the value representing if the panel contains an already
		* created session or not
		*/
		public CreateSession(CreateSessionPanel view, boolean isEditMode) {
//			GetAllDecksController.getInstance().refreshDecks();
			this.view = view;
			this.isEditMode = isEditMode;
		}

		/*
		* This method is called when the user clicks the "Create" button
		*
		* @see
		* java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		*/
		@Override
		public void actionPerformed(ActionEvent event) {
		// if a name was entered create the session
		// otherwise the button will do nothing
		if ((this.view.requiredFieldEntered() == true) && (this.isEditMode == false)) {
			// Get the name of the session
			String name = this.view.getNameTextField().getText();
		
			// TODO Session type should be stored
			Date d = this.view.getDeadline();
			String des = this.view.getDescriptionBox().getText();
			String deckName = (String) this.view.getDeckType().getSelectedItem();
		
			// Create a new session and populate its data
			PlanningPokerSession session = new PlanningPokerSession();
			session.setOwnerUserName(ConfigManager.getConfig().getUserName());
			session.setName(name);
			session.setDeadline(d);
			session.setDescription(des);
			try {
				session.setDeck(GetAllDecksController.getInstance().setDeckByName(deckName));
			} catch (WPISuiteException e) {
				Logger.getLogger("PlanningPoker").log(Level.SEVERE, "Error getting all decks", e);
			}
		
			session.save();
			//System.out.println("Saved session");
			ViewEventManager.getInstance().removeTab(this.view);
		} else {
			//System.out.println("Not saving session");
			// user has yet entered all required data
			//TODO: maybe make the warning a pop-up
			this.view.repaint();
		}

		}
	}
}
