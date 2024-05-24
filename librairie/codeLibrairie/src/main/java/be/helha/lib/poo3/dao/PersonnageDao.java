package be.helha.lib.poo3.dao;

import be.helha.lib.poo3.domaine.Personnage;

import java.util.List;
/**
 * interface avec les methode pour gere les personnage
 *
 * @author  Demba Mohamed Samba
 */
public interface PersonnageDao extends Dao {
    /**
     * Méthode pour ajouter un personnage à la base de données.
     *
     * @param perso Le personnage à ajouter.
     * @return {@code true} si l'ajout du personnage a réussi, {@code false} sinon.
     */
    boolean ajouterPerso(Personnage perso);
    /**
     * Récupère un personnage à partir de son nom.
     *
     * @param nom nom du personnage à récupérer.
     * @return {@code perso}Le personnage correspondant au nom spécifié, ou {@code perso = null} s'il n'existe pas.
     */
    Personnage getPersonnage(String nom);
    /**
     * Supprime un personnage de la base de données en utilisant son identifiant.
     *
     * @param id L'identifiant du personnage à supprimer.
     * @return {@code true} si la suppression du personnage a réussi, {@code false} sinon.
     */
    boolean supprimerPersonnage(int id);
    /**
     * Modifie un personnage dans la base de données.
     *
     * @param perso Le personnage à modifier avec ses nouveaux attributs.
     * @return {@code true} si la modification du personnage a réussi, sinon {@code false} .
     */
    boolean modifierPersonnage(Personnage perso);
    /**
     * Récupère une liste de tous les personnages depuis la base de données.
     *
     * @return {@code List<Personnage>} Une liste contenant tous les personnages présents dans la base de données ou {@code null}.
     */
    List<Personnage> listerPersonnages();
    /**
     * Récupère un personnage à partir de son id.
     *
     * @param id id du personnage à récupérer.
     * @return {@code perso}Le personnage correspondant à l'id spécifié, ou {@code perso = null} s'il n'existe pas.
     */
    Personnage getPersonnageID(String id);
}
