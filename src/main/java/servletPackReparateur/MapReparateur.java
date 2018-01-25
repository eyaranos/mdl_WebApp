package servletPackReparateur;

import ConnecteurAPI.ConnectAPIReparateur;
import ConnecteurAPI.ConnectAPIUtilisateur;
import Utils.UserUtility;
import beans.Reparateur;
import beans.Utilisateur;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MapReparateur")
public class MapReparateur extends HttpServlet {

    public static final String VUE              = "/RestrictAccess/Reparateur/MapReparateur.jsp";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Reparateur user = UserUtility.getReparateurFromSession(request);

        /* recup velos disponibles dans la bd */
        ConnectAPIReparateur connectAPIReparateur=new ConnectAPIReparateur(user.getAuth());
        try {
            String velosJson = connectAPIReparateur.getBikes();
            JSONObject obj = new JSONObject(velosJson);
            //envoi des velos à la vue
            request.setAttribute( "liste_velo",obj.get("object").toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*recup des zone de lock dans la bd*/
        try{
            String zonesJson = connectAPIReparateur.getZoneLock();
            JSONObject obj2 = new JSONObject(zonesJson);
            //envoi des zone à la vue
            request.setAttribute( "liste_zone",obj2.get("object").toString());

        } catch(Exception e){
            e.printStackTrace();
        }

         /*recup des stations dans la bd*/
        try{
            String stationsJson = connectAPIReparateur.getStations();
            if(!stationsJson.equals("404") && !stationsJson.equals("400")){
                JSONObject obj3 = new JSONObject(stationsJson);
                //envoi des zone à la vue
                request.setAttribute( "liste_station",obj3.get("object").toString());
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        /* recup velos cassés dans la bd */
        try {
            String velosCasserJson = connectAPIReparateur.getBrokenBikes();
            if(!velosCasserJson.equals("404") && !velosCasserJson.equals("400")) {
                JSONObject obj = new JSONObject(velosCasserJson);
                //envoi des velos à la vue
                request.setAttribute("liste_velo_casser", obj.get("object").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
    }
}
