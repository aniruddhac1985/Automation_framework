package stepDefination;

import ExtentListeners.ExtentTestManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static stepDefination.ReqresUserService.*;
import static stepDefination.Test_Steps.code;
import static stepDefination.Test_Steps.response;

public class PostStepdefs {

    static StringBuilder status_response = null;
    static Integer  status_code=-1;
    @Given("the system is running and the Create User API is available")
    public void theSystemIsRunningAndTheCreateUserAPIIsAvailable() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(BASE_URL);

            // Open a connection to the URL
            connection = (HttpURLConnection) url.openConnection();

            // Set the request method to HEAD for efficiency
            connection.setRequestMethod("HEAD");

            // Set a connection timeout (e.g., 5 seconds)
            connection.setConnectTimeout(5000);

            // Connect to the server
            connection.connect();

            // Get the response code
            int responseCode = connection.getResponseCode();

            // Consider any 2xx response code as healthy
            if (responseCode >= 200 && responseCode < 300) {
                System.out.println("API Server is UP! Health check returned: " + responseCode);
            } else {
                System.err.println("Unexpected server response: " + responseCode);
                throw new IllegalStateException("API health check failed, response: " + responseCode);
            }
        } catch (Exception e) {
            System.err.println("API Server is DOWN: " + e.getMessage());
            throw new RuntimeException("API server is down or not reachable: " + e.getMessage(), e);
        } finally {
            if (connection != null) {
                // Disconnect to release resources
                connection.disconnect();
            }
        }
    }

    @When("I send a POST request to {string} with the following details: {string} and {string}")
    public void iSendAPOSTRequestToWithTheFollowingDetailsAnd(String endPoint, String name, String job) {
        Map resultMap=ReqresUserService.sendPostRequestNameJob(endPoint,name,job);
        resultMap.forEach((key, value) -> ExtentTestManager.logInfo(key + " : " + value));
    }

    @Then("the response status code should be {string}")
    public void theResponseStatusCodeShouldBe(String statusCode) {
           int code = ReqresUserService.getCode();
        if (String.valueOf(code).equalsIgnoreCase(statusCode)) {
            //assertEquals(code, statusCode);
            ExtentTestManager.logPass("Response Code: " + code + " | Expected code : " + statusCode);
        } else {
            ExtentTestManager.logFail("Response Code: " + code + " | Expected code : " + statusCode);
        }
    }

    @And("the response should contain the field {string}")
    public void theResponseShouldContainTheField(String fieldName) {
        JSONObject Jresponse = new JSONObject(ReqresUserService.getResponse().toString());
        if (Jresponse.has(fieldName)) {
            ExtentTestManager.logPass("Field present : " + fieldName + " with value : " + Jresponse.get(fieldName));
        } else {
            ExtentTestManager.logFail(fieldName + "  not present : ");
        }
    }

    @And("the response field {string} should be {string}")
    public void theResponseFieldShouldBe(String fieldName, String value) {
        JSONObject Jresponse = new JSONObject(getResponse().toString());
        if (Jresponse.get(fieldName).toString().equalsIgnoreCase(value)) {
            ExtentTestManager.logPass("Field present : " + fieldName + " with value : " + Jresponse.get(fieldName));
        } else {
            ExtentTestManager.logFail(fieldName + "  not present.");
        }
    }

    @Given("I send POST request to {string} login with the {string} and {string}")
    public void iSendPOSTRequestToLoginWithTheEmailAndPassword(String endPoint, String email, String password) {
        Map resultMap= sendPostRequestEmailPassword(endPoint, email, password);
        resultMap.forEach((key, value) -> ExtentTestManager.logInfo(key + " : " + value));
    }


}
