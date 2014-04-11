package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GetUnimportedRequirementsController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.ImportRequirementController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.models.PlanningPokerRequirement;

public class ImportRequirementsPanel extends JSplitPane {

	private final JPanel rightPanel;
	private final JPanel leftPanel;
	private DefaultTableModel requirementsTableModel;
	private String strName;
	private String strDesc;
	private JTextArea name;
	private JTextArea description;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3141016398279783579L;

	private final int location = 256;
	{
		setDividerLocation(location);
	}

	/**
	 * Create the panel.
	 */
	public ImportRequirementsPanel() {
		leftPanel = new JPanel(new MigLayout());
		rightPanel = new JPanel(new MigLayout());

		// Add panels to main JSplitPane
		this.setRightComponent(rightPanel);
		this.setLeftComponent(leftPanel);
		this.setEnabled(false);

		this.createLeftPanel();
		this.createRightPanel();
	}

	public void createLeftPanel() {
		name = new JTextArea();
		name.setWrapStyleWord(true);
		name.setLineWrap(true);
		name.setBorder(BorderFactory.createEmptyBorder());
		name.setOpaque(false);
		name.setFocusable(false);
		name.setEditable(false);

		description = new JTextArea();
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setBorder(BorderFactory.createEmptyBorder());
		description.setOpaque(false);
		description.setFocusable(false);
		description.setEditable(false);

		leftPanel.add(new JLabel("Name"), "wrap");
		leftPanel.add(name, "wrap");
		leftPanel.add(new JLabel("Description"), "wrap");
		leftPanel.add(description, "wrap");
	}

	public void createRightPanel() {
		requirementsTableModel = ImportRequirementsTableModel.getInstance();
		GetUnimportedRequirementsController.getInstance().refreshData();

		final JTable requirementsTable = new JTable(requirementsTableModel) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5011870013482891222L;
			
			public boolean isCellEditable(int row, int column) {
				return column == 0;
			}
			
			@Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return String.class;
                }
            }
		};
		
		requirementsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				int row = table.getSelectedRow();

				if (row != -1){
					String name = ((PlanningPokerRequirement) table.getValueAt(row, 1)).getName();
					String desc = (String) table.getValueAt(row, 2);
					this.setSuperClassVariables(name, desc);
				}
			}
			
			public void setSuperClassVariables(String name, String desc) {
				strName = name;
				strDesc = desc;
				setReqLabels();
			}
		});
		
		requirementsTable.getTableHeader().setReorderingAllowed(false);
		requirementsTable.setBackground(Color.WHITE);
		// Hide the requirement column, but keep the data
//		requirementsTable.removeColumn(requirementsTable.getColumn(3));
		
		JScrollPane jsp = new JScrollPane(requirementsTable);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new MigLayout());
		
		JButton importButton = new JButton("Import Checked");
		importButton.addActionListener(new ImportRequirementController());

		buttonPanel.add(importButton);

		rightPanel.setLayout(new MigLayout());
		rightPanel.add(jsp, "dock center");
		rightPanel.add(buttonPanel, "dock south");
	}

	public void setReqLabels() {
		name.setText(strName);
		description.setText(strDesc);
	}
}