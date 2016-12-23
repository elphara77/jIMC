package be.rl.j.imc.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author rl
 */
public class InputUtils {

	private static Scanner scanner = new Scanner(System.in);

	public static Double inputMyNb(String msg, Object... args) {
		scanner.reset();
		System.out.print(String.format(msg, args));
		try {
			String input = scanner.nextLine();
			return inputMyNumber(input.trim());
		} catch (NumberFormatException e) {
			System.out.println(
					"Désolé mais je n'ai pas compris votre nombre entré ici avant, veuillez s'il vous plaît le corriger, merci !");
			return inputMyNb(msg, args);
		}
	}

	public static int inputQuit() {
		return InputUtils.inputMyQuery(false, "==> Voulez-vous continuer oui / non ? ", 1, "oui", "o", "yes", "Yeah",
				"ja", "j", "y", -1, "non", "n", "no", "nee", "neen");
	}

	private static int inputMyQuery(boolean test, String msg, Object... args) {
		String input = null;

		final Map<String, Integer> options = new HashMap<>();

		Integer option = -1;
		for (int i = 1; i < args.length; i++) {
			Object arg = args[i];
			if (arg instanceof Integer) {
				option = (Integer) arg;
			} else if (arg instanceof String) {
				options.put(((String) arg).toLowerCase(), option);
			}
		}

		String adaptedMsg = msg;
		System.out.print(String.format(adaptedMsg, args));
		if (test) {
			input = ((String) args[0]);
			System.out.print(input);
			if (input != null) {
				input = input.toLowerCase();
			}
		} else {
			scanner.reset();
			input = scanner.nextLine();
		}

		Integer response = options.get(input);
		if (response == null && input != null) {
			return inputMyQuery(test, adaptedMsg, args);
		}
		return response;
	}

	private static Double inputMyNumber(String str) {
		boolean alreadyHasComma = false;
		String result = "";
		int a = 0;
		while (str.length() > 0 && a++ < str.length()) {
			String charac = str.substring(a - 1, a);
			if (charac.matches("\\d")) {
				result += charac;
			} else if (!alreadyHasComma && (".".equals(charac) || ",".equals(charac))) {
				alreadyHasComma = true;
				result += ".";
			}
		}
		Double parsed = null;
		try {
			parsed = Double.parseDouble(result);
		} catch (Exception e) {
			System.out.println("Désolé, je n'ai pas compris votre nombre ! Veuillez réessayez svp ? Merci :)");
			return inputMyNumber(str);
		}
		return parsed;
	}

	public static Double testInputMyNb(String msg, Object... args) {
		return inputMyNumber(msg.trim());
	}

	public static int testInputMyQuery(String msg, Object... args) {
		return inputMyQuery(true, msg, args);
	}
}