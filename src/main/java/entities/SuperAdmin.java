package entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.CompanyData;
import org.springframework.web.bind.annotation.RequestMapping;
import security.GetTokenServiceImpl;

import javax.xml.ws.Response;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 07.08.2017.
 */
public class SuperAdmin extends User {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final String USER_AGENT= "Mozilla/5.0";
    private final String URL = "http://server.ru";
    private final String TOKEN = new GetTokenServiceImpl().getToken("superadmin@mail.ru", "password");

    public SuperAdmin() throws Exception {
    }

    public SuperAdmin(String email, String password, Role role, String name, String phone) throws Exception {
        super(email, password, role, name, phone);      //we need to hardcode superAdmin
    }

    @RequestMapping("/admin/login")
    public String logIn(String email, String password) {
        String params = "username=" + email + "&password=" + password;      //what`s the correct names of parameters username and password???
        String token = null;

        try {
            HttpURLConnection connection = sendPost(URL, params);
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {            // yeaaaahhhh))) success
                token = new GetTokenServiceImpl().getToken(email, password);
            } else if (responseCode == 409){
                System.out.println("wrong password or email");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    private HttpURLConnection sendPost(String url, String params) throws IOException {
        URL serverUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
//        connection.setConnectTimeout(250);
//        connection.setReadTimeout(250);
        connection.setDoOutput(true);
        connection.setDoInput(true);                                    //whats it?
        connection.setRequestProperty("entities.User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connection.setRequestProperty("token", TOKEN);

        connection.connect();
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(params);
        outputStream.flush();
        outputStream.close();

        return connection;
    }


    @RequestMapping("/admin/company")//didnot understand about headers - authorization via token
    public void addCompany(String companyName, String companyAddress, String ownerName,
                               String ownerPhone, String ownerEmail, String ownerPassword){

        String params = "companyname=" + companyName + "&companyaddress=" + companyAddress +
                "&ownername=" + ownerName + "&ownerphone=" + ownerPhone +
                "&owneremail=" + ownerEmail + "&ownerpassword=" + ownerPassword;

        try {
            HttpURLConnection conn = sendPost(URL, params);

            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                System.out.println("data have sent successfully");
            } else if (responseCode == 401) {
                System.out.println("You need authorize");
            } else if (responseCode == 409) {
                System.out.println("Owner already exists!");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/admin/companies")
    public List<CompanyData> getCompanies() throws IOException {
        List<CompanyData> companies = new ArrayList<CompanyData>();
        StringBuilder stringBuilder = sendGet(URL);
        if(stringBuilder != null) {

            String[] results = stringBuilder.toString().split("...");
            for(String currCompanyDataString : results) {
                CompanyData companyData = GSON.fromJson(currCompanyDataString, CompanyData.class);
                companies.add(companyData);
            }
        }
        else    //response was empty because of responseCode != 200
            System.out.println("You need authorize");
        return companies;
    }

    private StringBuilder sendGet(String url) throws IOException {

        URL serverUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setUseCaches(false);
        connection.setConnectTimeout(250);              //?
        connection.setReadTimeout(250);                 //?
        connection.setRequestProperty("entities.User-Agent", USER_AGENT);                //?
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");     //?
        connection.setRequestProperty("token", TOKEN);
        connection.connect();

        StringBuilder in = null;

        if(HttpURLConnection.HTTP_OK == connection.getResponseCode()){//why not if(connection.getResponseCode() == 200)????

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while((line = reader.readLine()) != null){
                in.append(line);
                if(line.endsWith("}"))
                    in.append("...");
                in.append("\n");
            }
        }
        return in;
    }


    @RequestMapping("/admin/company")
    public void removeCompany(String companyId) throws IOException {//companyId - from MongoDB
        URL serverUrl = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
        connection.setDoOutput(true);
        connection.setConnectTimeout(250);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded" );//whats it???
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("idInMongo", companyId);
        connection.setRequestProperty("token", TOKEN);
        connection.connect();
        switch (connection.getResponseCode()) {
            case (200):
                System.out.println("mission complete. record have been successfully deleted");
                break;
            case (401):
                System.out.println("You need authorize");
                break;
            case (409):
                System.out.println("entities.Company dose not exist!");
                break;
            default:
                System.out.println("strange server`s response code");
                break;
        }
    }

    @RequestMapping("/admin/company")//what about company`s owner changing?
    public Response updateCompany(String companyId, String companyName, String address){

        //why only 3 parameters? or need to execute for all fields?
        //or we will change only these fields?
        //or need to leave another params as is? or delete them?

        return null;
    }
/*=UPDATE COMPANY=
/admin/company
[UPDATE]
headers
Authorization : [token]

requestBody:
{
companyID : "id in mongo"
company : "Google",
address : "Tel Aviv, Herzel 4/6"
}
Response Error:
401 Unauthorized
{
error : "You need authorize"
}
409 Conflict
{
error : "entities.Company dose not exist!"
}*/
}
