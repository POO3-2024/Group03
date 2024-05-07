package be.helha.poo3.projet.javafx.projetjavafx.armes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GestionArmes {
    private static final String GET = "SELECT * FROM arme b WHERE b.Nom = ?";
    private static final String AJOUT = "INSERT INTO arme (ID, Nom, Degats) VALUES (?,?,?)";
    private static final String MAJ = "UPDATE arme SET Nom= ?, Degats= ?, where ID= ?";
    private static final String LISTER = "SELECT * FROM arme b ORDER BY b.ID";
    private static final String SUPPRIMER = "DELETE FROM arme b WHERE b.ID = ?";

    public GestionArmes() {
    }

    public boolean ajouterArme(Armes armes){
        boolean ajoutReussi = false;
        Connection con = null;
        PreparedStatement stmt = null;
        try{
            con = Factory.getInstance().getConnexion();
            stmt = con.prepareStatement(AJOUT);
            stmt.setInt(1,armes.getId());
            stmt.setString(2,armes.getNom());
            stmt.setInt(3,armes.getDegats());

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
            con = Factory.getInstance().getConnexion();
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

}