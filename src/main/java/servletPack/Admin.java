package servletPack;

import ConnecteurAPI.ConnectAPIReparateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Admin")
public class Admin extends HttpServlet {

    public static final String VUE = "/RestrictAccess/Admin.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ConnectAPIReparateur connectAPIReparateur = new ConnectAPIReparateur();

        String param = request.getParameter("name");
        String reponse;

        if(param.equals("startAlgo")){
          reponse = connectAPIReparateur.startAlgo();
        }
        else if(param.equals("getTrajet")){
            reponse = connectAPIReparateur.getTrajets();
        }
        else if(param.equals("getNbClient")) {
            reponse = connectAPIReparateur.getNbClient();
        }
        else if(param.equals("getNbClientAbo")) {
            reponse = connectAPIReparateur.getNbClientAbo();
        }
        else if(param.equals("getAllVeloForStation")) {
            reponse = connectAPIReparateur.getAllVeloForStation();
        }
        else{
            reponse = connectAPIReparateur.getEmploye();
        }

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(reponse);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
    }
}
