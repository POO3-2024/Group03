package be.helha.poo3.projet.javafx.projetjavafx.test.dbmanager;
import be.helha.poo3.projet.javafx.projetjavafx.personnages.Gestionpersonnages;
import be.helha.poo3.projet.javafx.projetjavafx.personnages.Personnage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Order;
import org.junit.platform.commons.annotation.Testable;

/**
 *
 *@author Mohamed Samba Demba
 *
 */

public class TestCrudPersonnage {

    Gestionpersonnages gestionpersonnages = new Gestionpersonnages();
    private static final List<Personnage> personnages = new ArrayList<>();

    /**
     * Teste la méthode d'ajout de personnage.
     *
     * @throws Throwable Si une exception est levée pendant le test.
     */
    @Test
    @Order(1)
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
        boolean ajoutResultat = false;
        for (Personnage perso : personnages) {
            ajoutResultat = gestionpersonnages.ajouterPerso(perso);
            assertTrue(ajoutResultat);
        }
        //essaye d'ajoute un perso deja existant
        ajoutResultat = gestionpersonnages.ajouterPerso(alice);
        assertFalse(ajoutResultat);
    }

    /**
     * Teste de la méthode listerPersonnages() de la classe Gestionpersonnages.
     * Cette méthode compare les résultats obtenus avec la liste de personnages déjà
     * présente et alimentée par le test précédent testAjouterPersonnage.
     *
     * @throws Throwable si une exception est levée pendant le test.
     */

    @Test
    @Order(2)
    public void testListPersonnages() throws Throwable {
        // Récupération des personnages depuis la base de données
        List<Personnage> personnagesResultat = gestionpersonnages.listerPersonnages();

        // Vérification que les deux listes ont la même taille
        assertEquals(personnages.size(), personnagesResultat.size());

        // Parcours des deux listes en même temps pour comparer les personnages
        for (int i = 0; i < personnages.size(); i++) {
            // Récupération des personnages des deux listes
            Personnage personnageAttendu = personnages.get(i);
            Personnage personnageResultat = personnagesResultat.get(i);
            // Comparaison des  personnages
            assertEquals(personnageResultat.getName(),personnageAttendu.getName());
            assertEquals(personnageResultat.getId(),personnageAttendu.getId());
            assertEquals(personnageResultat.getManna(),personnageAttendu.getManna());
            assertEquals(personnageResultat.getPointDeVie(),personnageAttendu.getPointDeVie());
        }
    }

    /**
     * Teste la méthode recherchePersonnages() de la classe Gestionpersonnages.
     * Cette méthode recherche une personnee de la base de données et le compare avec les personnages déjà présents dans la liste personnages.
     *
     * @throws Throwable si une exception est levée pendant le test.
     */

    @Test
    @Order(3)
    public void testRecherchePersonnages() throws Throwable {
        // On parcourt la liste des personnages et on les recherche une par une
        for (Personnage personnage : personnages) {
            Personnage personnageResultat = gestionpersonnages.getPersonnage(personnage.getName());
            //Test du resulta
            assertEquals(personnageResultat.getName(),personnage.getName());
            assertEquals(personnageResultat.getId(),personnage.getId());
            assertEquals(personnageResultat.getManna(),personnage.getManna());
            assertEquals(personnageResultat.getPointDeVie(),personnage.getPointDeVie());
        }
        // Test de récupération d'une personne non existante
        assertNull(gestionpersonnages.getPersonnage("jhon"));
    }

}