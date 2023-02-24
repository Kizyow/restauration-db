package fr.perrot54u.restaurationdb.utils;

import fr.perrot54u.restaurationdb.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class FXMLUtils {

    private static Stage stage;

    public static void initialize(Stage pStage){
        stage = pStage;
        stage.setResizable(false);
    }

    public static void loadSceneView(String fileName, String windowTitle) throws IOException {
        URL url = Main.class.getResource(fileName);
        FXMLLoader fxmlLoader = new FXMLLoader(url);

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(windowTitle);
        stage.setScene(scene);
        stage.show();

    }

}
