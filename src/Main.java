import exception.KilometrageInvalideException;
import exception.VehiculeIndisponibleException;
import model.Vehicule;
import service.LocationService;
import util.CSVReader;
import service.StatistiqueService;
import util.RapportWriter;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        CSVReader lecteur = new CSVReader();

        List<Vehicule> vehicules =
                lecteur.chargerVehicules("data/vehicules.csv");

        System.out.println(
                "Nombre de véhicules chargés : " + vehicules.size()
        );

        if (vehicules.isEmpty()) {
            System.out.println("Aucun véhicule disponible.");
            return;
        }

        Vehicule vehicule = vehicules.get(0);
        LocationService locationService = new LocationService();

        try {
            double montant =
                    locationService.louerVehicule(vehicule, 3);

            System.out.println(
                    "Véhicule loué : "
                            + vehicule.getImmatriculation()
            );

            System.out.println(
                    "Montant de la location : "
                            + montant + " €"
            );

            locationService.retournerVehicule(
                    vehicule,
                    1200
            );

            System.out.println("Véhicule retourné.");
            System.out.println(
                    "Nouveau kilométrage : "
                            + vehicule.getKilometrage() + " km"
            );

            System.out.println(
                    "Entretien nécessaire : "
                            + vehicule.entretienNecessaire()
            );

        } catch (VehiculeIndisponibleException |
                 KilometrageInvalideException |
                 IllegalArgumentException e) {

            System.out.println("Erreur : " + e.getMessage());
        }
        StatistiqueService statistiques = new StatistiqueService();

        System.out.println("\n--- STATISTIQUES DE LA FLOTTE ---");

        System.out.println(
                "Revenu total généré : "
                        + statistiques.calculerRevenuTotal(vehicules)
                        + " €"
        );

        System.out.println(
                "Kilométrage moyen : "
                        + statistiques.calculerKilometrageMoyen(vehicules)
                        + " km"
        );

        System.out.println(
                "Véhicules nécessitant un entretien : "
                        + statistiques.compterVehiculesEnEntretien(vehicules)
        );
        Vehicule meilleur = statistiques.vehiculeLePlusUtilise(vehicules);

        if (meilleur != null) {

            System.out.println("\n--- VÉHICULE LE PLUS UTILISÉ ---");

            System.out.println(
                    meilleur.getImmatriculation()
            );

            System.out.println(
                    "Nombre de locations : "
                            + meilleur.getNombreLocations()
            );
        }
        RapportWriter rapport = new RapportWriter();
        statistiques.afficherTauxUtilisation(vehicules);
        rapport.genererRapport(vehicules);

    }
}