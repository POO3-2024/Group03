package be.helha.poo3.projet.springboot.projetjavaspringboot;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests des fonctionnalités /arme de l'api
 * @author Tabi Zaccaria
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringControlArmeTests {

    @LocalServerPort
    private int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Verifie si l'api renvoie bien la liste des armes
     * @throws Exception
     */
    @Test
    public void testListerArmeOK() throws Exception {
		/* A AJOUTER !!!!!!!!!!!!
		- vider la db
		- ajouter 2 armes : {id = 1, nom = dague, degat = 12}, {id = 2, nom = marteau, degat = 45}
		- tester
		  !!!!!!!!!!!! */
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/arme", String.class))
                .contains("<ul><li>{ id : 1, nom : dague }</li><li>{ id : 2, nom : marteau }</li></ul>");
    }

    /**
     * Verifie si l'api renvoie le bon message si la liste des armes est vide
     * @throws Exception
     */
    @Test
    public void testListerArmeVide() throws Exception{
		/* A AJOUTER  !!!!!!!!!!!!
		- vider la db
		- tester
		  !!!!!!!!!!!!*/
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/arme", String.class))
                .contains("<p> <i> Pas d'armes <i></p>");

    }

    /**
     * Verifie si l'api renvoie les informations d'une arme
     * @throws Exception
     */
    @Test
    public void testGetArmeOK() throws Exception{
		/* A AJOUTER !!!!!!!!!!!!
		- vider la db
		- ajouter 1 arme (id = 1, nom = dague, degat = 12)
		- tester
		  !!!!!!!!!!!!*/
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/arme/1", String.class))
                .contains("<p>Arme{id=1, nom='dague', degat=12}</p>");

    }

    /**
     * Verifie si l'api renvoie un message lorsque l'identifiant d'une arme n'existe pas
     * @throws Exception
     */
    @Test
    public void testGetArmeInexistante() throws Exception{
		/* A AJOUTER !!!!!!!!!!!!
		- vider la db
		- tester
		  !!!!!!!!!!!!*/
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/arme/5", String.class))
                .contains("<p> Pas d'arme à l'id : 5 </p>");

    }

    /**
     * Verifie si l'api renvoie un message lorsque l'identifiant de l'arme passer en paramètre n'est pas un entier
     * @throws Exception
     */
    @Test
    public void testGetArmeIdPasEntier() throws Exception{
		/* A AJOUTER !!!!!!!!!!!!
		- vider la db
		- tester
		  !!!!!!!!!!!!*/
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/arme/$", String.class))
                .contains("<p> Identifiant n'est pas un entier </p>");

    }

}

