package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import be.helha.poo3.projet.javafx.projetjavafx.personnages.Gestionpersonnages;
import be.helha.poo3.projet.javafx.projetjavafx.personnages.Personnage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
/**
 *
 *@author Mohamed Samba Demba
 *
 */
public class ControleurPrincipal implements Initializable {

    @FXML
    public Button btPersonnage, btArme, btAttaquer;

    @FXML
    public Label lbNombreDarme, lbNombreDePersonnage;

    private final Gestionpersonnages gestionpersonnages = new Gestionpersonnages();
    private List<Personnage> personnages;

    private Stage stagePrincipal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getNbPersonnage();
        btPersonnage.setOnAction(event -> {
            try {
                openVuePersonnage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setStagePrincipal(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }

    public void openVuePersonnage() throws IOException {
        this.closseThisWindows();
        this.goTo("/Vues/lister-personnage.fxml");
    }

    private void getNbPersonnage() {
        this.personnages = gestionpersonnages.listerPersonnages();
        lbNombreDePersonnage.setText("" + this.personnages.size());
    }

    private void closseThisWindows(){
        // Fermer la fenêtre principale
        Stage currentStage = (Stage) btPersonnage.getScene().getWindow();
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
