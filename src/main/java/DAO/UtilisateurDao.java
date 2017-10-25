package DAO;

/**
 * Created by François on 01-10-17.
 */

import beans.Utilisateur;

import java.util.List;

public interface UtilisateurDao {
    void ajouter(Utilisateur utilisateur);
    List<Utilisateur> lister();
}