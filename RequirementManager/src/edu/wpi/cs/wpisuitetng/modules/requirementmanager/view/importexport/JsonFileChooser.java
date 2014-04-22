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

import javax.swing.JFileChooser;

/**
 * A file chooser that only accepts JSON files. Can be used for both opening and
 * saving JSON files.
 */
public class JsonFileChooser extends JFileChooser {

	/**
	 * UID for serializing.
	 */
	private static final long serialVersionUID = -1667118585381715201L;

	/**
	 * Constructs a JSON file chooser.
	 */
	public JsonFileChooser() {
		super(); // $codepro.audit.disable invocationOfDefaultConstructor

		configureFileChooser();
	}

	/**
	 * Configures the file chooser to only accept JSON files.
	 */
	private void configureFileChooser() {
		addChoosableFileFilter(new JsonFilter());
		setAcceptAllFileFilterUsed(false);
	}
}
