/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableCellRenderer;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.GetAllSessionsController;

/**
 * This table handles the display of the sessions table.
 * 
 * @author Hoang Ngo
 *
 */
public class OverviewSessionTable extends JTable {
	private OverviewTableSessionTableModel tableModel;
	private boolean initialized;	// Avoid loading data from not-yet-loadded model	
	private Border paddingBorder;

	/**
	 * Construct the table view
	 * 
	 * @param tableModel	The data model for the session table
	 */
	public OverviewSessionTable(OverviewTableSessionTableModel tableModel) {
		// Set the given model to the table
		this.tableModel = tableModel;
		setModel(tableModel);
		
		// Restrict single row selection and Allow multiple selection
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setDragEnabled(true);
        setDropMode(DropMode.ON);
        
        // Prevent rearranging columns
		getTableHeader().setReorderingAllowed(false);
		
		setAutoCreateRowSorter(true);
		setFillsViewportHeight(true);
		
		// Set the padding
		paddingBorder = BorderFactory.createEmptyBorder(0, 4, 0, 0);
		
		// Set initialized to false since the model is not ready to load
		initialized = false;

		/* Create double-click event listener */
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				if (getRowCount() > 0) {
					int mouseY = e.getY();
					Rectangle lastRow = getCellRect(getRowCount() - 1, 0, true);
					int lastRowY = lastRow.y + lastRow.height;
					repaint(0, 0, getWidth(), getHeight());

					if (mouseY > lastRowY) {
						getSelectionModel().clearSelection();
						repaint(0, 0, getWidth(), getHeight());
					}
				}
			}
		});
		 System.out.println("finished constructing the table");
	}
	
	/**
	 * Always return false to prevent user editing data
	 * directly on the session table
	 */
	public boolean isCellEditable(int row, int column) {
		return false;
	};
		
	/**
	 * Repaint the whole table when the model and the table are
	 * already constructed
	 */
	@Override
	public void repaint() {
		try {
			if (!initialized) {			// Prevent get sessions from not-yet-constructed model
				GetAllSessionsController.getInstance().retrieveSessions();
				initialized = true;
			}
		} catch (Exception e) {}
		
		// Repaint to avoid broken table bug
		repaint(0, 0, getWidth(), getHeight());
	}
	
	@Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component tempComp = super.prepareRenderer(renderer, row, column);

        if (JComponent.class.isInstance(tempComp)) {
            ((JComponent) tempComp).setBorder(paddingBorder);
        }
		return tempComp;
    }
}
