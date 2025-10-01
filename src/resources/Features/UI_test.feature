Feature: Checkout items in the basket
  As a customer
  I want to checkout items in my basket
  So that I can verify the total cost and tax calculations are correct

  Background:
#    Given Launch Browser "chrome"

  Scenario Outline: Check item total cost and tax
    Given Navigate to home page "https://www.saucedemo.com/"
    And I login with the following details:
      | userName      | password     |
      | standard_user | secret_sauce |
    And I add the following items to the basket:
      | Sauce Labs Backpack        |
      | Sauce Labs Fleece Jacket   |
      | Sauce Labs Bolt T-Shirt    |
      | Sauce Labs Onesie          |
    And I should see 4 items added to the shopping cart
    And I click on the shopping cart
    And I verify that the QTY count for each item should be 1
    And I remove the following item:
      | Sauce Labs Fleece Jacket |
    And I should see 3 items added to the shopping cart
    And I click on the CHECKOUT button
    And I type "<FirstName>" for First Name
    And I type "<LastName>" for Last Name
    And I type "<PostalCode>" for Postal Code
    When I click on the CONTINUE button
    Then Item total should be equal to the sum of individual item prices
    And Tax amount should be calculated correctly based on the item total
    And Total amount should equal item total plus tax
    And I should see the correct payment information
    And All selected items should be displayed in the checkout summary
  Examples:
  |FirstName|LastName|PostalCode|
  |Aniruddha|Chavan  |EC1A 9JU  |

  Scenario: Verify individual item prices in checkout summary
#    Given Navigate to home page "https://www.saucedemo.com/"
    Given Given I am on the home page
    And I login with the following details:
      | userName      | password     |
      | standard_user | secret_sauce |
    And I add the following items to the basket:
      | Sauce Labs Backpack     |
      | Sauce Labs Bolt T-Shirt |
      | Sauce Labs Onesie       |
    And I click on the shopping cart
    And I click on the CHECKOUT button
    And I type "Aniruddha" for First Name
    And I type "Chavan" for Last Name
    And I type "421201" for Postal Code
    When I click on the CONTINUE button
    When I proceed to checkout with valid shipping information
    Then I should see the following items with correct prices:
      | Item Name               | Price  |
      | Sauce Labs Backpack     | $29.99 |
      | Sauce Labs Bolt T-Shirt | $15.99 |
      | Sauce Labs Onesie       | $7.99  |
    And The subtotal should be $53.97
    And Tax should be calculated at the applicable rate
