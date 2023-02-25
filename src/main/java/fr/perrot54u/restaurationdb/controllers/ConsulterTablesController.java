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

public class ConsulterTablesController {


    public DatePicker date;
    public TextField heure;
    public TextArea result;

    private Serveur personne;

    public void initialize(Serveur personne) {
        this.personne = personne;
    }

    public void consulterTables(ActionEvent actionEvent) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if(date.getValue() == null || heure.getText().isEmpty()){
            result.setText("Erreur : vous n'avez pas mis de date ou d'heure");
            return;
        }

        String dateFormatted = date.getValue().format(formatter);
        String heureFormatted = heure.getText();

        try {
            List<Integer> lists = personne.consulterTablesDisponible(dateFormatted + " " + heureFormatted);
            StringBuilder res = new StringBuilder("Les numéros de tables disponibles sont : \n");
            for (int i : lists) {
                res.append(" - n° ").append(i).append("\n");
            }
            result.setText(res.toString());
        } catch (SQLException e) {
            result.setText("Erreur : vous avez mal inseré la date ou l'heure.");
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
