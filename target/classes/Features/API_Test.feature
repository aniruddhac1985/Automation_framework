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
    Examples:
      | id |
      | 3  |

  Scenario Outline: Should see Specific USER data
    Given Search user data "<name>" and "<email>" on page "/api/users?page="
    Examples:
      | name | email               |
      | Emma | emma.wong@reqres.in |

