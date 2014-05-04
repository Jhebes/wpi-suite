package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent.textfield;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JTextArea;

/**
 * A customized JTextArea which has transparent
 * background
 */
public class TransparentTextArea extends JTextArea {
	
	public TransparentTextArea() {
		setOpaque(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(255, 255, 255, 0));
		
		Insets insets = getInsets();
		int x = insets.left;
		int y = insets.top;
		int width = getWidth() - (x + y);
		int height = getHeight() - (x + y);
		
		g.fillRect(0, 0, width, height);
		
		super.paintComponent(g);
	}

}
