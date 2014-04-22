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
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.OverviewTable;

/**
 * Panel for exporting requirements into a JSON file.
 */
public class ExportPanel extends JPanel {

	private final JButton exportButton = new JButton("Export");
	private OverviewTable overviewTable;

	final JFileChooser fc = new JsonFileChooser();

	/**
	 * UID for serialization.
	 */
	private static final long serialVersionUID = 607828181423071068L;

	/**
	 * Constructor for the export requirements panel.
	 */
	public ExportPanel() {
		this.buildLayout();
	}

	/**
	 * Builds the layout of the panel.
	 */
	private void buildLayout() {

		String[] columnNames = { "ID", "Name", "Release #", "Iteration", "Type", "Status", "Priority", "Estimate" };
		Object[][] data = {};

		overviewTable = new OverviewTable(data, columnNames, true);
		overviewTable.refresh();

		JScrollPane tablePanel = new JScrollPane(overviewTable);

		overviewTable.getColumnModel().getColumn(0).setMaxWidth(40); // ID

		overviewTable.getColumnModel().getColumn(1).setMinWidth(250); // Name

		overviewTable.getColumnModel().getColumn(2).setMinWidth(90); // Release

		overviewTable.getColumnModel().getColumn(3).setMinWidth(90); // Iteration

		overviewTable.getColumnModel().getColumn(4).setMinWidth(105); // Type
		overviewTable.getColumnModel().getColumn(4).setMaxWidth(105); // Type

		overviewTable.getColumnModel().getColumn(5).setMinWidth(85); // Status
		overviewTable.getColumnModel().getColumn(5).setMaxWidth(85); // Status

		overviewTable.getColumnModel().getColumn(6).setMinWidth(75); // Priority
		overviewTable.getColumnModel().getColumn(6).setMaxWidth(75); // Priority

		overviewTable.getColumnModel().getColumn(7).setMinWidth(75); // Estimate
		overviewTable.getColumnModel().getColumn(7).setMaxWidth(75); // Estimate

		exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final int returnVal = fc.showSaveDialog(ExportPanel.this);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					final File file = fc.getSelectedFile();
					Logger.getLogger("RequirementManger").log(
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

						ViewEventController.getInstance().removeTab(ExportPanel.this);
					} catch (IOException ex) {
						Logger.getLogger("RequirementManager").log(
								Level.WARNING, "Could not write to file.", ex);
					}
				} else {
					Logger.getLogger("RequirementManger").log(
							Level.INFO, "Export command cancelled by user.");
				}
			}

		});

		this.setLayout(new MigLayout("fill"));
		this.add(new JLabel("Click and drag to select multiple requirements."), "wrap");
		this.add(tablePanel, "grow, wrap");
		this.add(exportButton);
	}
}
