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

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.longpoll.LongPollingClient;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.MainView;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ToolbarView;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.KeyboardShortcut;
import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;

/**
 * Module for Janeway.
 */
public class PlanningPoker implements IJanewayModule {
	private List<JanewayTabModel> tabs;
	private final LongPollingClient longPollingClient;

	/**
	 * Constructor for the module.
	 */
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
		
		// add keyboard shortcuts to planning poker tab
		registerKeyboardShortcuts(tab1);

		longPollingClient = new LongPollingClient();
		longPollingClient.startThread();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return "PlanningPoker";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}
	
	@SuppressWarnings("serial")
	private void registerKeyboardShortcuts(JanewayTabModel tab) {
		// control + tab: switch to right tab
		tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("F1"), new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventManager.getInstance().showTutorial();
			}
		}));
		//CTRL-N Should open a new pp session
		tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("control N"), new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventManager.getInstance().createSession();
			}
		}));
		
		// control + tab: switch to right tab
				tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("control TAB"), new AbstractAction() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ViewEventManager.getInstance().switchToRightTab();
					}
				}));
				
				// control + shift + tab: switch to left tab
				tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("control shift TAB"), new AbstractAction() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ViewEventManager.getInstance().switchToLeftTab();
					}
				}));
	}
}
