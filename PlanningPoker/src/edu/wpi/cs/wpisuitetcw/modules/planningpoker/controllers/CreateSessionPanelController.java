/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.EditSessionPanel;

/**
 * Controller that handles actions of the CreateSessionPanel buttons.
 */
public class CreateSessionPanelController implements ItemListener {
	/** A view that has the buttons to create a session */
	private EditSessionPanel view;

	/**
	 * Construct a CreateSessionPanelController by storing the
	 * CreateSessionPanel whose buttons can trigger the action here
	 * @param view
	 */
	public CreateSessionPanelController(EditSessionPanel view) {
		this.view = view;
	}

	/**
	 * Allow the users changing the deadline when they check the
	 * "Deadline" checkbox
	 * {@inheritDoc}
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		// iCf the deadline checkbox is checked then enable the deadline selector
		if (e.getStateChange() == ItemEvent.SELECTED){
			view.enableDeadlineField();			
		}
		// if the deadline checkbox is unchecked disable the deadline selector
		else if (e.getStateChange() == ItemEvent.DESELECTED) {
			view.disableDeadlineField();
		}
	}
}
