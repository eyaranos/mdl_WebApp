package servletPack;

import Form.ConnexionForm;
import beans.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "connection")
public class Connection extends HttpServlet {

    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_FORM         = "form";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String VUE              = "/WEB-INF/Connection.jsp";
    public static final String VUE_AFTER_CONN   = "/WEB-INF/Accueil.jsp";
    public static final String  CHAMP_MEMOIRE             = "memoire";
    public static final int     COOKIE_MAX_AGE            = 60 * 60 * 24 * 365;  // 1 an
    public static final String  COOKIE_DERNIERE_CONNEXION = "derniereConnexion";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        /* Préparation de l'objet formulaire */
        ConnexionForm form = new ConnexionForm();

        /* Traitement de la requête et récupération du bean en résultant */
        Utilisateur utilisateur = form.connecterUtilisateur( request );

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /**
         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
         * Utilisateur à la session, sinon suppression du bean de la session.
         */
        if ( form.getErreurs().isEmpty() ) {
            session.setAttribute(ATT_SESSION_USER, utilisateur);
        } else {
            session.setAttribute( ATT_SESSION_USER, null);
        }

            /* Si et seulement si la case du formulaire est cochée */
        if ( request.getParameter( CHAMP_MEMOIRE ) != null ) {

        /* Création du cookie, et ajout à la réponse HTTP */
            setCookie( response, COOKIE_DERNIERE_CONNEXION, Integer.toString(utilisateur.getId()), COOKIE_MAX_AGE );
        } else {
        /* Demande de suppression du cookie du navigateur */
            setCookie( response, COOKIE_DERNIERE_CONNEXION, "", 0 );
        }

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, utilisateur );

        if ( form.getErreurs().isEmpty() ) {
            this.getServletContext().getRequestDispatcher(VUE_AFTER_CONN).forward( request, response );
        }
        else{
            this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
    }

    /*
 * Méthode utilitaire gérant la création d'un cookie et son ajout à la
 * réponse HTTP.
 */
    private static void setCookie( HttpServletResponse response, String nom, String valeur, int maxAge ) {
        Cookie cookie = new Cookie( nom, valeur );
        cookie.setMaxAge( maxAge );
        response.addCookie( cookie );
    }
}
