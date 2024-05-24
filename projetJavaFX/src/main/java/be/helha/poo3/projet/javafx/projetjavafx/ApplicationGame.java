package be.helha.poo3.projet.javafx.projetjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.Objects;
/**
 * Classe principale de l'application.
 *@author Mohamed Samba Demba
 *
 */
public class ApplicationGame extends Application {

    /**
     * Fonction qui permet de lancer la vue principale de javaFX
     * @param primaryStage
     */
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
}
