package servletPackReparateur;

import ConnecteurAPI.ConnectAPIReparateur;
import Utils.UserUtility;
import beans.Reparateur;
import beans.Utilisateur;
import beans.Velo;
import beans.VeloToFix;
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
    private String msg;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //recup des variables nécessaire au process
        Reparateur reparateur = Utils.UserUtility.getReparateurFromSession(request);
        int id_employe = reparateur.getId();
        ConnectAPIReparateur connectAPIReparateur = new ConnectAPIReparateur();
        boolean isInsert = false;
        int id_velo;

        if(request.getParameter("type").equals("checkbox-repa-pending")){isInsert = true;}
        id_velo = Integer.valueOf(request.getParameter("idVelo"));

        /*-----------START PROCESS------------------*/
        if (isInsert) {

            JSONObject apiResponse = connectAPIReparateur.getIfBikeFixing(id_velo);

            if (apiResponse.getInt("status")== 400) {
                this.msg = "Un de vos collègue travaille déjà sur ce vélo";
            }
            else{
                if (!connectAPIReparateur.insertBikeFixing(id_employe, id_velo)) {
                    this.msg = "Une erreur est survenue...";
                }
                else{
                    this.msg = "Vous avez commencé à travailler sur le vélo "+id_velo;
                }
            }
        }
        else{
            if(!connectAPIReparateur.updateBikeTerminated(id_velo)){
                this.msg = "Une erreur est survenue...";
            }
            else{
                this.msg = "Vous avez terminé la réparation du vélo "+id_velo;
            }
        }

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(msg);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //recup le reparateur en session
        Reparateur employe = UserUtility.getReparateurFromSession(request);
        //connection + query l'API
        ConnectAPIReparateur connectAPIReparateur = new ConnectAPIReparateur();

        /*-------------------- PARTIE liste des velos dans un centre en particulier ---------------*/
        String apiResponse = connectAPIReparateur.getIdCentreRepa(employe.getId());
        //converti string response en json response
        JSONObject jsonResponse = new JSONObject(apiResponse);
        //recup de l'idée en allant chercher la bonne key qui contient notre id
        int id_centre = Integer.valueOf(jsonResponse.getString("object"));
        //query l'API avec cet id
        String listeVeloToFix = connectAPIReparateur.getBikesToFix(id_centre);
        //on converti la reponse string en objet json
        JSONObject obj = new JSONObject(listeVeloToFix);
        //on recupere la partie qui nous interesse, le table de velo
        JSONArray array = obj.getJSONArray("object");

        //nouvelle liste de velo que l'on renverra à la jsp
        List<VeloToFix> listeVelo = new ArrayList<VeloToFix>();

        for(int i=0;i<array.length();i++){

            JSONObject veloJson = array.getJSONObject(i);
            VeloToFix velo = new Gson().fromJson(veloJson.toString(), VeloToFix.class);

            /*-------------------- PARTIE informations sur un vélo particulier ---------------*/
            String tempResponse = connectAPIReparateur.getInfoReparation(velo.getId(), 2);
            if (tempResponse.equals("400") || tempResponse.equals("404")){
                String tempResponse2 = connectAPIReparateur.getInfoReparation(velo.getId(), 1);
                if (tempResponse2.equals("400") || tempResponse2.equals("404")){
                    velo.setReparateur(null); //cas C
                }
                else{
                    JSONObject objResponse2 = new JSONObject(tempResponse2);
                    Reparateur reparateur = new Gson().fromJson(objResponse2.getJSONObject("object").toString(), Reparateur.class);
                    velo.setReparateur(reparateur);  //cas B
                    velo.setType("terminated");
                }
            }
            else{
                JSONObject objResponse = new JSONObject(tempResponse);
                Reparateur reparateur = new Gson().fromJson(objResponse.getJSONObject("object").toString(), Reparateur.class);
                velo.setReparateur(reparateur); //cas A
                velo.setType("pending");
            }
            /*---------------------------------------------------------------------------------*/
            listeVelo.add(velo);
        }
        /*---------------------------------------------------------------------------------*/
        request.setAttribute("liste_velos", listeVelo);

        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
}
