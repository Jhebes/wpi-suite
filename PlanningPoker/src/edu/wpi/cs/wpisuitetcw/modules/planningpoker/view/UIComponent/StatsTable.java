package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

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
		Object[] header = {"Stats", "Value"};
		Object[][] data = {{"Mean", mean}, 
						  {"Median", median},
						  {"Median", mode},
						  {"Standard deviation", standardDeviation}};
		statsTable = new JTable(data, header);
		statsTable.setTableHeader(null);
		
		// Set columns size
		statsTable.getColumnModel().getColumn(0).setMaxWidth(100);
		statsTable.getColumnModel().getColumn(1).setMaxWidth(100);
		
		
		putGUIComponentOnPanel();
	}

	/*
	 * Put GUI components on the panel
	 */
	private void putGUIComponentOnPanel() {
		setLayout(new MigLayout("insets 0, fill"));
		add(statsTable, "grow");
		
//		setLayout(new MigLayout("insets 0, fill", "[]20[]", ""));
//		add(meanLabel, "left");
//		add(lblMeanValue, "left, wrap");
//		
//		add(medianLabel, "left");
//		add(lblMedianValue, "left, wrap");
//		
//		add(modeLabel, "left");
//		add(lblModeValue, "left, wrap");
//		
//		add(standardDeviationLabel, "left");
//		add(standardDeviation, "left, wrap");

	}

	/**
	 * Assign the given value to the mean
	 * @param lblMeanValue The value that would
	 * be assigned to the mean
	 */
	public void setMean(String mean) {
		this.mean = mean;
	}

	/**
	 * Assign the given value to the median
	 * @param lblMedianValue The value that would
	 * be assigned to the median
	 */
	public void setMedian(String median) {
		this.median = median;
	}

	/**
	 * Assign the given value to the mode
	 * @param lblModeValue The value that would
	 * be assigned to the mode
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * Assign the given value to the standard deviation
	 * @param standardDeviation The value that would
	 * be assigned to the standard deviation
	 */
	public void setStandardDeviation(String sd) {
		this.standardDeviation = sd;
	}
	
}
