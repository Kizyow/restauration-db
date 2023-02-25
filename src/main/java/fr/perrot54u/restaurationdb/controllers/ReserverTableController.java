package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.models.Serveur;
import fr.perrot54u.restaurationdb.views.Views;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReserverTableController {


    public DatePicker date;
    public TextField heure;
    public TextArea result;
    public TextField numTable;
    public TextField nbPersonnes;

    private Serveur personne;

    public void initialize(Serveur personne) {
        this.personne = personne;
    }

    public void reserverTable(ActionEvent actionEvent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (date.getValue() == null || heure.getText().isEmpty() || numTable.getText().isEmpty() || nbPersonnes.getText().isEmpty()) {
            result.setText("Erreur : vous n'avez pas mis tous les paramètres");
            return;
        }

        String dateFormatted = date.getValue().format(formatter);
        String heureFormatted = heure.getText();
        int numTab = Integer.parseInt(numTable.getText());
        int nbPers = Integer.parseInt(nbPersonnes.getText());

        try {
            boolean hasBeenReserved = personne.reserverTable(numTab, dateFormatted + " " + heureFormatted, nbPers);
            if(hasBeenReserved){
                result.setText("Votre avez bien réservé la table n°" + numTab + " pour le " + dateFormatted + " à " + heureFormatted + " avec " + nbPers + " personnes !");
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
