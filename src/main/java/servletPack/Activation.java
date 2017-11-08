package servletPack;

import ConnecteurAPI.ConnectAPIUtilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Activation")
public class Activation extends HttpServlet {

    public static final String VUE = "/WEB-INF/Activation.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String token = request.getParameter("token");

        ConnectAPIUtilisateur connectAPIUtilisateur =new ConnectAPIUtilisateur();
        String activatedUser = connectAPIUtilisateur.getUserActivated(token);

        if (!activatedUser.equals("Introuvable")){
            request.setAttribute("resultActivation", "Votre compte a bien été activé ! Vous pouvez maitenant vous connectez");
        }
        else{
            request.setAttribute("resultActivation", "Une erreur est survenu dans l'activation de votre compte! ");
        }

        this.getServletContext().getRequestDispatcher(VUE).forward(request,response);

    }
}
