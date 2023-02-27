package fr.perrot54u.restaurationdb.views;

import fr.perrot54u.restaurationdb.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public enum Views {

    LOGIN_ORACLE("login-oracle-view.fxml", "Connexion à la base de donnée - Système de restauration"),
    LOGIN_RESTO("login-resto-view.fxml", "Connexion au restaurant - Système de restauration"),
    MENU_SERVEUR("menu-serveur-view.fxml", "Menu (serveur) - Système de restauration"),
    MENU_GESTIONNAIRE("menu-gestionnaire-view.fxml", "Menu (gestionnaire) - Système de restauration"),
    CONSULTER_TABLES("consulter-tables-view.fxml", "Consulter les tables - Système de restauration"),
    RESERVER_TABLE("reserver-table-view.fxml", "Réserver une table - Système de restauration"),
    CONSULTER_PLATS("consulter-plats-view.fxml", "Consulter les plats - Système de restauration"),
    COMMANDER_PLAT("commander-plat-view.fxml", "Commander un plat - Système de restauration"),
    GERER_SERVEUR("gerer-serveur-view.fxml", "Gérer les serveurs - Système de restauration"),
    AFFECTER_SERVEUR("affecter-serveur-view.fxml", "Affecter un serveur - Système de restauration"),
    GERER_PLAT("gerer-plat-view.fxml", "Gérer les plats - Système de restauration"),
    CALCUL_MONTANT("calcul-montant-view.fxml", "Calculer le montant - Système de restauration"),
    AFFICHAGE_SERVEUR_CA("afficher-serveur-ca-view.fxml", "Afficher les serveurs ayant fait du C.A - Système de restauration"),
    AFFICHAGE_SERVEUR_NON_CA("afficher-serveur-non-ca-view.fxml", "Afficher les serveurs n'ayant pas fait du C.A - Système de restauration");

    private final String fileName;
    private final String windowTitle;
    private static Stage stage;

    Views(String fileName, String windowTitle) {
        this.fileName = fileName;
        this.windowTitle = windowTitle;
    }

    public static void initialize(Stage pStage) {
        stage = pStage;
        stage.setResizable(false);
    }

    public <T> T loadScene() {
        URL url = Main.class.getResource("/fxml/" + fileName);
        FXMLLoader fxmlLoader = new FXMLLoader(url);

        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle(windowTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fxmlLoader.getController();

    }

}
