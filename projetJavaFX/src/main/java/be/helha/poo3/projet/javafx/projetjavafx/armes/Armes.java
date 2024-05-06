package be.helha.poo3.projet.javafx.projetjavafx.Armes;

public class Armes {
    private static int nbArmes = 0;
    private  int id;
    private String nom;
    private  int degats;


    public Armes (String nom, int degats){
        this.id = nbArmes++;
        this.nom = nom;
        this.degats;
    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDegats() {
        return degats;
    }

    public void setDegats(int degats) {
        this.degats = degats;
    }
}

