package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.models.Gestionnaire;
import fr.perrot54u.restaurationdb.models.Plat;
import fr.perrot54u.restaurationdb.models.Serveur;
import fr.perrot54u.restaurationdb.views.Views;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.Arrays;

public class GererServeurController {


    public TextArea result;
    public TextField nomCree;
    public TextField emailCree;
    public PasswordField passwordCree;
    public ChoiceBox<String> listGradeCree;
    public ChoiceBox<Integer> listServeur;
    public TextField nomUpdate;
    public TextField emailUpdate;
    public PasswordField passwordUpdate;
    public ChoiceBox<String> listGradeUpate;

    private Gestionnaire personne;

    public void initialize(Serveur personne) {
        this.personne = (Gestionnaire) personne;
        this.initializeChoicebox();
    }

    public void initializeChoicebox() {
        try {
            listServeur.getItems().setAll(personne.listServeur());
            listGradeCree.getItems().addAll("serveur", "gestionnaire");
            listGradeUpate.getItems().addAll("serveur", "gestionnaire");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void creerServeur(ActionEvent actionEvent) {

        if (nomCree.getText().isEmpty() || emailCree.getText().isEmpty() || passwordCree.getText().isEmpty() || listGradeCree.getValue() == null) {
            result.setText("Erreur : vous n'avez pas mis tous les paramètres");
            return;
        }

        try {
            boolean cree = personne.creerServeur(nomCree.getText(), emailCree.getText(), passwordCree.getText(), listGradeCree.getValue());
            if (cree) {
                result.setText("Vous avez bien crée le serveur " + nomCree.getText() + " (" + emailCree.getText() + " - " + listGradeCree.getValue() + ")");
                listServeur.getItems().setAll(personne.listServeur());
            } else {
                result.setText("La création du serveur n'a pas pu être effectuée...");
            }
        } catch (SQLException e) {
            result.setText("La création du serveur n'a pas pu être effectuée...");
            throw new RuntimeException(e);
        }

    }

    public void updateServeur(ActionEvent actionEvent) {

        if (listServeur.getValue() == null || nomUpdate.getText().isEmpty() || emailUpdate.getText().isEmpty() || passwordUpdate.getText().isEmpty() || listGradeUpate.getValue() == null) {
            result.setText("Erreur : vous n'avez pas mis tous les paramètres");
            return;
        }

        try {
            boolean cree = personne.updateServeur(listServeur.getValue(), nomUpdate.getText(), emailUpdate.getText(), passwordUpdate.getText(), listGradeUpate.getValue());
            if (cree) {
                result.setText("Vous avez bien mis à jour le serveur " + nomUpdate.getText() + " (" + emailUpdate.getText() + " - " + listGradeUpate.getValue() + ")");
            } else {
                result.setText("La mise à jour du serveur n'a pas pu être effectuée...");
            }
        } catch (SQLException e) {
            result.setText("La mise à jour du serveur n'a pas pu être effectuée...");
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
