package be.helha.poo3.projet.springboot.projetjavaspringboot;


import be.helha.lib.poo3.daoImpl.ArmeDaoImpl;
import be.helha.lib.poo3.domaine.Armes;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controler des armes : permet de lister les armes ou voir le détail d'une arme
 * @author Tabi Zaccaria
 * @see be.helha.poo3.projet.springboot.projetjavaspringboot.ControlArme
 */
@RestController
@RequestMapping("/arme")
public class ControlArme {

    private ArmeDaoImpl armeDao = new ArmeDaoImpl();

    /**
     * Renvoie la liste des armes (HTTP GET)
     * @return String message contenant la liste des personnages ou un message d'erreur
     */
    @GetMapping("")
    public List<Map<String,String>> listerArme() {

        try{
            // chercher la liste des armes
            List<Armes> listerArmes = armeDao.ListerArmes(); // peu lancer une exception

            // preparer la réponse
            List<Map<String,String>> reponseArmes = new ArrayList<Map<String,String>>();
            listerArmes.forEach(armes -> {
                Map<String,String> mapObj = new HashMap<String,String>();
                mapObj.put("ID", String.valueOf(armes.getId()));
                mapObj.put("Nom", armes.getNom());
                reponseArmes.add(mapObj);
            });

            return reponseArmes;

        } catch (Exception e){ // en cas d'erreur un message est envoyé
            List<Map<String,String>> reponse = new ArrayList<>();
            Map<String,String> mapReponse = new HashMap<>();
            mapReponse.put("message","erreur lors du chargement de la liste des armes");
            reponse.add(mapReponse);

            return reponse;

        }


    }

    /**
     * Renvoie la description détaillée d'une arme selon un identifiant (HTTP GET)
     * @param id string identifiant de l'arme
     * @return message contenant la description du personnage correspondant à l'id ou un message d'erreur
     * @throws SQLException
     */
    @GetMapping("{id}")
    public Map<String,String> getArme(@PathVariable String id) {

        // verifie si l'identifiant est valide
        boolean estEntier = id.matches("[0-9]+");
        if(!estEntier){
            Map<String,String> mapReponse = new HashMap<>();
            mapReponse.put("message","l'identifiant de l'arme n'est pas un entier");
            return mapReponse;
        }

        try{
            // recuperer l'arme
            Armes arme = armeDao.getArmeID(id); // peu lancer une exception

            // verifie si l'arme existe
            if(arme == null){
                Map<String,String> mapReponse = new HashMap<>();
                mapReponse.put("message","L'arme avec l'identifiant " + id + " n'existe pas");
                return mapReponse;
            }

            // preparer la réponse
            Map<String,String> mapObj = new HashMap<String,String>();
            mapObj.put("ID", String.valueOf(arme.getId()));
            mapObj.put("Nom", arme.getNom());
            mapObj.put("Degat",String.valueOf(arme.getDegats()));

            return mapObj;

        } catch (Exception e){ // en cas d'erreur un message est envoyé
            Map<String,String> mapReponse = new HashMap<>();
            mapReponse.put("message","erreur lors du chargement de l'arme");
            return mapReponse;

        }


    }

}
