package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.UIComponent.textfield;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

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
	public void setColorTextCenter(Color color, String text) {
		 StyledDocument document = getStyledDocument();
	        
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
		document.setParagraphAttributes(0, document.getLength(), style, false);
		
        try { 
        	document.insertString(document.getLength(), text, style); 
        	}
        catch (BadLocationException e) {
        	 System.err.println("Couldn't insert initial text into text pane.");
        }
	}
	
	/**
	 * Set the given HTML styled text to this
	 * text pane
	 */
	public void setHTMLStyleText(String text) {
		setContentType("text/html");
		setEditable(false);
		HTMLDocument doc = (HTMLDocument)getDocument();
		HTMLEditorKit editorKit = (HTMLEditorKit)getEditorKit();
		try {
			editorKit.insertHTML(doc, doc.getLength(), text, 0, 0, null);
		} catch (BadLocationException e) {
       	 	System.err.println("Couldn't insert initial text into text pane.");
		} catch (IOException e) {
			System.err.println("Couldn't insert invalid text into text pane.");
		}
	}
	
	/**
	 * Clear the text
	 */
	public void clearText() {
		setText("");
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