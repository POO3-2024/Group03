package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import be.helha.poo3.projet.javafx.projetjavafx.personnages.Gestionpersonnages;
import be.helha.poo3.projet.javafx.projetjavafx.personnages.Personnage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

/**
 *
 *@author Mohamed Samba Demba
 *
 */
public class controleurListPErsonnages {

    @FXML
    public Button btAddPerson;
    @FXML
    public VBox vbNom, vbPv, vbMana;
    @FXML
    public ListView lvNom, lvPv, lvMana;

    private final Gestionpersonnages gestionpersonnages = new Gestionpersonnages();


    private List<Personnage> personnages;

    public void initialize() {
        this.listerPersonnages();
        btAddPerson.setOnAction(e -> {
            try {
                openVueAddPersonnage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void listerPersonnages() {
         personnages = gestionpersonnages.listerPersonnages();
         for (Personnage personnage : personnages) {
             lvNom.getItems().add(personnage.getName());
             lvPv.getItems().add(personnage.getPointDeVie());
             lvMana.getItems().add(personnage.getManna());

         }

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
