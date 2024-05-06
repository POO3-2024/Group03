package be.helha.poo3.projet.javafx.projetjavafx.dbmanager;

/**
 * La classe Persistance représente le contenu du fichier de config
 *
 * @author Diesbecq Aaron
 * @see be.helha.poo3.projet.javafx.projetjavafx.dbmanager.Persistance
 */
public class Persistance {
    /**
     * Le type de connexion
     */
    private String type;
    /**
     * Le chemin pour accéder à la DB
     */
    private String path;

    /**
     * Constructeur de la classe Persitance qui construit juste un objet
     */
    public Persistance() {
        super();
    }

    /**
     * Sert à récupérer le type de connexion
     * @return le type de connexion
     */
    public String getType(){
        return type;
    }

    /**
     * Sert à récupérer le chemin d'accès à la DB
     * @return le chemin d'accès à la DB
     */
    public String getPath(){
        return path;
    }
}
