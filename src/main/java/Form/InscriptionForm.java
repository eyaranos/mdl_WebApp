package Form;

import beans.Utilisateur;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.http.HttpServletRequest;

public final class InscriptionForm {

    public static final String CHAMP_EMAIL = "email";
    public static final String CHAMP_PASS = "mdp";
    public static final String CHAMP_CONF = "confirmation";
    public static final String CHAMP_NOM = "nom";
    public static final String CHAMP_PRENOM = "prenom";
    public static final String CHAMP_VILLE = "ville";
    public static final String CHAMP_ADDRESS = "adresse";
    public static final String CHAMP_ZIP = "code_postal";
    public static final String CHAMP_PAYS = "pays";
    public static final String CHAMP_TEL = "num_tel";
    public static final String CHAMP_NAISSANCE = "date_naissance";

    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
    public String getResultat() {
        return resultat;
    }
    public Map<String, String> getErreurs() {
        return erreurs;
    }
    private String generatedSecuredPasswordHash;

    public Utilisateur inscrireUtilisateur(HttpServletRequest request) {

        String email = getValeurChamp(request, CHAMP_EMAIL);
        String motDePasse = getValeurChamp(request, CHAMP_PASS);
        String confirmation = getValeurChamp(request, CHAMP_CONF);
        String nom = getValeurChamp(request, CHAMP_NOM);
        String prenom = getValeurChamp(request, CHAMP_PRENOM);
        String ville = getValeurChamp(request, CHAMP_VILLE);
        String adresse = getValeurChamp(request, CHAMP_ADDRESS);
        String code_postal = getValeurChamp(request, CHAMP_ZIP);
        String pays = getValeurChamp(request, CHAMP_PAYS);
        String num_tel = getValeurChamp(request, CHAMP_TEL);
        //String date_naissance =  getValeurChamp(request, CHAMP_NAISSANCE);

        Utilisateur utilisateur = new Utilisateur();

        try {
            validationEmail(email);
        } catch (Exception e) {
            setErreur(CHAMP_EMAIL, e.getMessage());
        }
        utilisateur.setEmail(email);

        try {
            validationMotsDePasse(motDePasse, confirmation);
        } catch (Exception e) {
            setErreur(CHAMP_PASS, e.getMessage());
            setErreur(CHAMP_CONF, null);
        }
        //---------HASH du PWD--------------------------------------
        try{
            generatedSecuredPasswordHash = Utils.hashPassword.generateStrongPasswordHash(motDePasse);
        }
        catch(NoSuchAlgorithmException a){
            a.printStackTrace();
        }
        catch(InvalidKeySpecException i){
            i.printStackTrace();
        }
        utilisateur.setMdp(generatedSecuredPasswordHash);

        utilisateur.setAdresse(adresse);
        utilisateur.setCodePostal(code_postal);
        //utilisateur.setDateNaissance(date_naissance);
        utilisateur.setNumTel(num_tel);
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setPays(pays);
        utilisateur.setVille(ville);


        if (erreurs.isEmpty()) {
            resultat = "Succès de l'inscription.";
        } else {
            resultat = "Échec de l'inscription.";
        }

        return utilisateur;
    }


    private void validationEmail(String email) throws Exception {
        if (email != null) {
            if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
                throw new Exception("Merci de saisir une adresse mail valide.");
            }
        } else {
            throw new Exception("Merci de saisir une adresse mail.");
        }
    }

    private void validationMotsDePasse(String motDePasse, String confirmation) throws Exception {
        if (motDePasse != null && confirmation != null) {
            if (!motDePasse.equals(confirmation)) {
                throw new Exception("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
            } else if (motDePasse.length() < 3) {
                throw new Exception("Les mots de passe doivent contenir au moins 3 caractères.");
            }
        } else {
            throw new Exception("Merci de saisir et confirmer votre mot de passe.");
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    public void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    /*
         * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
         * sinon.
         */
    private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
        String valeur = request.getParameter(nomChamp);
        if (valeur == null || valeur.trim().length() == 0) {
            return null;
        } else {
            return valeur.trim();
        }
    }
}