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

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionReqPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;

/**
 * Panel for adding requirements to a planning poker session.
 */
public class SessionRequirementPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		pkgPanel = new ViewSessionReqPanel(this, session);
		
		this.setLayout(new MigLayout());
		this.add(pkgPanel, "center");
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
