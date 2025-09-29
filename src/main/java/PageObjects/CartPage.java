package PageObjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import utilities.Constants;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * CartPage class represents the shopping cart page of SauceDemo application
 * Contains methods to interact with cart items, quantities, and checkout process
 * URL: https://www.saucedemo.com/cart.html
 */
public class CartPage extends BasePage {

    // ========================
    // PAGE HEADER ELEMENTS
    // ========================

    /** Page title element */
    @FindBy(className = "title")
    private WebElement pageTitle;

    /** Cart quantity label header */
    @FindBy(className = "cart_quantity_label")
    private WebElement quantityLabel;

    /** Cart description label header */
    @FindBy(className = "cart_desc_label")
    private WebElement descriptionLabel;

    // ========================
    // CART ITEMS ELEMENTS
    // ========================

    /** List of all cart items */
    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    /** List of item names in cart */
    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    /** List of item descriptions in cart */
    @FindBy(className = "inventory_item_desc")
    private List<WebElement> itemDescriptions;

    /** List of item prices in cart */
    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    /** List of item quantities in cart */
    @FindBy(className = "cart_quantity")
    private List<WebElement> itemQuantities;

    /** List of remove buttons for cart items */
    @FindBy(css = "button[id^='remove']")
    private List<WebElement> removeButtons;

    /** List of cart item links (clickable item names) */
    @FindBy(css = ".cart_item .inventory_item_name")
    private List<WebElement> itemNameLinks;

    // ========================
    // ACTION BUTTONS
    // ========================

    /** Continue shopping button */
    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    /** Checkout button */
    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    // ========================
    // CONSTRUCTOR
    // ========================

    /**
     * Constructor to initialize CartPage
     * @param driver WebDriver instance
     */
    public CartPage(WebDriver driver) {
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
     * Get quantity label text
     * @return Quantity label text as String
     */
    public String getQuantityLabel() {
        return quantityLabel.getText();
    }

    /**
     * Get description label text
     * @return Description label text as String
     */
    public String getDescriptionLabel() {
        return descriptionLabel.getText();
    }

    // ========================
    // CART ITEMS COUNT METHODS
    // ========================

    /**
     * Get total number of cart items
     * @return Number of items in cart
     */
    public int getCartItemsCount() {
        return cartItems.size();
    }

    /**
     * Get total quantity of all items (sum of all quantities)
     * @return Total quantity as integer
     */
    public int getTotalQuantity() {
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
    // CART ITEMS INFORMATION METHODS
    // ========================

    /**
     * Get list of all item names in cart
     * @return List of item names as Strings
     */
    public List<String> getItemNames() {
        return itemNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Get list of all item descriptions in cart
     * @return List of item descriptions as Strings
     */
    public List<String> getItemDescriptions() {
        return itemDescriptions.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Get list of all item prices in cart
     * @return List of item prices as Strings
     */
    public List<String> getItemPrices() {
        return itemPrices.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Get list of all item quantities in cart
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

    // ========================
    // SPECIFIC ITEM METHODS
    // ========================

    /**
     * Get price of specific item by name
     * @param itemName Name of the item
     * @return Price as String, or null if item not found
     */
    public String getItemPrice(String itemName) {
        List<String> names = getItemNames();
        List<String> prices = getItemPrices();

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(itemName)) {
                return prices.get(i);
            }
        }
        return null;
    }

    /**
     * Get quantity of specific item by name
     * @param itemName Name of the item
     * @return Quantity as String, or null if item not found
     */
    public String getItemQuantity(String itemName) {
        List<String> names = getItemNames();
        List<String> quantities = getItemQuantities();

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(itemName)) {
                return quantities.get(i);
            }
        }
        return null;
    }

    /**
     * Get description of specific item by name
     * @param itemName Name of the item
     * @return Description as String, or null if item not found
     */
    public String getItemDescription(String itemName) {
        List<String> names = getItemNames();
        List<String> descriptions = getItemDescriptions();

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(itemName)) {
                return descriptions.get(i);
            }
        }
        return null;
    }

    // ========================
    // CART MANAGEMENT METHODS
    // ========================

    /**
     * Remove item from cart by item name
     * @param itemName Name of the item to remove
     * @return true if item was found and removed, false otherwise
     */
    public boolean removeItemByName(String itemName) {
        List<String> names = getItemNames();

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(itemName)) {
                removeButtons.get(i).click();
                return true;
            }
        }
        return false;
    }

    /**
     * Remove item from cart by index
     * @param index Index of the item to remove (0-based)
     * @return true if item was removed, false if index is invalid
     */
    public boolean removeItemByIndex(int index) {
        if (index >= 0 && index < removeButtons.size()) {
            removeButtons.get(index).click();
            return true;
        }
        return false;
    }

    /**
     * Remove all items from cart
     * @return Number of items that were removed
     */
    public int removeAllItems() {
        int itemsRemoved = 0;

        // Remove items from the end to avoid index issues
        for (int i = removeButtons.size() - 1; i >= 0; i--) {
            try {
                removeButtons.get(i).click();
                itemsRemoved++;

                // Wait briefly for the item to be removed from DOM
                Thread.sleep(500);
            } catch (Exception e) {
                // Continue with next item if there's an issue
                continue;
            }
        }

        return itemsRemoved;
    }

    // ========================
    // CART VALIDATION METHODS
    // ========================

    /**
     * Check if cart is empty
     * @return true if cart has no items, false otherwise
     */
    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }

    /**
     * Check if specific item is in cart
     * @param itemName Name of the item to check
     * @return true if item is in cart, false otherwise
     */
    public boolean isItemInCart(String itemName) {
        return getItemNames().contains(itemName);
    }

    /**
     * Check if multiple items are in cart
     * @param itemNames List of item names to check
     * @return true if all items are in cart, false otherwise
     */
    public boolean areItemsInCart(List<String> itemNames) {
        List<String> cartItemNames = getItemNames();
        return cartItemNames.containsAll(itemNames);
    }

    /**
     * Verify cart contains expected number of items
     * @param expectedCount Expected number of items
     * @return true if count matches, false otherwise
     */
    public boolean verifyItemCount(int expectedCount) {
        return getCartItemsCount() == expectedCount;
    }

    // ========================
    // PRICE CALCULATION METHODS
    // ========================

    /**
     * Calculate total price of all items in cart
     * @return Total price as double
     */
    public double calculateTotalPrice() {
        double total = 0.0;
        List<Double> prices = getItemPricesAsDouble();
        List<String> quantities = getItemQuantities();

        for (int i = 0; i < prices.size(); i++) {
            try {
                int quantity = Integer.parseInt(quantities.get(i).trim());
                total += prices.get(i) * quantity;
            } catch (NumberFormatException e) {
                // If quantity is not a number, assume 1
                total += prices.get(i);
            }
        }

        return Math.round(total * 100.0) / 100.0; // Round to 2 decimal places
    }

    /**
     * Get the most expensive item in cart
     * @return Item name with highest price, or null if cart is empty
     */
    public String getMostExpensiveItem() {
        if (isCartEmpty()) {
            return null;
        }

        List<String> names = getItemNames();
        List<Double> prices = getItemPricesAsDouble();

        double maxPrice = prices.get(0);
        int maxIndex = 0;

        for (int i = 1; i < prices.size(); i++) {
            if (prices.get(i) > maxPrice) {
                maxPrice = prices.get(i);
                maxIndex = i;
            }
        }

        return names.get(maxIndex);
    }

    /**
     * Get the cheapest item in cart
     * @return Item name with lowest price, or null if cart is empty
     */
    public String getCheapestItem() {
        if (isCartEmpty()) {
            return null;
        }

        List<String> names = getItemNames();
        List<Double> prices = getItemPricesAsDouble();

        double minPrice = prices.get(0);
        int minIndex = 0;

        for (int i = 1; i < prices.size(); i++) {
            if (prices.get(i) < minPrice) {
                minPrice = prices.get(i);
                minIndex = i;
            }
        }

        return names.get(minIndex);
    }

    // ========================
    // NAVIGATION METHODS
    // ========================

    /**
     * Continue shopping (return to inventory page)
     * @return InventoryPage object
     */
    public InventoryPage continueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton)).click();
        return new InventoryPage(driver);
    }

    /**
     * Proceed to checkout
     * @return CheckoutStepOnePage object
     */
    public CheckoutStepOnePage checkout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
        return new CheckoutStepOnePage(driver);
    }

    /**
     * Click on item name to go to product detail page
     * @param itemName Name of the item to click
     * @return ProductDetailPage object, or null if item not found
     */
    public ProductDetailPage clickOnItem(String itemName) {
        List<String> names = getItemNames();

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(itemName)) {
                itemNameLinks.get(i).click();
                return new ProductDetailPage(driver);
            }
        }
        return null;
    }

    // ========================
    // PAGE VERIFICATION METHODS
    // ========================

    /**
     * Verify if cart page is loaded
     * @return true if cart page is loaded, false otherwise
     */
    public boolean isCartPageLoaded() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(pageTitle))
                    .getText().equals(Constants.CART_PAGE_TITLE);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify cart page headers are displayed
     * @return true if headers are visible, false otherwise
     */
    public boolean areCartHeadersDisplayed() {
        try {
            return quantityLabel.isDisplayed() && descriptionLabel.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify action buttons are displayed and enabled
     * @return true if both buttons are available, false otherwise
     */
    public boolean areActionButtonsEnabled() {
        try {
            return continueShoppingButton.isDisplayed() && continueShoppingButton.isEnabled() &&
                    checkoutButton.isDisplayed() && checkoutButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    // ========================
    // CART SUMMARY METHODS
    // ========================

    /**
     * Get cart summary information as formatted string
     * @return Cart summary as String
     */
    public String getCartSummary() {
        StringBuilder summary = new StringBuilder();

        try {
            summary.append("Cart Summary:\n");
            summary.append("=============\n");
            summary.append("Total Items: ").append(getCartItemsCount()).append("\n");
            summary.append("Total Quantity: ").append(getTotalQuantity()).append("\n");
            summary.append("Total Price: $").append(String.format("%.2f", calculateTotalPrice())).append("\n");
            summary.append("\nItems in Cart:\n");

            List<String> names = getItemNames();
            List<String> prices = getItemPrices();
            List<String> quantities = getItemQuantities();

            for (int i = 0; i < names.size(); i++) {
                summary.append("- ").append(names.get(i))
                        .append(" (Qty: ").append(quantities.get(i))
                        .append(", Price: ").append(prices.get(i)).append(")\n");
            }

        } catch (Exception e) {
            summary.append("Error generating cart summary: ").append(e.getMessage());
        }

        return summary.toString();
    }

    /**
     * Compare cart contents with expected items
     * @param expectedItems List of expected item names
     * @return true if cart contains exactly the expected items, false otherwise
     */
    public boolean verifyCartContents(List<String> expectedItems) {
        List<String> actualItems = getItemNames();

        if (actualItems.size() != expectedItems.size()) {
            return false;
        }

        // Check if all expected items are present (order independent)
        return actualItems.containsAll(expectedItems) && expectedItems.containsAll(actualItems);
    }

    // ========================
    // WAIT HELPER METHODS
    // ========================

    /**
     * Wait for cart to load with items
     * @param timeoutSeconds Timeout in seconds
     * @return true if cart loaded successfully, false otherwise
     */
    public boolean waitForCartLoad(int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(ExpectedConditions.visibilityOf(pageTitle));
            wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton));
            wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for specific item to be removed from cart
     * @param itemName Name of the item
     * @param timeoutSeconds Timeout in seconds
     * @return true if item was removed, false otherwise
     */
    public boolean waitForItemRemoval(String itemName, int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(driver -> !isItemInCart(itemName));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for cart to become empty
     * @param timeoutSeconds Timeout in seconds
     * @return true if cart becomes empty, false otherwise
     */
    public boolean waitForEmptyCart(int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(driver -> isCartEmpty());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

