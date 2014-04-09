/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;

/**
 * Sets up Import Requirements button panel
 * 
 */
public class ImportRequirementsButtonsPanel extends ToolbarGroupView {
	private final JPanel importRequirementsButtonsPanel = new JPanel();
	private final JButton importRequirementsButton;

	public ImportRequirementsButtonsPanel() {
		super("");

		// set up the panel
		this.importRequirementsButtonsPanel.setLayout(new BoxLayout(
				importRequirementsButtonsPanel, BoxLayout.X_AXIS));

		// create the buttons
		this.importRequirementsButton = new JButton("<html>Import<br />Requirements</html>");
		
		// action listener for the createSession button
		importRequirementsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// bring up a create session pane
				ViewEventManager.getInstance().createImportRequirementsPanel();
			}
		});

		importRequirementsButtonsPanel.setAlignmentX(CENTER_ALIGNMENT);

		importRequirementsButtonsPanel.add(Box.createHorizontalStrut(75));

		importRequirementsButtonsPanel.add(this.importRequirementsButton);
		importRequirementsButtonsPanel.setOpaque(false);

		importRequirementsButtonsPanel.add(Box.createHorizontalStrut(75));

		this.add(importRequirementsButtonsPanel);
	}
}
