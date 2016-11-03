package be.rl.j.imc.main;

public class JIMC {

    private final static double IDEAL = 25.;

    public static void main(String[] args) {
        double imc = -1.;
        double tailleIdeal = -1;


        //Coucou Jamal Melhaoui Champion Boxe & Cie Marroco 1997
        double poids = 54.;
        double taille = 1.65;

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
        }
    }

}
