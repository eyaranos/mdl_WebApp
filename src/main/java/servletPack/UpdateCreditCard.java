package servletPack;

import ConnecteurAPI.ConnectAPIUtilisateur;
import Utils.UserUtility;
import beans.Utilisateur;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "UpdateCreditCard")
public class UpdateCreditCard extends HttpServlet {

    public static final String VUE = "/RestrictAccess/Profile/UpdateCreditCard.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utilisateur user = UserUtility.getUserFromSession(request);
        Customer customer = null;

        ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur();
        //TODO : chck avec API ce qu'elle va renvoyer. Soit juste un string customerId soit beans Stripe_info from DB
        String customerId = connectAPIUtilisateur.getUserCustomerId(user.getId());

        Stripe.apiKey = "sk_test_INynjhmwE2v6sEuUl19b8mIr";
        String token = request.getParameter("stripeToken");


        Map<String, Object> updateParams = new HashMap<String, Object>();
        updateParams.put("source", token);

        try{
            customer = Customer.retrieve(customerId);
            customer.update(updateParams);

        }catch(AuthenticationException a){

        }catch(InvalidRequestException i){

        }catch(APIConnectionException p){

        }catch(CardException c){

        }catch(APIException e){

        }

        //TODO : figure out what feedback to display to the user
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
    }
}
