package be.helha.poo3.projet.javafx.projetjavafx.test.dbmanager;


import be.helha.lib.poo3.dao.ArmeDao;
import be.helha.lib.poo3.daoImpl.ArmeDaoImpl;
import be.helha.lib.poo3.domaine.Armes;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Classe de test pour la gestion des armes utilisant JUnit 5.
 * La DB de test doit être vide pour que les tests fonctionnent.
 *
 * @author Alahyane Abdel
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class testArmes {

    ArmeDao armeDaoImpl = new ArmeDaoImpl();
    private static final List<Armes> armes = new ArrayList<>();

    /**
     * Méthode d'initialisation exécutée avant tous les tests.
     * Initialise une liste d'armes à utiliser dans les tests.
     */
    @BeforeAll
    static void initialisation() {
        Armes epee = new Armes("Epée");
        Armes arc = new Armes("Arc");
        Armes lance = new Armes("Lance");
        Armes hache = new Armes("Hache");
        Armes pistolet = new Armes("Pistolet");

        armes.add(epee);
        armes.add(arc);
        armes.add(lance);
        armes.add(hache);
        armes.add(pistolet);

    }

    /**
     * Test de l'ajout d'armes dans la base de données.
     * Vérifie que chaque arme est ajoutée avec succès et qu'un doublon ne peut pas être ajouté.
     */
    @Test
    @Order(1)
    public void testAjoutArme(){
        boolean ajoutResult = false;
        for (Armes armes1 : armes){
            ajoutResult = armeDaoImpl.ajouterArme(armes1);
            assertTrue(ajoutResult);
        }
        ajoutResult = armeDaoImpl.ajouterArme(armes.get(0));
        assertFalse(ajoutResult);
    }


    /**
     * Test de la liste des armes dans la base de données.
     * Vérifie que toutes les armes sont correctement listées et que les attributs correspondent.
     */
    @Test
    @Order(2)
    public void testListerArmes() throws Throwable{
        List<Armes> armesResultat = armeDaoImpl.ListerArmes();
        assertEquals(armes.size(),armesResultat.size());

        for (int i=0; i<armes.size();i++){
            Armes armeAttendu = armes.get(i);
            Armes armeResultat = armesResultat.get(i);

            assertEquals(armeResultat.getNom(),armeAttendu.getNom());
            assertEquals(armeResultat.getId(),armeAttendu.getId());
            assertEquals(armeResultat.getDegats(),armeAttendu.getDegats());
        }
    }



    /**
     * Test de la recherche d'armes dans la base de données par nom.
     * Vérifie que chaque arme est correctement récupérée par son nom.
     */
    @Test
    @Order(3)
    public void testRechercherArmes() throws Throwable{
        for (Armes armes : armes){
            Armes armeResultat = armeDaoImpl.getArme(armes.getNom());

            assertEquals(armeResultat.getNom(),armes.getNom());
            assertEquals(armeResultat.getDegats(),armes.getDegats());
            assertEquals(armeResultat.getId(),armes.getId());
        }
        assertNull(armeDaoImpl.getArme("Pompe"));
    }

    /**
     * Test de récupération d'une arme existante par son nom.
     * Vérifie que l'arme "Epée" est correctement récupérée avec les bons attributs.
     */
    @Test
    @Order(4)
    public void testGetArmeTrouvee() {
        // Test pour vérifier si une arme existante est correctement récupérée
        Armes result = armeDaoImpl.getArme("Epée");
        assertNotNull(result, "L'arme aurait dû être trouvée dans la base de données.");
        assertEquals("Epée", result.getNom(), "Le nom de l'arme ne correspond pas.");
        assertEquals(100, result.getDegats(), "Les dégâts de l'arme ne correspondent pas.");
        assertEquals(0, result.getId(), "L'ID de l'arme ne correspond pas.");
    }


    /**
     * Test de la modification des attributs d'une arme.
     * Vérifie que les modifications sont correctement enregistrées dans la base de données.
     */
    @Test
    @Order(5)
    public void testModifierArmes() throws Throwable{

        String nom = armes.get(1).getNom();

        Armes armesModifier = armeDaoImpl.getArme(nom);
        armesModifier.setDegats(50);
        armesModifier.setNom("test");

        boolean result  = armeDaoImpl.modifierArmes(armesModifier);

        assertTrue(result);

        Armes armesResultat = armeDaoImpl.getArme("test");

        assertEquals(armesResultat.getDegats(),armesModifier.getDegats());
        assertEquals(armesResultat.getNom(),armesModifier.getNom());
    }

    /**
     * Test de la suppression des armes de la base de données.
     * Vérifie que chaque arme est correctement supprimée.
     */
    @Test
    @Order(6)
    public void testSupprimerArme() throws Throwable{
        for (Armes armes : armes){
            assertTrue(armeDaoImpl.supprimerArmes(armes.getId()));
        }

        List<Armes> armesResult= armeDaoImpl.ListerArmes();
        System.out.println(armesResult.toString());
        assertTrue(armesResult.isEmpty());
    }


}
