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

import java.awt.Component;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.OverviewPanel;

public class MainView extends ClosableFixedLengthTab {

	/**
	 * Serializable ID
	 */
	private static final long serialVersionUID = 4184001083813964646L;
	private OverviewPanel overviewPanel;

	/**
	 * Create the panel.
	 */

	public MainView() {		
		// Put all tabs in a scroll layout
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		
		overviewPanel = new OverviewPanel();
		this.addTab("Session Overview", overviewPanel);
	}
	
	/**
	 * Overridden insertTab function to add the closable tab element.
	 * 
	 * @param title	Title of the tab
	 * @param icon	Icon for the tab
	 * @param component	The tab
	 * @param tip	Showing mouse tip when hovering over tab
	 * @param index	Location of the tab
	 */
	@Override
	public void insertTab(String title, Icon icon, Component component,
			String tip, int index) {
		super.insertTab(title, icon, component, tip, index);
		if (!(component instanceof OverviewPanel)) {
			setTabComponentAt(index, new ClosableTabComponent(this));
		}
	}
}
