package beans;


import DAO.UtilisateurDao;

import java.util.List;

public class UtilisateurList {



    public Utilisateur checkLogPass(String mail,String mdp,UtilisateurDao utilisateurDao){

        List<Utilisateur> utilisateurList=utilisateurDao.lister();

        for (int i = 0; i < utilisateurList.size(); i++) {
            String log = utilisateurList.get(i).getMail();
            if (log.equals(mail)){
                String p = utilisateurList.get(i).getMdp();
                if (p.equals(mdp)){
                    return utilisateurList.get(i);
                }
            }
        }
        return null;

    }

}
