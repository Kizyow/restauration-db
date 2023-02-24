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

    private static Scanner scanner = new Scanner(System.in);

    public static boolean action(Serveur personne) {

        System.out.println("**Selectionnez l'action que vous souhaitez faire**");
        System.out.println("1. Consulter les tables disponibles à une date et heure donnée");
        System.out.println("2. Réserver une table à une date et heure donnée");
        System.out.println("Q. Pour quitter l'application");

        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("q")) {
            return false;
        }

        int rep = Integer.parseInt(input);

        switch (rep) {
            case 1:
                System.out.println("* Date et heure (sous format dd/mm/yyyy hh:mi) : ");
                String dateHeure = scanner.nextLine();

                try {
                    System.out.println("Voici les tables disponibles d'après vos informations :");
                    for (int t : personne.consulterTablesDisponible(dateHeure.trim())) {
                        System.out.println("Table n°" + t + " disponible");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 2:
                System.out.println("* Numéro de table : ");
                int numTab = Integer.parseInt(scanner.nextLine());
                System.out.println("* Date et heure (sous format dd/mm/yyyy hh:mi) : ");
                String dateHeure1 = scanner.nextLine();
                System.out.println("* Nombre de personnes : ");
                int nbPers = Integer.parseInt(scanner.nextLine());
                try {
                    if (personne.reserverTable(numTab, dateHeure1, nbPers)) {
                        System.out.println("La réservation a été effectuée !");
                    } else {
                        System.out.println("La réservation a échouée !");
                    }
                } catch (SQLException e1) {
                    throw new RuntimeException(e1);
                }

            default:
                break;
        }


        return true;

    }

    public static Serveur authenticate() throws SQLException, ClassNotFoundException {

        Connection connection = DBConnection.createSession();
        connection.setAutoCommit(false);

        System.out.print("Email du serveur: ");
        String username = scanner.nextLine();
        System.out.print("Mot de passe du serveur: ");
        String password = scanner.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM serveur WHERE email = ? AND passwd = ?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet rs = preparedStatement.executeQuery();
        Serveur personne = null;

        if (rs.next()) {
            int numserv = rs.getInt("numserv");
            String email = rs.getString("email");
            String nomServ = rs.getString("nomserv");
            String grade = rs.getString("grade");
            if (grade.equalsIgnoreCase("gestionnaire")) {
                personne = new Gestionnaire(numserv, email, nomServ);
            } else {
                personne = new Serveur(numserv, email, nomServ);
            }
        }

        connection.commit();
        connection.close();

        return personne;

    }

}
