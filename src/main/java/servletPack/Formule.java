package servletPack;

import ConnecteurAPI.ConnectAPIUtilisateur;
import Utils.UserUtility;
import beans.Abonnement;
import beans.Utilisateur;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Formule")
public class Formule extends HttpServlet {

    public static final String VUE = "/RestrictAccess/Profile/Formule.jsp";
    public static final String REDIRECT = "/RestrictAccess/Profile/Abonnement.jsp";
    private String erreurs;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //recup les variables dont on aura besoin
        int id_formule = Integer.valueOf(request.getParameter("id_formule"));
        Utilisateur user = Utils.UserUtility.getUserFromSession(request);
        ConnectAPIUtilisateur connectAPIUtilisateur = new ConnectAPIUtilisateur();

        /*----------------- INSERT dans la table abonnement -------------------*/
        try{
            connectAPIUtilisateur.insertAbonnement(user.getId(), id_formule);
        }
        catch(SQLException e){
            this.erreurs = "Une erreur est survenue, veuillez réessayer";
        }

        /*---------------------- GET le customerId de la DB ---------------------*/
        String apiResponse = connectAPIUtilisateur.getUserCustomerId(user.getId());
        JSONObject jsonResponse = new JSONObject(apiResponse);
        String customerId = jsonResponse.getString("object");
        /*------------------------------------------------------------------------*/

        /*--------------------------- GET la formule demandé par le client --------*/
        String apiResponse2 = connectAPIUtilisateur.getFormule(id_formule);
        JSONObject jsonResponse2 = new JSONObject(apiResponse2);
        beans.Formule formule = new Gson().fromJson(jsonResponse2.toString(), beans.Formule.class);
        /*-------------------------------------------------------------------------*/

        /*--------------------- On fait payer le client -------------------------*/
        Stripe.apiKey = "sk_test_INynjhmwE2v6sEuUl19b8mIr";

        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", formule.getPrix());
        chargeParams.put("currency", "eur");
        chargeParams.put("customer", customerId);

        try{
            Charge charge = Charge.create(chargeParams);
        }catch(AuthenticationException a){
            this.erreurs = "Une erreur est survenue, veuillez réessayer";
        }catch(InvalidRequestException i){
            this.erreurs = "Une erreur est survenue, veuillez réessayer";
        }catch(APIConnectionException p){
            this.erreurs = "Une erreur est survenue, veuillez réessayer";
        }catch(CardException c){
            this.erreurs = "Une erreur est survenue, veuillez réessayer";
        }catch(APIException e) {
            this.erreurs = "Une erreur est survenue, veuillez réessayer";
        }

        if (this.erreurs != null){
            this.getServletContext().getRequestDispatcher(REDIRECT).forward( request, response );
        }
        else{
            request.setAttribute("erreur-abonnement", this.erreurs);
            this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Utilisateur user = Utils.UserUtility.getUserFromSession(request);
        ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur();
        List<beans.Formule> listeFormules = new ArrayList<beans.Formule>();

        String apiResponse = connectAPIUtilisateur.getFormules();
        if(apiResponse.equals("400")){
            request.setAttribute("listeFormules", listeFormules);
        }
        else{
            JSONObject obj = new JSONObject(apiResponse);
            JSONArray array = obj.getJSONArray("object");

            for(int i=0;i<array.length();i++) {

                JSONObject formuleJson = array.getJSONObject(i);
                beans.Formule formule = new Gson().fromJson(formuleJson.toString(), beans.Formule.class);

                listeFormules.add(formule);
            }
            request.setAttribute("listeFormules", listeFormules);
        }

        String apiResponse2 = connectAPIUtilisateur.getUserCustomerId(user.getId());
        JSONObject obj = new JSONObject(apiResponse2);

        if(obj.get("object") == null){
            request.setAttribute("noCreditCard", "Vous devez d'abord introduire une carte de crédit pour pouvoir souscrire un abonnement");
        }

        request.setAttribute("erreur-abonnement", "");
        this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
    }
}
