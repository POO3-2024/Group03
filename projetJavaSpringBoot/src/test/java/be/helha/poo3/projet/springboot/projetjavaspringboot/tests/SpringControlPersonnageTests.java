package be.helha.poo3.projet.springboot.projetjavaspringboot.tests;


import be.helha.lib.poo3.daoImpl.PersonnageDaoImpl;
import be.helha.lib.poo3.domaine.Personnage;
import org.junit.jupiter.api.Order;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests des fonctionnalités /personnage de l'api
 * @author Tabi Zaccaria
 * @see be.helha.poo3.projet.springboot.projetjavaspringboot.tests.SpringControlPersonnageTests
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringControlPersonnageTests {

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
     * Gestionnaire de personnage
     */
    private PersonnageDaoImpl personnageDao = new PersonnageDaoImpl();
    /**
     * Personnage de test 1
     */
    Personnage perso1 = null;
    /**
     * Personnage de test 2
     */
    Personnage perso2 = null;



    /**
     * Verifie si l'api renvoie bien la liste des personnages
     */
    @Order(1)
    @Test
    public void testListerPersonnageOK() {

		//- la db doit être vide
		//- ajouter 2 personnages (nom : TEST + n°)
        ajouterPersonnagesDB();

		//- tester
        List<Map<String,String>> listePersonnage = new ArrayList<>();
        Map<String,String> mapPerso1 = new HashMap<String,String>();
        mapPerso1.put("ID", String.valueOf(this.perso1.getId()));
        mapPerso1.put("Nom",this.perso1.getName());


        Map<String,String> mapPerso2 = new HashMap<String,String>();
        mapPerso2.put("ID", String.valueOf(this.perso2.getId()));
        mapPerso2.put("Nom",this.perso2.getName());

        listePersonnage.add(mapPerso1);
        listePersonnage.add(mapPerso2);


        // Obtenir la réponse de l'API
        List<Map<String, String>> response = restTemplate.getForObject("http://localhost:" + this.port + "/personnage", List.class);

        // Comparer la réponse de l'API avec la liste attendue
        assertThat(response).containsExactlyInAnyOrderElementsOf(listePersonnage);

        supprimerPersonnagesDB();
    }

    /**
     * Verifie si l'api renvoie le bon message si la liste des personnages est vide
     */
    @Order(2)
    @Test
    public void testListerPersonnageVide(){

        List<Map<String,String>> listePersonnage = new ArrayList<>();


        // Obtenir la réponse de l'API
        List<Map<String, String>> response = restTemplate.getForObject("http://localhost:" + this.port + "/personnage", List.class);

        // Comparer la réponse de l'API avec la liste attendue
        assertThat(response).containsExactlyInAnyOrderElementsOf(listePersonnage);

    }

    /**
     * Verifie si l'api renvoie les informations d'un personnage
     */
    @Order(3)
    @Test
    public void testGetPersonnageOK() {


        ajouterPersonnagesDB();
        Integer id = this.perso1.getId();


        Map<String,String> mapPerso1 = new HashMap<String,String>();
        mapPerso1.put("ID", String.valueOf(this.perso1.getId()));
        mapPerso1.put("Nom",this.perso1.getName());
        mapPerso1.put("Point de vie", String.valueOf(this.perso1.getPointDeVie()));
        mapPerso1.put("Manna", String.valueOf(this.perso1.getManna()));

        Map<String, String> response = restTemplate.getForObject("http://localhost:" + this.port + "/personnage/" + id, Map.class);

        // Comparer la réponse de l'API avec la map attendue
        assertThat(response).containsExactlyInAnyOrderEntriesOf(mapPerso1);

        supprimerPersonnagesDB();

    }

    /**
     * Verifie si l'api renvoie un message lorsque l'identifiant d'un personnage n'existe pas
     */
    @Order(4)
    @Test
    public void testGetPersonnageInexistant() {

        Integer id = 5;

        Map<String,String> mapReponse = new HashMap<>();
        mapReponse.put("message","Le personnage avec l'identifiant " + id + " n'existe pas");

        Map<String, String> response = restTemplate.getForObject("http://localhost:" + this.port + "/personnage/" +id, Map.class);

        // Comparer la réponse de l'API avec la map attendue
        assertThat(response).containsAllEntriesOf(mapReponse);

    }

    /**
     * Verifie si l'api renvoie un message lorsque l'identifiant du personnage passer en paramètre n'est pas un entier
     */
    @Order(5)
    @Test
    public void testGetPersonnageIdPasEntier(){


        Map<String,String> mapReponse = new HashMap<>();
        mapReponse.put("message","l'identifiant du personnage n'est pas un entier");

        Map<String, String> response = restTemplate.getForObject("http://localhost:" + this.port + "/personnage/$", Map.class);

        // Comparer la réponse de l'API avec la map attendue
        assertThat(response).containsAllEntriesOf(mapReponse);

    }

    /**
     * Ajoute des personnages de test
     * @return boolean true si les personnages ont été ajouté ou false sinon
     */
    private boolean ajouterPersonnagesDB(){
        this.perso1 = new Personnage("TEST1");
        //perso1.setId(1);
        this.perso1.setPointDeVie(1000);
        this.perso1.setManna(100);

        this.perso2 = new Personnage("TEST2");
        //perso2.setId(2);
        this.perso2.setPointDeVie(1000);
        this.perso2.setManna(100);

        boolean ajoutOk1 = personnageDao.ajouterPerso(this.perso1);
        boolean ajoutOk2 =personnageDao.ajouterPerso(this.perso2);
        return ajoutOk1 && ajoutOk2;
    }

    /**
     * Supprime les personnages de test
     */
    private void supprimerPersonnagesDB(){
        this.personnageDao.supprimerPersonnage(this.perso1.getId());
        this.personnageDao.supprimerPersonnage(this.perso2.getId());
    }

}

