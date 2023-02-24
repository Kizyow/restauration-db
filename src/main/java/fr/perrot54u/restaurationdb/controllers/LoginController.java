package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.views.Views;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController {

    public TextField login;
    public PasswordField password;

    @FXML
    public void onLogin(MouseEvent mouseEvent) {

        System.out.println(login.getText() + " " + password.getText());
        Views.MENU.loadScene();

    }

}
