package be.helha.lib.poo3.dao;

import be.helha.lib.poo3.domaine.Armes;

import java.util.List;

/**
 * interface avec les methode pour gere les armes
 *
 * @author  Abdel Alahyane
 */
public interface ArmeDao extends Dao {
    /**
     * Ajoute une nouvelle arme dans la base de données.
     *
     * @param armes l'arme à ajouter
     * @return true si l'ajout a réussi, false sinon
     */
    boolean ajouterArme(Armes armes);
    /**
     * Récupère une arme à partir de son nom.
     *
     * @param nom nom de l'arme à récupérer
     * @return l'arme correspondante, ou null si aucune arme n'est trouvée
     */
    Armes getArme(String nom);
    /**
     * Supprime une arme de la base de données à partir de son ID.
     *
     * @param id l'ID de l'arme à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    boolean supprimerArmes(int id);
    /**
     * Modifie les informations d'une arme dans la base de données.
     *
     * @param armes l'arme à modifier
     * @return true si la modification a réussi, false sinon
     */
    boolean modifierArmes(Armes armes);
    /**
     * Liste toutes les armes de la base de données.
     *
     * @return une liste d'armes
     */
    List<Armes> ListerArmes();
    /**
     * Récupère une arme à partir de son id.
     *
     * @param id id de l'arme à récupérer
     * @return l'arme correspondante, ou null si aucune arme n'est trouvée
     */
    Armes getArmeID(String id);
}
