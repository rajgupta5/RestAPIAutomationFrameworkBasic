package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDef extends Utils {
    ResponseSpecification resspec;
    RequestSpecification res;
    Response response;
    TestDataBuild data = new TestDataBuild();
    static String place_id;

    @Given("Add Place Payload with {string} {string} {string}")
    public void add_place_payload(String name, String lang, String address) throws IOException {
        res=given().spec(requestSpecification())
                .body(data.addPlacePayload(name, address, lang));
    }
    @When("user calls {string} with {string} http request")
    public void user_calls_with_post_http_request(String resource, String method) {
        APIResources resourcesAPI = APIResources.valueOf(resource);

        resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if(method.equalsIgnoreCase("POST")) {
            response = res.when().post(resourcesAPI.getResource());
//                    then().spec(resspec).extract().response();
        }

        if(method.equalsIgnoreCase("GET")) {
            response = res.when().get(resourcesAPI.getResource());
        }

        if(method.equalsIgnoreCase("DELETE")) {
            response = res.when().delete(resourcesAPI.getResource());
        }


    }
    @Then("the Api call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(Integer int1) {
        assertEquals(response.getStatusCode(), 200);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String value) {
        assertEquals(getJsonPath(response, key), value);
    }

    @Then("verify place_Id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using_get_place_api(String expectedValue, String resource) throws IOException {
        place_id = getJsonPath(response, "place_id");
        res=given().spec(requestSpecification()).queryParam("place_id", place_id);
        user_calls_with_post_http_request(resource, "GET");
        assertEquals(getJsonPath(response, "name"), expectedValue);
    }

    @Given("DeletePlace Payload")
    public void delete_place_payload() throws IOException {
        res = given().spec(requestSpecification()).body(data.deletePayload(place_id));
    }
}
