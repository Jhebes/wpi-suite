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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
	private final JTextField name;
	private final JButton moveRequirementToAll;
	private final JButton moveAllRequirementsToAll;
	private final JButton moveRequirementToSession;
	private final JButton moveAllRequirementsToSession;
	private final JButton addRequirementToAll;
	private final JButton addRequirementToSession;
	private final JTable allReqTable;
	private final JTable sessionReqTable;
	
	public String[] getLeftSelectedRequirements()	{
		int[] selectedRows = this.allReqTable.getSelectedRows();
		String[] selectedNames = {};
		for(int i = 0; i < selectedRows.length; i++){
			selectedNames[i] = this.allReqTable.getValueAt(selectedRows[i],1).toString();
		}
		return selectedNames;
	}
	
	public String[] getRightSelectedRequirements()	{
		int[] selectedRows = this.sessionReqTable.getSelectedRows();
		String[] selectedNames = {};
		for(int i = 0; i < selectedRows.length; i++){
			selectedNames[i] = this.sessionReqTable.getValueAt(selectedRows[i],1).toString();
		}
		return selectedNames;
	}
	
	
	public ViewSessionReqPanel(ViewSessionPanel parentPanel) {
		this.setLayout(new GridBagLayout());
		this.parentPanel = parentPanel;
		this.sessionReqPanel = new ScrollablePanel();
		this.allReqPanel = new ScrollablePanel();
		this.buttonsPanel = new JPanel();
		this.description = new JTextArea("");
		this.name = new JTextField("");
		this.moveRequirementToAll = new JButton(" < ");
		this.moveAllRequirementsToAll = new JButton(" << ");
		this.moveRequirementToSession = new JButton(" > ");
		this.moveAllRequirementsToSession = new JButton(" >> ");
		this.addRequirementToAll = new JButton("Add Requirement to All");
		this.addRequirementToSession = new JButton("Add Requirement to Session");
		
		
		// setup panels
		Panel namePanel = new Panel();
		Panel leftPanel = new Panel();
		Panel rightPanel = new Panel();
		Panel centerPanel = new Panel();
		Panel bottomPanel = new Panel();

		// setup tables
		allReqTable = new JTable(ViewSessionTableModel.getInstance()) {
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
		sessionReqTable = new JTable(ViewSessionTableModel.getInstance()) {
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

		
		//sessionReqTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		//allReqTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		sessionReqTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		allReqTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		rightPanel.setLayout(new BorderLayout());
		JScrollPane sessionReqSp = new JScrollPane(sessionReqTable);
		rightPanel.add(sessionReqSp);

		moveAllRequirementsToSession.setPreferredSize(new Dimension(70, 50));
		moveRequirementToSession.setPreferredSize(new Dimension(70, 50));
		moveRequirementToAll.setPreferredSize(new Dimension(70, 50));
		moveAllRequirementsToAll.setPreferredSize(new Dimension(70, 50));

		// setup buttons panel
		buttonsPanel.setLayout(new GridLayout(0, 1, 0, 20));
		buttonsPanel.add(moveAllRequirementsToSession);
		buttonsPanel.add(moveRequirementToSession);
		buttonsPanel.add(moveRequirementToAll);
		buttonsPanel.add(moveAllRequirementsToAll);

		// buttons panel goes in the center
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(buttonsPanel);

		// text field for name goes in the top of the panel
		JLabel nameLabel = new JLabel("Name:");
		namePanel.setLayout(new BorderLayout());
		namePanel.add(nameLabel, BorderLayout.NORTH);
		namePanel.add(name, BorderLayout.SOUTH);
		
		
		// text field for description goes in the bottom of the panel
		JLabel descriptionLabel = new JLabel("Description:");
		JScrollPane descriptionSp = new JScrollPane(description);
		description.setLineWrap(true);
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(descriptionLabel, BorderLayout.NORTH);
		bottomPanel.add(descriptionSp, BorderLayout.CENTER);

		GridBagConstraints c = new GridBagConstraints();

		
		c.weighty = .2;
		c.weightx = .2;
		c.gridx = 1;
		c.gridy = 0;
		this.add(centerPanel, c);
		
		c.weighty = 1.0;
		c.weightx = 1.0;
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 1;
		c.gridy = 1;
		this.add(addRequirementToAll, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10,10,10,10);
		c.gridx = 2;
		c.gridy = 1;
		this.add(addRequirementToSession, c);
		
		c.insets = new Insets(10,10,10,10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		this.add(namePanel, c);
		
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.insets = new Insets(10,10,0,0);
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		this.add(leftPanel, c);

		c.weighty = 1.0;
		c.weightx = 1.0;
		c.insets = new Insets(10,0,0,10);
		c.gridx = 2;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		this.add(rightPanel, c);
		
		c.ipady = 100;
		c.insets = new Insets(10,10,10,10);
		c.weighty = 0;
		c.weightx = 0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(bottomPanel, c);

	}
}
