package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.models.Gestionnaire;
import fr.perrot54u.restaurationdb.models.Serveur;
import fr.perrot54u.restaurationdb.models.ServeurData;
import fr.perrot54u.restaurationdb.views.Views;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AfficherServeurCAController {

    public TextArea result;
    public DatePicker dateDebut;
    public DatePicker dateFin;

    private Gestionnaire personne;

    public void initialize(Serveur personne) {
        this.personne = (Gestionnaire) personne;
    }

    public void afficherServeurCA(ActionEvent actionEvent) {

        if (dateDebut.getValue() == null || dateFin.getValue() == null) {
            result.setText("Erreur : vous n'avez pas mis tous les paramètres");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateDebutFormatted = dateDebut.getValue().format(formatter);
        String dateFinFormatted = dateFin.getValue().format(formatter);

        try {
            List<ServeurData> serveurData = personne.afficherServeurCA(dateDebutFormatted, dateFinFormatted);

            String text = "";

            for(ServeurData data : serveurData){
                text += data.getNom() + " (n°" + data.getNumServ() + ") a fait " + data.getMontant() + "€ de C.A pour " + data.getNbCom() + " commande(s) !\n";
            }

            result.setText(text);

        } catch (SQLException e) {
            result.setText("L'affichage des serveurs ayant fait du chiffre d'affaire n'a pas pu être effectuée...");
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
