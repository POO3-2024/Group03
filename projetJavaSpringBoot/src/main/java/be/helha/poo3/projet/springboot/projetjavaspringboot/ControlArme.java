package be.helha.poo3.projet.springboot.projetjavaspringboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Controler des armes : permet de lister les armes ou voir le détail d'une arme
 */
@RestController
@RequestMapping("/arme")
public class ControlArme {


    /**
     * Renvoie la liste des armes
     * @return String message contenant la liste des personnages ou un message d'erreur
     */
    @GetMapping("")
    public String listerArme() {
        // connexion à la DB
        DBManager dbManager = new DBManager();
        Connection con = dbManager.connecter();

        if (con != null) {
            // Preparation de la requete sql
            String query = "SELECT * FROM ARME";
            try {
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                List<Arme> armes = new ArrayList<Arme>();
                StringBuilder htmlResponse = new StringBuilder("<ul>");
                while (resultSet.next()) {
                    // creer l'arme
                    Arme arme = new Arme(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3));
                    armes.add(arme);
                    htmlResponse.append("<li>{ id : ").append(arme.getId()).append(", nom : ").append(arme.getNom()).append(" }</li>");
                }
                // liste vide
                if (armes.isEmpty()) {
                    return "<p> <i> Pas d'armes <i></p>";
                } else {
                    // liste des armes
                    htmlResponse.append("</ul>");
                    return htmlResponse.toString();
                }
            } catch (SQLException sqlException) {
                return "<p> Erreur de traitement sql</p>";
            }

        } else {
            return "<p> GestionRPG : erreur de connexion a la db </p>";
        }
    }
}
