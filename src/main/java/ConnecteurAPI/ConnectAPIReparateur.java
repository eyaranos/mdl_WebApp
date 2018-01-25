package ConnecteurAPI;

import beans.Reparateur;
import beans.Utilisateur;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

public class ConnectAPIReparateur {
    private ConnectAPI connectAPI;
    private String auth;

    public ConnectAPIReparateur(String auth){
        this.connectAPI =new ConnectAPI(auth);
        this.auth = auth;
    }

    public ConnectAPIReparateur(){
        this.connectAPI = new ConnectAPI();
    }

    public String getAuth() {
        return auth;
    }

    public String checkConnectionRepa(Reparateur reparateur) throws IOException {

        //Création URL pour connection à l'API
        String url=connectAPI.getUrl_API()+"ReparateurController/connexion/reparateur";
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

    /**
     * Sur base de l'id du centre, on recupère l'ensemble des vélos à réparer présent dans celui ci
     *
     * @param id_centre int id
     * @return Strinf reponse de l'api, tableau json de bean VeloToFix
     * @throws IOException si erreur E/S
     */
    public String getBikesToFix(int id_centre) throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("ReparateurController/getVelos/"+id_centre, "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Sur base de l'idée d'un employé, on recupère l'id du centre dans lequel il travaille
     *
     * @param id_employe int id de l'employe
     * @return String reponse de l'api, id_centre
     * @throws IOException si erreur E/S
     */
    public String getIdCentreRepa(int id_employe) throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("ReparateurController/getCentreReparation/"+id_employe, "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Permet de récupérer l'état d'un vélo dans un centre de réparation : En cours de réparation, terminé ou aucun des deux.
     * Une seule méthode pour 3 états différents d'ou la présence d'un param type pour utiliser la même méthode pour chaque état.
     * Si type vaut 1 = cas B (cfr doc), "date_fin_rep is not null" || Si type vaut 2 = cas A (cfr doc), "date_fin_rep is null"
     *
     * @param id_velo int
     * @param type int qui vaut 1 ou 2
     * @return String reponse de l'api, beans employe
     * @throws IOException si erreur E/S
     */
    public String getInfoReparation(int id_velo, int type) throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("ReparateurController/getInfoReparation/"+id_velo+"/"+type, "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Permet de savoir si un velo précis est deja en train d'être réparé par un employé
     *
     * @param id_velo du velo que l'on veut tester
     * @return String la reponse json ou le code d'erreur http
     * @throws IOException si erreru E/S
     */
    public JSONObject getIfBikeFixing(int id_velo) throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("ReparateurController/getIfBikeFixing/"+id_velo, "GET");
        JSONObject jsonResponse;

        String rep=connectAPI.showBackMessage(conn);

        if (rep.equals("400")){
            jsonResponse = new JSONObject("{status : 400}");
        }
        else{
            jsonResponse = new JSONObject(rep);
        }

        return jsonResponse;
    }

    /**
     * Insert dans la table "a_repare", les info nécessaire pour représenter un velo en train d'etre réparé
     *
     * @param id_employe int
     * @param id_velo in
     * @return true si pas d'erreur
     * @throws IOException si erreur E/S
     */
    public boolean insertBikeFixing(int id_employe, int id_velo) throws IOException {

        //Création de la connection à l'API
        HttpURLConnection con = this.connectAPI.connectAPIJSON("ReparateurController/insert/bikeToFix/"+id_velo+"/"+id_employe, "POST");

         if (con.getResponseCode() == 200) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Update dans la table "a_repare", la date a laquelle un velo particulier a été réparé (date_fin_rep)
     *
     * @param id_velo int
     * @param commentaire String, eventuel commentaire du réparateur à propos de la réparation effectuée
     * @return true si pas d'erreur
     * @throws IOException si erreur E/S
     */
    public boolean updateBikeTerminated(int id_velo, String commentaire) throws IOException {

        //Création URL pour connection à l'API
        String url=connectAPI.getUrl_API()+"ReparateurController/update/bikeToFix";
        URL object=new URL(url);

        //Création de la connection à l'API
        HttpURLConnection conn= (HttpURLConnection) object.openConnection();
        conn.setDoOutput( true );
        conn.setInstanceFollowRedirects( false );
        conn.setRequestMethod( "POST" );
        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Authorization", getAuth());
        conn.setUseCaches( false );

        //Création des params pour l'url
        String urlParameters;
        urlParameters="id_velo="+id_velo;
        urlParameters=urlParameters+"&commentaire="+commentaire;

        //Envoie des param à l'API (email et mdp)
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(urlParameters);
        wr.flush();

        if (conn.getResponseCode() == 200) {
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Renvoie tous les vélos
     *
     * @return String une liste JSON avec l'ensemble des objets Vélo
     * @throws IOException si erreur E/S
     */
    public String getBikes() throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("VeloController/getvelos", "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Renvoie tous les vélos cassés
     *
     * @return String une liste JSON avec l'ensemble des objets Vélo qui présente un dommage
     * @throws IOException
     */
    public String getBrokenBikes() throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("VeloController/getAllVeloCasse", "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Renvoie les zone de lock pour les afficher sur la carte
     *
     * @return String une liste JSON avec les objets ZoneLock
     * @throws IOException si erreur E/S
     */
    public String getZoneLock() throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("ZoneLockController/getZoneLock", "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    /**
     * Renvoie les stations pour les afficher sur la carte
     *
     * @return String une liste JSON avec les objets Station
     * @throws IOException si erreur E/S
     */
    public String getStations() throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("StationController/getAllStation", "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    //--------------------- ADMIN SECTION --------------------------
    public String startAlgo() throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("Admin/startAlgo", "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    public String getTrajets() throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("Admin/getAllTrajet", "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    public String getNbClient() throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("Admin/getNbClient", "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    public String getNbClientAbo() throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("Admin/getNbClientAvecAbo", "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    public String getEmploye() throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("Admin/getAllEmp", "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

    public String getAllVeloForStation() throws IOException {

        //Création de la connection à l'API
        HttpURLConnection conn = this.connectAPI.connectAPIJSON("Admin/getAllVeloForAllStation", "GET");

        String rep=connectAPI.showBackMessage(conn);

        return rep;
    }

}
