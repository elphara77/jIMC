package be.rl.j.imc.utils;

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
			return scanMyNumber(input.trim());
		} catch (NumberFormatException e) {
			System.out.println(
					"Désolé mais je n'ai pas compris votre nombre entré ici avant, veuillez s'il vous plaît le corriger, merci !");
			return inputMyNb(msg, args);
		}
	}

	public static Double testInputMyNb(String msg, Object... args) {
		scanner.reset();
		try {
			return scanMyNumber(msg.trim());
		} catch (NumberFormatException e) {
			System.out.println(
					"Désolé mais je n'ai pas compris votre nombre entré ici avant, veuillez le corriger svp, merci !");
			return inputMyNb(msg, args);
		}
	}

	public static String inputMyQuery(String msg, Object... args) {
		scanner.reset();
		System.out.print(String.format(msg, args));
		return scanner.nextLine();
	}

	private static Double scanMyNumber(String str) {
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
		return Double.parseDouble(result);
	}
}