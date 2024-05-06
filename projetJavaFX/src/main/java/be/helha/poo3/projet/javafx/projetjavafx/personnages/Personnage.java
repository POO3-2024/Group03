package be.helha.poo3.projet.javafx.projetjavafx.personnages;

public class Personnage {
    private static int nbPerso =0;


    private int id;
    private String name ;
    private int pointDeVie;
    private int manna;

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
