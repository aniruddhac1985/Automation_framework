package PageObjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import utilities.Constants;
import java.util.regex.Pattern;

/**
 * ProductDetailPage class represents the product detail page of SauceDemo application
 * Contains methods to interact with individual product information and cart operations
 * URL pattern: https://www.saucedemo.com/inventory-item.html?id=X
 */
public class ProductDetailPage extends BasePage {

    // ========================
    // PRODUCT DETAIL ELEMENTS
    // ========================

    /** Product name element */
    @FindBy(className = "inventory_details_name")
    private WebElement productName;

    /** Product description element */
    @FindBy(className = "inventory_details_desc")
    private WebElement productDescription;

    /** Product price element */
    @FindBy(className = "inventory_details_price")
    private WebElement productPrice;

    /** Product image element */
    @FindBy(className = "inventory_details_img")
    private WebElement productImage;

    /** Product image source */
    @FindBy(css = ".inventory_details_img img")
    private WebElement productImageSrc;

    // ========================
    // ACTION BUTTONS
    // ========================

    /** Add to cart button (dynamic ID based on product) */
    @FindBy(css = "button[id^='add-to-cart']")
    private WebElement addToCartButton;

    /** Remove from cart button (dynamic ID based on product) */
    @FindBy(css = "button[id^='remove']")
    private WebElement removeButton;

    /** Back to products button */
    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    // ========================
    // CONSTRUCTOR
    // ========================

    /**
     * Constructor to initialize ProductDetailPage
     * @param driver WebDriver instance
     */
    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    // ========================
    // PRODUCT INFORMATION METHODS
    // ========================

    /**
     * Get product name displayed on the page
     * @return Product name as String
     */
    public String getProductName() {
        return wait.until(ExpectedConditions.visibilityOf(productName)).getText();
    }

    /**
     * Get product description
     * @return Product description as String
     */
    public String getProductDescription() {
        return wait.until(ExpectedConditions.visibilityOf(productDescription)).getText();
    }

    /**
     * Get product price
     * @return Product price as String (e.g., "$29.99")
     */
    public String getProductPrice() {
        return wait.until(ExpectedConditions.visibilityOf(productPrice)).getText();
    }

    /**
     * Get product price as double value
     * @return Product price as double (removes $ symbol)
     */
    public double getProductPriceAsDouble() {
        String priceText = getProductPrice();
        return Double.parseDouble(priceText.replace("$", ""));
    }

    /**
     * Get product image source URL
     * @return Image source URL as String
     */
    public String getProductImageSrc() {
        return wait.until(ExpectedConditions.visibilityOf(productImageSrc)).getAttribute("src");
    }

    /**
     * Get product image alt text
     * @return Image alt text as String
     */
    public String getProductImageAltText() {
        return wait.until(ExpectedConditions.visibilityOf(productImageSrc)).getAttribute("alt");
    }

    // ========================
    // PRODUCT VALIDATION METHODS
    // ========================

    /**
     * Validate if product price format is correct
     * @return true if price format is valid, false otherwise
     */
    public boolean isProductPriceValid() {
        String priceText = getProductPrice();
        return Pattern.matches(Constants.PRICE_REGEX, priceText);
    }

    /**
     * Validate if product name is not empty
     * @return true if product name is valid, false otherwise
     */
    public boolean isProductNameValid() {
        String name = getProductName();
        return name != null && !name.trim().isEmpty();
    }

    /**
     * Validate if product description is not empty
     * @return true if product description is valid, false otherwise
     */
    public boolean isProductDescriptionValid() {
        String description = getProductDescription();
        return description != null && !description.trim().isEmpty();
    }

    /**
     * Validate if product image is displayed
     * @return true if image is displayed, false otherwise
     */
    public boolean isProductImageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(productImageSrc)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ========================
    // CART OPERATION METHODS
    // ========================

    /**
     * Add product to cart
     */
    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    /**
     * Remove product from cart
     */
    public void removeFromCart() {
        wait.until(ExpectedConditions.elementToBeClickable(removeButton)).click();
    }

    /**
     * Toggle cart state (add if not in cart, remove if in cart)
     */
    public void toggleCartState() {
        if (isProductInCart()) {
            removeFromCart();
        } else {
            addToCart();
        }
    }

    // ========================
    // BUTTON STATE METHODS
    // ========================

    /**
     * Check if add to cart button is displayed and clickable
     * @return true if add to cart button is available, false otherwise
     */
    public boolean isAddToCartButtonDisplayed() {
        try {
            return addToCartButton.isDisplayed() && addToCartButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if remove button is displayed and clickable
     * @return true if remove button is available, false otherwise
     */
    public boolean isRemoveButtonDisplayed() {
        try {
            return removeButton.isDisplayed() && removeButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if product is currently in cart
     * @return true if product is in cart (remove button visible), false otherwise
     */
    public boolean isProductInCart() {
        return isRemoveButtonDisplayed();
    }

    /**
     * Get add to cart button text
     * @return Button text as String
     */
    public String getAddToCartButtonText() {
        if (isAddToCartButtonDisplayed()) {
            return addToCartButton.getText();
        }
        return "";
    }

    /**
     * Get remove button text
     * @return Button text as String
     */
    public String getRemoveButtonText() {
        if (isRemoveButtonDisplayed()) {
            return removeButton.getText();
        }
        return "";
    }

    // ========================
    // NAVIGATION METHODS
    // ========================

    /**
     * Navigate back to products/inventory page
     * @return InventoryPage object
     */
    public InventoryPage backToProducts() {
        wait.until(ExpectedConditions.elementToBeClickable(backToProductsButton)).click();
        return new InventoryPage(driver);
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
    // PAGE VERIFICATION METHODS
    // ========================

    /**
     * Verify if product detail page is loaded
     * @return true if page is loaded, false otherwise
     */
    public boolean isProductDetailPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(productName));
            wait.until(ExpectedConditions.visibilityOf(productPrice));
            wait.until(ExpectedConditions.visibilityOf(backToProductsButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify if specific product is displayed
     * @param expectedProductName Expected product name
     * @return true if correct product is displayed, false otherwise
     */
    public boolean isCorrectProductDisplayed(String expectedProductName) {
        try {
            String actualProductName = getProductName();
            return actualProductName.equals(expectedProductName);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get current product ID from URL
     * @return Product ID as String, or null if not found
     */
    public String getCurrentProductId() {
        try {
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("inventory-item.html?id=")) {
                return currentUrl.substring(currentUrl.lastIndexOf("=") + 1);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // ========================
    // PRODUCT COMPARISON METHODS
    // ========================

    /**
     * Verify if displayed product matches expected product details
     * @param expectedName Expected product name
     * @param expectedPrice Expected product price
     * @return true if product details match, false otherwise
     */
    public boolean verifyProductDetails(String expectedName, String expectedPrice) {
        try {
            String actualName = getProductName();
            String actualPrice = getProductPrice();

            return actualName.equals(expectedName) && actualPrice.equals(expectedPrice);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get all product information as formatted string
     * @return Product information as String
     */
    public String getProductInformation() {
        StringBuilder productInfo = new StringBuilder();

        try {
            productInfo.append("Product Name: ").append(getProductName()).append("\n");
            productInfo.append("Product Price: ").append(getProductPrice()).append("\n");
            productInfo.append("Product Description: ").append(getProductDescription()).append("\n");
            productInfo.append("In Cart: ").append(isProductInCart()).append("\n");
            productInfo.append("Product ID: ").append(getCurrentProductId()).append("\n");
        } catch (Exception e) {
            productInfo.append("Error retrieving product information: ").append(e.getMessage());
        }

        return productInfo.toString();
    }

    // ========================
    // SPECIFIC PRODUCT METHODS
    // ========================

    /**
     * Check if current product is Sauce Labs Backpack
     * @return true if current product is backpack, false otherwise
     */
    public boolean isBackpackProduct() {
        return getProductName().equals(Constants.SAUCE_LABS_BACKPACK);
    }

    /**
     * Check if current product is Sauce Labs Bike Light
     * @return true if current product is bike light, false otherwise
     */
    public boolean isBikeLightProduct() {
        return getProductName().equals(Constants.SAUCE_LABS_BIKE_LIGHT);
    }

    /**
     * Check if current product is the cheapest item
     * @return true if current product has lowest price, false otherwise
     */
    public boolean isCheapestProduct() {
        return getProductPrice().equals(Constants.ONESIE_PRICE);
    }

    /**
     * Check if current product is the most expensive item
     * @return true if current product has highest price, false otherwise
     */
    public boolean isMostExpensiveProduct() {
        return getProductPrice().equals(Constants.FLEECE_JACKET_PRICE);
    }

    // ========================
    // DYNAMIC ELEMENT METHODS
    // ========================

    /**
     * Get add to cart button by specific product ID
     * @param productId Product ID
     * @return WebElement for add to cart button
     */
    private WebElement getAddToCartButtonByProductId(String productId) {
        String buttonId = Constants.ADD_TO_CART_BUTTON_PREFIX + getProductNameForId(productId);
        return driver.findElement(By.id(buttonId));
    }

    /**
     * Get remove button by specific product ID
     * @param productId Product ID
     * @return WebElement for remove button
     */
    private WebElement getRemoveButtonByProductId(String productId) {
        String buttonId = Constants.REMOVE_BUTTON_PREFIX + getProductNameForId(productId);
        return driver.findElement(By.id(buttonId));
    }

    /**
     * Convert product ID to product name identifier for button IDs
     * @param productId Product ID
     * @return Product name identifier used in button IDs
     */
    private String getProductNameForId(String productId) {
        switch (productId) {
            case Constants.BACKPACK_ID:
                return "sauce-labs-backpack";
            case Constants.BIKE_LIGHT_ID:
                return "sauce-labs-bike-light";
            case Constants.BOLT_TSHIRT_ID:
                return "sauce-labs-bolt-t-shirt";
            case Constants.FLEECE_JACKET_ID:
                return "sauce-labs-fleece-jacket";
            case Constants.ONESIE_ID:
                return "sauce-labs-onesie";
            case Constants.TEST_TSHIRT_ID:
                return "test.allthethings()-t-shirt-(red)";
            default:
                return "";
        }
    }

    // ========================
    // WAIT HELPER METHODS
    // ========================

    /**
     * Wait for product detail page to fully load
     * @param timeoutSeconds Timeout in seconds
     * @return true if page loaded successfully, false otherwise
     */
    public boolean waitForPageLoad(int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(ExpectedConditions.visibilityOf(productName));
            wait.until(ExpectedConditions.visibilityOf(productPrice));
            wait.until(ExpectedConditions.visibilityOf(productDescription));
            wait.until(ExpectedConditions.elementToBeClickable(backToProductsButton));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for cart button to change state
     * @param expectedInCart Expected cart state
     * @param timeoutSeconds Timeout in seconds
     * @return true if state changed as expected, false otherwise
     */
    public boolean waitForCartStateChange(boolean expectedInCart, int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            if (expectedInCart) {
                wait.until(ExpectedConditions.visibilityOf(removeButton));
            } else {
                wait.until(ExpectedConditions.visibilityOf(addToCartButton));
            }

            return isProductInCart() == expectedInCart;
        } catch (Exception e) {
            return false;
        }
    }
}

