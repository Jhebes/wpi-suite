package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.vote.AddVoteController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.vote.GetRequirementsVotesController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.entitymanagers.ViewSessionTableManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerSession;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerVote;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;

import javax.swing.JTabbedPane;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class SessionInProgressPanel extends JSplitPane {

	private PlanningPokerSession session;
	private JTextField vote;
	private JLabel name;
	private JLabel description;
	private JLabel deadline;
	private JLabel lblReqName;
	private JLabel lblReqDesc;
	private PlanningPokerRequirement[] reqsList;
	private JButton btnSubmit;
	private String selectedReqName;
	private JTable reqsViewTable;
	private ViewSessionTableManager reqsViewTableManager = new ViewSessionTableManager();
	public JList voteList;
	private String reqName;
	private String reqDescription;

	/**
	 * Create the panel.
	 */
	public SessionInProgressPanel(final PlanningPokerSession session) {
		this.session = session;
		this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		// Set up Session Info Panel
		JPanel LeftPanel = new JPanel();
		LeftPanel.setLayout(new BoxLayout(LeftPanel, BoxLayout.Y_AXIS));

		// Padding
		Component verticalStrut = Box.createVerticalStrut(10);
		LeftPanel.add(verticalStrut);

		// "Session Info" label
		JLabel lblSessionInfo = new JLabel("Session Info:");
		lblSessionInfo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LeftPanel.add(lblSessionInfo);

		// Padding
		Component verticalStrut2 = Box.createVerticalStrut(20);
		LeftPanel.add(verticalStrut2);

		// "Name" label
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LeftPanel.add(lblName);

		// Call setter for session name (TBR)
		setSessionName(this.session.getName());
		LeftPanel.add(name);

		// Padding
		Component verticalStrut3 = Box.createVerticalStrut(20);
		LeftPanel.add(verticalStrut3);

		// "Description" label
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LeftPanel.add(lblDescription);

		// Call setter for session description (TBR)
		setSessionDescription(this.session.getDescription());
		LeftPanel.add(description);

		// Padding
		Component verticalStrut4 = Box.createVerticalStrut(20);
		LeftPanel.add(verticalStrut4);

		// "Deadline" label
		JLabel lblDate = new JLabel("Deadline:");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LeftPanel.add(lblDate);

		// Call setter for session deadline (TBR)
		setSessionDeadline(this.session.getDeadline().toString());
		LeftPanel.add(deadline);

		// Set up Reqs Panel
		JPanel requirementsPanel = new JPanel();
		requirementsPanel.setLayout(new BoxLayout(requirementsPanel,
				BoxLayout.X_AXIS));

		// Split out panes
		JSplitPane splitTopBottom = new JSplitPane();
		splitTopBottom.setResizeWeight(0.8);
		splitTopBottom.setOrientation(JSplitPane.VERTICAL_SPLIT);

		// Set up tabs at bottom
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		// Set up "Stats Tab"
		JPanel statsTab = new JPanel();
		tabbedPane.addTab("Statistics", null, statsTab, null);
		statsTab.setLayout(new GridLayout(1, 0, 0, 0));

		// Holder label (TBM)
		JLabel lblCurrentEstimate = new JLabel("Current Estimate:");
		statsTab.add(lblCurrentEstimate);

		// Holder label (TBM)
		JLabel lblNumberOfVotes = new JLabel("Number of Votes:");
		statsTab.add(lblNumberOfVotes);

		JList voteList = new JList();
		statsTab.add(voteList);

		// Set up "Vote Tab"
		JPanel voteTab = new JPanel();
		tabbedPane.addTab("Voting", null, voteTab, null);

		// "Estimate" label
		JLabel lblEstimate = new JLabel("Estimate:");
		voteTab.add(lblEstimate);

		// Text box for vote
		vote = new JTextField(10);
		voteTab.add(vote);

		// Submit button
		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new AddVoteController(this, this.session));
		voteTab.add(btnSubmit);

		// Split into Reqs list and Reqs info
		JSplitPane splitLeftRight = new JSplitPane();
		splitLeftRight.setResizeWeight(0.8);
		splitLeftRight.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		JPanel reqsView = new JPanel();

		// Extract the requirements from the table provided by
		// ViewSessionTableManager and converts them to list
		ArrayList<String> testReqs = new ArrayList<String>();
		ViewSessionTableManager a = new ViewSessionTableManager();
		ViewSessionTableModel v = a.get(this.session.getID());
		Vector vector = v.getDataVector();
		for (int i = 0; i < vector.size(); ++i) {
			testReqs.add((String) (((Vector) vector.elementAt(i)).elementAt(1)));
		}
		String[] reqArr = new String[testReqs.size()];
		for (int i = 0; i < testReqs.size(); ++i) {
			reqArr[i] = testReqs.get(i);
		}

		reqsViewTable = new JTable(){
			public boolean isCellEditable(int row, int column){
		        return false;
		   }
		};
		reqsViewTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				int row = table.getSelectedRow();
				
				if (row != -1){
					ViewSessionTableManager m = new ViewSessionTableManager();
					Vector v = m.get(session.getID()).getDataVector();
					String name = (String)((Vector) v.elementAt(row)).elementAt(1);
					PlanningPokerRequirement r = session.getReqByName(name);
					this.setSuperClassVariables(name, r.getDescription());
				}
				
			}
			public void setSuperClassVariables(String name, String desc){
				System.out.println(name);
				System.out.println(desc);			
				reqName = name;
				reqDescription = desc;
				setReqLabels();
			}
		});
		
		this.getReqsViewTable();
		//this.reqsViewTable.
		reqsView.setLayout(new BorderLayout(0, 0));
		reqsView.add(reqsViewTable);

		// Set all components
		splitLeftRight.setLeftComponent(reqsView);
		
		JLabel lblNewLabel = new JLabel("Requirements (ID, Name, Priority)");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		reqsView.add(lblNewLabel, BorderLayout.NORTH);

		splitTopBottom.setTopComponent(splitLeftRight);
		
		JPanel panel = new JPanel();
		splitLeftRight.setRightComponent(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Component verticalStrut_1 = Box.createVerticalStrut(10);
		panel.add(verticalStrut_1);
		
		JLabel lblNewLabel_1 = new JLabel("Requirement Detail:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_2);
		
		JLabel label = new JLabel("Name:");
		label.setAlignmentY(Component.TOP_ALIGNMENT);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(label);
		
		lblReqName = new JLabel("", SwingConstants.LEFT);
		panel.add(lblReqName);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_3);
		
		JLabel descLabel = new JLabel("Description:");
		descLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(descLabel);

		lblReqDesc = new JLabel("", SwingConstants.LEFT);
		panel.add(lblReqDesc);
		splitTopBottom.setBottomComponent(tabbedPane);

		setLeftComponent(LeftPanel);
		setRightComponent(splitTopBottom);
	}

	/**
	 * 
	 * @return Session Model for this Panel
	 */
	public PlanningPokerSession getSession() {
		return session;
	}

	/**
	 * 
	 * @param session
	 */
	public void setSession(PlanningPokerSession session) {
		this.session = session;
	}

	/**
	 * 
	 * @param sessionName
	 */
	public void setSessionName(String sessionName) {
		name = new JLabel(sessionName, JLabel.LEFT);
	}

	public void setSessionDescription(String sessionDescription) {
		description = new JLabel("<html>" + sessionDescription + "</html>",
				JLabel.LEFT);
	}

	/**
	 * 
	 * @param sessionDeadlineDate
	 *            Deadline Date (mm/dd/yyyy) of Session as a String
	 */
	public void setSessionDeadline(String sessionDeadlineDate) {
		deadline = new JLabel("<html>" + sessionDeadlineDate + 
				 "</html>", JLabel.LEFT);
	}

	/**
	 * 
	 * @return List of Reqs for this session
	 */
	public PlanningPokerRequirement[] getReqsList() {
		return reqsList;
	}

	/**
	 * 
	 * @param reqsList
	 */
	public void setReqsList(PlanningPokerRequirement[] reqsList) {
		this.reqsList = reqsList;
	}

	/**
	 * 
	 * @return Requirement Name selected in the list
	 */
	public String getSelectedRequirement() {
		return selectedReqName;
	}

	/**
	 * 
	 * @return vote parsed as an integer
	 */
	public int getVote() {
		return Integer.getInteger(vote.toString());
	}
	/**
	 * sets the reqsViewTable with the appropriate information
	 */
	public void getReqsViewTable(){
		String[] headers = {"ID","Name","Priority"};
		DefaultTableColumnModel columnModel = new DefaultTableColumnModel();
		TableColumn colID = new TableColumn();
		colID.setHeaderValue("ID");
		columnModel.addColumn(colID);
		TableColumn colName = new TableColumn();
		colName.setHeaderValue("Name");
		columnModel.addColumn(colName);
		TableColumn colPriority = new TableColumn();
		colPriority.setHeaderValue("Priority");
		columnModel.addColumn(colPriority);
		reqsViewTable.setColumnModel(columnModel);
		JTableHeader tableHeader = new JTableHeader();
		tableHeader.setColumnModel(columnModel);
		reqsViewTable.setTableHeader(tableHeader);  
		reqsViewTable.setModel(this.reqsViewTableManager.get(this.session.getID()));
		
	}
	
	public void setReqLabels() {
		lblReqName.setText("<html>"+reqName+"</html>");
		lblReqDesc.setText("<html>"+reqDescription+"</html>");
	}
}