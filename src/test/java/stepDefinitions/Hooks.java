package stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws IOException {
        StepDef stepDef = new StepDef();
        if(StepDef.place_id==null) {
            stepDef.add_place_payload("Shetty", "French", "Asia");
            stepDef.user_calls_with_post_http_request("AddPlaceAPI", "POST");
            stepDef.verify_place_id_created_maps_to_using_get_place_api("Shetty", "GetPlaceAPI");
        }
    }
}
