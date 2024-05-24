package be.helha.poo3.projet.springboot.projetjavaspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'api : permet le lancement de l'api
 */
@SpringBootApplication
public class ProjetJavaSpringBootApplication {

	/**
	 * Lance l'api
	 * @param args paramètre d'entrée au lancement
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProjetJavaSpringBootApplication.class, args);
	}

}
