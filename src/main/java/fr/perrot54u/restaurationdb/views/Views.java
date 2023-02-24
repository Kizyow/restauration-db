package fr.perrot54u.restaurationdb.views;

import fr.perrot54u.restaurationdb.utils.FXMLUtils;

import java.io.IOException;

public enum Views {

    LOGIN("login-view.fxml", "Connexion - Système de restauration"),
    MENU("menu-view.fxml", "Menu - Système de restauration");

    private String fileName;
    private String windowTitle;

    Views(String fileName, String windowTitle){
        this.fileName = fileName;
        this.windowTitle = windowTitle;
    }

    public void loadScene(){

        try {
            FXMLUtils.loadSceneView(this.fileName, this.windowTitle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
