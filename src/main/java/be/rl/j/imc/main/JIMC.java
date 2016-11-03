package be.rl.j.imc.main;

import java.io.IOException;
import java.util.Scanner;

public class JIMC {

    private final static double IDEAL = 25.;
    private final static String TITLE = "Calcul sur l'indice de masse corporelle (français)";

    public static void main(String[] args) {
        do {
            System.out.println(TITLE.replaceAll(".", "-"));
            System.out.println(TITLE);
            System.out.println(TITLE.replaceAll(".", "-"));

            double imc = -1.;
            double tailleIdeal = -1;

            // Coucou Jamal Melhaoui Champion Boxe & Cie Marroco 1997
            double poids = 54.;
            double taille = 1.65;

            Scanner scanner = null;
            try {
                scanner = new Scanner(System.in);
                System.out.print("Voutre poids en Kg ? ");
                String poidsStr = scanner.next("\\d+\\.?\\,?\\d*");
                System.out.print("Voutre taille en cm ? ");
                String tailleStr = scanner.next("\\d+\\.?\\,?\\d*");

                poids = Double.parseDouble(poidsStr.replace(",", "."));
                taille = Double.parseDouble(tailleStr.replace(",", ".")) / 100.;

                System.out.println(String.format("Vous pesez %1$.2f Kg pour %2$.2f m", poids, taille));

                imc = poids / (taille * taille);
                tailleIdeal = IDEAL * taille * taille;

                if (imc < 0 || tailleIdeal < 0) {
                    System.err.println("Erreur avec le calcul de votre IMC !!!");
                } else {
                    System.out.println(String.format("Votre IMC est de %1$.2f et votre poids idéal est de %2$.2f Kg (calcul sur l'IMC idéal de %3$.2f)", imc, tailleIdeal, IDEAL));
                    double surcharge = poids - (IDEAL * taille * taille);
                    if (Math.round(surcharge) > 0) {
                        System.out.println(String.format("Vous êtes en SUR-charge pondérale de %1$.2f Kg :-( !", surcharge));
                    } else if (Math.round(surcharge) < 0) {
                        System.out.println(String.format("Vous êtes en SOUS-charge pondérale de %1$.2f Kg :-( !", -surcharge));
                    } else {
                        System.out.println(String.format("Vous êtes en forme :-)", surcharge));
                    }
                    System.out.println();
                    System.out.println();
                }
            } catch (Throwable t) {
                System.err.println("Erreur avec le calcul de votre IMC !!!");
                System.err.printf("Erreur : %s", t.getMessage());
            } finally {
                if (scanner != null) {
                    scanner.reset();
                }
            }
        } while (true);
    }
}