package stepDefination;

import ExtentListeners.ExtentManager;
import ExtentListeners.ExtentTestManager;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.hu.Ha;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReqresUserService {

    public static String BASE_URL = null;
    public static final String API_KEY = "reqres-free-v1";
    public static int responseCode=-1;
    protected Scenario scenario;
    static String scenarioName;
    static int x = 0;
    public static String response=null;
    public static Integer code=null;

    public static Map sendPostRequestNameJob(String endPoint, String name, String job) {
        Map<String,String> resultMap= new HashMap<>();
        HttpURLConnection connection = null;
        try {
            URL url = new URL(BASE_URL + endPoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.setRequestProperty("x-api-key", ReqresUserService.API_KEY);
        String jObject = String.format("{\"name\": \"%s\", \"job\": \"%s\"}", name, job);
        System.out.println(jObject);
        // Write JSON input string to the request body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jObject.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            // Read response
            int code = connection.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuilder status_response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                status_response.append(responseLine.trim());
            }
            resultMap.put("Response Code: " , String.valueOf(code));
            resultMap.put("Response Body: " , status_response.toString());
            setStatusCode(code);
            setResponce(status_response.toString());
            ExtentTestManager.logPass("Response Code: " + code);
            ExtentTestManager.logPass("Response Body: " + status_response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            ExtentTestManager.logFail(e.getMessage());
        } finally {
            if (connection != null) {
                // Disconnect to release resources
                connection.disconnect();
            }
        }
        return resultMap;
    }

    @Before
    public synchronized void  before(Scenario scenario) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        x = x + 1;
        this.scenario = scenario;
        scenarioName = scenario.getName();
        ExtentTestManager.startTest("Scenario No : " + x + " : " + scenario.getName());
        ExtentTestManager.getTest().log(Status.INFO, "Scenario started : - " + scenario.getName());

    }

    @After
    public void after(Scenario scenario) {
        if (scenario.isFailed()) {
            ExtentTestManager.logFail("Scenario Failed");
         //   ExtentTestManager.addScreenShotsOnFailure();
            ExtentTestManager.scenarioFail();
        } else {
            ExtentTestManager.scenarioPass();
        }
        ExtentManager.getReporter().flush();

    }

    public static int getTotalNumberOfUsersIncludingAllPages(String endURL) {
        try {
            int totalUsers = ReqresUserService.getTotalUsersCount(endURL);
            ExtentTestManager.logPass("Total number of users (all pages): " + totalUsers);
            return totalUsers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * Public method to get total users by fetching all pages and counting.
     */
    public static int getTotalUsersCount(String endURL) throws Exception {
        int totalUsersCount = 0;
        int totalPages = getTotalPages(endURL);
        for (int page = 1; page <= totalPages; page++) {
            totalUsersCount += getUsersCountByPage(endURL+page);
            ExtentTestManager.logInfo("Page : "+page+" | Users on page : "+getUsersCountByPage(endURL+page));
        }
        ExtentTestManager.logInfo("Total User Count : "+totalUsersCount);
        return totalUsersCount;
    }

    /**
     * Fetches the total number of pages from the first page response.
     */
    public static int getTotalPages(String endPoint) throws Exception {
        String response = sendGetRequest(endPoint);
        JSONObject jsonResponse = new JSONObject(response);
        ExtentTestManager.logInfo("Total pages : "+ jsonResponse.getInt("total_pages"));
        return jsonResponse.getInt("total_pages");
    }

    /**
     * Gets the count of users on a specific page.
     */
    public static int getUsersCountByPage(String endPoint) throws Exception {
        JSONArray usersArray= ReqresUserService.getUsersDetailsByPage(endPoint);
        ExtentTestManager.logInfo("User count : "+ usersArray.length());
        return usersArray.length();
    }

    /**
     * Gets the users details on a specific page.
     */
    public static JSONArray getUsersDetailsByPage(String endPoint) throws Exception {
        String response = sendGetRequest(endPoint);
        JSONObject pageJson = new JSONObject(response);
        JSONArray usersArray = pageJson.getJSONArray("data");
        ExtentTestManager.logInfo("User Details : "+ usersArray.toString());
        return usersArray;
    }

    /**
     * Gets the all users on a specific page.
     */
    public static JSONArray getAllUsersDetailsByPage(String endURL) throws Exception {
        int totalPages = getTotalPages(endURL);
        JSONArray totalUsersArray = new JSONArray();

        for (int page = 1; page <= totalPages; page++) {
            JSONArray tempArray = getUsersDetailsByPage(endURL+page);
            for(int i=0; i<tempArray.length();i++ ) {
                totalUsersArray.put(tempArray.get(i));
            }
            ExtentTestManager.logInfo("Page : "+page+" | Users on page : "+getUsersCountByPage(endURL+page));
        }
        ExtentTestManager.logInfo("Total user count on all page : "+totalUsersArray);
        return totalUsersArray;
    }

    /**
     * Sends a GET request to Reqres API for a specific page and returns the response as String.
     */
    public static String sendGetRequest(String endPoint) {
        String urlString = BASE_URL + endPoint;
        StringBuilder responseBuilder = new StringBuilder();
        code = -1;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-api-key", API_KEY);
            responseCode = connection.getResponseCode();

            String logMsg = String.format("GET request for page %s, Response Code: %d", endPoint, responseCode);
            if (responseCode != HttpURLConnection.HTTP_OK) {
                ExtentTestManager.logFail("GET request failed: " + logMsg);
            } else {
                ExtentTestManager.logPass("GET request passed: " + logMsg);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    responseCode >= 400 ? connection.getErrorStream() : connection.getInputStream(),
                    StandardCharsets.UTF_8
            ))) {
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    responseBuilder.append(inputLine) ;
                }
            }
        } catch (IOException e) {
            ExtentTestManager.logFail("Exception in GET request for: " + endPoint + " - " + e.getMessage());
            e.printStackTrace();
        }
        response = responseBuilder.toString().trim();
        setStatusCode(responseCode);
        setResponce(response);
        return response.equals("{}") ? "" : response;
    }

    public static Map sendPostRequestEmailPassword(String endPoint, String email, String password){
        Map<String, String> responseMap = new HashMap<String, String>();
        HttpURLConnection connection = null;
        try {
            URL url = new URL(BASE_URL + endPoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.setRequestProperty("x-api-key", ReqresUserService.API_KEY);
        String jObject = null;
        if (!email.isEmpty() && !password.isEmpty()) {
            jObject = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        } else if (!email.isEmpty() && password.isEmpty()) {
            jObject = String.format("{\"email\": \"%s\"}", email);
        } else if (email.isEmpty() && !password.isEmpty()) {
            jObject = String.format("\"password\": \"%s\"}", password);
        }
        System.out.println(jObject);
        // Write JSON input string to the request body
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jObject.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            // Read response
            Integer code = connection.getResponseCode();
            BufferedReader br = null;
            if (code >= 400) {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            }
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            responseMap.put("Status Code", code.toString());
            responseMap.put("Status Body", response.toString());

            setResponce(response.toString());
            setStatusCode(code);
            ExtentTestManager.logPass("Response Code: " + code);
            ExtentTestManager.logPass("Response Body: " + response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            ExtentTestManager.logFail(e.getMessage());
        }
        return responseMap;
    }

    public static String getResponse() {
        return response;
    }

    public static Integer getCode() {
        return code;
    }

    public static void setResponce(String response) {
        ReqresUserService.response= response;
    }

    public static void setStatusCode(int responseCode) {
        ReqresUserService.code=responseCode;
    }

}
