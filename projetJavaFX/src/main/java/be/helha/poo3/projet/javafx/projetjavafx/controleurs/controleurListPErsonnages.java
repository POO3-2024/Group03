package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import be.helha.poo3.projet.javafx.projetjavafx.personnages.Gestionpersonnages;
import be.helha.poo3.projet.javafx.projetjavafx.personnages.Personnage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 *@author Mohamed Samba Demba
 *
 */
public class controleurListPErsonnages implements Initializable {

    @FXML
    public Button btAddPerson ,btRetour;
    @FXML
    public VBox vbNom, vbPv, vbMana;
    @FXML
    public ListView lvNom, lvPv, lvMana;

    private final Gestionpersonnages gestionpersonnages = new Gestionpersonnages();
    private List<Personnage> personnages;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.mettreAJourListePersonnages();
        this.initBtAddPerson();
        this.initBtRetour();
    }

    public void mettreAJourListePersonnages() {
        lvNom.getItems().clear();
        lvPv.getItems().clear();
        lvMana.getItems().clear();
        this.listerPersonnages(); // Réaffiche la liste des personnages
    }


    private void initBtRetour() {
        btRetour.setOnAction(e -> {
            try {
                openVueAcceille();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void initBtAddPerson() {
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

    private void openVueAcceille() throws IOException {
        this.closseThisWindows();
        this.mettreAJourListePersonnages();
        this.goTo("/Vues/acceuil.fxml");
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
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

}
