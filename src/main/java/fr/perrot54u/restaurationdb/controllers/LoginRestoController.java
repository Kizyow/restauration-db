package fr.perrot54u.restaurationdb.controllers;

import fr.perrot54u.restaurationdb.database.DBConnection;
import fr.perrot54u.restaurationdb.models.Gestionnaire;
import fr.perrot54u.restaurationdb.models.Serveur;
import fr.perrot54u.restaurationdb.views.Views;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginRestoController {

    public TextField login;
    public PasswordField password;
    public Text errorMsg;

    @FXML
    public void onLogin(MouseEvent mouseEvent) {

        try {

            Serveur personne = this.authenticate();

            if (personne == null) {
                errorMsg.setVisible(true);
                return;
            }

            if (personne.getGrade().equalsIgnoreCase("gestionnaire")) {
                MenuController menuController = Views.MENU_GESTIONNAIRE.loadScene();
                menuController.initialize(personne);
            } else {
                MenuController menuController = Views.MENU_SERVEUR.loadScene();
                menuController.initialize(personne);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public Serveur authenticate() throws SQLException {

        Connection connection = DBConnection.createSession();
        connection.setAutoCommit(false);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM serveur WHERE email = ? AND passwd = ?");
        preparedStatement.setString(1, login.getText());
        preparedStatement.setString(2, password.getText());
        ResultSet rs = preparedStatement.executeQuery();
        Serveur p = null;

        if (rs.next()) {
            int numserv = rs.getInt("numserv");
            String email = rs.getString("email");
            String nomServ = rs.getString("nomserv");
            String grade = rs.getString("grade");
            if (grade.equalsIgnoreCase("gestionnaire")) {
                p = new Gestionnaire(numserv, email, nomServ);
            } else {
                p = new Serveur(numserv, email, nomServ);
            }
        }

        connection.commit();
        connection.close();

        return p;

    }

}
