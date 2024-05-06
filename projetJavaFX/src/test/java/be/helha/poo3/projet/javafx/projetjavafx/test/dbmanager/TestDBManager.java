package be.helha.poo3.projet.javafx.projetjavafx.test.dbmanager;

import be.helha.poo3.projet.javafx.projetjavafx.dbmanager.ParserConfig;
import be.helha.poo3.projet.javafx.projetjavafx.dbmanager.Persistance;
import org.junit.jupiter.api.Test;

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
}
