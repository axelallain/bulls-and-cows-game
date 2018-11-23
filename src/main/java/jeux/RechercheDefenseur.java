package jeux;

import launcher.Menu;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

public class RechercheDefenseur {

    public static void rechercheDefenseur() throws FileNotFoundException {

        Scanner sc = new Scanner(System.in);
        Random r = new Random();

        ResourceBundle bundle = ResourceBundle.getBundle("config");

        int coups = 0;
        int coupsMax = Integer.parseInt(bundle.getString("coupsMaxDefenseur")); // NOMBRE DE COUPS (CONFIGURABLE)
        int fourchette = Integer.parseInt(bundle.getString("chiffreMax"));       // UTILISER DES CHIFFRES ENTRE 1 ET ... (CONFIGURABLE)
        int max = Integer.parseInt(bundle.getString("tailleCode"));              // TAILLE DU CODE (CONFIGURABLE)

        System.out.printf("%n");
        System.out.println("RECHERCHE : DEFENSEUR");
        System.out.println("Choisissez une combinaison !");
        System.out.printf("%n");

        // GENERATION DU CODE PAR L'UTILISATEUR
        int[] code = new int[max];
        String inputCode = sc.next();
        for (int i = 0; i < max; i++) {
            code[i] = (Integer.parseInt(inputCode.charAt(i) + ""));
        }

        // PREMIER ESSAI DU BOT
        ArrayList<Integer> TryBot = new ArrayList<Integer>();
        for (int i = 0; i < max; i++) {
            TryBot.add(r.nextInt(fourchette) + 1);
        }


        while (coups < coupsMax) {
            for (int i = 0; i < max; i++) {
                System.out.printf("%n");
                System.out.println("Ordinateur : " + StringUtils.join(TryBot, ""));
                System.out.printf("%n");
                System.out.print("Donnez des indices : ");
                String[] reponse = new String[max];
                String inputReponse = sc.next();
                for (i = 0; i < max; i++) {
                    reponse[i] = (inputReponse.charAt(i) + "");
                }

                for (i = 0; i < reponse.length; i++) {
                    if (reponse[i].equals("=")) {
                        TryBot.get(i);
                    } else if (reponse[i].equals("+")) {
                        TryBot.set(i, TryBot.get(i) + 1);
                    } else if (reponse[i].equals("-")) {
                        TryBot.set(i, TryBot.get(i) - 1);
                    }
                }
                int numberOfCorrectBot = StringUtils.countMatches(inputReponse, "="); // COMPTE LE NOMBRE DE "="
                coups++;

                if (coups == coupsMax) {
                    System.out.printf("%n");
                    System.out.println("Victoire, l'ordinateur a atteint les " + coupsMax + " coups autorisés");
                    Menu.endMenuRechercheDefenseur();
                }
                if (numberOfCorrectBot == max) {
                    System.out.printf("%n");
                    System.out.println("Défaite, l'ordinateur a trouvé le code en seulement " + coups + " coups !");
                    Menu.endMenuRechercheDefenseur();
                }
            }
        }
    }
}
