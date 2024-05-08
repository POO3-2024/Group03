package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import be.helha.poo3.projet.javafx.projetjavafx.personnages.Gestionpersonnages;
import be.helha.poo3.projet.javafx.projetjavafx.personnages.Personnage;
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
 *Contrôleur pour la vue qui permet d’ajouter un personnage.
 *
 *@author Mohamed Samba Demba
 *
 */
public class ControleurAddPersonnage implements Initializable {
    /**
     * Labels affichant le nom, pv et manna des personnages dans l'application.
     */
    @FXML
    public TextField tfName ,tfPv ,tfManna;
    /**
     *
     * Boutons de l’interface utilisateur pour ajouter une personne et passer à d’autres vues.
     *
     * */
    @FXML
    public Button btRetour,btAjouter;

    /**
     * Gestionnaire des personnages de l'application.
     */
    Gestionpersonnages gestionpersonnages = new Gestionpersonnages();

    /**
     * Initialise le contrôleur.
     *
     * @param url            L'emplacement utilisé pour résoudre les chemins relatifs pour l'objet racine, ou null si l'emplacement n'est pas connu.
     * @param resourceBundle Les ressources localisées utilisées par cet objet.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfManna.setText("");
        tfPv.setText("");
        tfName.setText("");
        this.initBtRetour();
        this.initBtAjouter();
    }

    /**
     * Initialise le gestionnaire d'événements pour le bouton Ajouter.
     */
    private void initBtAjouter() {
        btAjouter.setOnAction(e -> {
            try {
                this.addPersonnage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Ajoute un nouveau personnage à la base de données.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'ajout du personnage.
     */
    private void addPersonnage() throws IOException {
       Personnage persoToAdd = new Personnage(tfName.getText());

        // Convertir les chaînes en int
        int mana = Integer.parseInt(tfManna.getText());
        int pv = Integer.parseInt(tfPv.getText());

        persoToAdd.setPointDeVie(pv);
        persoToAdd.setManna(mana);

        gestionpersonnages.ajouterPerso(persoToAdd);
        this.openVueListPersonnage();
    }

    /**
     * Initialise le gestionnaire d'événements pour le bouton Retour.
     */
    private void initBtRetour() {
        btRetour.setOnAction(e -> {
            try {
                openVueListPersonnage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Ouvre la vue liste des personnages.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'ouverture de la vue.
     */
    private void openVueListPersonnage() throws IOException {
        this.closseThisWindows();
        this.goTo("/Vues/lister-personnage.fxml");
    }

    /**
     * Ferme la vue actuelle.
     */
    private void closseThisWindows(){
        // Fermer la fenêtre principale
        Stage currentStage = (Stage) btAjouter.getScene().getWindow();
        currentStage.close();
    }


    /**
     * Charge et affiche une nouvelle vue.
     *
     * @param path le chemin de la vue à charger.
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la lecture de la vue.
     */
    private void goTo(String path) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

}
