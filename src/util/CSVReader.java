package util;

import exception.DonneeInvalideException;
import exception.KilometrageInvalideException;
import model.Camion;
import model.Moto;
import model.Vehicule;
import model.Voiture;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public List<Vehicule> chargerVehicules(String fichier) {

        List<Vehicule> liste = new ArrayList<>();

        try (BufferedReader lecteur = new BufferedReader(new FileReader(fichier))) {

            String ligne;
            int numeroLigne = 0;

            while ((ligne = lecteur.readLine()) != null) {

                numeroLigne++;

                if (numeroLigne == 1) {
                    continue;
                }

                try {
                    Vehicule vehicule = convertirLigne(ligne);
                    liste.add(vehicule);

                } catch (DonneeInvalideException |
                         KilometrageInvalideException |
                         NumberFormatException e) {

                    System.out.println(
                            "Erreur à la ligne " + numeroLigne + " : " + e.getMessage()
                    );
                }
            }

        } catch (IOException e) {
            System.out.println(
                    "Erreur de lecture du fichier : " + e.getMessage()
            );
        }

        return liste;
    }

    private Vehicule convertirLigne(String ligne)
            throws DonneeInvalideException, KilometrageInvalideException {

        String[] donnees = ligne.split(",");

        if (donnees.length != 8) {
            throw new DonneeInvalideException(
                    "Nombre de colonnes incorrect."
            );
        }

        String type = donnees[0].trim();
        String immatriculation = donnees[1].trim();
        String marque = donnees[2].trim();
        String modele = donnees[3].trim();
        int annee = Integer.parseInt(donnees[4].trim());
        double kilometrage = Double.parseDouble(donnees[5].trim());
        double tarifJournalier = Double.parseDouble(donnees[6].trim());
        boolean disponible = Boolean.parseBoolean(donnees[7].trim());

        if (immatriculation.isEmpty()
                || marque.isEmpty()
                || modele.isEmpty()) {

            throw new DonneeInvalideException(
                    "Une donnée obligatoire est manquante."
            );
        }

        if (kilometrage < 0) {
            throw new KilometrageInvalideException(
                    "Le kilométrage ne peut pas être négatif."
            );
        }

        if (tarifJournalier <= 0) {
            throw new DonneeInvalideException(
                    "Le tarif doit être supérieur à zéro."
            );
        }

        if (type.equalsIgnoreCase("Voiture")) {
            return new Voiture(
                    immatriculation, marque, modele, annee,
                    kilometrage, tarifJournalier, disponible
            );
        }

        if (type.equalsIgnoreCase("Moto")) {
            return new Moto(
                    immatriculation, marque, modele, annee,
                    kilometrage, tarifJournalier, disponible
            );
        }

        if (type.equalsIgnoreCase("Camion")) {
            return new Camion(
                    immatriculation, marque, modele, annee,
                    kilometrage, tarifJournalier, disponible
            );
        }

        throw new DonneeInvalideException(
                "Type de véhicule inconnu : " + type
        );
    }
}