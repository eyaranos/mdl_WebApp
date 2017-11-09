package servletPack;

import ConnecteurAPI.ConnectAPIUtilisateur;
import Utils.EmailUtility;
import beans.Utilisateur;
import com.google.gson.Gson;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "ForgetPassword")
public class ForgetPassword extends HttpServlet {

    public static final String VUE  = "/WEB-INF//ForgetPassword.jsp";
    public static final String CHAMP_EMAIL = "email";
    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();

        try {
            validationEmail(request.getParameter(CHAMP_EMAIL));
        } catch (Exception e) {
            setErreur(CHAMP_EMAIL, e.getMessage());
        }


        //---envoi du mail de confirmation------------------------//
        // reads SMTP server setting from web.xml file
        ServletContext context = getServletContext();
        String host = context.getInitParameter("host");
        String port = context.getInitParameter("port");
        String username = context.getInitParameter("user");
        String pass = context.getInitParameter("pass");

        //-- generation d'un nouveau mot de passe --//
        String newPwd = UUID.randomUUID().toString();

        // reads form fields
        String recipient = request.getParameter(CHAMP_EMAIL);
        String subject = "Récupération de votre mot de passe - Velib";
        String content = "Vous pouvez vous connecter à votre compte avec ce nouveau mot de passe : "+newPwd;

        try {
            EmailUtility.sendEmail(host, port, username, pass, recipient, subject,
                    content);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //--------------------end send mail ----------------------------------////

        //--- traitement en DB : update le nouveau password de l'user
        Utilisateur user = new Utilisateur();
        user.setEmail(recipient);
        user.setMdp("tempPass");
        ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur();
        //todo : check si la reponse recue est un 404 (= user pas trouvé car email pas nexistant dans le systeme)
        String infoUser = connectAPIUtilisateur.checkConnectionUser(user);
        user = gson.fromJson(infoUser, Utilisateur.class);

        //------------ hash du new temp password + update de l'user en db
        try{
           String generatedSecuredPasswordHash = Utils.hashPassword.generateStrongPasswordHash(newPwd);
            user.setMdp(generatedSecuredPasswordHash);
            connectAPIUtilisateur.updateUser(user);
        }
        catch(NoSuchAlgorithmException a){
            a.printStackTrace();
        }
        catch(InvalidKeySpecException i){
            i.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        if (erreurs.isEmpty()) {
            resultat = "Un email vous a bien été envoyé.";
        } else {
            resultat = "Échec de la réinitialisation de votre mot de passe";
        }

        request.setAttribute( "erreurs", erreurs );
        request.setAttribute( "resultat", resultat );

        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
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

    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }
}
