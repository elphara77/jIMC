package be.rl.j.imc.main;

import java.util.Scanner;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class JIMC {

    private final static String TITRE = "Calcul sur l'indice de masse corporelle (français)";

    private static final Double FACTEUR_CALCUL_POIDS_IMC22 = 10_000.;

    private final static Double IMC_IDEAL_25 = 25.;
    private final static Double IMC_IDEAL_24 = 24.;
    private final static Double IMC_IDEAL_23 = 23.;
    private final static Double IMC_IDEAL_225 = 22.5;
    private final static Double IMC_IDEAL_22 = 22.;
    private final static Double IMC_IDEAL_21 = 21.;
    private final static Double IMC_IDEAL_20 = 20.;

    private final static Double[] IMC_IDEALS = {IMC_IDEAL_25, IMC_IDEAL_24, IMC_IDEAL_23, IMC_IDEAL_22, IMC_IDEAL_225, IMC_IDEAL_21, IMC_IDEAL_20};

    private static DoubleBinaryOperator imcOp = (poids, taille) -> poids
            / ((taille / FACTEUR_CALCUL_POIDS_IMC22 / FACTEUR_CALCUL_POIDS_IMC22) * (taille / FACTEUR_CALCUL_POIDS_IMC2 / FACTEUR_CALCUL_POIDS_IMC22));
    private static DoubleBinaryOperator poidIdealOp = (imcIdeal, taille) -> imcIdeal * taille * taille / FACTEUR_CALCUL_POIDS_IMC2 / FACTEUR_CALCUL_POIDS_IMC22;
    private static Function<Double, Function<Double, UnaryOperator<Double>>> surchargeFunction = imcIdeal -> poids -> taille -> poids
            - (imcIdeal * taille * taille) / FACTEUR_CALCUL_POIDS_IMC22 / FACTEUR_CALCUL_POIDS_IMC22;

    private static Scanner scanner = new Scanner(System.in);

    private static final String[] quit = new String[]{"o"};

    public static void main(String[] args) {
        do {
            System.out.println(TITRE.replaceAll(".", "-"));
            System.out.println(TITRE);
            System.out.println(TITRE.replaceAll(".", "-"));

            try {
                System.out.print("==> Votre poids en Kg svp ? ");
                String poidsStr = scanner.next("\\d+\\.?\\,?\\d*.*").replaceAll("^[\\w\\.\\,]", "").replace(",", ".").trim();
                System.out.print("==> Votre taille en cm svp ? ");
                String tailleStr = scanner.next("\\d+\\.?\\,?\\d*.*").replaceAll("^[\\w\\.\\,]", "").replace(",", ".").trim();

                // Dédicace à mon ami Jamal Melhaoui (triple Champion
                // Boxe/Full-Contact/KickBoxing @ Marroco) 1997
                // paramètres du 3/11/2016 :
                Double poids = 54.;
                Double taille = 1.65;

                poids = Double.parseDouble(poidsStr);
                taille = Double.parseDouble(tailleStr);

                Double imc = imcOp.applyAsDouble(poids, taille);

                System.out.println();
                System.out.print(String.format("Actuellement, Vous pesez %.2f Kg pour %.2f m et donc vous avez un IMC de %.2f", poids, taille, imc));

                Double poidsIdeal = -1.;
                {
                    for (Double imcIdeal : IMC_IDEALS) {
                        poidsIdeal = poidIdealOp.applyAsDouble(imcIdeal, taille);

                        System.out.println();

                        if (imc <= 0 || poidsIdeal <= 0) {
                            throw new RuntimeException("Erreur avec le calcul de votre IMC #1 !!!");
                        } else {
                            System.out.println(String.format("Votre poids idéal est de %.2f Kg (calcul sur la base d'un IMC idéal référent de %.0f)", poidsIdeal, imcIdeal));
                            Double surcharge = surchargeFunction.apply(imcIdeal).apply(poids).apply(taille);
                            if (surcharge >= 1) {
                                System.out.println(String.format(
                                        "Donc, Vous êtes en SUR-charge pondérale de %.2f Kg :-( ! (tjs calculé sur la base d'un IMC idéal référent de %.0f)", surcharge, imcIdeal));
                            } else if (-surcharge >= 1) {
                                System.out.println(
                                        String.format("Donc, Vous êtes en SOUS-charge pondérale de %.2f Kg :-( ! (tjs calculé sur la base d'un IMC idéal référent de %.0f)",
                                                -surcharge, imcIdeal));
                            } else {
                                System.out.println(String.format("Donc, Vous êtes en forme :-) (tjs calculé sur la base d'un IMC idéal référent de %.0f)", imcIdeal));
                            }
                        }
                        System.out.println();
                    }
                }
                {
                    System.out.print("==> Votre IMC visé svp ? ");
                    String targetImcStr = scanner.next("\\d+");
                    Double targetImc = Double.parseDouble(targetImcStr.replace(",", "."));
                    Double poidsCalcul = targetImc * taille * taille / 10000.;

                    System.out.println();
                    System.out.println(String.format("Pour un IMC de %.0f vous peseriez %.2f Kg", targetImc, poidsCalcul));
                    if (targetImc <= 0 || poidsCalcul <= 0) {
                        throw new RuntimeException("Erreur avec le calcul de votre IMC #2 !!!");
                    } else {
                        Double ideal225 = IMC_IDEAL_225;
                        Double surcharge = poidsCalcul - (ideal225 * taille * taille / 10000.);
                        if (surcharge >= 1) {
                            System.out.println(String.format("Vous seriez en SUR-charge pondérale de %.2f Kg :-( (par rapport à un IMC moyen normale de 22,5) !", surcharge));
                        } else if (-surcharge >= 1) {
                            System.out.println(String.format("Vous seriez en SOUS-charge pondérale de %.2f Kg :-( (par rapport à un IMC moyen normale de 22,5) !", -surcharge));
                        } else {
                            System.out.println(String.format("Vous seriez en forme ;-)", surcharge));
                        }
                    }
                }
                System.out.println();
                System.out.println();
                {
                    System.out.print("==> Poids visé (en Kg) svp ? ");
                    String targetPoidsStr = scanner.next("\\d+");
                    Double targetPoids = Double.parseDouble(targetPoidsStr.replace(",", "."));
                    imc = imcOp.applyAsDouble(targetPoids, taille);

                    System.out.println();

                    if (targetPoids <= 0) {
                        throw new RuntimeException("Erreur avec le calcul de votre IMC #3 !!!");
                    } else {
                        System.out.println(String.format(
                                "Votre IMC serait alors de %.2f et votre poids idéal (pour rappel) est toujours de %.2f Kg (calcul sur la base d'un IMC idéal référent de %.0f)",
                                imc, poidsIdeal, IMC_IDEAL_225));
                        Double surcharge = targetPoids - (IMC_IDEAL_225 * taille * taille / 10000.);
                        if (surcharge >= 1) {
                            System.out.println(String.format("Vous seriez en SUR-charge pondérale de %.2f Kg :-( !", surcharge));
                        } else if (-surcharge >= 1) {
                            System.out.println(String.format("Vous seriez en SOUS-charge pondérale de %.2f Kg :-( !", -surcharge));
                        } else {
                            System.out.println(String.format("Vous seriez en forme ;-)", surcharge));
                        }
                    }
                }
                System.out.println();
                System.out.println("S'il vous plaît, J'espère que vous n'allez pas trop vous prendre la tête pour quelques Kg !");
                System.out.println();
                System.out.print("==> Voulez-vous continuer oui / non ? ");
                scanner.reset();
                quit[0] = scanner.next("\\w+");
                System.out.println();
                System.out.println();
            } catch (Throwable t) {
                System.err.printf("Erreur : %s", t.getMessage());
            } finally {
                scanner.reset();
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