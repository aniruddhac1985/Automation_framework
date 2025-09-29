package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import utilities.Constants;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Comparator;

/**
 * InventoryPage class represents the products/inventory page of SauceDemo application
 * Contains methods to interact with product listings, sorting, and cart operations
 * URL: https://www.saucedemo.com/inventory.html
 */
public class InventoryPage extends BasePage {

    // ========================
    // PAGE HEADER ELEMENTS
    // ========================

    /** Page title element */
    @FindBy(className = "title")
    private WebElement pageTitle;

    /** Product sorting dropdown */
    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    /** Secondary header element */
    @FindBy(className = "header_secondary_container")
    private WebElement secondaryHeader;

    // ========================
    // PRODUCT CONTAINER ELEMENTS
    // ========================

    /** List of all inventory items */
    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    /** List of inventory item containers */
    @FindBy(className = "inventory_list")
    private List<WebElement> inventoryList;

    // ========================
    // PRODUCT INFORMATION ELEMENTS
    // ========================

    /** List of product names */
    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    /** List of product descriptions */
    @FindBy(className = "inventory_item_desc")
    private List<WebElement> itemDescriptions;

    /** List of product prices */
    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    /** List of product images */
    @FindBy(className = "inventory_item_img")
    private List<WebElement> itemImages;

    /** List of product image links */
    @FindBy(css = ".inventory_item_img a")
    private List<WebElement> itemImageLinks;

    // ========================
    // PRODUCT ACTION ELEMENTS
    // ========================

    /** List of add to cart buttons */
    @FindBy(css = "button[id^='add-to-cart']")
    private List<WebElement> addToCartButtons;

    /** List of remove from cart buttons */
    @FindBy(css = "button[id^='remove']")
    private List<WebElement> removeButtons;

    // ========================
    // CONSTRUCTOR
    // ========================

    /**
     * Constructor to initialize InventoryPage
     * @param driver WebDriver instance
     */
    public InventoryPage(WebDriver driver) {
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
     * Get current sort selection
     * @return Currently selected sort option as String
     */
    public String getCurrentSortOption() {
        Select sortSelect = new Select(sortDropdown);
        return sortSelect.getFirstSelectedOption().getText();
    }

    /**
     * Get current sort value
     * @return Currently selected sort value as String
     */
    public String getCurrentSortValue() {
        Select sortSelect = new Select(sortDropdown);
        return sortSelect.getFirstSelectedOption().getAttribute("value");
    }

    // ========================
    // INVENTORY COUNT METHODS
    // ========================

    /**
     * Get total number of inventory items displayed
     * @return Number of inventory items
     */
    public int getInventoryItemCount() {
        return inventoryItems.size();
    }

    /**
     * Verify expected number of products are displayed
     * @param expectedCount Expected number of products
     * @return true if count matches, false otherwise
     */
    public boolean verifyProductCount(int expectedCount) {
        return getInventoryItemCount() == expectedCount;
    }

    // ========================
    // PRODUCT INFORMATION METHODS
    // ========================

    /**
     * Get list of all product names
     * @return List of product names as Strings
     */
    public List<String> getItemNames() {
        return itemNames.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Get list of all product descriptions
     * @return List of product descriptions as Strings
     */
    public List<String> getItemDescriptions() {
        return itemDescriptions.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Get list of all product prices
     * @return List of product prices as Strings
     */
    public List<String> getItemPrices() {
        return itemPrices.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Get list of all product prices as double values
     * @return List of product prices as doubles
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
     * Get product name by index
     * @param index Index of the product (0-based)
     * @return Product name as String, or null if index is invalid
     */
    public String getItemNameByIndex(int index) {
        if (index >= 0 && index < itemNames.size()) {
            return itemNames.get(index).getText();
        }
        return null;
    }

    /**
     * Get product price by name
     * @param itemName Name of the product
     * @return Product price as String, or null if product not found
     */
    public String getItemPriceByName(String itemName) {
        List<String> names = getItemNames();
        List<String> prices = getItemPrices();

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(itemName)) {
                return prices.get(i);
            }
        }
        return null;
    }

    // ========================
    // SORTING METHODS
    // ========================

    /**
     * Sort products by visible text option
     * @param sortOption Sort option text (e.g., "Name (A to Z)")
     */
    public void sortProducts(String sortOption) {
        Select sortSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(sortDropdown)));
        sortSelect.selectByVisibleText(sortOption);

        // Wait for sorting to complete
        waitForSortingToComplete();
    }

    /**
     * Sort products by dropdown value
     * @param sortValue Sort value (e.g., "az", "za", "lohi", "hilo")
     */
    public void sortProductsByValue(String sortValue) {
        Select sortSelect = new Select(wait.until(ExpectedConditions.elementToBeClickable(sortDropdown)));
        sortSelect.selectByValue(sortValue);

        // Wait for sorting to complete
        waitForSortingToComplete();
    }

    /**
     * Get all available sort options
     * @return List of sort options as Strings
     */
    public List<String> getAvailableSortOptions() {
        Select sortSelect = new Select(sortDropdown);
        return sortSelect.getOptions().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Wait for sorting animation/process to complete
     */
    private void waitForSortingToComplete() {
        try {
            Thread.sleep(1000); // Wait for sorting to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // ========================
    // CART OPERATION METHODS
    // ========================

    /**
     * Add item to cart by product name
     * @param itemName Name of the product to add
     * @return true if item was found and added, false otherwise
     */
    public boolean addItemToCartByName(String itemName) {
        List<String> names = getItemNames();

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(itemName)) {
                if (i < addToCartButtons.size()) {
                    addToCartButtons.get(i).click();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Add item to cart by index
     * @param index Index of the product to add (0-based)
     * @return true if item was added, false if index is invalid
     */
    public boolean addItemToCartByIndex(int index) {
        if (index >= 0 && index < addToCartButtons.size()) {
            addToCartButtons.get(index).click();
            return true;
        }
        return false;
    }

    /**
     * Remove item from cart by product name
     * @param itemName Name of the product to remove
     * @return true if item was found and removed, false otherwise
     */
    public boolean removeItemFromCartByName(String itemName) {
        List<String> names = getItemNames();

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(itemName)) {
                if (i < removeButtons.size() && removeButtons.get(i).isDisplayed()) {
                    removeButtons.get(i).click();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Remove item from cart by index
     * @param index Index of the product to remove (0-based)
     * @return true if item was removed, false if index is invalid
     */
    public boolean removeItemFromCartByIndex(int index) {
        if (index >= 0 && index < removeButtons.size() && removeButtons.get(index).isDisplayed()) {
            removeButtons.get(index).click();
            return true;
        }
        return false;
    }

    /**
     * Add multiple items to cart by names
     * @param itemNames List of product names to add
     * @return Number of items successfully added
     */
    public int addMultipleItemsToCart(List<String> itemNames) {
        int addedCount = 0;
        for (String itemName : itemNames) {
            if (addItemToCartByName(itemName)) {
                addedCount++;
            }
        }
        return addedCount;
    }

    // ========================
    // CART STATE VERIFICATION
    // ========================

    /**
     * Check if item is currently in cart (remove button visible)
     * @param itemName Name of the product to check
     * @return true if item is in cart, false otherwise
     */
    public boolean isItemInCart(String itemName) {
        List<String> names = getItemNames();

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(itemName)) {
                return i < removeButtons.size() && removeButtons.get(i).isDisplayed();
            }
        }
        return false;
    }

    /**
     * Get count of items currently in cart based on remove buttons
     * @return Number of items in cart
     */
    public int getItemsInCartCount() {
        return (int) removeButtons.stream()
                .filter(WebElement::isDisplayed)
                .count();
    }

    /**
     * Get list of items currently in cart
     * @return List of item names that are in cart
     */
    public List<String> getItemsInCart() {
        List<String> itemsInCart = new ArrayList<>();
        List<String> names = getItemNames();

        for (int i = 0; i < names.size(); i++) {
            if (i < removeButtons.size() && removeButtons.get(i).isDisplayed()) {
                itemsInCart.add(names.get(i));
            }
        }
        return itemsInCart;
    }

    // ========================
    // NAVIGATION METHODS
    // ========================

    /**
     * Click on product name to navigate to product detail page
     * @param itemName Name of the product to click
     * @return ProductDetailPage object, or null if product not found
     */
    public ProductDetailPage clickOnItemName(String itemName) {
        List<String> names = getItemNames();

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(itemName)) {
                itemNames.get(i).click();
                return new ProductDetailPage(driver);
            }
        }
        return null;
    }

    /**
     * Click on product image to navigate to product detail page
     * @param index Index of the product image to click (0-based)
     * @return ProductDetailPage object, or null if index is invalid
     */
    public ProductDetailPage clickOnItemImage(int index) {
        if (index >= 0 && index < itemImageLinks.size()) {
            itemImageLinks.get(index).click();
            return new ProductDetailPage(driver);
        }
        return null;
    }

    /**
     * Click on product image by product name
     * @param itemName Name of the product image to click
     * @return ProductDetailPage object, or null if product not found
     */
    public ProductDetailPage clickOnItemImageByName(String itemName) {
        List<String> names = getItemNames();

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(itemName)) {
                if (i < itemImageLinks.size()) {
                    itemImageLinks.get(i).click();
                    return new ProductDetailPage(driver);
                }
            }
        }
        return null;
    }

    /**
     * Navigate to cart page
     * @return CartPage object
     */
    public CartPage goToCart() {
        clickCart();
        return new CartPage(driver);
    }

    // ========================
    // SEARCH AND FILTER METHODS
    // ========================

    /**
     * Find products by name pattern
     * @param namePattern Pattern to search for in product names
     * @return List of matching product names
     */
    public List<String> findProductsByNamePattern(String namePattern) {
        return getItemNames().stream()
                .filter(name -> name.toLowerCase().contains(namePattern.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Find products within price range
     * @param minPrice Minimum price (inclusive)
     * @param maxPrice Maximum price (inclusive)
     * @return List of product names within price range
     */
    public List<String> findProductsByPriceRange(double minPrice, double maxPrice) {
        List<String> names = getItemNames();
        List<Double> prices = getItemPricesAsDouble();
        List<String> matchingProducts = new ArrayList<>();

        for (int i = 0; i < names.size() && i < prices.size(); i++) {
            if (prices.get(i) >= minPrice && prices.get(i) <= maxPrice) {
                matchingProducts.add(names.get(i));
            }
        }

        return matchingProducts;
    }

    /**
     * Get cheapest product
     * @return Name of the cheapest product
     */
    public String getCheapestProduct() {
        List<String> names = getItemNames();
        List<Double> prices = getItemPricesAsDouble();

        if (names.isEmpty() || prices.isEmpty()) {
            return null;
        }

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

    /**
     * Get most expensive product
     * @return Name of the most expensive product
     */
    public String getMostExpensiveProduct() {
        List<String> names = getItemNames();
        List<Double> prices = getItemPricesAsDouble();

        if (names.isEmpty() || prices.isEmpty()) {
            return null;
        }

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

    // ========================
    // SORTING VERIFICATION METHODS
    // ========================

    /**
     * Verify products are sorted by name A to Z
     * @return true if products are sorted correctly, false otherwise
     */
    public boolean isProductsSortedByNameAtoZ() {
        List<String> names = getItemNames();
        List<String> sortedNames = new ArrayList<>(names);
        Collections.sort(sortedNames);
        return names.equals(sortedNames);
    }

    /**
     * Verify products are sorted by name Z to A
     * @return true if products are sorted correctly, false otherwise
     */
    public boolean isProductsSortedByNameZtoA() {
        List<String> names = getItemNames();
        List<String> sortedNames = new ArrayList<>(names);
        sortedNames.sort(Collections.reverseOrder());
        return names.equals(sortedNames);
    }

    /**
     * Verify products are sorted by price low to high
     * @return true if products are sorted correctly, false otherwise
     */
    public boolean isProductsSortedByPriceLowToHigh() {
        List<Double> prices = getItemPricesAsDouble();
        List<Double> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);
        return prices.equals(sortedPrices);
    }

    /**
     * Verify products are sorted by price high to low
     * @return true if products are sorted correctly, false otherwise
     */
    public boolean isProductsSortedByPriceHighToLow() {
        List<Double> prices = getItemPricesAsDouble();
        List<Double> sortedPrices = new ArrayList<>(prices);
        sortedPrices.sort(Collections.reverseOrder());
        return prices.equals(sortedPrices);
    }

    // ========================
    // PAGE VERIFICATION METHODS
    // ========================

    /**
     * Verify if inventory page is loaded
     * @return true if inventory page is loaded, false otherwise
     */
    public boolean isInventoryPageLoaded() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(pageTitle))
                    .getText().equals(Constants.INVENTORY_PAGE_TITLE);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify all expected products are displayed
     * @return true if all 6 products are displayed, false otherwise
     */
    public boolean areAllProductsDisplayed() {
        return getInventoryItemCount() == 6;
    }

    /**
     * Verify sort dropdown is functional
     * @return true if sort dropdown is displayed and enabled, false otherwise
     */
    public boolean isSortDropdownFunctional() {
        try {
            return sortDropdown.isDisplayed() && sortDropdown.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify specific product exists on page
     * @param productName Name of the product to verify
     * @return true if product exists, false otherwise
     */
    public boolean isProductDisplayed(String productName) {
        return getItemNames().contains(productName);
    }

    /**
     * Verify all expected products from constants are displayed
     * @return true if all expected products are present, false otherwise
     */
    public boolean areExpectedProductsDisplayed() {
        List<String> displayedProducts = getItemNames();
        String[] expectedProducts = Constants.getAllProducts();

        for (String expectedProduct : expectedProducts) {
            if (!displayedProducts.contains(expectedProduct)) {
                return false;
            }
        }

        return expectedProducts.length == displayedProducts.size();
    }

    // ========================
    // INVENTORY SUMMARY METHODS
    // ========================

    /**
     * Get inventory page summary
     * @return Inventory summary as formatted String
     */
    public String getInventorySummary() {
        StringBuilder summary = new StringBuilder();

        try {
            summary.append("Inventory Page Summary:\n");
            summary.append("=====================\n");
            summary.append("Total Products: ").append(getInventoryItemCount()).append("\n");
            summary.append("Current Sort: ").append(getCurrentSortOption()).append("\n");
            summary.append("Items in Cart: ").append(getItemsInCartCount()).append("\n");
            summary.append("Cheapest Product: ").append(getCheapestProduct()).append("\n");
            summary.append("Most Expensive Product: ").append(getMostExpensiveProduct()).append("\n");
            summary.append("\nProduct List:\n");

            List<String> names = getItemNames();
            List<String> prices = getItemPrices();

            for (int i = 0; i < names.size() && i < prices.size(); i++) {
                String inCart = isItemInCart(names.get(i)) ? " [IN CART]" : "";
                summary.append("- ").append(names.get(i))
                        .append(" (").append(prices.get(i)).append(")")
                        .append(inCart).append("\n");
            }

        } catch (Exception e) {
            summary.append("Error generating inventory summary: ").append(e.getMessage());
        }

        return summary.toString();
    }

    // ========================
    // WAIT HELPER METHODS
    // ========================

    /**
     * Wait for inventory page to fully load
     * @param timeoutSeconds Timeout in seconds
     * @return true if page loaded successfully, false otherwise
     */
    public boolean waitForInventoryPageLoad(int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(ExpectedConditions.visibilityOf(pageTitle));
            wait.until(ExpectedConditions.elementToBeClickable(sortDropdown));
            wait.until(ExpectedConditions.visibilityOfAllElements(inventoryItems));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for cart badge to update with expected count
     * @param expectedCount Expected cart item count
     * @param timeoutSeconds Timeout in seconds
     * @return true if cart count matches expected, false otherwise
     */
    public boolean waitForCartBadgeUpdate(int expectedCount, int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(driver -> getCartItemCount() == expectedCount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

