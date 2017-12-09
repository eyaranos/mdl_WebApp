package servletPack;

import ConnecteurAPI.ConnectAPIUtilisateur;
import Utils.UserUtility;
import beans.Utilisateur;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.ExternalAccount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UpdateCreditCard")
public class UpdateCreditCard extends HttpServlet {

    public static final String VUE = "/RestrictAccess/Profile/UpdateCreditCard.jsp";
    public static final String REDIRECT = "/RestrictAccess/Profile/AddCreditCard.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utilisateur user = UserUtility.getUserFromSession(request);
        Customer customer = null;

        ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur(user.getAuth());
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

        List<ExternalAccount> carteTemp = customer.getSources().getData();
        Card carte = (Card)carteTemp.get(0);
        String fourLastDigit = carte.getLast4();


        try{
            if(connectAPIUtilisateur.updateCreditCard(customer.getId(),fourLastDigit).equals("ok")){
                this.getServletContext().getRequestDispatcher(REDIRECT).forward( request, response );
            }
            else{
                request.setAttribute("errorUpdateCard", "Un erreur est survenu lors de la mise a jour de votre carte...");
                this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
            }
        }catch(SQLException e){
            request.setAttribute("errorUpdateCard", "Un erreur est survenu lors de la mise a jour de votre carte...");
            this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
    }
}
