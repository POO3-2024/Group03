/**
 * @author Demba Mohamed Samba
 * */
package be.helha.poo3.projet.javafx.projetjavafx.domaine;

/**
 * Cette classe représente un personnage.
 * Un personnage a un nom, des points de vie et du manna.
 * Chaque personnage possède également un identifiant unique.
 * Les points de vie et du mana sont initialisés par défaut à 1000 et 100 respectivement lors de la création d'un personnage.
 * L'identifiant est automatiquement attribué de manière séquentielle à chaque nouveau personnage créé.
 *
 */
public class Personnage {
    private static int nbPerso =0;

    private int id;
    private String name ;
    private int pointDeVie;
    private int manna;

/**
 * Constructeur pour créer un personnage avec un nom donné.
 * Initialise les points de vie et de mana par défaut.
 *
 * @param name Le nom du personnage.
 */
    public Personnage (String name) {
        this.name = name;
        this.pointDeVie =1000;
        this.manna=100;
        this.id = nbPerso;
        nbPerso += 1;
    }

    /**
     * Récupère l'identifiant du personnage.
     * @return l'identifiant du personnage {@code int}.
     */
    public int getId() {
        return id;
    }
    /**
     * Définit l'identifiant du personnage.
     * @param id l'identifiant du personnage à définir.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Récupère le nom.
     * @return le nom {@code String}.
     */
    public String getName() {
        return name;
    }


    /**
     * Récupère les points de vie.
     * @return les points de vie {@code int}.
     */
    public int getPointDeVie() {
        return pointDeVie;
    }
    /**
     * Récupère le manna.
     * @return le manna {@code int}.
     */
    public int getManna() {
        return manna;
    }

    /**
     * Modifie les points de vie.
     * @param pointDeVie les points de vie à définir.
     */
    public void setPointDeVie(int pointDeVie) {
        this.pointDeVie = pointDeVie;
    }
    /**
     * Définit la manna.
     * @param manna la manna à définir.
     */
    public void setManna(int manna) {
        this.manna = manna;
    }
}
