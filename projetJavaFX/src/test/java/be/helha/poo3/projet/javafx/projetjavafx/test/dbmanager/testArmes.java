package be.helha.poo3.projet.javafx.projetjavafx.test.dbmanager;

import be.helha.poo3.projet.javafx.projetjavafx.armes.GestionArmes;
import be.helha.poo3.projet.javafx.projetjavafx.armes.Armes;
import javafx.fxml.FXML;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


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

}
