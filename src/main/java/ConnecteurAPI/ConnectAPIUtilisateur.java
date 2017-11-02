package ConnecteurAPI;

import beans.Utilisateur;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

/**
 * Created by François on 29-10-17.
 */
public class ConnectAPIUtilisateur {

    private ConnectAPI connectAPI;

    public ConnectAPIUtilisateur(){
        this.connectAPI =new ConnectAPI();
    }


    public void insertUser(Utilisateur utilisateur) throws SQLException, IOException {

        //Création de la connection à l'API
        HttpURLConnection con = this.connectAPI.connectAPIJSON("UserController/insert/user/", "POST");

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

        if (con.getResponseCode() == 400) throw new SQLException("Email existe deja");
        connectAPI.showBackMessage(con);
    }

    public void updateUser(Utilisateur utilisateur) throws Exception {

        //Création de la connection à l'API
        HttpURLConnection con = this.connectAPI.connectAPIJSON("update/user/", "POST");

        //Création du JSONObject à envoyer à l'API avec les info de l'utilisateur à ajouter
        JSONObject uJson   = new JSONObject();

        uJson.put("email",utilisateur.getEmail());
        uJson.put("nom",utilisateur.getNom());
        uJson.put("prénom",utilisateur.getPrenom());
        uJson.put("mdp",utilisateur.getMdp());
        uJson.put("id",utilisateur.getId());
        uJson.put("ville",utilisateur.getVille());
        uJson.put("adresse",utilisateur.getAdresse());
        uJson.put("codeP",utilisateur.getCodePostal());
        uJson.put("pays",utilisateur.getPays());
        uJson.put("num",utilisateur.getNumTel());


        //Envoie de l'objetJSON à l'API
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(uJson.toString());
        wr.flush();

        connectAPI.showBackMessage(con);
    }

    public String checkConnectionUser(Utilisateur utilisateur) throws IOException {

        //Création URL pour connection à l'API
        String url=connectAPI.getUrl_API()+"UserController/connexion/user";
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

        return rep;
    }

    public String getUser(int id) throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("UserController/get/user/"+id, "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }


    public String getBikes() throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("BikeController/get/bikes", "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }
}
