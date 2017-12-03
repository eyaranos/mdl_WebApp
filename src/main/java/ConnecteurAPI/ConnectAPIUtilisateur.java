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


    /*
    Envoie dans un objet JSOn les données de l'utilisateur
    return true si l'API a ajouté l'utilisateur à la db
    Sinon false
     */
    public boolean insertUser(Utilisateur utilisateur) throws SQLException, IOException {

        //Création de la connection à l'API
        HttpURLConnection con = this.connectAPI.connectAPIJSON("UserController/insert/user/", "POST");

        //Création du JSONObject à envoyer à l'API avec les info de l'utilisateur à ajouter
        JSONObject uJson   = new JSONObject();

        uJson.put("email",utilisateur.getEmail());
        uJson.put("nom",utilisateur.getNom());
        uJson.put("prenom",utilisateur.getPrenom());
        uJson.put("mdp",utilisateur.getMdp());
        uJson.put("id",utilisateur.getId());
        uJson.put("ville",utilisateur.getVille());
        uJson.put("adresse",utilisateur.getAdresse());
        uJson.put("codeP",utilisateur.getCodePostal());
        uJson.put("pays",utilisateur.getPays());
        uJson.put("num",utilisateur.getNum());
        uJson.put("token",utilisateur.getToken());

        //Envoie de l'objetJSON à l'API
        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(uJson.toString());
        wr.flush();
/*
        if (con.getResponseCode() == 400) throw new SQLException("Email existe deja");*/
        String rep= connectAPI.showBackMessage(con);

        if (con.getResponseCode() == 400){
            throw new SQLException("Email existe deja");
        }
        else{
            return true;
        }
    }

    public void updateUser(Utilisateur utilisateur) throws Exception {

        //Création de la connection à l'API
        HttpURLConnection con = this.connectAPI.connectAPIJSON("UserController/update/user/", "POST");

        //Création du JSONObject à envoyer à l'API avec les info de l'utilisateur à ajouter
        JSONObject uJson   = new JSONObject();

        uJson.put("email",utilisateur.getEmail());
        uJson.put("nom",utilisateur.getNom());
        uJson.put("prenom",utilisateur.getPrenom());
        uJson.put("mdp",utilisateur.getMdp());
        uJson.put("id",utilisateur.getId());
        uJson.put("ville",utilisateur.getVille());
        uJson.put("adresse",utilisateur.getAdresse());
        uJson.put("codeP",utilisateur.getCodePostal());
        uJson.put("pays",utilisateur.getPays());
        uJson.put("num",utilisateur.getNum());

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

    /**
     * Permet de récupérer l'utilisateur sur base de son id
     *
     * @param id int, id de l'utilisateur que l'on veut récupérer
     * @return String response de l'api, bean utilisateur formatté en json
     * @throws IOException
     */
    public String getUser(int id) throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("UserController/get/user/"+id, "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Permet de récupérer l'utilisateur sur base de son token + d'activer son compte (effacer le token et mettre "activated")
     *
     * @param token String, token fixé lors de l'inscription de l'user
     * @return String reponse de l'api, bean utilisateur dormatté json
     * @throws IOException si erreur E/S
     */
    public String getUserActivated(String token) throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("UserController/get/user/activation/"+token, "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Permet de récupérer l'ensemble des vélos pour les afficher sur la carte coté utilisateur
     *
     * @return String reponse de l'api, liste de beans vélo formatté json
     * @throws IOException si erreur E/S
     */
    public String getBikes() throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("VeloController/getvelos", "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Permet d'insérer les infos nécessaire à des futurs paiement de l'utilisateur
     *
     * @param id_client int id du client sui ajoute une carte
     * @param customerId String, généré par api Stripe
     * @param partialCardNumber String, 4 dernier chiffre pour que le client puisse quand même identifier quelle est la carte en action
     * @return String reponse de l'api, code 200 ou 400
     * @throws SQLException
     * @throws IOException
     */
    public String insertCreditCard(int id_client, String customerId, String partialCardNumber) throws SQLException, IOException {

        //Création de la connection à l'API
        HttpURLConnection con = this.connectAPI.connectAPIJSON("CreditCardController/insertCreditCard/"+id_client+"/"+customerId+"/"+partialCardNumber, "PUT");

        if (con.getResponseCode() == 400 ){
           throw new SQLException("Erreur lors de l'insertion de la carte");
        }
        else{
           return "ok" ;
        }
    }

    /**
     * Permet de récupérer le customerId pour pouvoir déduire l'argent de la carte du client
     *
     * @param id_client int, id du client concerné
     * @return String reponse de l'api, customerId
     * @throws IOException
     */
    public String getUserCustomerId(int id_client) throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("CreditCardController/getCustomerId/"+id_client, "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Recupère l'ensemble des abonnements (passés et présents) pour un utilisateur donné
     *
     * @param id_client int, id du client
     * @return String reponse de l'api, liste de Abonnement
     * @throws IOException si erreur E/S
     */
    public String getAbonnements(int id_client) throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("AbonnementController/getAbonnements/"+id_client, "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Retourne la liste des différentes type de formules possibles pour un abonnement
     *
     * @return String, reponse de l'api, liste de Formule
     * @throws IOException
     */
    public String getFormules() throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("AbonnementController/getFormules", "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Lorsque le client choisi une formule à laquelle s'abonner, on l'insert dans la db
     *
     * @param id_client int , id du client concerné
     * @param id_formule int, id de la formule choisie par le client
     * @return String reponse de l'api
     * @throws SQLException
     * @throws IOException
     */
    public String insertAbonnement(int id_client, int id_formule) throws SQLException, IOException {

        //Création de la connection à l'API
        HttpURLConnection con = this.connectAPI.connectAPIJSON("AbonnementController/insertAbonnement/"+id_client+"/"+id_formule, "POST");

        if (con.getResponseCode() == 400 ){
            throw new SQLException("Erreur lors de l'insertion de la carte");
        }
        else{
            return "ok" ;
        }
    }

    /**
     * Recupere les infos à propos d'une formule en particulier
     *
     * @param id_formule int, l'id de la formule en db
     * @return String reponse de l'api
     * @throws IOException
     */
    public String getFormule(int id_formule) throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("AbonnementController/getFormule/"+id_formule, "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Recupere l'id de l'abonnement en cours d'un client
     *
     * @param id_client int, id du client concerné
     * @return String, repose de l'api, 0 si pas d'abonnement en cours, sinon id de l'abonnement
     * @throws IOException
     */
    public String getActuelAbonnement(int id_client) throws IOException {

        //Création URL pour connection à l'API
        String url=connectAPI.getUrl_API()+"AbonnementController/getAbonnementValable";
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
        urlParameters="idClient="+id_client;

        //Envoie des param à l'API (email et mdp)
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(urlParameters);
        wr.flush();

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }
}
