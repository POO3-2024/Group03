package be.helha.poo3.projet.springboot.projetjavaspringboot;

import org.springframework.web.bind.annotation.*;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/personnage")
public class controlPersonnage {


    @GetMapping ("")
    public String listerPersonnage(){
        // connexion à la DB
        Connection con = null;
        /*
        DBManager dbManager = new DBManager();
        Connection con = dbManager.connecter();
         */

        if(con!=null){
            // Preparation de la requete sql
            String query = "SELECT * FROM PERSONNAGE";
            try{
                Statement statement = con.createStatement();
                ResultSet resultSet =statement.executeQuery(query);

                List<String> personnages = new ArrayList<String>();
                StringBuilder htmlResponse = new StringBuilder("<ul>");
                while (resultSet.next()){
                    //System.out.println(resultSet.toString());
                    //eleve = new Eleve(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getInt(5));
                    String perso = resultSet.getString(2);
                    personnages.add(perso);
                    htmlResponse.append("<li>").append(perso).append("</li>");
                }
                // liste vide
                if(personnages.isEmpty()){
                    return "<h2> <i> Erreur dans la requete <i></h2>";
                } else{
                    // liste des personnages
                    htmlResponse.append("</ul>");
                    return htmlResponse.toString();
                }
            } catch (SQLException sqlException){
                return "Erreur de traitement";
            }

        } else{
            return "<h2> <i> GestionRPG : erreur de connexion a la db <i></h2>";
        }
    }
}
