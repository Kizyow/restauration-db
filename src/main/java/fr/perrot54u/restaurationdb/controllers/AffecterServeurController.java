package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.models.Gestionnaire;
import fr.perrot54u.restaurationdb.models.Serveur;
import fr.perrot54u.restaurationdb.views.Views;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class AffecterServeurController {

    public TextArea result;
    public ChoiceBox<Integer> listNumTab;
    public ChoiceBox<Integer> listNumServ;
    public DatePicker date;

    private Gestionnaire personne;

    public void initialize(Serveur personne) {
        this.personne = (Gestionnaire) personne;
        this.initializeChoicebox();
    }

    public void initializeChoicebox() {
        try {
            listNumServ.getItems().setAll(personne.listServeur());
            listNumTab.getItems().setAll(personne.listTable());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void affecterServeur(ActionEvent actionEvent) {

        if (listNumServ.getValue() == null || listNumTab.getValue() == null || date.getValue() == null) {
            result.setText("Erreur : vous n'avez pas mis tous les paramètres");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateFormatted = date.getValue().format(formatter);

        try {
            boolean done = personne.affecterServeur(listNumTab.getValue(), dateFormatted, listNumServ.getValue());
            if (done) {
                result.setText("vous avez bien affecté le serveur n°" + listNumServ.getValue() + " à la table n°" + listNumTab.getValue());
            } else {
                result.setText("L'affectation n'a pas pu être effectuée...");
            }
        } catch (SQLException e) {
            result.setText("L'affectation n'a pas pu être effectuée...");
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
