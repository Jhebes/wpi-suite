/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.Dimension;

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
				"Session 1", "Session 2", "Session 3", "Session 1",
				"Session 2", "Session 3", "Session 1", "Session 2",
				"Session 3", "Session 1", "Session 2", "Session 3",
				"Session 1", "Session 2", "Session 3", "Session 1",
				"Session 2", "Session 3", "Session 1", "Session 2",
				"Session 3", "Session 1", "Session 2", "Session 3",
				"Session 1", "Session 2", "Session 3", "Session 1",
				"Session 2", "Session 3", "Session 1", "Session 2",
				"Session 3", "Session 1", "Session 2", "Session 3",
				"Session 1", "Session 2", "Session 3", "Session 1",
				"Session 2", "Session 3", "Session 1", "Session 2",
				"Session 3", "Session 1", "Session 2", "Session 3",
				"Session 1", "Session 2", "Session 3", "Session 1",
				"Session 2", "Session 3", "Session 1", "Session 2",
				"Session 3", "Session 1", "Session 2", "Session 3",
				"Session 1", "Session 2", "Session 3", "Session 1",
				"Session 2", "Session 3", "Session 1", "Session 2",
				"Session 3", "Session 1", "Session 2", "Session 3",
				"Session 1", "Session 2", "Session 3", "Session 1",
				"Session 2", "Session 3", "Session 1", "Session 2",
				"Session 3", "Session 1", "Session 2", "Session 3",
				"Session 1", "Session 2", "Session 3", "Session 1",
				"Session 2", "Session 3", "Session 1", "Session 2",
				"Session 3", "Session 1", "Session 2", "Session 3",
				"Session 1", "Session 2", "Session 3", "Session 1",
				"Session 2", "Session 3", "Session 1", "Session 2",
				"Session 3", "Session 1", "Session 2", "Session 3",
				"Session 1", "Session 2", "Session 3", "Session 1",
				"Session 2", "Session 3", "Session 1", "Session 2", "Session 3" };

		// Create the list of existing sessions
		JList<String> existingSessionsList = new JList<String>(dummySessions);

		leftPanel.add(existingSessionsList);
		leftPanel.add(new JButton("Join"));
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.add(Box.createHorizontalStrut(10));

		rightPanel.add(new JLabel("Test Label"));

		this.setRightComponent(rightPanel);
		this.setLeftComponent(leftPanel);
		this.setDividerLocation(180);

		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
		JScrollPane jsp = new JScrollPane(leftPanel, v, h);
		this.add(jsp);

	}
}
