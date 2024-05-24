package be.helha.poo3.projet.springboot.projetjavaspringboot.tests;


import be.helha.lib.poo3.dao.ArmeDao;
import be.helha.lib.poo3.daoImpl.ArmeDaoImpl;
import be.helha.lib.poo3.domaine.Armes;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests des fonctionnalités /arme de l'api
 * @author Tabi Zaccaria
 * @see be.helha.poo3.projet.springboot.projetjavaspringboot.tests.SpringControlArmeTests
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringControlArmeTests {

    /**
     * Port du serveur local
     */
    @LocalServerPort
    private int port = 8080;

    /**
     * Classe de test pour api springboot (permet recuperer réponse des requetes)
     */
    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Gestionnaire d'arme
     */
    private ArmeDao armeDao = new ArmeDaoImpl();
    /**
     * Arme de test 1
     */
    private Armes arme1 = null;

    /**
     * Arme de test 2
     */
    private Armes arme2 = null;

    /**
     * Verifie si l'api renvoie bien la liste des armes
     */
    @Order(1)
    @Test
    public void testListerArmeOK()  {


        ajouterArmeDB();

        //- tester
        List<Map<String,String>> listeArmes = new ArrayList<>();
        Map<String,String> mapArme1 = new HashMap<String,String>();
        mapArme1.put("ID", String.valueOf(this.arme1.getId()));
        mapArme1.put("Nom",this.arme1.getNom());


        Map<String,String> mapArme2 = new HashMap<String,String>();
        mapArme2.put("ID",String.valueOf(this.arme2.getId()));
        mapArme2.put("Nom",this.arme2.getNom());

        listeArmes.add(mapArme1);
        listeArmes.add(mapArme2);


        List<Map<String, String>> response = restTemplate.getForObject("http://localhost:" + this.port + "/arme", List.class);

        // Comparer la réponse de l'API avec la liste attendue
        assertThat(response).containsExactlyInAnyOrderElementsOf(listeArmes);

        supprimerArmesDB();



    }

    /**
     * Verifie si l'api renvoie le bon message si la liste des armes est vide
     */
    @Order(2)
    @Test
    public void testListerArmeVide() {



        List<Map<String,String>> listeArmes = new ArrayList<>();


        // Obtenir la réponse de l'API
        List<Map<String, String>> response = restTemplate.getForObject("http://localhost:" + this.port + "/arme", List.class);

        // Comparer la réponse de l'API avec la liste attendue
        assertThat(response).containsExactlyInAnyOrderElementsOf(listeArmes);

    }

    /**
     * Verifie si l'api renvoie les informations d'une arme

     */
    @Order(3)
    @Test
    public void testGetArmeOK(){
		/* A AJOUTER !!!!!!!!!!!!
		- vider la db
		- ajouter 1 arme (id = 1, nom = dague, degat = 12)
		- tester
		  !!!!!!!!!!!!*/
        ajouterArmeDB();
        Integer id = this.arme1.getId();
        Map<String,String> mapArme1 = new HashMap<String,String>();
        mapArme1.put("ID", String.valueOf(this.arme1.getId()));
        mapArme1.put("Nom",this.arme1.getNom());
        mapArme1.put("Degat", String.valueOf(this.arme1.getDegats()));

        Map<String, String> response = restTemplate.getForObject("http://localhost:" + this.port + "/arme/" + id, Map.class);

        // Comparer la réponse de l'API avec la map attendue
        assertThat(response).containsExactlyInAnyOrderEntriesOf(mapArme1);

        supprimerArmesDB();
    }

    /**
     * Verifie si l'api renvoie un message lorsque l'identifiant d'une arme n'existe pas

     */
    @Order(4)
    @Test
    public void testGetArmeInexistante() {

        Integer id = 5;

        Map<String,String> mapReponse = new HashMap<>();
        mapReponse.put("message","L'arme avec l'identifiant " + id + " n'existe pas");

        Map<String, String> response = restTemplate.getForObject("http://localhost:" + this.port + "/arme/" +id, Map.class);

        // Comparer la réponse de l'API avec la map attendue
        assertThat(response).containsAllEntriesOf(mapReponse);

    }

    /**
     * Verifie si l'api renvoie un message lorsque l'identifiant de l'arme passer en paramètre n'est pas un entier
     */
    @Order(5)
    @Test
    public void testGetArmeIdPasEntier() {

        Map<String,String> mapReponse = new HashMap<>();
        mapReponse.put("message","l'identifiant de l'arme n'est pas un entier");

        Map<String, String> response = restTemplate.getForObject("http://localhost:" + this.port + "/arme/$", Map.class);

        // Comparer la réponse de l'API avec la map attendue
        assertThat(response).containsAllEntriesOf(mapReponse);

    }

    /**
     * Ajoute des armes de test
     * @return boolean true si les armes ont été ajouté ou false sinon
     */
    private boolean ajouterArmeDB(){
        this.arme1 = new Armes("TEST1");
        //perso1.setId(1);
        this.arme1.setDegats(20);


        this.arme2 = new Armes("TEST2");
        //perso2.setId(2);
        this.arme2.setDegats(40);

        boolean ajoutOk1 = armeDao.ajouterArme(this.arme1);
        boolean ajoutOk2 = armeDao.ajouterArme(this.arme2);
        return ajoutOk1 && ajoutOk2;
    }

    /**
     * Supprime les armes de test
     */
    private void supprimerArmesDB(){

        this.armeDao.supprimerArmes(this.arme1.getId());
        this.armeDao.supprimerArmes(this.arme2.getId());
    }

}

