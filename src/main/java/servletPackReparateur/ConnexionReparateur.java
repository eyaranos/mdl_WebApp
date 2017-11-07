package servletPackReparateur;

import FormReparateur.ConnexionFormReparateur;
import beans.Reparateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ConnexionReparateur")
public class ConnexionReparateur extends HttpServlet {

    public static final String ATT_USER         = "reparateur";
    public static final String ATT_FORM         = "form";
    public static final String ATT_SESSION_USER = "sessionReparateur";
    public static final String VUE              = "/WEB-INF/Connection_Repa.jsp";
    public static final String VUE_AFTER_CONN   = "/WEB-INF/Accueil.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        ConnexionFormReparateur form = new ConnexionFormReparateur();

        /* Traitement de la requête et récupération du bean en résultant */
        Reparateur reparateur = form.connecterReparateur( request );

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /**
         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
         * Utilisateur à la session, sinon suppression du bean de la session.
         */
        if ( form.getErreurs().isEmpty() ) {
            session.setAttribute( ATT_SESSION_USER, reparateur );
        } else {
            session.setAttribute( ATT_SESSION_USER, null );
        }

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, reparateur );

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
}