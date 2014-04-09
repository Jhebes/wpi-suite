/**
 * 
 */
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


