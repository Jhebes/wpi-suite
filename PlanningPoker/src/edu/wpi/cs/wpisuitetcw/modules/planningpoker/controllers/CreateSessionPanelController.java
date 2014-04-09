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
			this.view.enableDeadlineField();
		//if the deadline checkbox is unchecked disable the deadline selector
		else if( e.getStateChange() == ItemEvent.DESELECTED)
			this.view.disableDeadlineField();
			
			
		
	}
}
