package edu.wpi.cs.wpisuitetcw.modules.planningpoker.notifications;

import com.wilko.jaim.JaimConnection;

public class IMNotifier {
	// Hostname and port number to connect to.
	//
	private static String HOST = "login.oscar.aol.com";
	private static int PORT = 5190;
	private static String name = "teamcombatwombat";
	private static String pass = "80% done";

	// text for message
	private static String START_MESSAGE = "A new planning poker session has begun! It ends at ";
	private static String END_MESSAGE = "A planning poker session has ended! Its deadline was ";

	public static String createMessage(String notificationType, String deadline) {
		if (notificationType.equals("start")) {
			return START_MESSAGE + deadline + ".";
		} else if (notificationType.equals("end")) {
			return END_MESSAGE + deadline + ".";
		}
		return "";
	}

	public static void sendMessage(String notificationType, String buddy,
			String deadline) {
		String message = createMessage(notificationType, deadline);
		sendMessage(message, buddy);

	}

	public static void sendMessage(String message, String buddy) {
		JaimConnection connection;
		try {
			connection = new JaimConnection(HOST, PORT);
			connection.connect();
			connection.watchBuddy(name);
			connection.logIn(name, pass, 15000);
			connection.sendIM(buddy, message);
			System.out.println("Message successfully sent.");
			connection.logOut();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
