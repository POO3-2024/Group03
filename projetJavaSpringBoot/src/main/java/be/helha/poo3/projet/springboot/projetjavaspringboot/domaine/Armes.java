package be.helha.poo3.projet.springboot.projetjavaspringboot.domaine;


public class Armes {
    private static int nbArmes = 0;
    private  int id;
    private String nom;
    private  int degats;


    public Armes (String nom){
        this.id = nbArmes++;
        this.nom = nom;
        this.degats = 100;
    }


    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
        //nbArmes -=1;
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
