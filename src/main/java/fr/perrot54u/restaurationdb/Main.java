package fr.perrot54u.restaurationdb;

import fr.perrot54u.restaurationdb.views.Views;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Point d'entrée de l'application
     */

    @Override
    public void start(Stage stage) {

        // initialiser les vues
        Views.initialize(stage);
        // charger l'application en demandant à l'user de se login à la base de donnée infodb d'oracle de charlemagne
        Views.LOGIN_ORACLE.loadScene();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
