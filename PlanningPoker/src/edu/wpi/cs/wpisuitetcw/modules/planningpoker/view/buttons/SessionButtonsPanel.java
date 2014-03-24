/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.buttons;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;

/**
 * Sets up Session button panel
 * @author troyling, Jake, Zack
 *
 */
public class SessionButtonsPanel extends ToolbarGroupView {
	private final JPanel sessionPanel = new JPanel();
	private final JButton createSession;
	private final JButton joinSession;
	
	public SessionButtonsPanel() {
		super("");
		
		// set up session panel 
		this.sessionPanel.setLayout(new BoxLayout(sessionPanel, BoxLayout.X_AXIS));
		
		// create the buttons
		this.createSession = new JButton("<html>Create<br />Session</html>");
		//@TODO add button listeners to the createSession button
		
		this.joinSession = new JButton("<html>Join<br />Session</html>");
		//@TODO add button listeners to the joinSession button
		
		//@TODO add the image later
//		try {
//		    Image img = ImageIO.read(new FileInputStream("resources/img/new_session.png"));
//		    createSession.setIcon(new ImageIcon(img));
//		    joinSession.setIcon(new ImageIcon(img));
//		       
//		} catch (IOException ex) {}
		
		sessionPanel.add(this.createSession);
		sessionPanel.add(this.joinSession);
		sessionPanel.setOpaque(false);
		
		this.add(sessionPanel);
	}
	
	/**
	 * Method getCreateSessionButton.
	 * 
	 * @return JButton
	 */
	public JButton getCreateSessionButton() {
		return this.createSession;
	}
	
	/**
	 * Method getJoinSessionButton.
	 * @return JButton */
	public JButton getJoinSessionButton() {
		return this.joinSession;
	}
}

