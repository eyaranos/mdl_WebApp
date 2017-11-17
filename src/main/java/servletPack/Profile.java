package servletPack;

import ConnecteurAPI.ConnectAPIUtilisateur;
import DAO.UtilisateurDao;
import Form.InscriptionForm;
import Form.ProfileForm;
import Utils.UserUtility;
import beans.Utilisateur;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Profile")
public class Profile extends HttpServlet {

    public static final String ATT_USER = "utilisateur";
    public static final String ATT_FORM = "form";
    public static final String VUE = "/RestrictAccess/Profile/Profile.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        ProfileForm form = new ProfileForm();

        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        Utilisateur utilisateur = form.updateUtilisateur(request);

        HttpSession session = request.getSession();

        //cast de l'obj du beans Utilisateur qui se trouve dans la session pour recup l'id du user
        Utilisateur user = UserUtility.getUserFromSession(request);
        int id = user.getId();
        utilisateur.setId(id);
        //-----------------------------------------------------------------------------------------

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, utilisateur );

        /* update des infos utilisateur dans la bd */
        ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur();
        try {
            connectAPIUtilisateur.updateUser(utilisateur);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userJson;
        Utilisateur utilisateur = new Utilisateur();
        Gson gson = new Gson();

        //TODO : gestion des reparateur
        //cast de l'obj du beans Utilisateur qui se trouve dans la session pour recup l'id du user
        Utilisateur user = UserUtility.getUserFromSession(request);
        int id = user.getId();
        //-----------------------------------------------------------------------------------------

        /* recup infos utilisateur dans la bd */
        ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur();
        try {
            userJson = connectAPIUtilisateur.getUser(id);
            //map l'objet json recup de la bd à notre beans utilisateur
            utilisateur = gson.fromJson(userJson, Utilisateur.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //envoi le beans utilisateur à la vue
        request.setAttribute( ATT_USER, utilisateur );

        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
    }
}
