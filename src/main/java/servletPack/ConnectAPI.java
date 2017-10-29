package servletPack;

import beans.Utilisateur;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by François on 28-10-17.
 */
public class ConnectAPI {
    private final String USER_AGENT = "Mozilla/5.0";
    public void insertUser(Utilisateur utilisateur) throws Exception {

        String url="http://localhost:8080/restservices/UserController/insert/user";

        URL object=new URL(url);

        JSONObject uJson   = new JSONObject();

        //Création de la connection à l'API
        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");

        System.out.println("test");
        //Création du JSONObject à envoyer à l'API avec les info de l'utilisateur à ajouter
        


        System.out.println(utilisateur.getPrenom());
        System.out.println(utilisateur.getNom());
        System.out.println(utilisateur.getEmail());
        System.out.println(utilisateur.getMdp());

        uJson.put("email",utilisateur.getEmail());
        uJson.put("nom",utilisateur.getNom());
        uJson.put("prénom",utilisateur.getPrenom());
        uJson.put("mdp",utilisateur.getMdp());

        System.out.println(uJson.toString());

        System.out.println(uJson.toString());
        System.out.println("test");
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(uJson.toString());
        wr.flush();
        System.out.println("test");

        //display what returns the POST request

        StringBuilder sb = new StringBuilder();
        int HttpResult = con.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.out.println("" + sb.toString());
        } else {
            System.out.println(con.getResponseMessage());
        }


    }

}
