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

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.tabs.SessionRequirementPanel;

/**
 * The panel for displaying info about the planning poker session.
 */
public class ViewSessionInfoPanel extends JPanel {
	private final PlanningPokerSession session;
	private final SessionRequirementPanel parentPanel;
	
	public ViewSessionInfoPanel(SessionRequirementPanel parentPanel, PlanningPokerSession session) {
		this.parentPanel = parentPanel;
		this.session = session;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// create labels for data field;
		final JLabel labelName = new JLabel("Name ");
		final JTextArea labelSessionName = new JTextArea(this.session.getName());
		labelSessionName.setLineWrap(true);
		labelSessionName.setEditable(false);
		labelSessionName.setWrapStyleWord(false);
		labelSessionName.setBackground(Color.LIGHT_GRAY);
		

		
		this.add(labelName);
		this.add(labelSessionName);
		
	}
	
}
