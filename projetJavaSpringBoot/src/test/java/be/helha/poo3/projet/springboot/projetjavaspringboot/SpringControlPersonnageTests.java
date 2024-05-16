package be.helha.poo3.projet.springboot.projetjavaspringboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests des fonctionnalités /personnage de l'api
 * @author Tabi Zaccaria
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringControlPersonnageTests {

    @LocalServerPort
    private int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Verifie si l'api renvoie bien la liste des personnages
     * @throws Exception
     */
    @Test
    public void testListerPersonnageOK() throws Exception {
		/* A AJOUTER !!!!!!!!!!!!
		- vider la db
		- ajouter 3 personnages (nom : TEST + n°)
		- tester
		- vider la db
		  !!!!!!!!!!!! */
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/personnage", String.class))
                .contains("<ul><li>{ id : 1, nom : TEST1 }</li><li>{ id : 2, nom : TEST2 }</li><li>{ id : 3, nom : TEST3 }</li></ul>");
    }

    /**
     * Verifie si l'api renvoie le bon message si la liste des personnages est vide
     * @throws Exception
     */
    @Test
    public void testListerPersonnageVide() throws Exception{
		/* A AJOUTER  !!!!!!!!!!!!
		- vider la db
		- tester
		- vider la db
		  !!!!!!!!!!!!*/
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/personnage", String.class))
                .contains("<p> <i> Pas de personnages <i></p>");

    }

    /**
     * Verifie si l'api renvoie les informations d'un personnage
     * @throws Exception
     */
    @Test
    public void testGetPersonnageOK() throws Exception{
		/* A AJOUTER !!!!!!!!!!!!
		- vider la db
		- ajouter 1 personnage (id = 1 , nom  = "TEST1", pv = 0, manna = 1000)
		- tester
		- vider la db
		  !!!!!!!!!!!!*/
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/personnage/1", String.class))
                .contains("Personnage{id=1, nom='TEST1', pv=0, manna=100}");

    }

    /**
     * Verifie si l'api renvoie un message lorsque l'identifiant d'un personnage n'existe pas
     * @throws Exception
     */
    @Test
    public void testGetPersonnageInexistant() throws Exception{
		/* A AJOUTER !!!!!!!!!!!!
		- vider la db
		- tester
		- vider la db
		  !!!!!!!!!!!!*/
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/personnage/5", String.class))
                .contains("<p> Pas de personnage à l'id : 5 </p>");

    }

    /**
     * Verifie si l'api renvoie un message lorsque l'identifiant du personnage passer en paramètre n'est pas un entier
     * @throws Exception
     */
    @Test
    public void testGetPersonnageIdPasEntier() throws Exception{
		/* A AJOUTER !!!!!!!!!!!!
		- vider la db
		- tester
		  !!!!!!!!!!!!*/
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/personnage/$", String.class))
                .contains("<p> Identifiant n'est pas un entier </p>");

    }

}

