package be.helha.poo3.projet.javafx.projetjavafx.controleurs;

import be.helha.poo3.projet.javafx.projetjavafx.daoImpl.ArmeDaoImpl;
import be.helha.poo3.projet.javafx.projetjavafx.daoImpl.PersonnageDaoImpl;
import be.helha.poo3.projet.javafx.projetjavafx.domaine.Armes;
import be.helha.poo3.projet.javafx.projetjavafx.domaine.Personnage;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class controleurAttaquer implements Initializable {
    /**
     * Initialisation des choiceBox
     */
    @FXML
    public ChoiceBox cbPersonnage ,cbArme;
    /**
     * Boutons de l'interface utilisateur.
     */
    @FXML
    public Button btRetour, btAttaquer;
    /**
     *
     */
    @FXML
    public Label lbMessageErreur;

    /**
     * Gestionnaire des personnages et des armes de l'application.
     */
    private final PersonnageDaoImpl personnageDaoImpl = new PersonnageDaoImpl();
    private final ArmeDaoImpl armeDaoImpl = new ArmeDaoImpl();
    /**
     * Liste des personnages et des armes des  de l'application.
     */
    private  List<Armes> armes;
    private List<Personnage> personnages;

    /**
     * Initialise le contrôleur.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initBtRetour();
        initListePersannages();
        initListeArmes();
        initBtAttaquer();
    }

    /**
     * Initialise le gestionnaire d'événements pour le bouton de Attaquer.
     */
    private void initBtAttaquer() {
        btAttaquer.setOnAction(e -> {
                attaquerPersonnage();
        });
    }
    /**
    *
     */
    private void attaquerPersonnage(){
       Personnage persoAttaquer = getPersonnageAAtaquer();
       Armes arme = getArme();
       if(persoAttaquer != null && arme!=null){
           int oldPv = persoAttaquer.getPointDeVie();
           if (oldPv == 0){
               lbMessageErreur.setText("les pv du personnage sont à  0");
               return;
           }else {
               int newPointdeVie = oldPv - arme.getDegats();
               persoAttaquer.setPointDeVie(newPointdeVie);
               if (personnageDaoImpl.modifierPersonnage(persoAttaquer)){
                   lbMessageErreur.setText("Les points de vie de "+ persoAttaquer.getName() +" sont passé de " + oldPv+ " à "+ persoAttaquer.getPointDeVie()+ "\n"+ arme.getNom()+" a fait "+ arme.getDegats() +" de dégats");
               }
           }
       }else{
           lbMessageErreur.setText(" Veuillez choisir une arme et un personnage");
       }

    }
    private Personnage getPersonnageAAtaquer(){
        for (Personnage personnage : personnages) {
            if (personnage.getName().equals(cbPersonnage.getValue())){
                return personnage;
            }
        }
        return null;
    }
    private Armes getArme(){
        for (Armes arme : armes) {
            if (arme.getNom().equals(cbArme.getValue())){
                return arme;
            }
        }
        return null;
    }

    /**
     * Initialise la liste des personnages.
     */
    private void initListePersannages(){
        personnages = personnageDaoImpl.listerPersonnages();
        cbPersonnage.setValue("Choisir Personnage");
        for (Personnage personnage : personnages) {
            cbPersonnage.getItems().add(personnage.getName());
        }
    }

    /**
     * Initialise la liste des armes.
     */
    private void initListeArmes(){
        armes = armeDaoImpl.ListerArmes();
        cbArme.setValue("Choisir Arme");
        for (Armes arme : armes) {
            cbArme.getItems().add(arme.getNom());
        }
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
     * Ouvre la vue d'accueil.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit lors de l'ouverture de la vue.
     */
    private void openVueAcceille() throws IOException {
        this.goTo("/Vues/acceuil.fxml");
    }

    /**
     * Charge et affiche une nouvelle vue.
     * @param path le chemin de la vue à charger.
     * @throws IOException si une erreur d'entrée/sortie se produit lors de la lecture de la vue.
     */
    private void goTo(String path) throws IOException {
        Stage stagePrincipal = (Stage) btRetour.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        stagePrincipal.setScene(new Scene(root));
        stagePrincipal.show();
    }
}
