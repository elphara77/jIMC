package be.rl.j.imc.main;

import java.util.Arrays;

import be.rl.j.imc.utils.ImcOperatorUtils;
import be.rl.j.imc.utils.ImcRef;
import be.rl.j.imc.utils.InputUtils;

/**
 *
 * @since 11/03/2016
 *
 * @version beta je suis
 *
 * @author rl
 *
 * @see "Ex I 1)" <a href="https://goo.gl/AxYexx">MySelf</a>
 *
 * @category fun
 */
public final class JImc {

	private final static String TITRE = "= Calcul sur l'indice de masse corporelle (français) =";

	private final static byte SEPARATOR_CHAR = '=';
	private static StringBuilder SEPARATOR = null;

	private static final Double CM_TO_METRE_FACTEUR = .01;

	static {
		// for fun whenever possible :P
		byte[] bytes = new byte[180];
		Arrays.fill(bytes, 0, bytes.length, SEPARATOR_CHAR);
		SEPARATOR = new StringBuilder(new String(bytes));
	}

	private static final int[] quit = new int[] { 0 };

	private static Profil profil = null;

	public static void main(String[] args) {
		do {
			System.out.println(TITRE.replaceAll(".", "" + (char) SEPARATOR_CHAR));
			System.out.println(TITRE);
			System.out.println(TITRE.replaceAll(".", "" + (char) SEPARATOR_CHAR));

			try {
				System.out.println();
				System.out.println();

				profil = new Profil();

				profil.setWeight(InputUtils.inputMyNb("==> Votre poids en kg svp ? "));
				profil.setTargetWeight(InputUtils.inputMyNb("==> Poids visé (en kg) svp ? "));
				profil.setHeight(
						InputUtils.inputMyNb("==> Votre taille en cm svp (pas en mètre) ? ") * CM_TO_METRE_FACTEUR);

				profil.calculate();

				final String imcStr = String.format(
						"* Actuellement, vous avez un IMC de %.2f pour %.2f kg et %.2f m %s %s *", profil.getImc(),
						profil.getWeight(), profil.getHeight(),
						getOverloadDescription(profil.getOverload(),
								ImcRef.getImcRefs(profil.getImc()).getDescription(), true),
						ImcRef.getOverloadDescriptionAsSmiley(profil.getImc(), profil.getOverload()));

				System.out.println();

				String replaceAll = imcStr.replaceAll(".", "*");
				System.out.println(replaceAll);
				System.out.println(imcStr);
				System.out.println(replaceAll);

				Double targetWeight = profil.getTargetImc() * profil.getHeight() * profil.getHeight();

				final String str = String.format("* Pour votre poids visé de %.2f kg vous auriez un IMC de %.2f (%s) *",
						targetWeight, profil.getTargetImc(), ImcRef.getImcRefs(profil.getTargetImc()).getDescription());
				System.out.println();
				String replaceAll2 = str.replaceAll(".", "*");
				System.out.println(replaceAll2);
				System.out.println(str);
				System.out.println(replaceAll2);

				Double idealWeightForTargetImc = -1.;
				for (ImcRef targetImc : ImcRef.values()) {

					Double targetImcMin = targetImc.getMin();
					if (targetImcMin == 0) {
						targetImcMin = targetImc.getMax();
					}

					idealWeightForTargetImc = ImcOperatorUtils.idealWeightOperator.applyAsDouble(targetImcMin,
							profil.getHeight());

					Double overloadForTargetImc = ImcOperatorUtils.overloadFunction.apply(profil.getImc())
							.apply(idealWeightForTargetImc).apply(profil.getHeight());

					final String str1 = String.format("Pour un IMC %s il faudrait peser %.2f kg (%s) ",
							targetImc.toString(), idealWeightForTargetImc, targetImc.getDescription());

					String str2 = getOverloadDescription(overloadForTargetImc,
							ImcRef.getOverloadDescriptionAsSmiley(targetImcMin, overloadForTargetImc), false);

					final String allStr = String.format("%s%s", str1, str2);
					System.out.println(SEPARATOR);
					System.out.println(allStr);
				}

			} catch (Throwable t) {
				System.err.printf("Erreur : %s", t.getMessage());
				t.printStackTrace();
			}

			System.out.println(SEPARATOR);
			System.out.println();
			System.out.println(
					"S'il vous plaît, J'espère que vous n'allez pas trop vous prendre la tête pour quelques kg !");
			System.out.println();
			quit[0] = InputUtils.inputQuit();
			System.out.println();
			System.out.println();
		} while (quit[0] > 0);

		System.out.println();
		System.out.println("Fin du Calcul de l'IMC");
		System.out.println();
		System.out.println("A bientôt !");
		System.out.println();
		System.out.println("signé... Sorcier Elphara77 :D");
		System.out.println();
		System.out.println("Merci :-)");
	}

	private static String getOverloadDescription(Double overloadForTargetImc, String overDescr, boolean invert) {
		String ret = "";

		String adj = "plus ????";// TODO fix this by extra method ?!
		int adjSign = ImcRef.getOverloadSign(overloadForTargetImc);

		if (ImcRef.isOverloadMax(overloadForTargetImc)) {
			ret = String.format(" pas de différence ( votre cas : %s )", ImcRef.NORMAL_2.getDescription());
		} else {
			if (adjSign > 0 && !invert) {
				ret = String.format("=> Vous avez %.2f kg en %s (%s)", Math.abs(overloadForTargetImc), adj, overDescr);
			} else if (adjSign < 0 && !invert) {
				ret = String.format("=> Vous avez %.2f kg en %s (%s)", Math.abs(overloadForTargetImc), adj, overDescr);
			} else {
				ret = String.format("=> Vous avez %.2f kg en %s (%s)", Math.abs(overloadForTargetImc), adj, overDescr);
			}
		}

		return ret;
	}
}