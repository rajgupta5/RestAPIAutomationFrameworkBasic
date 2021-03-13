Feature: Validate Place API

  @AddPlace
  Scenario Outline: Verify if place is being added successfully
    Given Add Place Payload with "<name>" "<language>" "<address>"
    When user calls "AddPlaceAPI" with "POST" http request
    Then the Api call is success with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify place_Id created maps to "<name>" using "GetPlaceAPI"

    Examples:
    |name|language|address|
    |AAhouse|English|World Cross center|
#    |BBhouse|Spanish|Sea Cross Center|

  @DeletePlace
  Scenario: Verify if Delete Place API is working fine
    Given DeletePlace Payload
    When user calls "DeletePlaceAPI" with "DELETE" http request
    Then the Api call is success with status code 200
    And "status" in response body is "OK"