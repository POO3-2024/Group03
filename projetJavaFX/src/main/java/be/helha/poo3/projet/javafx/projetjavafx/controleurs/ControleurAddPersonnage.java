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
    @FXML
    public Label lbMessageError;

    /**
     * Gestionnaire des personnages de l'application.
     */
    PersonnageDaoImpl personnageDaoImpl = new PersonnageDaoImpl();

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
        lbMessageError.setText("");
        this.initBtRetour();
        this.initBtAjouter();
    }

    /**
     * Initialise le gestionnaire d'événements pour le bouton Ajouter.
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
     * Ajoute un nouveau personnage à la base de données.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'ajout du personnage.
     *
     * @return {@code true} si le personnage a bien etais ajouter sinom {@code false}
     */
    private boolean addPersonnage(Personnage perso) throws IOException {
        return personnageDaoImpl.ajouterPerso(perso);
    }
    /**
     *
     * Vérifie que tous les champs sont remplis, que les valeurs de points de vie et de mana sont des entiers positifs,
     * et que le nom ne contient que des caractères non numériques avant d'ajoute un nouveau personnage à la base de données.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'ajout du personnage.
     */
    private void checkValidity() throws IOException {
        String name = tfName.getText();
        String manaText = tfManna.getText();
        String pvText = tfPv.getText();
        lbMessageError.setText("");
        if (!allFieldsFilled(name, manaText, pvText)) {
            lbMessageError.setText("Veuillez remplir tous les champs.");
            return;
        }

        int mana = parseInteger(manaText);
        int pv = parseInteger(pvText);

        if (!(mana >= 0 && pv >= 0)) {
            lbMessageError.setText("Les pv et le manna doivent être positifs.");
            return;
        }
        if(containsOnlyDigits(name)){
            lbMessageError.setText("Le nom ne peut pas contenir que des chiffres.");
            return;
        }

        Personnage persoToAdd = new Personnage(name);

        if (persoToAdd.getPointDeVie() < pv){
            lbMessageError.setText("les points de vie ne peuvent pas depasés "+ persoToAdd.getPointDeVie()+".");
            return;
        }else if (persoToAdd.getManna() < mana){
            lbMessageError.setText("le manna ne peut pas depasés "+ persoToAdd.getManna()+".");
            return;
        }else {
            persoToAdd.setPointDeVie(pv);
            persoToAdd.setManna(mana);
        }

        if (!(this.addPersonnage(persoToAdd))){
            lbMessageError.setText("Erreur de l'ajout du personnage.");
        }
        openVueListPersonnage();
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
        this.goTo("/Vues/lister-personnage.fxml");
    }

    /**
     * Charge et affiche une nouvelle vue.
     *
     * @param path le chemin de la vue à charger.
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la lecture de la vue.
     */
    private void goTo(String path) throws IOException {
        Stage stagePrincipal = (Stage) btAjouter.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        stagePrincipal.setScene(new Scene(root));
        stagePrincipal.show();
    }
}
