/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventController;

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

		// Listener for Open Sessions Button
		openSessionBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TO_DO: Get real data
				System.out.println("YOU CLICKED ME!");
			}
		});

		// Listener for Closed Sessions Button
		closedSessionBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TO_DO: Get real data
				System.out.println("YOU CLICKED ME!");
			}
		});

		// Listener for All Sessions Button
		allSessionsBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TO_DO: Get real data
				System.out.println("YOU CLICKED ME!");
			}
		});

		// Add the buttons to the leftPanel
		leftPanel.add(openSessionBtn);
		leftPanel.add(closedSessionBtn);
		leftPanel.add(allSessionsBtn);

		// Dummy Data for now, eventually this will be generated from BD

		String[] colNames = { "ID", "Name", "Start Date", "Description",
				"Status" };

		Object[][] tableData = {
				{
					Integer.valueOf(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(2), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(3), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(4), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(5), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(6), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(7), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(8), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(9), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(10), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(11), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(12), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(13), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(14), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(15), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(16), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(17), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(18), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(19), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(20), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(21), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(22), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(23), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(24), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(25), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(26), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(27), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(28), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(29), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(30), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(31), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(32), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(33), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(34), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					Integer.valueOf(35), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(36), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(37), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(38), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(39), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(40), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(41), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(42), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(43), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(44), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(45), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(46), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					Integer.valueOf(47), "This is another name", "2/22/2222", "numbah two", "In Progress"
				}
		};
		
		// Create Table using data above
		// final JTable table = new JTable(tableData, colNames);

		final JTable table = new JTable(tableData, colNames) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

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
	
	public void recieveSessionList(PlanningPokerSession[] pps) {
		
	}

}
