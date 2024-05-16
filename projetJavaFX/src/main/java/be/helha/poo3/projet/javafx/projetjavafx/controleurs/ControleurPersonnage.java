package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import be.helha.poo3.projet.javafx.projetjavafx.personnages.Personnage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControleurPersonnage  implements Initializable {
    @FXML
    public Button btNonconfimation ,btConfirmation ,btSupprimer ,btModifier,btRetour;
    @FXML
    public Pane pnPopup;
    @FXML
    public TextField lbManna, lbPv, lbName;
    public AnchorPane acpVuePersonnage;
    public Label lbId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        pnPopup.setVisible(false);
        initBtRetour();
        initbtConfirmation();
        initbtSupprimer();
        initbtModifier();
        initBtNonconfimation();
    }


    /**
     * Initialise le gestionnaire d'événements pour le bouton btConfirmation
     */
    private void initbtModifier() {
        btModifier.setOnAction(e -> {
            try {
                System.out.println(e.getSource().toString());
                openVuePersonnage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    /**
     * Initialise le gestionnaire d'événements pour le bouton Supprimer
     */
    private void initbtSupprimer() {
        btSupprimer.setOnAction(e -> {
               // openVuePersonnage();
                pnPopup.setVisible(true);

        });
    }
    /**
     * Initialise le gestionnaire d'événements pour le bouton Confirmation
     */
    private void initbtConfirmation() {
        btConfirmation.setOnAction(e -> {
            try {
                openVuePersonnage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    /**
     * Initialise le gestionnaire d'événements pour le bouton  Nonconfirmation
     */
    private void initBtNonconfimation() {
        btNonconfimation.setOnAction(e -> {
               // openVuePersonnage();
            pnPopup.setVisible(false);
        });
    }
    /**
     * Initialise le gestionnaire d'événements pour le bouton retoure
     */
    private void initBtRetour() {
        btRetour.setOnAction(e -> {
            try {
                openVuePersonnage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Cette méthode ouvre la vue Lister-personnages'.
     *
     * @throws IOException Si une erreur d'entrée-sortie se produit lors du chargement de la vue.
     */
        public void openVuePersonnage() throws IOException {
            this.goTo("/Vues/lister-personnage.fxml");
        }
    /**
    * Navigue vers une nouvelle vue spécifiée par le chemin du fichier FXML.
    *
    * @param path Le chemin du fichier FXML de la vue à afficher.
    * @throws IOException Si une erreur d'entrée-sortie se produit lors du chargement de la vue.
    */
        private void goTo(String path) throws IOException {
            Stage stagePrincipal = (Stage) btConfirmation.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            stagePrincipal.setScene(new Scene(root));
            stagePrincipal.show();
        }
}
