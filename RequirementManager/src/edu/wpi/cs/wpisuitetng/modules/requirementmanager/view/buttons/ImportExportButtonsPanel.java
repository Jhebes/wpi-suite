/**
 *  * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 */
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.buttons;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

/**
 * Group of buttons related to importing and exporting requirements.
 */
public class ImportExportButtonsPanel extends ToolbarGroupView {

	/**
	 * Random number for seriailzation reasons.
	 */
	private static final long serialVersionUID = -8151853089564675130L;

	private final JPanel contentPanel = new JPanel();
	final JButton importButton = new JButton("<html>Import<br />Requirements</html>");
	final JButton exportButton = new JButton("<html>Export<br />Requirements</html>");

	/**
	 * Constructs the import requirements button panel.
	 */
	public ImportExportButtonsPanel() {
		super("");

		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(390);

		try {
			final Image importImage = ImageIO.read(getClass().getResource("cancel.png"));
			importButton.setIcon(new ImageIcon(importImage));

			final Image exportImage = ImageIO.read(getClass().getResource("edit.png"));
			exportButton.setIcon(new ImageIcon(exportImage));

		} catch (IOException e) {
			Logger.getLogger("RequirementManager").log(Level.SEVERE,
					"Error loading icons for import/export button panel.", e);
		}

		// the action listener for the Edit Estimates button
		importButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().openImportTab();
			}
			// }
		});

		exportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventController.getInstance().openExportTab();
			}
		});
		contentPanel.add(importButton);
		contentPanel.add(exportButton);
		contentPanel.setOpaque(false);

		this.add(contentPanel);
	}
}
