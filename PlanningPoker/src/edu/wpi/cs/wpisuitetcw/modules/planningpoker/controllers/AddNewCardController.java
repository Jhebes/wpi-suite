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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateNewDeckPanel;

public class AddNewCardController implements ActionListener {
	private CreateNewDeckPanel view;
	
	public AddNewCardController(CreateNewDeckPanel deckPanel){
		this.view = deckPanel;
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.view.addNewCard();
		//JPanel centerPanel = this.view.getCenterPanel();
		//centerPanel.paintImmediately(centerPanel.getX(), centerPanel.getY(), centerPanel.getWidth(), centerPanel.getHeight());
		this.view.updateUI();
	}

}


