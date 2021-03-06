package ConnecteurAPI;

import beans.Utilisateur;
import org.json.JSONObject;


import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
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
    //url à changer en fonction de si on se connecte en local à l'API rest ou sur le serveur en ligne
    private String url_API ="http://188.226.175.148:8080/MDL-API-0.0.1-SNAPSHOT/restservices/";
   //private String url_API ="http://localhost:8080/restservices/";
    private String auth;


    public ConnectAPI(String auth){
        this.auth = auth;
    }


    public ConnectAPI(){ }

    /*
    Affiche dans le terminal le message recu dans la HttpURLConnection con
     */
    public String showBackMessage(HttpURLConnection con) throws IOException {
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
            System.out.println(con.getResponseCode());
            return String.valueOf(con.getResponseCode());
        }
    }

    /**
    Cette fonction va généré une connection avec l'API Rest en fonction de l'url et ce sera des objets JSON qui seront envoyés

    @param url String = url correspondante à la requete désirée sur l'API (Exemple : url ="UserController/user/insert/")
    @param method String = "GET" ou "POST"
    @return HttpURLConnection connecté à l'API selon le type de requete, on peut envoyer des objet JSON avec cette connection

     */

    public HttpURLConnection connectAPIJSON(String url, String method ) throws IOException {
        url=url_API+url;

        URL object=new URL(url);

        //Création de la connection à l'API
        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        //token de sécurité pour accéder à l'API rest
        con.setRequestProperty("Authorization", getAuth() );
        con.setRequestMethod(method);


        return con;
    }

    public String getUrl_API() {
        return url_API;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
