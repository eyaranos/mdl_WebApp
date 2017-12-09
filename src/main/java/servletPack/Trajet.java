package servletPack;

import ConnecteurAPI.ConnectAPIUtilisateur;
import beans.Utilisateur;
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

@WebServlet(name = "Trajet")
public class Trajet extends HttpServlet {

    public static final String VUE = "/RestrictAccess/Profile/Trajet.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utilisateur user = Utils.UserUtility.getUserFromSession(request);
        ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur(user.getAuth());
        List<beans.Trajet> listeTrajet = new ArrayList<beans.Trajet>();

        String apiResponse = connectAPIUtilisateur.getTrajets(user.getId());
        if(apiResponse.equals("400")){
           request.setAttribute("listeTrajets", listeTrajet);
        }
        else{
            JSONObject obj = new JSONObject(apiResponse);
            JSONArray array = obj.getJSONArray("object");

            for(int i=0;i<array.length();i++) {

                JSONObject trajetJson = array.getJSONObject(i);
                beans.Trajet trajet = new Gson().fromJson(trajetJson.toString(), beans.Trajet.class);

                listeTrajet.add(trajet);
            }
            request.setAttribute("listeTrajets", listeTrajet);
        }

        this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
    }
}
