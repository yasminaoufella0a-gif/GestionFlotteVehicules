package service;

import model.Vehicule;

import java.util.List;


public class StatistiqueService {

    public double calculerRevenuTotal(List<Vehicule> flotte) {

        double total = 0;

        for (Vehicule v : flotte) {
            total += v.getRevenuGenere();
        }

        return total;
    }

    public double calculerKilometrageMoyen(List<Vehicule> flotte) {

        if (flotte.isEmpty()) {
            return 0;
        }

        double total = 0;

        for (Vehicule v : flotte) {
            total += v.getKilometrage();
        }

        return total / flotte.size();
    }

    public int compterVehiculesEnEntretien(List<Vehicule> flotte) {

        int compteur = 0;

        for (Vehicule v : flotte) {

            if (v.entretienNecessaire()) {
                compteur++;
            }
        }

        return compteur;

    }
    public Vehicule vehiculeLePlusUtilise(List<Vehicule> flotte) {

        if (flotte.isEmpty()) {
            return null;
        }

        Vehicule meilleur = flotte.get(0);

        for (Vehicule v : flotte) {

            if (v.getNombreLocations() > meilleur.getNombreLocations()) {
                meilleur = v;
            }
        }

        return meilleur;
    }
    public void afficherTauxUtilisation(List<Vehicule> flotte) {

        int voitures = 0;
        int motos = 0;
        int camions = 0;

        int voituresLouees = 0;
        int motosLouees = 0;
        int camionsLoues = 0;

        for (Vehicule v : flotte) {

            if (v instanceof model.Voiture) {
                voitures++;

                if (v.isEnLocation()) {
                    voituresLouees++;
                }

            } else if (v instanceof model.Moto) {

                motos++;

                if (v.isEnLocation()) {
                    motosLouees++;
                }

            } else if (v instanceof model.Camion) {

                camions++;

                if (v.isEnLocation()) {
                    camionsLoues++;
                }
            }
        }

        System.out.println("\n--- TAUX D'UTILISATION ---");

        System.out.println(
                "Voitures : "
                        + (voitures == 0 ? 0 : (100.0 * voituresLouees / voitures))
                        + " %"
        );

        System.out.println(
                "Motos : "
                        + (motos == 0 ? 0 : (100.0 * motosLouees / motos))
                        + " %"
        );

        System.out.println(
                "Camions : "
                        + (camions == 0 ? 0 : (100.0 * camionsLoues / camions))
                        + " %"
        );
    }
}