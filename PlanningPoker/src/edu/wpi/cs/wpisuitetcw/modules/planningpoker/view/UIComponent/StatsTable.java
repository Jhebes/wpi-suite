package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * StatsTable exhibiting the mean, mode, median,
 * and standard deviation
 * 
 */
public class StatsTable extends JPanel {
	
	/** Mean value and its label*/
	private final JLabel meanLabel;
	private JLabel lblMeanValue;

	/** Median value and its label */
	private final JLabel medianLabel;
	private JLabel lblMedianValue;

	/** Mode value and its label */
	private final JLabel modeLabel;
	private JLabel lblModeValue;
	
	/** Standard Deviation and its label */
	private final JLabel standardDeviationLabel;
	private JLabel standardDeviation;
	
	public StatsTable() {
		// Create label for MEAN
		lblMeanValue = new JLabel("TEST");
		meanLabel = new JLabel("Mean");

		// Create label for MEDIAN
		lblMedianValue = new JLabel("TEST");
		medianLabel = new JLabel("Median");

		// Create label for MODE
		lblModeValue = new JLabel("TEST");
		modeLabel = new JLabel("Mode");

		// Create label for STANDARD DEVIATION
		standardDeviation = new JLabel("TEST");
		standardDeviationLabel = new JLabel("Standard Deviation");
		
		setGUIComponentsBorder();
		putGUIComponentOnPanel();
	}

	private void setGUIComponentsBorder() {
	}

	/*
	 * Put GUI components on the panel
	 */
	private void putGUIComponentOnPanel() {
		setLayout(new MigLayout("insets 0, fill", "[]5[]", ""));
		add(meanLabel, "left");
		add(lblMeanValue, "left, wrap");
		
		add(medianLabel, "left");
		add(lblMedianValue, "left, wrap");
		
		add(modeLabel, "left");
		add(lblModeValue, "left, wrap");
		
		add(standardDeviationLabel, "left");
		add(standardDeviation, "left, wrap");

	}

	/**
	 * Assign the given value to the mean
	 * @param lblMeanValue The value that would
	 * be assigned to the mean
	 */
	public void setMean(String mean) {
		this.lblMeanValue.setText(mean);;
	}

	/**
	 * Assign the given value to the median
	 * @param lblMedianValue The value that would
	 * be assigned to the median
	 */
	public void setMedian(String median) {
		this.lblMedianValue.setText(median);;
	}

	/**
	 * Assign the given value to the mode
	 * @param lblModeValue The value that would
	 * be assigned to the mode
	 */
	public void setMode(String mode) {
		this.lblModeValue.setText(mode);;
	}

	/**
	 * Assign the given value to the standard deviation
	 * @param standardDeviation The value that would
	 * be assigned to the standard deviation
	 */
	public void setStandardDeviation(String sd) {
		this.standardDeviation.setText(sd);;
	}
	
}
