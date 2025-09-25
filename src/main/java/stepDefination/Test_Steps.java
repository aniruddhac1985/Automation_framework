package stepDefination;


import ExtentListeners.ExtentManager;
import ExtentListeners.ExtentTestManager;
import PageObjects.HomePage;
import com.aventstack.extentreports.Status;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import steps.BaseSteps;

import static io.restassured.RestAssured.given;

public class Test_Steps  {

    public static Response response=null;
    static String url = "https://reqres.in/api/users?page=2";
    static Date d = new Date();
    public static Logger log = Logger.getLogger("Test_Steps");


    @Given("^authorized user")
    public int authorized_user()  throws Throwable{
        int responseCode=0;
        try {

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set request method to GET
            con.setRequestMethod("GET");

            // Add the API key header
            con.setRequestProperty("x-api-key", "reqres-free-v1");

            responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream())
            );
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the response
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseCode;
    }



    @Given("get the default list of users for on page {string}")
    public void getTheDefaultListOfUsersForOnStPage(String endpoint)  {
        // Print response
        try{
        int totalUserOnThePage=ReqresUserService.getUsersCountByPage(endpoint);
            log.info("Response=> " + totalUserOnThePage);
            ExtentTestManager.logPass("Total number of user on page => " + totalUserOnThePage);
        }catch(Exception e){
            System.out.println(e.getMessage());
            ExtentTestManager.logFail("Response = -1 ");
        }
    }

    @Given("BaseURL {string}")
    public void baseurl(String baseurl) {
        ReqresUserService.BASE_URL=baseurl;
    }

    @When("get the list of all users within every page {string}")
    public void getTheListOfAllUsersWithinEveryPage(String endURL) {
        ReqresUserService.getTotalNumberOfUsersIncludingAllPages(endURL);
    }


//    @Override
//    public void close() throws Exception {
//        extent.flush();
//        extent=null;
//        test=null;
//        System.out.println("Extent Report generated.");
//    }

    @When("status is {int}")
    public void statusIsStatus_code(Integer statusCode) throws Throwable {
        if(authorized_user()==statusCode)
            ExtentTestManager.logPass("Status received => " + authorized_user());
        else
            ExtentTestManager.logFail("Status received => " + authorized_user());
    }

    @Then("I should see total users count equals the number of user ids {string}")
    public void iShouldSeeTotalUsersCountEqualsTheNumberOfUserIds(String endPoint) {
        Set<Integer> idSet= new HashSet<Integer>();
        try {
            JSONArray usersArray = ReqresUserService.getAllUsersDetailsByPage(endPoint);
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject obj = usersArray.getJSONObject(i);
                int id = obj.getInt("id");
                idSet.add(id);

            }
            System.out.println("ID's: " + idSet.toString());
            if(ReqresUserService.getTotalUsersCount(endPoint)==idSet.size()){
                ExtentTestManager.logPass("Count is matching");
            }else{
                ExtentTestManager.logFail("Count is mismatching");
            }
        }catch (Exception e){

        }
    }


    @Given("search for user id on page {string}")
    public void searchForUserIdOnPage(String endPoint) throws Exception {
        String userData = ReqresUserService.sendGetRequest(endPoint);
        if(userData!=null){
            ExtentTestManager.logPass("User found : "+ userData);
        }else{
            ExtentTestManager.logFail("User not found");
        }

    }


    @Given("Search user data {string} and {string} on page {string}")
    public JSONObject searchUserDataNameAndEmailOnPage(String name, String email,String endPoint) {
        try {
            JSONArray allUserDetails= ReqresUserService.getAllUsersDetailsByPage(endPoint);

                for (int i = 0; i < allUserDetails.length(); i++) {
                    JSONObject obj = allUserDetails.getJSONObject(i);
                    if (obj.getString("first_name").equalsIgnoreCase(name) && obj.getString("email").equalsIgnoreCase(email)) {
                        ExtentTestManager.logPass("User found : " + obj.toString());
                        return obj;
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExtentTestManager.logFail("User not found with name : "+name+" and email :"+email);
        return null;

    }



    public static class ReqresUserService extends BaseSteps {

        public static String BASE_URL = null;
        public static final String API_KEY = "reqres-free-v1";
        public HomePage home;

        protected Scenario scenario;
        static String scenarioName;
        static int x = 0;

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
            setUpFramework();
        }

        @After
        public void after(Scenario scenario) {
            if (scenario.isFailed()) {
                ExtentTestManager.logFail("Scenario Failed");
                ExtentTestManager.addScreenShotsOnFailure();
            } else {
                ExtentTestManager.scenarioPass();
            }
            ExtentManager.getReporter().flush();
            quit();
        }

        public static int getTotalNumberOfUsersIncludingAllPages(String endURL) {
            try {
                int totalUsers = ReqresUserService.getTotalUsersCount(endURL);
                ExtentTestManager.logPass("Total number of users (all pages): " + totalUsers);
                return totalUsers;
            } catch (Exception e) {
                log.info(e.getMessage());
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
        public static String sendGetRequest(String endPoint) throws Exception {
            URL url = new URL(BASE_URL + endPoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-api-key", API_KEY);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("GET request failed for page " + (endPoint.split("="))[1] + ", Response Code: " + responseCode);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBuilder.append(inputLine);
            }
            in.close();

            return responseBuilder.toString();
        }


    }
}