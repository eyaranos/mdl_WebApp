package servletPack;

import ConnecteurAPI.ConnectAPIUtilisateur;
import Utils.UserUtility;
import beans.Utilisateur;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteCreditCard")
public class DeleteCreditCard extends HttpServlet {

    public static final String VUE = "/RestrictAccess/Profile/AddCreditCard.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Utilisateur user = UserUtility.getUserFromSession(request);
        ConnectAPIUtilisateur connectAPIUtilisateur=new ConnectAPIUtilisateur();
        String apiResponse = connectAPIUtilisateur.getUserCustomerId(user.getId());

        JSONObject obj = new JSONObject(apiResponse);
        String customerId = obj.getString("object");

        try{
            connectAPIUtilisateur.deleteCreditCard(customerId);
            request.setAttribute("okDeleteCard", "Votre carte a bien ete supprimée");
            this.getServletContext().getRequestDispatcher(VUE).forward(request, response );
        }
        catch(SQLException e){
            request.setAttribute("errorDeleteCard", e.getMessage());
            this.getServletContext().getRequestDispatcher(VUE).forward(request, response );
        }
    }
}
