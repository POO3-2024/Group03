package be.helha.poo3.projet.springboot.projetjavaspringboot.dao;

import be.helha.poo3.projet.springboot.projetjavaspringboot.domaine.Armes;

import java.util.List;

/**
 * interface avec les methode pour gere les armes
 *
 * @author  Abdel Alahyane
 */
public interface ArmeDao extends Dao {
    boolean ajouterArme(Armes armes);
    Armes getArme(String nom);
    boolean supprimerArmes(int id);
    boolean modifierArmes(Armes armes);
    List<Armes> ListerArmes();
}
