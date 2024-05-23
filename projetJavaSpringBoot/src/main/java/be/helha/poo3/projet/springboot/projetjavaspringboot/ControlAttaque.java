package be.helha.poo3.projet.springboot.projetjavaspringboot;


import be.helha.lib.poo3.daoImpl.ArmeDaoImpl;
import be.helha.lib.poo3.daoImpl.PersonnageDaoImpl;
import be.helha.lib.poo3.domaine.Armes;
import be.helha.lib.poo3.domaine.Personnage;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Controler contient les méthodes d'attaques
 * @author Tabi Zaccaria
 * @see be.helha.poo3.projet.springboot.projetjavaspringboot.ControlAttaque
 */
@RestController
@RequestMapping("/attaque")
public class ControlAttaque {

    private ArmeDaoImpl armeDao = new ArmeDaoImpl();
    private PersonnageDaoImpl personnageDao = new PersonnageDaoImpl();

    /**
     * Permet d'attaquer une personnage avec une arme (HTTP PUT)
     * @param idPerso String identifiant du personnage attaqué
     * @param idArme String identifiant de l'arme utilisée lors de l'attaque
     * @return Map<String,String> message contenant les détails du personnage attaqué ou un message d'erreur
     */
    @PutMapping ("{idPerso}/{idArme}")
    public Map<String,String> attaquer(@PathVariable String idPerso, @PathVariable String idArme) {

        // verifier si idPerso et idArme sont des entiers
        boolean idPersoEstEntier = idPerso.matches("[0-9]+");
        boolean idArmeEstEntier = idArme.matches("[0-9]+");

        if(!idPersoEstEntier || !idArmeEstEntier){
            Map<String,String> mapReponse = new HashMap<>();
            mapReponse.put("message","Au moins un des identifiants n'est pas un entier");
            return mapReponse;
        }

        try{
            // recuperer arme
            Armes arme = armeDao.getArmeID(idArme); // peu lancer une exception

            // recuperer perso
            Personnage perso = personnageDao.getPersonnageID(idPerso); // peu lancer une exception

            // verifie si perso et arme existe
            if (perso == null) {
                Map<String,String> mapReponse = new HashMap<>();
                mapReponse.put("message","Le personnage avec l'identifiant " + idPerso + " n'existe pas");
                return mapReponse;
            }
            if (arme == null) {
                Map<String,String> mapReponse = new HashMap<>();
                mapReponse.put("message","L'arme avec l'identifiant " + idArme + " n'existe pas");
                return mapReponse;
            }

            // attaque
            perso.setPointDeVie(perso.getPointDeVie()-arme.getDegats());
            if(perso.getPointDeVie() <= 0) perso.setPointDeVie(0);

            // modifier personnage
            personnageDao.modifierPersonnage(perso);

            // preparer la reponse
            Map<String,String> mapObj = new HashMap<String,String>();
            mapObj.put("ID", String.valueOf(perso.getId()));
            mapObj.put("Nom", perso.getName());
            mapObj.put("Point de vie",String.valueOf(perso.getPointDeVie()));
            mapObj.put("Manna",String.valueOf(perso.getManna()));

            return mapObj;

        } catch (Exception e){
            Map<String,String> mapReponse = new HashMap<>();
            mapReponse.put("message","erreur lors du chargement de l'attaque");
            return mapReponse;
        }
    }
}
