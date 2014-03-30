/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;


/**
 * @author troyling, Jake, Zack
 * 
 */
public class OverviewPanel extends JSplitPane {
	/**
	 * 4	 */
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
		
		// Set leftPanel to GridLayout
		leftPanel.setLayout(new GridLayout( 6, 1, 1, 20 ) );
		
		// Initialize the buttons
		openSessionBtn = new JButton("Open Sessions");
		closedSessionBtn = new JButton("Closed Sessions");
		allSessionsBtn = new JButton("All Sessions");
		
		// Add the buttons to the leftPanel
		leftPanel.add( openSessionBtn );
		leftPanel.add( closedSessionBtn );
		leftPanel.add( allSessionsBtn );
		
		
		// Dummy Data for now, eventually this will be generated from BD
		
		String[] colNames = {"ID", "Name", "Start Date", "Description", "Status"};
		
		Object[][] tableData = {
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(0), "This is a name", "1/11/1111", "WEEEE a description", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				},
				{
					new Integer(1), "This is another name", "2/22/2222", "numbah two", "In Progress"
				}
				
				
				
		};
		
		// Create Table using data above
		JTable table = new JTable(tableData, colNames);
		
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
