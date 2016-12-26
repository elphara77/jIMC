package be.rl.j.imc.main;

import java.util.Arrays;

import be.rl.j.imc.utils.ImcRefs;
import be.rl.j.imc.utils.InputUtils;
import be.rl.j.imc.utils.OperatorUtils;

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
				ownImc = OperatorUtils.imcOperator.applyAsDouble(ownWeight, ownHeight);

				final String imcStr = String.format(
						"* Actuellement, Vous avez un IMC de %.2f pour %.2f Kg et %.2f m => Vous êtes %s %s %s *",
						ownImc, ownWeight, ownHeight, ImcRefs.getPrepoDescription(ownImc),
						ImcRefs.getDescription(ownImc), ImcRefs.getDescriptionAsSmiley(ownImc));
				String replaceAll = imcStr.replaceAll(".", "*");
				System.out.println(replaceAll);
				System.out.println(imcStr);
				System.out.println(replaceAll);
				Double poidsIdeal = -1.;
				{
					for (ImcRefs imc : ImcRefs.values()) {
						Double imcMin = imc.getMin();
						if (imcMin == 0) {
							imcMin = imc.getMax();
						}
						poidsIdeal = OperatorUtils.idealWeightOperator.applyAsDouble(imcMin, ownHeight);
						if (ownImc <= 0 || poidsIdeal <= 0) {
							throw new RuntimeException("Erreur avec le calcul de votre IMC #1 !!!");
						} else {
							final String str1 = String.format("Pour um IMC %s il faudrait peser %.2f Kg (%s) ",
									ImcRefs.getDescriptionAsValues(imc), poidsIdeal, imc.getDescription());
							Double surcharge = OperatorUtils.overloadFunction.apply(imcMin).apply(ownWeight)
									.apply(ownHeight);
							String str2 = String.format(", ", ImcRefs.getDescriptionAsSmiley(ownImc));
							if (Math.abs(surcharge) <= 0.1) {
								str2 = String.format(" pas de différence ( votre cas : %s )",
										ImcRefs.getDescriptionAsSmiley(ownImc));
							} else if (surcharge >= 1) {
								str2 = String.format("=> Vous avez %.2f en plus %s",
										ImcRefs.getOverload(surcharge, imc),
										ImcRefs.getDescriptionSurchargeAsSmiley(imcMin, surcharge));
							} else if (-surcharge >= 1) {
								str2 = String.format("=> Vous avez %.2f en moins %s",
										ImcRefs.getOverload(surcharge, imc), ImcRefs.getDescriptionAsSmiley(ownImc));
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
						for (ImcRefs imcIdeal : new ImcRefs[] { ImcRefs.MAIGREUR_1, ImcRefs.NORMAL_2,
								ImcRefs.SURPOIDS_3 }) {
							System.out.println();
							System.out.println(String.format("Par rapport à un IMC de %.2f (%s) : ", imcIdeal.getMax(),
									imcIdeal.getDescription()));
							Double surcharge = poidsCalcul - (imcIdeal.getMin() * ownHeight * ownHeight);
							if (surcharge >= 1) {
								System.out
										.println(String.format("Vous êtes (%.2f) en SUR-charge pondérale de %.2f Kg !",
												surcharge, imcIdeal.getMax(), imcIdeal.getDescription()));
							} else if (-surcharge >= 1) {
								System.out
										.println(String.format("Vous êtes (%.2f) en SOUS-charge pondérale de %.2f Kg !",
												-surcharge, imcIdeal.getMax(), imcIdeal.getDescription()));
							} else {
								System.out.println(String.format("Vous seriez en forme ;-)", surcharge));
							}
						}
					}
					System.out.println();
					System.out.println();
					{
						Double targetPoids = InputUtils.inputMyNb("==> Poids visé (en Kg) svp ? ");
						ownImc = OperatorUtils.imcOperator.applyAsDouble(targetPoids, ownHeight);

						System.out.println();

						if (targetPoids <= 0) {
							throw new RuntimeException("Erreur avec le calcul de votre IMC #3 !!!");
						} else {
							System.out.println(String.format(
									"Votre IMC serait alors de %.2f (votre poids idéal (pour rappel) est toujours de %.2f Kg calcul sur la base d'un IMC idéal référent de %.1f)",
									ownImc, poidsIdeal, ImcRefs.SURPOIDS_3.getMax(),
									ImcRefs.SURPOIDS_3.getDescription()));
							Double surcharge = targetPoids - OperatorUtils.idealWeightOperator
									.applyAsDouble(ImcRefs.SURPOIDS_3.getMax(), ownHeight);
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