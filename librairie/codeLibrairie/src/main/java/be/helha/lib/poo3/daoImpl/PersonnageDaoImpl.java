package be.helha.lib.poo3.daoImpl;


import be.helha.lib.poo3.dao.PersonnageDao;
import be.helha.lib.poo3.domaine.Personnage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface {@link PersonnageDao} pour gérer les opérations CRUD sur les objets Personnage.
 * Cette classe utilise des requêtes SQL pour interagir avec une base de données.
 * @author Mohamed Samba Demba
 */
public class PersonnageDaoImpl implements PersonnageDao {
    /**
     * Requêtes SQL utilisées:
     * {@code GET}: Sélectionne un personnage par son nom.
     */
    private static final String GET = "SELECT * FROM personnage b WHERE b.Nom = ?";
    /**
     * {@code AJOUT}: Insère un nouveau personnage.
     */
    private static final String AJOUT = "INSERT INTO personnage (ID, Nom, PV, Manna) VALUES (?,?,?,?)";
    /**
     * {@code MAJ}: Met à jour un personnage existant.
     */
    private static final String MAJ = "UPDATE personnage SET Manna= ?, PV= ?, Nom= ? where ID= ?";
    /**
     * {@code LISTER}: Liste tous les personnages par ordre d'identifiant.
     */
    private static final String LISTER = "SELECT * FROM personnage b ORDER BY b.ID";
    /**
     * {@code SUPPRIMER}: Supprime un personnage avec son identifiant.
     */
    private static final String SUPPRIMER = "DELETE FROM personnage WHERE ID = ?";
    /**
     * Requêtes SQL utilisées:
     * {@code GET}: Sélectionne un personnage par son id.
     */
    private static final String GET_ID = "SELECT * FROM personnage b WHERE b.ID = ?";


    /**
     * Ferme les ressources de base de données telles que {@code ResultSet}, {@code PreparedStatement} et {@code Connection}.
     * Cette méthode tente de fermer chaque ressource, capturant et ignorant toute exception qui pourrait survenir.*
     * @param rs le {@code  ResultSet} à fermer, peut être {@code null}.
     * @param stmt le {@code PreparedStatement} à fermer, peut être {@code null}.
     * @param con la {@code Connection} à fermer, peut être {@code null}.
     */
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
        if (getPersonnage(perso.getName())!= null) return false;
        try {
            con = DBManager.getInstance().getConnexion();
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
     * @param nom nom du personnage à récupérer.
     * @return {@code perso}Le personnage correspondant au nom spécifié, ou {@code perso = null} s'il n'existe pas.
     */
    public Personnage getPersonnage(String nom) {
        Personnage perso = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getInstance().getConnexion();
            stmt = con.prepareStatement(GET);
            stmt.setString(1, nom.trim());
            rs = stmt.executeQuery();
            if (rs.next()) {
                perso = new Personnage(rs.getString("Nom"));
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
     * Récupère un personnage à partir de son id.
     *
     * @param id id du personnage à récupérer.
     * @return {@code perso}Le personnage correspondant à l'id spécifié, ou {@code perso = null} s'il n'existe pas.
     */
    public Personnage getPersonnageID(String id) {
        Personnage perso = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getInstance().getConnexion();
            stmt = con.prepareStatement(GET_ID);
            stmt.setString(1, id.trim());
            rs = stmt.executeQuery();
            if (rs.next()) {
                perso = new Personnage(rs.getString("Nom"));
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
     * @return {@code List<Personnage>} Une liste contenant tous les personnages présents dans la base de données ou {@code null}.
     */
    public List<Personnage> listerPersonnages() {
        List<Personnage> liste = new ArrayList<Personnage>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getInstance().getConnexion();
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

    /**
     * Modifie un personnage dans la base de données.
     *
     * @param perso Le personnage à modifier avec ses nouveaux attributs.
     * @return {@code true} si la modification du personnage a réussi, sinon {@code false} .
     */
    public boolean modifierPersonnage(Personnage perso) {
        boolean modificationReussie = false;
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DBManager.getInstance().getConnexion();
            stmt = con.prepareStatement(MAJ);
            stmt.setInt(4, perso.getId());
            stmt.setInt(1, perso.getManna());
            stmt.setInt(2, perso.getPointDeVie());
            stmt.setString(3, perso.getName());
            int resultat = stmt.executeUpdate();
            if (resultat == 1) {
                modificationReussie = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cloturer(null, stmt, con);
        }
        return modificationReussie;
    }

    /**
     * Supprime un personnage de la base de données en utilisant son identifiant.
     *
     * @param id L'identifiant du personnage à supprimer.
     * @return {@code true} si la suppression du personnage a réussi, {@code false} sinon.
     */
    public boolean supprimerPersonnage(int id) {
        boolean suppressionReussie = false;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getInstance().getConnexion();
            stmt = con.prepareStatement(SUPPRIMER);
            stmt.setInt(1, id);
            int nb = stmt.executeUpdate();
            if (nb == 1)
                suppressionReussie = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            cloturer(rs, stmt, con);
        }
        return suppressionReussie;
    }


}
