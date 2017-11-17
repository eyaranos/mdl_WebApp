package FormReparateur;

import ConnecteurAPI.ConnectAPIReparateur;
import beans.Reparateur;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public class ConnexionFormReparateur {

    private static final String CHAMP_EMAIL  = "email";
    private static final String CHAMP_PASS   = "motdepasse";
    private static final String NOT_FOUND_EMAIL   = "noUserSendBAck";
    private static final String NOT_FOUND_MDP   = "wrongPwd";

    private String              resultat;
    private Map<String, String> erreurs      = new HashMap<String, String>();


    public Reparateur connecterReparateur(HttpServletRequest request ) {

        /* Récupération des champs du formulaire */
        String email = getValeurChamp( request, CHAMP_EMAIL );
        String motDePasse = getValeurChamp( request, CHAMP_PASS );
        String infoUser = "Pas de Contenu";

        Reparateur reparateur = new Reparateur();

        /* Validation du champ email. */
        try {
            validationEmail( email );
        } catch ( Exception e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        reparateur.setEmail( email );

        /* Validation du champ mot de passe. */
        try {
            validationMotDePasse( motDePasse );
        } catch ( Exception e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
        }
        reparateur.setMdp( motDePasse );

        /* Vérification par l'API dans la DB de l'existance de l'reparateur et de son mdp */
        ConnectAPIReparateur connectAPIReparateur =new ConnectAPIReparateur();

        try{
           infoUser= connectAPIReparateur.checkConnectionRepa(reparateur);
        }
        catch( IOException e){

        }
        if (!infoUser.equals("400") && !infoUser.equals("404")){
            JSONObject obj = new JSONObject(infoUser);
           reparateur.setId(obj.getInt("id"));

            try{
                if(!Utils.hashPassword.validatePassword(motDePasse, obj.getString("mdp"))){
                    setErreur( NOT_FOUND_MDP,"Wrong password" );
                }
            }
            catch(NoSuchAlgorithmException a){

            }
            catch(InvalidKeySpecException e){

            }
        }
        else{
            setErreur( NOT_FOUND_EMAIL,"Wrong email" );
        }


        /* Initialisation du résultat global de la validation. */
        if ( erreurs.isEmpty()) {
            resultat = "Succès de la connexion.";
        } else {
            resultat = "Échec de la connexion.";
        }

        return reparateur;
    }

    /**
     * Valide l'adresse email saisie.
     */
    private void validationEmail( String email ) throws Exception {
        if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception( "Merci de saisir une adresse mail valide." );
        }
    }

    /**
     * Valide le mot de passe saisi.
     */
    private void validationMotDePasse( String motDePasse ) throws Exception {
        if ( motDePasse != null ) {
            if ( motDePasse.length() < 3 ) {
                throw new Exception( "Le mot de passe doit contenir au moins 3 caractères." );
            }
        } else {
            throw new Exception( "Merci de saisir votre mot de passe." );
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    public String getResultat() {
        return resultat;
    }

}
