package be.helha.poo3.projet.javafx.projetjavafx.controleurs;


import be.helha.lib.poo3.daoImpl.ArmeDaoImpl;
import be.helha.lib.poo3.domaine.Armes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
/**
 * Contrôleur pour la liste des armes dans l'application JavaFX.
 * Cette classe gère l'affichage des armes et les interactions utilisateur pour ajouter ou afficher les détails des armes.
 *
 * @author Alahyane Abdel
 */
public class ControleurListArmes implements Initializable {
    /**
     * Boîte de contenu pour les noms des armes.
     */
    public VBox vbNom;
    /**
     * Boîte de contenu pour les dégâts des armes.
     */
    public VBox vbDegats;

    /**
     * Bouton pour ajouter une nouvelle arme.
     */
    @FXML
    public Button btAddArmes;

    /**
     * Bouton pour retourner à la vue précédente.
     */
    @FXML
    public Button btRetour;

    /**
     * Liste des noms des armes.
     */
    @FXML
    public ListView lvNom ;

    /**
     * Liste des dégâts des armes.
     */
    @FXML
    public ListView lvDegats;

    /**
     * Implémentation du DAO pour la gestion des armes.
     */
    private final ArmeDaoImpl armeDaoImpl = new ArmeDaoImpl();

    /**
     * Liste des armes.
     */
    private List<Armes> armesList;

    /**
     * Initialise la classe du contrôleur. Cette méthode est automatiquement appelée après le chargement du fichier FXML.
     *
     * @param url            L'emplacement utilisé pour résoudre les chemins relatifs pour l'objet racine, ou null si l'emplacement n'est pas connu.
     * @param resourceBundle Les ressources utilisées pour localiser l'objet racine, ou null si l'objet racine n'a pas été localisé.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.mettreAJourListeArmes();
        this.initBtAddArmes();
        this.initBtRetour();
    }

    /**
     * Initialise le bouton d'ajout d'armes avec son action.
     */
    private void initBtAddArmes() {
        btAddArmes.setOnAction(e -> {
            try {
                openVueAddArmes();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Ouvre la vue pour ajouter une nouvelle arme.
     *
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    private void openVueAddArmes() throws IOException{
        this.goTo("/Vues/ajouter-arme.fxml");
    }

    /**
     * Initialise le bouton de retour avec son action.
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
     * Ouvre la vue d'accueil.
     *
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    private void openVueAcceille() throws IOException{
        this.mettreAJourListeArmes();
        this.goTo("/Vues/acceuil.fxml");
    }

    /**
     * Change la scène actuelle pour celle spécifiée.
     *
     * @param s Le chemin de la nouvelle vue.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    private void goTo(String s) throws IOException {
        Stage stagePrincipal = (Stage) btAddArmes.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        Parent root = loader.load();
        stagePrincipal.setScene(new Scene(root));
        stagePrincipal.show();
    }

    /**
     * Met à jour la liste des armes affichées.
     */
    private void mettreAJourListeArmes() {
        lvNom.getItems().clear();
        lvDegats.getItems().clear();
        this.listerArmes();
    }

    /**
     * Liste les armes et les affiche dans les listes correspondantes.
     */
    private void listerArmes() {
        armesList = armeDaoImpl.ListerArmes();

        for (Armes armes : armesList) {
            Label lbNameArme= new Label(armes.getNom());
            lbNameArme.setMinWidth(420);
            initNameAction(lbNameArme, armes);
            lvNom.getItems().add(lbNameArme);
            lvDegats.getItems().add(armes.getDegats());

        }
    }

    /**
     * Initialise l'action de clic sur le nom d'une arme pour ouvrir sa vue de détails.
     *
     * @param lbNameArmes Le label contenant le nom de l'arme.
     * @param armes       L'arme correspondante.
     */
    private void initNameAction(Label lbNameArmes, Armes armes) {
        lbNameArmes.setOnMouseClicked(e -> {
            try {
                openVueArmes(armes);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Ouvre la vue des détails d'une arme.
     *
     * @param armes L'arme à afficher dans les détails.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    private void openVueArmes(Armes armes) throws IOException {
        Stage stagePrincipal = (Stage) lvNom.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/fiche-arme.fxml"));
        Parent root = loader.load();
        ControleurArme controller = loader.getController();

        // Stocker les données dans le nœud racine de la vue source


        controller.lbId.setText("Armes : "+ armes.getNom());
        controller.lbName.setText(armes.getNom());
        controller.lbDegats.setText(""+ armes.getDegats());
        controller.idArmes = armes.getId();

        stagePrincipal.setScene(new Scene(root));
        stagePrincipal.show();
    }

}
