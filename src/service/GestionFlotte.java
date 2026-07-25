package service;

import model.Vehicule;
import java.util.ArrayList;
import java.util.List;

public class GestionFlotte {

    private List<Vehicule> flotte;

    public GestionFlotte() {
        flotte = new ArrayList<>();
    }

    public void ajouterVehicule(Vehicule vehicule) {
        flotte.add(vehicule);
    }

    public void afficherVehicules() {
        for (Vehicule v : flotte) {
            System.out.println(v);
        }
    }

    public List<Vehicule> getFlotte() {
        return flotte;
    }
}