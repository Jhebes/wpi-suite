/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GetAllSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.RetrieveFreePlanningPokerRequirementsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.ViewSessionTableModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ScrollablePanel;

/**
 * @author troyling
 * 
 */
public class ViewSessionReqPanel extends JPanel {
	private final ViewSessionPanel parentPanel;
	private final ScrollablePanel sessionReqPanel;
	private final ScrollablePanel allReqPanel;
	private final JPanel buttonsPanel;
	private final JTextArea description;
	private final JTable allReqTable;
	private final JTable sessionReqTable;

	public ViewSessionReqPanel(ViewSessionPanel parentPanel) {
		this.setLayout(new BorderLayout());
		this.parentPanel = parentPanel;
		this.sessionReqPanel = new ScrollablePanel();
		this.allReqPanel = new ScrollablePanel();
		this.buttonsPanel = new JPanel();
		this.description = new JTextArea();

		// setup panels
		Panel leftPanel = new Panel();
		Panel rightPanel = new Panel();
		Panel centerPanel = new Panel();
		Panel bottomPanel = new Panel();

		// setup tables
		allReqTable = new JTable(
				ViewSessionTableModel.getInstance()) {
			private static final long serialVersionUID = 1L;
			private boolean initialized = false;

			public boolean isCellEditable(int row, int colunm) {
				return false;
			}

			public void valueChanged(ListSelectionEvent e) {
				
			}
			
			@Override
			public void repaint() {
				// because janeway is terrible and instantiates this class
				// before the network objects
				if (!initialized) {
					try {
						RetrieveFreePlanningPokerRequirementsController
						.getInstance().refreshData();
						initialized = true;
					} catch (Exception e) {

					}
				}
				
				super.repaint();
			}
		};
		
		allReqTable.setBackground(Color.WHITE);
		
		// add table to rightPanel
		rightPanel.setLayout(new BorderLayout());
		JScrollPane allReqSp = new JScrollPane(allReqTable);
		rightPanel.add(allReqSp);
		
		// table for left pain
		sessionReqTable = new JTable(
				ViewSessionTableModel.getInstance()) {
			private static final long serialVersionUID = 2L;
			private boolean initialized = false;

			public boolean isCellEditable(int row, int colunm) {
				return false;
			}

			public void valueChanged(ListSelectionEvent e) {

			}
			
			@Override
			public void repaint() {
				// because janeway is terrible and instantiates this class
				// before the network objects
				if (!initialized) {
					try {
						RetrieveFreePlanningPokerRequirementsController
						.getInstance().refreshData();
						initialized = true;
					} catch (Exception e) {

					}
				}
				
				super.repaint();
			}
		};
		
		
		leftPanel.setLayout(new BorderLayout());
		JScrollPane sessionReqSp = new JScrollPane(sessionReqTable);
		leftPanel.add(sessionReqSp);
		
		
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(buttonsPanel);
		
		// setup panels
		this.add(leftPanel, BorderLayout.LINE_START);
		this.add(rightPanel, BorderLayout.LINE_END);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add()
		this.setEnabled(false);
	}
}
