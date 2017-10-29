package ConnecteurAPI;

import beans.Utilisateur;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by François on 29-10-17.
 */
public class ConnectAPIUtilisateur {

    private ConnectAPI connectAPI;

    public ConnectAPIUtilisateur(){
        this.connectAPI =new ConnectAPI();
    }


    public void insertUser(Utilisateur utilisateur) throws Exception {


        //Création de la connection à l'API

        HttpURLConnection con = this.connectAPI.connectAPIJSON("insert/user/");

        //Création du JSONObject à envoyer à l'API avec les info de l'utilisateur à ajouter
        JSONObject uJson   = new JSONObject();

        uJson.put("email",utilisateur.getEmail());
        uJson.put("nom",utilisateur.getNom());
        uJson.put("prénom",utilisateur.getPrenom());
        uJson.put("mdp",utilisateur.getMdp());


        //Envoie de l'objetJSON à l'API
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(uJson.toString());
        wr.flush();

        connectAPI.showBackMessage(con);

    }

    public boolean checkConnectionUser(Utilisateur utilisateur) throws IOException {

        //Création URL pour connection à l'API
        String url=connectAPI.getUrl_API()+"connexion/user";
        URL object=new URL(url);

        //Création de la connection à l'API
        HttpURLConnection conn= (HttpURLConnection) object.openConnection();
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        conn.setRequestMethod( "POST" );
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty( "charset", "utf-8");
        conn.setUseCaches( false );

        //Création des params pour l'url
        String urlParameters;
        urlParameters="email="+utilisateur.getEmail();
        urlParameters=urlParameters+"&mdp="+utilisateur.getMdp();

        //Envoie des param à l'API (email et mdp)
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(urlParameters);
        wr.flush();

        String rep=connectAPI.showBackMessage(conn);

        //TODO à cahnger avec renvoie Utilisateur
        if( rep.equals("No Content")){
            return false;
        }else{
            return true;
        }



    }
}
