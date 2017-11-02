package ConnecteurAPI;

import beans.Reparateur;
import beans.Utilisateur;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectAPIReparateur {
    private ConnectAPI connectAPI;

    public ConnectAPIReparateur(){
        this.connectAPI =new ConnectAPI();
    }


    public void insertReparateur(Reparateur reparateur) throws Exception {

        //Création de la connection à l'API
        HttpURLConnection con = this.connectAPI.connectAPIJSON("insert/reparateur/", "POST");

        //Création du JSONObject à envoyer à l'API avec les info de l'reparateur à ajouter
        JSONObject json   = new JSONObject();

        json.put("email",reparateur.getEmail());
        json.put("nom",reparateur.getNom());
        json.put("prénom",reparateur.getPrenom());
        json.put("mdp",reparateur.getMdp());

        //Envoie de l'objetJSON à l'API
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(json.toString());
        wr.flush();

        connectAPI.showBackMessage(con);
    }

    public String checkConnectionUser(Reparateur reparateur) throws IOException {

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
        urlParameters="email="+reparateur.getEmail();
        urlParameters=urlParameters+"&mdp="+reparateur.getMdp();

        //Envoie des param à l'API (email et mdp)
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(urlParameters);
        wr.flush();

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }





}
