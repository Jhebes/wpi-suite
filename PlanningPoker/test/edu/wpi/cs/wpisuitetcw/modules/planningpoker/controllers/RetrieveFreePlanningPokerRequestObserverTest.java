/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.CreateSessionPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * @author psover
 * 
 */
public class RetrieveFreePlanningPokerRequestObserverTest {

	RetrieveFreePlanningPokerRequirementsController controller;
	RetrieveFreePlanningPokerRequirementsRequestObserver observer;

	@Before
	public void setUp() throws Exception {
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));
		controller = new RetrieveFreePlanningPokerRequirementsController(
				new CreateSessionPanel());
	}

	@Test
	public void testResponseSuccess() {
		Request request = Network.getInstance().makeRequest(
				"planningpoker/requirement", HttpMethod.GET);
		observer = new RetrieveFreePlanningPokerRequirementsRequestObserver(
				controller);
		request.addObserver(observer);
		request.send();
		observer.responseSuccess(request);
		assertNotNull(controller);
	}
	/*
	 * @Test public void testResponseError(){ fail("Not done yet"); }
	 * 
	 * @Test public void testFail(){ fail("Not done yet"); }
	 */
}
