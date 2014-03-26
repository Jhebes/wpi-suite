/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

/**
 * @author troyling, Jake, Zack
 * 
 */
public class OverviewPanel extends JSplitPane {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel rightPanel;
	private final JPanel leftPanel;

	public OverviewPanel() {
		rightPanel = new JPanel();
		leftPanel = new JPanel();

		// list of sessions
		String dummySessions[] = { "Session 1", "Session 2", "Session 3",
				"Session 4", "Session 5", "Session 6", "Session 7",
				"Session 8", "Session 9", "Session 10", "Session 11",
				"Session 12", "Session 13", "Session 14", "Session 15",
				"Session 16", "Session 17", "Session 18", "Session 19",
				"Session 20", "Session 21", "Session 22", "Session 23",
				"Session 24", "Session 25", "Session 26", "Session 27",
				"Session 28", "Session 29", "Session 30", "Session 31",
				"Session 32", "Session 33", "Session 34", "Session 35",
				"Session 36", "Session 37", "Session 38", "Session 39",
				"Session 40", "Session 41", "Session 42", "Session 43",
				"Session 44", "Session 45", "Session 46", "Session 47",
				"Session 48", "Session 49", "Session 50", "Session 51",
				"Session 52", "Session 53", "Session 54", "Session 55",
				"Session 56", "Session 57", "Session 58"};

		// Create the list of existing sessions
		JList<String> existingSessionsList = new JList<String>(dummySessions);

		leftPanel.add(existingSessionsList);
		
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.add(Box.createHorizontalStrut(10));

		
		// Right Panel
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		JLabel overviewLabel = new JLabel("Overview");
		overviewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		rightPanel.add(overviewLabel);
		rightPanel.add(Box.createVerticalStrut(500));
		
		JButton joinButton = new JButton("Join");
		joinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		rightPanel.add(joinButton);

		this.setRightComponent(rightPanel);
		this.setLeftComponent(leftPanel);
		this.setDividerLocation(180);

		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
		JScrollPane jsp = new JScrollPane(leftPanel, v, h);
		this.add(jsp);

	}
}
