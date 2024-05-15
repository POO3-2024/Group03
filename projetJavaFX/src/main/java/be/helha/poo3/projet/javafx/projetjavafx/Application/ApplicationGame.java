package be.helha.poo3.projet.javafx.projetjavafx.Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;
/**
 *
 *@author Mohamed Samba Demba
 *
 */
public class ApplicationGame extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Vues/acceuil.fxml")));
            Scene scene = new Scene(root, 614, 410);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void lancer(String[] args) {
        launch(args);
    }
}
