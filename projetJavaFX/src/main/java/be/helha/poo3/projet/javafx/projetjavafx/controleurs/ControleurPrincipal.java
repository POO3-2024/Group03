package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import be.helha.poo3.projet.javafx.projetjavafx.personnages.Gestionpersonnages;
import be.helha.poo3.projet.javafx.projetjavafx.personnages.Personnage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 *Contrôleur principal de l'application.
 *Ce contrôleur gère la logique de l'interface accueil de l'application.
 *Il affiche le nombre de personnages et navigue vers d'autres vues.
 *
 *@author Mohamed Samba Demba
 *
 */

public class ControleurPrincipal implements Initializable {
    /**
     * Boutons de l'interface utilisateur pour naviguer vers d'autres vues.
     */
    @FXML
    public Button btPersonnage, btArme, btAttaquer;

    /**
     * Labels affichant le nombre de personnages et d'armes dans l'application.
     */
    @FXML
    public Label lbNombreDarme, lbNombreDePersonnage;

    /**
     * Gestionnaire des personnages de l'application.
     */
    private final Gestionpersonnages gestionpersonnages = new Gestionpersonnages();

    /**
     * Liste des personnages de l'application.
     */
    private List<Personnage> personnages;

    /**
     * Référence à la fenêtre principale de l'application.
     */
    private Stage stagePrincipal;

    /**
     * Initialise le contrôleur lors de son chargement.
     * Affiche le nombre de personnages au chargement de la vue.
     *
     * @param url            L'URL utilisée pour initialiser le contrôleur.
     * @param resourceBundle Le ResourceBundle utilisé pour localiser l'objet racine.
     */
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

    /**
     * Définis la fenêtre principale de l'application.
     *pour pouvoir la fermer lorsqu'on change de vue
     *
     * @param stagePrincipal La fenêtre principale de l'application.
     */
    public void setStagePrincipal(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
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
     * Récupère le nombre de personnages dans la base de données et l'affiche dans un label.
     */
    private void getNbPersonnage() {
        this.personnages = gestionpersonnages.listerPersonnages();
        lbNombreDePersonnage.setText("" + this.personnages.size());
    }

    /**
     * Navigue vers une nouvelle vue spécifiée par le chemin du fichier FXML.
     *
     * @param path Le chemin du fichier FXML de la vue à afficher.
     * @throws IOException Si une erreur d'entrée-sortie se produit lors du chargement de la vue.
     */
    private void goTo(String path) throws IOException {
        Stage stagePrincipal = (Stage) btPersonnage.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        stagePrincipal.setScene(new Scene(root));
        stagePrincipal.show();
    }
}
