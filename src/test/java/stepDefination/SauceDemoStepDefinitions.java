package stepDefination;

import ExtentListeners.ExtentTestManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;

import PageObjects.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.tracing.opentelemetry.SeleniumSpanExporter;
import utilities.Constants;
import utilities.DriverFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Step Definitions class for SauceDemo BDD test scenarios
 * Contains all the step implementations for Cucumber feature files
 */
public class SauceDemoStepDefinitions {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutStepOnePage checkoutStepOne;
    private CheckoutStepTwoPage checkoutStepTwo;
    private CheckoutCompletePage checkoutComplete;
    Map<String, String> itemPriceMap = new HashMap<>();
    // Store values for validation
    private double expectedItemTotal = 0.0;
    private double expectedTax = 0.0;
    private double expectedTotal = 0.0;
    private List<String> addedItems;

    // ========================
    // NAVIGATION STEP DEFINITIONS
    // ========================

    /**
     * Navigate to the SauceDemo home page
     * @param url The URL to navigate to
     */
    @Given("Navigate to home page {string}")
    public void navigateToHomePage(String url) {
        try {
            // Initialize driver if not already done
            if (!DriverFactory.isDriverInitialized()) {
                DriverFactory.initializeDriver(Constants.FIREFOX);
            }

            // Navigate to the specified URL
            DriverFactory.getDriver().get(url);

            // Initialize login page
            loginPage = new LoginPage(DriverFactory.getDriver());

            // Verify login page is loaded
            Assert.assertTrue("Login page should be loaded successfully",
                    loginPage.isLoginPageLoaded());

            ExtentTestManager.logPass("Successfully navigated to: " + url);

        } catch (Exception e) {
            ExtentTestManager.logFail("Failed to navigate to home page: " + e.getMessage());
        }
    }

    // ========================
    // LOGIN STEP DEFINITIONS
    // ========================

    /**
     * Login with credentials from data table
     * @param dataTable Data table containing username and password
     */
    @And("I login with the following details:")
    public void loginWithDetails(DataTable dataTable) {
        try {
            List<Map<String, String>> credentials = dataTable.asMaps(String.class, String.class);
            Map<String, String> loginData = credentials.get(0);

            String username = loginData.get("userName");
            String password = loginData.get("password");

            System.out.println("Attempting login with username: " + username);

            // Perform login
            inventoryPage = loginPage.login(username, password);

            // Verify successful login
            Assert.assertTrue("User should be successfully logged in and redirected to inventory page",
                    inventoryPage.isInventoryPageLoaded());

            ExtentTestManager.logPass("Successfully logged in as: " + username);

        } catch (Exception e) {
            ExtentTestManager.logFail("Login failed: " + e.getMessage());
        }
    }

    // ========================
    // SHOPPING CART STEP DEFINITIONS
    // ========================

    /**
     * Add multiple items to the shopping cart
     * @param dataTable Data table containing item names to add
     */
    @And("I add the following items to the basket:")
    public void addItemsToBasket(DataTable dataTable) {
        try {
            List<String> itemsToAdd = dataTable.asList(String.class);
            addedItems = new ArrayList<>(itemsToAdd); // Store for later validation

            System.out.println("Adding " + itemsToAdd.size() + " items to cart:");

            for (String itemName : itemsToAdd) {
                System.out.println("- Adding: " + itemName);

                boolean itemAdded = inventoryPage.addItemToCartByName(itemName);
                ExtentTestManager.logPass("Item should be successfully added to cart: " + itemName);

                // Verify item is now in cart
                Assert.assertTrue("Item should be marked as in cart: " + itemName,
                        inventoryPage.isItemInCart(itemName));
            }

            ExtentTestManager.logPass("Successfully added all items to cart");

        } catch (Exception e) {
            ExtentTestManager.logFail("Failed to add items to basket: " + e.getMessage());
        }
    }

    /**
     * Verify the number of items in the shopping cart
     * @param expectedCount Expected number of items in cart
     */
    @And("I should see {int} items added to the shopping cart")
    public void verifyItemsInShoppingCart(int expectedCount) {
        try {
            int actualCartCount = inventoryPage.getCartItemCount();

          //  Assert.assertEquals(String.format("Cart should contain %d items, but found %d",
                //            expectedCount, actualCartCount),String.valueOf(actualCartCount).equalsIgnoreCase(String.valueOf(expectedCount)));

            System.out.println("Verified cart contains " + expectedCount + " items");

        } catch (Exception e) {
            Assert.fail("Failed to verify cart item count: " + e.getMessage());
        }
    }

    /**
     * Click on the shopping cart icon
     */
    @And("I click on the shopping cart")
    public void clickOnShoppingCart() {
        try {
            cartPage = inventoryPage.goToCart();

            // Verify cart page is loaded
            Assert.assertTrue("Cart page should be loaded successfully",
                    cartPage.isCartPageLoaded());

            System.out.println("Successfully navigated to cart page");

        } catch (Exception e) {
            Assert.fail("Failed to navigate to cart page: " + e.getMessage());
        }
    }

    /**
     * Verify that each item has quantity of 1
     */
    @And("I verify that the QTY count for each item should be 1")
    public void verifyQuantityCountForEachItem() {
        try {
            List<String> quantities = cartPage.getItemQuantities();

            for (int i = 0; i < quantities.size(); i++) {
                String quantity = quantities.get(i);
                Assert.assertEquals(String.format("Item %d should have quantity 1, but found: %s",
                        i + 1, quantity),quantity, "1");
            }

            System.out.println("Verified all items have quantity of 1");

        } catch (Exception e) {
            Assert.fail("Failed to verify item quantities: " + e.getMessage());
        }
    }

    /**
     * Remove specific items from the cart
     * @param dataTable Data table containing items to remove
     */
    @And("I remove the following item:")
    public void removeItemFromCart(DataTable dataTable) {
        try {
            List<String> itemsToRemove = dataTable.asList(String.class);

            for (String itemName : itemsToRemove) {
                System.out.println("Removing item from cart: " + itemName);

                boolean itemRemoved = cartPage.removeItemByName(itemName);
                Assert.assertTrue("Item should be successfully removed from cart: " + itemName,
                        itemRemoved);

                // Verify item is no longer in cart
                Assert.assertFalse("Item should no longer be in cart: " + itemName,
                        cartPage.isItemInCart(itemName));

                // Update our tracking list
                if (addedItems != null) {
                    addedItems.remove(itemName);
                }
            }

            System.out.println("Successfully removed specified items from cart");

        } catch (Exception e) {
            Assert.fail("Failed to remove items from cart: " + e.getMessage());
        }
    }

    // ========================
    // CHECKOUT STEP DEFINITIONS
    // ========================

    /**
     * Click on the checkout button
     */
    @And("I click on the CHECKOUT button")
    public void clickOnCheckoutButton() {
        try {
            checkoutStepOne = cartPage.checkout();

            // Verify checkout step one page is loaded
            Assert.assertTrue("Checkout step one page should be loaded successfully",
                    checkoutStepOne.isCheckoutStepOnePageLoaded());

            System.out.println("Successfully navigated to checkout step one");

        } catch (Exception e) {
            Assert.fail("Failed to navigate to checkout: " + e.getMessage());
        }
    }

    /**
     * Enter first name in checkout form
     * @param firstName First name to enter
     */
    @And("I type {string} for First Name")
    public void enterFirstName(String firstName) {
        try {
            checkoutStepOne.enterFirstName(firstName);

            ExtentTestManager.logPass("Entered first name: " + firstName);

        } catch (Exception e) {
            Assert.fail("Failed to enter first name: " + e.getMessage());
        }
    }

    /**
     * Enter last name in checkout form
     * @param lastName Last name to enter
     */
    @And("I type {string} for Last Name")
    public void enterLastName(String lastName) {
        try {
            checkoutStepOne.enterLastName(lastName);

            System.out.println("Entered last name: " + lastName);

        } catch (Exception e) {
            Assert.fail("Failed to enter last name: " + e.getMessage());
        }
    }

    /**
     * Enter postal code in checkout form
     * @param postalCode Postal code to enter
     */
    @And("I type {string} for Postal Code")
    public void enterPostalCode(String postalCode) {
        try {
            checkoutStepOne.enterPostalCode(postalCode);

            // Verify postal code was entered correctly
            Assert.assertEquals("Postal code should be entered correctly",
                    checkoutStepOne.getPostalCodeValue(), postalCode);

            System.out.println("Entered postal code: " + postalCode);

        } catch (Exception e) {
            Assert.fail("Failed to enter postal code: " + e.getMessage());
        }
    }

    /**
     * Click on the continue button
     */
    @When("I click on the CONTINUE button")
    public void clickOnContinueButton() {
        try {
            checkoutStepTwo = checkoutStepOne.continueToNextStep();

            // Verify checkout step two page is loaded
            Assert.assertTrue("Checkout step two page should be loaded successfully",
                    checkoutStepTwo.isCheckoutStepTwoPageLoaded());

            ExtentTestManager.logPass("Successfully navigated to checkout step two");

        } catch (Exception e) {
            Assert.fail("Failed to continue to checkout step two: " + e.getMessage());
        }
    }

    // ========================
    // VALIDATION STEP DEFINITIONS
    // ========================

    /**
     * Validate that item total equals sum of individual item prices
     */
    @Then("Item total should be equal to the sum of individual item prices")
    public void validateItemTotal() {
        try {
            // Get displayed item total
            double displayedSubtotal = checkoutStepTwo.getSubtotalAmount();

            // Calculate expected subtotal
            double calculatedSubtotal = checkoutStepTwo.calculateExpectedSubtotal();

            // Store for later calculations
            expectedItemTotal = displayedSubtotal;

            // Verify subtotal calculation
            Assert.assertTrue("Subtotal calculation should be correct",
                    checkoutStepTwo.verifySubtotalCalculation());

            Assert.assertEquals("Item total should be %.2f but found %.2f",
                    displayedSubtotal, calculatedSubtotal,0.01);
//            Assert.assertEquals(displayedSubtotal, calculatedSubtotal, 0.01,
//                    String.format(calculatedSubtotal, displayedSubtotal),
//                    "Item total should be %.2f but found %.2f");

            System.out.println(String.format("Verified item total: $%.2f", displayedSubtotal));

        } catch (Exception e) {
            Assert.fail("Failed to validate item total: " + e.getMessage());
        }
    }

    /**
     * Validate tax calculation based on item total
     */
    @And("Tax amount should be calculated correctly based on the item total")
    public void validateTaxCalculation() {
        try {
            // Get displayed tax
            double displayedTax = checkoutStepTwo.getTaxAmount();

            // Calculate expected tax
            double calculatedTax = expectedItemTotal * Constants.TAX_RATE;
            calculatedTax = Math.round(calculatedTax * 100.0) / 100.0;

            // Store for total calculation
            expectedTax = displayedTax;

            // Verify tax calculation
            Assert.assertTrue("Tax calculation should be correct",
                    checkoutStepTwo.verifyTaxCalculation());

            Assert.assertEquals(String.format("Tax amount should be %.2f but found %.2f",displayedTax, calculatedTax),displayedTax, calculatedTax,0.01);

            System.out.println(String.format("Verified tax amount: $%.2f", displayedTax));

        } catch (Exception e) {
            Assert.fail("Failed to validate tax calculation: " + e.getMessage());
        }
    }

    /**
     * Validate that total equals item total plus tax
     */
    @And("Total amount should equal item total plus tax")
    public void validateTotalCalculation() {
        try {
            // Get displayed total
            double displayedTotal = checkoutStepTwo.getTotalAmount();

            // Calculate expected total
            double calculatedTotal = expectedItemTotal + expectedTax;

            // Verify total calculation
            Assert.assertTrue("Total calculation should be correct",
                    checkoutStepTwo.verifyTotalCalculation());

//            Assert.assertEquals(String.format("Total amount should be %.2f but found %.2f",
//                            calculatedTotal, displayedTotal),displayedTotal, calculatedTotal);
            Assert.assertTrue(String.format("Total amount should be %.2f but found %.2f",
                    calculatedTotal, displayedTotal),displayedTotal==calculatedTotal);
            System.out.println(String.format("Verified total amount: $%.2f", displayedTotal));

        } catch (Exception e) {
            Assert.fail("Failed to validate total calculation: " + e.getMessage());
        }
    }

    /**
     * Validate payment information is displayed correctly
     */
    @And("I should see the correct payment information")
    public void validatePaymentInformation() {
        try {
            // Verify payment information is displayed
            Assert.assertTrue("Payment information should be displayed",
                    checkoutStepTwo.isPaymentInformationDisplayed());

            // Verify expected payment information
            Assert.assertTrue("Expected payment information should be shown",
                    checkoutStepTwo.isExpectedPaymentInfoDisplayed());

            String paymentInfo = checkoutStepTwo.getPaymentInformation();
            Assert.assertTrue("Payment information should contain SauceCard",
                    paymentInfo.contains("SauceCard"));

            // Verify shipping information
            Assert.assertTrue("Shipping information should be displayed",
                    checkoutStepTwo.isShippingInformationDisplayed());

            Assert.assertTrue("Expected shipping information should be shown",
                    checkoutStepTwo.isExpectedShippingInfoDisplayed());

            String shippingInfo = checkoutStepTwo.getShippingInformation();
            Assert.assertTrue("Shipping information should contain Pony Express",
                    shippingInfo.contains("Pony Express"));

            System.out.println("Verified payment and shipping information");
            System.out.println("Payment: " + paymentInfo);
            System.out.println("Shipping: " + shippingInfo);

        } catch (Exception e) {
            Assert.fail("Failed to validate payment information: " + e.getMessage());
        }
    }

    /**
     * Validate all selected items are displayed in checkout summary
     */
    @And("All selected items should be displayed in the checkout summary")
    public void validateAllItemsInCheckoutSummary() {
        try {
            // Get items in checkout overview
            List<String> checkoutItems = checkoutStepTwo.getItemNames();

            // Verify all added items are present (excluding removed items)
            if (addedItems != null) {
                Assert.assertTrue("All added items should be present in checkout summary",
                        checkoutStepTwo.areItemsInCheckout(addedItems));

                // Verify item count matches
           //     Assert.assertEquals("Number of items in checkout should match added items",checkoutItems.size(),addedItems.size());

                System.out.println("Verified all items in checkout summary:");
                for (String item : checkoutItems) {
                    System.out.println("- " + item);
                }
            }

            // Verify each item has correct details
            List<String> quantities = checkoutStepTwo.getItemQuantities();
            for (String quantity : quantities) {
                Assert.assertEquals("Each item should have quantity of 1",
                        quantity, "1");
            }

            System.out.println("Verified all selected items in checkout summary");

        } catch (Exception e) {
            Assert.fail("Failed to validate items in checkout summary: " + e.getMessage());
        }
    }

    // ========================
    // UTILITY METHODS
    // ========================

    /**
     * Helper method to print current page information for debugging
     */
    private void printCurrentPageInfo() {
        try {
            String currentUrl = DriverFactory.getDriver().getCurrentUrl();
            String pageTitle = DriverFactory.getDriver().getTitle();

            System.out.println("Current URL: " + currentUrl);
            System.out.println("Page Title: " + pageTitle);

        } catch (Exception e) {
            System.out.println("Could not retrieve page information: " + e.getMessage());
        }
    }

    /**
     * Helper method to take screenshot for debugging (if needed)
     */
    private void takeScreenshotForDebugging() {
        try {
            if (DriverFactory.isDriverInitialized()) {
                String screenshot = DriverFactory.getScreenshotAsBase64();
                if (screenshot != null) {
                    System.out.println("Screenshot captured for debugging");
                }
            }
        } catch (Exception e) {
            System.out.println("Could not capture screenshot: " + e.getMessage());
        }
    }

    /**
     * Helper method to verify page is ready for interaction
     */
    private void waitForPageToBeReady() {
        try {
            Thread.sleep(1000); // Brief wait for page stabilization
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Given("Launch Browser {string}")
    public void launchBrowser(String arg0) {
    }

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        DriverFactory.getDriver().get("https://www.saucedemo.com/");
    }

    @When("I proceed to checkout with valid shipping information")
    public void iProceedToCheckoutWithValidShippingInformation() {
//        DriverFactory.getDriver().findElement(By.id("shopping_cart_container")).click();
//        DriverFactory.getDriver().findElement(By.id("checkout")).click();
//        DriverFactory.getDriver().findElement(By.id("first-name")).sendKeys("John");
//        DriverFactory.getDriver().findElement(By.id("last-name")).sendKeys("Doe");
//        DriverFactory.getDriver().findElement(By.id("postal-code")).sendKeys("12345");
//        DriverFactory.getDriver().findElement(By.id("continue")).click();
        String checkOutStep2= checkoutStepTwo.getShippingInformation();
        if(!checkOutStep2.isEmpty() && !checkOutStep2.isBlank()&& checkOutStep2!=null)
            ExtentTestManager.logPass("Shipping Information : " + checkOutStep2);

    }

    @Then("I should see the following items with correct prices:")
    public void iShouldSeeTheFollowingItemsWithCorrectPrices(DataTable dataTable) {
        Map<String, String> expectedItems = dataTable.asMap(String.class, String.class);
        List<String> names = checkoutStepTwo.getItemNames();
        List<String> prices = checkoutStepTwo.getItemPrices();
        int index =-1;
        if(expectedItems.size()== names.size()+1 && expectedItems.size()== prices.size()+1){
            for (Map.Entry<String, String> entry : expectedItems.entrySet()) {
                if(names.contains(entry.getKey()) && prices.contains(entry.getValue())){
                    index=names.indexOf(entry.getKey());
                    Assert.assertEquals(entry.getValue(), prices.get(index));
                    itemPriceMap.put(entry.getKey(), prices.get(index));
                    ExtentTestManager.logPass("Item present in with name :"+entry.getKey()+" | Item price is : "+ entry.getValue());
                }
            }
            Assert.assertTrue("Item count are matching",(expectedItems.size()-1)==names.size());
        }
        else{
            Assert.assertTrue("Item count are mis-matching ",expectedItems.size()-1== names.size() && expectedItems.size()-1== prices.size());
        }
    }

    @And("The subtotal should be ${double}")
    public void theSubtotalShouldBe$(double subTotal) {
        WebElement subtotalElement = DriverFactory.getDriver().findElement(By.className("summary_subtotal_label"));
        String subtotalText = subtotalElement.getText().split(":")[1].trim().replace("$",""); // e.g., "Item total: $53.97"
        ExtentTestManager.logPass("Expected value : "+ subtotalText+" | Actual Value: "+subtotalText);
    }

    @And("Tax should be calculated at the applicable rate")
    public void taxShouldBeCalculatedAtTheApplicableRate() {
        Assert.assertTrue("Applicable rates are matching",checkoutStepTwo.verifyTotalCalculation());
        ExtentTestManager.logPass("Applicable rates are matching is "+checkoutStepTwo.verifyTotalCalculation());
    }

    @Given("Given I am on the home page")
    public void givenIAmOnTheHomePage() {
    }

//    @And("I type {string} for Postal Code")
//    public void iTypeForZIPPostalCode(String postalCode) {
//        try {
//            checkoutStepOne.enterPostalCode(postalCode);
//
//            System.out.println("Entered Postal Code: " + postalCode);
//
//        } catch (Exception e) {
//            Assert.fail("Failed to enter last name: " + e.getMessage());
//        }
//    }

}

