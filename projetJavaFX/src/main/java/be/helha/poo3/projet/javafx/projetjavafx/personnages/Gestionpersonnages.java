package be.helha.poo3.projet.javafx.projetjavafx.personnages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

}
