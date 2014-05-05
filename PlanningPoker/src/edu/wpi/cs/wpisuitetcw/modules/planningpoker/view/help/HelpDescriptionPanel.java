/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.help;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 * Contents to be display for the help panel. Content would be instantly updated
 * once a user selects a help item from the tree.
 * 
 */
public class HelpDescriptionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private final String[] HELP_GUIDE_TEXT = {
			"How to Use this Guide", //super huge
			"\n\nWelcome to the Planning Poker Guide. To browse topics, navigate using the" 
			+ "panel to the left. Click on a topic to have it displayed." //large
	};
	
	/** constants */
	private final String[] FAQ_TEXT_PANE = {
			"Frequently Asked Questions", // Super huge
			"\n\nWho can view my vote?", // medium
			"\n Once your vote is submitted, your vote remains anonymous and is only"
					+ " used for the calculation of the final estimation of a requirement.", // small
			"\n\nWhat is the difference between cancelling and ending a session?", // medium
			"\n   When a session ends due to the deadline being reached or the administrator"
					+ " manually ending a session, a final estimation is calculated, while cancelling "
					+ "a session does not generate a final estimation.", // small
			"", // medium
			"" // small
	};

	private final String[] DEFAULT_TEXT_PANE = {
			"Planning Poker", // super huge
			"\nWhat is Planning Poker?", // large
			"\nPlanning Poker is a software development tool for estimating requirements "
					+ "in software development projects. In Planning Poker, each group member "
					+ "has a deck of cards to rank the requirements of a project according to "
					+ "how important they think that task ranks among the others. The project "
					+ "administrators can then use this data to create a final priority list "
					+ "for the project.", // medium
			"\n" + "\n" + "How Does Planning Poker Work?", // large
			"\nIndividual stories and requirements are created by customers or programmers "
					+ "are presented for estimation. After a period of time, each participant "
					+ "chooses from his own deck the numbered card that represents his estimate "
					+ "of how much work is involved in the story under discussion. All estimates "
					+ "are kept private until each participant has chosen a card. At that time, "
					+ "all estimates are revealed and discussion can begin again.", // medium
			"\n" + "\n" + "Who Can Use Planning Poker?", // large
			"\nAll developers of a team should use Planning Poker. Planning Poker will aid "
					+ "the programming team in prioritizing requirements.", // medium
			"Roles", // large
			"When a user creates a session, they become the administrator. The administrator "
					+ "has the ability to add requirements to a session in addition to being able to "
					+ "edit the session after it has been created. The administrator also sets the "
					+ "final estimation for requirements. When a session has been created by the "
					+ "administrator, all users, including the administrator, are able to anonymously "
					+ "vote on requirements." // medium
	};

	private final String[] SESSION_TEXT = {
			"Sessions", // super huge
			"\n\nTo begin using Planning Poker, an administrator creates a session that contains "
					+ "the requirements that are to be voted upon. A session runs until a session deadline "
					+ "has been reached or the administrator ends a session.", // medium
			"\n\nViewing Sessions", // large
			"\nTo view sessions...", // medium
			"\n\nCreate a Session", // large
			"\nTo create a session click on the \"Create Session\" button, which will bring up a "
					+ "page where it is possible to customize a session. A session must have a name, type "
					+ "of deck, session description, and set of requirements. The administrator has the "
					+ "option of using the default Fibonacci sequence deck, using a previously created deck, "
					+ "or creating a new one. It is possible to add a deadline for the voting process by "
					+ "selecting the Deadline text box followed by setting an end date and time. Click "
					+ "save to store the session.", // medium
			"\n\nState of Sessions", // large
			"\nOnce a session has been created, there are four possible states...", // medium
			"\n\nEditing a Session", // large
			"\nSessions can be edited by the administrator before users begin voting. To edit a session,"
					+ "first open the session by selecting the desired session from the Session Overview tree on"
					+ "the left hand side of the Home Page. Once the session is open, the administrator is able"
					+ "to make changes to the session and save them by clicking on save changes. The administrator"
					+ "also has the option of discarding the changes by selecting the discard changes button.", // medium
			"\n\nEnding a Session", // large
			"\nThere are two ways in which a session can end. The first occurs automatically when a session "
					+ "deadline has been reached. This option can be enabled when creating a session. The second "
					+ "way is by manually selecting to end a session on the edit session panel. Upon session "
					+ "completion, all the votes are calculated into mean, median, and mode and available for "
					+ "final estimations.", // medium
			"\n\nCancelling a Session", // large
			"\nAfter a session has been created, the administrator has the ability to cancel the session by"
					+ "selecting Cancel on the edit session page. Once the session has been canceled, a final"
					+ "estimation will no longer be calculated." };

	private final String[] DECK_TEXT = {
			"Planning Poker Deck", // super huge
			"\n\nWhen a session has been created, each user is given a deck of cards. Each "
					+ "card has been previously created by the administrator to display one "
					+ "of the valid estimates. Each voter may, for example, be given a deck of "
					+ "cards that reads 0, 1, 1, 2, 3, 5, 8, and 13.", // medium
			"Selecting a Deck", // large
			"When creating a session, the administrator has four deck selections to "
					+ "choose from. The first is to use the default Fibonacci deck which has "
					+ "cards of value 0, 1, 1, 2, 3, 5, 8, and 13. The second is to create a "
					+ "new deck by selecting \"Create New Deck\" from the deck dropdown menu. "
					+ "This gives the administrator the ability to assign numerical values to "
					+ "a desired amount of cards." // medium
	};

	private final String[] REQUIREMENTS_TEXT = {
			"Requirements", // super huge
			"A requirement is a description of the item that is to be voted upon.", // medium
			"Importing Requirements", // large
			"PENDING...", // medium
			"Adding Requirements", // large
			"Adding requirements to a session is done when creating the session. Click on the"
					+ "Requirement tab on the right hand side of the page to open the list of imported "
					+ "requirements. You also have the option of manually entering requirements", // medium
			"Creating Requirements", // large
			"Creating requirements can be done when creating or editing a session." // medium
	};

	private final String[] VOTING_TEXT = { "Voting", // super huge
			"Describe what voting is used for", // large
			"How To Vote",// large
			"Viewing Votes",// large
			"Setting a Final Estimation"// large
	};

	private final String[] SHORTCUTS_TEXT = {
			"Keyboard Shortcuts", // super huge
			"\n\nThe following list contains shortcuts that you can use in Planning Poker:", // large
			"\nEnter		               Select an item"
					+ "\nCTRL + W                  Closes a Session"
					+ "\nCTRL + N                  Opens a Session"
					+ "\nCTRL + S                  Saves a Session"
					+ "\nCTRL + Shift              Move to the next tab"
					+ "\nCTRL + Tab + Shift        Move to the previous tab"
					+ "\nF1 		 Pull up the Help Page" // medium
	};

	/** styles for the help context */
	String[] STYLES = { "super huge", "large", "medium", "large", "medium",
			"large", "medium", "large", "medium", "large", "medium", "large",
			"medium", "large", "medium", "large", "medium", "large", "medium",
			"large", "medium", "large", "medium", "large", "medium", "large",
			"medium", "large", "medium", "large", "medium" };

	/** hashmap for storing help context */
	private HashMap<HelpEntry, String[]> helpEntries;

	/**
	 * Constructor to create a new help description panel for display help
	 * context
	 */
	public HelpDescriptionPanel() {

		// create help entries
		helpEntries = new HashMap<HelpEntry, String[]>();

		// fill the hashmap with all help contexts
		storeHelpEntries();

		// set up the textpane
		displayHelp(HELP_GUIDE_TEXT);
	}

	/**
	 * Update the panel with the given help entry
	 * 
	 * @param help
	 *            entry
	 */
	public void updateHelp(HelpEntry entry) {
		String[] helpContext = helpEntries.get(entry);
		displayHelp(helpContext);
	}

	/**
	 * stores the entries into the hash map
	 */
	private void storeHelpEntries() {
		helpEntries.put(HelpEntry.PLANNINGPOKER, PLANNING_POKER_TEXT);
		helpEntries.put(HelpEntry.FAQ, FAQ_TEXT_PANE);
		helpEntries.put(HelpEntry.SESSION, SESSION_TEXT);
		helpEntries.put(HelpEntry.DECK, DECK_TEXT);
		helpEntries.put(HelpEntry.REQUIREMENT, REQUIREMENTS_TEXT);
		helpEntries.put(HelpEntry.VOTING, VOTING_TEXT);
		helpEntries.put(HelpEntry.SHORTCUT, SHORTCUTS_TEXT);
	}

	/**
	 * display the help entries
	 */
	private void displayHelp(String[] helpContext) {
		
		
		// remove all context
		this.removeAll();

		this.setLayout(new BorderLayout());
		
		JTextPane textPane = generateHelpPane(helpContext);
		
		// add the text
		this.add(textPane, BorderLayout.CENTER);
		textPane.setEditable(false);
		textPane.setOpaque(true);
		this.setEnabled(true);
		this.validate();
		this.updateUI();
	}

	/**
	 * create a textpane with the given help entry
	 * 
	 * @param helpContext
	 * @return textpane with stylized context
	 */
	private JTextPane generateHelpPane(String[] helpContext) {
		JTextPane textPane = new JTextPane();

		// stylize the text pane
		StyledDocument doc = textPane.getStyledDocument();
		addStylesToDocument(doc);

		try {
			for (int i = 0; i < helpContext.length; i++) {
				doc.insertString(doc.getLength(), helpContext[i],
						doc.getStyle(STYLES[i]));
			}
		} catch (BadLocationException ble) {
			System.err.println("Couldn't insert initial text into text pane.");
		}
		return textPane;
	}

	/**
	 * Stylize the given document
	 * 
	 * @param doc
	 */
	protected void addStylesToDocument(StyledDocument doc) {
		// Initialize some styles.
		Style def = StyleContext.getDefaultStyleContext().getStyle(
				StyleContext.DEFAULT_STYLE);

		Style regular = doc.addStyle("regular", def);
		StyleConstants.setFontFamily(def, "SansSerif");

		Style Georgia = doc.addStyle("georgia", def);
		StyleConstants.setFontFamily(def, "Georgia");

		Style s = doc.addStyle("italic", regular);
		StyleConstants.setItalic(s, true);
		//
		s = doc.addStyle("bold", regular);
		StyleConstants.setBold(s, true);

		s = doc.addStyle("small", regular);
		StyleConstants.setFontSize(s, 19);

		s = doc.addStyle("medium", regular);
		StyleConstants.setFontSize(s, 20);
		StyleConstants.setBold(s, true);

		s = doc.addStyle("large", Georgia);
		StyleConstants.setFontSize(s, 24);
		StyleConstants.setBold(s, true);

		s = doc.addStyle("super huge", Georgia);
		StyleConstants.setFontSize(s, 34);
		StyleConstants.setBold(s, true);

	}

}
