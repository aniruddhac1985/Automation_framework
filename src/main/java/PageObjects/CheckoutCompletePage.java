package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.Constants;

/**
 * CheckoutCompletePage class represents the order completion confirmation page in SauceDemo application
 * Contains methods to verify successful order completion and navigate back to shopping
 * URL: https://www.saucedemo.com/checkout-complete.html
 */
public class CheckoutCompletePage extends BasePage {

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
    // SUCCESS MESSAGE ELEMENTS
    // ========================

    /** Order completion success header */
    @FindBy(className = "complete-header")
    private WebElement successHeader;

    /** Order completion success message/text */
    @FindBy(className = "complete-text")
    private WebElement successText;

    /** Complete checkout container */
    @FindBy(className = "checkout_complete_container")
    private WebElement checkoutCompleteContainer;

    // ========================
    // IMAGE ELEMENTS
    // ========================

    /** Pony Express delivery image */
    @FindBy(className = "pony_express")
    private WebElement ponyExpressImage;

    /** Checkout complete image container */
    @FindBy(css = ".checkout_complete_container img")
    private WebElement completionImage;

    // ========================
    // ACTION BUTTONS
    // ========================

    /** Back to products/home button */
    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;

    /** Alternative back to products button (if exists) */
    @FindBy(css = "button[data-test='back-to-products']")
    private WebElement backToProductsButton;

    // ========================
    // CONSTRUCTOR
    // ========================

    /**
     * Constructor to initialize CheckoutCompletePage
     * @param driver WebDriver instance
     */
    public CheckoutCompletePage(WebDriver driver) {
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
     * Check if checkout complete page is loaded
     * @return true if page is loaded correctly, false otherwise
     */
    public boolean isCheckoutCompletePageLoaded() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(pageTitle))
                    .getText().equals(Constants.CHECKOUT_COMPLETE_TITLE);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if order completion is successful based on page content
     * @return true if order success is confirmed, false otherwise
     */
    public boolean isOrderCompletionSuccessful() {
        try {
            return isCheckoutCompletePageLoaded() &&
                    getSuccessHeader().equals(Constants.ORDER_SUCCESS_HEADER);
        } catch (Exception e) {
            return false;
        }
    }

    // ========================
    // SUCCESS MESSAGE METHODS
    // ========================

    /**
     * Get success header text
     * @return Success header text as String
     */
    public String getSuccessHeader() {
        return wait.until(ExpectedConditions.visibilityOf(successHeader)).getText();
    }

    /**
     * Get success message text
     * @return Success message text as String
     */
    public String getSuccessText() {
        return wait.until(ExpectedConditions.visibilityOf(successText)).getText();
    }

    /**
     * Check if order was successful based on success header
     * @return true if order success header is displayed, false otherwise
     */
    public boolean isOrderSuccessful() {
        try {
            return getSuccessHeader().equals(Constants.ORDER_SUCCESS_HEADER);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify success message content
     * @return true if success message matches expected content, false otherwise
     */
    public boolean isSuccessMessageCorrect() {
        try {
            String actualMessage = getSuccessText();
            return actualMessage.contains("dispatched") &&
                    actualMessage.contains("pony") &&
                    actualMessage.toLowerCase().contains("order");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verify expected success message is displayed
     * @return true if expected success message is shown, false otherwise
     */
    public boolean isExpectedSuccessMessageDisplayed() {
        try {
            return getSuccessText().contains(Constants.ORDER_SUCCESS_MESSAGE) ||
                    isSuccessMessageCorrect();
        } catch (Exception e) {
            return false;
        }
    }

    // ========================
    // SUCCESS HEADER VALIDATION
    // ========================

    /**
     * Check if success header contains "Thank you"
     * @return true if header contains thank you message, false otherwise
     */
    public boolean isThankYouMessageDisplayed() {
        try {
            return getSuccessHeader().toLowerCase().contains("thank you");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate success header format and content
     * @return true if header format is correct, false otherwise
     */
    public boolean isSuccessHeaderValid() {
        try {
            String header = getSuccessHeader();
            return header != null &&
                    !header.trim().isEmpty() &&
                    header.contains("!");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get success header text length
     * @return Length of success header text
     */
    public int getSuccessHeaderLength() {
        try {
            return getSuccessHeader().length();
        } catch (Exception e) {
            return 0;
        }
    }

    // ========================
    // IMAGE VALIDATION METHODS
    // ========================

    /**
     * Check if Pony Express image is displayed
     * @return true if pony express image is visible, false otherwise
     */
    public boolean isPonyExpressImageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(ponyExpressImage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if completion image is displayed
     * @return true if completion image is visible, false otherwise
     */
    public boolean isCompletionImageDisplayed() {
        try {
            return completionImage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get Pony Express image source URL
     * @return Image source URL as String, or null if not available
     */
    public String getPonyExpressImageSrc() {
        try {
            if (isPonyExpressImageDisplayed()) {
                return ponyExpressImage.getAttribute("src");
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get Pony Express image alt text
     * @return Image alt text as String, or null if not available
     */
    public String getPonyExpressImageAltText() {
        try {
            if (isPonyExpressImageDisplayed()) {
                return ponyExpressImage.getAttribute("alt");
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // ========================
    // NAVIGATION METHODS
    // ========================

    /**
     * Navigate back to products/inventory page
     * @return InventoryPage object
     */
    public InventoryPage backToProducts() {
        WebElement backButton = getBackButton();
        wait.until(ExpectedConditions.elementToBeClickable(backButton)).click();
        return new InventoryPage(driver);
    }

    /**
     * Alternative method name for back navigation
     * @return InventoryPage object
     */
    public InventoryPage backToHome() {
        return backToProducts();
    }

    /**
     * Get the appropriate back button element
     * @return WebElement representing the back button
     */
    private WebElement getBackButton() {
        try {
            if (backHomeButton.isDisplayed()) {
                return backHomeButton;
            }
        } catch (Exception e) {
            // Try alternative button if primary is not available
        }

        try {
            if (backToProductsButton.isDisplayed()) {
                return backToProductsButton;
            }
        } catch (Exception e) {
            // Return primary button as fallback
        }

        return backHomeButton; // Default fallback
    }

    // ========================
    // BUTTON STATE METHODS
    // ========================

    /**
     * Check if back to products button is enabled and clickable
     * @return true if button is clickable, false otherwise
     */
    public boolean isBackButtonEnabled() {
        try {
            WebElement backButton = getBackButton();
            return backButton.isDisplayed() && backButton.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get back button text
     * @return Button text as String
     */
    public String getBackButtonText() {
        try {
            return getBackButton().getText();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Check if back button is visible
     * @return true if button is visible, false otherwise
     */
    public boolean isBackButtonVisible() {
        try {
            return getBackButton().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ========================
    // COMPLETE PAGE VALIDATION
    // ========================

    /**
     * Validate all essential elements are present on the page
     * @return true if all key elements are displayed, false otherwise
     */
    public boolean areAllEssentialElementsDisplayed() {
        try {
            return isCheckoutCompletePageLoaded() &&
                    isOrderSuccessful() &&
                    isSuccessMessageCorrect() &&
                    isPonyExpressImageDisplayed() &&
                    isBackButtonEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Perform comprehensive validation of the completion page
     * @return true if page passes all validation checks, false otherwise
     */
    public boolean validateCompletionPage() {
        boolean isValid = true;

        // Check page loading
        if (!isCheckoutCompletePageLoaded()) {
            isValid = false;
        }

        // Check success header
        if (!isOrderSuccessful()) {
            isValid = false;
        }

        // Check success message
        if (!isSuccessMessageCorrect()) {
            isValid = false;
        }

        // Check image display
        if (!isPonyExpressImageDisplayed()) {
            isValid = false;
        }

        // Check button functionality
        if (!isBackButtonEnabled()) {
            isValid = false;
        }

        return isValid;
    }

    // ========================
    // INFORMATION RETRIEVAL METHODS
    // ========================

    /**
     * Get complete order completion information
     * @return Order completion summary as formatted String
     */
    public String getOrderCompletionSummary() {
        StringBuilder summary = new StringBuilder();

        try {
            summary.append("Order Completion Summary:\n");
            summary.append("========================\n");
            summary.append("Page Title: ").append(getPageTitle()).append("\n");
            summary.append("Success Header: ").append(getSuccessHeader()).append("\n");
            summary.append("Success Message: ").append(getSuccessText()).append("\n");
            summary.append("Order Successful: ").append(isOrderSuccessful()).append("\n");
            summary.append("Pony Image Displayed: ").append(isPonyExpressImageDisplayed()).append("\n");
            summary.append("Back Button Enabled: ").append(isBackButtonEnabled()).append("\n");
            summary.append("Back Button Text: ").append(getBackButtonText()).append("\n");
            summary.append("Page Validation Passed: ").append(validateCompletionPage()).append("\n");

            String imageSrc = getPonyExpressImageSrc();
            if (imageSrc != null) {
                summary.append("Image Source: ").append(imageSrc).append("\n");
            }

        } catch (Exception e) {
            summary.append("Error generating completion summary: ").append(e.getMessage());
        }

        return summary.toString();
    }

    /**
     * Get current page URL for verification
     * @return Current page URL as String
     */
    public String getCurrentUrl() {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Verify current URL matches expected checkout complete URL
     * @return true if URL is correct, false otherwise
     */
    public boolean isOnCorrectUrl() {
        try {
            String currentUrl = getCurrentUrl();
            return currentUrl.contains("checkout-complete.html") ||
                    currentUrl.contains(Constants.CHECKOUT_COMPLETE_URL);
        } catch (Exception e) {
            return false;
        }
    }

    // ========================
    // SHOPPING CONTINUATION METHODS
    // ========================

    /**
     * Check if user can continue shopping (back button available)
     * @return true if shopping can be continued, false otherwise
     */
    public boolean canContinueShopping() {
        return isBackButtonEnabled();
    }

    /**
     * Start new shopping session by going back to products
     * @return InventoryPage object for new shopping session
     */
    public InventoryPage startNewShoppingSession() {
        return backToProducts();
    }

    // ========================
    // WAIT HELPER METHODS
    // ========================

    /**
     * Wait for checkout complete page to fully load
     * @param timeoutSeconds Timeout in seconds
     * @return true if page loaded successfully, false otherwise
     */
    public boolean waitForPageLoad(int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(ExpectedConditions.visibilityOf(pageTitle));
            wait.until(ExpectedConditions.visibilityOf(successHeader));
            wait.until(ExpectedConditions.visibilityOf(successText));
            wait.until(ExpectedConditions.visibilityOf(ponyExpressImage));
            wait.until(ExpectedConditions.elementToBeClickable(getBackButton()));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for order completion confirmation to be displayed
     * @param timeoutSeconds Timeout in seconds
     * @return true if order completion is confirmed, false otherwise
     */
    public boolean waitForOrderCompletion(int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(driver -> isOrderCompletionSuccessful());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for all essential elements to be displayed
     * @param timeoutSeconds Timeout in seconds
     * @return true if all elements are displayed, false otherwise
     */
    public boolean waitForAllElements(int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(driver -> areAllEssentialElementsDisplayed());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for success message to be displayed
     * @param timeoutSeconds Timeout in seconds
     * @return true if success message appeared, false otherwise
     */
    public boolean waitForSuccessMessage(int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(ExpectedConditions.visibilityOf(successHeader));
            wait.until(ExpectedConditions.visibilityOf(successText));
            return isOrderSuccessful() && isSuccessMessageCorrect();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for pony express image to load
     * @param timeoutSeconds Timeout in seconds
     * @return true if image loaded, false otherwise
     */
    public boolean waitForPonyImage(int timeoutSeconds) {
        try {
            wait = new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(timeoutSeconds));

            wait.until(ExpectedConditions.visibilityOf(ponyExpressImage));
            return isPonyExpressImageDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

