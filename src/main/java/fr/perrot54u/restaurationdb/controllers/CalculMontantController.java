package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.models.Gestionnaire;
import fr.perrot54u.restaurationdb.models.Serveur;
import fr.perrot54u.restaurationdb.views.Views;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class CalculMontantController {

    public TextArea result;
    public ChoiceBox<Integer> listNumRes;
    public ChoiceBox<String> listModPaiement;
    public DatePicker date;
    public TextField heure;

    private Gestionnaire personne;

    public void initialize(Serveur personne) {
        this.personne = (Gestionnaire) personne;
        this.initializeChoicebox();
    }

    public void initializeChoicebox() {
        try {
            listNumRes.getItems().setAll(personne.listReservation());
            listModPaiement.getItems().setAll(Arrays.asList("Carte", "Chèque", "Espèces"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void calculerMontant(ActionEvent actionEvent) {

        if (listNumRes.getValue() == null || listModPaiement.getValue() == null || date.getValue() == null || heure.getText().isEmpty()) {
            result.setText("Erreur : vous n'avez pas mis tous les paramètres");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateFormatted = date.getValue().format(formatter);
        String heureFormatted = heure.getText();

        try {
            double montantReservation = personne.calculMontantReservation(listNumRes.getValue(), dateFormatted + " " + heureFormatted, listModPaiement.getValue());
            if (montantReservation >= 0) {
                result.setText("Le montant de la commande pour la réservation n°" + listNumRes.getValue() + " est de " + montantReservation + " €");
            } else {
                result.setText("Le calcul du montant n'a pas pu être effectuée...");
            }
        } catch (SQLException e) {
            result.setText("Le calcul du montant n'a pas pu être effectuée...");
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
