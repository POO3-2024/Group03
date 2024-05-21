package be.helha.poo3.projet.springboot.projetjavaspringboot.dao;

import be.helha.poo3.projet.springboot.projetjavaspringboot.domaine.Personnage;

import java.util.List;
/**
 * interface avec les methode pour gere les personnage
 *
 * @author  Demba Mohamed Samba
 */
public interface PersonnageDao extends Dao {
    boolean ajouterPerso(Personnage perso);
    Personnage getPersonnage(String id);
    boolean supprimerPersonnage(int id);
    boolean modifierPersonnage(Personnage perso);
    List<Personnage> listerPersonnages();
}