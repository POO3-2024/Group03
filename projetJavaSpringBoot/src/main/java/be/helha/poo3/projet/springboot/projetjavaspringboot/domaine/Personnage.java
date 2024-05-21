/**
 * @author Demba Mohamed Samba
 * */
package be.helha.poo3.projet.springboot.projetjavaspringboot.domaine;




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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getPointDeVie() {
        return pointDeVie;
    }

    public int getManna() {
        return manna;
    }

    public void setPointDeVie(int pointDeVie) {
        this.pointDeVie = pointDeVie;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManna(int manna) {
        this.manna = manna;
    }
}
