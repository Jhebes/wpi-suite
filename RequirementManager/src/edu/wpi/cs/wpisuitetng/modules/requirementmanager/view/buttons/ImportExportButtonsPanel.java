/**
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 */
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.buttons;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
import javax.swing.table.TableModel;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.importexport.JsonFileChooser;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.OverviewTable;

/**
 * Group of buttons related to importing and exporting requirements.
 */
public class ImportExportButtonsPanel extends ToolbarGroupView {

	/**
	 * Random number for serialization reasons.
	 */
	private static final long serialVersionUID = -8151853089564675130L;

	private final JPanel contentPanel = new JPanel();
	private final JButton importButton = new JButton("<html>Import</html>");
	private final JButton exportButton = new JButton("<html>Export</html>");
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
					Logger.getLogger("RequirementManager").log(Level.INFO, "Exporting to: " + file.getName() + ".");

					try {
						final String data = new String(Files.readAllBytes(file.toPath()));
						final Requirement[] newRequirements = Requirement.fromJsonArray(data);
						
						for (Requirement newRequirement : newRequirements) {
							newRequirement.setId(RequirementModel.getInstance().getNextID());
							try {
								newRequirement.setParentID(-1);
							} catch (Exception e1) {
								Logger.getLogger("RequirementManager").log(
										Level.SEVERE, "Error setting the parent ID to -1", e1);
							}
							newRequirement.getHistory().add("Requirement was imported.");
							newRequirement.setIteration("Backlog");
							newRequirement.setRelease("");
							newRequirement.setPriority(RequirementPriority.BLANK);
							newRequirement.setEstimate(0);
							RequirementModel.getInstance().addRequirement(newRequirement);
						}
					} catch (IOException ex) {
						Logger.getLogger("RequirementManager").log(Level.WARNING, "Could not write to file.", ex);
					}
				} else {
					Logger.getLogger("RequirementManager").log(Level.INFO, "Export command cancelled by user.");
				}
			}

		});
		
		final OverviewTable overviewTable = ViewEventController.getInstance().getOverviewTable();
		exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final int returnVal = fc.showSaveDialog(null);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = fc.getSelectedFile();
					Logger.getLogger("RequirementManager").log(
							Level.INFO, "Exporting to: " + file.getName() + ".");

					try {
						final Writer writer = new FileWriter(file);
						final TableModel tableModel = overviewTable.getTableModel();
						
						final int[] rows = overviewTable.getSelectedRows();
						
						final Gson gson = new Gson();
						
						final List<Requirement> requirements = new ArrayList<Requirement>();
						for (int row : rows) {
							final String rowIDstr = tableModel.getValueAt(row, 0).toString();
					    	final int rowID = Integer.parseInt(rowIDstr); 
					    	final Requirement req = RequirementModel.getInstance().getRequirement(rowID);
					    	requirements.add(req);
						}
						
						writer.write(gson.toJson(requirements));
						writer.close();
					} catch (IOException ex) {
						Logger.getLogger("RequirementManager").log(
								Level.WARNING, "Could not write to file.", ex);
					}
				} else {
					Logger.getLogger("RequirementManager").log(
							Level.INFO, "Export command cancelled by user.");
				}
			}

		});

		exportButton.setEnabled(false);
		contentPanel.add(importButton);
		contentPanel.add(exportButton);
		contentPanel.setOpaque(false);

		this.add(contentPanel);
	}

	public JButton getExportButton() {
		return exportButton;
	}
}
