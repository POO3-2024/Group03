package be.helha.lib.poo3.daoImpl;

/**
 * La classe Persistance représente le contenu du fichier de config
 *
 * @author Diesbecq Aaron
 * @see Persistance
 */
public class Persistance {
    /**
     * Le type de connexion
     */
    public String ConnectionType;
    /**
     * Le chemin pour accéder à la DB
     */
    public String DBPath;

    /**
     * Constructeur de la classe Persitance qui construit juste un objet
     */
    public Persistance() {

    }

    /**
     * Sert à récupérer le type de connexion
     * @return le type de connexion
     */
    public String getConnectionType(){
        return ConnectionType;
    }

    /**
     * Sert à récupérer le chemin d'accès à la DB
     * @return le chemin d'accès à la DB
     */
    public String getDBPath(){
        return DBPath;
    }
}
