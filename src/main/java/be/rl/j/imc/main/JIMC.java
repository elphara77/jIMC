package be.rl.j.imc.main;

import java.util.Scanner;
import java.util.function.DoubleBinaryOperator;

public class JIMC {

    private final static double IMC_IDEAL_25 = 25.;
    private final static double IMC_IDEAL_24 = 24.;
    private final static double IMC_IDEAL_23 = 23.;
    private final static double IMC_IDEAL_225 = 22.5;
    private final static double IMC_IDEAL_22 = 22.;
    private final static double IMC_IDEAL_21 = 21.;
    private final static double IMC_IDEAL_20 = 20.;

    private final static double[] IMC_IDEALS = {IMC_IDEAL_25, IMC_IDEAL_24, IMC_IDEAL_23, IMC_IDEAL_22, IMC_IDEAL_21, IMC_IDEAL_20};

    private static DoubleBinaryOperator imcOp = (p, t) -> p / ((t / 100.) * (t / 100.));

    private final static String TITLE = "Calcul sur l'indice de masse corporelle (français)";

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        final String[] quit = new String[]{"o"};
        do {
            System.out.println(TITLE.replaceAll(".", "-"));
            System.out.println(TITLE);
            System.out.println(TITLE.replaceAll(".", "-"));

            try {
                System.out.print("==> Votre poids en Kg svp ? ");
                String poidsStr = scanner.next("\\d+\\.?\\,?\\d*");
                System.out.print("==> Votre taille en cm svp ? ");
                String tailleStr = scanner.next("\\d+\\.?\\,?\\d*");

                // Dédicace à mon ami Jamal Melhaoui (triple Champion
                // Boxe/Full-Contact/KickBoxing @ Marroco) 1997
                double poids = 54.;
                double taille = 1.65;

                poids = Double.parseDouble(poidsStr.replace(",", "."));
                taille = Double.parseDouble(tailleStr.replace(",", "."));

                double imc = imcOp.applyAsDouble(poids, taille);

                System.out.println();
                System.out.print(String.format("Actuellement, Vous pesez %.2f Kg pour %.2f m et donc vous avez un IMC de %.2f", poids, taille, imc));

                double poidsIdeal = -1;
                {
                    for (double idealImc : IMC_IDEALS) {

                        poidsIdeal = idealImc * taille * taille / 10000.;

                        System.out.println();

                        if (imc <= 0 || poidsIdeal <= 0) {
                            System.err.println("Erreur avec le calcul de votre IMC !!!");
                        } else {
                            System.out.println(String.format("Votre poids idéal est de %.2f Kg (calcul sur la base d'un IMC idéal référent de %.0f)", poidsIdeal, idealImc));
                            double surcharge = poids - (idealImc * taille * taille) / 10000.;
                            if (surcharge >= 1) {
                                System.out.println(String.format(
                                        "Donc, Vous êtes en SUR-charge pondérale de %.2f Kg :-( ! (tjs calculé sur la base d'un IMC idéal référent de %.0f)", surcharge, idealImc));
                            } else if (-surcharge >= 1) {
                                System.out.println(
                                        String.format("Donc, Vous êtes en SOUS-charge pondérale de %.2f Kg :-( ! (tjs calculé sur la base d'un IMC idéal référent de %.0f)",
                                                -surcharge, idealImc));
                            } else {
                                System.out.println(String.format("Donc, Vous êtes en forme :-) (tjs calculé sur la base d'un IMC idéal référent de %.0f)", idealImc));
                            }
                        }
                        System.out.println();
                    }
                }

                {
                    System.out.print("==> Votre IMC visé svp ? ");
                    String targetImcStr = scanner.next("\\d+");
                    double targetImc = Double.parseDouble(targetImcStr.replace(",", "."));
                    double poidsCalcul = targetImc * taille * taille / 10000.;

                    System.out.println();
                    System.out.println(String.format("Pour un IMC de %.0f vous peseriez %.2f Kg", targetImc, poidsCalcul));
                    if (targetImc <= 0 || poidsCalcul <= 0) {
                        System.err.println("Erreur avec le calcul de votre IMC !!!");
                    } else {
                        double ideal22 = IMC_IDEALS[3];
                        double surcharge = poidsCalcul - (ideal22 * taille * taille / 10000.);
                        if (surcharge >= 1) {
                            System.out.println(String.format("Vous seriez en SUR-charge pondérale de %1$.2f Kg :-( !", surcharge));
                        } else if (-surcharge >= 1) {
                            System.out.println(String.format("Vous seriez en SOUS-charge pondérale de %1$.2f Kg :-( !", -surcharge));
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
                    double targetPoids = Double.parseDouble(targetPoidsStr.replace(",", "."));
                    imc = imcOp.applyAsDouble(targetPoids, taille);

                    System.out.println();

                    if (targetPoids <= 0) {
                        System.err.println("Erreur avec le calcul de votre IMC !!!");
                    } else {

                        System.out.println(String.format(
                                "Votre IMC serait alors de %1$.2f et votre poids idéal (pour rappel) est toujours de %2$.2f Kg (calcul sur la base d'un IMC idéal référent de %3$.0f)",
                                imc, poidsIdeal, IMC_IDEAL_225));
                        double surcharge = targetPoids - (IMC_IDEAL_225 * taille * taille / 10000.);
                        if (surcharge >= 1) {
                            System.out.println(String.format("Vous seriez en SUR-charge pondérale de %1$.2f Kg :-( !", surcharge));
                        } else if (-surcharge >= 1) {
                            System.out.println(String.format("Vous seriez en SOUS-charge pondérale de %1$.2f Kg :-( !", -surcharge));
                        } else {
                            System.out.println(String.format("Vous seriez en forme ;-)", surcharge));
                        }
                    }
                }
                System.out.println();
                System.out.println();
                System.out.print("==> Voulez-vous continuer o / n ? ");
                scanner.reset();
                quit[0] = scanner.next("\\w");
                System.out.println();
                System.out.println();
            } catch (Throwable t) {
                System.err.println("Erreur avec le calcul de votre IMC !!!");
                System.err.printf("Erreur : %s", t.getMessage());
            } finally {
                scanner.reset();
            }
        } while ("o".equalsIgnoreCase(quit[0]) || "".equals(quit[0]));
        System.out.println();
        System.out.println("Fin du Calcul de l'IMC");
        System.out.println();
        System.out.println("S'il vous plaît, J'espère que vous n'allez pas trop vous prendre la tête pour quelques Kg !");
        System.out.println();
        System.out.println("A bientôt !");
        System.out.println();
        System.out.println("Doc Raph. L. :D");
    }
}