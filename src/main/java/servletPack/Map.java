package servletPack;

import ConnecteurAPI.ConnectAPIUtilisateur;
import Utils.UserUtility;
import beans.Utilisateur;
import beans.Velo;
import com.google.gson.Gson;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Map")
public class Map extends HttpServlet {

    public static final String VUE              = "/RestrictAccess/Map.jsp";




    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String velosJson = null;
        String zonesJson = null;
        JSONObject obj = null;
        JSONObject obj2 = null;
        Utilisateur user = UserUtility.getUserFromSession(request);

        /* recup velos dans la bd */
        ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur(user.getAuth());
        try {
            velosJson = connectAPIUtilisateur.getBikes();
            obj = new JSONObject(velosJson);
            //envoi des velos à la vue
            request.setAttribute( "liste_velo",obj.get("object").toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*recup des zone de lock dans la bd*/
        try{
            zonesJson = connectAPIUtilisateur.getZoneLock();
            obj2 = new JSONObject(zonesJson);
            //envoi des zone à la vue
            request.setAttribute( "liste_zone",obj2.get("object").toString());

        } catch(Exception e){
            e.printStackTrace();
        }

        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
    }
}
