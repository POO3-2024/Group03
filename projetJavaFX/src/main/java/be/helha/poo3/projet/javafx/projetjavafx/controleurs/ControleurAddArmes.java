package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import be.helha.poo3.projet.javafx.projetjavafx.daoImpl.PersonnageDaoImpl;
import be.helha.poo3.projet.javafx.projetjavafx.domaine.Armes;
import be.helha.poo3.projet.javafx.projetjavafx.daoImpl.ArmeDaoImpl;

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

/**
 * Contrôleur pour l'ajout d'armes dans l'application JavaFX.
 * Cette classe gère les interactions entre les éléments de l'interface utilisateur et la logique métier pour l'ajout d'une arme.
 */
public class ControleurAddArmes implements Initializable {


    /**
     * Champ de texte pour le nom et les dégats de l'arme.
     */
    @FXML
    public TextField tfNom ,tfDegats;

    /**
     * Bouton pour retourner à la vue précédente.
     */
    @FXML
    public Button btRetour;

    /**
     * Bouton pour ajouter l'arme.
     */
    @FXML
    public Button btAjouter;
    /**
     * Label pour afficher les messages d'erreur.
     */
    @FXML
    public Label lbMessageError;

    /**
     * Implémentation du DAO pour la gestion des armes.
     */
    ArmeDaoImpl armeDaoImpl = new ArmeDaoImpl();

    /**
     * Initialise la classe du contrôleur. Cette méthode est automatiquement appelée après le chargement du fichier FXML.
     *
     * @param url            L'emplacement utilisé pour résoudre les chemins relatifs pour l'objet racine, ou null si l'emplacement n'est pas connu.
     * @param resourceBundle Les ressources utilisées pour localiser l'objet racine, ou null si l'objet racine n'a pas été localisé.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfNom.setText("");
        tfDegats.setText("");
        lbMessageError.setText("");
        this.initBtRetour();
        this.initBtAjouter();

    }

    /**
     * Initialise le bouton d'ajout avec son action.
     */
    private void initBtAjouter() {
        btAjouter.setOnAction(e -> {
            try {
                this.checkValidity();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Vérifie la validité des champs et ajoute l'arme si tous les champs sont valides.
     *
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    private void checkValidity() throws IOException{
        String nom = tfNom.getText();
        String degatsText = tfDegats.getText();

        lbMessageError.setText("");
        if (!allFieldsFilled(nom, degatsText)) {
            lbMessageError.setText("Veuillez remplir tous les champs.");
            return;
        }

        int degats = parseInteger(degatsText);

        if (!(degats >= 0)) {
            lbMessageError.setText("Les dégats doivent être positifs.");
            return;
        }
        if(containsOnlyDigits(nom)){
            lbMessageError.setText("Le nom ne peut pas contenir que des chiffres.");
            return;
        }

        Armes armeToAdd = new Armes(nom);

        if (armeToAdd.getDegats() < degats){
            lbMessageError.setText("les dégats de vie ne peuvent pas depasés "+ armeToAdd.getDegats()+".");
            return;
        }else {
            armeToAdd.setDegats(degats);
        }

        if (!(this.addArmes(armeToAdd))){
            lbMessageError.setText("Erreur de l'ajout du personnage.");
        }
        openVueListArmes();
    }

    /**
     * Ajoute une arme en utilisant le DAO.
     *
     * @param armes L'arme à ajouter.
     * @return true si l'ajout est réussi, false sinon.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    private boolean addArmes(Armes armes) throws IOException {
        return armeDaoImpl.ajouterArme(armes);
    }

    /**
     * Vérifie si tous les champs sont remplis.
     *
     * @param nom        Le nom de l'arme.
     * @param degatsText Les dégâts de l'arme.
     * @return true si tous les champs sont remplis, false sinon.
     */
    private boolean allFieldsFilled(String nom, String degatsText) {
        return !nom.isEmpty() && !degatsText.isEmpty();
    }

    /**
     * Convertit une chaîne de caractères en entier.
     *
     * @param text La chaîne de caractères à convertir.
     * @return L'entier converti, ou -1 si la chaîne n'est pas un nombre valide.
     */
    private int parseInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            lbMessageError.setText("Veuillez entrer un nombre valide.");
            return -1;
        }
    }

    /**
     * Vérifie si une chaîne de caractères ne contient que des chiffres.
     *
     * @param text La chaîne de caractères à vérifier.
     * @return true si la chaîne ne contient que des chiffres, false sinon.
     */
    private boolean containsOnlyDigits(String text) {
        for (char c : text.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Initialise le bouton de retour avec son action.
     */
    private void initBtRetour() {
        btRetour.setOnAction(e -> {
            try {
                openVueListArmes();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Ouvre la vue de la liste des armes.
     *
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    private void openVueListArmes() throws IOException{
        this.goTo("/Vues/lister-arme.fxml");
    }

    /**
     * Change la scène actuelle pour celle spécifiée.
     *
     * @param s Le chemin de la nouvelle vue.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    private void goTo(String s) throws IOException{
        Stage stagePrincipal = (Stage) btAjouter.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        Parent root = loader.load();
        stagePrincipal.setScene(new Scene(root));
        stagePrincipal.show();
    }
}
