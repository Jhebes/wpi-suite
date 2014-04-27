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

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.AddRequirementPanel;

public class ViewSessionInfoPanel extends JPanel {
	private final PlanningPokerSession session;
	private final AddRequirementPanel parentPanel;
	
	public ViewSessionInfoPanel(AddRequirementPanel parentPanel, PlanningPokerSession session) {
		this.parentPanel = parentPanel;
		this.session = session;
		
		// create labels for data field;
		final JLabel labelName = new JLabel("Name ");
		final JLabel labelSessionName = new JLabel(this.session.getName());
		
		this.add(labelName);
		this.add(labelSessionName);
		
	}
	
}
