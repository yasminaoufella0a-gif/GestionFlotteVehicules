package model;

public class Camion extends Vehicule {

    public Camion(String immatriculation,
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
        return (tarifJournalier * jours) + 100;
    }
}
