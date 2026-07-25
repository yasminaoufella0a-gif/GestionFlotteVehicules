package model;

public class Voiture extends Vehicule {

    public Voiture(String immatriculation,
                   String marque,
                   String modele,
                   int annee,
                   double kilometrage,
                   double tarifJournalier,
                   boolean disponible) {

        super(immatriculation, marque, modele, annee,
                kilometrage, tarifJournalier, disponible);
    }

    @Override
    public double calculerTarifLocation(int jours) {
        return tarifJournalier * jours;
    }
}