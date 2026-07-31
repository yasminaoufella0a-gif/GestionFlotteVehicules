package util;

import model.Vehicule;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RapportWriter {

    public void genererRapport(List<Vehicule> flotte) {

        try {

            PrintWriter writer = new PrintWriter(new FileWriter("rapport.txt"));

            writer.println("=====& RAPPORT DE LA FLOTTE BMW  &=====");
            writer.println();

            System.out.println("=====& RAPPORT DE LA FLOTTE BMW &=====");
            System.out.println();

            for (Vehicule v : flotte) {

                String ligne = v.getImmatriculation() + " - "
                        + v.getMarque() + " "
                        + v.getModele();

                writer.println(ligne);
                System.out.println(ligne);
            }

            writer.close();

            System.out.println();
            System.out.println("Rapport généré avec succès.");

        } catch (IOException e) {

            System.out.println("Erreur lors de la génération du rapport.");
        }
    }
}