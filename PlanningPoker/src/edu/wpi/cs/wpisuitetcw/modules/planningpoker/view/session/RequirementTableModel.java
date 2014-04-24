/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;

/**
 * RequirementTableModel is a model that store the name and description
 * of the PlanningPokerRequirements
 */
public class RequirementTableModel extends DefaultTableModel{
	private static final long serialVersionUID = -2776175314270450120L;
	
	/** First row */
	private final String[] colNames = {"Name", "Description"};
	
	/**
	 * Construct a RequirementTableModel by assign
	 * values to the first row, the column identifiers
	 */
	public RequirementTableModel() {
		setColumnIdentifiers(colNames);
	}
	
	/**
	 * Refreshes the requirements.
	 * 
	 * @param sessions
	 *            The new list of requirements
	 */
	public void refreshRequirements(List<PlanningPokerRequirement> requirements) {
		// Remove all the existing data
		this.setDataVector(null, colNames);
		
		for (PlanningPokerRequirement requirement : requirements) {			
			Object[] row = {requirement.getName(), 
							requirement.getDescription()};
			this.addRow(row);
		}
	}
}



