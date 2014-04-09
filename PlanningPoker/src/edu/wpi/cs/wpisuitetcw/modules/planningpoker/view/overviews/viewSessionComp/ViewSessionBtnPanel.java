/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session.ActivateSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionPanel;

/**
 * @author troyling
 *
 */
public class ViewSessionBtnPanel extends JPanel{
	private final JButton activateBtn;
	private final JButton addBtn;
	private final ViewSessionPanel parentPanel;
	
	
	public ViewSessionBtnPanel(ViewSessionPanel parentPanel) {
		this.parentPanel = parentPanel;
		
		// set up buttons
		this.addBtn = new JButton("Add Requirements");
		this.activateBtn = new JButton("Activate Session");
		
		activateBtn.addActionListener(new ActivateSessionController(parentPanel, parentPanel.getPPSession()));
		
		// add buttons
		this.add(this.addBtn);
		this.add(this.activateBtn);
	}
	

}
