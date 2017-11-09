package servletPack;

import ConnecteurAPI.ConnectAPIUtilisateur;
import beans.Utilisateur;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet(name = "Accueil")
public class Accueil extends HttpServlet {


    public static final String  COOKIE_DERNIERE_CONNEXION = "derniereConnexion";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userJson;
        Utilisateur utilisateur = new Utilisateur();
        Gson gson = new Gson();

        //--------------- VERIF ALREADY CONNECTED -------------------------//
        /* Tentative de récupération du cookie depuis la requête */
        String derniereConnexion = getCookieValue( request, COOKIE_DERNIERE_CONNEXION );

         //Si le cookie existe, alors on log user
        if ( derniereConnexion != null ) {
            //recup id en int
            int id = Integer.parseInt(derniereConnexion);
            //crée la session
            HttpSession session = request.getSession();
            //recup le user basé sur son id via l'api
            ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur();
            userJson = connectAPIUtilisateur.getUser(id);
            //map l'objet json recup de la bd à notre beans utilisateur
            utilisateur = gson.fromJson(userJson, Utilisateur.class);

            session.setAttribute(ATT_SESSION_USER, utilisateur);
        }
        //-------------------------- END ----------------------------------------------//

        this.getServletContext().getRequestDispatcher("/WEB-INF/Accueil.jsp").forward(request,response);
    }

    /**
     * Méthode utilitaire gérant la récupération de la valeur d'un cookie donné
     * depuis la requête HTTP.
     */
    private static String getCookieValue( HttpServletRequest request, String nom ) {
        Cookie[] cookies = request.getCookies();
        if ( cookies != null ) {
            for ( Cookie cookie : cookies ) {
                if ( cookie != null && nom.equals( cookie.getName() ) ) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
