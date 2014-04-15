package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session.GetAllSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.OverviewTableSessionTableModel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.SessionInProgressPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * The controller for SessionInProgress Panel
 *
 */
public class SessionInProgressController {
	private SessionInProgressPanel view;
	private JButton cancelBtn;

	public SessionInProgressController(SessionInProgressPanel view) {
		this.view = view;
		this.cancelBtn = view.getCancelBtn();
	}
		
	/**
	 * Set the action listener for canceling this session in progress
	 * @param button
	 * @param panel
	 */
	public void cancelSessionAction(JButton button, final SessionInProgressPanel panel) {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.getSession().cancel();
				ViewEventManager.getInstance().removeTab(panel);
				System.out.println("You are canceling this");
				panel.getSession().update();
				GetAllSessionsController.getInstance().retrieveSessions();
			}
		});
	}
}
