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
				profil.setWeight(InputUtils.inputMyNb("==> Votre poids en Kg svp ? "));
				profil.setTargetWeight(InputUtils.inputMyNb("==> Poids visé (en Kg) svp ? "));
				profil.setHeight(
						InputUtils.inputMyNb("==> Votre taille en cm svp (pas en mètre) ? ") * CM_TO_METRE_FACTEUR);
				profil.calculateOwnImc();
				profil.calculateTargetImc();

				final String imcStr = String.format(
						"* Actuellement, vous avez un IMC de %.2f pour %.2f Kg et %.2f m => Vous êtes %s %s %s *",
						profil.getImc(), profil.getWeight(), profil.getHeight(),
						ImcRef.getPrepoDescription(profil.getImc()), ImcRef.getDescription(profil.getImc()),
						ImcRef.getImcDescriptionAsSmiley(profil.getImc()));
				String replaceAll = imcStr.replaceAll(".", "*");
				System.out.println(replaceAll);
				System.out.println();
				System.out.println(imcStr);
				System.out.println(replaceAll);
				Double idealWeightForTargetImc = -1.;
				{
					for (ImcRef targetImc : ImcRef.values()) {

						Double targetImcMin = targetImc.getMin();
						if (targetImcMin == 0) {
							targetImcMin = targetImc.getMax();
						}

						idealWeightForTargetImc = ImcOperatorUtils.idealWeightOperator.applyAsDouble(targetImcMin,
								profil.getHeight());
						
						Double overloadForTargetImc = ImcOperatorUtils.overloadFunction.apply(targetImcMin)
								.apply(profil.getWeight()).apply(profil.getHeight());

						if (profil.getImc() <= 0 || idealWeightForTargetImc <= 0) {
							throw new RuntimeException("Erreur avec le calcul de votre IMC !!!");
						} else {

							final String str1 = String.format("Pour um IMC %s il faudrait peser %.2f Kg (%s) ",
									targetImc, idealWeightForTargetImc,
									targetImc.getDescription());

							String str2 = null;

							if (ImcRef.isOverloadMax(overloadForTargetImc)) {
								str2 = String.format(" pas de différence ( votre cas : %s )",
										ImcRef.getImcDescriptionAsSmiley(profil.getImc()));
							} else if (ImcRef.getOverloadSign(overloadForTargetImc) > 0) {
								str2 = String.format("=> Vous avez %.2f en plus %s", 0.,
										ImcRef.getOverloadDescriptionAsSmiley(targetImcMin, overloadForTargetImc));
							} else if (ImcRef.getOverloadSign(overloadForTargetImc) < 0) {
								str2 = String.format("=> Vous avez %.2f en moins %s", 0.,
										ImcRef.getImcDescriptionAsSmiley(profil.getImc()));
							} else {
								throw new RuntimeException("unknown difference for this IMC ref !");
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

					Double poidsCalcul = targetImc * profil.getHeight() * profil.getHeight();

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
						for (ImcRef imcIdeal : new ImcRef[] { ImcRef.MAIGREUR_1, ImcRef.NORMAL_2, ImcRef.SURPOIDS_3 }) {
							System.out.println();
							System.out.println(String.format("Par rapport à un IMC de %.2f (%s) : ", imcIdeal.getMax(),
									imcIdeal.getDescription()));
							Double surcharge = poidsCalcul
									- (imcIdeal.getMin() * profil.getHeight() * profil.getHeight());
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
						System.out.println();
						System.out.println(String.format(
								"Votre IMC serait alors de %.2f (votre poids idéal (pour rappel) est toujours de %.2f Kg calcul sur la base d'un IMC idéal référent de %.1f)",
								profil.getImc(), idealWeightForTargetImc, ImcRef.SURPOIDS_3.getMax(),
								ImcRef.SURPOIDS_3.getDescription()));
						Double surcharge = profil.getTargetWeight() - ImcOperatorUtils.idealWeightOperator
								.applyAsDouble(ImcRef.SURPOIDS_3.getMax(), profil.getHeight());
						if (surcharge >= 1) {
							System.out.println(
									String.format("Vous seriez en SUR-charge pondérale de %.2f Kg :-( !", surcharge));
						} else if (-surcharge >= 1) {
							System.out.println(
									String.format("Vous seriez en SOUS-charge pondérale de %.2f Kg :-( !", -surcharge));
						} else {
							System.out.println(String.format("Vous seriez en forme ;-)", surcharge));
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