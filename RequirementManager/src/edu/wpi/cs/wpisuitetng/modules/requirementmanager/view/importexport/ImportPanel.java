/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.importexport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.controller.AddRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;

/**
 * Panel for exporting requirements into a JSON file.
 */
public class ImportPanel extends JPanel {

	private static final JButton importButton = new JButton("Import");
	final JFileChooser fc = new JsonFileChooser();

	/**
	 * UID for serialization.
	 */
	private static final long serialVersionUID = -4529669314739245277L;

	/**
	 * Constructor for the export requirements panel.
	 */
	public ImportPanel() {
		this.buildLayout();
	}

	/**
	 * Builds the layout of the panel.
	 */
	private void buildLayout() {
		this.setLayout(new MigLayout("fill"));
		this.add(new JLabel("Select requirements to import from a file."), "wrap");

		importButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// In response to a button click:
				final int returnVal = fc.showOpenDialog(ImportPanel.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = fc.getSelectedFile();
					// This is where a real application would open the file.
					Logger.getLogger("RequirementManger").log(Level.INFO, "Exporting to: " + file.getName() + ".");

					try {
						final String data = new String(Files.readAllBytes(file.toPath()));
						final Requirement[] newRequirements = Requirement.fromJsonArray(data);

						for (Requirement neqRequirement : newRequirements) {
							AddRequirementController.getInstance().addRequirement(neqRequirement);
							
						}
						RequirementModel.getInstance().addRequirements(newRequirements);

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

		this.add(importButton);
	}

}
