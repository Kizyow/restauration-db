package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.models.Serveur;
import fr.perrot54u.restaurationdb.views.Views;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MenuController {

    public Text serverData;
    private Serveur personne;

    /**
     * Controller principal de l'application qui va rediriger vers les fonctionnalités
     */

    public void initialize(Serveur personne) {
        this.personne = personne;
        // charge les infos du serveur/gestionnaire
        serverData.setText(personne.getNomServ() + " - " + personne.getGrade());
    }

    /**
     * Se deconnecter de l'appli du restaurant (pas de la DB!!!)
     * @param mouseEvent
     */
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
        CommanderPlatController controller = Views.COMMANDER_PLAT.loadScene();
        controller.initialize(personne);
    }

    public void gestionServeurs(ActionEvent actionEvent) {
        GererServeurController controller = Views.GERER_SERVEUR.loadScene();
        controller.initialize(personne);
    }

    public void affectationServeurs(ActionEvent actionEvent) {
        AffecterServeurController controller = Views.AFFECTER_SERVEUR.loadScene();
        controller.initialize(personne);
    }

    public void gestionPlats(ActionEvent actionEvent) {
        GererPlatController controller = Views.GERER_PLAT.loadScene();
        controller.initialize(personne);
    }

    public void calculReservation(ActionEvent actionEvent) {
        CalculMontantController controller = Views.CALCUL_MONTANT.loadScene();
        controller.initialize(personne);
    }

    public void afficherCA(ActionEvent actionEvent) {
        AfficherServeurCAController controller = Views.AFFICHAGE_SERVEUR_CA.loadScene();
        controller.initialize(personne);
    }

    public void afficherServeursNonCA(ActionEvent actionEvent) {
        AfficherServeurNonCAController controller = Views.AFFICHAGE_SERVEUR_NON_CA.loadScene();
        controller.initialize(personne);
    }

}
