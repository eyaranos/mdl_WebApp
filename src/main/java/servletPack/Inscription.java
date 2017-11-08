package servletPack;

import ConnecteurAPI.ConnectAPI;
import ConnecteurAPI.ConnectAPIUtilisateur;
import DAO.DAOFactory;
import DAO.UtilisateurDao;
import Form.InscriptionForm;
import Utils.EmailUtility;
import beans.Utilisateur;

import javax.servlet.ServletContext;
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

        //---envoi du mail de confirmation------------------------//
        // reads SMTP server setting from web.xml file
        ServletContext context = getServletContext();
        String host = context.getInitParameter("host");
        String port = context.getInitParameter("port");
        String user = context.getInitParameter("user");
        String pass = context.getInitParameter("pass");

        // reads form fields
        String recipient = utilisateur.getEmail();
        String subject = "Confirmation de votre inscription sur Velib";
        String content = "Veuillez suivre ce lien pour activer votre compte http://localhost:8081/activation?token="+utilisateur.getToken();

        try {
            EmailUtility.sendEmail(host, port, user, pass, recipient, subject,
                    content);
        } catch (Exception ex) {
            ex.printStackTrace();
            form.setErreur("confirmation", "Un probleme est survenu dans l'envoie de la confirmation");
            form.setResultat("Echec de l'inscription");
        }
        //--------------------end send mail ----------------------------------////

        request.setAttribute( ATT_FORM, form );

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
    }
}
