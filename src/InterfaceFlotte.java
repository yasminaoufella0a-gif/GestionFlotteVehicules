
    import exception.KilometrageInvalideException;
import exception.VehiculeIndisponibleException;
import model.Vehicule;
import service.LocationService;
import service.StatistiqueService;
import util.CSVReader;
import util.RapportWriter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

    public class InterfaceFlotte extends JFrame {

        private final List<Vehicule> vehicules;
        private final LocationService locationService;
        private final StatistiqueService statistiqueService;

        private final JComboBox<Vehicule> listeVehicules;
        private final JTextField champJours;
        private final JTextField champKilometrage;
        private final JTextArea zoneResultat;

        public InterfaceFlotte() {

            CSVReader lecteur = new CSVReader();

            vehicules = lecteur.chargerVehicules(
                    "data/vehicules.csv"
            );

            locationService = new LocationService();
            statistiqueService = new StatistiqueService();

            setTitle("Gestion de la flotte de véhicules");
            setSize(750, 550);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            listeVehicules = new JComboBox<>();

            for (Vehicule vehicule : vehicules) {
                listeVehicules.addItem(vehicule);
            }

            champJours = new JTextField("3", 10);
            champKilometrage = new JTextField("1200", 10);

            zoneResultat = new JTextArea();
            zoneResultat.setEditable(false);
            zoneResultat.setFont(
                    new Font("Monospaced", Font.PLAIN, 14)
            );

            JButton boutonAfficher = new JButton(
                    "Afficher les véhicules"
            );

            JButton boutonLouer = new JButton(
                    "Louer"
            );

            JButton boutonRetourner = new JButton(
                    "Retourner"
            );

            JButton boutonStatistiques = new JButton(
                    "Statistiques"
            );

            JButton boutonRapport = new JButton(
                    "Générer le rapport"
            );

            JPanel panneauSelection = new JPanel(
                    new GridLayout(3, 2, 10, 10)
            );

            panneauSelection.setBorder(
                    BorderFactory.createTitledBorder(
                            "Informations de location"
                    )
            );

            panneauSelection.add(
                    new JLabel("Véhicule :")
            );

            panneauSelection.add(listeVehicules);

            panneauSelection.add(
                    new JLabel("Nombre de jours :")
            );

            panneauSelection.add(champJours);

            panneauSelection.add(
                    new JLabel("Nouveau kilométrage :")
            );

            panneauSelection.add(champKilometrage);

            JPanel panneauBoutons = new JPanel();

            panneauBoutons.add(boutonAfficher);
            panneauBoutons.add(boutonLouer);
            panneauBoutons.add(boutonRetourner);
            panneauBoutons.add(boutonStatistiques);
            panneauBoutons.add(boutonRapport);

            add(panneauSelection, BorderLayout.NORTH);
            add(new JScrollPane(zoneResultat), BorderLayout.CENTER);
            add(panneauBoutons, BorderLayout.SOUTH);

            boutonAfficher.addActionListener(
                    e -> afficherVehicules()
            );

            boutonLouer.addActionListener(
                    e -> louerVehicule()
            );

            boutonRetourner.addActionListener(
                    e -> retournerVehicule()
            );

            boutonStatistiques.addActionListener(
                    e -> afficherStatistiques()
            );

            boutonRapport.addActionListener(
                    e -> genererRapport()
            );
        }

        private Vehicule obtenirVehiculeSelectionne() {

            return (Vehicule) listeVehicules.getSelectedItem();
        }

        private void afficherVehicules() {

            zoneResultat.setText(
                    "--- LISTE DES VÉHICULES ---\n\n"
            );

            if (vehicules.isEmpty()) {
                zoneResultat.append(
                        "Aucun véhicule disponible."
                );
                return;
            }

            for (Vehicule vehicule : vehicules) {
                zoneResultat.append(
                        vehicule + "\n"
                );
            }
        }

        private void louerVehicule() {

            Vehicule vehicule =
                    obtenirVehiculeSelectionne();

            if (vehicule == null) {
                afficherErreur(
                        "Veuillez sélectionner un véhicule."
                );
                return;
            }

            try {

                int jours = Integer.parseInt(
                        champJours.getText()
                );

                double montant =
                        locationService.louerVehicule(
                                vehicule,
                                jours
                        );

                zoneResultat.setText(
                        "--- LOCATION RÉUSSIE ---\n\n"
                );

                zoneResultat.append(
                        "Véhicule : "
                                + vehicule.getImmatriculation()
                                + "\n"
                );

                zoneResultat.append(
                        "Durée : " + jours + " jours\n"
                );

                zoneResultat.append(
                        "Montant : " + montant + " $\n"
                );

            } catch (VehiculeIndisponibleException |
                     IllegalArgumentException e) {

                afficherErreur(e.getMessage());
            }
        }

        private void retournerVehicule() {

            Vehicule vehicule =
                    obtenirVehiculeSelectionne();

            if (vehicule == null) {
                afficherErreur(
                        "Veuillez sélectionner un véhicule."
                );
                return;
            }

            try {

                double kilometrage =
                        Double.parseDouble(
                                champKilometrage.getText()
                        );

                locationService.retournerVehicule(
                        vehicule,
                        kilometrage
                );

                zoneResultat.setText(
                        "--- RETOUR RÉUSSI ---\n\n"
                );

                zoneResultat.append(
                        "Véhicule : "
                                + vehicule.getImmatriculation()
                                + "\n"
                );

                zoneResultat.append(
                        "Kilométrage : "
                                + vehicule.getKilometrage()
                                + " km\n"
                );

                zoneResultat.append(
                        "Entretien nécessaire : "
                                + vehicule.entretienNecessaire()
                                + "\n"
                );

            } catch (KilometrageInvalideException |
                     IllegalArgumentException e) {

                afficherErreur(e.getMessage());
            }
        }

        private void afficherStatistiques() {

            zoneResultat.setText(
                    "--- STATISTIQUES DE LA FLOTTE ---\n\n"
            );

            zoneResultat.append(
                    "Nombre de véhicules : "
                            + vehicules.size()
                            + "\n"
            );

            zoneResultat.append(
                    "Revenu total : "
                            + statistiqueService
                            .calculerRevenuTotal(vehicules)
                            + " $\n"
            );

            zoneResultat.append(
                    "Kilométrage moyen : "
                            + statistiqueService
                            .calculerKilometrageMoyen(vehicules)
                            + " km\n"
            );

            zoneResultat.append(
                    "Véhicules en entretien : "
                            + statistiqueService
                            .compterVehiculesEnEntretien(vehicules)
                            + "\n"
            );

            Vehicule meilleur =
                    statistiqueService
                            .vehiculeLePlusUtilise(vehicules);

            if (meilleur != null) {

                zoneResultat.append(
                        "\nVéhicule le plus utilisé : "
                                + meilleur.getImmatriculation()
                );

                zoneResultat.append(
                        "\nNombre de locations : "
                                + meilleur.getNombreLocations()
                );
            }
        }

        private void genererRapport() {

            RapportWriter rapportWriter =
                    new RapportWriter();

            rapportWriter.genererRapport(vehicules);

            JOptionPane.showMessageDialog(
                    this,
                    "Le rapport a été généré.",
                    "Rapport",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

        private void afficherErreur(String message) {

            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        public static void main(String[] args) {

            SwingUtilities.invokeLater(() -> {

                InterfaceFlotte interfaceFlotte =
                        new InterfaceFlotte();

                interfaceFlotte.setVisible(true);
            });
        }
    }

