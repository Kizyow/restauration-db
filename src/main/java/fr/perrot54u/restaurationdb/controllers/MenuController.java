package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.models.Serveur;
import fr.perrot54u.restaurationdb.views.Views;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MenuController {

    public Text serverData;
    private Serveur personne;

    public void initialize(Serveur personne) {
        this.personne = personne;
        serverData.setText(personne.getNomServ() + " - " + personne.getGrade());
    }

    public void onDisconnect(MouseEvent mouseEvent) {
        Views.LOGIN_RESTO.loadScene();
    }

    public void consulterTablesDisponibles(ActionEvent actionEvent) {
        ConsulterTablesController controller = Views.CONSULTER_TABLES.loadScene();
        controller.initialize(personne);
    }

    public void reserverTable(ActionEvent actionEvent) {
        ReserverTableController controller = Views.RESERVER_TABLE.loadScene();
        controller.initialize(personne);
    }

    public void consulterPlatsDisponibles(ActionEvent actionEvent) {
        ConsulterPlatsController controller = Views.CONSULTER_PLATS.loadScene();
        controller.initialize(personne);
    }

    public void commanderPlats(ActionEvent actionEvent) {
    }

    public void gestionServeurs(ActionEvent actionEvent) {
    }

    public void affectationServeurs(ActionEvent actionEvent) {
    }

    public void gestionPlats(ActionEvent actionEvent) {
    }

    public void calculReservation(ActionEvent actionEvent) {
    }

    public void afficherCA(ActionEvent actionEvent) {
    }

    public void afficherServeursNonCA(ActionEvent actionEvent) {
    }

}
