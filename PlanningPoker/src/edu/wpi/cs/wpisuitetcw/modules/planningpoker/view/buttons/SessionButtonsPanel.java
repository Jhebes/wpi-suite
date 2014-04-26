/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.buttons;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;

/**
 * Sets up Session button panel
 * 
 */
public class SessionButtonsPanel extends ToolbarGroupView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel sessionPanel = new JPanel();
	private final JButton createSession;
	private final JButton tutorialPanel;

	public SessionButtonsPanel() {
		super("");

		// set up session panel
		this.sessionPanel.setLayout(new BoxLayout(sessionPanel,
				BoxLayout.X_AXIS));

		// create the buttons
		this.createSession = new JButton("<html>Create<br />Session</html>");
		this.tutorialPanel = new JButton("<html>Help<br /></html>");

		// add image to buttons
		try {
			Image img = ImageIO.read(getClass().getResource("new_session.png"));
			createSession.setIcon(new ImageIcon(img));

			img = ImageIO.read(getClass().getResource("help.png"));
			tutorialPanel.setIcon(new ImageIcon(img));
		} catch (IOException ex) {
		}

		// action listener for the createSession button
		createSession.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// bring up a create session pane
				ViewEventManager.getInstance().createSession();
			}
		});

		tutorialPanel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventManager.getInstance().showTutorial();
			}
		});

		// setup the toolbar
		sessionPanel.add(this.createSession);
		sessionPanel.add(Box.createHorizontalStrut(10));
		sessionPanel.add(this.tutorialPanel);
		sessionPanel.setOpaque(false);

		this.add(sessionPanel);
	}

	/**
	 * Method getCreateSessionButton.
	 * 
	 * @return JButton
	 */
	public JButton getCreateSessionButton() {
		return this.createSession;
	}

	/**
	 * Method getJoinSessionButton.
	 * 
	 * @return JButton
	 */
	public JButton getTutorialPanelButton() {
		return this.tutorialPanel;
	}
}
