/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;

/**
 * @author troyling, Jenny
 *
 */
public class ViewSessionTableModel extends DefaultTableModel{
	private final String[] colNames = {"Name", "Description"};
	
	public ViewSessionTableModel() {
		setColumnIdentifiers(colNames);
	}
	
	/**
	 * Refreshes the requirements.
	 * 
	 * @param sessions
	 *            The new list of requirements
	 */
	public void refreshRequirements(List<PlanningPokerRequirement> requirements) {

		this.setDataVector(null, colNames);
		for (PlanningPokerRequirement requirement : requirements) {			
			Object[] row = { 
					requirement.getName(),
					requirement.getDescription()
			};
			this.addRow(row);
		}
	}
}



