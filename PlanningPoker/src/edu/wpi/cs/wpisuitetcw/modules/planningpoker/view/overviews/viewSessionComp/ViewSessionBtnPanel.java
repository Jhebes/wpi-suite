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

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.session.ActivateSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.AddRequirementPanel;

public class ViewSessionBtnPanel extends JPanel {
	private static final long serialVersionUID = -5483659811074321821L;
	private final JButton activateBtn;
	private final AddRequirementPanel parentPanel;

	public ViewSessionBtnPanel(AddRequirementPanel parentPanel) {
		this.parentPanel = parentPanel;

		// set up button
		this.activateBtn = new JButton("Activate Session");

		activateBtn.addActionListener(new ActivateSessionController(
				parentPanel, parentPanel.getPPSession()));

		// add button
		this.add(this.activateBtn);
	}

	public AddRequirementPanel getParentPanel() {
		return parentPanel;
	}	
	
	/**
	 * @return This panel's activate session button
	 */
	public JButton getActivateBtn() {
		return activateBtn;
	}

}
