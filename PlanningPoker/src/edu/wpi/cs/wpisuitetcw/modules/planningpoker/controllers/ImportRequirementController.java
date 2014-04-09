package edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ImportRequirementsTableModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Creates a planning poker requirement from a requirement manager requirement
 * and sends it to the database.
 */
public class ImportRequirementController implements ActionListener {

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
				Requirement req = ppreq.getInnerRequirement();
				importRequirement(req);
			}
		}
	}

	public void importRequirement(Requirement requirement) {
		PlanningPokerRequirement newRequirement = new PlanningPokerRequirement(requirement);
		final Request request = Network.getInstance().makeRequest(
				"planningpoker/requirement", HttpMethod.PUT);
		request.setBody(newRequirement.toJSON());
		request.addObserver(new ImportRequirementRequestObserver(this));
		request.send();
	}

	public void onSuccess() {

	}

}
