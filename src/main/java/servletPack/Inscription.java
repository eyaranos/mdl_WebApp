package servletPack;

import ConnecteurAPI.ConnectAPI;
import ConnecteurAPI.ConnectAPIUtilisateur;
import DAO.DAOFactory;
import DAO.UtilisateurDao;
import Form.InscriptionForm;
import beans.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Inscription")
public class Inscription extends HttpServlet {

    private UtilisateurDao utilisateurDao;

    public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";
    public static final String VUE = "/WEB-INF/Inscription.jsp";

    public void init()throws ServletException{
        DAOFactory daoFactory= DAOFactory.getInstance();
        this.utilisateurDao=daoFactory.getUtilisateurDao();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

         /* Préparation de l'objet formulaire */
        InscriptionForm form = new InscriptionForm();

        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        Utilisateur utilisateur = form.inscrireUtilisateur( request );

        /* Stockage du formulaire et du bean dans l'objet request */
        /*request.setAttribute( ATT_FORM, form );*/
        request.setAttribute( ATT_USER, utilisateur );

        /* ajout du nouvel utilisateur dans la bd */
        ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur();
        try {
            boolean inscriptionValide= connectAPIUtilisateur.insertUser(utilisateur);
            //Test si l'utilisateur a été ajouté dans la BD del'API
            if (inscriptionValide) {form.setResultat("Echec de l'inscription");}
        } catch (Exception e) {
            form.setErreur("email", "Email existe déjà");
            form.setResultat("Echec de l'inscription");
            e.printStackTrace();
        }
        request.setAttribute( ATT_FORM, form );

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
    }
}
