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

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventController;

/**
 * @author troyling, Jake, Zack
 * 
 */
public class OverviewPanel extends JSplitPane {
	/**
	 * 4
	 */
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
				{ new Integer(0), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(1), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(2), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(3), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(4), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(5), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(6), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(7), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(8), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(9), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(10), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(11), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(12), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(13), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(14), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(15), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(16), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(17), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(18), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(19), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(20), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(21), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(22), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(23), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(24), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(25), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(26), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(27), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(28), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(29), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(30), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(31), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(32), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(33), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(34), "This is a name", "1/11/1111",
						"WEEEE a description", "In Progress" },
				{ new Integer(35), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(36), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(37), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(38), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(39), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(40), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(41), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(42), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(43), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(44), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(45), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(46), "This is another name", "2/22/2222",
						"numbah two", "In Progress" },
				{ new Integer(47), "This is another name", "2/22/2222",
						"numbah two", "In Progress" } };

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

}
