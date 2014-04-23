/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;

/**
 * SessionTableModel is a singleton model that 
 * stores a list of PlanningPokerSessions
 */
public class SessionTableModel extends DefaultTableModel {

	/** Random number for serializing */
	private static final long serialVersionUID = -7397557876939565129L;

	/** First row */
	private final String[] colNames = { "ID", "Name", "Deadline", "Status" };
	
	/** A List of PlanningPokerSessions */
	private List<PlanningPokerSession> sessions = new ArrayList<PlanningPokerSession>();
	
	/** Possible modes of PlanningPokerSession */
	public static enum Mode {OPEN, CLOSED, ALL};
	
	/** Private instance */
	private static SessionTableModel instance;

	/**
	 * Constructs a table session for the overview table.
	 */
	private SessionTableModel() {
		setColumnIdentifiers(colNames);
	}

	/**
	 * Construct if needed and return the instance
	 * of SessionTableModel
	 * @return Return the instance of SessionTableModel
	 */
	public static SessionTableModel getInstance() {
		if (instance == null) {
			instance = new SessionTableModel();
		}
		return instance;
	}
	
	/**
	 * Put all the sessions of the given mode to the model
	 * @param m The mode of the sessions that would be retrieved
	 */
	public void setMode(Mode m){
		ArrayList<PlanningPokerSession> a = SessionStash.getInstance().getSessions();
		switch(m){
			case ALL:
				this.refreshSessions(a);
				break;
			case CLOSED:
				ArrayList<PlanningPokerSession> b = new ArrayList<PlanningPokerSession>();
				for (int i = 0; i < a.size(); ++i){
					if (a.get(i).getStatus().equals("Closed")){
						b.add(a.get(i));
					}
				}
				System.out.println("Found:");
				this.refreshSessions(b);
				break;
			case OPEN:
				ArrayList<PlanningPokerSession> b2 = new ArrayList<PlanningPokerSession>();
				for(int i = 0; i < a.size(); ++i){
					if(a.get(i).getStatus().equals("Open")){
						b2.add(a.get(i));
					}
				}
				System.out.println("Found:");
				this.refreshSessions(b2);
				break;
			default:
				//this.refreshSessions((PlanningPokerSession[]) a.toArray());
				break;
		}
	}
	
	/**
	 * Update the list of sessions
	 */
	public void update(){		
		this.refreshSessions(SessionStash.getInstance().getSessions());
		System.out.println("Success");
	}
	
	/**
	 * Refreshes the sessions.
	 * 
	 * @param sessions
	 *            The new list of sessions
	 */
	public void refreshSessions(List<PlanningPokerSession> sessions) {
		// Remove all the existing data
		this.setDataVector(null, colNames);
		
		this.sessions = sessions;
		
		
		// Stop update if there is no given session
		if (this.sessions == null || this.sessions.size() == 0) {
			return;
		}
		if(this.sessions.size() > 0){
			for(int i = 0; i < this.sessions.size(); ++i){
				if(this.sessions.get(i).getID() == 1){
					this.sessions.remove(i);
				}
			}
		}
		for (PlanningPokerSession session : this.sessions) {
			Date deadline = session.getDeadline();
			String formattedDeadline = "";
			if (deadline != null) {
				formattedDeadline = DateFormat.getInstance().format(deadline);	
			}
			
			Object[] row = {session.getID(), 
							session.getName(),
							formattedDeadline, 
							session.getStatus()};
			this.addRow(row);
		}
	}

	/**
	 * Return a list of PlanningPokerSessions
	 * @return Return a list of PlanningPokerSessions
	 */
	public List<PlanningPokerSession> getSessions() {
		return sessions;
	}

	/**
	 * Assign the given list of PlanningPokerSession to the model's
	 * @param sessions A list of PlanningPokerSession that would be
	 * assigned to the model's
	 */
	public void setSessions(List<PlanningPokerSession> sessions) {
		this.sessions = sessions;
	}

}
