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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/**
 * A panel that has a name and description text boxes
 * 
 * This panel should be used when clients needs a name
 * and a description box as an integrated unit
 */
public class NameDescriptionPanel extends JPanel {
	
	private static final int TEXTBOX_HEIGHT = 26;
	
	/** A text field for name */
	private final JLabel nameLabel;
	private final JTextArea nameTextField;
	
	/** A text field for description */
	private final JLabel descriptionLabel;
	private final JTextArea descriptionTextField;
	
	/**
	 * Construct the NameDescriptionPanel by
	 * creating and adding the name and description
	 * text boxes with their labels
	 */
	public NameDescriptionPanel() {
		// Create text box and label for name
		nameLabel =  new JLabel("Name *");
		nameTextField	 = new JTextArea();
		nameTextField.setLineWrap(true);
		nameTextField.setWrapStyleWord(true);
		final JScrollPane nameFrame = new JScrollPane(nameTextField);

		// Create text box and label for desription
		descriptionLabel = new JLabel("Description *");
		descriptionTextField 	= new JTextArea();
		descriptionTextField.setLineWrap(true);
		descriptionTextField.setWrapStyleWord(true);
		final JScrollPane descriptionFrame = new JScrollPane(descriptionTextField);


		setLayout(new MigLayout("fill, inset 0", "", "0[][][][grow]0"));
		add(nameLabel, "left, growx, wrap");
		add(nameFrame, "growx, wrap, height " + TEXTBOX_HEIGHT + "px!");
		add(descriptionLabel, "left, growx, span");
		add(descriptionFrame, "grow");
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
	
	/**
	 * Construct the NameDescriptionPanel by
	 * creating and adding the name and description
	 * text boxes with the given Strings as their corresponding labels
	 * The text boxes can only be editable when if isEditable is true
	 */
	public NameDescriptionPanel(String name, String description, boolean isEditable) {
		this(name, description);
		
		// Disable the text boxes if isEditable is false
		if (!isEditable) {
			nameTextField.setEditable(false);
			descriptionTextField.setEditable(false);
		}
	}
	
	/**
	 * Put the given name in the name text box
	 * @param A string that would be put 
	 * in the name text box
	 */
	public void setName(String name) {
		nameTextField.setText(name);
	}
	
	/**
	 * Remove the text inside the name text box
	 */
	public void clearName() {
		nameTextField.setText("");
	}
	
	/**
	 * Put the given text in the description text box
	 * @param text A string that would be put
	 * in the description text box
	 */
	public void setDescription(String text) {
		descriptionTextField.setText(text);
	}
	
	/**
	 * Remove the text inside the description text box
	 */
	public void clearDescription() {
		descriptionTextField.setText("");
	}
}
