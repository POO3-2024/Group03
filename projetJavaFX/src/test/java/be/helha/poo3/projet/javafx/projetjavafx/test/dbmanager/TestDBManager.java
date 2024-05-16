package be.helha.poo3.projet.javafx.projetjavafx.test.dbmanager;

import be.helha.poo3.projet.javafx.projetjavafx.daoImpl.DBManager;
import be.helha.poo3.projet.javafx.projetjavafx.daoImpl.ParserConfig;
import be.helha.poo3.projet.javafx.projetjavafx.daoImpl.Persistance;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test de DBManager
 *
 * @author Diesbecq Aaron
 * @see be.helha.poo3.projet.javafx.projetjavafx.test.dbmanager.TestDBManager
 */
public class TestDBManager {

    /**
     * Attribut qui récupère le chemin du fichier de config
     */
    private static final String CONFIG = "src/main/resources/configDB.json";

    /**
     * Test de la méthode lireConfig pour voir si la lecture du fichier de config se réalise correctement
     * @throws Throwable
     */
    @Test
    public void testLireConfig() throws Throwable {
        Persistance persistance = ParserConfig.lireConfig(CONFIG);
        assert persistance != null;
        assertEquals(persistance.getConnectionType(), "DB");
        assertEquals(persistance.getDBPath(), "jdbc:sqlite:C:/sqlite/db/poo3.db");
    }

    /**
     * Test de la méthode connexion pour voir si on arrive bien à se connecter à la DB
     * @throws Throwable
     */
    @Test
    public void testConnexionDB() throws Throwable{
        DBManager dbManager = DBManager.getInstance();
        Connection connection = dbManager.getConnexion();
        assertNotNull(connection);
    }
}
