package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent.textfield;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JTextField;

/**
 * A customized JTextField whose background
 * is transparent
 */
public class TransparentTextField extends JTextField {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construct a TransparentTextField with
	 * the given text
	 */
	public TransparentTextField(String text) {
		this();
		setText(text);
	}
	
	/**
	 * Construct a TransparentTextField
	 */
	public TransparentTextField() {
		setOpaque(false);
		setBackground(new Color(255, 255, 255, 0));
		setBorder(null);
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