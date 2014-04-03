/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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
	private final JButton moveRequirementToAll;
	private final JButton moveAllRequirementsToAll;
	private final JButton moveRequirementToSession;
	private final JButton moveAllRequirementsToSession;
	private final JTable allReqTable;
	private final JTable sessionReqTable;

	public ViewSessionReqPanel(ViewSessionPanel parentPanel) {
		this.setLayout(new GridBagLayout());
		this.parentPanel = parentPanel;
		this.sessionReqPanel = new ScrollablePanel();
		this.allReqPanel = new ScrollablePanel();
		this.buttonsPanel = new JPanel();
		this.description = new JTextArea();
		this.moveRequirementToAll = new JButton(" < ");
		this.moveAllRequirementsToAll = new JButton(" << ");
		this.moveRequirementToSession = new JButton(" > ");
		this.moveAllRequirementsToSession = new JButton(" >> ");
				
		
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
		leftPanel.setLayout(new BorderLayout());
		JScrollPane allReqSp = new JScrollPane(allReqTable);
		leftPanel.add(allReqSp);
		
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
		
		
		rightPanel.setLayout(new BorderLayout());
		JScrollPane sessionReqSp = new JScrollPane(sessionReqTable);
		rightPanel.add(sessionReqSp);
		
		
		moveAllRequirementsToSession.setPreferredSize(new Dimension(70,50));
		moveRequirementToSession.setPreferredSize(new Dimension(70,50));
		moveRequirementToAll.setPreferredSize(new Dimension(70,50));
		moveAllRequirementsToAll.setPreferredSize(new Dimension(70,50));
		
		// setup buttons panel
		buttonsPanel.setLayout(new GridLayout(0, 1, 0, 20));
		buttonsPanel.add(moveAllRequirementsToSession);
		buttonsPanel.add(moveRequirementToSession);
		buttonsPanel.add(moveRequirementToAll);
		buttonsPanel.add(moveAllRequirementsToAll);

		// buttons panel goes in the center
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(buttonsPanel);
		
		// text field for description goes in the bottom of the panel
		JScrollPane descriptionSp = new JScrollPane(description);
		description.setLineWrap(true);
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(descriptionSp);
		
		GridBagConstraints c = new GridBagConstraints();

		c.weighty = 1.0;
		c.weightx = 1.0;
		// c.insets = new Insets(10,0,0,0); // this is how we pad
		c.gridx = 0;
		c.gridy = 0;
		this.add(leftPanel, c);
		
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = 0;
		this.add(centerPanel, c);
		
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.gridx = 2;
		c.gridy = 0;
		this.add(rightPanel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;
		c.weighty = 0;
		c.weightx = 0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		this.add(bottomPanel, c);
		
		// setup panels
		/*this.add(leftPanel, BorderLayout.WEST);
		this.add(rightPanel, BorderLayout.EAST);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		this.setEnabled(false);*/
	}
}
