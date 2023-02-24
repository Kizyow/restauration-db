package fr.perrot54u.restaurationdb.views;

import fr.perrot54u.restaurationdb.utils.FXMLUtils;

import java.io.IOException;

public enum Views {

    LOGIN_ORACLE("login-oracle-view.fxml", "Connexion à la base de donnée - Système de restauration"),
    LOGIN_RESTO("login-resto-view.fxml", "Connexion au restaurant - Système de restauration"),
    MENU_SERVEUR("menu-serveur-view.fxml", "Menu (serveur) - Système de restauration"),
    MENU_GESTIONNAIRE("menu-gestionnaire-view.fxml", "Menu (gestionnaire) - Système de restauration");

    private String fileName;
    private String windowTitle;

    Views(String fileName, String windowTitle) {
        this.fileName = fileName;
        this.windowTitle = windowTitle;
    }

    public void loadScene() {

        try {
            FXMLUtils.loadSceneView(this.fileName, this.windowTitle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
