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
 * Controler contient les méthodes d'attaques
 */
@RestController
@RequestMapping("/attaque")
public class ControlAttaque {

    /**
     * Permet d'attaquer une personnage avec une arme
     * @param idPerso String identifiant du personnage attaqué
     * @param idArme String identifiant de l'arme utilisée lors de l'attaque
     * @return String message contenant les détails du personnage attaqué ou un message d'erreur
     * @throws SQLException
     */
    @GetMapping("{idPerso}/{idArme}")
    public String attaquer(@PathVariable String idPerso, @PathVariable String idArme) throws SQLException {
        // connexion
        DBManager dbManager = new DBManager();
        Connection con = dbManager.connecter();

        // verifier si idPerso et idArme sont des entiers
        boolean idPersoEstEntier = idPerso.matches("[0-9]+");
        boolean idArmeEstEntier = idArme.matches("[0-9]+");

        if(!idPersoEstEntier || !idArmeEstEntier){
            return "<p> Au moins un des identifiants n'est pas un entier </p>";
        }

        // verifier connexion
        if (con == null) {
            return "<p> Erreur de connexion a la db </p>";
        }

        try {
            // recuperer arme
            String query = "SELECT * FROM ARME WHERE id=" + "'" + idArme + "'";
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            Arme arme = null;
            while (resultSet.next()) {
                arme = new Arme(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3));

            }

            // recuperer perso
            query = "SELECT * FROM PERSONNAGE WHERE id=" + "'" + idPerso + "'";
            statement = con.createStatement();
            resultSet = statement.executeQuery(query);
            Personnage perso = null;
            while (resultSet.next()) {
                perso = new Personnage(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3),resultSet.getInt(4));

            }

            if (perso == null) {
                return "<p> Pas de personnage à l'id : " + idPerso + "</p>";
            }
            if (arme == null) {
                return "<p> Pas d'arme à l'id : " + idArme + "</p>";
            } else {
                StringBuilder htmlResponse = new StringBuilder();
                htmlResponse.append("<p>{ perso avant l'attaque : ").append(perso.toString()).append(" }</p><br>");
                htmlResponse.append("<p>{ Avant arme utilisé : ").append(arme.toString()).append(" }</p><br>");

                perso.setPv(perso.getPv()-arme.getDegat());
                if(perso.getPv()<0) perso.setPv(0);


                // Modifier dans la db
                query = "UPDATE PERSONNAGE SET pv=" + perso.getPv() + " WHERE id=" + "'" + idPerso + "'";
                statement = con.createStatement();
                statement.executeUpdate(query);


                htmlResponse.append("<p>{ détail du perso attaqué : ").append(perso.toString()).append(" }</p><br>");
                return htmlResponse.toString();
            }
        } catch (SQLException sqlException) {
            return "<p>" + sqlException.getMessage() + "</p>";
        }

    }
}
