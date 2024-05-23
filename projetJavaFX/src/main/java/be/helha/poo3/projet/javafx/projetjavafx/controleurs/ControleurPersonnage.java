package be.helha.poo3.projet.javafx.projetjavafx.controleurs;


import be.helha.lib.poo3.daoImpl.PersonnageDaoImpl;
import be.helha.lib.poo3.domaine.Personnage;
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

/**
 * Contrôleur pour la vue permettant d'interagir avec un personnage.
 * Ce contrôleur gère l'affichage et la modification des détails d'un personnage, ainsi que la suppression de celui-ci.
 * Il affiche également un message d'erreur si une validation échoue lors de l'interaction avec le personnage.
 *
 * @author Mohamed Samba Demba
 */
public class ControleurPersonnage  implements Initializable {
    /**
     *
     * Boutons de l’interface utilisateur pour Modifier , supprimer, confirmer ou non la suppression une personnee.
     *
     * */
    @FXML
    public Button btNonconfimation ,btConfirmation ,btSupprimer ,btModifier,btRetour;
    /**
     * Panneau pour afficher une popup de confirmation.
     */
    @FXML
    public Pane pnPopup;
    /**
     * Labels affichant le nom, pv et manna des personnages dans l'application.
     */
    @FXML
    public TextField lbManna, lbPv, lbName;
    /**
     * Labels affichant l'identifiant du personnage et les messages d'erreur dans l'application.
     */
    @FXML
    public Label lbId, lbMessageError;
    /**
     * Gestionnaire des personnages de l'application.
     */
    PersonnageDaoImpl personnageDaoImpl = new PersonnageDaoImpl();

    /**
     * Identifiant du personnage.
     */
    int idPersonnage;


    /**
     * Initialise le contrôleur.
     *
     * @param url            L'emplacement utilisé pour résoudre les chemins relatifs pour l'objet racine, ou null si l'emplacement n'est pas connu.
     * @param resourceBundle Les ressources localisées utilisées par cet objet.
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
     * Initialise le gestionnaire d'événements pour le bouton btConfirmation
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
     * Initialise le gestionnaire d'événements pour le bouton Supprimer
     */
    private void initbtSupprimer() {
        btSupprimer.setOnAction(e -> {
            pnPopup.setVisible(true);
        });
    }
    /**
     * Initialise le gestionnaire d'événements pour le bouton Confirmation
     */
    private void initbtConfirmation() {
        btConfirmation.setOnAction(e -> {
            try {
                checkValidity("supprimer");
                pnPopup.setVisible(false);
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


    /**
     *
     * Vérifie que tous les champs sont remplis, que les valeurs de points de vie et de mana sont des entiers positifs,
     * et que le nom ne contient que des caractères non numériques avant d'ajoute un nouveau personnage à la base de données.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'ajout du personnage.
     */
    private void checkValidity(String operation) throws IOException {
        String name = lbName.getText();
        String manaText = lbManna.getText();
        String pvText = lbPv.getText();
        lbMessageError.setText("");
        if (!allFieldsFilled(name, manaText, pvText)) {
            lbMessageError.setText("Veuillez remplir tous les champs.");
            return;
        }

        int mana = parseInteger(manaText);
        int pv = parseInteger(pvText);

        if (!(mana >= 0 && pv >= 0)) {
            lbMessageError.setText("Les valeurs doivent être des nombres positifs.");
            return;
        }
        if(containsOnlyDigits(name)){
            lbMessageError.setText("Le nom ne peut pas contenir que des chiffres.");
            return;
        }

        Personnage persoToAdd = new Personnage(name);
        persoToAdd.setId(idPersonnage);

        if (persoToAdd.getPointDeVie() < pv){
            lbMessageError.setText("les points de vie ne peuvent pas depasés "+ persoToAdd.getPointDeVie()+".");
            return;
        }else if (persoToAdd.getManna() < mana){
            lbMessageError.setText("le manna ne peut pas depasé "+ persoToAdd.getManna()+".");
            return;
        }else {
            persoToAdd.setPointDeVie(pv);
            persoToAdd.setManna(mana);
        }

        if (Objects.equals(operation, "modifier")){
            if (!(this.modifierPersonnage(persoToAdd))){
                lbMessageError.setText("Erreur de la modification du personnage.");
            }else{
                lbMessageError.setText("Personnage modifier");
            }
        }else if (Objects.equals(operation, "supprimer")){
            if (!(this.supprimer(persoToAdd))){
                lbMessageError.setText("Erreur de la suppression du personnage.");
            }
        }
    }
    /**
     * Modifie les informations d'un personnage dans la base de données.
     *
     * @param perso Le personnage à modifier.
     * @return {@code true} si la modification a été effectuée avec succès, sinon {@code false}.
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la modification du personnage.
     */
    private boolean modifierPersonnage(Personnage perso) throws IOException {
        return personnageDaoImpl.modifierPersonnage(perso);
    }

    /**
     * Supprime un personnage de la base de données.
     *
     * @param perso Le personnage à supprimer.
     * @return {@code true} si la suppression a été effectuée avec succès, sinon {@code false}.
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la suppression du personnage.
     */
    private boolean supprimer(Personnage perso) throws IOException {
        return personnageDaoImpl.supprimerPersonnage(perso.getId());
    }
    /**
     * Ouvre la vue liste des personnages.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'ouverture de la vue.
     */
    private void openVueListPersonnage() throws IOException {
        this.goTo("/Vues/lister-personnage.fxml");
    }
    /**
     * Vérifie si tous les champs sont remplis.
     *
     * @param name     le nom du personnage.
     * @param manaText le texte représentant le mana du personnage.
     * @param pvText   le texte représentant les points de vie du personnage.
     * @return {@code true} si tous les champs sont remplis, sinon {@code false}.
     */
    private boolean allFieldsFilled(String name, String manaText, String pvText) {
        return !name.isEmpty() && !manaText.isEmpty() && !pvText.isEmpty() ;
    }

    /**
     * Convertit une chaîne de caractères en entier.
     *
     * @param text la chaîne de caractères à convertir.
     * @return {@code int} l'entier représenté par la chaîne, ou {@code -1} si la chaîne n'est pas un entier valide.
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
     * Vérifie si une chaîne de caractères contient uniquement des chiffres.
     *
     * @param text la chaîne de caractères à vérifier.
     * @return {@code true} si la chaîne contient uniquement des chiffres, sinon {@code false}.
     */
    private boolean containsOnlyDigits(String text) {
        for (char c : text.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }


}
