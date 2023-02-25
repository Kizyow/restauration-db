package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.models.Serveur;
import fr.perrot54u.restaurationdb.views.Views;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class CommanderPlatController {

    public TextArea result;
    public ChoiceBox<Integer> listNumRes;
    public ChoiceBox<Integer> listPlats;
    public ChoiceBox<Integer> listQte;

    private Serveur personne;

    public void initialize(Serveur personne) {
        this.personne = personne;
        this.initializeChoicebox();
    }

    public void initializeChoicebox() {
        try {
            listNumRes.getItems().setAll(personne.listReservation());
            listPlats.getItems().setAll(personne.listPlat());
            for (int i = 1; i < 10; i++) listQte.getItems().add(i);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void commanderPlat(ActionEvent actionEvent) {

        if (listNumRes.getValue() == null || listPlats.getValue() == null || listQte.getValue() == null) {
            result.setText("Erreur : vous n'avez pas mis tous les paramètres");
            return;
        }

        try {
            boolean done = personne.commanderPlat(listNumRes.getValue(), listPlats.getValue(), listQte.getValue());
            if (done) {
                result.setText("vous avez bien commandé le plat n°" + listPlats.getValue() + " (" + listQte.getValue() + "x) pour la réservation n°" + listNumRes.getValue());
            } else {
                result.setText("La réservation n'a pas pu être effectuée...");
            }
        } catch (SQLException e) {
            result.setText("La réservation n'a pas pu être effectuée...");
            throw new RuntimeException(e);
        }

    }

    public void onBack(ActionEvent actionEvent) {
        if (personne.getGrade().equalsIgnoreCase("gestionnaire")) {
            MenuController menuController = Views.MENU_GESTIONNAIRE.loadScene();
            menuController.initialize(personne);
        } else {
            MenuController menuController = Views.MENU_SERVEUR.loadScene();
            menuController.initialize(personne);
        }
    }

}
