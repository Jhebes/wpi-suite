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

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

/**
 * A panel that has a name and description text boxes
 * 
 * This panel should be used when clients needs a name
 * and a description box as an integrated unit
 * 
 * ######################################################
 * Warning!!!: Only anyone who is familiar with MigLayout 
 * should modify the implementation of this file.
 * 
 * Panel Structure: 
 * ---------------------------------------------------
 * nameLabel
 * nameFrame [Additional GUI components can be added here]
 * [Additional GUI components can be added here]
 * descriptionLabel
 * descriptionFrame
 * ---------------------------------------------------
 * 
 * File Structure:
 * 		+ Constants
 * 		+ Ivars
 * 		+ Constructors
 * 		+ Content manipulating methods
 * 		+ Layout manipulating methods
 * 		+ Underlying implementations
 */
public class NameDescriptionPanel extends JPanel {

	private static final int TEXTBOX_HEIGHT = 26;

	/** A text field for name */
	private final JLabel nameLabel;
	private final JTextArea nameTextField;
	private final JScrollPane nameFrame;
	
	/* A flag that determines whether the name text box is required or not */
	// If this flag is true, the name text box and its label will be put
	// on the NameDescriptionPanel
	private boolean isNameTextboxNeeded;
	
	/** A text field for description */
	private final JLabel descriptionLabel;
	private final JTextArea descriptionTextField;
	final JScrollPane descriptionFrame;
	
	/** A list of Components that lie to the right of name text box */
	private List<Component> nextToNameTextboxComponents;
	
	/** A list of Components that lie below the name text box */
	private List<Component> belowNameTextboxComponents;
	
	/**
	 * Construct the NameDescriptionPanel by creating and adding the name and
	 * description text boxes with their labels
	 */
	public NameDescriptionPanel() {
		// Create text box and label for name
		nameLabel = new JLabel("Name *");
		nameTextField = new JTextArea();
		nameTextField.setLineWrap(true);
		nameTextField.setWrapStyleWord(true);
		nameTextField.setDisabledTextColor(Color.BLACK);
		nameTextField.setBorder(new EmptyBorder(4, 3, 2, 3));
		nameFrame = new JScrollPane(nameTextField);
		
		setAutoHighlightWhenClicked();
		
		// Set the name text box to be required in the panel
		isNameTextboxNeeded = true;

		// Create text box and label for desription
		descriptionLabel = new JLabel("Description *");
		descriptionTextField = new JTextArea();
		descriptionTextField.setLineWrap(true);
		descriptionTextField.setWrapStyleWord(true);
		descriptionTextField.setDisabledTextColor(Color.BLACK);
		descriptionTextField.setBorder(new EmptyBorder(5, 5, 5, 5));
		descriptionFrame = new JScrollPane(descriptionTextField);
		
		// Initialize the list of extra components
		nextToNameTextboxComponents = new ArrayList<>();
		belowNameTextboxComponents  = new ArrayList<>();

		putAllComponentsOnPanel();
	}

	/**
	 * Construct the NameDescriptionPanel by creating and adding the name and
	 * description text boxes with the given Strings as their corresponding
	 * labels
	 */
	public NameDescriptionPanel(String name, String description) {
		this();

		// Assign the given strings to the JLabel
		nameLabel.setText(name);
		descriptionLabel.setText(description);
	}

	/**
	 * Construct the NameDescriptionPanel by creating and adding the name and
	 * description text boxes with the given Strings as their corresponding
	 * labels The text boxes can only be editable when if isEditable is true
	 */
	public NameDescriptionPanel(String name, String description,
			boolean isEditable) {
		this(name, description);

		// Disable the text boxes if isEditable is false
		if (!isEditable) {
			nameTextField.setEnabled(false);
			descriptionTextField.setEnabled(false);
		}
	}
	
	// VVVVVVVVVVVVVVVVVVVVVVVV CONTENT METHODS VVVVVVVVVVVVVVVVVVVVVVVV
	/**
	 * Put the given name in the name text box
	 * 
	 * @param A
	 *            string that would be put in the name text box
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
	 * 
	 * @param text
	 *            A string that would be put in the description text box
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
	
	/**
	 * Assign the given to the title of the description box
	 */
	public void setDescriptionTitle(String title) {
		descriptionLabel.setText(title);
	}
	
	// VVVVVVVVVVVVVVVVVVVVVV LAYOUT METHODS VVVVVVVVVVVVVVVVVVVVVVVV
	/**
	 * Remove the name text box and its label
	 */
	public void removeNameTextbox() {
		// Set the flag to false so the text box
		// is excluded when the panel is re-constructed
		isNameTextboxNeeded = false;
		
		putAllComponentsOnPanel();
	}
	

	/**
	 * Add new component to the right side of the name text box
	 * @param otherComponent The component that would be added to
	 * the right side of the name text box
	 */
	public void addToRightSideNameTextbox(Component otherComponent) {
		nextToNameTextboxComponents.add(otherComponent);
		putAllComponentsOnPanel();
	}

	/**
	 * Add a list of components to the right side of the name text box
	 * @param otherComponents The list of components that would be added to
	 * the right side of the name text box
	 */
	public void addToRightSideNameTextbox(List<Component> otherComponents) {
		nextToNameTextboxComponents.addAll(otherComponents);
		putAllComponentsOnPanel();
	}
	
	/**
	 * Add a component below the name text box
	 * @param otherComponent The component that would be added 
	 * below name text box
	 */
	public void addBelowNameTextbox(Component otherComponent) {
		belowNameTextboxComponents.add(otherComponent);
		putAllComponentsOnPanel();
	}
	
	/**
	 * Add a list of components below the name text box
	 * @param otherComponents The list of component that would be added
	 * below the name text box
	 */
	public void addBelowNameTextbox(List<Component> otherComponents) {
		belowNameTextboxComponents.addAll(otherComponents);
		putAllComponentsOnPanel();
	}
	
	
	// VVVVVVVVVVVVVVVVVV PRIVATE IMPLEMENTATIONS VVVVVVVVVVVVVVVVVVVVVV
	/*
	 * Put all the necessary components on the NameDescriptionPanel
	 */
	private void putAllComponentsOnPanel() {
		removeAll();
		
		String rowConstrain = generateRowConstrain();
		setLayout(new MigLayout("fill, inset 0", "", rowConstrain));
		
		if (isNameTextboxNeeded) {
			add(nameLabel, "left, growx, wrap");
			add(nameFrame, "growx, height " + TEXTBOX_HEIGHT + "px!"
							+ generateNumSplit());
		}
		addElementsNextToNameTextbox();
		addElementsBelowNameTextbox();
		add(descriptionLabel, "left, growx, span");
		add(descriptionFrame, "grow, hmin " + TEXTBOX_HEIGHT + "px");
	}

	/*
	 * Add elements below the name text box
	 * to a row below it
	 */
	private void addElementsBelowNameTextbox() {
		// Add the component and create a new row if
		// there is only one element below name text box
		if (!belowNameTextboxComponents.isEmpty() &&
				belowNameTextboxComponents.size() == 1) {
			
			add(belowNameTextboxComponents.get(0), 
				"split " + belowNameTextboxComponents.size() + ", growx, center, wrap");
		} else if (!belowNameTextboxComponents.isEmpty()) {
			// Add the first component with split cell constrain
			add(belowNameTextboxComponents.get(0), 
				"split " + belowNameTextboxComponents.size() + ", growx, center");
			
			// Add the elements after the first and before the last
			if (belowNameTextboxComponents.size() >= 3) {
				for (int i = 1; i < belowNameTextboxComponents.size() - 1; i++) {
					add(belowNameTextboxComponents.get(i), "growx, center");
				}
			}
			
			// Add a new row after the last component
			if (!belowNameTextboxComponents.isEmpty()) {
				add(belowNameTextboxComponents
						.get(belowNameTextboxComponents.size() - 1), 
					"growx, center, wrap");
			}
		}
	}

	/*
	 * Add element next to the name box
	 */
	private void addElementsNextToNameTextbox() {
		if (isNameTextboxNeeded) {
			for (int i = 0; i < nextToNameTextboxComponents.size() - 1; i++) {
				add(nextToNameTextboxComponents.get(i), "left, height " + TEXTBOX_HEIGHT + "px!");
			}
			if (!nextToNameTextboxComponents.isEmpty()) {
				add(nextToNameTextboxComponents.
						get(nextToNameTextboxComponents.size() - 1), 
					"left, wrap");
			}
		}
	}

	/*
	 * Return the constrain for split cell
	 * This method returns number of subcells to
	 * hold the elements next to name text box
	 */
	private String generateNumSplit() {
		String splitConstrain = "";
		
		// Add split constrain only if there is elements next to
		// the name text box
		if (!nextToNameTextboxComponents.isEmpty()) {
			// + 1 because the name box is counted as 1 column
			splitConstrain += ", split " + nextToNameTextboxComponents.size() + 1;
		} else {
			splitConstrain += ", wrap";
		}
		
		return splitConstrain;
	}

	/*
	 * Return a column constain for MigLayout
	 */
	private String generateRowConstrain() {
		// Initialize and set up the row constrain for name text box
		// and its label
		String rowConstrain = "0";
		
		// Create row constrain for name text box and its label
		// if they are existed
		if (isNameTextboxNeeded) {
			rowConstrain += "[]10[]";
		}
		
		// Add another row if there is an element below the name text box
		if (!belowNameTextboxComponents.isEmpty()) {
			rowConstrain += "[]";
		}
		
		// Create row constrain for the description box and its label AND 
		// Set the description box to fill up the available space
		rowConstrain += "[]10[grow]0";
		
		return rowConstrain;
	}
	

	/*
	 *  Auto select all text when mouse clicks on the name text box
	 */
	private void setAutoHighlightWhenClicked() {
		nameTextField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				// Do nothing when user clicks somewhere else
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				nameTextField.selectAll();
			}
		});
	}
	
}
