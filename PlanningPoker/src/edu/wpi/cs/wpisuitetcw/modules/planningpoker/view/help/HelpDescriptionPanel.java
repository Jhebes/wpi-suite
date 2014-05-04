/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.help;

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

	/** constants */
	private final String[] DEFAULT_TEXT_PANE = {
			"Frequently Asked Questions", // large
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
	
	private final String[] PLANNING_POKER_TEXT = {
			"Planning Poker", //super huge
			"\nWhat is Planning Poker?", //large
			"\nPlanning Poker is a software development tool for estimating requirements "
			+ "in software development projects. In Planning Poker, each group member "
			+ "has a deck of cards to rank the requirements of a project according to "
			+ "how important they think that task ranks among the others. The project "
			+ "administrators can then use this data to create a final priority list "
			+ "for the project.", //medium
			"\n"
			+ "\n"
			+ "How Does Planning Poker Work?", //large
			"\nIndividual stories and requirements are created by customers or programmers "
			+ "are presented for estimation. After a period of time, each participant "
			+ "chooses from his own deck the numbered card that represents his estimate "
			+ "of how much work is involved in the story under discussion. All estimates "
			+ "are kept private until each participant has chosen a card. At that time, "
			+ "all estimates are revealed and discussion can begin again.", //medium
			"\n"
			+ "\n"
			+ "Who Can Use Planning Poker?", //large
			"\nAll developers of a team should use Planning Poker. Planning Poker will aid "
			+ "the programming team in prioritizing requirements." // medium
	};
	
	private final String[] SESSION_TEXT = {
			"Sessions", //super huge
			"\n\nCreate a Session", //large
			"\nTo create a session click on the top button \"Create a Session\" which will "
			+ "bring up a page where you can customize your session. You must name and "
			+ "create a description of the session in order to begin the "
			+ "session. You have the option of", //medium
			"\n\nEnding a Session", //large
			"\nTo end a session..." //medium
	};
	
	
	
	private final String[] DECK_TEXT = {
			"Planning Poker Deck", //super huge
			"**Describe what a deck is used for**", //medium
			"\n"
			+ "\n"
			+ "Creating a Deck", //large
			"Describe how to create a Deck"		
	};
	
	private final String[] REQUIREMENTS_TEXT = {
			"Requirements", //super huge
			"\n**Describe what a requirement is used for**", //medium
			"\n\nImporting Requirements", //large
			"\n**Describe the process of importing requirements",// medium
			"\nAdding Requirements", //large
			"\n**Describe how to add requirements", //medium
			"\nCreating Requirements", //large
			"**Describe how to create requirements**" //medium
	};
	
	private final String[] VOTING_TEXT = {
			"Voting", //super huge
			"Describe what voting is used for", //large
			"How To Vote",//large
			"Viewing Votes",//large
			"Setting a Final Estimation"//large
	};

	private HashMap<HelpEntry, String[]> helpEntries;

	public HelpDescriptionPanel() {

		// create help entries
		helpEntries = new HashMap<HelpEntry, String[]>();
		storeHelpEntries();

		JTextPane textPane = createTextPane();

		this.add(textPane);
		textPane.setEditable(false);
		this.setEnabled(true);
	}

	/**
	 * stores the entries into the hash map
	 */
	private void storeHelpEntries() {
		helpEntries.put(HelpEntry.DEFAULT, DEFAULT_TEXT_PANE);
		helpEntries.put(HelpEntry.POKER, PLANNING_POKER_TEXT);
		helpEntries.put(HelpEntry.SESSION, SESSION_TEXT);
		helpEntries.put(HelpEntry.DECK, DECK_TEXT);
		helpEntries.put(HelpEntry.REQUIREMENT, REQUIREMENTS_TEXT);
		helpEntries.put(HelpEntry.VOTING, VOTING_TEXT);
	}

	private JTextPane createTextPane() {

		String[] initStyles = { "large", "medium", "small", "medium", "small",
				"medium", "small" };

		JTextPane textPane = new JTextPane();
		StyledDocument doc = textPane.getStyledDocument();
		addStylesToDocument(doc);

		try {
			for (int i = 0; i < DEFAULT_TEXT_PANE.length; i++) {
				doc.insertString(doc.getLength(), DEFAULT_TEXT_PANE[i],
						doc.getStyle(initStyles[i]));
			}
		} catch (BadLocationException ble) {
			System.err.println("Couldn't insert initial text into text pane.");
		}

		return textPane;
	}
	
	
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
