package edu.wpi.cs.wpisuitetcw.modules.planningpoker.notifications;

import java.util.LinkedHashMap;

import com.plivo.helper.api.client.RestAPI;
import com.plivo.helper.api.response.message.MessageResponse;
import com.plivo.helper.exception.PlivoException;

import edu.wpi.cs.wpisuitetcw.modules.planningpoker.ConfigLoader;
import edu.wpi.cs.wpisuitetcw.modules.planningpoker.exceptions.ConfigLoaderError;


public class SMSNotifier {

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

	public static void sendMessage(String notificationType, String phoneNumber,
			String deadline) {
		String message = createMessage(notificationType, deadline);
		sendMessage(message, phoneNumber);
	}

	public static void sendMessage(String message, String phoneNumber) {
		String authId, authToken, src;
		try {
			authId = ConfigLoader.getPlivoAuthId();
			authToken = ConfigLoader.getPlivoAuthToken();
			src = ConfigLoader.getPlivoPhoneNumber();
		} catch (ConfigLoaderError e) {
			e.printStackTrace();
			return;
		}
		
		
		RestAPI api = new RestAPI(authId, authToken, "v1");

		LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
		parameters.put("src", src);
		parameters.put("dst", phoneNumber);
		parameters.put("text", message);
		parameters.put("url", "http://server/message/notification/");

		try {
			MessageResponse msgResponse = api.sendMessage(parameters);
			System.out.println(msgResponse.apiId);
			if (msgResponse.serverCode == 202) {
				System.out.println(msgResponse.messageUuids.get(0).toString());
			} else {
				System.out.println(msgResponse.error);
			}
		} catch (PlivoException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
}
