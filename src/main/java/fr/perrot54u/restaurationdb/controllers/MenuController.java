package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.models.Serveur;
import fr.perrot54u.restaurationdb.views.Views;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class MenuController {

    public Text serverData;

    public void loadServerData(Serveur personne) {
        serverData.setText(personne.getNomServ() + " - " + personne.getGrade());
    }

    public void consulterTablesDisponibles(ActionEvent actionEvent) {
        System.out.println("cc");
    }

    public void onDisconnect(MouseEvent mouseEvent) {
        Views.LOGIN_RESTO.loadScene();
    }


}
