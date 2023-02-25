package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.database.DBConnection;
import fr.perrot54u.restaurationdb.models.Gestionnaire;
import fr.perrot54u.restaurationdb.models.Serveur;
import fr.perrot54u.restaurationdb.views.Views;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginOracleController {

    public TextField login;
    public PasswordField password;

    @FXML
    public void onLogin(MouseEvent mouseEvent) {

        try {
            DBConnection.initializeDatabase(login.getText(), password.getText());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Views.LOGIN_RESTO.loadScene();

    }

}
