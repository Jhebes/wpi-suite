/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;

/**
 * @author troyling, remckenna
 * 
 * Controller for CreateSessionPanel buttons.
 */
public class CreateSessionPanelController implements ItemListener {
	private CreateSessionPanel view;

	public CreateSessionPanelController(CreateSessionPanel view) {
		this.view = view;

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		//if the deadline checkbox is checked then enable the deadline selector
		if(e.getStateChange() == ItemEvent.SELECTED)
			this.view.toggleDeadline(true);
		//if the deadline checkbox is unchecked disable the deadline selector
		else if( e.getStateChange() == ItemEvent.DESELECTED)
			this.view.toggleDeadline(false);
			
			
		
	}
}
