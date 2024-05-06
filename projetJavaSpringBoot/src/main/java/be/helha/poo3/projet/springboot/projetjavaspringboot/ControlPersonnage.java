package be.helha.poo3.projet.springboot.projetjavaspringboot;

import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Controler des personnages : permet de lister les personnages ou voir le détail d'un personnage
 */
@RestController
@RequestMapping("/personnage")
public class ControlPersonnage {


    /**
     * Renvoie la liste des personnages
     * @return String message contenant la liste des personnages ou un message d'erreur
     */
    @GetMapping ("")
    public String listerPersonnage(){
        // connexion à la DB
        DBManager dbManager = new DBManager();
        Connection con = dbManager.connecter();

        if(con!=null){
            // Preparation de la requete sql
            String query = "SELECT * FROM PERSONNAGE";
            try{
                Statement statement = con.createStatement();
                ResultSet resultSet =statement.executeQuery(query);

                List<Personnage> personnages = new ArrayList<Personnage>();
                StringBuilder htmlResponse = new StringBuilder("<ul>");
                while (resultSet.next()){
                    // creer le personnage
                    Personnage perso = new Personnage(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getInt(4));
                    personnages.add(perso);
                    htmlResponse.append("<li>{ id : ").append(perso.getId()).append(", nom : ").append(perso.getNom()).append(" }</li>");
                }
                // liste vide
                if(personnages.isEmpty()){
                    return "<p> <i> Pas de personnages <i></p>";
                } else{
                    // liste des personnages
                    htmlResponse.append("</ul>");
                    return htmlResponse.toString();
                }
            } catch (SQLException sqlException){
                return "<p> Erreur de traitement sql</p>";
            }

        } else{
            return "<p> GestionRPG : erreur de connexion a la db </p>";
        }
    }

    /**
     * Renvoie la description détaillée d'un personnage selon un identifiant
     * @param id String identifiant du personnage
     * @return message contenant la description du personnage correspondant à l'id ou un message d'erreur
     * @throws SQLException
     */
    @GetMapping("{id}")
    public String getPersonnage(@PathVariable String id) throws SQLException {
        DBManager dbManager = new DBManager();
        Connection con = dbManager.connecter();

        if(con==null){
            return "<p> Erreur de connexion a la db </p>";
        }
        try {
            String query = "SELECT * FROM personnage WHERE id=" + "'" + id + "'";
            Statement statement = con.createStatement();
            ResultSet resultSet =statement.executeQuery(query);
            Personnage perso = null;
            while (resultSet.next()){
                perso = new Personnage(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getInt(4));
            }
            if(perso == null){
                return "<p> Pas de personnage à l'id : " +  id +  "</p>";
            } else{
                return  "<p>" + perso.toString() + "</p>";
            }
        } catch (SQLException sqlException){
            return "<p>" + sqlException.getMessage() + "</p>";
        }

    }
}
