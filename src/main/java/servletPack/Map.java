package servletPack;

import ConnecteurAPI.ConnectAPIUtilisateur;
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
        JSONObject obj = null;

        /* recup velos dans la bd */
        ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur();
        try {
            velosJson = connectAPIUtilisateur.getBikes();

            obj = new JSONObject(velosJson);

        } catch (Exception e) {
            e.printStackTrace();
        }

      //  System.out.println(obj.get("object").toString());

        //envoi des velos à la vue
        request.setAttribute( "liste_velo",obj.get("object").toString() );

        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
    }
}
