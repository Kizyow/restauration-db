package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.models.Gestionnaire;
import fr.perrot54u.restaurationdb.models.Plat;
import fr.perrot54u.restaurationdb.models.Serveur;
import fr.perrot54u.restaurationdb.views.Views;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class GererPlatController {


    public TextArea result;
    public TextField nomPlatCree;
    public TextField prixPlatCree;
    public ChoiceBox<String> listTypeCree;
    public ChoiceBox<Integer> listPlat;
    public TextField nomPlatUpdate;
    public TextField prixPlatUpdate;
    public ChoiceBox<String> listTypeUpdate;

    private Gestionnaire personne;

    public void initialize(Serveur personne) {
        this.personne = (Gestionnaire) personne;
        this.initializeChoicebox();
    }

    public void initializeChoicebox() {
        try {
            listPlat.getItems().setAll(personne.listPlat());
            Arrays.stream(Plat.PlatType.values()).forEach(platType -> {
                listTypeCree.getItems().add(platType.getLibelle());
                listTypeUpdate.getItems().add(platType.getLibelle());
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void creerPlat(ActionEvent actionEvent) {

        if (nomPlatCree.getText().isEmpty() || prixPlatCree.getText().isEmpty() || listTypeCree.getValue() == null) {
            result.setText("Erreur : vous n'avez pas mis tous les paramètres");
            return;
        }

        try {
            boolean cree = personne.creerPlat(nomPlatCree.getText(), listTypeCree.getValue(), Double.parseDouble(prixPlatCree.getText()));
            if(cree){
                result.setText("Vous avez bien crée le plat " + nomPlatCree.getText()  + " (" + listTypeCree.getValue() + ") à " + prixPlatCree.getText() + "€");
                listPlat.getItems().setAll(personne.listPlat());
            } else {
                result.setText("La création du plat n'a pas pu être effectuée...");
            }
        } catch (SQLException e) {
            result.setText("La création du plat n'a pas pu être effectuée...");
            throw new RuntimeException(e);
        }

    }

    public void updatePlat(ActionEvent actionEvent) {

        if (nomPlatUpdate.getText().isEmpty() || prixPlatUpdate.getText().isEmpty() || listTypeUpdate.getValue() == null || listPlat.getValue() == null) {
            result.setText("Erreur : vous n'avez pas mis tous les paramètres");
            return;
        }

        try {
            boolean cree = personne.updatePlat(listPlat.getValue(), nomPlatUpdate.getText(), listTypeUpdate.getValue(), Double.parseDouble(prixPlatUpdate.getText()));
            if(cree){
                result.setText("Vous avez bien mis à jour le plat n° " + listPlat.getValue() + " s'appelant " + nomPlatUpdate.getText()  + " (" + listTypeUpdate.getValue() + ") à " + prixPlatUpdate.getText() + "€");
            } else {
                result.setText("La mise à jour du plat n'a pas pu être effectuée...");
            }
        } catch (SQLException e) {
            result.setText("La mise à jour du plat n'a pas pu être effectuée...");
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
