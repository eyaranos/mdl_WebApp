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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddCreditCard")
public class AddCreditCard extends HttpServlet {

    public static final String VUE = "/RestrictAccess/Profile/AddCreditCard.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utilisateur user = UserUtility.getUserFromSession(request);
        Customer customer = null;

        Stripe.apiKey = "sk_test_INynjhmwE2v6sEuUl19b8mIr";
        String token = request.getParameter("stripeToken");

        //get just the 4 last number of the card number to zllow user to know which card is currently active
        String cardNumber = request.getParameter("cardnumber");
        String fourLastDigit = cardNumber.substring(cardNumber.length()-4, cardNumber.length()-1);


        // Create a Customer:
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", user.getEmail());
        customerParams.put("source", token);
        try{
            customer = Customer.create(customerParams);
        }catch(AuthenticationException a){

        }catch(InvalidRequestException i){

        }catch(APIConnectionException p){

        }catch(CardException c){

        }catch(APIException e){

        }

        //customerId insert into the db. To user to charge client in the future
        ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur();
        try{
            connectAPIUtilisateur.insertUserCreditCard(user.getId(),  customer.getId(),fourLastDigit);
            request.setAttribute("succes", "Votre carte a bien été ajoutée !");
        }catch(SQLException e){

        }

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);
    }
}
