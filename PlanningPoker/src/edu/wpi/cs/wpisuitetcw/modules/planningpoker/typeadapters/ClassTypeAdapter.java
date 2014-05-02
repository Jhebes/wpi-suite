/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.typeadapters;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Type adapter for serializing types. I mean classes.
 */
@SuppressWarnings("rawtypes")
public class ClassTypeAdapter extends TypeAdapter<Class> {
	
	/**
	 * Parses a class by its canonical name.
	 * @param reader The JSON reader
	 * @return The class parsed from the reader, or null if not found
	 */
	public Class read(JsonReader reader) throws IOException {
		if (reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			return null;
		}
		String name = reader.nextString();
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	/**
	 * Converts the class to JSON by specifying its canonical name.
	 * @param writer The JSON writer
	 * @param value The class to convert
	 */
	public void write(JsonWriter writer, Class value) throws IOException {
		if (value == null) {
			writer.nullValue();
			return;
		}
		writer.value(value.getCanonicalName());
	}
}
