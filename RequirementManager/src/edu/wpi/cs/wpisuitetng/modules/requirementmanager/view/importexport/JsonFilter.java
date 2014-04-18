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

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * File filter that only accepts JSON files.
 */
public class JsonFilter extends FileFilter {

	/** File extension for JSON files. */
	private static final String json = "json";

	/**
	 * @return Whether this file can be accepted as a JSON file.
	 */
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		final String extension = getExtension(f);
		if (extension != null) {
			return extension.equals(json);
		}

		return false;
	}

	/**
	 * Returns the description that shows up in the file type dropdown menu.
	 */
	public String getDescription() {
		return "*.json";
	}

	/**
	 * Parses the extension from a file.
	 * 
	 * @param file
	 *            The file object
	 * @return The extension for this file's filename
	 */
	public static String getExtension(File file) {
		String ext = null;
		final String s = file.getName();
		final int index = s.lastIndexOf('.');

		if (index > 0 && index < s.length() - 1) {
			ext = s.substring(index + 1).toLowerCase();
		}
		return ext;
	}
}
