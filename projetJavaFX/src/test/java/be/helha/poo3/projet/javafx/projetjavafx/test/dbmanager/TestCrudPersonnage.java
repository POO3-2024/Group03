package be.helha.poo3.projet.javafx.projetjavafx.test.dbmanager;

import be.helha.lib.poo3.daoImpl.PersonnageDaoImpl;
import be.helha.lib.poo3.domaine.Personnage;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * La DB de test doit être vide pour que les tests fonctionnent.
 *@author Mohamed Samba Demba
 *
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCrudPersonnage {

    PersonnageDaoImpl personnageDaoImpl = new PersonnageDaoImpl();
    private static final List<Personnage> personnages = new ArrayList<>();

    @BeforeAll
    // sera exécuté une fois avant toutes les méthodes
    static void initialisation() throws SQLException {
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
    }
    /**
     * Teste la méthode d'ajout de personnage.
     *
     * @throws Throwable Si une exception est levée pendant le test.
     */
    @Test
    @Order(1)
    public void testAjoutPersonnage() throws Throwable {
        // Ajout des personnages à la base de donnée
        boolean ajoutResultat = false;
        for (Personnage perso : personnages) {
            ajoutResultat = personnageDaoImpl.ajouterPerso(perso);
            assertTrue(ajoutResultat);
        }
        //essaye d'ajoute un perso deja existant
        ajoutResultat = personnageDaoImpl.ajouterPerso(personnages.get(0));
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
        List<Personnage> personnagesResultat = personnageDaoImpl.listerPersonnages();
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
            Personnage personnageResultat = personnageDaoImpl.getPersonnage(personnage.getName());
            //Test du resulta
            assertEquals(personnageResultat.getName(),personnage.getName());
            assertEquals(personnageResultat.getId(),personnage.getId());
            assertEquals(personnageResultat.getManna(),personnage.getManna());
            assertEquals(personnageResultat.getPointDeVie(),personnage.getPointDeVie());
        }
        // Test de récupération d'une personne non existante
        assertNull(personnageDaoImpl.getPersonnage("jhon"));
    }
    /**
     * Teste la méthode de modification de personnages.
     * Modifie un personnage de la liste personnages, vérifie que la modification s'est bien déroulée
     * et compare les attributs modifiés avec les attributs du personnage résultant.
     *
     * @throws Throwable Si une exception est levée pendant le test.
     */
    @Test
    @Order(4)
    public void testModifierPersonnages() throws Throwable {
        // Obtient le nom du personnage à modifier
        String name = personnages.get(1).getName();
        // Récupère le personnage à modifier
        Personnage personnageModifier = personnageDaoImpl.getPersonnage(name);
        // Modifie les attributs du personnage
        personnageModifier.setManna(75);
        personnageModifier.setPointDeVie(8);
        // enregistrer la modification dans la base de données
        boolean result = personnageDaoImpl.modifierPersonnage(personnageModifier);
        // Vérifie que la modification s'est déroulée avec succès
        assertTrue(result);

        // Récupère le personnage après modification depuis la base de données
        Personnage personnageResultat = personnageDaoImpl.getPersonnage(name);
        // Compare les attributs modifiés avec les attributs du personnage résultant
        assertEquals(personnageResultat.getManna(), personnageModifier.getManna());
        assertEquals(personnageResultat.getPointDeVie(), personnageModifier.getPointDeVie());
    }

    /**
     * Teste la méthode de suppression de personnage.
     * Supprime chaque personnage de la liste personnages et vérifie que la suppression s'est bien déroulée.
     * Ensuite, vérifie que la liste des personnages résultants est vide.
     *
     * @throws Throwable Si une exception est levée pendant le test.
     */
    @Test
    @Order(5)
    public void testSupprimerPersonnage() throws Throwable {
        // Supprimer chaque personnage de la liste personnages et vérifier que la suppression s'est bien déroulée
        for (Personnage personnage : personnages) {
            assertTrue(personnageDaoImpl.supprimerPersonnage(personnage.getId()));
        }

        // Récupérer la liste des personnages depuis la base de données
        List<Personnage> personnagesResultat = personnageDaoImpl.listerPersonnages();
        // Vérifier que la liste des personnages résultants est vide
        assertTrue(personnagesResultat.isEmpty());
    }
}