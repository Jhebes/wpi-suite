package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ImportRequirementsTableModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Creates a planning poker requirement from a requirement manager requirement
 * and sends it to the database.
 */
public class ImportRequirementController implements ActionListener {

	private PlanningPokerRequirement requirement;
	
	public ImportRequirementController() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ImportRequirementsTableModel dtm = ImportRequirementsTableModel
				.getInstance();
		int nRow = dtm.getRowCount();
		for (int i = 0; i < nRow; i++) {
			if ((Boolean) dtm.getValueAt(i, 0)) {
				PlanningPokerRequirement ppreq = (PlanningPokerRequirement) dtm
						.getValueAt(i, 1);
				importRequirement(ppreq);
			}
		}
	}

	public void importRequirement(PlanningPokerRequirement requirement) {
		this.requirement = requirement;
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/session/1", HttpMethod.GET);
		request.addObserver(new ImportRequirementRequestObserver(this));
		request.send();
	}

	
	public void onSuccess(PlanningPokerSession freeReqsSession) {
		freeReqsSession.addRequirement(requirement);
		freeReqsSession.update();
	}
}
