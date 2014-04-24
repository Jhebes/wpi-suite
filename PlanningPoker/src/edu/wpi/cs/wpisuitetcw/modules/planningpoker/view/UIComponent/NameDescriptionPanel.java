/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/**
 * A panel that has a name and description text boxes
 * 
 * This panel should be used when clients needs a name
 * and a description box as an integrated unit
 */
public class NameDescriptionPanel extends JPanel {
	
	/** A text field for name */
	private final JLabel nameLabel;
	private final JTextField nameTextField;
	
	/** A text field for description */
	private final JLabel descriptionLabel;
	private final JTextField descriptionTextField;
	
	/**
	 * Construct the NameDescriptionPanel by
	 * creating and adding the name and description
	 * text boxes with their labels
	 */
	public NameDescriptionPanel() {
		// Create text box and label for name
		nameLabel =  new JLabel("Name *");
		nameTextField	 = new JTextField();
		
		// Create text box and label for desription
		descriptionLabel = new JLabel("Description *");
		descriptionTextField 	= new JTextField();

		setLayout(new MigLayout());
		add(nameLabel, "left, span");
		add(nameTextField, "span");
		add(descriptionLabel, "left, span");
		add(descriptionTextField, "grow");
	}
	
	/**
	 * Construct the NameDescriptionPanel by
	 * creating and adding the name and description
	 * text boxes with the given Strings as their corresponding labels
	 */
	public NameDescriptionPanel(String name, String description) {
		this();
		
		// Assign the given strings to the JLabel
		nameLabel.setText(name);
		descriptionLabel.setText(description);
	}
	
}
