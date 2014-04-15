/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;



/**
 * @author Jenny12593
 *
 */
public class DefaultHomePanel extends JPanel{

	private final String CREATESESSIONSTRING = "Create Session.";
	private final String OPENSESSIONSTRING = "Open Sessions.";
	private final String ALLSESSIONSSTRING = "All Sessions.";
	private final String WELCOMESTRING = "Welcome to Planning Poker! "
			+ "To get started, click ".concat(CREATESESSIONSTRING)			
			+ " To view open sessions click on " .concat(OPENSESSIONSTRING)
			+ " To view all sessions click on " .concat(ALLSESSIONSSTRING);

	public DefaultHomePanel(){
				
		// adds 10 pixels of padding
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
//		this.createStrokeBorder(BasicStroke stroke, Paint paint)
		
//		try {
//		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("A.ttf")));
//		} catch (IOException|FontFormatException e) {
//		     //Handle exception
//		}
		
		// Welcome text area
		JTextArea welcomeTextArea = new JTextArea(5, 15);
		
		Font plainFont = new Font("Serif", Font.PLAIN, 36);
		Font boldFont = new Font("Serif", Font.BOLD, 36);
		welcomeTextArea.setForeground(new Color(0x061692));
		
//		textAreaNewSessions.setFont(boldFont);
//		textAreaNewSessions.setText(NEWSESSIONSTRING);
//		textAreaNewSessions.setWrapStyleWord(true);
//		textAreaNewSessions.setLineWrap(true);
//		textAreaNewSessions.setBorder(BorderFactory.createEmptyBorder());
//		textAreaNewSessions.setOpaque(false);
//		textAreaNewSessions.setFocusable(false);
//		textAreaNewSessions.setEditable(false);
		
		welcomeTextArea.setFont(plainFont);
		welcomeTextArea.setText(WELCOMESTRING);
		welcomeTextArea.setWrapStyleWord(true);
		welcomeTextArea.setLineWrap(true);
		welcomeTextArea.setBorder(BorderFactory.createEmptyBorder());
		welcomeTextArea.setOpaque(false);
		welcomeTextArea.setFocusable(false);
		welcomeTextArea.setEditable(false);
		
		// add the label to the home panel
		this.add(welcomeTextArea);
//		this.add(textAreaNewSessions);
	}
}
