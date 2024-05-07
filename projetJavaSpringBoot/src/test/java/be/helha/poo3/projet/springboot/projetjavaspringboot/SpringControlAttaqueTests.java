package be.helha.poo3.projet.springboot.projetjavaspringboot;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests des fonctionnalités /attaque de l'api
 * @author Tabi Zaccaria
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringControlAttaqueTests {

    @LocalServerPort
    private int port = 8080;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Verifie si l'api renvoie les informations mis à jour du personnage attaqué après l'attaque
     * @throws Exception
     */
    @Test
    public void testAttaqueOK() throws Exception {
		/* A AJOUTER !!!!!!!!!!!!
		- vider la db
		- ajouter 1 personnage : {id = 1 , nom  = "TEST1", pv = 1000, manna = 1000}
		- ajouter 1 armes : {id = 1, nom = dague, degat = 12}
		- tester
		  !!!!!!!!!!!! */
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/attaque/1/1", String.class))
                .contains("<p>{ détail du perso attaqué : Personnage{id=1, nom='TEST1', pv=988, manna=100} }</p><br>");
    }

    /**
     * Verifie si l'api renvoie un message lorsque l'identifiant d'un personnage n'existe pas
     * @throws Exception
     */
    @Test
    public void testAttaqueIdPersonnageInexistant() throws Exception{
		/* A AJOUTER !!!!!!!!!!!!
		- vider la db
		- ajouter 1 personnage : {id = 1 , nom  = "TEST1", pv = 1000, manna = 1000}
		- ajouter 1 armes : {id = 1, nom = dague, degat = 12}
		- tester
		  !!!!!!!!!!!! */
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/attaque/50/1", String.class))
                .contains("<p> Pas de personnage à l'id : 50</p>");

    }

    /**
     * Verifie si l'api renvoie un message lorsque l'identifiant d'une arme n'existe pas
     * @throws Exception
     */
    @Test
    public void testAttaqueIdArmeInexistant() throws Exception{
		/* A AJOUTER !!!!!!!!!!!!
		- vider la db
		- ajouter 1 personnage : {id = 1 , nom  = "TEST1", pv = 1000, manna = 1000}
		- ajouter 1 armes : {id = 1, nom = dague, degat = 12}
		- tester
		  !!!!!!!!!!!! */
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/attaque/1/50", String.class))
                .contains("<p> Pas d'arme à l'id : 50</p>");

    }

    /**
     * Verifie si l'api renvoie un message lorsque l'identifiant du personnage passer en paramètre n'est pas un entier
     * @throws Exception
     */
    @Test
    public void testPersonnageIdPasEntier() throws Exception{
		/* A AJOUTER !!!!!!!!!!!!
		- vider la db
		- ajouter 1 personnage : {id = 1 , nom  = "TEST1", pv = 1000, manna = 1000}
		- ajouter 1 armes : {id = 1, nom = dague, degat = 12}
		- tester
		  !!!!!!!!!!!! */
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/attaque/$/1", String.class))
                .contains("<p> Au moins un des identifiants n'est pas un entier </p>");

    }

    /**
     * Verifie si l'api renvoie un message lorsque l'identifiant d'une arme passer en paramètre n'est pas un entier
     * @throws Exception
     */
    @Test
    public void testArmeIdPasEntier() throws Exception{
		/* A AJOUTER !!!!!!!!!!!!
		- vider la db
		- ajouter 1 personnage : {id = 1 , nom  = "TEST1", pv = 1000, manna = 1000}
		- ajouter 1 armes : {id = 1, nom = dague, degat = 12}
		- tester
		  !!!!!!!!!!!! */
        assertThat(restTemplate
                .getForObject("http://localhost:" + 8080 + "/attaque/1/$", String.class))
                .contains("<p> Au moins un des identifiants n'est pas un entier </p>");

    }

}


