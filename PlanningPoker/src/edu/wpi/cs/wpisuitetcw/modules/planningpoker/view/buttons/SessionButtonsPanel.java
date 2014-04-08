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
import java.io.FileInputStream;
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
	private final JPanel sessionPanel = new JPanel();
	private final JButton createSession;
	private final JButton joinSession;

	public SessionButtonsPanel() {
		super("");

		// set up session panel
		this.sessionPanel.setLayout(new BoxLayout(sessionPanel,
				BoxLayout.X_AXIS));

		// create the buttons
		this.createSession = new JButton("<html>Create<br />Session</html>");
		// TODO add button listeners to the createSession button

		this.joinSession = new JButton("<html>Join<br />Session</html>");
		// TODO add button listeners to the joinSession button
		// TODO add the image later
		try {
			Image img = ImageIO.read(getClass().getResource("new_session.png"));
			System.out.println("ICON IMAGE: " + img.toString());
			createSession.setIcon(new ImageIcon(img));
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

		// Hide joinSessions button for now...
		joinSession.setVisible(false);
		// createSession.setPreferredSize(new
		// Dimension(createSession.getHeight(), 50));

		createSession.setAlignmentX(CENTER_ALIGNMENT);

		sessionPanel.add(Box.createHorizontalStrut(75));

		sessionPanel.add(this.createSession);
		// sessionPanel.add(Box.createHorizontalStrut(125));
		// sessionPanel.add(this.joinSession);
		sessionPanel.setOpaque(false);

		sessionPanel.add(Box.createHorizontalStrut(75));

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
	public JButton getJoinSessionButton() {
		return this.joinSession;
	}
}
