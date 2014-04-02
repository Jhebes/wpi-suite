/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GetAllSessionsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;

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

		// Add the buttons to the leftPanel
		leftPanel.add(openSessionBtn);
		leftPanel.add(closedSessionBtn);
		leftPanel.add(allSessionsBtn);

		// Create Table using data above

		final JTable table = new JTable(
				OverviewTableSessionTableModel.getInstance()) {
			private static final long serialVersionUID = 1L;
			private boolean initialized = false;

			public boolean isCellEditable(int row, int column) {
				return false;
			};

			public void valueChanged(ListSelectionEvent e) {

			}

			@Override
			public void repaint() {
				// because janeway is terrible and instantiates this class
				// before the network objects
				if (!initialized) {
					try {
						GetAllSessionsController.getInstance()
								.retrieveSessions();
						initialized = true;
					} catch (Exception e) {

					}
				}
				
				super.repaint();
			}
		};

		// Create Table using data above
		// final JTable table = new JTable(tableData, colNames);

		// Add mouse listener to check for mouse clicks on the table
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// Check to see if user double clicked
				if (e.getClickCount() == 2) {
					System.out.println(table.getValueAt(table.getSelectedRow(),
							table.getSelectedColumn()));
					// TO_DO: OPEN SESSION DETAIL VIEW HERE
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

}
