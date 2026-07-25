package service;

import exception.KilometrageInvalideException;
import exception.VehiculeIndisponibleException;
import model.Vehicule;

public class LocationService {

    public double louerVehicule(Vehicule vehicule, int nombreJours)
            throws VehiculeIndisponibleException {

        if (!vehicule.isDisponible()) {
            throw new VehiculeIndisponibleException(
                    "Le véhicule " + vehicule.getImmatriculation()
                            + " est indisponible."
            );
        }

        if (nombreJours <= 0) {
            throw new IllegalArgumentException(
                    "Le nombre de jours doit être supérieur à zéro."
            );
        }

        double montant = vehicule.calculerTarifLocation(nombreJours);

        vehicule.louer();
        vehicule.incrementerNombreLocations();
        vehicule.ajouterRevenu(montant);

        return montant;
    }

    public void retournerVehicule(Vehicule vehicule, double kilometresParcourus)
            throws KilometrageInvalideException {

        if (kilometresParcourus < 0) {
            throw new KilometrageInvalideException(
                    "Le kilométrage parcouru ne peut pas être négatif."
            );
        }

        vehicule.ajouterKilometrage(kilometresParcourus);
        vehicule.retourner();

        if (kilometresParcourus >= 1000) {
            vehicule.signalerEntretien();
        }
    }
}