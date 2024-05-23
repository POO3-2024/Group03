package be.helha.poo3.projet.javafx.projetjavafx.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import be.helha.poo3.projet.javafx.projetjavafx.dao.ArmeDao;
import be.helha.poo3.projet.javafx.projetjavafx.domaine.Armes;


/**
 * Implémentation de l'interface ArmeDao pour la gestion des armes dans la base de données.
 *
 * @author Alahyane Abdel
 */
public class ArmeDaoImpl implements ArmeDao {
    private static final String GET = "SELECT * FROM arme b WHERE b.Nom = ?";
    private static final String AJOUT = "INSERT INTO arme (Nom,Degats,ID) VALUES (?,?,?)";
    private static final String MAJ = "UPDATE arme SET Nom= ?, Degats= ? where ID= ?";
    private static final String LISTER = "SELECT * FROM arme b ORDER BY b.ID";
    private static final String SUPPRIMER = "DELETE FROM arme WHERE ID = ?";

    /**
     * Constructeur par défaut de la classe ArmeDaoImpl.
     */
    public ArmeDaoImpl() {
    }

    /**
     * Ajoute une nouvelle arme dans la base de données.
     *
     * @param armes l'arme à ajouter
     * @return true si l'ajout a réussi, false sinon
     */
    public boolean ajouterArme(Armes armes){

        if(this.getArme(armes.getNom())!=null){
            return false;
        }


        boolean ajoutReussi = false;
        Connection con = null;
        PreparedStatement stmt = null;
        try{
            con = DBManager.getInstance().getConnexion();
            stmt = con.prepareStatement(AJOUT);
            stmt.setString(1,armes.getNom());
            stmt.setInt(2,armes.getDegats());
            stmt.setInt(3,armes.getId());

            int resultat = stmt.executeUpdate();
            if(resultat==1) {
                ajoutReussi = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            cloturer(null,stmt,con);
        }
        return ajoutReussi;
    }

    /**
     * Ferme les ressources de la base de données.
     *
     * @param rs   le ResultSet à fermer
     * @param stmt le PreparedStatement à fermer
     * @param con  la connexion à fermer
     */
    private void cloturer(ResultSet rs, PreparedStatement stmt,Connection con){
        try{
            if(rs != null)
                rs.close();
        }catch (Exception e){
        }
        try {
            if(stmt != null)
                stmt.close();
        }catch (Exception e){
        }
        try {
            if(con != null)
                con.close();
        }catch (Exception e){

        }
    }

    /**
     * Récupère une arme à partir de son nom.
     *
     * @param nom le nom de l'arme à récupérer
     * @return l'arme correspondante, ou null si aucune arme n'est trouvée
     */
    public Armes getArme(String nom){
        Armes armes = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getInstance().getConnexion();
            stmt = con.prepareStatement(GET);
            stmt.setString(1,nom);
            rs = stmt.executeQuery();
            if(rs.next()){
                armes = new Armes(rs.getString("Nom"));
                armes.setId(rs.getInt("ID"));
                armes.setDegats(rs.getInt("Degats"));
            }else{
                System.out.println("Aucune arme trouvé avec le nom " + nom);
            }
        } catch (Exception e) {
            System.out.println("Erreur pendant la récupération de l'arme" + e.getMessage());
            e.printStackTrace();
        }finally {
            cloturer(rs,stmt,con);
        }
        return armes;
    }

    /**
     * Supprime une arme de la base de données à partir de son ID.
     *
     * @param id l'ID de l'arme à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean supprimerArmes(int id){
        boolean suppressionReussie = false;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try{
            con = DBManager.getInstance().getConnexion();
            stmt = con.prepareStatement(SUPPRIMER);
            stmt.setInt(1,id);
            int nb = stmt.executeUpdate();
            if (nb==1)
                suppressionReussie = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            cloturer(rs,stmt,con);
        }
        return  suppressionReussie;
    }

    /**
     * Modifie les informations d'une arme dans la base de données.
     *
     * @param armes l'arme à modifier
     * @return true si la modification a réussi, false sinon
     */
    public boolean modifierArmes(Armes armes){
        boolean modificationReussie = false;
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = DBManager.getInstance().getConnexion();
            stmt = con.prepareStatement(MAJ);
            stmt.setString(1, armes.getNom());
            stmt.setInt(2,armes.getDegats());
            stmt.setInt(3,armes.getId());
            int result = stmt.executeUpdate();
            if (result==1){
                modificationReussie = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cloturer(null,stmt,con);
        }
        return modificationReussie;
    }


    /**
     * Liste toutes les armes de la base de données.
     *
     * @return une liste d'armes
     */
    public List<Armes> ListerArmes(){
        List<Armes> liste = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = DBManager.getInstance().getConnexion();
            stmt = con.prepareStatement(LISTER);
            rs = stmt.executeQuery();
            while (rs.next()){
                Armes armes = new Armes(rs.getString(2));
                armes.setId(rs.getInt(1));
                armes.setDegats(rs.getInt(3));
                liste.add(armes);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cloturer(rs,stmt,con);
        }
        return liste;
    }

}