/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.AddNewCardController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.CreateNewDeckController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;

/**
 * @author troyling
 * 
 */
public class CreateNewDeckPanel extends JPanel {
	// constants
	private final String ADD_CARD_LABEL = "[+] New Card";
	private final String CREATE_LABEL_STRING = "Create";
	private final String CANCEL_LABEL_STRING = "Cancel";
	private final String DECK_NAME_LABEL = "Name *";

	// instance fields
	private final JLabel labelName;
	private final JButton btnAddCard;
	private final JButton btnCreate;
	private final JButton btnCancel;
	private final JTextField textboxName;
	private final JPanel topPanel;
	private final JPanel centerPanel;
	private final JPanel bottomPanel;

	// subject to change
	private final JTextField textboxVal;

	public CreateNewDeckPanel() {
		// sub panels
		topPanel = new JPanel();
		centerPanel = new JPanel();
		bottomPanel = new JPanel();

		// delete these
		topPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

		// text labels
		this.labelName = new JLabel(DECK_NAME_LABEL);

		// textfields
		this.textboxName = new JTextField(15);
		textboxVal = new JTextField(3);

		// buttons
		this.btnAddCard = new JButton(ADD_CARD_LABEL);
		this.btnCreate = new JButton(CREATE_LABEL_STRING);
		this.btnCancel = new JButton(CANCEL_LABEL_STRING);
		
		// action listeners
		btnAddCard.addActionListener(new AddNewCardController(this));
		btnCreate.addActionListener(new CreateNewDeckController(this));
		this.addAction(btnCancel, this);

		// set up the top panel
		topPanel.add(labelName);
		topPanel.add(textboxName);

		// setup centerPanel
		centerPanel.setLayout(new MigLayout("center, wrap 5", "[] 5 []", "[] 5 []"));
		centerPanel.add(btnAddCard, "wrap, center, span");
		centerPanel.add(textboxVal);
		
		// setup bottomPanel
		bottomPanel.add(btnCreate);
		bottomPanel.add(btnCancel);
		
		// setup the overal layout
		this.setLayout(new MigLayout("", "", ""));
		this.add(topPanel, "dock north");
		this.add(centerPanel, "dock center");
		this.add(bottomPanel, "center, dock south");
	}

	/**
	 * Add a new textfiled for now
	 * 
	 */
	public void addNewCard(){
		JTextField val = new JTextField(3);
		this.centerPanel.add(val);
	}
	
	
	/**
	 * Adds listener to button
	 * @param button
	 * @param panel
	 */
	public void addAction(JButton button, final CreateNewDeckPanel panel){
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventManager.getInstance().removeTab(panel);
			}
		});
	}
	
	/**
	 * 
	 * @return the center panel of this deck creation instance
	 */
	public JPanel getCenterPanel(){
		return this.centerPanel;
	}
}
