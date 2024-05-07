/**
 * @author Mohamed Samba Demba
 * */
package be.helha.poo3.projet.javafx.projetjavafx.test.dbmanager;
import be.helha.poo3.projet.javafx.projetjavafx.personnages.Gestionpersonnages;
import be.helha.poo3.projet.javafx.projetjavafx.personnages.Personnage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestCrudPersonnage {

    Gestionpersonnages gestionpersonnages = new Gestionpersonnages();
    private List<Personnage> personnages = new ArrayList<>();
    /**
     * Teste la méthode d'ajout de personnage.
     *
     * @throws Throwable Si une exception est levée pendant le test.
     */
    @Test
    public void testAjoutPersonnage() throws Throwable {
        // Création de plusieurs personnages
        Personnage alice = new Personnage("Alice");
        Personnage bob = new Personnage("Bob");
        Personnage moria = new Personnage("Moria");
        Personnage diane = new Personnage("Diane");
        Personnage charle = new Personnage("Charle");

        // Ajout des personnages à la liste personnages
        personnages.add(alice);
        personnages.add(bob);
        personnages.add(moria);
        personnages.add(diane);
        personnages.add(charle);

        // Ajout des personnages à la base de donnée
        boolean ajoutResulta = false;
        for (Personnage perso : personnages) {
            ajoutResulta = gestionpersonnages.ajouterPerso(perso);
            assertTrue(ajoutResulta);
        }
        //essaye d'ajoute un perso deja existant
        ajoutResulta = gestionpersonnages.ajouterPerso(alice);
        assertFalse(ajoutResulta);
    }
}
