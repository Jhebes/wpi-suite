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
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 * @author Jenny12593
 * 
 */
public class DefaultHomePanel extends JSplitPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Border compound, paneEdgeBorder, grayAndDarkGrayBorder;
	private final String WHATISPPTITLE = "What is Planning Poker?";
	private final String WHATISPPDESCRIPTION = "This will describe what planning poker is";
	private final String TOGETSTARTEDTITLE = "To Get Started,";
	private final String CREATESESSIONSTRING = "Create Session.";
	private final String OPENSESSIONSTRING = "Open Sessions.";
	private final String ALLSESSIONSSTRING = "All Sessions.";
	private final String CLOSEDSESSIONSTRING = "Closed Sessions";
	private final String WELCOMESTRING = "Welcome to Planning Poker!\n";

	//
	// + "To get started, click ".concat(CREATESESSIONSTRING)
	// + "\nTo view all the sessions click on ".concat(ALLSESSIONSSTRING)
	// + "\nTo view open sessions click on ".concat(OPENSESSIONSTRING)
	// + "\nTo view closed sessions click on ".concat(CLOSEDSESSIONSTRING);

	public DefaultHomePanel() {

		// // Sets to flow layout
		// this.setLayout(new FlowLayout());

		JTextPane leftTextPane = createLeftTextPane();
		JTextPane rightTextPane = createRightTextPane();

		leftTextPane.setEditable(false);
		rightTextPane.setEditable(false);

		// setup the layout
		this.setLeftComponent(leftTextPane);
		this.setRightComponent(rightTextPane);
		this.setDividerSize(0);
		this.setResizeWeight(.5);
		this.setEnabled(true);

		leftTextPane.setOpaque(false);
		rightTextPane.setOpaque(false);

		// //set sizes of JTextPanes
		// leftTextPane.setBounds( 100, 100, 50, 50 );
		// rightTextPane.setBounds(10, 10, 200, 200);

		//
		// this.setPreferredSize(new Dimension( 600, 600 ));

		// // Adds left and right Panes in Flow Layout
		// this.add(leftTextPane);
		// this.add(rightTextPane);

		// A border that puts 10 extra pixels at the sides and a gray and blue
		// border
		paneEdgeBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		grayAndDarkGrayBorder = BorderFactory.createEtchedBorder(
				Color.lightGray, Color.darkGray);

		// Combines borders
		compound = BorderFactory.createCompoundBorder(paneEdgeBorder,
				grayAndDarkGrayBorder);

		// sets the panel border
		this.setBorder(compound);

	}

	// Welcome text area
	// JTextArea welcomeTextBox = new JTextArea(5, 30);

	// Font plainFont = new Font("Serif", Font.PLAIN, 36);
	// Font boldFont = new Font("Serif", Font.BOLD, 36);

	// text color
	// welcomeTextBox.setForeground(new Color(0x061692));

	// welcomeTextBox.setFont(plainFont);
	// welcomeTextBox.setText(WELCOMESTRING);
	// welcomeTextBox.setWrapStyleWord(true);
	// welcomeTextBox.setLineWrap(true);
	// welcomeTextBox.setBorder(BorderFactory.createEmptyBorder());
	// welcomeTextBox.setOpaque(false);
	// welcomeTextBox.setFocusable(false);
	// welcomeTextBox.setEditable(false);

	// add the text area to the home panel
	// this.add(welcomeTextBox);

	private JTextPane createLeftTextPane() {

		String[] leftPaneText = {
				"       Welcome to Planning Poker!", // super huge
				"\n\n" + "What is Planning Poker?", // large
				"\n  Planning Poker is a software development tool for estimating "
						+ "requirements in software development projects. In Planning Poker,"
						+ "each group member has a deck of cards to rank the requirements "
						+ "of a project according to how important they think that task "
						+ "ranks among the others. The project administrators can then "
						+ "use this data to create a final priority list for the project."
						+ "", // regular
		};

		String[] initStyles = { "super huge", "large", "regular" };

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

		String[] rightPaneText = { "\n", // super huge
				"\nTHIS COLUMN WILL BE FILLED WITH QUESTIONS", // large
				"\nYo, add more stuff", // regular
		};

		String[] initStyles = { "super huge", "large", "regular" };

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
		StyleConstants.setFontSize(s, 20);

		s = doc.addStyle("large", Georgia);
		StyleConstants.setFontSize(s, 24);
		StyleConstants.setBold(s, true);

		s = doc.addStyle("super huge", Georgia);
		StyleConstants.setBold(s, true);
		StyleConstants.setFontSize(s, 30);

		// s = doc.addStyle("icon", regular);
		// StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);
		// ImageIcon pigIcon = createImageIcon("images/Pig.gif",
		// "a cute pig");
		// if (pigIcon != null) {
		// StyleConstants.setIcon(s, pigIcon);
		// }
		//

	}
}
