package servletPack;

import ConnecteurAPI.ConnectAPIUtilisateur;
import beans.Utilisateur;
import beans.VeloToFix;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Abonnement")
public class Abonnement extends HttpServlet {

    public static final String VUE = "/RestrictAccess/Profile/Abonnement.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utilisateur user = Utils.UserUtility.getUserFromSession(request);
        ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur();
        List<beans.Abonnement> listeAbos = new ArrayList<beans.Abonnement>();

        String apiResponse = connectAPIUtilisateur.getAbonnements(user.getId());
        if(apiResponse.equals("400")){
           request.setAttribute("listeAbos", listeAbos);
        }
        else{
            JSONObject obj = new JSONObject(apiResponse);
            JSONArray array = obj.getJSONArray("object");

            for(int i=0;i<array.length();i++) {

                JSONObject aboJson = array.getJSONObject(i);
                beans.Abonnement abo = new Gson().fromJson(aboJson.toString(), beans.Abonnement.class);

                listeAbos.add(abo);
            }
            request.setAttribute("listeAbos", listeAbos);
        }

        //todo : write method getAbonnementEnCours(id_client) pour eviter de permettre à un utilisateur de prendre un abonemment
        //todo :    alors qu'il en a encore un

        this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
    }
}
