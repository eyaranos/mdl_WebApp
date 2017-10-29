package ConnecteurAPI;

import beans.Utilisateur;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * Created by François on 28-10-17.
 */
public class ConnectAPI {

    private final String USER_AGENT = "Mozilla/5.0";
    private String url_API ="http://188.226.146.41:8080/MDL-API-0.0.1-SNAPSHOT/restservices/UserController/";

    public void insertUser(Utilisateur utilisateur) throws Exception {

        String url=url_API+"insert/user/";

        URL object=new URL(url);

        JSONObject uJson   = new JSONObject();

        //Création de la connection à l'API
        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestMethod("POST");


        //Création du JSONObject à envoyer à l'API avec les info de l'utilisateur à ajouter

        uJson.put("email",utilisateur.getEmail());
        uJson.put("nom",utilisateur.getNom());
        uJson.put("prénom",utilisateur.getPrenom());
        uJson.put("mdp",utilisateur.getMdp());


        //Envoie de l'objetJSON à l'API
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(uJson.toString());
        wr.flush();

        showBackMessage(con);

    }

    public boolean checkConnectionUser(Utilisateur utilisateur) throws IOException {

        String url=url_API+"connexion/user";

        String urlParameters;


        URL object=new URL(url);

        //Création de la connection à l'API
        HttpURLConnection conn= (HttpURLConnection) object.openConnection();
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        conn.setRequestMethod( "POST" );
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty( "charset", "utf-8");
        conn.setUseCaches( false );

        //Création des params
        urlParameters="email="+utilisateur.getEmail();
        urlParameters=urlParameters+"&mdp="+utilisateur.getMdp();

        //Envoie des param à l'API (email et mdp)
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(urlParameters);
        wr.flush();

        String rep=showBackMessage(conn);

        //TODO à cahnger avec renvoie Utilisateur
        if( rep.equals("No Content")){
            return false;
        }else{
            return true;
        }



    }

    private String showBackMessage(HttpURLConnection con) throws IOException {
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
            return sb.toString();
        } else {
            System.out.println(con.getResponseMessage());
            return con.getResponseMessage();
        }
    }

}
