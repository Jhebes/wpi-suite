/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.BorderLayout;

import javax.swing.JSplitPane;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionBtnPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionInfoPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp.ViewSessionReqPanel;

/**
 * @author troyling
 * 
 */
public class ViewSessionPanel extends JSplitPane {
	final private ViewSessionBtnPanel buttonPanel;
	final private ViewSessionInfoPanel infoPanel;
	final private ViewSessionReqPanel pkgPanel;
	final private PlanningPokerSession session;

	/**
	 * Create a view session panel
	 */
	public ViewSessionPanel(PlanningPokerSession session) {
		this.session = session;
		this.infoPanel = new ViewSessionInfoPanel(this, session);
		this.buttonPanel = new ViewSessionBtnPanel(this);
		this.pkgPanel = new ViewSessionReqPanel(this);

		// set sub panels
		JSplitPane contentPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				true, infoPanel, pkgPanel);
		contentPanel.setDividerLocation(180);
		contentPanel.setEnabled(false);
		this.setLayout(new BorderLayout());

		// set up the viewSessionPanel
		this.add(contentPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}

	public ViewSessionBtnPanel getButtonPanel() {
		return buttonPanel;
	}

	public ViewSessionInfoPanel getInfoPanel() {
		return infoPanel;
	}

	public ViewSessionReqPanel getPkgPanel() {
		return pkgPanel;
	}

	public PlanningPokerSession getPPSession() {
		return session;
	}

	
}