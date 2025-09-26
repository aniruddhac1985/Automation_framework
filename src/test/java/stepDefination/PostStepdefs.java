package stepDefination;

import ExtentListeners.ExtentTestManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import utilities.APIConnection;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static stepDefination.Test_Steps.ReqresUserService.BASE_URL;

public class PostStepdefs {

    StringBuilder response =null;
    static Integer code = -1;
    @Given("the system is running and the Create User API is available")
    public void theSystemIsRunningAndTheCreateUserAPIIsAvailable() {
        try {
            URL url = new URL(BASE_URL+"/api/users/1");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(3000); // 3 seconds timeout for connection
            con.setReadTimeout(3000);    // 3 seconds timeout for reading data

            int responseCode = con.getResponseCode();
            // Consider any 2xx response code as healthy
            if(responseCode >= 200 && responseCode < 300){
                ExtentTestManager.logPass("Server is up and running with response code: "+ responseCode);
            }
        } catch (Exception e) {
            // Exception means server is not reachable or request failed
            ExtentTestManager.logFail("API Server is down");
            throw new RuntimeException("API server is down");
        }
    }

    @When("I send a POST request to {string} with the following details: {string} and {string}")
    public void iSendAPOSTRequestToWithTheFollowingDetailsAnd(String endPoint, String name, String job) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(BASE_URL+endPoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        connection.setRequestProperty("x-api-key", Test_Steps.ReqresUserService.API_KEY);
//        Map dataMap= new HashMap();
//        dataMap.put("name", name);
//        dataMap.put("job", job);
        String jObject= String.format("{\"name\": \"%s\", \"job\": \"%s\"}", name, job);
        System.out.println(jObject);
        // Write JSON input string to the request body
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jObject.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            // Read response
            code = connection.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            ExtentTestManager.logPass("Response Code: " + code);
            ExtentTestManager.logPass("Response Body: " + response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            ExtentTestManager.logFail(e.getMessage());
        }
        }

    @Then("the response status code should be {string}")
    public void theResponseStatusCodeShouldBe(String statusCode) {
        //   int code = con.getResponseCode();
        if(code.toString().equalsIgnoreCase(statusCode)) {
            //assertEquals(code, statusCode);
            ExtentTestManager.logPass("Response Code: " + code + " | Expected code : " + statusCode);
        }
        else
            ExtentTestManager.logFail("Response Code: " + code + " | Expected code : "+statusCode);
    }

    @And("the response should contain the field {string}")
    public void theResponseShouldContainTheField(String fieldName) {
        JSONObject Jresponse = new JSONObject(response.toString());
        if(Jresponse.has(fieldName)){
            ExtentTestManager.logPass("Field present : " + fieldName + " with value : " + Jresponse.get(fieldName));
        }else{
            ExtentTestManager.logFail(fieldName +"  not present : ");
        }
    }

    @And("the response field {string} should be {string}")
    public void theResponseFieldShouldBe(String fieldName, String value) {
        JSONObject Jresponse = new JSONObject(response.toString());
        if(Jresponse.get(fieldName).toString().equalsIgnoreCase(value)){
            ExtentTestManager.logPass("Field present : " + fieldName + " with value : " + Jresponse.get(fieldName));
        }else{
            ExtentTestManager.logFail(fieldName +"  not present : ");
        }
    }

    @Given("I send POST request to {string} login with the {string} and {string}")
    public void iSendPOSTRequestToLoginWithTheEmailAndPassword(String endPoint, String email, String password) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(BASE_URL+endPoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        connection.setRequestProperty("x-api-key", Test_Steps.ReqresUserService.API_KEY);
        String jObject=null;
        if(!email.isEmpty()&& !password.isEmpty()) {
            jObject= String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        }else if(!email.isEmpty()&& password.isEmpty()) {
            jObject= String.format("{\"email\": \"%s\"}", email);
        }else if(email.isEmpty()&& !password.isEmpty()) {
            jObject= String.format("\"password\": \"%s\"}", password);
        }
        System.out.println(jObject);
        // Write JSON input string to the request body
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jObject.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            // Read response
            code = connection.getResponseCode();
            BufferedReader br = null;
            if(code>=400){
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }else {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            }
            response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            ExtentTestManager.logPass("Response Code: " + code);
            ExtentTestManager.logPass("Response Body: " + response.toString());

        } catch (Exception e) {
            e.printStackTrace();
            ExtentTestManager.logFail(e.getMessage());
        }
    }
}
