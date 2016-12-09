package be.rl.j.imc.main;

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

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
public class JIMC {

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

	private static enum ImcIdeal {

		IMC_IDEAL_25(25., "normal limite surcharge"), //
		IMC_IDEAL_24(24., "normal haut"), //
		IMC_IDEAL_23(23., "normal moyen haut"), //
		IMC_IDEAL_22_5(22.5, "normal moyen"), //
		IMC_IDEAL_22(22., "normal moyen bas"), //
		IMC_IDEAL_21(21., "normal bas"), //
		IMC_IDEAL_20(20., "normal limite sous-charge");

		private Double imc = 0.;
		private String description = "à plat :P !";

		private ImcIdeal(Double imc, String description) {
			this.imc = imc;
			this.description = description;
		}
	}

	private static DoubleBinaryOperator imcOp = (poids, taille) -> poids / (taille * taille);
	private static DoubleBinaryOperator poidIdealOp = (imcIdeal, taille) -> imcIdeal * taille * taille;

	private static Function<Double, Function<Double, UnaryOperator<Double>>> surchargeFunction = imcIdeal -> poids -> taille -> poids
			- (imcIdeal * taille * taille);

	private static final String[] quit = new String[] { "non" };

	public static void main(String[] args) {
		do {
			System.out.println(TITRE.replaceAll(".", "" + (char) SEPARATOR_CHAR));
			System.out.println(TITRE);
			System.out.println(TITRE.replaceAll(".", "" + (char) SEPARATOR_CHAR));

			try {
				System.out.println();
				Double poids = InputUtils.inputMyNb("==> Votre poids en Kg svp !? ");
				Double taille = InputUtils.inputMyNb("==> Votre taille en cm svp ?") * CM_TO_METRE_FACTEUR;

				System.out.println();

				// Dédicace à mon ami Jamal Melhaoui (triple Champion
				// Boxe/Full-Contact/KickBoxing @ Marroco) 1997
				// paramètres du 3/11/2016 :

				Double imc = imcOp.applyAsDouble(poids, taille);

				final String imcStr = String.format("* Actuellement, Vous avez un IMC de %.2f pour %.2f Kg et %.2f m *",
						imc, poids, taille);
				String replaceAll = imcStr.replaceAll(".", "*");
				System.out.println(replaceAll);
				System.out.println(imcStr);
				System.out.println(replaceAll);
				Double poidsIdeal = -1.;
				{
					for (ImcIdeal ideal : ImcIdeal.values()) {
						Double imcIdeal = ideal.imc;
						poidsIdeal = poidIdealOp.applyAsDouble(imcIdeal, taille);
						if (imc <= 0 || poidsIdeal <= 0) {
							throw new RuntimeException("Erreur avec le calcul de votre IMC #1 !!!");
						} else {
							final String str1 = String.format(
									"Pour un IMC idéal référent de %.1f (%s), votre poids idéal est de %.2f Kg",
									imcIdeal, ideal.description, poidsIdeal);
							Double surcharge = surchargeFunction.apply(imcIdeal).apply(poids).apply(taille);
							String str2 = String.format(", Vous êtes en forme :-)", imcIdeal);
							if (surcharge >= 1) {
								str2 = String.format(" alors vous êtes en SUR-charge pondérale de %.2f Kg :-( !",
										surcharge, imcIdeal);
							} else if (-surcharge >= 1) {
								str2 = String.format(" alors vous êtes en SOUS-charge pondérale de %.2f Kg :-( !",
										-surcharge, imcIdeal);
							}
							final String str = String.format("%s%s", str1, str2);
							System.out.println(SEPARATOR.toString());
							System.out.println(str);
						}
					}
				}
				// Still bugs to be fixed after this line sure
				// before... pfff the
				// comment :D
				{
					System.out.println(SEPARATOR.toString());
					System.out.println();

					Double targetImc = InputUtils.inputMyNb("==> Votre IMC visé svp ? ");

					Double poidsCalcul = targetImc * taille * taille;

					final String str = String.format("Pour un IMC de %.1f vous devriez peser %.2f Kg !", targetImc,
							poidsCalcul);
					System.out.println();
					String replaceAll2 = str.replaceAll(".", "*");
					System.out.println(replaceAll2);
					System.out.println(str);
					System.out.println(replaceAll2);

					if (targetImc <= 0 || poidsCalcul <= 0) {
						throw new RuntimeException("Erreur avec le calcul de votre IMC #2 !!!");
					} else {
						for (ImcIdeal imcIdeal : new ImcIdeal[] { ImcIdeal.IMC_IDEAL_25, ImcIdeal.IMC_IDEAL_22_5,
								ImcIdeal.IMC_IDEAL_20 }) {
							System.out.println();
							System.out.println(String.format("Par rapport à un IMC de %.2f (%s) : ", imcIdeal.imc,
									imcIdeal.description));
							Double surcharge = poidsCalcul - (imcIdeal.imc * taille * taille);
							if (surcharge >= 1) {
								System.out
										.println(String.format("Vous êtes (%.2f) en SUR-charge pondérale de %.2f Kg !",
												surcharge, imcIdeal.imc, imcIdeal.description));
							} else if (-surcharge >= 1) {
								System.out
										.println(String.format("Vous êtes (%.2f) en SOUS-charge pondérale de %.2f Kg !",
												-surcharge, imcIdeal.imc, imcIdeal.description));
							} else {
								System.out.println(String.format("Vous seriez en forme ;-)", surcharge));
							}
						}
					}
					System.out.println();
					System.out.println();
					{
						Double targetPoids = InputUtils.inputMyNb("==> Poids visé (en Kg) svp ? ");
						imc = imcOp.applyAsDouble(targetPoids, taille);

						System.out.println();

						if (targetPoids <= 0) {
							throw new RuntimeException("Erreur avec le calcul de votre IMC #3 !!!");
						} else {
							System.out.println(String.format(
									"Votre IMC serait alors de %.2f (votre poids idéal (pour rappel) est toujours de %.2f Kg calcul sur la base d'un IMC idéal référent de %.1f)",
									imc, poidsIdeal, ImcIdeal.IMC_IDEAL_22_5.imc, ImcIdeal.IMC_IDEAL_22_5.description));
							Double surcharge = targetPoids
									- poidIdealOp.applyAsDouble(ImcIdeal.IMC_IDEAL_22_5.imc, taille);
							if (surcharge >= 1) {
								System.out.println(String.format("Vous seriez en SUR-charge pondérale de %.2f Kg :-( !",
										surcharge));
							} else if (-surcharge >= 1) {
								System.out.println(String
										.format("Vous seriez en SOUS-charge pondérale de %.2f Kg :-( !", -surcharge));
							} else {
								System.out.println(String.format("Vous seriez en forme ;-)", surcharge));
							}
						}
					}
					System.out.println();
					System.out.println(
							"S'il vous plaît, J'espère que vous n'allez pas trop vous prendre la tête pour quelques Kg !");
					System.out.println();
					quit[0] = InputUtils.inputMyQuery("==> Voulez-vous continuer oui / non ? ");
					System.out.println();
					System.out.println();
				}
			} catch (Throwable t) {
				System.err.printf("Erreur : %s", t.getMessage());
				t.printStackTrace();
			}
		} while ("".equals(quit[0].trim()) || "o".equalsIgnoreCase(quit[0].trim().substring(0, 1)));
		System.out.println();
		System.out.println("Fin du Calcul de l'IMC");
		System.out.println();
		System.out.println("A bientôt !");
		System.out.println();
		System.out.println("signé... Sorcier Elphara77 :D");
		System.out.println();
		System.out.println("Merci :-)");
	}
}