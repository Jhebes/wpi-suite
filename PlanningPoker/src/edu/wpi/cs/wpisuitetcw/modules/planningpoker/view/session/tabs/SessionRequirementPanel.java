/******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.tabs;

import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionBtnPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionInfoPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionReqPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;

/**
 * Panel for adding requirements to a planning poker session.
 */
public class SessionRequirementPanel extends JSplitPane {
	private final ViewSessionBtnPanel buttonPanel;
	private final ViewSessionInfoPanel infoPanel;
	private final ViewSessionReqPanel pkgPanel;
	private final PlanningPokerSession pPSession;

	/**
	 * Create a view session panel
	 * @param session The planning poker session for this panel.
	 */
	public SessionRequirementPanel(PlanningPokerSession session) {
		final RequirementTableManager manager = new RequirementTableManager();
		manager.fetch(session.getID());
		pPSession = session;
		infoPanel = new ViewSessionInfoPanel(this, session);
		buttonPanel = new ViewSessionBtnPanel(this);
		pkgPanel = new ViewSessionReqPanel(this, session);

		// set sub panels
		final JSplitPane contentPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				true, infoPanel, pkgPanel);
		contentPanel.setDividerLocation(180);
		contentPanel.setEnabled(false);
		this.setLayout(new BorderLayout());

		// set up the viewSessionPanel
		this.add(contentPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		SessionStash.getInstance().synchronize();
	}

	/**
	 * @return The buttons panel at the bottom
	 */
	public ViewSessionBtnPanel getButtonPanel() {
		return buttonPanel;
	}

	/**
	 * @return The information panel on the left
	 */
	public ViewSessionInfoPanel getInfoPanel() {
		return infoPanel;
	}

	/**
	 * @return The main panel in the beginning
	 */
	public ViewSessionReqPanel getPkgPanel() {
		return pkgPanel;
	}

	/**
	 * @return The planning poker session for this panel.
	 */
	public PlanningPokerSession getPPSession() {
		return pPSession;
	}

	
}
