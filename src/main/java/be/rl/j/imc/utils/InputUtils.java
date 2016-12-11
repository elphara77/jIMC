package be.rl.j.imc.utils;

import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.text.StyledEditorKit.ForegroundAction;

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
					"Désolé mais je n'ai pas compris votre nombre entré ici avant, veuillez s'il vous plaît le corriger, merci !");
			return inputMyNb(msg, args);
		}
	}

	public static String inputMyQuery(String msg, Object... args) {
		scanner.reset();
		System.out.print(String.format(msg, args));
		return scanner.nextLine();
	}

	private static Double scanMyNumber(String str) {
		return simpleParse(replaceAllBadChars(keepOnlyFirstDot(replaceAllCommas(str))));
	}

	private static Double simpleParse(String str) {
		return Double.parseDouble(str);
	}

	private static String replaceAllCommas(String str) {
		return str.replaceAll(",", ".");
	}

	private static String replaceAllBadChars(String str) {
		String result = "";
		int a = 0;
		while (str.length() > 0 && a++ < str.length()) {
			String charac = str.substring(a - 1, a);
			if (charac.matches("\\d")) {
				result += charac;
			} else if (".".equals(charac)) {
				result += ".";
			}
		}
		return result;
	}

	public static void main(String[] args) {
		String[] strs = { "111", "111.3", "111,3", "&&ss 2,5, ,,,sss" };
		for (String str : strs) {
			System.out.println("\"" + str + "\"" + " --> " + "\"" + replaceAllBadChars(str) + "\"");
			System.out.println(replaceAllCommas(str));
			System.out.println(keepOnlyFirstDot(replaceAllCommas(str)));
			System.out.println(replaceAllBadChars(keepOnlyFirstDot(replaceAllCommas(str))));
			simpleParse(replaceAllBadChars(keepOnlyFirstDot(replaceAllCommas(str))));
		}
	}

	private static String keepOnlyFirstDot(String str) {
		if (str.length() > 1) {
			int first = str.indexOf(".");
			if (first > -1) {
				return str.substring(0, first) + "." + str.substring(first + 1, str.length()).replace(".", "");
			}
		}
		return str;
	}
}