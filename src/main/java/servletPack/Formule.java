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
    private String erreurs = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //recup les variables dont on aura besoin
        int id_formule = Integer.valueOf(request.getParameter("id_formule"));
        Utilisateur user = Utils.UserUtility.getUserFromSession(request);
        ConnectAPIUtilisateur connectAPIUtilisateur = new ConnectAPIUtilisateur(user.getAuth());

        /*----------------- INSERT dans la table abonnement -------------------*/
        try{
            connectAPIUtilisateur.insertAbonnement(user.getId(), id_formule);
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        /*---------------------- GET le customerId de la DB ---------------------*/
        String apiResponse = connectAPIUtilisateur.getUserCustomerId(user.getId());
        JSONObject jsonResponse = new JSONObject(apiResponse);
        String customerId = jsonResponse.getString("object");
        /*------------------------------------------------------------------------*/

        /*--------------------------- GET la formule demandé par le client --------*/
        String apiResponse2 = connectAPIUtilisateur.getFormule(id_formule);
        JSONObject jsonResponse2 = new JSONObject(apiResponse2);
        JSONObject formule = jsonResponse2.getJSONObject("object");
        Double pi = formule.getDouble("prix") * 10;
        String tempPrix = String.valueOf(pi);
        String ifd = tempPrix.replace(".","");
        int prix = Integer.valueOf(ifd);
        /*-------------------------------------------------------------------------*/

        /*--------------------- On fait payer le client -------------------------*/
        Stripe.apiKey = "sk_test_INynjhmwE2v6sEuUl19b8mIr";

        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", prix);
        chargeParams.put("currency", "eur");
        chargeParams.put("customer", customerId);

        try{
            Charge charge = Charge.create(chargeParams);
        }catch(AuthenticationException a){
            this.erreurs = "Une erreur est survenue, veuillez réessayer 1";
        }catch(InvalidRequestException i){
            this.erreurs = "Une erreur est survenue, veuillez réessayer 2";
        }catch(APIConnectionException p){
            this.erreurs = "Une erreur est survenue, veuillez réessayer 3";
        }catch(CardException c){
            this.erreurs = "Une erreur est survenue, veuillez réessayer 4";
        }catch(APIException e) {
            this.erreurs = "Une erreur est survenue, veuillez réessayer 5";
        }

        if (this.erreurs == null){

            response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("200");       // Write response body.
        }
        else{
            response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("400");       // Write response body.
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Utilisateur user = Utils.UserUtility.getUserFromSession(request);
        ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur(user.getAuth());
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

        /*-------------------- Check if user has a valid credit card ----------------------*/
        String apiResponse2 = connectAPIUtilisateur.getUserCustomerId(user.getId());
        if (apiResponse2.equals("400")){
            request.setAttribute("noCreditCard", "Vous devez d'abord introduire une carte de crédit pour pouvoir souscrire un abonnement");
        }

        /*----------- Check if user already has a valid subscription ----------------*/
        String apiResponse3 = connectAPIUtilisateur.getActuelAbonnement(user.getId());
        if (!apiResponse3.equals("400")){
            JSONObject obj3 = new JSONObject(apiResponse3);
            int reponse = obj3.getInt("object");
            if (reponse == 0){
                request.setAttribute("erreur_abonnement", 1);
            }
            else{
                request.setAttribute("erreur_abonnement", 0);
            }
        }
        this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
    }
}
