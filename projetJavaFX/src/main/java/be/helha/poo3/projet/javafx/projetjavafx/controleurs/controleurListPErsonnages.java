package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
/**
 *
 *@author Mohamed Samba Demba
 *
 */
public class controleurListPErsonnages {
    @FXML
    public Button btAddPerson;

    public void initialize() {
        btAddPerson.setOnAction(e -> {
            try {
                openVueAddPersonnage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void openVueAddPersonnage() throws IOException {
        this.closseThisWindows();
        this.goTo("/Vues/ajouter-personnage.fxml");
    }


    private void closseThisWindows(){
        // Fermer la fenêtre principale
        Stage currentStage = (Stage) btAddPerson.getScene().getWindow();
        currentStage.close();
    }

    private void goTo(String path) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Liste des personnages");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
    }
}
