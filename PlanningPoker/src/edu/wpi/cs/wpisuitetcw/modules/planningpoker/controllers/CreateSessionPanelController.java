/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;

/**
 * @author troyling, remckenna
 * 
 */
public class CreateSessionPanelController implements ActionListener {
	private CreateSessionPanel view;

	public CreateSessionPanelController(CreateSessionPanel view) {
		this.view = view;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.view.toggleDeadline();

	}

}
