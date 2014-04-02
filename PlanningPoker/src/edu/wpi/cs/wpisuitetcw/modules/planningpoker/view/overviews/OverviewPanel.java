/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GetAllSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GetClosedSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GetOpenSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;

/**
 * @author troyling, Jake, Zack
 * 
 */
public class OverviewPanel extends JSplitPane {

	private static final long serialVersionUID = 1L;
	private final JPanel rightPanel;
	private final JPanel leftPanel;
	private final JButton openSessionBtn;
	private final JButton closedSessionBtn;
	private final JButton allSessionsBtn;

	public OverviewPanel() {
		// Create the right side of the panel
		rightPanel = new JPanel();

		// Create the left side
		leftPanel = new JPanel();

		// adds some padding
		leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		// Set leftPanel to GridLayout
		leftPanel.setLayout(new GridLayout(6, 1, 1, 20));

		// Initialize the buttons
		openSessionBtn = new JButton("Open Sessions");
		closedSessionBtn = new JButton("Closed Sessions");
		allSessionsBtn = new JButton("All Sessions");

		openSessionBtn.addActionListener(new GetOpenSessionsController(this));
		closedSessionBtn
				.addActionListener(new GetClosedSessionsController(this));
		allSessionsBtn
				.addActionListener(GetAllSessionsController.getInstance());

		// Create Table using data above

		final JTable table = new JTable(
				OverviewTableSessionTableModel.getInstance()) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};

			public void valueChanged(ListSelectionEvent e) {

			}
		};

		openSessionBtn.addActionListener(new GetOpenSessionsController(this));
		closedSessionBtn
				.addActionListener(new GetClosedSessionsController(this));
		allSessionsBtn
				.addActionListener(GetAllSessionsController.getInstance());

		// Add the buttons to the leftPanel
		leftPanel.add(openSessionBtn);
		leftPanel.add(closedSessionBtn);
		leftPanel.add(allSessionsBtn);

		// Add mouse listener to check for mouse clicks on the table
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// Check to see if user double clicked
				if (e.getClickCount() == 2) {
					JTable resultsTable = (JTable) e.getSource();
					int row = resultsTable.rowAtPoint(e.getPoint());

					if (row > -1) {
						// Gets the name, which is index 1
						String sessionName = (String) table.getValueAt(row, 1);
						ViewEventManager.getInstance().viewSession(sessionName);
					}
				}
			}
		});

		// Sets table bg to white
		table.setBackground(Color.WHITE);

		// Set layout for right panel
		rightPanel.setLayout(new BorderLayout());

		// Add table inside a JScrollPane to rightPanel
		JScrollPane jsp = new JScrollPane(table);

		// Add the JSP to the rightPanel
		rightPanel.add(jsp);

		// Set panels background to white (matching table)
		rightPanel.setBackground(Color.WHITE);

		// Add panels to main JSplitPane
		this.setRightComponent(rightPanel);
		this.setLeftComponent(leftPanel);

		// Set divider location between right and left panel
		this.setDividerLocation(180);

	}

	// Getters
	public JButton getOpenSessionBtn() {
		return openSessionBtn;
	}

	public JButton getClosedSessionBtn() {
		return closedSessionBtn;
	}

	public JButton getAllSessionsBtn() {
		return allSessionsBtn;
	}

	public void recieveSessionList(PlanningPokerSession[] pps) {

	}

}
