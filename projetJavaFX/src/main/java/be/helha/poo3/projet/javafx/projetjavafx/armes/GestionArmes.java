package be.helha.poo3.projet.javafx.projetjavafx.armes;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import be.helha.poo3.projet.javafx.projetjavafx.dbmanager.DBManager;


public class GestionArmes {
    private static final String GET = "SELECT * FROM arme b WHERE b.Nom = ?";
    private static final String AJOUT = "INSERT INTO arme (Nom, Degats) VALUES (?,?)";
    private static final String MAJ = "UPDATE arme SET Nom= ?, Degats= ?, where ID= ?";
    private static final String LISTER = "SELECT * FROM arme b ORDER BY b.ID";
    private static final String SUPPRIMER = "DELETE FROM arme b WHERE b.ID = ?";

    public GestionArmes() {
    }

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
                armes = new Armes(rs.getString(2));
                armes.setId(rs.getInt(1));
                armes.setDegats(rs.getInt(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            cloturer(rs,stmt,con);
        }
        return armes;
    }

    public boolean supprimerArmes(int id){
        boolean suppressionReussie = false;
        Connection con = null;
        PreparedStatement stmt = null;
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
            cloturer(null,stmt,con);
        }
        return  suppressionReussie;
    }

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