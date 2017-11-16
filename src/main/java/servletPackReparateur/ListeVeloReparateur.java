package servletPackReparateur;

import ConnecteurAPI.ConnectAPIReparateur;
import Utils.UserUtility;
import beans.Reparateur;
import beans.Utilisateur;
import beans.Velo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

@WebServlet(name = "ListeVeloReparateur")
public class ListeVeloReparateur extends HttpServlet {


    public static final String VUE = "/RestrictAccess/Reparateur/ListeVeloReparateur.jsp";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        //recup le reparateur en session
        Reparateur employe = UserUtility.getReparateurFromSession(request);
        //connection + query l'API
        ConnectAPIReparateur connectAPIReparateur = new ConnectAPIReparateur();
        String apiResponse = connectAPIReparateur.getIdCentreRepa(employe.getId());
        //converti string response en json response
        JSONObject jsonResponse = new JSONObject(apiResponse);
        //recup de l'idée en allant chercher la bonne key qui contient notre id
        int id_centre = Integer.valueOf(jsonResponse.getString("object"));
        //query l'API avec cet id
        String listeVeloToFix = connectAPIReparateur.getBikesToFix(id_centre);

        JSONObject obj = new JSONObject(listeVeloToFix);
        JSONArray array = obj.getJSONArray("object");


        List<Velo> listeVelo = new ArrayList<Velo>();

        for(int i=0;i<array.length();i++){

             JSONObject veloJson = array.getJSONObject(i);
             Velo velo = new Gson().fromJson(veloJson.toString(), Velo.class);
              listeVelo.add(velo);
        }

        request.setAttribute("liste_velos", listeVelo);


        //TODO : figure it out => either send back to JSP the JSON list OR mapping b\ listVelo and beans Velo to send back object
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
}
