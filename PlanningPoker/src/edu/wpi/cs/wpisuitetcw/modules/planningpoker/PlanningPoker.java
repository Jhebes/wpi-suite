/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.plaf.FontUIResource;

import com.lowagie.text.Font;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;

public class PlanningPoker implements IJanewayModule {
	private List<JanewayTabModel> tabs;

	//Define the standard font that will be enforced by default throughout the project
	public static final FontUIResource defaultLabelFont = new FontUIResource("Serif",Font.NORMAL,12);
	
	//Define the standard font that will be enforced by default throughout the project
	public static final FontUIResource defaultButtonFont = new FontUIResource("Serif",Font.ITALIC,12);
	
	//Define the standard font that will be enforced by default throughout the project
	public static final FontUIResource defaultHeaderFont = new FontUIResource("Serif",Font.ITALIC,25);
	
	
	public PlanningPoker() {
		tabs = new ArrayList<JanewayTabModel>();

		// toolbar panel
		final ToolbarView toolbarPanel = new ToolbarView();

		// main view
		final MainView mainview = new MainView();

		// add toolbar and mainview to the ViewEventController instance
		ViewEventManager.getInstance().setToolBar(toolbarPanel);
		ViewEventManager.getInstance().setMainView(mainview);

		// add this to tab
		final JanewayTabModel tab1 = new JanewayTabModel(getName(), new ImageIcon(),
				toolbarPanel, mainview);

		// add the tab
		tabs.add(tab1);
	}

	@Override
	public String getName() {
		return "PlanningPoker";
	}

	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}
}
