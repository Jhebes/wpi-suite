/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * @author Jenny12593
 * 
 */
public class DefaultHomePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String CREATESESSIONSTRING = "Create Session.";
	private final String OPENSESSIONSTRING = "Open Sessions.";
	private final String ALLSESSIONSSTRING = "All Sessions.";
	private final String CLOSEDSESSIONSTRING = "Closed Sessions";
	private final String WELCOMESTRING = "Welcome to Planning Poker!\n"
			+ "To get started, click ".concat(CREATESESSIONSTRING)
			+ "\nTo view all the sessions click on ".concat(ALLSESSIONSSTRING)
			+ "\nTo view open sessions click on ".concat(OPENSESSIONSTRING)
			+ "\nTo view closed sessions click on ".concat(CLOSEDSESSIONSTRING);

	public DefaultHomePanel() {

		// adds 10 pixels of padding
		// this.setBorder(new EmptyBorder(10, 10, 10, 10));

		// sets the panel border
		this.setBorder(BorderFactory.createEtchedBorder(Color.lightGray,
				Color.blue));

		// this.createStrokeBorder(BasicStroke stroke, Paint paint)

		// try {
		// GraphicsEnvironment ge =
		// GraphicsEnvironment.getLocalGraphicsEnvironment();
		// ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new
		// File("A.ttf")));
		// } catch (IOException|FontFormatException e) {
		// //Handle exception
		// }

		// Welcome text area
		JTextArea welcomeTextArea = new JTextArea(5, 30);

		Font plainFont = new Font("Serif", Font.PLAIN, 36);
		Font boldFont = new Font("Serif", Font.BOLD, 36);

		// text color
		welcomeTextArea.setForeground(new Color(0x061692));

		// textAreaNewSessions.setFont(boldFont);
		// textAreaNewSessions.setText(NEWSESSIONSTRING);
		// textAreaNewSessions.setWrapStyleWord(true);
		// textAreaNewSessions.setLineWrap(true);
		// textAreaNewSessions.setBorder(BorderFactory.createEmptyBorder());
		// textAreaNewSessions.setOpaque(false);
		// textAreaNewSessions.setFocusable(false);
		// textAreaNewSessions.setEditable(false);

		welcomeTextArea.setFont(plainFont);
		welcomeTextArea.setText(WELCOMESTRING);
		welcomeTextArea.setWrapStyleWord(true);
		welcomeTextArea.setLineWrap(true);
		welcomeTextArea.setBorder(BorderFactory.createEmptyBorder());
		welcomeTextArea.setOpaque(false);
		welcomeTextArea.setFocusable(false);
		welcomeTextArea.setEditable(false);

		// add the text area to the home panel
		this.add(welcomeTextArea);
	}
}
