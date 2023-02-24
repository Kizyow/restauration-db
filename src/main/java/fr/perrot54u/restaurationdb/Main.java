package fr.perrot54u.restaurationdb;

import fr.perrot54u.restaurationdb.utils.FXMLUtils;
import fr.perrot54u.restaurationdb.views.Views;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLUtils.initialize(stage);
        Views.LOGIN.loadScene();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
