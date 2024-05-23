package be.helha.poo3.projet.springboot.projetjavaspringboot;


import be.helha.lib.poo3.daoImpl.PersonnageDaoImpl;
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
 * Controler des personnages : permet de lister les personnages ou voir le détail d'un personnage
 * @author Tabi Zaccaria
 * @see be.helha.poo3.projet.springboot.projetjavaspringboot.ControlPersonnage
 */
@RestController
@RequestMapping("/personnage")
public class ControlPersonnage {

    private PersonnageDaoImpl personnageDao = new PersonnageDaoImpl();
    /**
     * Renvoie la liste des personnages (HTTP GET)
     * @return String message contenant la liste des personnages ou un message d'erreur
     */
    @GetMapping ("")
    public List<Map<String,String>> listerPersonnage(){

        try{
            // chercher la liste des personnages
            List<Personnage> listePersonnage = personnageDao.listerPersonnages() ; // peu lancer une exception

            // preparer la réponse
            List<Map<String,String>> reponsePersos = new ArrayList<Map<String,String>>();
            listePersonnage.forEach(perso -> {
                Map<String,String> mapObj = new HashMap<String,String>();
                mapObj.put("ID", String.valueOf(perso.getId()));
                mapObj.put("Nom", perso.getName());
                reponsePersos.add(mapObj);
            });

            return reponsePersos;

        } catch (Exception e){ // en cas d'erreur un message est envoyé
            List<Map<String,String>> reponse = new ArrayList<>();
            Map<String,String> mapReponse = new HashMap<>();
            mapReponse.put("message","erreur lors du chargement de la liste des personnages");
            reponse.add(mapReponse);

            return reponse;

        }


    }

    /**
     * Renvoie la description détaillée d'un personnage selon un identifiant (HTTP GET)
     * @param id String identifiant du personnage
     * @return message contenant la description du personnage correspondant à l'id ou un message d'erreur
     */
    @GetMapping("{id}")
    public Map<String,String> getPersonnage(@PathVariable String id) {

        // verifie si l'identifiant est valide
        boolean estEntier = id.matches("[0-9]+");
        if(!estEntier){
            Map<String,String> mapReponse = new HashMap<>();
            mapReponse.put("message","l'identifiant du personnage n'est pas un entier");
            return mapReponse;
        }

        try{
            // recuperer le personnage
            Personnage perso = personnageDao.getPersonnageID(id); // peu lancer une exception

            // verifie si l'arme existe
            if(perso == null){
                Map<String,String> mapReponse = new HashMap<>();
                mapReponse.put("message","Le personnage avec l'identifiant " + id + " n'existe pas");
                return mapReponse;
            }

            // preparer la réponse
            Map<String,String> mapObj = new HashMap<String,String>();
            mapObj.put("ID", String.valueOf(perso.getId()));
            mapObj.put("Nom", perso.getName());
            mapObj.put("Point de vie",String.valueOf(perso.getPointDeVie()));
            mapObj.put("Manna",String.valueOf(perso.getManna()));

            return mapObj;

        } catch (Exception e){ // en cas d'erreur un message est envoyé
            Map<String,String> mapReponse = new HashMap<>();
            mapReponse.put("message","erreur lors du chargement du personnage");
            return mapReponse;

        }


    }
}
