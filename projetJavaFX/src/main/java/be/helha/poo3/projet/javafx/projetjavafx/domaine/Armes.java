package be.helha.poo3.projet.javafx.projetjavafx.domaine;

/**
 * La classe Armes représente un objet d'arme avec un identifiant unique, un nom et des dégâts.
 *
 * @author Alahyane Abdel
 */
public class Armes {
    // Attributs
    private static int nbArmes = 0;
    private  int id;
    private String nom;
    private  int degats;


    /**
     * Constructeur de la classe Armes.
     * @param nom Le nom de l'arme.
     */
    public Armes (String nom){
        this.id = nbArmes++;
        this.nom = nom;
        this.degats = 100;
    }

    // Getters et Setters
    /**
     * Obtient l'identifiant de l'arme.
     * @return L'identifiant de l'arme.
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'arme.
     * @param id Le nouvel identifiant de l'arme.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Obtient le nom de l'arme.
     * @return Le nom de l'arme.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de l'arme.
     * @param nom Le nouveau nom de l'arme.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }


    /**
     * Obtient les dégâts de l'arme.
     * @return Les dégâts de l'arme.
     */
    public int getDegats() {
        return degats;
    }


    /**
     * Définit les dégâts de l'arme.
     * @param degats Les nouveaux dégâts de l'arme.
     */
    public void setDegats(int degats) {
        this.degats = degats;
    }
}

