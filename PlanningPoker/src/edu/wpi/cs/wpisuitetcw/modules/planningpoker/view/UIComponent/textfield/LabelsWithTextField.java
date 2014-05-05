package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent.textfield;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	private final TransparentTextPane topLine;
	private boolean isTopLineNeeded;
	
	/** Middle line */
	private final TransparentTextField middleLine;
	private boolean isMiddleLineNeeded;
	
	/** Bottom line */
	private final TransparentTextPane bottomLine;
	private boolean isBottomLineNeeded;
	
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
		this.topLine = new TransparentTextPane();
		topLine.setEditable(isTopEditable);
		topLine.setHighlighter(null);
		isTopLineNeeded = true;

		// Set the max number of columns and center aligned
		this.middleLine = new TransparentTextField();
		middleLine.setFont(new Font("SansSerif", Font.BOLD, 80));
		middleLine.setHorizontalAlignment(JTextField.CENTER);
		isMiddleLineNeeded = true;

		// Create the bottom line
		this.bottomLine = new TransparentTextPane();
		bottomLine.setEditable(isBottomEditable);
		bottomLine.setHighlighter(null);
		isBottomLineNeeded = true;		

		// Store the background image. putGUIComponentsOnPanel
		// handles the background setting
		this.background = image;
		if (background == null) {
			setBackground(new Color(255, 255, 255, 255));
		}
		
		putGUIComponentsOnPanel();
	} 
	
	/*
	 * Put the top, middle, and bottom lines
	 * on the panel
	 */
	private void putGUIComponentsOnPanel() {
		removeAll();
		
		setLayout(new MigLayout("insets 0, fill", "[center]", generateRowConstrain()));
		if (isTopLineNeeded) {
			add(topLine, "growx, height 20px!, wrap");
		}
		if (isMiddleLineNeeded) {
			add(middleLine, ", grow, wrap");
		}
		if (isBottomLineNeeded) {
			add(bottomLine, "growx, height 20px!, wrap");
		}
	}
	
	/**
	 * Generate the row constrain base on
	 * the existence of the top, middle, bottom
	 * line
	 * @return A row constrain for MigLayout
	 */
	private String generateRowConstrain() {
		String result = "";
		
		if (isTopLineNeeded) result += "[]0";
		if (isMiddleLineNeeded) result += "[grow]0";
		if (isBottomLineNeeded) result += "[]";
		
		return result;
	}
	
	/**
	 * Remove the top line
	 */
	public void removeTop() {
		isTopLineNeeded = false;
		putGUIComponentsOnPanel();
	}
	
	/**
	/**
	 * Assign the given String to the top line
	 * @param text A string that would be assigned
	 * to the top line
	 */
	public void setTextTop(String text) {
		topLine.setHTMLStyleText(text);
	}
	

	/**
	 * Set the text at the top line to ""
	 */
	public void clearTopText() {
		topLine.clearText();
	}
	
	/**
	 * Assign the given String to the bottom line
	 * @param text A string that would be assigned
	 * to the bottom line
	 */
	public void setCenterTextBottom(String text) {
		bottomLine.setTextCenter(text);
	}
	
	/**
	 * Assign the given String to the bottom line
	 * @param text A string that would be assigned
	 * to the bottom line
	 * @param color Color of the text
	 */
	public void setTextBottom(Color color, String text) {
		bottomLine.setColorTextCenter(color, text);
	}
	
	/**
	 * Assign the given HTML styled String to the bottom line
	 * @param text A string that would be assigned
	 * to the bottom line
	 */
	public void setTextBottom(String text) {
		bottomLine.setHTMLStyleText(text);
	}
	
	/**
	 * Clear the text at the bottom
	 */
	public void clearBottomText() {
		bottomLine.clearText();
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