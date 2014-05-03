package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent.textfield;

import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JTextArea;
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

	private static final int DEFAULT_TEXT_LENGTH = 3;

	/** Top line */
	private final TransparentTextArea topLine;
	
	/** Middle line */
	private final JTextField middleLine;
	
	/** Bottom line */
	private final TransparentTextArea bottomLine;
	
	/**
	 * Construct a LabelsWithTextField with
	 * 3 as the maximum text length
	 */
	public LabelsWithTextField() {
		this(DEFAULT_TEXT_LENGTH);
	}
	
	/**
	 * Construct a LabelsWithTextField with the
	 * given max text length of the middle JTextField
	 * @param maxTextLength
	 */
	public LabelsWithTextField(int maxTextLength) {
		this.topLine = new TransparentTextArea();

		this.middleLine = new JTextField(maxTextLength);
		middleLine.setHorizontalAlignment(JTextField.CENTER);

		this.bottomLine = new TransparentTextArea();
		
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
}
