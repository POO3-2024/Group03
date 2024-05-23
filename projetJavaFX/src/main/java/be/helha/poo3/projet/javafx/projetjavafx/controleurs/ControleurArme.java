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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControleurArme implements Initializable {

    /**
     * Champ de texte pour les dégâts de l'arme.
     *
     *@author Alahyane Abdel
     */
    @FXML
    public TextField lbDegats;
    /**
     * Champ de texte pour le nom de l'arme.
     */
    @FXML
    public TextField lbName;
    /**
     * Label pour l'identifiant de l'arme.
     */
    @FXML
    public Label lbId;

    /**
     * Label pour afficher les messages d'erreur.
     */
    @FXML
    public Label
    lbMessageError;

    /**
     * Panneau pour le popup de confirmation.
     */
    @FXML
    public Pane pnPopup;

    /**
     * Bouton pour annuler la confirmation.
     */
    @FXML
    public Button btNonconfimation;
    /**
     * Bouton pour confirmer l'action.
     */
    @FXML
    public Button btConfirmation ;

    /**
     * Bouton pour supprimer l'arme.
     */
    @FXML
    public Button btSupprimer;

    /**
     * Bouton pour modifier l'arme.
     */
    @FXML
    public Button btModifier;

    /**
     * Bouton pour retourner à la vue précédente.
     */
    @FXML
    public Button btRetour;

    /**
     * Identifiant de l'arme en cours de modification ou de suppression.
     */
    int idArmes;

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
        pnPopup.setVisible(false);
        initBtRetour();
        initbtConfirmation();
        initbtSupprimer();
        initbtModifier();
        initBtNonconfimation();
    }

    /**
     * Initialise le bouton d'annulation de confirmation avec son action.
     */
    private void initBtNonconfimation() {
        btNonconfimation.setOnAction(e -> {
            pnPopup.setVisible(false);
        });
    }

    /**
     * Initialise le bouton de modification avec son action.
     */
    private void initbtModifier() {
        btModifier.setOnAction(e -> {
            try {
                checkValidity("modifier");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    /**
     * Initialise le bouton de suppression avec son action.
     */
    private void initbtSupprimer() {
        btSupprimer.setOnAction(e -> {
            pnPopup.setVisible(true);
        });
    }

    /**
     * Initialise le bouton de confirmation avec son action.
     */
    private void initbtConfirmation() {
        btConfirmation.setOnAction(e -> {
            try {
                checkValidity("supprimer");
                pnPopup.setVisible(false);
                openVueArmes();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    /**
     * Vérifie la validité des champs et effectue l'opération spécifiée (modification ou suppression) si tous les champs sont valides.
     *
     * @param operation L'opération à effectuer ("modifier" ou "supprimer").
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    private void checkValidity(String operation) throws IOException{
        String name = lbName.getText();
        String degatsText = lbDegats.getText();
        lbMessageError.setText("");
        if (!allFieldsFilled(name, degatsText)) {
            lbMessageError.setText("Veuillez remplir tous les champs.");
            return;
        }

        int degats = parseInteger(degatsText);

        if (!(degats >= 0)) {
            lbMessageError.setText("Les valeurs doivent être des nombres positifs.");
            return;
        }
        if(containsOnlyDigits(name)){
            lbMessageError.setText("Le nom ne peut pas contenir que des chiffres.");
            return;
        }

        Armes armesToAdd = new Armes(name);
        armesToAdd.setId(idArmes);

        if (armesToAdd.getDegats() < degats){
            lbMessageError.setText("les dégats ne peuvent pas depasés "+ armesToAdd.getDegats()+".");
            return;
        }else {
            armesToAdd.setDegats(degats);
        }

        if (Objects.equals(operation, "modifier")){
            if (!(this.modifierArmes(armesToAdd))){
                lbMessageError.setText("Erreur de la modification de l'arme.");
            }else{
                lbMessageError.setText("Arme modifier");
            }
        }else if (Objects.equals(operation, "supprimer")){
            if (!(this.supprimer(armesToAdd))){
                lbMessageError.setText("Erreur de la suppression de l'arme.");
            }
        }
    }

    /**
     * Modifie une arme en utilisant le DAO.
     *
     * @param armes L'arme à modifier.
     * @return true si la modification est réussie, false sinon.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    private boolean modifierArmes(Armes armes) throws IOException {
        return armeDaoImpl.modifierArmes(armes);
    }


    /**
     * Supprime une arme en utilisant le DAO.
     *
     * @param arme L'arme à supprimer.
     * @return true si la suppression est réussie, false sinon.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    private boolean supprimer(Armes arme) throws IOException {
        return armeDaoImpl.supprimerArmes(arme.getId());
    }

    /**
     * Vérifie si tous les champs sont remplis.
     *
     * @param name       Le nom de l'arme.
     * @param degatsText Les dégâts de l'arme.
     * @return true si tous les champs sont remplis, false sinon.
     */
    private boolean allFieldsFilled(String name, String degatsText) {
        return !name.isEmpty() && !degatsText.isEmpty();
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
                openVueArmes();
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
    private void openVueArmes() throws IOException {
        this.goTo("/Vues/lister-arme.fxml");
    }


    /**
     * Change la scène actuelle pour celle spécifiée.
     *
     * @param s Le chemin de la nouvelle vue.
     * @throws IOException Si une erreur d'entrée/sortie se produit.
     */
    private void goTo(String s) throws IOException {
        Stage stagePrincipal = (Stage) btConfirmation.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        Parent root = loader.load();
        stagePrincipal.setScene(new Scene(root));
        stagePrincipal.show();
    }
}
