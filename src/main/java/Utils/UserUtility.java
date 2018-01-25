package Utils;

import beans.Reparateur;
import beans.Utilisateur;
import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserUtility {




    public static Utilisateur getUserFromSession(HttpServletRequest request){

        Utilisateur sessionUser;

        HttpSession session = request.getSession();
        Object obj = session.getAttribute("sessionUtilisateur");

        Class<Utilisateur> u = Utilisateur.class;

        return sessionUser = u.cast(obj);

    }


    public static Reparateur getReparateurFromSession(HttpServletRequest request){

        Reparateur sessionRepa;

        HttpSession session = request.getSession();
        Object obj = session.getAttribute("sessionReparateur");

        Class<Reparateur> u = Reparateur.class;

        return sessionRepa = u.cast(obj);

    }

    public static void validationMotsDePasse( String motDePasse, String confirmation ) throws Exception {
        if ( motDePasse != null && confirmation != null ) {
            if ( !motDePasse.equals( confirmation ) ) {
                throw new Exception( "Les mots de passe entrés sont différents, merci de les saisir à nouveau." );
            } else if ( motDePasse.length() < 3 ) {
                throw new Exception( "Les mots de passe doivent contenir au moins 3 caractères." );
            }
        } else {
            throw new Exception( "Merci de saisir et confirmer votre mot de passe." );
        }
    }
}
