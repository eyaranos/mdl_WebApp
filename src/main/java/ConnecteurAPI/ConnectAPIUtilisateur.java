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
    private String auth;


    public ConnectAPIUtilisateur(String auth){

        this.connectAPI = new ConnectAPI(auth);
        this.auth = auth;
    }

    public ConnectAPIUtilisateur(){

        this.connectAPI = new ConnectAPI();
    }

    public String getAuth() {
        return auth;
    }

    /**
     * Permet d'insérer un nouvel utilisateur lors de l'inscription de celui-ci
     *
     * @param utilisateur Utilisateur concerné par l'inscription
     * @return true si ok, false si erreur
     * @throws SQLException
     * @throws IOException
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

    /**
     * Permet à un utilisateur de mettre ses informations de compte à jour
     *
     * @param utilisateur Utilisateur concerné
     * @return true si ok, sinon false
     * @throws Exception
     */
    public boolean updateUser(Utilisateur utilisateur) throws Exception {

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

        return (con.getResponseCode() != 400);
    }

    /**
     * Permet de mettre à jour le mot de passe d'un utilisateur avec un mot de passe temporaire si celui-ci à oublié
     * le sien.
     *
     * @param utilisateur Utilisateur concerné
     * @return true si ok, false sinon
     * @throws Exception
     */
    public boolean updateUserForgotPassword(Utilisateur utilisateur) throws Exception {

        //Création de la connection à l'API
        HttpURLConnection con = this.connectAPI.connectAPIJSON("UserController/update/user/forget", "POST");

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

        return (con.getResponseCode() != 400);
    }


    /**
     * Permet de verifier l'existence d'un client et de créer sa session si ok
     *
     * @param utilisateur Utilisateur concerné
     * @return Json, les infos liées à cet utilisateur
     * @throws IOException si erreur E/S
     */
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

        if (con.getResponseCode() != 200 ){
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
            throw new SQLException("Erreur lors de l'insertion de l'abonnement");
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
        conn.setRequestProperty("Authorization", this.getAuth() );
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

    /**
     * Permet de récupérer l'ensemble des trajets effectués par un client
     *
     * @param id_client int, le client concerné
     * @return reponse de l'api, la liste de trajets
     * @throws IOException si erreur E/S
     */
    public String getTrajets(int id_client) throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("UserController/getTrajets/"+id_client, "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     *  Récupère l'ensemble des zones de lock
     * @return reponse de l'api String, soit code d'erreur soit la liste des zone de lock
     * @throws IOException
     */
    public String getZoneLock() throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("ZoneLockController/getZoneLock", "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Recupere les 4 derniers chiffre de la carte de credit du client pour lui faire savoir qu'elle carte il utilise
     * @param id_client int, le client concerné
     * @return api response String
     * @throws IOException si erreur E/S
     */
    public String getLast4(int id_client) throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("CreditCardController/getLast4/"+id_client, "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Permet de mettre à jour les infos d'une carte de credit liée à un client.
     *
     * @param customerId String, le token renvoyé par Stripe qui permet d'identifier chaque client sur leur serveur
     * @param partialCardNumber String, les 4 derniers chiffre de la carte du client
     * @return String, message de feedback ok ou nok
     * @throws SQLException
     * @throws IOException
     */
    public String updateCreditCard(String customerId, String partialCardNumber) throws SQLException, IOException {

        //Création de la connection à l'API
        HttpURLConnection con = this.connectAPI.connectAPIJSON("CreditCardController/updateCreditCard/"+customerId+"/"+partialCardNumber, "PUT");

        if (con.getResponseCode() != 200 ){
            throw new SQLException("Erreur lors de la mise a jour de la carte");
        }
        else{
            return "ok" ;
        }
    }

    /**
     * Permet à un client de supprimer sa carte de crédit couramment liée à son compte
     *
     * @param customerId String, le token renvoyé par Stripe qui permet d'identifier chaque client sur leur serveur
     * @return String, message de feedback ok ou nok
     * @throws SQLException
     * @throws IOException
     */
    public String deleteCreditCard(String customerId) throws SQLException, IOException {

        //Création de la connection à l'API
        HttpURLConnection con = this.connectAPI.connectAPIJSON("CreditCardController/deleteCreditCard/"+customerId, "DELETE");

        if (con.getResponseCode() != 200 ){
            throw new SQLException("Erreur lors de la suppression de la carte");
        }
        else{
            return "ok" ;
        }
    }
}
