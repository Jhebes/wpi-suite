/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.buttons.ImportRequirementsButtonsPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.buttons.SessionButtonsPanel;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.DefaultToolbarView;

/**
 * Sets up upper toolbar of PlanningPoker tab.
 * 
 */
public class ToolbarView extends DefaultToolbarView {
	private final SessionButtonsPanel sessionPanel = new SessionButtonsPanel();
	private final ImportRequirementsButtonsPanel importPanel = new ImportRequirementsButtonsPanel();

	/**
	 * creates and positions buttons in the upper toolbar
	 */
	public ToolbarView() {
		this.addGroup(sessionPanel);
		this.addGroup(importPanel);
		//this.setAlignmentX(alignmentX);
	}

	/**
	 * Method getSessionButtonsPanel.
	 * 
	 * @return SessionButtonsPanel
	 */
	public SessionButtonsPanel getSessionButtonsPanel() {
		return this.sessionPanel;
	}
	
	/**
	 * @return The import requirements buttons panel
	 */
	public ImportRequirementsButtonsPanel getImportButtonsPanel() {
		return this.importPanel;
	}

}
