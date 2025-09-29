package utilities;

import ExtentListeners.ExtentTestManager;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

import static stepDefination.ReqresUserService.BASE_URL;

public class APIConnection {
    HttpURLConnection con = null;
    public static Logger log = Logger.getLogger("APIConnection");


    public HttpURLConnection getAPIConnection(String endPoint){
        try {

            URL obj = new URL(BASE_URL);
            con = (HttpURLConnection) obj.openConnection();

            // Set request method to GET
            con.setRequestMethod("GET");

            // Add the API key header
            con.setRequestProperty("x-api-key", "reqres-free-v1");
            ExtentTestManager.logInfo("Connection created successfully");
            return con;
        }catch (Exception e){
            e.printStackTrace();
            ExtentTestManager.logInfo(e.getMessage());
            return null;
        }
    }

    public String createJSONObject(Map data){
        JSONObject newUser= new JSONObject();
        data.forEach((key, value) -> {
            newUser.put(key.toString(),value.toString());
            System.out.println("Key: " + key + ", Value: " + value);
        });
            return newUser.toString();
    }
}
