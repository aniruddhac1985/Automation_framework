package stepDefination;


import ExtentListeners.ExtentTestManager;
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
import stepDefination.ReqresUserService;

public class Test_Steps  {


    static String url = "https://reqres.in/api/users?page=2";
    static Date d = new Date();
    public static Logger log = Logger.getLogger("Test_Steps");
    public static Response response=null;
    public static Integer code=null;


    @Given("BaseURL {string}")
    public void baseurl(String baseurl) {
        ReqresUserService.BASE_URL=baseurl;
    }

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
        if(userData!=null && !userData.isBlank() && !userData.isEmpty()){
            ExtentTestManager.logPass("User found : "+ userData);
        }else{
            ExtentTestManager.logFail("User not found : "+ endPoint);
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


    @Given("I send get request to {string} wait for the user list to load")
    public void iSendGetRequestToWaitForTheUserListToLoad(String endURL) {
        String response = ReqresUserService.sendGetRequest(endURL);
        JSONObject resultArray= new JSONObject(response);
        if (resultArray.length()>0) {
            ReqresUserService.setResponce(response);
            ExtentTestManager.logPass(response);
        } else {
            ExtentTestManager.logFail(response);
        }
    }

    @Then("I should see that every user has a unique id")
    public void iShouldSeeThatEveryUserHasAUniqueId() {
        JSONObject jsonObject = new JSONObject(ReqresUserService.getResponse());
        JSONArray usersArray = jsonObject.getJSONArray("data");

        Set<Integer> uniqueIds = new HashSet<>();
        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject user = usersArray.getJSONObject(i);
            int userId = user.getInt("id");
            // Assert no duplicates
            if(uniqueIds.contains(userId)){
                ExtentTestManager.logFail("Duplicate user id found: " + userId);
            }
            uniqueIds.add(userId);
        }

        // Optionally, assert count match
        ExtentTestManager.logPass(uniqueIds.size() == usersArray.length() ?
                "User count matches unique user ids count "+
                        uniqueIds.size() +" : User in the list are : " +
                        ""+ usersArray.length():
                "User count does not match unique user ids count" );
    }
}