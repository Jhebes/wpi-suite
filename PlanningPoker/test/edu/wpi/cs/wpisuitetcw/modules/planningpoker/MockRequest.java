package edu.wpi.cs.wpisuitetcw.modules.planningpoker;

/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 *    JPage
 ******************************************************************************/

import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class MockRequest extends Request {

	protected boolean sent = false;
	protected boolean shouldError = false;

	public MockRequest(NetworkConfiguration networkConfiguration, String path,
			HttpMethod requestMethod) {
		super(networkConfiguration, path, requestMethod);
	}

	@Override
	public void send() throws IllegalStateException {
		// don't actually send
		sent = true;
		ResponseModel response = new ResponseModel();
		if (shouldError) {
			response.setStatusCode(500);
			response.setBody("TWC");
			this.setResponse(response);
			this.notifyObserversResponseError();
		} else {

			response.setStatusCode(200);
			response.setBody("TWC");
			this.setResponse(response);
			this.notifyObserversResponseSuccess();
		}
	}

	public boolean isSent() {
		return sent;
	}
}
