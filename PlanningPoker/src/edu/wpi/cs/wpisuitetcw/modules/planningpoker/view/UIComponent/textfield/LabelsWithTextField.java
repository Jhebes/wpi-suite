package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent.textfield;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/**
 * LabelsWithTextField has a JTextField and
 * 2 JTextArea above and below the JTextField
 * 
 * Panel Structure
 * -------------------------------------------
 * 					topLine			[Modifiable]
 * 				   middleLine
 * 				   bottomLine		[Modifiable]
 * -------------------------------------------
 */
public class LabelsWithTextField extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_NUM_COLUMNS = 3;

	/** Top line */
	private final TransparentTextArea topLine;
	
	/** Middle line */
	private final TransparentTextField middleLine;
	
	/** Bottom line */
	private final TransparentTextArea bottomLine;
	
	/** Background image */
	private BufferedImage background;
	
	
	/**
	 * Construct a LabelsWithTextField with no
	 * background image
	 */
	public LabelsWithTextField() {
		this(null);
	}
	
	/**
	 * Construct a LabelsWithTextField with the
	 * given image as the background
	 * @param image An image that would be set as
	 * background
	 */
	public LabelsWithTextField(BufferedImage image) {
		this(image, DEFAULT_NUM_COLUMNS);
	}
	
	/**
	 * Construct a LabelsWithTextField with the
	 * given image as a background and the given
	 * number as the max length of the middle text field
	 * @param image An image that would be set as
	 * background
	 * @param columns A maximum number of columns
	 * the middle text field can have
	 */
	public LabelsWithTextField(BufferedImage image, int columns) {
		this(image, columns, false, false);
	}
	
	/**
	 * Construct a LabelsWithTextField with the
	 * given image as a background and the given
	 * number as the max length of the middle text field
	 * Additionally, set the mutability of top and bottom
	 * text area
	 * 
	 * @param image An image that would be set as
	 * background
	 * @param columns A maximum number of columns
	 * the middle text field can have
	 * @param isTopEditable False to set the text
	 * at the top immutable
	 * @param isBottomEditable False to set the text
	 * at the bottom immutable
	 */
	public LabelsWithTextField(BufferedImage image, 
								int columns, 
								boolean isTopEditable, 
								boolean isBottomEditable) {
		// Create the top line
		this.topLine = new TransparentTextArea();
		topLine.setEnabled(isTopEditable);
		
		// Set the max number of columns and center aligned
		this.middleLine = new TransparentTextField();

		// Create the bottom line
		this.bottomLine = new TransparentTextArea();
		bottomLine.setEditable(isBottomEditable);
		
		// Store the background image. putGUIComponentsOnPanel
		// handles the background setting
		this.background = image;
		
		putGUIComponentsOnPanel();
	} 
	
	/*
	 * Put the top, middle, and bottom lines
	 * on the panel
	 */
	private void putGUIComponentsOnPanel() {
		setLayout(new MigLayout("insets 0, fill", "[center]", "[][grow][]"));
		add(topLine, "wrap");
		add(middleLine, "wrap");
		add(bottomLine, "wrap");
	}
	
	/**
	/**
	 * Assign the given String to the top line
	 * @param text A string that would be assigned
	 * to the top line
	 */
	public void setTextTop(String text) {
		topLine.setText(text);
	}
	
	/**
	 * Assign the given String to the bottom line
	 * @param text A string that would be assigned
	 * to the bottom line
	 */
	public void setTextBottom(String text) {
		bottomLine.setText(text);
	}
	
	/**
	 * Return the text user types in the middle 
	 * text field
	 * @return Return the text user types in the
	 * middle text field
	 */
	public String getTextMiddle() {
		return middleLine.getText();
	}
	
	/**
	 * Assign the given String to the middle
	 * text field
	 * @param value A String that would be assigned
	 * to the middle text field
	 */
	public void setTextMiddle(String value) {
		middleLine.setText(value);
	}
	
	/**
	 * Return the middle TransparentTextField
	 * @return Return the middle TransparentTextField
	 */
	public TransparentTextField getTextField() {
		return middleLine;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Dimension getPreferredSize() {
		return background == null ? super.getPreferredSize() : new Dimension(background.getWidth(), background.getHeight());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (background != null) {
			int x = (getWidth() - background.getWidth()) / 2;
			int y = (getHeight() - background.getHeight()) / 2;
			g.drawImage(background, x, y, this);
		}
	}
}