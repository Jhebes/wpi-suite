/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import net.miginfocom.swing.MigLayout;

/**
 * @author Jenny12593
 * 
 */
public class DefaultHomePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DefaultHomePanel() {

		// Default Home Panel's borders
		final Border paneEdgeBorder = BorderFactory.createEmptyBorder(10, 10,
				10, 10);

		// sets the panel border
		this.setBorder(paneEdgeBorder);

		final JTextPane leftTextPane = createLeftTextPane();
		final JTextPane topTextPane = createTopTextPane();

		// Cannot edit text panes
		leftTextPane.setEditable(false);
		topTextPane.setEditable(false);

		// Makes text panes opaque
		leftTextPane.setOpaque(false);
		topTextPane.setOpaque(false);

		// Makes containers for top and bottom halves
		final JPanel topContainer = new JPanel();
		final JPanel centerPanel = new JPanel();
		
		final JPanel overallPanel = new JPanel();
		
		centerPanel.setLayout(new MigLayout());
		centerPanel.add(leftTextPane);
	
		// Add welcome message to top panel
		topContainer.add(topTextPane);

		// Set to make layout and adds welcome message to top and splitpane to
		// bottom
		overallPanel.setLayout(new MigLayout());
		overallPanel.add(topContainer, "dock north");
		overallPanel.add(centerPanel, "center");
		
		this.add(overallPanel);
	}

	private JTextPane createTopTextPane() {

		final String[] TopPaneText = { "Welcome to Planning Poker!" };
		final String[] initStyles = { "super huge" };

		final JTextPane textPane = new JTextPane();
		final StyledDocument doc = textPane.getStyledDocument();
		addStylesToDocument(doc);

		try {
			for (int i = 0; i < TopPaneText.length; i++) {
				doc.insertString(doc.getLength(), TopPaneText[i],
						doc.getStyle(initStyles[i]));
			}
		} catch (BadLocationException ble) {
			System.err.println("Couldn't insert initial text into text pane.");
		}
		return textPane;
	}

	private JTextPane createLeftTextPane() {

		final String[] leftPaneText = {
				"\nWhat is Planning Poker?", // large
				"\nPlanning Poker is a software development tool \nfor estimating "
						+ "requirements in software development \nprojects. In Planning Poker, "
						+ "each group member has \na deck of cards to rank the requirements "
						+ "of a project\naccording to how important they think that task "
						+ "ranks\namong the others. The project administrators can then "
						+ "use\nthis data to create a final priority list for the project. \n", // small
				"\n\nHow To Get Started", // large
				"\nTo start a planning poker session, click create a session and \n"
						+ "assign a type of deck to the session. You can either use the \n"
						+ "default deck, or create your own deck. Once a session has\nbeen created,"
						+ "you can select what requirements you would \nlike to add to the session"
						+ "or create a requirement of your \nown. Now it's time to start the voting!\n", // small
		};

		// set sytles for the text
		final String[] initStyles = { "large", "medium", "large", "medium" };

		final JTextPane textPane = new JTextPane();
		final StyledDocument doc = textPane.getStyledDocument();
		addStylesToDocument(doc);

		try {
			for (int i = 0; i < leftPaneText.length; i++) {
				doc.insertString(doc.getLength(), leftPaneText[i],
						doc.getStyle(initStyles[i]));
			}
		} catch (BadLocationException ble) {
			System.err.println("Couldn't insert initial text into text pane.");
		}
		return textPane;
	}

	protected void addStylesToDocument(StyledDocument doc) {
		// Initialize some styles.
		Style def = StyleContext.getDefaultStyleContext().getStyle(
				StyleContext.DEFAULT_STYLE);

		Style regular = doc.addStyle("regular", def);
		StyleConstants.setFontFamily(def, "SansSerif");

		Style Georgia = doc.addStyle("georgia", def);
		StyleConstants.setFontFamily(def, "Georgia");

		Style s;

		s = doc.addStyle("medium", regular);
		StyleConstants.setFontSize(s, 20);

		s = doc.addStyle("large", Georgia);
		StyleConstants.setFontSize(s, 24);
		StyleConstants.setBold(s, true);
		StyleConstants.setItalic(s, true);

		s = doc.addStyle("super huge", Georgia);
		StyleConstants.setFontSize(s, 38);
		StyleConstants.setBold(s, true);
	}
}
