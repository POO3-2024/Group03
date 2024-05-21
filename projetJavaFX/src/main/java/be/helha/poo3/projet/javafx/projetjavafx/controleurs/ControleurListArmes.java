package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import be.helha.poo3.projet.javafx.projetjavafx.domaine.Armes;
import be.helha.poo3.projet.javafx.projetjavafx.domaine.Personnage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import be.helha.poo3.projet.javafx.projetjavafx.daoImpl.ArmeDaoImpl;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControleurListArmes implements Initializable {
    public VBox vbNom;
    public VBox vbDegats;

    @FXML
    public Button btAddArmes ,btRetour;

    @FXML
    public ListView lvNom, lvDegats;

    private final ArmeDaoImpl armeDaoImpl = new ArmeDaoImpl();

    private List<Armes> armesList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.mettreAJourListeArmes();
        this.initBtAddArmes();
        this.initBtRetour();
    }

    private void initBtAddArmes() {
        btAddArmes.setOnAction(e -> {
            try {
                openVueAddArmes();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void openVueAddArmes() throws IOException{
        this.goTo("/Vues/ajouter-arme.fxml");
    }

    private void initBtRetour() {
        btRetour.setOnAction(e -> {
            try {
                openVueAcceille();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void openVueAcceille() throws IOException{
        this.mettreAJourListeArmes();
        this.goTo("/Vues/acceuil.fxml");
    }
    private void goTo(String s) throws IOException {
        Stage stagePrincipal = (Stage) btAddArmes.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        Parent root = loader.load();
        stagePrincipal.setScene(new Scene(root));
        stagePrincipal.show();
    }

    private void mettreAJourListeArmes() {
        lvNom.getItems().clear();
        lvDegats.getItems().clear();
        this.listerArmes();
    }

    private void listerArmes() {
        armesList = armeDaoImpl.ListerArmes();

        for (Armes armes : armesList) {
            Label lbNameArme= new Label(armes.getNom());
            initNameAction(lbNameArme, armes);
            lvNom.getItems().add(lbNameArme);
            lvDegats.getItems().add(armes.getDegats());

        }
    }

    private void initNameAction(Label lbNameArmes, Armes armes) {
        lbNameArmes.setOnMouseClicked(e -> {
            try {
                openVueArmes(armes);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void openVueArmes(Armes armes) throws IOException {
        Stage stagePrincipal = (Stage) btAddArmes.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vues/fiche-arme.fxml"));
        Parent root = loader.load();
        ControleurArme controller = loader.getController();

        // Stocker les données dans le nœud racine de la vue source

        controller.lbId.setText("Armes : "+ armes.getId());
        controller.lbName.setText(armes.getNom());
        controller.lbDegats.setText(""+ armes.getDegats());
        controller.lbId.setText(""+armes.getId());

        stagePrincipal.setScene(new Scene(root));
        stagePrincipal.show();
    }

}
