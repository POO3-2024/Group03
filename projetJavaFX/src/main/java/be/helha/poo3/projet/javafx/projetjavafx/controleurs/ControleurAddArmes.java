package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
        tfNom.setText("");
        tfDegats.setText("");
        lbMessageError.setText("");
        this.initBtRetour();
        this.initBtAjouter();

    }

    private void initBtAjouter() {
    }

    private void initBtRetour() {
        btRetour.setOnAction(e -> {
            try {
                openVueListArmes();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void openVueListArmes() throws IOException{
        this.goTo("/Vues/lister-arme.fxml");
    }

    private void goTo(String s) throws IOException{
        Stage stagePrincipal = (Stage) btAjouter.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        Parent root = loader.load();
        stagePrincipal.setScene(new Scene(root));
        stagePrincipal.show();
    }
}
