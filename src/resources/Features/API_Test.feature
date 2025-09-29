Feature: API test

  Background:
    Given BaseURL "https://reqres.in"

  Scenario Outline: Should see LIST USERS of all existing users
    Given get the default list of users for on page "/api/users?page=<page>"
    When status is <status_code>
    When get the list of all users within every page "/api/users?page="
    Then I should see total users count equals the number of user ids "/api/users?page="
    Examples:
      | page | status_code |
      | 1    | 200         |

  Scenario Outline: Should see SINGLE USER data
    Given search for user id on page "/api/users/<id>"
    When status is <status_code>
    Examples:
      | id |status_code|
      | 3  |200        |

  Scenario Outline: Should see SINGLE USER data
    Given search for user id on page "/api/users/<id>"
    When the response status code should be "<status_code>"
    And the response should contain the field "error"
    Examples:
      | id |status_code|error|
      | 55 |404   |     |

  Scenario Outline: Should see Specific USER data
    Given Search user data "<name>" and "<email>" on page "/api/users?page="
    Examples:
      | name | email               |
      | Emma | emma.wong@reqres.in |



  Scenario Outline: Create a new user
    Given the system is running and the Create User API is available
    When I send a POST request to "/api/users" with the following details: "<name>" and "<job>"
    Then the response status code should be "<status>"
    And the response should contain the field "id"
    And the response should contain the field "createdAt"
    And the response field "name" should be "<name>"
    And the response field "job" should be "<job>"
    Examples:
      | name | job |status|
      | Peter | Manager |201|
      | Liza | Sales |201   |


  Scenario Outline: LOGIN - SUCCESSFUL by a user
    Given I send POST request to "/api/login" login with the "<Email>" and "<Password>"
    Then the response status code should be "<Status>"
    And the response should contain the field "token"
    Examples:
      | Email                | Password   |Status|
      | eve.holt@reqres.in   | cityslicka |200    |

  Scenario Outline: LOGIN - UNSUCCESSFUL by a user
    Given I send POST request to "/api/login" login with the "<Email>" and "<Password>"
    Then the response status code should be "<Status>"
    And the response should contain the field "error"
    Examples:
      | Email              | Password | Status |
      | eve.holt@reqres.in |          | 400    |


  Scenario: Should see the list of users with DELAYED RESPONSE
    Given I send get request to "/api/users?delay=3" wait for the user list to load
    Then I should see that every user has a unique id