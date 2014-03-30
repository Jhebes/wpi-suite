/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.BorderLayout;
import java.awt.Component;
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
	 * 4	 */
	private static final long serialVersionUID = 1L;
	private final JPanel rightPanel;
	private final JPanel leftPanel;
	private final JButton openSessionBtn;
	private final JButton closedSessionBtn;
	private final JButton allSessionsBtn;
	private final Dimension buttonDimension;

	public OverviewPanel() {
		rightPanel = new JPanel();
		leftPanel = new JPanel();
		buttonDimension = new Dimension(100, 50);
		

		// Right PrightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		JLabel overviewLabel = new JLabel("Overview");
		overviewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		rightPanel.add(overviewLabel, BorderLayout.CENTER);
		rightPanel.add(Box.createVerticalStrut(500));
		
		JButton joinButton = new JButton("Join");
		joinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		rightPanel.add(joinButton);
		
		
		this.leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		this.openSessionBtn = new JButton("Open Sessions");
		this.closedSessionBtn = new JButton("Closed Sessions");
		this.allSessionsBtn = new JButton("All Sessions");
		
		this.openSessionBtn.setPreferredSize(buttonDimension);
		this.closedSessionBtn.setPreferredSize(buttonDimension);
		this.allSessionsBtn.setPreferredSize(buttonDimension);
		
		this.leftPanel.add(openSessionBtn, BorderLayout.NORTH);
		this.leftPanel.add(closedSessionBtn, BorderLayout.CENTER);
		this.leftPanel.add(allSessionsBtn, BorderLayout.SOUTH);
		
	
		
		this.leftPanel.add(Box.createHorizontalStrut(50));

		
		
		
		
		
		
		
		
		this.setRightComponent(rightPanel);
		this.setLeftComponent(leftPanel);
		this.setDividerLocation(180);

		int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
		int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
		JScrollPane jsp = new JScrollPane(leftPanel, v, h);
		this.add(jsp);

	}

	public JButton getOpenSessionBtn() {
		return openSessionBtn;
	}

	public JButton getClosedSessionBtn() {
		return closedSessionBtn;
	}

	public JButton getAllSessionsBtn() {
		return allSessionsBtn;
	}
}
