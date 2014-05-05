package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

/**
 * StatsTable exhibiting the mean, mode, median,
 * and standard deviation
 * 
 */
public class StatsTable extends JPanel {
	
	/** Mean value */
	private String mean;

	/** Median value */
	private String median; 

	/** Mode value */
	private String mode;
	
	/** Standard Deviation */
	private String standardDeviation;
	
	/** JTable exhibiting the stats */
	private final JTable statsTable;
	
	public StatsTable() {
		mean = "0.0";
		median = "0";
		mode = "0";
		standardDeviation = "0.0";
		
		// Create the stats table
		Object[] header = {"Type", "Value"};
		Object[][] data = {{"Mean", mean}, 
						   {"Median", median},
						   {"Mode", mode},
						   {"<html>Standard<br>deviation</html>", standardDeviation}};
		
		statsTable = new JTable(data, header);
		statsTable.setTableHeader(null);
		

		// Set columns size
		statsTable.getColumnModel().getColumn(0).setMinWidth(150);
		statsTable.getColumnModel().getColumn(0).setMaxWidth(150);
		statsTable.getColumnModel().getColumn(1).setMinWidth(50);
		statsTable.getColumnModel().getColumn(1).setMaxWidth(50);

		// Center align the first column
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		statsTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

		// Set row height
		statsTable.setRowHeight(40);
		
		// Set the grid line to black
		statsTable.setShowGrid(true);
		statsTable.setGridColor(Color.BLACK);
		statsTable.setShowVerticalLines(false);
		
		// Create border
		statsTable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// Set the table non-selectable
		statsTable.setEnabled(false);
		
		putGUIComponentOnPanel();
	}

	/*
	 * Put GUI components on the panel
	 */
	private void putGUIComponentOnPanel() {
		setLayout(new MigLayout("insets 0, fill"));
		add(statsTable, "grow");
	}

	/**
	 * Assign the given value to the mean
	 * @param lblMeanValue The value that would
	 * be assigned to the mean
	 */
	public void setMean(String mean) {
		statsTable.getModel().setValueAt(mean, 0, 1);
	}

	/**
	 * Assign the given value to the median
	 * @param lblMedianValue The value that would
	 * be assigned to the median
	 */
	public void setMedian(String median) {
		statsTable.getModel().setValueAt(median, 1, 1);
	}

	/**
	 * Assign the given value to the mode
	 * @param lblModeValue The value that would
	 * be assigned to the mode
	 */
	public void setMode(String mode) {
		statsTable.getModel().setValueAt(mode, 2, 1);
	}

	/**
	 * Assign the given value to the standard deviation
	 * @param standardDeviation The value that would
	 * be assigned to the standard deviation
	 */
	public void setStandardDeviation(String sd) {
		statsTable.getModel().setValueAt(sd, 3, 1);
	}
	
}
