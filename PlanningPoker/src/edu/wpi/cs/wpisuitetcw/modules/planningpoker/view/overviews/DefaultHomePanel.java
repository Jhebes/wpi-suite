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

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
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
	private final Border compound, paneEdgeBorder, grayAndDarkGrayBorder;

	public DefaultHomePanel() {

		// Default Home Panel's borders
		paneEdgeBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		grayAndDarkGrayBorder = BorderFactory.createEtchedBorder(
				Color.lightGray, Color.darkGray);

		// Combines borders, grayAndDarkBorder within 10 pixels of padding
		compound = BorderFactory.createCompoundBorder(paneEdgeBorder,
				grayAndDarkGrayBorder);

		// sets the panel border
		this.setBorder(compound);

		JTextPane leftTextPane = createLeftTextPane();
		JTextPane rightTextPane = createRightTextPane();
		JTextPane topTextPane = createTopTextPane();

		// Cannot edit text panes
		leftTextPane.setEditable(false);
		rightTextPane.setEditable(false);
		topTextPane.setEditable(false);

		// Makes text panes opaque
		leftTextPane.setOpaque(false);
		rightTextPane.setOpaque(false);
		topTextPane.setOpaque(false);

		// Makes containers for top and bottom halves
		JPanel topContainer = new JPanel();
		JSplitPane container = new JSplitPane();

		// Gets rid of SplitPane Border
		container.setBorder(null);

		// setup the SplitPane layout
		container.setLeftComponent(leftTextPane);
		container.setRightComponent(rightTextPane);
		container.setDividerSize(0);
		container.setResizeWeight(.5);
		container.setEnabled(true);

		// Add welcome message to top panel
		topContainer.add(topTextPane);

		// Set to make layout and adds welcome message to top and splitpane to
		// bottom
		this.setLayout(new MigLayout());
		this.add(topContainer, "dock north");
		this.add(container);
	}

	private JTextPane createTopTextPane() {

		String[] TopPaneText = { "Welcome to Planning Poker!" };
		String[] initStyles = { "super huge" };

		JTextPane textPane = new JTextPane();
		StyledDocument doc = textPane.getStyledDocument();
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

		String[] leftPaneText = {
				"What is Planning Poker?", // large
				"\n\n   Planning Poker is a software development tool for estimating "
						+ "requirements in software development projects. In Planning Poker, "
						+ "each group member has a deck of cards to rank the requirements "
						+ "of a project according to how important they think that task "
						+ "ranks among the others. The project administrators can then "
						+ "use this data to create a final priority list for the project."
						+ "\n", // small
				"\nHow To Get Started", // large
				"\n   To start a planning poker session, click create a session and "
						+ "assign a type of deck to the session. You can either use the "
						+ "default deck, or create your own deck. Once a session has been created, "
						+ "you can select what requirements you would like to add to the session "
						+ "or create a requirement of your own. Now it's time to start the voting!\n", // small
		};

		String[] initStyles = { "large", "small", "large", "small" };

		JTextPane textPane = new JTextPane();
		StyledDocument doc = textPane.getStyledDocument();
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

	private JTextPane createRightTextPane() {

		String[] rightPaneText = {
				"Frequently Asked Questions", // large
				"\n\nWho can view my vote?", // medium
				"\n Once your vote is submitted, your vote remains anonymous and is only"
						+ " used for the calculation of the final estimation of a requirement.", // small
				"\n\nWhat is the difference between cancelling and ending a session?", // medium
				"\n   When a session ends due to the deadline being reached or the administrator"
						+ " manually ending a session, a final estimation is calculated, while cancelling "
						+ "a session does not generate a final estimation.", // small
				"", // medium
				"" // small
		};

		String[] initStyles = { "large", "medium", "small", "medium", "small",
				"medium", "small" };

		JTextPane textPane = new JTextPane();
		StyledDocument doc = textPane.getStyledDocument();
		addStylesToDocument(doc);

		try {
			for (int i = 0; i < rightPaneText.length; i++) {
				doc.insertString(doc.getLength(), rightPaneText[i],
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

		Style s = doc.addStyle("italic", regular);
		StyleConstants.setItalic(s, true);
		//
		s = doc.addStyle("bold", regular);
		StyleConstants.setBold(s, true);

		s = doc.addStyle("small", regular);
		StyleConstants.setFontSize(s, 19);

		s = doc.addStyle("medium", regular);
		StyleConstants.setFontSize(s, 20);
		StyleConstants.setBold(s, true);

		s = doc.addStyle("large", Georgia);
		StyleConstants.setFontSize(s, 24);
		StyleConstants.setBold(s, true);

		s = doc.addStyle("super huge", Georgia);
		StyleConstants.setFontSize(s, 34);
		StyleConstants.setBold(s, true);
	}
}
