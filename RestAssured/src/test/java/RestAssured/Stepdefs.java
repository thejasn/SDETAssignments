package RestAssured;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import gherkin.deps.com.google.gson.JsonElement;
import gherkin.deps.com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class Stepdefs {
    private RequestSpecification requestSpecification;
    private Response response;
    JSONObject requestParams = new JSONObject();
    private String url = "https://jsonplaceholder.typicode.com";


    //Scenario 1
    @Given("^I perform the get operation on the given url$")
    public void iPerformTheGetOperationOnTheGivenUrl() {
        requestSpecification = given().contentType(ContentType.JSON);

    }

    @When("^The given url is \"([^\"]*)\"$")
    public void theGivenUrlIs(String arg0) throws Throwable {
        response = requestSpecification.when().get(url+arg0);
    }

    @Then("^Verify the response and status code as (\\d+)$")
    public void verifyTheResponseAndStatusCodeAs(int arg0) {
        assertEquals(arg0,response.getStatusCode());
        System.out.println(response.getBody().prettyPrint());
    }



    // Scenario 2

    @Given("^With the following details for post:$")
    public void withTheFollowingDetails(List<String> values) {
        requestSpecification = given().contentType(ContentType.JSON);
        requestSpecification.header("charset","UTF-8");
        requestParams.put("title",values.get(0));
        requestParams.put("body",values.get(1));
        requestParams.put("userId",Integer.parseInt(values.get(2)));
    }

    @When("^Make a post request to \"([^\"]*)\"$")
    public void makeAPostRequestTo(String arg0) throws Throwable {
        requestSpecification.body(requestParams.toString());
        response = requestSpecification.post(url+arg0);

    }

    @Then("^Verify the response body and the status as (\\d+)$")
    public void verifyTheResponseBodyAndTheStatusAs(int arg0) {
        assertEquals(arg0,response.getStatusCode());
        System.out.println("Response:\n" + response.getBody().prettyPrint());
    }


    //Scenario 3

    @Given("^With the following details for put:$")
    public void withTheFollowingDetailsForPut(List<String> values) {
        requestSpecification = given().contentType(ContentType.JSON);
        requestSpecification.header("charset","UTF-8");
        requestParams.put("id",Integer.parseInt(values.get(0)));
        requestParams.put("title",values.get(1));
        requestParams.put("body",values.get(2));

        requestParams.put("userId",Integer.parseInt(values.get(3)));

    }

    @When("^Make a put request to \"([^\"]*)\"$")
    public void makeAPutRequestTo(String arg0) throws Throwable {
        requestSpecification.body(requestParams.toString());
        response = requestSpecification.put(url+arg0);
    }


    //Scenario 4

    @Given("^With the given value of title:$")
    public void with_the_given_value_of_title(List<String> values) throws Exception {
        requestSpecification = given().contentType(ContentType.JSON);
        requestSpecification.header("charset","UTF-8");
        requestParams.put("title",values.get(0));
    }

    @When("^Make a patch request to \"([^\"]*)\"$")
    public void make_a_patch_request_to(String arg1) throws Exception {
        requestSpecification.body(requestParams.toString());
        response = requestSpecification.patch(url+arg1);
    }

    //Scenario 5

    @Given("^Perform a delete operation of a post$")
    public void perform_a_delete_operation_of_a_post() throws Exception {
        requestSpecification = given();
    }

    @When("^Delete the post with url \"([^\"]*)\"$")
    public void delete_the_post_with_url(String arg1) throws Exception {
        response = requestSpecification.delete(url+arg1);
    }

    //Scenario 6
    @Given("^Perform a length check on the given url$")
    public void performALengthCheckOnTheGivenUrl() {
        requestSpecification = given();
    }

    @When("^if the url is \"([^\"]*)\"$")
    public void ifTheUrlIs(String arg0) throws Throwable {
        response = requestSpecification.when().get(url+arg0);
    }

    @Then("^Verify the length as \"([^\"]*)\"$")
    public void verifyTheLengthAs(String arg0) throws Throwable {
        response.then().assertThat().body("size()",is(Integer.parseInt(arg0)));
    }
}