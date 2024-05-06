package be.helha.poo3.projet.javafx.projetjavafx.personnages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Gestionpersonnages {
    private static final String GET = "SELECT * FROM personnage b WHERE b.Nom = ?";
    private static final String AJOUT = "INSERT INTO personnage (ID, Nom, PV, Manna) VALUES (?,?,?,?)";
    private static final String MAJ = "UPDATE personnage SET Manna= ?, PV= ?, name= ? where ID= ?";
    private static final String LISTER = "SELECT * FROM personnage b ORDER BY b.ID";
    private static final String SUPPRIMER = "DELETE FROM personnage b WHERE b.ID = ?";

    public Gestionpersonnages() {}

    private void cloturer(ResultSet rs, PreparedStatement stmt, Connection con) {
        try {
            if (rs != null)
                rs.close();
        } catch (Exception ex) {
        }
        try {
            if (stmt != null)
                stmt.close();
        } catch (Exception ex) {
        }
        try {
            if (con != null)
                con.close();
        } catch (Exception ex) {
        }
    }

    /**
     * Méthode pour ajouter un personnage à la base de données.
     *
     * @param perso Le personnage à ajouter.
     * @return {@code true} si l'ajout du personnage a réussi, {@code false} sinon.
     */
    public boolean ajouterPerso(Personnage perso) {
        boolean ajoutReussi = false;
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DaoFactory.getInstance().getConnexion();
            stmt = con.prepareStatement(AJOUT);
            stmt.setInt(1, perso.getId());
            stmt.setString(2, perso.getName().trim());
            stmt.setInt(3, perso.getPointDeVie());
            stmt.setInt(4, perso.getManna());
            int resultat = stmt.executeUpdate();
            if (resultat == 1) {
                ajoutReussi = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cloturer(null, stmt, con);
        }
        return ajoutReussi;
    }

    /**
     * Récupère un personnage à partir de son nom.
     *
     * @param nom Le nom du personnage à récupérer.
     * @return {@code perso}Le personnage correspondant au nom spécifié, ou {@code perso = null} s'il n'existe pas.
     */

    public Personnage getPersonnage(String nom) {
        Personnage perso = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = DaoFactory.getInstance().getConnexion();
            stmt = con.prepareStatement(GET);
            stmt.setString(1, nom.trim());
            rs = stmt.executeQuery();
            if (rs.next()) {
                perso = new Personnage(nom);
                perso.setManna(rs.getInt(4));
                perso.setPointDeVie(rs.getInt(3));
                perso.setId(rs.getInt(1));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cloturer(rs, stmt, con);
        }
        return perso;
    }

    /**
     * Récupère une liste de tous les personnages depuis la base de données.
     *
     * @return Une liste contenant tous les personnages présents dans la base de données.
     */

    public List<Personnage> listerPersonnages() {
        List<Personnage> liste = new ArrayList<Personnage>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = DaoFactory.getInstance().getConnexion();
            stmt = con.prepareStatement(LISTER);
            rs = stmt.executeQuery();
            String nom = "";
            while (rs.next()) {
                nom = rs.getString(2);
               Personnage perso = new Personnage(nom);
                perso.setManna(rs.getInt(4));
                perso.setPointDeVie(rs.getInt(3));
                perso.setId(rs.getInt(1));
                liste.add(perso);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cloturer(rs, stmt, con);
        }
        return liste;
    }


}
