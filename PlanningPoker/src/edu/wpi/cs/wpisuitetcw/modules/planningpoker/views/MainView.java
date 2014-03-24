package edu.wpi.cs.wpisuitetcw.modules.planningpoker;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.SpringLayout;

public class MainView extends JPanel {

	/**
	 *  Serializable ID
	 */
	private static final long serialVersionUID = 4184001083813964646L;

	/**
	 * Create the panel.
	 */
	
	public MainView() {
		
		//Generate some dummy "sessions" to populate the list of sessions
		String dummySessions[] =
		{
			"Session 1",
			"Session 2",
			"Session 3",
			"Session 4",
			"Session 5"
		};
		
		setLayout(null);
		
		//We may not end up using strings, so I'm going to keep this generic
		@SuppressWarnings({ "unchecked", "rawtypes" })
		
		//Create the list of existing sessions
		JList existingSessionsList = new JList(dummySessions);
		existingSessionsList.setBounds(10, 0, 206, 303);
		existingSessionsList.setFixedCellWidth(300);
		
		//Formatting for the table
		existingSessionsList.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		
		//Button to launch a new planning poker session
		JButton btnCreateNewSession = new JButton("Create New Session");
		btnCreateNewSession.setBounds(337, 144, 222, 67);
		
		
		//Select an already-started session
		JButton btnJoinSession = new JButton("Join Session");
		btnJoinSession.setBounds(10, 319, 206, 40);
		btnJoinSession.setPreferredSize(new Dimension(300, 40));
		
		
		
		add(existingSessionsList);
		add(btnCreateNewSession);
		add(btnJoinSession);

	}
}
