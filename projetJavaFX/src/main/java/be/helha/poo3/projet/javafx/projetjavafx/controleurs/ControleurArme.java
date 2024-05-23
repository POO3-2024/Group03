package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import be.helha.poo3.projet.javafx.projetjavafx.daoImpl.ArmeDaoImpl;
import be.helha.poo3.projet.javafx.projetjavafx.daoImpl.PersonnageDaoImpl;
import be.helha.poo3.projet.javafx.projetjavafx.domaine.Armes;
import be.helha.poo3.projet.javafx.projetjavafx.domaine.Personnage;
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

    @FXML
    public TextField lbDegats, lbName;
    @FXML
    public Label lbId,lbMessageError;
    @FXML
    public Pane pnPopup;

    @FXML
    public Button btNonconfimation ,btConfirmation ,btSupprimer ,btModifier,btRetour;


    int idArmes;

    ArmeDaoImpl armeDaoImpl = new ArmeDaoImpl();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pnPopup.setVisible(false);
        initBtRetour();
        initbtConfirmation();
        initbtSupprimer();
        initbtModifier();
        initBtNonconfimation();
    }

    private void initBtNonconfimation() {
        btNonconfimation.setOnAction(e -> {
            pnPopup.setVisible(false);
        });
    }

    private void initbtModifier() {
        btModifier.setOnAction(e -> {
            try {
                checkValidity("modifier");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void initbtSupprimer() {
        btSupprimer.setOnAction(e -> {
            pnPopup.setVisible(true);
        });
    }

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

    private boolean modifierArmes(Armes armes) throws IOException {
        return armeDaoImpl.modifierArmes(armes);
    }

    private boolean supprimer(Armes arme) throws IOException {
        return armeDaoImpl.supprimerArmes(arme.getId());
    }

    private boolean allFieldsFilled(String name, String degatsText) {
        return !name.isEmpty() && !degatsText.isEmpty();
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
                openVueArmes();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void openVueArmes() throws IOException {
        this.goTo("/Vues/lister-arme.fxml");
    }

    private void goTo(String s) throws IOException {
        Stage stagePrincipal = (Stage) btConfirmation.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        Parent root = loader.load();
        stagePrincipal.setScene(new Scene(root));
        stagePrincipal.show();
    }
}
