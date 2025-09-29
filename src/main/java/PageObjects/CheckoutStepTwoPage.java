package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.Constants;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * CheckoutStepTwoPage class represents the checkout overview/confirmation page in SauceDemo application
 * Contains methods to review order details, verify calculations, and complete the purchase
 * URL: https://www.saucedemo.com/checkout-step-two.html
 */
public class CheckoutStepTwoPage extends BasePage {

    // ========================
    // PAGE HEADER ELEMENTS
    // ========================

    /** Page title element */
    @FindBy(className = "title")
    private WebElement pageTitle;

    /** Secondary header container */
    @FindBy(className = "header_secondary_container")
    private WebElement secondaryHeader;

    // ========================
    // CART ITEMS IN CHECKOUT
    // ========================

    /** List of cart items displayed in checkout overview */
    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    /** List of item names in checkout */
    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    /** List of item descriptions in checkout */
    @FindBy(className = "inventory_item_desc")
    private List<WebElement> itemDescriptions;

    /** List of item prices in checkout */
    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    /** List of item quantities in checkout */
    @FindBy(className = "cart_quantity")
    private List<WebElement> itemQuantities;

    // ========================
    // SUMMARY INFORMATION
    // ========================

    /** Summary information container */
    @FindBy(className = "summary_info")
    private WebElement summaryInfo;

    /** List of summary info labels (Payment Information, Shipping Information) */
    @FindBy(className = "summary_info_label")
    private List<WebElement> summaryLabels;

    /** List of summary info values */
    @FindBy(className = "summary_value_label")
    private List<WebElement> summaryValues;

    // ========================
    // PRICE SUMMARY ELEMENTS
    // ========================

    /** Item subtotal label and value */
    @FindBy(className = "summary_subtotal_label")
    private WebElement subtotalLabel;

    /** Tax amount label and value */
    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    /** Total amount label and value */
    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    // ========================
    // ACTION BUTTONS
    // ========================

    /** Finish button to complete the order */
    @FindBy(id = "finish")
    private WebElement finishButton;

    /** Cancel button to return to inventory */
    @FindBy(id = "cancel")
    private WebElement cancelButton;

    // ========================
    // CONSTRUCTOR
    // ========================

    /**
     * Constructor to initialize CheckoutStepTwoPage
     * @param driver WebDriver instance
     */
    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    // ========================
    // PAGE INFORMATION METHODS
    // ========================

    /**
     * Get page title text
     * @return Page title as String
     */
    public String getPageTitle() {
        return wait.until(ExpectedConditions.visibilityOf(pageTitle)).getText();
    }

    /**
     * Check if checkout step two page is loaded
     * @return true if page is loaded correctly, false otherwise
     */
    public boolean isCheckoutStepTwoPageLoaded() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(pageTitle))
                    .getText().equals(Constants.CHECKOUT_STEP_TWO_TITLE);
        } catch (Exception e) {
            return false;
        }
    }

    // ========================
    // CART ITEMS INFORMATION
    // ========================

    /**
     * Get list of all item names in checkout overview
     * @return List of item names as Strings
     */
    public List<String> getItemNames() {
        return itemNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Get list of all item descriptions in checkout overview
     * @return List of item descriptions as Strings
     */
    public List<String> getItemDescriptions() {
        return itemDescriptions.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Get list of all item prices in checkout overview
     * @return List of item prices as Strings
     */
    public List<String> getItemPrices() {
        return itemPrices.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Get list of all item quantities in checkout overview
     * @return List of item quantities as Strings
     */
    public List<String> getItemQuantities() {
        return itemQuantities.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Get list of all item prices as double values
     * @return List of item prices as doubles
     */
    public List<Double> getItemPricesAsDouble() {
        List<Double> prices = new ArrayList<>();
        for (String priceText : getItemPrices()) {
            try {
                double price = Double.parseDouble(priceText.replace("$", ""));
                prices.add(price);
            } catch (NumberFormatException e) {
                prices.add(0.0);
            }
        }
        return prices;
    }

    /**
     * Get total number of items in checkout overview
     * @return Number of items
     */
    public int getItemsCount() {
        return cartItems.size();
    }

    /**
     * Get total quantity of all items (sum of all quantities)
     * @return Total quantity as integer
     */
    public int getTotalItemQuantity() {
        int totalQuantity = 0;
        for (WebElement quantityElement : itemQuantities) {
            try {
                totalQuantity += Integer.parseInt(quantityElement.getText().trim());
            } catch (NumberFormatException e) {
                // If quantity is not a number, assume 1
                totalQuantity += 1;
            }
        }
        return totalQuantity;
    }

    // ========================
    // PRICE INFORMATION METHODS
    // ========================

    /**
     * Get subtotal text from the page
     * @return Subtotal text as String (e.g., "Item total: $29.99")
     */
    public String getSubtotal() {
        return wait.until(ExpectedConditions.visibilityOf(subtotalLabel)).getText();
    }

    /**
     * Get tax text from the page
     * @return Tax text as String (e.g., "Tax: $2.40")
     */
    public String getTax() {
        return wait.until(ExpectedConditions.visibilityOf(taxLabel)).getText();
    }

    /**
     * Get total text from the page
     * @return Total text as String (e.g., "Total: $32.39")
     */
    public String getTotal() {
        return wait.until(ExpectedConditions.visibilityOf(totalLabel)).getText();
    }

    /**
     * Get subtotal amount as double value
     * @return Subtotal amount as double
     */
    public double getSubtotalAmount() {
        String subtotalText = getSubtotal();
        try {
            // Extract number from "Item total: $29.99"
            String amountText = subtotalText.substring(subtotalText.lastIndexOf("$") + 1);
            return Double.parseDouble(amountText);
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Get tax amount as double value
     * @return Tax amount as double
     */
    public double getTaxAmount() {
        String taxText = getTax();
        try {
            // Extract number from "Tax: $2.40"
            String amountText = taxText.substring(taxText.lastIndexOf("$") + 1);
            return Double.parseDouble(amountText);
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Get total amount as double value
     * @return Total amount as double
     */
    public double getTotalAmount() {
        String totalText = getTotal();
        try {
            // Extract number from "Total: $32.39"
            String amountText = totalText.substring(totalText.lastIndexOf("$") + 1);
            return Double.parseDouble(amountText);
        } catch (Exception e) {
            return 0.0;
        }
    }

    // ========================
    // SUMMARY INFORMATION METHODS
    // ========================

    /**
     * Get payment information displayed in checkout
     * @return Payment information as String
     */
    public String getPaymentInformation() {
        for (int i = 0; i < summaryLabels.size(); i++) {
            String labelText = summaryLabels.get(i).getText();
            if (labelText.contains("Payment Information")) {
                if (i < summaryValues.size()) {
                    return summaryValues.get(i).getText();
                }
            }
        }
        return "";
    }

    /**
     * Get shipping information displayed in checkout
     * @return Shipping information as String
     */
    public String getShippingInformation() {
        for (int i = 0; i < summaryLabels.size(); i++) {
            String labelText = summaryLabels.get(i).getText();
            if (labelText.contains("Shipping Information")) {
                if (i < summaryValues.size()) {
                    return summaryValues.get(i).getText();
                }
            }
        }
        return "";
    }

    /**
     * Get all summary information as a formatted string
     * @return Summary information as String
     */
    public String getAllSummaryInformation() {
        StringBuilder summary = new StringBuilder();

        try {
            summary.append("Order Summary Information:\n");
            summary.append("========================\n");

            for (int i = 0; i < summaryLabels.size() && i < summaryValues.size(); i++) {
                summary.append(summaryLabels.get(i).getText()).append(": ")
                        .append(summaryValues.get(i).getText()).append("\n");
            }

            summary.append("\nPrice Breakdown:\n");
            summary.append("================\n");
            summary.append(getSubtotal()).append("\n");
            summary.append(getTax()).append("\n");
            summary.append(getTotal()).append("\n");

        } catch (Exception e) {
            summary.append("Error retrieving summary information: ").append(e.getMessage());
        }

        return summary.toString();
    }

    // ========================
    // PRICE CALCULATION VALIDATION
    // ========================

    /**
     * Verify that total calculation is correct (subtotal + tax = total)
     * @return true if calculation is correct, false otherwise
     */
    public boolean verifyTotalCalculation() {
        try {
            double subtotal = getSubtotalAmount();
            double tax = getTaxAmount();
            double total = getTotalAmount();
            double calculatedTotal = subtotal + tax;

            // Allow for small rounding differences (within 1 cent)
            return Math.abs(calculatedTotal - total) < 0.01;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Calculate expected subtotal based on item prices and quantities
     * @return Expected subtotal as double
     */
    public double calculateExpectedSubtotal() {
        double expectedSubtotal = 0.0;
        List<Double> prices = getItemPricesAsDouble();
        List<String> quantities = getItemQuantities();

        for (int i = 0; i < prices.size() && i < quantities.size(); i++) {
            try {
                int quantity = Integer.parseInt(quantities.get(i).trim());
                expectedSubtotal += prices.get(i) * quantity;
            } catch (NumberFormatException e) {
                // If quantity is not a number, assume 1
                expectedSubtotal += prices.get(i);
            }
        }

        return Math.round(expectedSubtotal * 100.0) / 100.0; // Round to 2 decimal places
    }

    /**
     * Verify subtotal matches calculated expected value
     * @return true if subtotal is correct, false otherwise
     */
    public boolean verifySubtotalCalculation() {
        try {
            double displayedSubtotal = getSubtotalAmount();
            double expectedSubtotal = calculateExpectedSubtotal();

            // Allow for small rounding differences (within 1 cent)
            return Math.abs(displayedSubtotal - expectedSubtotal) < 0.01;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify tax calculation based on subtotal and tax rate
     * @return true if tax calculation is correct, false otherwise
     */
    public boolean verifyTaxCalculation() {
        try {
            double subtotal = getSubtotalAmount();
            double displayedTax = getTaxAmount();
            double expectedTax = subtotal * Constants.TAX_RATE;

            // Round expected tax to 2 decimal places
            expectedTax = Math.round(expectedTax * 100.0) / 100.0;

            // Allow for small rounding differences (within 1 cent)
            return Math.abs(displayedTax - expectedTax) < 0.01;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify all price calculations are correct
     * @return true if all calculations are correct, false otherwise
     */
    public boolean verifyAllPriceCalculations() {
        return verifySubtotalCalculation() &&
                verifyTaxCalculation() &&
                verifyTotalCalculation();
    }

    // ========================
    // ORDER VERIFICATION METHODS
    // ========================

    /**
     * Verify specific item is in the checkout overview
     * @param itemName Name of the item to verify
     * @return true if item is present, false otherwise
     */
    public boolean isItemInCheckout(String itemName) {
        return getItemNames().contains(itemName);
    }

    /**
     * Verify multiple items are in the checkout overview
     * @param itemNames List of item names to verify
     * @return true if all items are present, false otherwise
     */
    public boolean areItemsInCheckout(List<String> itemNames) {
        List<String> checkoutItems = getItemNames();
        return checkoutItems.containsAll(itemNames);
    }

    /**
     * Get item quantity by name
     * @param itemName Name of the item
     * @return Quantity as String, or null if item not found
     */
    public String getItemQuantityByName(String itemName) {
        List<String> names = getItemNames();
        List<String> quantities = getItemQuantities();

        for (int i = 0; i < names.size() && i < quantities.size(); i++) {
            if (names.get(i).equals(itemName)) {
                return quantities.get(i);
            }
        }
        return null;
    }

    /**
     * Get item price by name
     * @param itemName Name of the item
     * @return Price as String, or null if item not found
     */
    public String getItemPriceByName(String itemName) {
        List<String> names = getItemNames();
        List<String> prices = getItemPrices();

        for (int i = 0; i < names.size() && i < prices.size(); i++) {
            if (names.get(i).equals(itemName)) {
                return prices.get(i);
            }
        }
        return null;
    }

    // ========================
    // SUMMARY VALIDATION METHODS
    // ========================

    /**
     * Verify payment information is displayed correctly
     * @return true if payment information is present, false otherwise
     */
    public boolean isPaymentInformationDisplayed() {
        String paymentInfo = getPaymentInformation();
        return paymentInfo != null && !paymentInfo.trim().isEmpty();
    }

    /**
     * Verify shipping information is displayed correctly
     * @return true if shipping information is present, false otherwise
     */
    public boolean isShippingInformationDisplayed() {
        String shippingInfo = getShippingInformation();
        return shippingInfo != null && !shippingInfo.trim().isEmpty();
    }

    /**
     * Verify expected payment information is displayed
     * @return true if expected payment info is shown, false otherwise
     */
    public boolean isExpectedPaymentInfoDisplayed() {
        String paymentInfo = getPaymentInformation();
        return paymentInfo.contains("SauceCard") || paymentInfo.contains(Constants.PAYMENT_INFO);
    }

    /**
     * Verify expected shipping information is displayed
     * @return true if expected shipping info is shown, false otherwise
     */
    public boolean isExpectedShippingInfoDisplayed() {
        String shippingInfo = getShippingInformation();
        return shippingInfo.contains("Pony Express") || shippingInfo.contains(Constants.SHIPPING_INFO);
    }

    // ========================
    // NAVIGATION METHODS
    // ========================

    /**
     * Complete the order by clicking finish button
     * @return CheckoutCompletePage object
     */
    public CheckoutCompletePage finish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();
        return new CheckoutCompletePage(driver);
    }

    /**
     * Cancel the checkout and return to inventory page
     * @return InventoryPage object
     */
    public InventoryPage cancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
        return new InventoryPage(driver);
    }

    // ========================
    // BUTTON STATE METHODS
    // ========================

    /**
     * Check if finish button is enabled and clickable
     * @return true if button is clickable, false otherwise
     */
    public boolean isFinishButtonEnabled() {
        try {
            return finishButton.isDisplayed() && finishButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if cancel button is enabled and clickable
     * @return true if button is clickable, false otherwise
     */
    public boolean isCancelButtonEnabled() {
        try {
            return cancelButton.isDisplayed() && cancelButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get finish button text
     * @return Button text as String
     */
    public String getFinishButtonText() {
        return finishButton.getText();
    }

    /**
     * Get cancel button text
     * @return Button text as String
     */
    public String getCancelButtonText() {
        return cancelButton.getText();
    }

    // ========================
    // CHECKOUT OVERVIEW METHODS
    // ========================

    /**
     * Get complete checkout overview information
     * @return Checkout overview as formatted String
     */
    public String getCheckoutOverview() {
        StringBuilder overview = new StringBuilder();

        try {
            overview.append("Checkout Overview:\n");
            overview.append("==================\n");
            overview.append("Page Title: ").append(getPageTitle()).append("\n");
            overview.append("Number of Items: ").append(getItemsCount()).append("\n");
            overview.append("Total Quantity: ").append(getTotalItemQuantity()).append("\n");
            overview.append("\nItems in Order:\n");

            List<String> names = getItemNames();
            List<String> prices = getItemPrices();
            List<String> quantities = getItemQuantities();

            for (int i = 0; i < names.size(); i++) {
                overview.append("- ").append(names.get(i));
                if (i < prices.size()) {
                    overview.append(" (Price: ").append(prices.get(i));
                }
                if (i < quantities.size()) {
                    overview.append(", Qty: ").append(quantities.get(i));
                }
                overview.append(")\n");
            }

            overview.append("\n").append(getAllSummaryInformation());
            overview.append("\nPrice Validation:\n");
            overview.append("=================\n");
            overview.append("Subtotal Correct: ").append(verifySubtotalCalculation()).append("\n");
            overview.append("Tax Correct: ").append(verifyTaxCalculation()).append("\n");
            overview.append("Total Correct: ").append(verifyTotalCalculation()).append("\n");

        } catch (Exception e) {
            overview.append("Error generating checkout overview: ").append(e.getMessage());
        }

        return overview.toString();
    }

    // ========================
    // WAIT HELPER METHODS
    // ========================

    /**
     * Wait for checkout step two page to fully load
     * @param timeoutSeconds Timeout in seconds
     * @return true if page loaded successfully, false otherwise
     */
    public boolean waitForPageLoad(int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(ExpectedConditions.visibilityOf(pageTitle));
            wait.until(ExpectedConditions.visibilityOf(summaryInfo));
            wait.until(ExpectedConditions.visibilityOf(subtotalLabel));
            wait.until(ExpectedConditions.visibilityOf(taxLabel));
            wait.until(ExpectedConditions.visibilityOf(totalLabel));
            wait.until(ExpectedConditions.elementToBeClickable(finishButton));
            wait.until(ExpectedConditions.elementToBeClickable(cancelButton));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for price calculations to complete and be accurate
     * @param timeoutSeconds Timeout in seconds
     * @return true if calculations are correct, false otherwise
     */
    public boolean waitForPriceCalculations(int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(driver -> verifyAllPriceCalculations());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

