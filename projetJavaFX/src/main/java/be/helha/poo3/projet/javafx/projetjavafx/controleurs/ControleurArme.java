package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControleurArme implements Initializable {

    @FXML
    public TextField lbDegats, lbName;
    @FXML
    public Label lbId;
    @FXML
    public Pane pnPopup;

    @FXML
    public Button btNonconfimation ,btConfirmation ,btSupprimer ,btModifier,btRetour;
    public Label lbMessageError;

    int idArmes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pnPopup.setVisible(false);
        initBtRetour();
        initbtConfirmation();
        initbtSupprimer();
        initbtModifier();
        initBtNonconfimation();
    }

    private void initBtNonconfimation() {
    }

    private void initbtModifier() {
    }

    private void initbtSupprimer() {
    }

    private void initbtConfirmation() {
    }

    private void initBtRetour() {
        btRetour.setOnAction(e -> {
            try {
                openVueArmes();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void openVueArmes() throws IOException {
        this.goTo("/Vues/lister-arme.fxml");
    }

    private void goTo(String s) throws IOException {
        Stage stagePrincipal = (Stage) btConfirmation.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        Parent root = loader.load();
        stagePrincipal.setScene(new Scene(root));
        stagePrincipal.show();
    }
}
