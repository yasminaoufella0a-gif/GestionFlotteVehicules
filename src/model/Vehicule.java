package model;

public abstract class Vehicule implements interfaces.Louable, interfaces.Entretenable {
    protected int nombreLocations;
    protected double revenuGenere;
    protected boolean enLocation;
    protected boolean entretienRequis;
    protected String immatriculation;
    protected String marque;
    protected String modele;
    protected int annee;
    protected double kilometrage;
    protected double tarifJournalier;
    protected boolean disponible;

    public Vehicule(String immatriculation,
                    String marque,
                    String modele,
                    int annee,
                    double kilometrage,
                    double tarifJournalier,
                    boolean disponible) {
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.annee = annee;
        this.kilometrage = kilometrage;
        this.tarifJournalier = tarifJournalier;
        this.disponible = disponible;
        this.enLocation = !disponible;
        this.entretienRequis = false;
        this.nombreLocations = 0;
        this.revenuGenere = 0;
    }

    public abstract double calculerTarifLocation(int jours);

    @Override
    public String toString() {
        return immatriculation + " - " + marque + " " + modele;
    }
    @Override
    public void louer() {
        disponible = false;
        enLocation = true;
    }

    @Override
    public void retourner() {
        disponible = true;
        enLocation = false;
    }

    @Override
    public void effectuerEntretien() {
        entretienRequis = false;
    }

    @Override
    public boolean entretienNecessaire() {
        return entretienRequis;
    }
    public String getImmatriculation() {
        return immatriculation;
    }

    public double getKilometrage() {
        return kilometrage;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public boolean isEnLocation() {
        return enLocation;
    }

    public int getNombreLocations() {
        return nombreLocations;
    }

    public double getRevenuGenere() {
        return revenuGenere;
    }

    public void ajouterKilometrage(double kilometres) {
        kilometrage += kilometres;
    }

    public void ajouterRevenu(double montant) {
        revenuGenere += montant;
    }

    public void incrementerNombreLocations() {
        nombreLocations++;
    }

    public void signalerEntretien() {
        entretienRequis = true;
    }
}