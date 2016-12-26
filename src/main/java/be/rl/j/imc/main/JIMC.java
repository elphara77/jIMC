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

		IMC_0_DENUTRITION(0., 16.5, "dénutrition", "en"), //
		IMC_1_MAIGREUR(16.5, 18.5, "maigreur", "en"), //
		IMC_2_NORMAL(18.5, 25., "corpulence normale", "de"), //
		IMC_3_SURPOIDS(25., 30., "surpoids", "en"), //
		IMC_4_MODEREE(30., 35., "obésité modérée", "en"), //
		IMC_5_SEVERE(35., 40., "obésité sévère", "en"), //
		IMC_6_MASS(40., Double.MAX_VALUE, "obésité massive", "en");

		private Double max = 0.;
		private Double min = 0.;
		private String description = "à plat :P !";
		private String prepoDescription = "en";

		private ImcIdeal(Double imcMin, Double imcMax, String description, String prepo) {
			this.min = imcMin;
			this.max = imcMax;
			this.description = description;
			this.prepoDescription = prepo;
		}

		private static Double getSurcharge(Double surcharge, ImcIdeal ideal) {
//			for (ImcIdeal imcRef : ImcIdeal.values()) {
//				if (imc >= imcRef.min && imc < imcRef.max) {
//					switch (imcRef) {
//					case IMC_0_DENUTRITION:
//						return ":-S CRITIQUE !";
//					case IMC_1_MAIGREUR:
//						return ":-(";
//					case IMC_2_NORMAL:
//						return "Vous êtes en forme :-)";
//					case IMC_3_SURPOIDS:
//						return ":-(";
//					case IMC_4_MODEREE:
//						return ":-S ATTENTION !";
//					case IMC_5_SEVERE:
//						return ":-S SEVERE !!!";
//					case IMC_6_MASS:
//						return ":-( CRITIQUE !!!";
//					default:
//						throw new RuntimeException("IMC incalculable !");
//					}
//				}
//			}
//			throw new RuntimeException("IMC incalculable !");
			return 0.;
		}

		private static String getDescription(Double imc) {
			for (ImcIdeal imcRef : ImcIdeal.values()) {
				if (imc >= imcRef.min && imc < imcRef.max) {
					return imcRef.description;
				}
			}
			throw new RuntimeException("IMC incalculable !");
		}

		private static String getPrepoDescription(Double imc) {
			for (ImcIdeal imcRef : ImcIdeal.values()) {
				if (imc >= imcRef.min && imc < imcRef.max) {
					return imcRef.prepoDescription;
				}
			}
			throw new RuntimeException("IMC incalculable !");
		}

		private static String getDescriptionAsValues(ImcIdeal ideal) {
			if (ImcIdeal.IMC_6_MASS.equals(ideal)) {
				return String.format("au-dessus de %.2f", ideal.min);
			} else {
				return String.format("entre %.2f et %.2f", ideal.min, ideal.max);
			}
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

		private static String getDescriptionSurchargeAsSmiley(Double imc, Double surcharge) {
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
	private static DoubleBinaryOperator idealWeightOp = (imcIdeal, taille) -> imcIdeal * taille * taille;

	private static Function<Double, Function<Double, UnaryOperator<Double>>> surchargeFunction = imcIdeal -> poids -> taille -> poids
			- (imcIdeal * taille * taille);

	private static final int[] quit = new int[] { 0 };

	private static Double ownWeight = 0.;
	private static Double ownHeight = 0.;
	private static Double ownImc;

	public static void main(String[] args) {
		do {
			System.out.println(TITRE.replaceAll(".", "" + (char) SEPARATOR_CHAR));
			System.out.println(TITRE);
			System.out.println(TITRE.replaceAll(".", "" + (char) SEPARATOR_CHAR));

			try {
				System.out.println();
				System.out.println();

				// Dédicace à mon ami Jamal Melhaoui (triple Champion
				// Boxe/Full-Contact/KickBoxing @ Marroco) 1997
				ownWeight = InputUtils.inputMyNb("==> Votre poids en Kg svp ? ");
				ownHeight = InputUtils.inputMyNb("==> Votre taille en cm svp ? ") * CM_TO_METRE_FACTEUR;
				ownImc = imcOp.applyAsDouble(ownWeight, ownHeight);

				final String imcStr = String.format(
						"* Actuellement, Vous avez un IMC de %.2f pour %.2f Kg et %.2f m => Vous êtes %s %s %s *",
						ownImc, ownWeight, ownHeight, ImcIdeal.getPrepoDescription(ownImc),
						ImcIdeal.getDescription(ownImc), ImcIdeal.getDescriptionAsSmiley(ownImc));
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
						poidsIdeal = idealWeightOp.applyAsDouble(imcIdeal, ownHeight);
						if (ownImc <= 0 || poidsIdeal <= 0) {
							throw new RuntimeException("Erreur avec le calcul de votre IMC #1 !!!");
						} else {
							final String str1 = String.format("Pour um IMC %s il faudrait peser %.2f Kg (%s) ",
									ImcIdeal.getDescriptionAsValues(ideal), poidsIdeal, ideal.description);
							Double surcharge = surchargeFunction.apply(imcIdeal).apply(ownWeight).apply(ownHeight);
							String str2 = String.format(", ", ImcIdeal.getDescriptionAsSmiley(ownImc));
							if (Math.abs(surcharge) <= 0.1) {
								str2 = String.format(" pas de différence ( votre cas : %s )",
										ImcIdeal.getDescriptionAsSmiley(ownImc));
							} else if (surcharge >= 1) {
								str2 = String.format("=> Vous avez %.2f en plus %s",
										ImcIdeal.getSurcharge(surcharge, ideal),
										ImcIdeal.getDescriptionSurchargeAsSmiley(imcIdeal, surcharge));
							} else if (-surcharge >= 1) {
								str2 = String.format("=> Vous avez %.2f en moins %s",
										ImcIdeal.getSurcharge(surcharge, ideal),
										ImcIdeal.getDescriptionAsSmiley(ownImc));
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

					Double poidsCalcul = targetImc * ownHeight * ownHeight;

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
							Double surcharge = poidsCalcul - (imcIdeal.min * ownHeight * ownHeight);
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
						ownImc = imcOp.applyAsDouble(targetPoids, ownHeight);

						System.out.println();

						if (targetPoids <= 0) {
							throw new RuntimeException("Erreur avec le calcul de votre IMC #3 !!!");
						} else {
							System.out.println(String.format(
									"Votre IMC serait alors de %.2f (votre poids idéal (pour rappel) est toujours de %.2f Kg calcul sur la base d'un IMC idéal référent de %.1f)",
									ownImc, poidsIdeal, ImcIdeal.IMC_3_SURPOIDS.max,
									ImcIdeal.IMC_3_SURPOIDS.description));
							Double surcharge = targetPoids
									- idealWeightOp.applyAsDouble(ImcIdeal.IMC_3_SURPOIDS.max, ownHeight);
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