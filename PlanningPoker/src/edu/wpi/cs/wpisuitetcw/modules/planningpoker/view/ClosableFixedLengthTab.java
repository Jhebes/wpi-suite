package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

public class ClosableFixedLengthTab extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClosableFixedLengthTab() {
		super();
	}
	
	@Override 
	public void addTab(String title, Icon icon, Component component, String tip) {
		String t = "HAHA";
		super.addTab(t, icon, component, tip);
		
	}
	
}
