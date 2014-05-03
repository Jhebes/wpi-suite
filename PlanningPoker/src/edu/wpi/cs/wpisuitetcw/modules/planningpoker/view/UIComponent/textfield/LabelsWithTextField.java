package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent.textfield;

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
	 */
	public LabelsWithTextField(BufferedImage image) {
		this.topLine = new TransparentTextArea();

		this.middleLine = new TransparentTextField();
		middleLine.setHorizontalAlignment(JTextField.CENTER);

		this.bottomLine = new TransparentTextArea();
		
		this.background = image;
		
		putGUIComponentsOnPanel();
	}

	/*
	 * Put the top, middle, and bottom lines
	 * on the panel
	 */
	private void putGUIComponentsOnPanel() {
		setLayout(new MigLayout("insets 0, fill", "", "[][grow][]"));
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
