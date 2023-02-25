package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.models.Plat;
import fr.perrot54u.restaurationdb.models.Serveur;
import fr.perrot54u.restaurationdb.views.Views;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsulterPlatsController {


    public TextArea result;

    private Serveur personne;

    public void initialize(Serveur personne) {
        this.personne = personne;
        this.consulterPlats();
    }

    public void consulterPlats() {
        try {
            List<Plat> plats = personne.consulterPlats();
            StringBuilder res = new StringBuilder("Les plats disponibles sont : \n");
            for (Plat plat : plats) {
                res.append(" - ")
                        .append(plat.getNomPlat())
                        .append(" (n°")
                        .append(plat.getNumPlat())
                        .append(", ")
                        .append(plat.getPlatType().getLibelle())
                        .append(") et coûte ")
                        .append(plat.getPrix())
                        .append("€")
                        .append("\n");
            }
            result.setText(res.toString());

        } catch (SQLException e) {
            result.setText("Erreur d'affichage des plats");
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
