package be.helha.poo3.projet.javafx.projetjavafx.dbmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe qui permet de gérer la connexion à la DB (implémentée sous forme d'un singleton)
 *
 * @author Diesbecq Aaron
 * @see be.helha.poo3.projet.javafx.projetjavafx.dbmanager.DBManager
 */
public class DBManager {
    /**
     * Instance de notre classe DBManager
     */
    private static final DBManager INSTANCE = new DBManager();
    /**
     * Chemin d'accès vers le fichier de config
     */
    private static final String CONFIG = "src/main/resources/configDB.json";
    /**
     * Instance de la classe Persistance
     */
    private Persistance persistance = null;
    /**
     * Instance de la classe Connection
     */
    private Connection connection = null;


    /**
     * Constructeur de la classe DBManager (en privé car singleton)
     */
    private DBManager(){
        try {
            this.persistance = ParserConfig.lireConfig(CONFIG);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Permet de récuper l'instance de la classe DBManager
     * @return Une instance de la classe DBManager
     */
    public static DBManager getInstance(){
        return INSTANCE;
    }

    /**
     * Permet de se connecter à la DB
     * @return Une instance de la classe Connection
     * @throws SQLException
     */
    public Connection getConnexion() throws SQLException {
        try {
            connection = DriverManager.getConnection(persistance.getDBPath());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
