package DAO;

/**
 * Created by François on 01-10-17.
 */

import beans.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDaoImpl implements UtilisateurDao {
    private DAOFactory daoFactory;

    UtilisateurDaoImpl(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void ajouter(Utilisateur utilisateur) {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement =connexion.prepareStatement("INSERT  INTO Client(nom,prenom,mdp,ville,adresse,code_postal,mail,pays,num_tel,date_naissance) VALUES (?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,utilisateur.getNom());
            preparedStatement.setString(2,utilisateur.getPrenom());
            preparedStatement.setString(3,utilisateur.getMdp());
            preparedStatement.setString(4,utilisateur.getVille());
            preparedStatement.setString(5,utilisateur.getAdresse());
            preparedStatement.setString(6,utilisateur.getCodePostal());
            preparedStatement.setString(7,utilisateur.getMail());
            preparedStatement.setString(8,utilisateur.getPays());
            preparedStatement.setString(9,utilisateur.getNumTel());
            preparedStatement.setDate(10, (Date) utilisateur.getDateNaissance());


            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Utilisateur> lister() {
        List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            //Execute request
            resultSet=statement.executeQuery("SELECT nom,prenom,mdp,ville,adresse,code_postal,mail,pays,num_tel,date_naissance FROM Client;");

            //Recup data
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String mdp = resultSet.getString("mdp");
                String ville = resultSet.getString("ville");
                String adresse = resultSet.getString("adresse");
                String code_postal = resultSet.getString("code_postal");
                String mail = resultSet.getString("mail");
                String pays = resultSet.getString("pays");
                String num_tel = resultSet.getString("num_tel");
                Date date_naissance= (Date) resultSet.getDate("date_naissance");


                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setNom(nom);
                utilisateur.setPrenom(prenom);
                utilisateur.setMdp(mdp);
                utilisateur.setVille(ville);
                utilisateur.setAdresse(adresse);
                utilisateur.setCodePostal(code_postal);
                utilisateur.setMail(mail);
                utilisateur.setPays(pays);
                utilisateur.setNumTel(num_tel);
                utilisateur.setDateNaissance(date_naissance);


                utilisateurs.add(utilisateur);
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

}