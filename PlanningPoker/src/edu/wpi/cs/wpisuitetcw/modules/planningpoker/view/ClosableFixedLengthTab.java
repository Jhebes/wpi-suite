package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

public class ClosableFixedLengthTab extends JTabbedPane {

	/**
	 * This long is required for class serialization
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The max length of text within a tab
	 */
	private static final int MAX_TITLE_LENGTH = 15;
	
	/**
	 * The symbol to use when a tab text is longer than MAX_TITLE_LENGTH
	 */
	private static final String OVERFLOW_SYMBOL = "...";
	
	/**
	 * Check length of text within a tab. It it is too long, cut it off
	 * and add overflow symbol to the end.
	 */
	@Override
	public void addTab(String title, Icon icon, Component component, String tip) {
		title = ClosableFixedLengthTab.limitTitle(title);
		super.addTab(title, icon, component, tip);

	}
	
	/**
	 * Limits the size to max title length.
	 * @param title The title to limit
	 * @return The limited title, if it needs to be shortened
	 */
	public static String limitTitle(String title) {
		if (title.length() > MAX_TITLE_LENGTH) {
			title = title.substring(0, MAX_TITLE_LENGTH - OVERFLOW_SYMBOL.length()) + OVERFLOW_SYMBOL;
		}
		return title;
	}

}
