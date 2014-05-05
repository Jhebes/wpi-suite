/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.help;

import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import net.miginfocom.swing.MigLayout;

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
			"How to Use this Guide", // super huge
			"",
			"\n\nWelcome to the Planning Poker Guide. To browse topics, navigate using the"
					+ "\npanel to the left. Click on a topic to have it displayed." // large
	};

	/** constants */
	private final String[] FAQ_TEXT_PANE = {
			"Frequently Asked Questions", // Super huge
			"\n\nWho can view my vote?", // medium
			"\n Once your vote is submitted, your vote remains anonymous and is only"
					+ " \nused for the calculation of the final estimation of a requirement.", // small
			"\n\nWhat is the difference between cancelling and ending a session?", // medium
			"\nWhen a session ends due to the deadline being reached or the administrator"
					+ " \nmanually ending a session, a final estimation is calculated, while cancelling "
					+ "\na session does not generate a final estimation.", // small
			"", // medium
			"" // small
	};

	private final String[] PLANNING_POKER_TEXT = {
			"Planning Poker", // super huge
			"\n\nWhat is Planning Poker?", // large
			"\nPlanning Poker is a software development tool for estimating requirements "
					+ "\nin software development projects. In Planning Poker, each group member "
					+ "\nhas a deck of cards to rank the requirements of a project according to "
					+ "\nhow important they think that task ranks among the others. The project "
					+ "\nadministrators can then use this data to create a final priority list "
					+ "\nfor the project.", // medium
			"\n" + "\n" + "How Does Planning Poker Work?", // large
			"\nIndividual stories and requirements are created by customers or programmers "
					+ "\nare presented for estimation. After a period of time, each participant "
					+ "\nchooses from his own deck the numbered card that represents his estimate "
					+ "\nof how much work is involved in the story under discussion. All estimates "
					+ "\nare kept private until each participant has chosen a card. At that time, "
					+ "\nall estimates are revealed and discussion can begin again.", // medium
			"\n" + "\n" + "Who Can Use Planning Poker?", // large
			"\nAll developers of a team should use Planning Poker. Planning Poker will aid "
					+ "\nthe programming team in prioritizing requirements.", // medium
			"\n\nRoles", // large
			"\nWhen a user creates a session, they become the administrator. The administrator "
					+ "\nhas the ability to add requirements to a session in addition to being able to "
					+ "\nedit the session after it has been created. The administrator also sets the "
					+ "\nfinal estimation for requirements. When a session has been created by the "
					+ "\nadministrator, all users, including the administrator, are able to anonymously "
					+ "\nvote on requirements." // medium
	};

	private final String[] SESSION_TEXT = {
			"Sessions", // super huge
			"\n\nWhat is a Session?",
			"\nTo begin using Planning Poker, an administrator creates a session that contains "
					+ "\nthe requirements that are to be voted upon. A session runs until a session deadline "
					+ "\nhas been reached or the administrator ends a session.", // medium
			"\n\nViewing Sessions", // large
			"\nTo view sessions, click on the Session Overview tab and expand the sessions.", // medium
			"\n\nCreate a Session", // large
			"\nTo create a session click on the \"Create Session\" button, which will bring up a "
					+ "\npage where it is possible to customize a session. A session must have a name, type "
					+ "\nof deck, session description, and set of requirements. The administrator has the "
					+ "\noption of using the default Fibonacci sequence deck, using a previously created deck, "
					+ "\nor creating a new one. It is possible to add a deadline for the voting process by "
					+ "\nselecting the Deadline text box followed by setting an end date and time. Click "
					+ "\nsave to store the session.", // medium
			"\n\nState of Sessions", // large
			"\nOnce a session is created, it can be in three possible states: new, open, or closed, or canceled."
					+ "\nA New session is a state that has been created, but has not yet been activated. "
					+ "\nAn open session is a session that has been activated by the administrator that is "
					+ "\navailable for users to vote upon. A closed session is a session that has been closed "
					+ "\nfor voting for which requirement statistics have been calculated. A canceled session "
					+ "\nis a session that has closed, but does not display requirement statistics.", // medium
			"\n\nEditing a Session", // large
			"\nSessions can be edited by the administrator before users begin voting. To edit a session,"
					+ "\nfirst open the session by selecting the desired session from the Session Overview tree on"
					+ "\nthe left hand side of the Home Page. Once the session is open, the administrator is able"
					+ "\nto make changes to the session and save them by clicking on save changes. The administrator"
					+ "\nalso has the option of discarding the changes by selecting the discard changes button.", // medium
			"\n\nEnding a Session", // large
			"\nThere are two ways in which a session can end. The first occurs automatically when a session "
					+ "\ndeadline has been reached. This option can be enabled when creating a session. The second "
					+ "\nway is by manually selecting to end a session on the edit session panel. Upon session "
					+ "\ncompletion, all the votes are calculated into mean, median, and mode and available for "
					+ "\nfinal estimations.", // medium
			"\n\nCancelling a Session", // large
			"\nAfter a session has been created, the administrator has the ability to cancel the session by"
					+ "\nselecting Cancel on the edit session page. Once the session has been canceled, a final"
					+ "\nestimation will no longer be calculated." };

	private final String[] DECK_TEXT = {
			"Planning Poker Deck", // super huge
			"\n\nWhat is Planning Poker Deck?",
			"\nWhen a session has been created, each user is given a deck of cards. Each "
					+ "\ncard has been previously created by the administrator to display one "
					+ "\nof the valid estimates. Each voter may, for example, be given a deck of "
					+ "\ncards that reads 0, 1, 1, 2, 3, 5, 8, and 13.", // medium
			"\n\nSelecting a Deck", // large
			"\nWhen creating a session, the administrator has four deck selections to "
					+ "\nchoose from. The first is to use the default Fibonacci deck which has "
					+ "\ncards of value 0, 1, 1, 2, 3, 5, 8, and 13. The second is to create a "
					+ "\nnew deck by selecting \"Create New Deck\" from the deck dropdown menu. "
					+ "\nThis gives the administrator the ability to assign numerical values to "
					+ "\na desired amount of cards." // medium
	};

	private final String[] REQUIREMENTS_TEXT = {
			"Requirements", // super huge
			"\n\nWhat is a Requirement?",
			"\nA requirement is a description of the item that is to be voted upon. Previously "
			+ "\ncreated requirements can be imported into a session.", // medium
			"\n\nExporting Requirements", //large
			"\nTo export requirements, select the Requirement Manager tab to view all the requirements. "
			+ "\nSelect multiple requirements by holding down the CTRL key. Once all desired "
			+ "\nrequirements have been selected, click on the Export button and select a folder "
			+ "\nto save the requirements in.", //medium
			"\n\nImporting Requirements", // large
			"\nTo import requirements, select the Requirement Manager tab to view all the requirements. "
			+ "\nClick on the Import button and select the folder in which the requirements are "
			+ "\nsaved in. ", // medium
			"\n\nAdding Requirements", // large
			"\nAdding requirements to a session is done when creating the session. Click on the"
					+ "\nRequirement tab on the right hand side of the page to open the list of imported "
					+ "\nrequirements. You also have the option of manually entering requirements", // medium
			"\n\nCreating Requirements", // large
			"\nCreating requirements can be done when creating or editing a session. To create a "
			+ "\nrequirement, select the Requirements tab and enter a name and description. Click "
			+ "\nAdd Requirement to Session and then Save Changes to update the requirements." // medium
	};

	private final String[] VOTING_TEXT = { "Voting", // super huge
			"\n\nWhen Does Voting Happen?", // large
			"\nVoting happens when users anonymously set a rank to each requirement", // medium
			"\n\nHow To Vote",// large
			"\nWhen a session has become active, users are able to set a rank to each requirement. "
			+ "\nIn order to vote, click on the session under Open Sessions To vote on a certain requirement, "
			+ "\nselect that requirement from the Session Requirements. If the session uses a deck for voting, "
			+ "\nselect a card to represent the rank assigned to the requirement. If the administrator has enabled "
			+ "\nmultiple selections, it is possible to select multiple cards. If no deck has been assigned to the "
			+ "\nsession, the user is able to manually enter a value in the \"Vote Here\" text box.", // medium
			"\n\nSetting a Final Estimation",// large
			"\nOnce the voting process has finished and the session has ended, the administrator "
			+ "\nwill be able to view requirement statistics which include the mean, median, mode,"
			+ "\nand standard deviation. Using this information, the administrator is able to manually "
			+ "\nsubmit a final estimation for each requirement in the final estimation card which will "
			+ "\nthen be reflected under the requirement manager section." // medium
			};

	private final String[] NOTIFICATIONS_TEXT ={
		"Notifications", //super huge
		"\n\nSMS and Email Notifications", //large
		"\nAll Users are able to configure email and SMS notifications when creating their accounts on "
		+ "\nWPI Suite.", // medium
		"\n\nWhen Will Users Receive Notifications?", //large
		"\nUsers receive notifications whenever sessions are opened and closed." //medium
	};
	
	private final String[] SHORTCUTS_TEXT = {
			"Keyboard Shortcuts", // super huge
			"\n\nThe following list contains shortcuts that you can use "
			+ "\nin Planning Poker:", // large
			"\n\nEnter		                      Select an item"
					+ "\nCTRL + W                                Closes a Session"
					+ "\nCTRL + N                                 Opens a Session"
					+ "\nCTRL + S                                  Saves a Session"
					+ "\nCTRL + Shift                            Move to the next tab"
					+ "\nCTRL + Tab + Shift                 Move to the previous tab"
					+ "\nF1 		                       Pull up the Help Page" // medium
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
		// Default Home Panel's borders
		final Border paneEdgeBorder = BorderFactory.createEmptyBorder(10, 10,
				10, 10);

		// sets the panel border
		this.setBorder(paneEdgeBorder);

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
		helpEntries.put(HelpEntry.NOTIFICATIONS, NOTIFICATIONS_TEXT);
		helpEntries.put(HelpEntry.SHORTCUT, SHORTCUTS_TEXT);
	}

	/**
	 * display the help entries
	 */
	private void displayHelp(String[] helpContext) {

		// remove all context
		this.removeAll();

		this.setLayout(new MigLayout());

		// create and set up text pane
		JTextPane textPane = generateHelpPane(helpContext);
		textPane.setEditable(false);
		textPane.setOpaque(false);
		textPane.setCaretPosition(0);

		// add the text
		this.add(textPane, "center");

		// set up the panel
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

		Style s;

		s = doc.addStyle("medium", regular);
		StyleConstants.setFontSize(s, 20);

		s = doc.addStyle("large", Georgia);
		StyleConstants.setFontSize(s, 24);
		StyleConstants.setBold(s, true);
		StyleConstants.setItalic(s, true);

		s = doc.addStyle("super huge", Georgia);
		StyleConstants.setFontSize(s, 34);
		StyleConstants.setBold(s, true);

	}

}
