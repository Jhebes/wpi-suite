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
 * TODO: add description
 */
public class SessionTableModel extends DefaultTableModel {

	/**
	 * random number for serializing
	 */
	private static final long serialVersionUID = -7397557876939565129L;

	private static SessionTableModel instance;
	private final String[] colNames = { "ID", "Name", "Deadline", "Status" };
	private List<PlanningPokerSession> sessions = new ArrayList<PlanningPokerSession>();

	/**
	 * Constructs a table session for the overview table.
	 */
	private SessionTableModel() {
		setColumnIdentifiers(colNames);
	}

	public static SessionTableModel getInstance() {
		if (instance == null) {
			instance = new SessionTableModel();
		}
		return instance;
	}

	public static enum Mode {OPEN, CLOSED, ALL};
	
	public void setMode(Mode m){
		
		ArrayList<PlanningPokerSession> a = SessionStash.getInstance().getSessions();
		switch(m){
			case ALL:
				this.refreshSessions(a);
				break;
			case CLOSED:
				ArrayList<PlanningPokerSession> b = new ArrayList<PlanningPokerSession>();
				for(int i = 0; i < a.size(); ++i){
					if(a.get(i).getStatus().equals("Closed")){
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
		this.setDataVector(null, colNames);
		this.sessions = sessions;
		if (sessions == null) {
			return;
		}
		for (PlanningPokerSession session : sessions) {
			Date deadline = session.getDeadline();
			String formattedDeadline = "";
			if (deadline != null) {
				formattedDeadline = DateFormat.getInstance().format(deadline);	
			}
			
			Object[] row = { 
				session.getID(), 
				session.getName(),
				formattedDeadline, 
				session.getStatus()
			};
			this.addRow(row);
		}
	}

	public List<PlanningPokerSession> getSessions() {
		return sessions;
	}

	public void setSessions(List<PlanningPokerSession> sessions) {
		this.sessions = sessions;
	}

}
