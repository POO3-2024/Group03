package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import be.helha.poo3.projet.javafx.projetjavafx.personnages.Gestionpersonnages;
import be.helha.poo3.projet.javafx.projetjavafx.personnages.Personnage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 *@author Mohamed Samba Demba
 *
 */
public class ControleurAddPersonnage implements Initializable {
    @FXML
    public TextField tfName ,tfPv ,tfManna;
    @FXML
    public Button btRetour,btAjouter;
    Gestionpersonnages gestionpersonnages = new Gestionpersonnages();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfManna.setText("");
        tfPv.setText("");
        tfName.setText("");
        this.initBtRetour();
        this.initBtAjouter();
    }

    private void initBtAjouter() {
        btAjouter.setOnAction(e -> {
            try {
                this.addPersonnage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void addPersonnage() throws IOException {
       Personnage persoToAdd = new Personnage(tfName.getText());
        // Convertir les chaînes en types appropriés (int dans ce cas)
        int mana = Integer.parseInt(tfManna.getText());
        int pv = Integer.parseInt(tfPv.getText());
        persoToAdd.setPointDeVie(pv);
        persoToAdd.setManna(pv);
        gestionpersonnages.ajouterPerso(persoToAdd);
        this.openVueListPersonnage();
    }


    private void initBtRetour() {
        btRetour.setOnAction(e -> {
            try {
                openVueListPersonnage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    private void openVueListPersonnage() throws IOException {
        this.closseThisWindows();
        this.goTo("/Vues/lister-personnage.fxml");
    }

    private void closseThisWindows(){
        // Fermer la fenêtre principale
        Stage currentStage = (Stage) btAjouter.getScene().getWindow();
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
