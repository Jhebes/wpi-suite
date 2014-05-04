/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Combat Wombat
 ******************************************************************************/

package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews.viewSessionComp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.AddRequirementToSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.EditRequirementDescriptionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveAllRequirementsToAllController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveAllRequirementsToCurrentSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveRequirementToAllController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.req.MoveRequirementToCurrentSessionController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.stash.SessionStash;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.session.tabs.SessionRequirementPanel;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.tablemanager.RequirementTableManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.requirements.ScrollablePanel;

/**
 * The panel for adding requirements to a session.
 */
public class ViewSessionReqPanel extends JPanel {
	

}
