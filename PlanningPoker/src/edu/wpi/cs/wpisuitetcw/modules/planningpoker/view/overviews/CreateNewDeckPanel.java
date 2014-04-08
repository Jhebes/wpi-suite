/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.overviews;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.AddNewCardController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.controllers.CreateNewDeckController;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.ViewEventManager;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.pokers.Card;

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
	private final ArrayList<Card> cardList;

	// subject to change
	// private final JTextField textboxVal;

	public CreateNewDeckPanel() {
		// sub panels
		topPanel = new JPanel();
		centerPanel = new JPanel();
		bottomPanel = new JPanel();

		// delete these
		topPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

		cardList = new ArrayList<Card>();

		// text labels
		this.labelName = new JLabel(DECK_NAME_LABEL);

		// textfields
		this.textboxName = new JTextField(15);

		// cards
		Card starterCard = new Card();
		cardList.add(starterCard);

		// textboxVal = new JTextField(3);
		// cardList.add(textboxVal);

		// buttons
		this.btnAddCard = new JButton(ADD_CARD_LABEL);
		this.btnCreate = new JButton(CREATE_LABEL_STRING);
		this.btnCancel = new JButton(CANCEL_LABEL_STRING);

		// action listeners
		btnAddCard.addActionListener(new AddNewCardController(this));
		btnCreate.addActionListener(new CreateNewDeckController(this));
		
		//TODO: Make sure that this action listener for btnCreate is correct.
		this.addAction(btnCreate, this);
		this.addAction(btnCancel, this);

		// set up the top panel
		topPanel.add(labelName);
		topPanel.add(textboxName);

		// setup centerPanel
		centerPanel.setLayout(new MigLayout("center, wrap 5", "[] 5 []",
				"[] 5 []"));
		centerPanel.add(btnAddCard, "wrap, center, span");
		centerPanel.add(starterCard);

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
	 * Add a new textbox to the view and the list as well
	 */
	public void addNewCard() {
		ArrayList<Card> cardList = this.getCardList();
		// JTextField txtBoxVal = new JTextField(3);
		Card aCard = new Card();
		cardList.add(aCard);
		this.centerPanel.add(aCard);
		System.out.println("There are " + cardList.size() + " card");
	}

	/**
	 * Adds listener to button
	 * 
	 * @param button
	 * @param panel
	 */
	public void addAction(JButton button, final CreateNewDeckPanel panel) {
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewEventManager.getInstance().removeTab(panel);
			}
		});
	}

	/**
	 * This function extracts all values from textboxes
	 * 
	 * @return an array list with the card values user enters
	 */
	public ArrayList<Integer> getNewDeckValues() {
		ArrayList<Integer> cardValues = new ArrayList<Integer>();
		for (Card aCard : this.cardList) {
			cardValues.add(Integer.parseInt(aCard.getTxtboxValue().getText()));
		}
		return cardValues;
	}

	/**
	 * Confirm that all values entered in textboxes are integers
	 */
	public boolean validateCardValues() {
		// TODO implement this or equivalent validater
		return false;
	}

	/**
	 * @return the textbox field for name of the deck
	 */
	public JTextField getTextboxName() {
		return textboxName;
	}

	/**
	 * 
	 * @return the center panel of this deck creation instance
	 */
	public JPanel getCenterPanel() {
		return this.centerPanel;
	}

	/**
	 * @return a list of textboxes for entering new card value
	 */
	public ArrayList<Card> getCardList() {
		return cardList;
	}

}
