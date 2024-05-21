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

public class ControleurAddArmes implements Initializable {


    @FXML
    public TextField tfNom ,tfDegats;

    @FXML
    public Button btRetour,btAjouter;
    @FXML
    public Label lbMessageError;

    ArmeDaoImpl armeDaoImpl = new ArmeDaoImpl();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfNom.setText("");
        tfDegats.setText("");
        lbMessageError.setText("");
        this.initBtRetour();
        this.initBtAjouter();

    }

    private void initBtAjouter() {
        btAjouter.setOnAction(e -> {
            try {
                this.checkValidity();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

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

    private boolean addArmes(Armes armes) throws IOException {
        return armeDaoImpl.ajouterArme(armes);
    }


    private boolean allFieldsFilled(String nom, String degatsText) {
        return !nom.isEmpty() && !degatsText.isEmpty();
    }

    private int parseInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            lbMessageError.setText("Veuillez entrer un nombre valide.");
            return -1;
        }
    }

    private boolean containsOnlyDigits(String text) {
        for (char c : text.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }






    private void initBtRetour() {
        btRetour.setOnAction(e -> {
            try {
                openVueListArmes();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void openVueListArmes() throws IOException{
        this.goTo("/Vues/lister-arme.fxml");
    }

    private void goTo(String s) throws IOException{
        Stage stagePrincipal = (Stage) btAjouter.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        Parent root = loader.load();
        stagePrincipal.setScene(new Scene(root));
        stagePrincipal.show();
    }
}
