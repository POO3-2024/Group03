package be.helha.poo3.projet.javafx.projetjavafx.test.dbmanager;

import be.helha.poo3.projet.javafx.projetjavafx.armes.GestionArmes;
import be.helha.poo3.projet.javafx.projetjavafx.armes.Armes;
import javafx.fxml.FXML;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class testArmes {

    GestionArmes gestionArmes = new GestionArmes();
    private static final List<Armes> armes = new ArrayList<>();

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

    @Test
    @Order(1)
    public void testAjoutArme(){
        boolean ajoutResult = false;
        for (Armes armes1 : armes){
            ajoutResult = gestionArmes.ajouterArme(armes1);
            assertTrue(ajoutResult);
        }
        ajoutResult = gestionArmes.ajouterArme(armes.get(0));
        assertFalse(ajoutResult);
    }


    @Test
    @Order(2)
    public void testListerArmes() throws Throwable{
        List<Armes> armesResultat = gestionArmes.ListerArmes();
        assertEquals(armes.size(),armesResultat.size());

        for (int i=0; i<armes.size();i++){
            Armes armeAttendu = armes.get(i);
            Armes armeResultat = armesResultat.get(i);

            assertEquals(armeResultat.getNom(),armeAttendu.getNom());
            assertEquals(armeResultat.getId(),armeAttendu.getId());
            assertEquals(armeResultat.getDegats(),armeAttendu.getDegats());
        }
    }



    @Test
    @Order(3)
    public void testRechercherArmes() throws Throwable{
        for (Armes armes : armes){
            Armes armeResultat = gestionArmes.getArme(armes.getNom());

            assertEquals(armeResultat.getNom(),armes.getNom());
            assertEquals(armeResultat.getDegats(),armes.getDegats());
            assertEquals(armeResultat.getId(),armes.getId());
        }
        assertNull(gestionArmes.getArme("Pompe"));
    }

    @Test
    @Order(4)
    public void testGetArmeTrouvee() {
        // Test pour vérifier si une arme existante est correctement récupérée
        Armes result = gestionArmes.getArme("Epée");
        assertNotNull(result, "L'arme aurait dû être trouvée dans la base de données.");
        assertEquals("Epée", result.getNom(), "Le nom de l'arme ne correspond pas.");
        assertEquals(100, result.getDegats(), "Les dégâts de l'arme ne correspondent pas.");
        assertEquals(0, result.getId(), "L'ID de l'arme ne correspond pas.");
    }



    @Test
    @Order(5)
    public void testModifierArmes() throws Throwable{

        String nom = armes.get(1).getNom();

        Armes armesModifier = gestionArmes.getArme(nom);
        armesModifier.setDegats(50);
        armesModifier.setNom("test");

        boolean result  = gestionArmes.modifierArmes(armesModifier);

        assertTrue(result);

        Armes armesResultat = gestionArmes.getArme("test");

        assertEquals(armesResultat.getDegats(),armesModifier.getDegats());
        assertEquals(armesResultat.getNom(),armesModifier.getNom());
    }

    @Test
    @Order(6)
    public void testSupprimerArme() throws Throwable{
        for (Armes armes : armes){
            assertTrue(gestionArmes.supprimerArmes(armes.getId()));
        }

        List<Armes> armesResult= gestionArmes.ListerArmes();
        System.out.println(armesResult.toString());
        assertTrue(armesResult.isEmpty());
    }


}
