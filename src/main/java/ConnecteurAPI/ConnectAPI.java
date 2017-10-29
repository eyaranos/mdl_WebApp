package ConnecteurAPI;

import beans.Utilisateur;
import org.json.JSONObject;


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
    /*private String url_API ="http://188.226.146.41:8080/MDL-API-0.0.1-SNAPSHOT/restservices/UserController/";*/
    private String url_API ="http://localhost:8080/restservices/UserController/";

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
            System.out.println(con.getResponseMessage());
            return con.getResponseMessage();
        }
    }

    /*
    Cette fonction va généré une connection avec l'API Rest en fonction de l'url et ce sera des objets JSON qui seront envoyés

    url = au type de requete que l'on veut faire à l'API
    method String "GET" ou "POST"
    Exemple : url ="/user/insert/"
    Return un HttpURLConnection connecté à l'API selon le type de requete, on peut envoyer des objet JSON avec cette connection

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
        con.setRequestMethod(method);

        return con;
    }

    public String getUrl_API() {
        return url_API;
    }
}
