/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Ian Naval
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;

/**
 * A dummy module to demonstrate the Janeway client
 * 
 */
public class PlanningPokerModule implements IJanewayModule {

	/** The tabs used by this module */
	private ArrayList<JanewayTabModel> tabs;
	private String name;

	/**
	 * Construct a new PlanningPokerModule for demonstration purposes
	 */
	public PlanningPokerModule() {

		// Setup button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		// Setup the main panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(new JLabel(this.name), BorderLayout.PAGE_START);

		tabs = new ArrayList<JanewayTabModel>();
		JanewayTabModel tab = new JanewayTabModel(this.name, new ImageIcon(),
				buttonPanel, mainPanel);
		tabs.add(tab);
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
	 */
	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}
}
