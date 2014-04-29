package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view;

import java.awt.Component;
import java.awt.FontMetrics;

import javax.swing.Icon;
import javax.swing.JTabbedPane;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

public class ClosableFixedLengthTab extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int MAX_TAB_WIDTH = 140;
	private final int MAX_TITLE_LENGTH = 18;
	private final String OVERFLOW_SYMBOL = "...";
	private final String EMPTY_STRING = "                       ";

	public ClosableFixedLengthTab() {
		super();

		this.setUI(new MetalTabbedPaneUI() {
			@Override
			protected int calculateTabWidth(int tabPlacement, int tabIndex,
					FontMetrics metrics) {
				return MAX_TAB_WIDTH;
			}
		});
	}

	@Override
	public void addTab(String title, Icon icon, Component component, String tip) {
		if(title.length() > MAX_TITLE_LENGTH) {
			title = title.substring(0, MAX_TITLE_LENGTH - OVERFLOW_SYMBOL.length()) + OVERFLOW_SYMBOL;
		} else {
			title += EMPTY_STRING.substring(0,
					MAX_TITLE_LENGTH - title.length());
		}
		super.addTab(title, icon, component, tip);

	}

}
