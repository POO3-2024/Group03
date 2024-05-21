package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ControleurAddArmes implements Initializable {


    @FXML
    public TextField tfNom ,tfDegats;

    @FXML
    public Button btRetour,btAjouter;
    @FXML
    public Label lbMessageError;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
