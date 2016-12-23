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
public final class JIMC {

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

		IMC_0_DENUTRITION(0., 16.5, "dénutrition"), //
		IMC_1_MAIGREUR(16.5, 18.5, "maigreur"), //
		IMC_2_NORMAL(18.5, 25., "corpulence normale"), //
		IMC_3_SURPOIDS(25., 30., "surpoids"), //
		IMC_4_MODEREE(30., 35., "obésité modérée"), //
		IMC_5_SEVERE(35., 40., "obésité sévère"), //
		IMC_6_MASS(40., Double.MAX_VALUE, "obésité massive");

		private Double max = 0.;
		private Double min = 0.;
		private String description = "à plat :P !";

		private ImcIdeal(Double imc, String description) {
			this.max = imc;
			this.description = description;
		}

		private ImcIdeal(Double imcMin, Double imcMax, String description) {
			this.min = imcMin;
			this.max = imcMax;
			this.description = description;
		}

		private static Double getSurcharge(Double imc) {
			return imc - IMC_2_NORMAL.min;
		}

		private static String getDescription(Double imc) {
			for (ImcIdeal imcRef : ImcIdeal.values()) {
				if (imc >= imcRef.min && imc < imcRef.max) {
					return imcRef.description;
				}
			}
			throw new RuntimeException("IMC incalculable !");
		}

		private static String getDescriptionAsValues(Double imc) {
			for (ImcIdeal imcRef : ImcIdeal.values()) {
				if (imc >= imcRef.min && imc < imcRef.max) {
					return String.format("entre %.2f et %.2f", imcRef.min, imcRef.max);
				}
			}
			//TODO with mixed switch
			throw new RuntimeException("description des valeurs IMC introuvable!");
		}

		private static String getDescriptionAsSmiley(Double imc) {
			for (ImcIdeal imcRef : ImcIdeal.values()) {
				if (imc >= imcRef.min && imc < imcRef.max) {
					switch (imcRef) {
					case IMC_0_DENUTRITION:
						return ":-S CRITIQUE !";
					case IMC_1_MAIGREUR:
						return ":-(";
					case IMC_2_NORMAL:
						return "Vous êtes en forme :-)";
					case IMC_3_SURPOIDS:
						return ":-(";
					case IMC_4_MODEREE:
						return ":-S ATTENTION !";
					case IMC_5_SEVERE:
						return ":-S SEVERE !!!";
					case IMC_6_MASS:
						return ":-( CRITIQUE !!!";
					default:
						throw new RuntimeException("IMC incalculable !");
					}
				}
			}
			throw new RuntimeException("IMC incalculable !");

		}
	}

	private static DoubleBinaryOperator imcOp = (poids, taille) -> poids / (taille * taille);
	private static DoubleBinaryOperator poidIdealOp = (imcIdeal, taille) -> imcIdeal * taille * taille;

	private static Function<Double, Function<Double, UnaryOperator<Double>>> surchargeFunction = imcIdeal -> poids -> taille -> poids
			- (imcIdeal * taille * taille);

	private static final int[] quit = new int[] { 0 };

	public static void main(String[] args) {
		do {
			System.out.println(TITRE.replaceAll(".", "" + (char) SEPARATOR_CHAR));
			System.out.println(TITRE);
			System.out.println(TITRE.replaceAll(".", "" + (char) SEPARATOR_CHAR));

			try {
				System.out.println();
				Double poids = InputUtils.inputMyNb("==> Votre poids en Kg svp ? ");
				Double taille = InputUtils.inputMyNb("==> Votre taille en cm svp ? ") * CM_TO_METRE_FACTEUR;

				System.out.println();

				// Dédicace à mon ami Jamal Melhaoui (triple Champion
				// Boxe/Full-Contact/KickBoxing @ Marroco) 1997
				// paramètres du 3/11/2016 :

				Double imc = imcOp.applyAsDouble(poids, taille);

				final String imcStr = String.format(
						"* Actuellement, Vous avez un IMC de %.2f pour %.2f Kg et %.2f m => %s %s *", imc, poids,
						taille, ImcIdeal.getDescription(imc), ImcIdeal.getDescriptionAsSmiley(imc));
				String replaceAll = imcStr.replaceAll(".", "*");
				System.out.println(replaceAll);
				System.out.println(imcStr);
				System.out.println(replaceAll);
				Double poidsIdeal = -1.;
				{
					for (ImcIdeal ideal : ImcIdeal.values()) {
						Double imcIdeal = ideal.min;
						if (imcIdeal == 0) {
							imcIdeal = ideal.max;
						}
						poidsIdeal = poidIdealOp.applyAsDouble(imcIdeal, taille);
						if (imc <= 0 || poidsIdeal <= 0) {
							throw new RuntimeException("Erreur avec le calcul de votre IMC #1 !!!");
						} else {
							final String str1 = String.format("Pour um IMC %s il faudrait peser %.2f Kg (%s) ",
									ImcIdeal.getDescriptionAsValues(imcIdeal), poidsIdeal, ideal.description);
							Double surcharge = surchargeFunction.apply(imcIdeal).apply(poids).apply(taille);
							String str2 = String.format(", ", ImcIdeal.getDescriptionAsSmiley(imc));
							if (surcharge >= 0.1 || -surcharge >= 0.1) {
								str2 = String.format(" pas de différence ( votre cas : %s )",
										ImcIdeal.getDescriptionAsSmiley(imc));
							} else if (surcharge >= 1) {
								str2 = String.format(" une différence de %s de %.2f Kg en plus %s",
										ImcIdeal.getSurcharge(imc), imcIdeal, ImcIdeal.getDescriptionAsSmiley(imc));
							} else if (-surcharge >= 1) {
								str2 = String.format(" différence %s de %.2f Kg en moins %s",
										ImcIdeal.getSurcharge(imc), imcIdeal, ImcIdeal.getDescriptionAsSmiley(imc));
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
						for (ImcIdeal imcIdeal : new ImcIdeal[] { ImcIdeal.IMC_1_MAIGREUR, ImcIdeal.IMC_2_NORMAL,
								ImcIdeal.IMC_3_SURPOIDS }) {
							System.out.println();
							System.out.println(String.format("Par rapport à un IMC de %.2f (%s) : ", imcIdeal.max,
									imcIdeal.description));
							Double surcharge = poidsCalcul - (imcIdeal.min * taille * taille);
							if (surcharge >= 1) {
								System.out
										.println(String.format("Vous êtes (%.2f) en SUR-charge pondérale de %.2f Kg !",
												surcharge, imcIdeal.max, imcIdeal.description));
							} else if (-surcharge >= 1) {
								System.out
										.println(String.format("Vous êtes (%.2f) en SOUS-charge pondérale de %.2f Kg !",
												-surcharge, imcIdeal.max, imcIdeal.description));
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
									imc, poidsIdeal, ImcIdeal.IMC_3_SURPOIDS.max, ImcIdeal.IMC_3_SURPOIDS.description));
							Double surcharge = targetPoids
									- poidIdealOp.applyAsDouble(ImcIdeal.IMC_3_SURPOIDS.max, taille);
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
					quit[0] = InputUtils.inputQuit();
					System.out.println();
					System.out.println();
				}
			} catch (Throwable t) {
				System.err.printf("Erreur : %s", t.getMessage());
				t.printStackTrace();
			}
		} while (quit[0] > -1);
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