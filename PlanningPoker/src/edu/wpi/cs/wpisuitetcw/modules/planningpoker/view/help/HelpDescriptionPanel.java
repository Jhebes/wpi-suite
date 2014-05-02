/**
 * 
 */
package edu.wpi.cs.wpisuitetcw.modules.planningpoker.view.help;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 * @author Jenny12593
 *
 */
public class HelpDescriptionPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
		
	public HelpDescriptionPanel(){
		
		JTextPane textPane = createTextPane();
		
		this.add(textPane);
		textPane.setEditable(false);
		this.setEnabled(true);
	}
	
	private JTextPane createTextPane() {

		String[] homeTextPane = {
				"Frequently Asked Questions", // large
				"\n\nWho can view my vote?", // medium
				"\n Once your vote is submitted, your vote remains anonymous and is only"
				+ " used for the calculation of the final estimation of a requirement.", //small
				"\n\nWhat is the difference between cancelling and ending a session?", // medium
				"\n   When a session ends due to the deadline being reached or the administrator"
				+ " manually ending a session, a final estimation is calculated, while cancelling "
				+ "a session does not generate a final estimation.", //small
				"", //medium
				"" //small
				
		};

		String[] initStyles = {"large", "medium", "small", "medium", "small",
								"medium", "small"};

		JTextPane textPane = new JTextPane();
		StyledDocument doc = textPane.getStyledDocument();
		addStylesToDocument(doc);

		try {
			for (int i = 0; i < homeTextPane.length; i++) {
				doc.insertString(doc.getLength(), homeTextPane[i],
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
		StyleConstants.setBold(s,true);
		
		s = doc.addStyle("large", Georgia);
		StyleConstants.setFontSize(s, 24);
		StyleConstants.setBold(s, true);

		s = doc.addStyle("super huge", Georgia);
		StyleConstants.setFontSize(s, 34);
		StyleConstants.setBold(s, true);

	}

}
