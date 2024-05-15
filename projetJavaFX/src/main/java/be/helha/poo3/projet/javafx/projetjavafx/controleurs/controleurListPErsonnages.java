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
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * Contrôleur pour la vue listerPersonnages.
 * Ce contrôleur récupère la liste des personnes dans la base de données et les affiche.
 *@author Mohamed Samba Demba
 *
 */
public class controleurListPErsonnages implements Initializable {
    /**
     * Boutons de l'interface utilisateur pour naviguer vers d'autres vues.
     */
    @FXML
    public Button btAddPerson ,btRetour;
    /**
     * Labels affichant le nom, pv et manna des personnages dans l'application.
     */
    @FXML
    public ListView lvNom, lvPv, lvMana;
    /**
     * Gestionnaire des personnages de l'application.
     */
    private final Gestionpersonnages gestionpersonnages = new Gestionpersonnages();
    /**
     * Liste des personnages de l'application.
     */
    private List<Personnage> personnages;

    /**
     * Initialise le contrôleur.
     *
     * @param url            L'emplacement utilisé pour résoudre les chemins relatifs pour l'objet racine, ou null si l'emplacement n'est pas connu.
     * @param resourceBundle Les ressources localisées utilisées par cet objet.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.mettreAJourListePersonnages();
        this.initBtAddPerson();
        this.initBtRetour();
    }

    /**
     * Met à jour la liste des personnages dans l'affichage.
     */
    public void mettreAJourListePersonnages() {
        lvNom.getItems().clear();
        lvPv.getItems().clear();
        lvMana.getItems().clear();
        this.listerPersonnages(); // Réaffiche la liste des personnages
    }

    /**
     * Initialise le gestionnaire d'événements pour le bouton de retour.
     */
    private void initBtRetour() {
        btRetour.setOnAction(e -> {
            try {
                openVueAcceille();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Initialise le gestionnaire d'événements pour le bouton d'ajout de personnage.
     */
    private void initBtAddPerson() {
        btAddPerson.setOnAction(e -> {
        try {
            openVueAddPersonnage();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    });
    }

    /**
     * Liste les personnages et les affiche dans la vue.
     */
    private void listerPersonnages() {
         personnages = gestionpersonnages.listerPersonnages();
         for (Personnage personnage : personnages) {
             lvNom.getItems().add(personnage.getName());
             lvPv.getItems().add(personnage.getPointDeVie());
             lvMana.getItems().add(personnage.getManna());
             initNameAction(personnage.getName());
         }
    }
    /**
     * Initialise le gestionnaire d'évènements lorsqu'on appuie sur le nom du personnage.
     * **/
    public void initNameAction(String name){
        lvNom.setOnMouseClicked(e -> {
            try {
                openVuePersonnageUser();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    /**
     * Ouvre la vue pour ajouter un personnage.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'ouverture de la vue.
     */
    public void openVueAddPersonnage() throws IOException {
        this.goTo("/Vues/ajouter-personnage.fxml");
    }

    /**
     * Ouvre la vue d'accueil.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'ouverture de la vue.
     */
    private void openVueAcceille() throws IOException {
        this.mettreAJourListePersonnages();
        this.goTo("/Vues/acceuil.fxml");
    }
    /**
     * Ouvre la vue pour vour les detail de l'utilisateur.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'ouverture de la vue.
     */
    private void openVuePersonnageUser() throws IOException {
        this.goTo("/Vues/fiche-personnage.fxml");
    }

    /**
     * Charge et affiche une nouvelle vue.
     *
     * @param path le chemin de la vue à charger.
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la lecture de la vue.
     */
    private void goTo(String path) throws IOException {
        Stage stagePrincipal = (Stage) btAddPerson.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        stagePrincipal.setScene(new Scene(root));
        stagePrincipal.show();
    }
}
