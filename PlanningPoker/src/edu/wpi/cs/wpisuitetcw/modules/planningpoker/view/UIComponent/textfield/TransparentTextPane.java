package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent.textfield;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * A customized JTextField whose background
 * is transparent
 */
public class TransparentTextPane extends JTextPane {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construct a TransparentTextField with
	 * the given text
	 */
	public TransparentTextPane(String text) {
		this();
		setTextCenter(text);
	}

	/**
	 * Construct a TransparentTextField
	 */
	public TransparentTextPane() {
		setOpaque(false);
		setBackground(new Color(255, 255, 255, 0));
		setBorder(null);
	}
	
	/**
	 * Set the given text at the center
	 * @param text A string that would be set
	 * at the center
	 */
	public void setTextCenter(String text) {
		StyledDocument tempText = getStyledDocument();
		SimpleAttributeSet centerAlign = new SimpleAttributeSet();
		StyleConstants.setAlignment(centerAlign, StyleConstants.ALIGN_CENTER);
		tempText.setParagraphAttributes(0, tempText.getLength(), centerAlign, false);
		
		setText(text);
	}
	
	/**
	 * Set the given text at the center
	 * and with given color
	 * @param text A string that would be set
	 * at the center
	 * @param color Color of the text
	 */
	public void setColorText(Color color, String text) {
		Style style = addStyle(text, null);
		StyleConstants.setForeground(style, Color.red);

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		int width = getWidth() - (x + y);
		int height = getHeight() - (x + y);
	
		g.fillRect(0, 0, width, height);
		
		super.paintComponent(g);
	}
}