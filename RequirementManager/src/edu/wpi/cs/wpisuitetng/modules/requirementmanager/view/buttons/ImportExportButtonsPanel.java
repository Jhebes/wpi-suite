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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.AddRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.UpdateRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.importexport.JsonFileChooser;

/**
 * Group of buttons related to importing and exporting requirements.
 */
public class ImportExportButtonsPanel extends ToolbarGroupView {

	/**
	 * Random number for serialization reasons.
	 */
	private static final long serialVersionUID = -8151853089564675130L;

	private final JPanel contentPanel = new JPanel();
	private final JButton importButton = new JButton("<html>Import<br />Requirements</html>");
	private final JButton exportButton = new JButton("<html>Export<br />Requirements</html>");
	private static final JFileChooser fc = new JsonFileChooser(); 

	/**
	 * Constructs the import requirements button panel.
	 */
	public ImportExportButtonsPanel() {
		super("");

		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(390);

		try {
			final Image importImage = ImageIO.read(getClass().getResource("import.png"));
			importButton.setIcon(new ImageIcon(importImage));

			final Image exportImage = ImageIO.read(getClass().getResource("export.png"));
			exportButton.setIcon(new ImageIcon(exportImage));

		} catch (IOException e) {
			Logger.getLogger("RequirementManager").log(Level.SEVERE,
					"Error loading icons for import/export button panel.", e);
		}

		importButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final int returnVal = fc.showOpenDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = fc.getSelectedFile();
					Logger.getLogger("RequirementManger").log(Level.INFO, "Exporting to: " + file.getName() + ".");

					try {
						final String data = new String(Files.readAllBytes(file.toPath()));
						final Requirement[] newRequirements = Requirement.fromJsonArray(data);
						
						final List<Requirement> requirements = RequirementModel.getInstance().getRequirements();
						final List<Integer> requirementIDs = new ArrayList<Integer>();
						for (Requirement requirement : requirements) {
							requirementIDs.add(requirement.getId());
						}

						for (Requirement newRequirement : newRequirements) {
							if (requirementIDs.contains(newRequirement.getId())) {
								UpdateRequirementController.getInstance().updateRequirement(newRequirement);
								RequirementModel.getInstance().getRequirement(
										newRequirement.getId()).copyFrom(newRequirement);
							} else {
								AddRequirementController.getInstance().addRequirement(newRequirement);
								RequirementModel.getInstance().addRequirement(newRequirement);
							}
							
						}

						ViewEventController.getInstance().refreshTable();
						ViewEventController.getInstance().refreshTree();

					} catch (IOException ex) {
						Logger.getLogger("RequirementManager").log(Level.WARNING, "Could not write to file.", ex);
					}
				} else {
					Logger.getLogger("RequirementManger").log(Level.INFO, "Export command cancelled by user.");
				}
			}

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
