/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.AddNewCardController;

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
	// private final JPanel leftPanel;
	// private final JPanel rightPanel;
	// private final JPanel container;
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
		
		btnAddCard.addActionListener(new AddNewCardController(this));

		// set up the right panel
		this.setLayout(new MigLayout("", "", ""));

		topPanel.add(labelName);
		topPanel.add(textboxName);
		this.add(topPanel, "dock north");
		// this.add(Box.createVerticalStrut(40));

		// this.add(labelName, "left");
		// this.add(textboxName, "left, wrap");
		centerPanel.setLayout(new MigLayout("wrap 5", "[] 5 []", "[] 5 []"));
		centerPanel.add(btnAddCard, "wrap, center, span");
		centerPanel.add(textboxVal);
		this.add(centerPanel, "dock center");

		bottomPanel.add(btnCreate);
		bottomPanel.add(btnCancel);
		this.add(bottomPanel, "center, dock south");

		// rightpanel
		// rightPanel.add(container);

		// this.setLeftComponent(leftPanel);
		// this.setRightComponent(rightPanel);
		// this.setDividerLocation(180);
		// this.setEnabled(false);
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
	 * 
	 * @return the center panel of this deck creation instance
	 */
	
	public JPanel getCenterPanel(){
		return this.centerPanel;
	}
}
